package com.liferay.lms;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * Portlet implementation class CourseDetailPortlet
 */
public class CourseDetailPortlet extends BaseCourseAdminPortlet {

	private static final Log log = LogFactoryUtil.getLog(CourseDetailPortlet.class);
	
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads"; 
	
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "course";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Course Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = StringPool.BLANK;	
	
	public void init() throws PortletException {
//		viewJSP = getInitParameter("view-template");
		editCourseJSP =  getInitParameter("edit-course-template");
		roleMembersTabJSP =  getInitParameter("role-members-tab-template");
		competenceTabJSP =  getInitParameter("competence-tab-template");
		usersResultsJSP = getInitParameter("users-results-template");
		competenceResultsJSP = getInitParameter("competence-results-template");
		importUsersJSP = getInitParameter("import-users-template");
	}

//	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		String jsp = renderRequest.getParameter("view");
		if(log.isDebugEnabled())log.debug("VIEW "+jsp);
		try {
			if(jsp == null || "".equals(jsp)){
				showViewEditCourse(renderRequest, renderResponse);
			}else if("edit-course".equals(jsp)){
				showViewEditCourse(renderRequest, renderResponse);
			}else if("role-members-tab".equals(jsp)){
				showViewRoleMembersTab(renderRequest, renderResponse);
			}else if("competence-tab".equals(jsp)){
				showViewCompetenceTab(renderRequest, renderResponse);
			}else if("import-users".equals(jsp)){
				showViewImportUsers(renderRequest, renderResponse);
			}else if("users-results".equals(jsp)){
				showViewUsersResults(renderRequest, renderResponse);
			}else if("competence-results".equals(jsp)){
				showViewCompetenceResults(renderRequest, renderResponse);
			}
		} catch (IOException e) {
			log.error("Se ha producido un error al obtener la vista por defecto", e);
			showViewEditCourse(renderRequest, renderResponse);
		} catch (PortletException e) {
			log.error("Se ha producido un error al obtener la vista por defecto", e);
			showViewEditCourse(renderRequest, renderResponse);
		}
		
	}
	
	public void showViewEditCourse(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		
		Course course = null;
		try {
			course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
		} catch (SystemException e) {
			log.error("No se ha encontrado ningún curso con el groupCreatedId=" + groupId, e);
		}
		
		if (Validator.isNotNull(course)) {
			renderRequest.setAttribute("course", course);
		}
		
		include(this.editCourseJSP, renderRequest, renderResponse);
		
	}

}
