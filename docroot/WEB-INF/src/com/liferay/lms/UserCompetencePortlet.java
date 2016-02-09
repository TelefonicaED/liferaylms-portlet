package com.liferay.lms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.views.CompetenceView;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
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
				Competence competence = CompetenceLocalServiceUtil.getCompetence(uc.getCompetenceId());
				if(competence!=null){
					
					competences.add(new CompetenceView(competence, uc));
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
		DateFormat dateFormatDate = DateFormat.getDateInstance(DateFormat.SHORT,user.getLocale());
		dateFormatDate.setTimeZone(user.getTimeZone());
		 if (dateFormatDate instanceof SimpleDateFormat)
	      {
	            SimpleDateFormat sdf = (SimpleDateFormat) dateFormatDate;
	            // To show Locale specific short date expression with full year
	            String pattern = sdf.toPattern().replaceAll("y+","yyyy");
	            sdf.applyPattern(pattern); 
	            dateFormatDate=sdf;
	      }
		if(user!=null&&competence!=null&&course!=null){
			if(log.isDebugEnabled())log.debug("Enter:"+user.getLocale());
			
			CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), user.getUserId());

			ITextRenderer renderer = new ITextRenderer();
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("dateFormatDate", dateFormatDate);
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
			variables.put("courseName", course.getTitle(user.getLocale()));
			variables.put("competenceName", competence.getTitle(user.getLocale()));
			variables.put("userName", user.getFullName());
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
			
			String template = StringPool.BLANK;
			
			try {
				template = VelocityUtil.evaluate(competence.getDiplomaTemplate(user.getLocale()).replaceAll("&nbsp;", StringPool.BLANK), variables);
			} catch (Exception e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
			
			String cssurl = GetterUtil.get(PropsUtil.get("com.ted.siele.diploma.css"), StringPool.BLANK);
			String imageurl =CompetenceLocalServiceUtil.getBGImageURL( competence, PortalUtil.getHttpServletRequest(request));
									
			StringBuffer html = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
			html.append("@page { size: ");
			html.append(competence.getPage()); 
			html.append(" ; background: url('");
			html.append(imageurl);
			html.append("') repeat-y top center}");
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
			
			String listafuentes = GetterUtil.get(PropsUtil.get("com.ted.siele.diploma.fonts"), StringPool.BLANK);
			String[] fonts = listafuentes.split(",");
			for (int i = 0; i<fonts.length; i++){
				renderer.getFontResolver().addFont(getAbsolutePath(fonts[i]),BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.setDocumentFromString(html.toString());

			renderer.layout(); 
			
			try {
				renderer.createPDF(out, false);
			} catch (DocumentException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
			
			
			renderer.layout(); 
			
			renderer.finishPDF();
			
			
		}else{
			if(log.isDebugEnabled())log.debug("Nodata!");

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocumentFromString(StringPool.BLANK);

			renderer.layout(); 
			
			renderer.finishPDF();
			
		}
    	
    }
	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException,IOException{
		
		Long competenceId = ParamUtil.getLong(request, "competenceId", 0);
		String uuid=ParamUtil.getString(request, "uuid", "");
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		UserCompetence userCompetence=null;
		if("".equals(uuid))
		{
			userCompetence= UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(themeDisplay.getUserId(), competenceId);
		}
		else
		{
			userCompetence= UserCompetenceLocalServiceUtil.findByUuid(uuid);
		}
		
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
