package com.liferay.lms;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.Format;
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

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.liferay.lms.inscriptionadmin.InscriptionAdminPortlet;
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
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
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
    private void printCertificate(OutputStream out,String uuid,PortletRequest request) throws SystemException, PortalException, IOException
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
						variables.put("extraData", eldoc);
					} catch (com.liferay.portal.kernel.xml.DocumentException e) {
						// TODO Auto-generated catch block
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
			
			String css = GetterUtil.get(competence.getCss(), StringPool.BLANK);
			
			String imageurl =CompetenceLocalServiceUtil.getBGImageURL( competence, PortalUtil.getHttpServletRequest(request));
									
			StringBuffer html = new StringBuffer("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><style type=\"text/css\">");
			html.append("@page { size: ");
			html.append(competence.getPage()); 
			html.append(" ; background: url('");
			html.append(imageurl);
			html.append("') repeat-y top center}");
			if(!StringPool.BLANK.equals(css)){
				html.append(css);
			}
			html.append("</style></head><body>");
			html.append("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /><style type='text/css'>@font-face{font-family:'Lato Light';font-style:normal;font-weight:300;src:url(/sieleportalpublico-theme/fonts/Lato-Light.ttf);src:local('Lato Light'),url(/sieleportalpublico-theme/fonts/Lato-Light.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-Light.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-Light.ttf#webfont) format('svg')}@font-face{font-family:'Lato Bold';font-style:normal;font-weight:400;src:url(/sieleportalpublico-theme/fonts/Lato-Bold.ttf);src:local('Lato Bold'),url(/sieleportalpublico-theme/fonts/Lato-Bold.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-Bold.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-Bold.ttf#webfont) format('svg')}@font-face{font-family:Lato;font-style:normal;src:url(/sieleportalpublico-theme/fonts/Lato-Regular.ttf);src:local('Lato-Regular'),url(/sieleportalpublico-theme/fonts/Lato-Regular.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-Regular.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-Regular.ttf#webfont) format('svg')}@font-face{font-family:'Lato LightItalic';font-style:italic;font-weight:300;src:url(/sieleportalpublico-theme/fonts/Lato-LightItalic.ttf);src:local('Lato LightItalic'),url(/sieleportalpublico-theme/fonts/Lato-LightItalic.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-LightItalic.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-LightItalic.ttf#webfont) format('svg')}@font-face{font-family:'Lato Italic';font-style:italic;font-weight:400;src:url(/sieleportalpublico-theme/fonts/Lato-Italic.ttf);src:local('Lato Italic'),url(/sieleportalpublico-theme/fonts/Lato-Italic.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-Italic.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-Italic.ttf#webfont) format('svg')}@font-face{font-family:Lato-BoldItalic;font-style:italic;font-weight:700;src:url(/sieleportalpublico-theme/fonts/Lato-BoldItalic.ttf);src:local('Lato BoldItalic'),url(/sieleportalpublico-theme/fonts/Lato-BoldItalic.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-BoldItalic.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-BoldItalic.ttf#webfont) format('svg')}@font-face{font-family:'Lato Black';src:url(/sieleportalpublico-theme/fonts/Lato-Black.ttf);src:local('?'),local('Lato Black'),url(/sieleportalpublico-theme/fonts/Lato-Black.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-Black.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-Black.ttf#webfont) format('svg')}@font-face{font-family:'Lato Black';font-style:italic;font-weight:700;src:url(/sieleportalpublico-theme/fonts/Lato-BlackItalic.ttf);src:local('Lato BlackItalic'),local('Lato-BlackItalic'),url(/sieleportalpublico-theme/fonts/Lato-BlackItalic.ttf) format('woff'),url(/sieleportalpublico-theme/fonts/Lato-BlackItalic.ttf) format('opentype'),url(/sieleportalpublico-theme/fonts/Lato-BlackItalic.ttf#webfont) format('svg')}body{background:#fff;font:400 14px Lato,sans-serif;background-size:cover;color:#333;margin:0 auto}body .wrapper{padding:25px 25px 25px 50px}body .wrapper .logos,body .wrapper .logos img{width:100%}body .wrapper .content{padding-top:25px}body .wrapper .content .content_center{text-align:center;margin-bottom:45px}body .wrapper .content .content_center h1{font-weight:400}body .wrapper .content .content_center span{font-size:18px}body .wrapper .content .content_center.supFirmas{margin-bottom:0;margin-top:100px}body .wrapper .content .content_parrafo .fecha{font-size:16px}body .wrapper .content .bloques,body .wrapper .content .bloques_2{width:90%;display:inline-block;margin:40px auto}body .wrapper .content .bloques .bloq40,body .wrapper .content .bloques_2 .bloq40{float:left;width:35%;margin:0 15px}body .wrapper .content .bloques .bloq60,body .wrapper .content .bloques_2 .bloq60{float:left;width:56%;padding-left:35px;border-left:2px solid #dbd9dd}body .wrapper .content .bloques .bloq60 .totales,body .wrapper .content .bloques_2 .bloq60 .totales{font-size:18px}body .wrapper .content .bloques .bloq60 .gridValores,body .wrapper .content .bloques_2 .bloq60 .gridValores{padding:40px 0}body .wrapper .content .bloques .bloq60 .gridValores .boxesPuntuacion,body .wrapper .content .bloques_2 .bloq60 .gridValores .boxesPuntuacion{width:25%;float:left}body .wrapper .content .bloques .title,body .wrapper .content .bloques_2 .title{background-color:#dbd9dd;padding:5px 10px;display:block;width:80%}body .wrapper .content .bloques ul,body .wrapper .content .bloques_2 ul{padding:0}body .wrapper .content .bloques ul li,body .wrapper .content .bloques_2 ul li{list-style:none;line-height:25px}body .wrapper .content .bloques .puntosText,body .wrapper .content .bloques .valueText,body .wrapper .content .bloques ul li .valueText,body .wrapper .content .bloques_2 .puntosText,body .wrapper .content .bloques_2 .valueText,body .wrapper .content .bloques_2 ul li .valueText{font-weight:700}body .wrapper .content .bloques_2{border-bottom:2px solid #dbd9dd;margin:0;padding:40px 0}body .wrapper .content .bloques_2 .bloq60{border-left:none;color:#3967aa}body .wrapper .content .bloques_2:last-child{border-bottom:none}body .wrapper .content .bloques_2:nth-child(1) .icon{color:#a78732;border:2px solid #a78732}body .wrapper .content .bloques_2:nth-child(2) .icon{color:#27976c;border:2px solid #27976c}body .wrapper .content .bloques_2:nth-child(3) .icon{color:#1e62a2;border:2px solid #1e62a2}body .wrapper .content .bloques_2:nth-child(4) .icon{color:#bf6c22;border:2px solid #bf6c22}body .wrapper .content .bloques_2 .icon{color:#a78732;border:2px solid #f47523;border-radius:50%;width:35px;height:28px;margin:0 9px 15px auto;padding-top:7px;font-family:Arial Rounded Mt,Lato;font-size:17px;float:left;text-align:center}body .wrapper .content .bloques_2 .title{background-color:transparent;font-size:18px;height:30px;width:100%}body .wrapper .content .gridFirmas .bloques{width:50%;float:left;height:100px;text-align:center}body .wrapper .content .gridFirmas .bloques .datosEntidad,body .wrapper .content .gridFirmas .bloques .espacioFirmas{background-color:#dbd9dd;width:50%;margin:0 auto;height:75px;text-align:center}body .wrapper .content .gridFirmas .bloques .datosEntidad span,body .wrapper .content .gridFirmas .bloques .espacioFirmas span{margin-top:20px;display:inline-block}body .wrapper .content .gridFirmas .bloques .fdo{margin:5px 112px;text-align:left}body .wrapper .content .valueText{color:#3967aa;font-weight:700}body .wrapper .content .datosEntidad{text-align:right;font-size:12px;line-height:5px}body .wrapper .content footer{margin:35px 0}body .wrapper .paginacion{text-align:center;margin-top:-55px;font-size:12px}</style><title>Diploma</title></head>");
			html.append(template); 
			html.append("</body></html>");
				
			if(log.isDebugEnabled())log.debug(html);

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
}
