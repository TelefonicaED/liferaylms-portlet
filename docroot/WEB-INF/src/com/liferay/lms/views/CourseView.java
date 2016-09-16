package com.liferay.lms.views;

import com.liferay.lms.CourseHistory;
import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

public class CourseView {
	
	private static Log log = LogFactoryUtil.getLog(CourseView.class); 

	private long courseId;
	private String title;
	private String logoURL = null;
	private String url = null;
	private boolean closed;
	
	public CourseView(Course course, ThemeDisplay themeDisplay){
		setCourseId(course.getCourseId());
		setTitle(course.getTitle(themeDisplay.getLocale()));
		try {
			if(Validator.isNotNull(course.getIcon())){
				log.debug("Tiene icono el curso");
				long logoId = course.getIcon();
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(logoId);
				log.debug("url del logo: " + DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK));
				setLogoURL(DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK) );
			}else{
				log.debug("Comprobamos si lo tiene el group");
				Group groupCourse= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
				if(groupCourse.getPublicLayoutSet().getLogo()){
					log.debug("Lo tiene el group");
					setLogoURL("/image/layout_set_logo?img_id=" + groupCourse.getPublicLayoutSet().getLogoId());
				}	
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setUrl(themeDisplay.getPortalURL()+"/"+themeDisplay.getLocale().getLanguage()+"/web" + course.getFriendlyURL());

		setClosed(course.isClosed());
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
}
