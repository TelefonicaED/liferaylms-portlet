package com.liferay.lms.course.inscriptiontype;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;

public class UserDaysInscriptionType extends BaseInscriptionType{
	
	public static final long TYPE = 1;
	
	private static Log log = LogFactoryUtil.getLog(UserDaysInscriptionType.class);
	
	public long getTypeId(){
		return TYPE;
	}
	
	@Override
	public String getSpecificContentPage() {
		return "/html/courseadmin/inscriptiontype/baseinscription.jsp";
	}

	@Override
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse, Course course) {
		log.debug("*******setExtraContent**********");
		try {
			String value=ParamUtil.getString(uploadRequest, "inscriptionDays", "");
			
			if(Validator.isNotNull(value)){
				log.debug("****inscriptionDays:"+value);
				
				PortletPreferences prefs= PortalPreferencesLocalServiceUtil.getPreferences(course.getCompanyId(), course.getGroupCreatedId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	
				if(!prefs.isReadOnly("inscription-days")){
					prefs.setValue("inscription-days", value);
					prefs.store();
				}
			}else{
				return "inscription-days-required";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return null;
	}
	
	@Override
	public String enrollUser(long courseId, long userId, long teamId, ServiceContext serviceContext) throws PortalException, SystemException {
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		int days = PrefsPropsUtil.getInteger(course.getGroupCreatedId(), "inscription-days");
		Date allowStartDate = new Date();
		Calendar allowDate = Calendar.getInstance();
		allowDate.setTime(allowStartDate);
		allowDate.add(Calendar.DAY_OF_YEAR, days);
		Date allowEndDate = allowDate.getTime();
		
		CourseLocalServiceUtil.addStudentToCourseWithDates(courseId, userId, allowStartDate, allowEndDate);
		
		return "ok";
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "inscription-type.user-days.title");
	}
	
	@Override
	public boolean canUnsubscribe() {
		return false;
	}
	
	@Override
	public Set<Integer> getGroupTypesAvailable(){
		Set<Integer> sites = new HashSet<Integer>();
		sites.add(GroupConstants.TYPE_SITE_OPEN);
		return sites;
	}
	
	@Override
	public boolean isActive(long companyId){
		return true;
	}

	@Override
	public String getAllowedTime(long courseId, long userId, Locale locale) {
	    log.debug("*******getAllowedTime**********");
		String message = StringPool.BLANK;
		try {
			CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, userId);
			Course course = CourseLocalServiceUtil.fetchCourse(courseId);			
			boolean isSubscribed = GroupLocalServiceUtil.hasUserGroup(userId, course.getGroupCreatedId());		
			log.debug("isSubscribed: " + isSubscribed);
			if (isSubscribed) {
			    if (Validator.isNotNull(courseResult)) {
			        log.debug("passed: " + courseResult.isPassed());
			        log.debug("allowFinishDate: " + courseResult.getAllowFinishDate());
			        if (Validator.isNull(courseResult.getPassedDate()) && Validator.isNotNull(courseResult.getAllowFinishDate())) {
			            log.debug("started");
			            Date allowFinishDate = courseResult.getAllowFinishDate();
                        Date nowDate = new Date();                        
                        long time = allowFinishDate.getTime() - nowDate.getTime();  
                        log.debug("time: " + time);
                        message = getAllowedTimeMessage(time, locale);
			        }
			    }
			} else {				
				// Aun no esta inscrito en el curso
				if (Validator.isNotNull(course)) {
					int days = PrefsPropsUtil.getInteger(course.getGroupCreatedId(), "inscription-days");
					message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-info", days);
				}
			}		
		} catch (SystemException e) {
			log.error(e);
		}
		
		return message;
	}

    /**
     * Obtiene el texto para mostrar el tiempo restante del usuario
     * 
     * @param time tiempo restante en milisegundos
     * @param locale locale del mensaje a mostrar
     * @return string con el mensaje
     */
    private String getAllowedTimeMessage(long time, Locale locale)
    {
        String message = StringPool.BLANK;
        if (time <= 0) {
            return LanguageUtil.get(locale, "inscriptioncommunity.allowed-time-finish");
        }        
        
        long daysCount = time / (24 * 60 * 60 * 1000);
        long hoursCount = time / (60 * 60 * 1000) - daysCount * 24;
        long minutesCount = time / (60 * 1000) - daysCount * 24 *60 - hoursCount * 60;
        
        if (daysCount > 0 && hoursCount > 12) {
            daysCount++;
        }
        
        log.debug("daysCount: " + daysCount);
        log.debug("hoursCount: " + hoursCount);
        log.debug("minutesCount: " + minutesCount);
        
        if (daysCount == 0) {
            message = getHourMessage( hoursCount,minutesCount,locale);
        } else if (daysCount > 1) {
            message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-days", daysCount);
        } else { // queda 1 dia
            message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-day", daysCount);
        }
         
        return message;
    }

    /**
     * Obtiene el texto para mostrar el tiempo restante del usuario cuando le queda menos de un dia
     * 
     * @param hoursCount horas restantes
     * @param minutesCount minutos restantes
     * @param locale locale del mensaje a mostrar
     * @return string con el mensaje
     */
    private String getHourMessage(long hoursCount, long minutesCount, Locale locale)
    {
        String message = StringPool.BLANK;
        if (hoursCount > 0) {
            message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-hours", hoursCount);
        } else if (minutesCount > 1) {
            message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-minutes", minutesCount);
        } else {
            message = LanguageUtil.format(locale, "inscriptioncommunity.allowed-time-minute", minutesCount);
        }

        return message;
    }
}