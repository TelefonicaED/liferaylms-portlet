package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ProcessAction;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.service.impl.CompetenceLocalServiceImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class CompetencesAdmin
 */
public class CompetencesAdmin extends MVCPortlet{
	private static Log log = LogFactoryUtil.getLog(CompetencesAdmin.class);
	
	
	@ProcessAction(name="saveImage")
	public void saveImage(ActionRequest actionRequest,ActionResponse actionResponse) throws FileNotFoundException, IOException 
	{
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), uploadRequest);
		} catch (PortalException e1) {
			
		} catch (SystemException e1) {
			
		}
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),0L,ActionKeys.UPDATE))
		{
			User user = themeDisplay.getUser();
		
		String fileName = uploadRequest.getFileName("fileName");
		if(fileName!=null && fileName.length()>0)
		{
			File file = uploadRequest.getFile("fileName");
			String contentType = uploadRequest.getContentType("fileName");
			byte[] data=IOUtils.toByteArray(new FileInputStream(file));
			CompetenceLocalServiceUtil.setBGImage(data, themeDisplay.getScopeGroupId(), fileName);
		}
		}
		
	}
	
	@ProcessAction(name="savePages")
	public void savePages(ActionRequest actionRequest,ActionResponse actionResponse) throws FileNotFoundException, IOException {

		PortletPreferences preferences = null;
		String pages=ParamUtil.getString(actionRequest, "pages", "A4"); 

		String portletResource = ParamUtil.getString(actionRequest, "portletResource");	
		if (Validator.isNotNull(portletResource)) {
			try {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
			} catch (PortalException e) {
				preferences = actionRequest.getPreferences();
			} catch (SystemException e) {
				preferences = actionRequest.getPreferences();
			}
		}
		else{
			preferences = actionRequest.getPreferences();
		}
		
		try {
			preferences.setValue("pages", pages);
			preferences.store();
		} catch (ReadOnlyException e) {
			if(log.isDebugEnabled())e.printStackTrace();
		} catch (ValidatorException e) {
			if(log.isDebugEnabled())e.printStackTrace();
		}
	}
	
	
	
	
	@ProcessAction(name="saveCompetence")
	public void saveCompetence(ActionRequest actionRequest,ActionResponse actionResponse) {

		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Competence.class.getName(), actionRequest);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),0L,ActionKeys.UPDATE))
		{
			Enumeration<String> parNam = actionRequest.getParameterNames();
		
		String title = "";
	    boolean noTitle = true;
		while (parNam.hasMoreElements()) {
			
			String paramName = parNam.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals("")) {
					title = actionRequest.getParameter(paramName);
					noTitle = false;
				}

			}
		}
		if (noTitle) {
				
			SessionErrors.add(actionRequest, "title-required");
			actionResponse.setRenderParameter("jspPage",
					"/html/competencesadmin/editcompetence.jsp");
			return;
		}
		String description = actionRequest.getParameter("description");
		long competenceId = ParamUtil.getLong(actionRequest, "competenceId", 0);
		boolean generateCertificate= ParamUtil.getBoolean(actionRequest, "generateCertificate", false);
		String template= ParamUtil.getString(actionRequest, "template", StringPool.BLANK);
		String css= ParamUtil.getString(actionRequest, "css", StringPool.BLANK);
		String page = ParamUtil.getString(actionRequest, "page", StringPool.BLANK);
		
		Competence competence=null;
		if(competenceId==0)
		{
			serviceContext.setAttribute("page", page);
			 try {
				competence=CompetenceLocalServiceUtil.addCompetence(title, description,generateCertificate, serviceContext);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			
			competence.setTitle(title);
			competence.setDescription(description);
			competence.setGenerateCertificate(generateCertificate);
			competence.setDiplomaTemplate(template);
			competence.setPage(page);
			try {
				CompetenceLocalServiceUtil.updateCompetence(competence, serviceContext);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		boolean oneTitleNotEmpty = false;
		Enumeration<String> parNames = actionRequest.getParameterNames();
			while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				String language = paramName.substring(6);
				Locale locale = LocaleUtil.fromLanguageId(language);
				competence.setTitle(actionRequest.getParameter(paramName),locale);

				if (!actionRequest.getParameter(paramName).equals("")) {
					oneTitleNotEmpty = true;
				}
			}
		}

		if (!oneTitleNotEmpty) {
			SessionErrors.add(actionRequest, "title-empty");
			actionResponse.setRenderParameter("jspPage",
					"/html/competencesadmin/editcompetence.jsp");
			return;
		}
			
		try{
			CompetenceLocalServiceUtil.modCompetence(competence, serviceContext);
		}catch(Exception e){
			if(log.isDebugEnabled())e.printStackTrace();
		}
		if(log.isDebugEnabled())log.debug("End right");
		}
	}
	
	@ProcessAction(name="deleteCompetence")
	public void deleteCompetence(ActionRequest request, ActionResponse response)throws Exception {
		
		long id = ParamUtil.getLong(request, "competenceId");
		if (Validator.isNotNull(id)) {
			if(log.isDebugEnabled())log.debug("deleteCompetence");
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
			DynamicQuery dq = DynamicQueryFactoryUtil.forClass(CourseCompetence.class,classLoader)
					.add(PropertyFactoryUtil.forName("competenceId").eq(id));
			
			List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.dynamicQuery(dq);
			
			if(courseCompetences!=null&&courseCompetences.size()>0){
				SessionErrors.add(request, "competence.courseCompetence-in-use");
				return;
			}

			dq = DynamicQueryFactoryUtil.forClass(UserCompetence.class,classLoader)
					.add(PropertyFactoryUtil.forName("competenceId").eq(id));
			List<UserCompetence> userCompetences = UserCompetenceLocalServiceUtil.dynamicQuery(dq);

			if(userCompetences!=null&&userCompetences.size()>0){
				SessionErrors.add(request, "competence.userCompetence-in-use");
				return;
			}
			
			CompetenceLocalServiceUtil.deleteCompetence(id);
		}
	}
}
