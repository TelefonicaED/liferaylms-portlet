package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TestAssetRenderer;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.tls.lms.util.LiferaylmsUtil;

public class TestLearningActivityType extends BaseLearningActivityType 
{
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"execactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());

	@Override
	public long getDefaultScore() {
		return 50;
	}


	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public boolean isTriesConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return true;
	}
	
	@Override
	public boolean hasDeleteTries() {
		return true;
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) throws SystemException, PortalException {	
		return new TestAssetRenderer(learningactivity,this);
	}


	@Override
	public String getName() {
		return "learningactivity.test";
	}


	@Override
	public long getTypeId() {
		return 0;
	}

	
	@Override
	public String getExpecificContentPage() {
		return "/html/execactivity/test/admin/editoptions.jsp";
	}
		
	@Override
	public String getMesageEditDetails() {
		return "execactivity.editquestions";
	}
	
	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		PortletRequest actionRequest = (PortletRequest)uploadRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		boolean validate=true;
		
		if((Validator.isNotNull(uploadRequest.getParameter("random")))&&
		   ((!Validator.isNumber(uploadRequest.getParameter("random")))||
		    (Long.parseLong(uploadRequest.getParameter("random"))>100)||
		    (Long.parseLong(uploadRequest.getParameter("random"))<0)))
		{
			SessionErrors.add(actionRequest, "execactivity.editActivity.random.number");
			validate=false;
		}
		if((Validator.isNotNull(uploadRequest.getParameter("questionsPerPage")))&&
		   ((!Validator.isNumber(uploadRequest.getParameter("questionsPerPage")))||
			(Long.parseLong(uploadRequest.getParameter("questionsPerPage"))>100)||
		    (Long.parseLong(uploadRequest.getParameter("questionsPerPage"))<0)))
		{
			SessionErrors.add(actionRequest, "execactivity.editActivity.questionsPerPage.number");
			validate=false;
		}
		return validate;
	}
	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws NumberFormatException, Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		//Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
				
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("test");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			Element random=rootElement.element("random");
			if(random!=null)
			{
				random.detach();
				rootElement.remove(random);
			}
			random = SAXReaderUtil.createElement("random");
			random.setText(Long.toString(ParamUtil.get(uploadRequest,"random",0l)));		
			rootElement.add(random);	
			
			Element password=rootElement.element("password");
			if(password!=null)
			{
				password.detach();
				rootElement.remove(password);
			}
			password = SAXReaderUtil.createElement("password");
			password.setText(HtmlUtil.escape(ParamUtil.get(uploadRequest,"password",StringPool.BLANK).trim()));		
			rootElement.add(password);	
						
			Element timeStamp=rootElement.element("timeStamp");
			if(timeStamp!=null)
			{
				timeStamp.detach();
				rootElement.remove(timeStamp);
			}
			timeStamp = SAXReaderUtil.createElement("timeStamp");
			timeStamp.setText(Long.toString(ParamUtil.getLong(uploadRequest, "hourDuration",0) * 3600 
					                      + ParamUtil.getLong(uploadRequest, "minuteDuration",0) * 60 
					                      + ParamUtil.getLong(uploadRequest, "secondDuration",0)));		
			rootElement.add(timeStamp);	

			Element showCorrectAnswer=rootElement.element("showCorrectAnswer");
			if(showCorrectAnswer!=null)
			{
				showCorrectAnswer.detach();
				rootElement.remove(showCorrectAnswer);
			}
			showCorrectAnswer = SAXReaderUtil.createElement("showCorrectAnswer");
			showCorrectAnswer.setText(Boolean.toString(ParamUtil.get(uploadRequest,"showCorrectAnswer",false)));		
			rootElement.add(showCorrectAnswer);	
			
			Element showCorrectAnswerOnlyOnFinalTry=rootElement.element("showCorrectAnswerOnlyOnFinalTry");
			if(showCorrectAnswerOnlyOnFinalTry!=null)
			{
				showCorrectAnswerOnlyOnFinalTry.detach();
				rootElement.remove(showCorrectAnswerOnlyOnFinalTry);
			}
			showCorrectAnswerOnlyOnFinalTry = SAXReaderUtil.createElement("showCorrectAnswerOnlyOnFinalTry");
			showCorrectAnswerOnlyOnFinalTry.setText(Boolean.toString(ParamUtil.get(uploadRequest,"showCorrectAnswerOnlyOnFinalTry",false)));		
			rootElement.add(showCorrectAnswerOnlyOnFinalTry);	
			

			Element improve=rootElement.element("improve");
			if(improve!=null)
			{
				improve.detach();
				rootElement.remove(improve);
			}
			improve = SAXReaderUtil.createElement("improve");
			improve.setText(Boolean.toString(ParamUtil.get(uploadRequest,"improve",false)));		
			rootElement.add(improve);
			
			Element enableorder=rootElement.element("enableorder");
			if(enableorder!=null)
			{
				enableorder.detach();
				rootElement.remove(enableorder);
			}
			enableorder = SAXReaderUtil.createElement("enableorder");
			enableorder.setText(Boolean.toString(ParamUtil.get(uploadRequest,"enableorder",false)));		
			rootElement.add(enableorder);	
			
			Element questionsPerPage=rootElement.element("questionsPerPage");
			if(questionsPerPage!=null)
			{
				questionsPerPage.detach();
				rootElement.remove(questionsPerPage);
			}
			questionsPerPage = SAXReaderUtil.createElement("questionsPerPage");
			
			questionsPerPage.setText(Long.toString(ParamUtil.get(uploadRequest,"questionsPerPage", GetterUtil.getLong(PropsUtil.get("lms.questionsPerPage"), 0L))));
			rootElement.add(questionsPerPage);	
			
			String team = ParamUtil.getString(uploadRequest, "team","0");
			long teamId = 0;
			if(!team.equalsIgnoreCase("0")){
				teamId = Long.parseLong(team);
			}
			
			Element teamElement=rootElement.element("team");
			if(teamElement!=null)
			{
				teamElement.detach();
				rootElement.remove(teamElement);
			}
			if(teamId!=0){
				teamElement = SAXReaderUtil.createElement("team");
				teamElement.setText(Long.toString(teamId));
				rootElement.add(teamElement);
			}
			
			learningActivity.setExtracontent(document.formattedString());
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.test.helpmessage";
	}


	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
	
	@Override
	public boolean allowsBank() {
		return true;
	}
	
}
