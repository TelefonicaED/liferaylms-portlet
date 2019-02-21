package com.liferay.lms.course.inscriptiontype;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.ServiceContext;

public class BaseInscriptionType implements InscriptionType, Serializable {
	
	public static final long TYPE = 0;
		
	public long getTypeId(){
		return TYPE;
	}
	
	public String getDescription(Locale locale){
		return "";
	}
	
	public String getSpecificContentPage(){
		return "";
	}
	
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse,Course course){
		return null;
	}
	
	public String getPortletId(){
		return null;
	}
	
	@Override
	public String enrollUser(long courseId, long userId, long teamId, ServiceContext serviceContext) throws PortalException, SystemException {
		return CourseLocalServiceUtil.addStudentToCourseByUserId(courseId, userId, teamId, serviceContext);
	}
	
	@Override
	public boolean unsubscribeUser(Course course, long userId) throws PortalException, SystemException{
		return CourseLocalServiceUtil.unsubscribeUser(course, userId);
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "inscription-type.title");
	}

	@Override
	public boolean canUnsubscribe() {
		return true;
	}
	
	@Override
	public Set<Integer> getGroupTypesAvailable(){
		Set<Integer> sites = new HashSet<Integer>();
		String site = PropsUtil.get("lms.site.types");
		if(Validator.isNotNull(site)){
			String[] ssites = site.split(",");
			for(int i=0;i<ssites.length;i++){
				try{
					sites.add(Integer.valueOf(ssites[i]));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if (sites.isEmpty()) {
			sites.add(GroupConstants.TYPE_SITE_OPEN);
			sites.add(GroupConstants.TYPE_SITE_RESTRICTED);
			sites.add(GroupConstants.TYPE_SITE_PRIVATE);
		}
		return sites;
	}
	
	@Override
	public boolean isActive(long companyId){
		return true;
	}
}
