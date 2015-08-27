package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TaskP2PAssetRenderer;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskP2PLearningActivityType extends BaseLearningActivityType {

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
		return 3;
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
		boolean start = ParamUtil.getBoolean(uploadRequest, "startdate-enabled", false);
		boolean stop  = ParamUtil.getBoolean(uploadRequest, "stopdate-enabled", false);
		
		Date upload = getDateFromRequest(uploadRequest,"upload");
		Date end = getDateFromRequest(uploadRequest,"stop");

		if((Validator.isNotNull(uploadRequest.getParameter("numValidaciones")))&&
				(!Validator.isNumber(uploadRequest.getParameter("numValidaciones"))))
		{
			SessionErrors.add(actionRequest, "p2ptaskactivity.editActivity.numValidaciones.number");
			validate=false;
		}
		
//		if(start && stop && upload != null && end != null && upload.after(end))
//		{
//			SessionErrors.add(actionRequest, "p2ptaskactivity.editActivity.dateupload.afteractivity");
//			validate=false;
//			System.out.println(" ERROR EN FECHA");
//		}
		
		return validate;
	}
	
	private Date getDateFromRequest(UploadRequest uploadRequest, String key){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int uploadMonth = ParamUtil.getInteger(uploadRequest, key+"Mon");
		int uploadYear = ParamUtil.getInteger(uploadRequest, key+"Year");
		int uploadDay = ParamUtil.getInteger(uploadRequest, key+"Day");
		int uploadHour = ParamUtil.getInteger(uploadRequest, key+"Hour");
		int uploadMinute = ParamUtil.getInteger(uploadRequest, key+"Min");
		int uploadAMPM = ParamUtil.getInteger(uploadRequest, key+"AMPM");
		
		if (uploadAMPM > 0) {
			uploadHour += 12;
		}
		
		try {
			return PortalUtil.getDate(uploadMonth, uploadDay, uploadYear, uploadHour, uploadMinute, themeDisplay.getTimeZone(),  (Class<? extends PortalException>)null);
		} catch (PortalException e) {}
		
		return null;
	}
	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
				
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("p2p");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			Element anonimous=rootElement.element("anonimous");
			if(anonimous!=null)
			{
				anonimous.detach();
				rootElement.remove(anonimous);
			}
			anonimous = SAXReaderUtil.createElement("anonimous");
			anonimous.setText(Boolean.toString(ParamUtil.get(uploadRequest,"anonimous",false)));		
			rootElement.add(anonimous);	
			
			if(P2pActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(P2pActivity.class).add(PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId())))==0
					|| themeDisplay.getPermissionChecker().isOmniadmin()){
			
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
				
			}
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
		return "learningactivity.p2p.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
}

