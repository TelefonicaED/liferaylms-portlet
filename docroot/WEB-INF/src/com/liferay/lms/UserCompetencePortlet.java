package com.liferay.lms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.velocity.tools.generic.DateTool;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.UserCertificateDownloadLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.lms.views.CompetenceView;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.util.VelocityUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;


public class UserCompetencePortlet extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(UserCompetencePortlet.class);
 
	private String viewJSP; 
	
	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int page = 0;
		int delta = 10;
		try{
			page = Integer.valueOf(renderRequest.getParameter("act"));
			if(page>0){
				page = page -1;
			}
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		try{

			delta = Integer.valueOf(renderRequest.getParameter("delta"));
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		try{
			if(renderRequest.getParameter("deltaact")!=null){
				delta = Integer.valueOf(renderRequest.getParameter("deltaact"));
			}
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		
		List<UserCompetence> ucs = UserCompetenceLocalServiceUtil.findBuUserId(themeDisplay.getUserId(),page*delta,(page*delta)+delta);
		int totale = UserCompetenceLocalServiceUtil.countByUserId(themeDisplay.getUserId());
		
		List<CompetenceView> competences = new ArrayList<CompetenceView>();
		for(UserCompetence uc : ucs){
			try {
				Course course = CourseLocalServiceUtil.getCourse(uc.getCourseId());
				Competence competence = CompetenceLocalServiceUtil.getCompetence(uc.getCompetenceId());
				if(competence!=null && course != null){
					
					competences.add(new CompetenceView(course, competence, uc));
				}
			} catch (PortalException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}

		renderRequest.setAttribute("competences", competences);
		renderRequest.setAttribute("totale", String.valueOf(totale));
		renderRequest.setAttribute("delta", String.valueOf(delta));
		include(viewJSP, renderRequest, renderResponse);
	}
    private void printCertificate(OutputStream out,String uuid,PortletRequest request) throws SystemException, PortalException, IOException, DocumentException
    {
    	UserCompetence userCompetence=UserCompetenceLocalServiceUtil.findByUuid(uuid);
    	User user=UserLocalServiceUtil.getUser(userCompetence.getUserId());
    	Competence competence = CompetenceLocalServiceUtil.getCompetence(userCompetence.getCompetenceId());
		Course course=CourseLocalServiceUtil.getCourse(userCompetence.getCourseId());
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(user.getTimeZone());
		
		if(user!=null&&competence!=null&&course!=null){
			if(log.isDebugEnabled())log.debug("Enter:"+user.getLocale());
			
			CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), user.getUserId());
			ITextRenderer renderer = new ITextRenderer();
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("dateFormatDate", sdf);
			variables.put("dateTool", new DateTool());
			variables.put("user", user);
			variables.put("competence", competence);
			variables.put("course", course);
			variables.put("uuid", userCompetence.getUuid());
			variables.put("userCompetence", userCompetence);
			if(courseResult!=null)
			{
				variables.put("courseResult", courseResult);
				String extraData=courseResult.getExtraData();
				if(extraData!=null&&!extraData.trim().equals(""))
				{
					try {
						Document eldoc= SAXReaderUtil.read(extraData);
						Element root = eldoc.getRootElement();
						List<Element> elements = root.elements();
						for (Element element : elements) {
							processElement(element, variables);
						}
					} catch (com.liferay.portal.kernel.xml.DocumentException e) {
						e.printStackTrace();
					}
				}
			}
			
			Date endDate = CourseLocalServiceUtil.getLastModuleDateInCourse(course.getCourseId());
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTimeZone(user.getTimeZone());
			endDateCalendar.setTime(endDate);
			SimpleDateFormat longSdf = null;
			
			if(themeDisplay.getLocale().getLanguage().toLowerCase().equals("es")){
				longSdf = new SimpleDateFormat("dd '"+LanguageUtil.get(themeDisplay.getLocale(), "of")+"' MMMM '"+LanguageUtil.get(themeDisplay.getLocale(), "of")+"' yyyy", themeDisplay.getLocale());
			}else if(themeDisplay.getLocale().getLanguage().toLowerCase().equals("en")){
				longSdf = new SimpleDateFormat("dd'"+getDaySuffix(endDateCalendar.get(Calendar.DATE))+"' MMMM yyyy", themeDisplay.getLocale());
			}else if(themeDisplay.getLocale().getLanguage().toLowerCase().equals("pt")){
				longSdf = new SimpleDateFormat("dd '"+LanguageUtil.get(themeDisplay.getLocale(), "of")+"' MMMM '"+LanguageUtil.get(themeDisplay.getLocale(), "of")+"' yyyy", themeDisplay.getLocale());
			}
			
			if(longSdf==null){
				longSdf = new SimpleDateFormat("EEEE',' dd MMMM yyyy", themeDisplay.getLocale());
			}
			longSdf.setTimeZone(user.getTimeZone());
					
					
					
			variables.put("courseName", course.getTitle(user.getLocale()));
			variables.put("competenceName", competence.getTitle(user.getLocale()));
			variables.put("userName", user.getFirstName() + " "+user.getLastName());
			variables.put("startDate", sdf.format(CourseLocalServiceUtil.getFirstModuleDateInCourse(course.getCourseId())));
			variables.put("endDate",sdf.format(endDate));
			variables.put("endLongDate", longSdf.format(endDate));
			variables.put("themeDisplay", themeDisplay);
			
			LmsPrefs lmsprefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			long teacherRoleId=lmsprefs.getTeacherRole();
			List<UserGroupRole> teachersGroups=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(course.getGroupCreatedId(),teacherRoleId);
			StringBuffer teachersNames = new StringBuffer(StringPool.BLANK);
			List<User> teachers = new ArrayList<User>();
			if(teachersGroups!=null){
				if(log.isDebugEnabled())log.debug(teachersGroups.size());
				teachersNames.append("<ul>");
				for(UserGroupRole userGroupRole : teachersGroups){
					User teacher = UserLocalServiceUtil.getUser(userGroupRole.getUserId());
					if(teacher!=null){
						teachersNames.append("<li>");
						teachersNames.append(teacher.getFullName());
						teachers.add(teacher);
						teachersNames.append("</li>");
					}
				}
				teachersNames.append("</ul>");
			}
			StringBuffer modulesNames = new StringBuffer(StringPool.BLANK);
			List<Module> modules= ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
			Map<Long,ModuleResult> moduleResults=new java.util.HashMap<Long,ModuleResult>();
			if(modules!=null){
				modulesNames.append("<ul>");
				if(log.isDebugEnabled())log.debug(modules.size());
				for(Module module : modules){
					modulesNames.append("<li>");
					modulesNames.append(module.getTitle(user.getLocale()));
					modulesNames.append("</li>");
					ModuleResult mResult=ModuleResultLocalServiceUtil.getByModuleAndUser(module.getModuleId(), user.getUserId());
					moduleResults.put(module.getModuleId(), mResult);
					
				} 
				modulesNames.append("</ul>");
			}
			variables.put("modules", modules); 
			variables.put("moduleResults",moduleResults);
			variables.put("modulesNames", modulesNames);
			variables.put("teachers", teachers);
			variables.put("teachersNames", teachersNames);
			
			
			// Sustitucion de campos expando de Course
			List<ExpandoColumn> courseExpandoColumnList = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(themeDisplay.getCompanyId(), Course.class.getName());
			
			for (ExpandoColumn courseExpandoColumn : courseExpandoColumnList) {
				log.debug("ExapandoColumn "+courseExpandoColumn.getName());
				Serializable courseExpandoValue = course.getExpandoBridge().getAttribute(courseExpandoColumn.getName(),false);
				log.debug("ExpandoValue "+courseExpandoValue);
				if (Validator.isNotNull(courseExpandoValue)){ 
					if (courseExpandoColumn.getType() == ExpandoColumnConstants.BOOLEAN) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Boolean) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.DATE) {
						variables.put(courseExpandoColumn.getName(), sdf.format((Date) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.DOUBLE) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Double) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.FLOAT) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Float) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.INTEGER) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Integer) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.SHORT) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Short) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.LONG) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Long) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.NUMBER) {
						variables.put(courseExpandoColumn.getName(), String.valueOf((Number) courseExpandoValue));
					} else if (courseExpandoColumn.getType() == ExpandoColumnConstants.STRING) {
						variables.put(courseExpandoColumn.getName(), (String) courseExpandoValue);
					} else {
						variables.put(courseExpandoColumn.getName(), courseExpandoValue);
					}
				}
			}
			
			String template = StringPool.BLANK;
			
			try {
				template = competence.getDiplomaTemplate(user.getLocale()).replace("<p>&nbsp;</p>","<br/>");
				template = VelocityUtil.evaluate(template.replace("&nbsp;", StringPool.BLANK), variables);
			} catch (Exception e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
			
			String cssurl = GetterUtil.get(PropsUtil.get("com.liferay.lms.diploma.css"), StringPool.BLANK);
			String imageurl = CompetenceLocalServiceUtil.getBGImageURL( competence, PortalUtil.getHttpServletRequest(request));
			StringBuffer html = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
			html.append("@page { size: ");
			html.append(competence.getPage()); 
			if (competence.getDiplomaBackground() <= 0) {
				// No esta en la document library o no tiene asociado fondo
				File file = new File(imageurl.replace("file:", ""));
				if(file.exists()){
					html.append(" ; background: url('");
					html.append(imageurl);
					html.append("') repeat-y top center}");
				}else{
					html.append("}");
				}
			} else {
				html.append(" ; background: url(");
				html.append(HtmlUtil.escape(imageurl));
				html.append(") repeat-y top center}");
			}
			if(!StringPool.BLANK.equals(cssurl)){
				String css = getFileContent(cssurl);
				if(css!=null){
					html.append(css);
				}
			}
			html.append("</style></head><body>");
			html.append(template); 
			html.append("</body></html>");
				
			if(log.isDebugEnabled())log.debug(html);
			
			String listafuentes = GetterUtil.get(PropsUtil.get("com.liferay.lms.diploma.fonts"), StringPool.BLANK);
			if (Validator.isNotNull(listafuentes) && !listafuentes.isEmpty()) {
				String[] fonts = listafuentes.split(",");
				for (int i = 0; i<fonts.length; i++){
					renderer.getFontResolver().addFont(getAbsolutePath(fonts[i]),BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
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
			if (competence.getDiplomaAdditional() > 0) {
				StringBuffer sb = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
				sb.append("@page { size: ");
				sb.append(competence.getPage() + "}");
				sb.append("</style></head><body>");
				if (competence.getDiplomaAdditional() == 1 || competence.getDiplomaAdditional() == 2) {
					sb.append("<h3>" + LanguageUtil.get(themeDisplay.getLocale(), "competence.template.topics") + "</h3>");
					sb.append("<ul>");
					for(Module module : modules) {
						sb.append("<li>");
						sb.append("<h4>" + module.getTitle(themeDisplay.getLocale()) + "</h4>");
						if(competence.getDiplomaAdditional() == 2) {
							List<LearningActivity> activities = new ArrayList<LearningActivity>();
							try {
								activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
							} catch (Exception e) {
								log.error("Se ha producido un error al obtener la lista de actividades del módulo " + module.getTitle(themeDisplay.getLocale()));
							}
							sb.append("<ul>");
							for(LearningActivity learningActivity : activities) {
								sb.append("<li>");
								sb.append(learningActivity.getTitle(themeDisplay.getLocale()));
								sb.append("</li>");
							}
							sb.append("</ul>");
						}
						sb.append("</li>");
						
					}
					sb.append("</ul>");
				} else if (competence.getDiplomaAdditional() == 3) {
					sb.append("<p>");
					try {
						ExpandoColumn column = null;
						//Si existe el expando para la informacion adicional, se añade.
						ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(themeDisplay.getCompanyId(), Course.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME);
						if (table != null) {
							column =  ExpandoColumnLocalServiceUtil.getColumn (table.getTableId(), LmsConstant.ADDITIONAL_INFORMATION_COURSE_EXPANDO);
							if(column!=null){
								if(course.getExpandoBridge().getAttribute(LmsConstant.ADDITIONAL_INFORMATION_COURSE_EXPANDO,false)!=null){
									String summary = String.valueOf(course.getExpandoBridge().getAttribute(LmsConstant.ADDITIONAL_INFORMATION_COURSE_EXPANDO,false));
									if (Validator.isNotNull(summary) && !summary.isEmpty()) {
										summary = summary.replaceAll(StringPool.NEW_LINE, "<br/>");
									}
									sb.append(summary);
								}
							}
						}
					}catch(Exception e){
						log.error("Se ha producido un error al buscar la información adicional del curso " + course.getCourseId());
						e.printStackTrace();
					}
					
					sb.append("</p>");
				}
				 
				sb.append("</body></html>");
					
				log.info(sb.toString());
					
				renderer.setDocumentFromString(sb.toString());
				renderer.layout();
				renderer.writeNextDocument();
			}
				
			renderer.finishPDF();
			
			// Si se ha descargado el diploma => Se deja traza de la descarga 
			// (Solo tiene en cuenta la primera vez que lo descarga)
			if (themeDisplay.getUser().getUserId() == themeDisplay.getRealUserId()) {
				UserCertificateDownloadLocalServiceUtil.addUserCertificateDownload(themeDisplay.getUser().getUserId(), course.getCourseId(), competence.getCompetenceId());
			}
			
		}else{
			if(log.isDebugEnabled())log.debug("Nodata!");

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(StringPool.BLANK);
			renderer.layout(); 
			renderer.finishPDF();
		}
    }
    
    
    private static String getDaySuffix(int day){
	    if (day >= 11 && day <= 13) {
	        return "th";
	    }
	    switch (day % 10) {
	        case 1:  return "st";
	        case 2:  return "nd";
	        case 3:  return "rd";
	        default: return "th";
	    }
			
	}
    
	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException,IOException{
		
		Long competenceId = ParamUtil.getLong(request, "competenceId", 0);
		Long courseId = ParamUtil.getLong(request, "courseId", 0);
		
		String courseName="";
		String uuid=ParamUtil.getString(request, "uuid", "");
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		
		UserCompetence userCompetence=null;
		if("".equals(uuid))
		{
			userCompetence= UserCompetenceLocalServiceUtil.findByUserIdCompetenceIdCourseId(themeDisplay.getUserId(), competenceId, courseId);
		}
		else
		{
			userCompetence= UserCompetenceLocalServiceUtil.findByUuid(uuid);
		}
		
		Course course=null;
		if(userCompetence!=null){
			try {
				course = CourseLocalServiceUtil.fetchCourse(userCompetence.getCourseId());
				if(course!=null){
					courseName = "_"+course.getTitle(themeDisplay.getLocale());
				}
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
		}
		response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + LanguageUtil.get(themeDisplay.getLocale(), "competence.generated-file-name") + courseName +".pdf\"");
		response.setContentType("application/pdf");
		if(themeDisplay.isSignedIn())
		 {
			if(userCompetence!=null)
			{
			 
				try {
					
						OutputStream out = response.getPortletOutputStream();
						printCertificate(out, userCompetence.getUuid(), request);
						out.close();
					
					
				} catch (PortalException e) {
					if(log.isDebugEnabled())e.printStackTrace();
				} catch (SystemException e) {
					if(log.isDebugEnabled())e.printStackTrace();
				} catch (DocumentException e) {
					if(log.isDebugEnabled())e.printStackTrace();
				}
			}
		}
	}
 

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
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
	protected String getAbsolutePath(String filepath) {
		
		return System.getProperty("catalina.base")+filepath;
	}
	protected void processElement(Element element, Map<String, Object> variables){
		if(element.hasContent()){
			variables.put(element.getName(), element.getData());
		}
		List<Attribute> attributes = element.attributes();
		for (Attribute attribute : attributes) {
			variables.put(attribute.getName(), attribute.getValue());
		}
		List<Element> elements = element.elements();
		for (Element ele : elements) {
			processElement(ele, variables);
		}
	}
}
