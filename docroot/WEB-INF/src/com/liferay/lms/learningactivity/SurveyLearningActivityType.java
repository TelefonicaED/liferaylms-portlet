package com.liferay.lms.learningactivity;

import javax.portlet.PortletResponse;

import com.liferay.lms.asset.SurveyAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class SurveyLearningActivityType extends BaseLearningActivityType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"surveyactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	

	public final static long TYPE_ID = 4;
	
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) throws SystemException, PortalException {
		return new SurveyAssetRenderer(larn,this);
	}

	@Override
	public String getName() {
		
		return "learningactivity.survey";
	}

	@Override
	public long getTypeId() {
		return 4;
	}
	
	@Override
	public long getDefaultTries() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getMesageEditDetails() {
		return "surveyactivity.editquestions";
	}
	
	@Override
	public String setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws NumberFormatException, Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
			String team = ParamUtil.getString(uploadRequest, "team","0");
			long teamId = 0;
			if(!team.equalsIgnoreCase("0")){
				teamId = Long.parseLong(team);
			}
			
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("survey");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
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
			
			return null;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.survey.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasDeleteTries() {
		return true;
	}
	
	
	@Override
	public boolean canBeLinked(){
		return true;
	}
	
}
