package com.liferay.lms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ProcessAction;
import javax.portlet.ReadOnlyException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.tools.generic.DateTool;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.util.VelocityUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;


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
		long background = ParamUtil.getLong(actionRequest, "diplomaBackground", 0);
		long additional = ParamUtil.getLong(actionRequest, "diplomaAdditional", 0);
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
			competence.setDiplomaBackground(background);
			competence.setDiplomaAdditional(additional);
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
	
	@ProcessAction(name="saveBackground")
	public void saveBackground(ActionRequest actionRequest,ActionResponse actionResponse) throws FileNotFoundException, IOException 
	{

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long companyId = themeDisplay.getCompanyId();
		
		Role siteMemberRole = null;
		try {
			siteMemberRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.SITE_MEMBER);
		} catch (PortalException e) {
			log.error("Se ha producido un error al obtener el rol " + RoleConstants.SITE_MEMBER, e);
		} catch (SystemException e) {
			log.error("Se ha producido un error al obtener el rol " + RoleConstants.SITE_MEMBER, e);
		}
		Role guestRole = null;
		try {
			guestRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST);
		} catch (PortalException e) {
			log.error("Se ha producido un error al obtener el rol " + RoleConstants.GUEST, e);
		} catch (SystemException e) {
			log.error("Se ha producido un error al obtener el rol " + RoleConstants.GUEST, e);
		}
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		long folderId = ParamUtil.getLong(uploadRequest, "folderId", 0L);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), uploadRequest);
		} catch (PortalException e) {
			log.error("Se ha producido un error al obtener el service Context", e);
		} catch (SystemException e) {
			log.error("Se ha producido un error al obtener el service Context", e);
		}
		PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
		if (folderId > 0 && Validator.isNotNull(serviceContext)) {
			if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  DLFolder.class.getName(), 0L, ActionKeys.ADD_DOCUMENT))
			{
				String fileName = uploadRequest.getFileName("fileName");
				if(fileName!=null && fileName.length()>0) {
					File file = uploadRequest.getFile("fileName");
					String contentType = uploadRequest.getContentType("fileName");
					
					try {
						FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, folderId, fileName, contentType, fileName, StringPool.BLANK, StringPool.BLANK, file, serviceContext);

						String [] actionIds = { ActionKeys.VIEW };
						if (Validator.isNotNull(siteMemberRole)) {
							ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, DLFileEntryConstants.getClassName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(fileEntry.getFileEntryId()), siteMemberRole.getRoleId(), actionIds);
						}
						if (Validator.isNotNull(guestRole)) {
							ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, DLFileEntryConstants.getClassName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(fileEntry.getFileEntryId()), guestRole.getRoleId(), actionIds);
						}
						
						if (Validator.isNotNull(fileEntry)) {
							SessionMessages.add(actionRequest, "add-diploma-background-success");
						}
					} catch (DuplicateFileException e) {
						log.error("Se ha producido un error al crear el fichero a subir, porque ya existe otro con el mismo nombre");

						SessionErrors.add(actionRequest, "duplicate-diploma-background-error");
					} catch (PortalException e) {
						log.error("Se ha producido un error al crear el fichero a subir", e);

						SessionErrors.add(actionRequest, "add-diploma-background-error");
					} catch (SystemException e) {
						log.error("Se ha producido un error al crear el fichero a subir", e);

						SessionErrors.add(actionRequest, "add-diploma-background-error");
					}
				}
			}
		} else if (Validator.isNull(serviceContext)) {
			
			SessionErrors.add(actionRequest, "add-diploma-background-error");
			
		}
		
		actionResponse.setRenderParameter("jspPage", "/html/competencesadmin/editbackground.jsp");
		
	}
	
	@ProcessAction(name="deleteBackground")
	public void deleteBackground(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		long fileEntryId = ParamUtil.get(actionRequest, "fileEntryId", 0);
		if (fileEntryId > 0) {
			
			try {
				DLAppServiceUtil.deleteFileEntry(fileEntryId);
				
				SessionMessages.add(actionRequest, "delete-diploma-background-success");
				
				List<Competence> competenceList = CompetenceLocalServiceUtil.getCompetences(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				for(Competence competence : competenceList) {
					if (competence.getDiplomaBackground() == fileEntryId) {
						competence.setDiplomaBackground(0L);
						
						CompetenceLocalServiceUtil.updateCompetence(competence);
					}
				}
			} catch (PortalException e) {
				log.error("No se ha podido eliminar el fichero fileEntryId=" + fileEntryId, e);
				
				SessionErrors.add(actionRequest, "delete-diploma-background-error");
			} catch (SystemException e) {
				log.error("No se ha podido eliminar el fichero fileEntryId=" + fileEntryId, e);
				
				SessionErrors.add(actionRequest, "delete-diploma-background-error");
			}
			
		}
		
		actionResponse.setRenderParameter("jspPage", "/html/competencesadmin/editbackground.jsp");
		
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		
		String competenceNameDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.competenceName");
		String templateContentDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.content");
		
		String title = ParamUtil.getString(resourceRequest, "title", competenceNameDefault);
		if (Validator.isNull(title) || title.isEmpty()) {
			title = competenceNameDefault;
		}
		String page = ParamUtil.getString(resourceRequest, "page", "A4");
		String template = ParamUtil.getString(resourceRequest, "template", templateContentDefault);
		long background = ParamUtil.getLong(resourceRequest, "background", 0);
		long additional = ParamUtil.getLong(resourceRequest, "additional", 0);
		
		log.debug(title);
		log.debug(page);
		log.debug(template);
		log.debug(background);
		log.debug(groupId);
		
		resourceResponse.setContentType("application/pdf");
		if(themeDisplay.isSignedIn()) {
			try {
				OutputStream out = resourceResponse.getPortletOutputStream();
				printCertificate(out, title, page, template, background, additional, resourceRequest);
				out.close();
			} catch (PortalException e) {
				if (log.isDebugEnabled())
					e.printStackTrace();
			} catch (SystemException e) {
				if (log.isDebugEnabled())
					e.printStackTrace();
			} catch (DocumentException e) {
				if (log.isDebugEnabled())
					e.printStackTrace();
			}
		}
		
	}
	
    private void printCertificate(OutputStream out,String title, String page, String template, long background, long additional, PortletRequest request) throws SystemException, PortalException, IOException, DocumentException {
    	
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		User user = themeDisplay.getUser();
		
		String uuidDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.uuid");
		String courseNameDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.courseName");
		String userNameDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.userName");
		String modulesNamesDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.modulesNames");
		String activitiesNamesDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.activitiesNames");
		String teachersNamesDefault = LanguageUtil.get(themeDisplay.getLocale(), "competence.template.teachersNames");
		
		DateFormat dateFormatDate = DateFormat.getDateInstance(DateFormat.SHORT,user.getLocale());
		dateFormatDate.setTimeZone(user.getTimeZone());
		if (dateFormatDate instanceof SimpleDateFormat) {
			SimpleDateFormat sdf = (SimpleDateFormat) dateFormatDate;
			// To show Locale specific short date expression with full year
			String pattern = sdf.toPattern().replaceAll("y+","yyyy");
			sdf.applyPattern(pattern);
			dateFormatDate=sdf;
		}
		
		if(log.isDebugEnabled())log.debug("Enter:"+user.getLocale());
			
		ITextRenderer renderer = new ITextRenderer();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("dateFormatDate", dateFormatDate);
		variables.put("dateTool", new DateTool());
		variables.put("user", user);
		variables.put("uuid", uuidDefault);
		variables.put("courseName", courseNameDefault);
		variables.put("competenceName", title);
		variables.put("userName", userNameDefault);
		variables.put("themeDisplay", themeDisplay);
			
		StringBuffer teachersNames = new StringBuffer(StringPool.BLANK);
		teachersNames.append("<ul><li>" + teachersNamesDefault.replace("{0}", "1") + "</li></ul>");
		variables.put("teachersNames", teachersNames);
			
		StringBuffer modulesNames = new StringBuffer(StringPool.BLANK);
		modulesNames.append("<ul>");
		int modulesMax = 5;
		for(int i = 0; i < modulesMax; i++) {
			modulesNames.append("<li>" + modulesNamesDefault.replace("{0}", String.valueOf(i)) + "</li>");
		}
		modulesNames.append("</ul>");
		variables.put("modulesNames", modulesNames);
		
		// Sustitucion de campos expando de Course
		List<ExpandoColumn> courseExpandoColumnList = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(themeDisplay.getCompanyId(), Course.class.getName());
		for (ExpandoColumn courseExpandoColumn : courseExpandoColumnList) {
			variables.put(courseExpandoColumn.getName(), "[" + courseExpandoColumn.getDisplayName(themeDisplay.getLocale()) + "]");
		}
			
		String templateStr = StringPool.BLANK;
			
		try {
			templateStr =template.replace("<p>&nbsp;</p>","<br/>");
			templateStr = VelocityUtil.evaluate(templateStr.replace("&nbsp;", StringPool.BLANK), variables);
			
			log.debug("TEMPLATE "+templateStr);
		} catch (Exception e) {
			if(log.isDebugEnabled())e.printStackTrace();
		} 
			
		String cssurl = GetterUtil.get(PropsUtil.get("com.liferay.lms.diploma.css"), StringPool.BLANK);
		StringBuffer html = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
		html.append("@page { size: ");
		html.append(page); 
		if (background > 0) {
				
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(background);
				String imageurl = PropsUtil.get("dl.store.file.system.root.dir")
						+ "/" + fileEntry.getCompanyId() + "/"
					    + fileEntry.getFolderId() + "/"
					    + ((DLFileEntry) fileEntry.getModel()).getName() + "/"
					    + fileEntry.getVersion();
				
				log.info("URL: " + imageurl);
				
				html.append(" ; background: url(");
				html.append(HtmlUtil.escape(imageurl));
				html.append(") repeat-y top center}");
			} catch (Exception e) {
				html.append("}");
			}
			
		} else {
			// Si no se ha seleccionado fondo => Se coge el que tuviera asignado
			String imageurl = CompetenceLocalServiceUtil.getBGImageURL(groupId, PortalUtil.getHttpServletRequest(request));
			File file = new File(imageurl.replace("file:", ""));
			if(file.exists()){
				html.append(" ; background: url('");
				html.append(imageurl);
				html.append("') repeat-y top center}");
			}else{
				html.append("}");
			}
		}
		if(!StringPool.BLANK.equals(cssurl)){
			String css = getFileContent(cssurl);
			if(css!=null){
				html.append(css);
			}
		}
		html.append("</style></head><body>");
		html.append(templateStr); 
		html.append("</body></html>");
				
		if(log.isDebugEnabled())log.debug(html);
			
		String listafuentes = GetterUtil.get(PropsUtil.get("com.liferay.lms.diploma.fonts"), StringPool.BLANK);
		if (Validator.isNotNull(listafuentes) && !listafuentes.isEmpty()) {
			String[] fonts = listafuentes.split(",");
			for (int i = 0; i<fonts.length; i++){
				renderer.getFontResolver().addFont(System.getProperty("catalina.base") + fonts[i],BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
		}
		renderer.setDocumentFromString(html.toString());
		renderer.layout();
		try {
			renderer.createPDF(out, false);
		} catch (DocumentException e) {
			if(log.isDebugEnabled())e.printStackTrace();
		}
			
		renderer.layout();
		
		// Si se incluye otra pagina mas informacion adicional en el diploma
		if (additional > 0) {
			StringBuffer sb = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
			sb.append("@page { size: ");
			sb.append(page + "}");
			sb.append("</style></head><body>");
			if (additional == 1 || additional == 2) {
				sb.append("<h3>" + LanguageUtil.get(themeDisplay.getLocale(), "competence.template.topics") + "</h3>");
				sb.append("<ul>");
				for(int i = 0; i < modulesMax; i++) {
					sb.append("<li>");
					sb.append("<h4>" + modulesNamesDefault.replace("{0}", String.valueOf(i)) + "</h4>");
					if(additional == 2) {
						int max = i * 2 + 1;
						sb.append("<ul>");
						for(int j = 0; j < max; j++) {
							sb.append("<li>");
							sb.append(activitiesNamesDefault.replace("{0}", String.valueOf(j)));
							sb.append("</li>");
						}
						sb.append("</ul>");
					}
					sb.append("</li>");
					
				}
				sb.append("</ul>");
			} else if (additional == 3) {
//				sb.append("<h3>" + LanguageUtil.get(themeDisplay.getLocale(), "competence.template.topics") + "</h3>");
				sb.append("<p>");
				sb.append(LanguageUtil.get(themeDisplay.getLocale(), "competence.template.courseSummary"));
				sb.append("</p>");
			}
			 
			sb.append("</body></html>");
				
			log.info(sb.toString());
				
			renderer.setDocumentFromString(sb.toString());
			renderer.layout();
			renderer.writeNextDocument();
		}
			
		renderer.finishPDF();

    }
    
	protected String getFileContent(String filepath) {
		
		String absolutePath = System.getProperty("catalina.base")+filepath;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(absolutePath));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				sb.append(readData);
				buf = new char[1024];
			}
			reader.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			if(log.isDebugEnabled()){
				log.debug(e);
			}
			return null;
		} catch (IOException e) {
			if(log.isDebugEnabled()){
				log.debug(e);
			}
			return null;
		}
		
	}
	
}
