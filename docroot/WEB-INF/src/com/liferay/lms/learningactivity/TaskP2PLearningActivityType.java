package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TaskP2PAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
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

public class TaskP2PLearningActivityType extends BaseLearningActivityType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactoryUtil.getLog(TaskP2PLearningActivityType.class);
	
	public final static long TYPE_ID = 3;
	
	public static final int DEFAULT_VALIDATION_NUMBER = 3;
	
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"p2ptaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) throws SystemException, PortalException {
		return new TaskP2PAssetRenderer(larn,this);
	}
	@Override
	public long getDefaultScore() {		
		return 50;
	}

	@Override
	public String getName() {
		return "learningactivity.p2p";
	}

	@Override
	public long getTypeId() {
		return TYPE_ID;
	}
	@Override
	public long getDefaultTries() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public String getExpecificContentPage() {
		return "/html/p2ptaskactivity/admin/edit.jsp";
	}

	@Override
	public boolean hasEditDetails() {
		return false;
	}

	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		PortletRequest actionRequest = (PortletRequest)uploadRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		
		boolean validate = true;

		if((Validator.isNotNull(uploadRequest.getParameter("numValidaciones")))&&
				(!Validator.isNumber(uploadRequest.getParameter("numValidaciones"))))
		{
			SessionErrors.add(actionRequest, "p2ptaskactivity.editActivity.numValidaciones.number");
			validate=false;
		}
	
		return validate;
	}
	
	@Override
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
		String error = null;
		ThemeDisplay themeDisplay = (ThemeDisplay)uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);	
				
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("p2p");
			}else{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			
			
			if(P2pActivityLocalServiceUtil.countByActId(learningActivity.getActId())==0){
			
				//Assignation type team members or course members
				Element assignationType=rootElement.element("assignationType");
				if(assignationType!=null){
					assignationType.detach();
					rootElement.remove(assignationType);
				}
				assignationType = SAXReaderUtil.createElement("assignationType");
				assignationType.setText(ParamUtil.get(uploadRequest,"assignationType","course"));		
				rootElement.add(assignationType);	
				
				//Anonimous P2P
				Element anonimous=rootElement.element("anonimous");
				if(anonimous!=null)
				{
					anonimous.detach();
					rootElement.remove(anonimous);
				}
				anonimous = SAXReaderUtil.createElement("anonimous");
				anonimous.setText(Boolean.toString(ParamUtil.get(uploadRequest,"anonimous",false)));		
				rootElement.add(anonimous);	
				
				Element email_anonimous=rootElement.element("email_anonimous");
				if(email_anonimous!=null)
				{
					email_anonimous.detach();
					rootElement.remove(email_anonimous);
				}
				email_anonimous = SAXReaderUtil.createElement("email_anonimous");
				email_anonimous.setText(Boolean.toString(ParamUtil.get(uploadRequest,"email_anonimous",false)));		
				rootElement.add(email_anonimous);	
				
				Element askForP2PActivities=rootElement.element("askforp2pactivities");
				if(askForP2PActivities!=null)
				{
					askForP2PActivities.detach();
					rootElement.remove(askForP2PActivities);
				}
				askForP2PActivities = SAXReaderUtil.createElement("askforp2pactivities");
				askForP2PActivities.setText(Boolean.toString(ParamUtil.get(uploadRequest,"askforp2pactivities",false)));		
				rootElement.add(askForP2PActivities);
				
				Element numValidaciones=rootElement.element("validaciones");
				if(numValidaciones!=null)
				{
					numValidaciones.detach();
					rootElement.remove(numValidaciones);
				}
				numValidaciones = SAXReaderUtil.createElement("validaciones");
				numValidaciones.setText(Long.toString(ParamUtil.getLong(uploadRequest,"numValidaciones",DEFAULT_VALIDATION_NUMBER)));		
				rootElement.add(numValidaciones);	
				
				Element result=rootElement.element("result");
				if(result!=null)
				{
					result.detach();
					rootElement.remove(result);
				}
				result = SAXReaderUtil.createElement("result");
				result.setText(Boolean.toString(ParamUtil.get(uploadRequest,"result",false)));		
				rootElement.add(result);	
				
				Element fileOptional=rootElement.element("fileoptional");
				if(fileOptional!=null)
				{
					fileOptional.detach();
					rootElement.remove(fileOptional);
				}
				fileOptional = SAXReaderUtil.createElement("fileoptional");
				fileOptional.setText(Boolean.toString(ParamUtil.get(uploadRequest,"fileoptional",false)));		
				rootElement.add(fileOptional);	
				
				
				int uploadMonth = ParamUtil.getInteger(uploadRequest, "uploadMon");
				int uploadYear = ParamUtil.getInteger(uploadRequest, "uploadYear");
				int uploadDay = ParamUtil.getInteger(uploadRequest, "uploadDay");
				int uploadHour = ParamUtil.getInteger(uploadRequest, "uploadHour");
				int uploadMinute = ParamUtil.getInteger(uploadRequest, "uploadMin");
				int uploadAMPM = ParamUtil.getInteger(uploadRequest, "uploadAMPM");
				
				if (uploadAMPM > 0) {
					uploadHour += 12;
				}
				
				Date uploadDate = PortalUtil.getDate(uploadMonth, uploadDay, uploadYear, uploadHour, uploadMinute, themeDisplay.getTimeZone(), (Class<? extends PortalException>)null);
	
				SimpleDateFormat formatUploadDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				//formatUploadDate.setTimeZone(themeDisplay.getTimeZone());
					
				Element dateUpload=rootElement.element("dateupload");
				if(dateUpload!=null)
				{
					dateUpload.detach();
					rootElement.remove(dateUpload);
				}
				dateUpload = SAXReaderUtil.createElement("dateupload");
				dateUpload.setText(formatUploadDate.format(uploadDate));		
				rootElement.add(dateUpload);
				
				
				int elements = 0;
				int numQuestion = Integer.parseInt(PropsUtil.get("lms.p2p.numcustomquestion"));
				for(int i=0;i<numQuestion;i++){
					Element text=rootElement.element("text"+i);
					if(text!=null)
					{
						text.detach();
						rootElement.remove(text);
					}
					
					String textAdd = ParamUtil.getString(uploadRequest,"text"+i,null);
					
					if(textAdd!=null){
						Element textAddElement = SAXReaderUtil.createElement("text"+elements);
						textAddElement.setText(textAdd);
						rootElement.add(textAddElement);
						elements++;
					}
				}
				
			}else{
				log.debug("***NO SE ACTUALIZA EL EXTRA CONTENT PORQUE YA HAY P2PACTIVITY ASOCIADOS A LA ACTIVIDAD***");
				error = "error-p2pActivity-inProgress";
				
				
			}
			
			
			String team = ParamUtil.getString(uploadRequest, "team","0");
			long teamId = 0;
			if(!team.equalsIgnoreCase("0")){
				teamId = Long.parseLong(team);
			}
			Element teamElement=rootElement.element("team");
			if(teamElement!=null){
				teamElement.detach();
				rootElement.remove(teamElement);
			}
			if(teamId!=0){
				teamElement = SAXReaderUtil.createElement("team");
				teamElement.setText(Long.toString(teamId));
				rootElement.add(teamElement);
			}
		
			
		
			learningActivity.setExtracontent(document.formattedString());
			return error;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.p2p.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
	
	@Override
	public boolean isAutoCorrect() {
		return false;
	}
	
	@Override
	public boolean canBeLinked(){
		return false;
	}
	
	@Override
	public boolean canBeSeenResults(){
		return true;
	}
}

