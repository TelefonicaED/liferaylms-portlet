package com.tls.lms.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class LiferaylmsUtil {
	
	private static Log log = LogFactoryUtil.getLog(LiferaylmsUtil.class); 
	
	public static final String DEREGISTER_USER_EXPANDO = "deregister-mail";
	public static final int defaultStartYear = Calendar.getInstance().get(Calendar.YEAR) - 10; //porque es necesario mantener los valores antiguos para los cursos antiguos
	public static final int defaultEndYear = Calendar.getInstance().get(Calendar.YEAR) + 10;
    public static final String CHARSET_UTF_8        = "UTF-8".intern();
    public static final String CHARSET_X_ISO_10646_UCS_4_3412 = "X-ISO-10646-UCS-4-3412".intern(); // Malformed UTF-32
    public static final String CHARSET_UTF_16BE     = "UTF-16BE".intern();
    public static final String CHARSET_UTF_16LE     = "UTF-16LE".intern();
    public static final String CHARSET_UTF_32BE     = "UTF-32BE".intern();
    public static final String CHARSET_UTF_32LE     = "UTF-32LE".intern();
    public static final String CHARSET_X_ISO_10646_UCS_4_2143 = "X-ISO-10646-UCS-4-2143".intern();

	public static void setPermission(ThemeDisplay themeDisplay, String classname, Role role, String[] actionIds, long primaryKey) throws Exception{
		Resource resource =  ResourceLocalServiceUtil.getResource(themeDisplay.getCompanyId(), classname, ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(primaryKey));
		if(ResourceBlockLocalServiceUtil.isSupported(classname)){
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourceBlockServiceUtil.setIndividualScopePermissions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), resource.getName(),
					primaryKey, roleIdsToActionIds);
		}else{
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(), resource.getName(),
					Long.toString(primaryKey), roleIdsToActionIds);	
		}
	}

	public static List<LearningActivity> getVisibleActivities(ThemeDisplay themeDisplay,
			List<LearningActivity> res, PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		List<LearningActivity> res2 = null;
		if(res != null && res.size()>0){
			res2 = new ArrayList<LearningActivity>();
			res2.addAll(res);
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			for(ListIterator<LearningActivity> itr = res2.listIterator(); itr.hasNext();){
				LearningActivity activity = itr.next();
				try {
					if(!ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW)
							&& !permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId() , "CORRECT"))
						itr.remove();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		return res2;
	}
	
	public static String getEncodingTypeOfFile(final byte[] buf, int offset, int length) {
		
		String detectedCharset = new String();

		if (length > 3) {
			int b1 = buf[offset] & 0xFF;
			int b2 = buf[offset + 1] & 0xFF;
			int b3 = buf[offset + 2] & 0xFF;
			int b4 = buf[offset + 3] & 0xFF;

			switch (b1) {
			case 0xEF:
				if (b2 == 0xBB && b3 == 0xBF) {
					detectedCharset = CHARSET_UTF_8;
				}
				break;
			case 0xFE:
				if (b2 == 0xFF && b3 == 0x00 && b4 == 0x00) {
					detectedCharset = CHARSET_X_ISO_10646_UCS_4_3412;
				} else if (b2 == 0xFF) {
					detectedCharset = CHARSET_UTF_16BE;
				}
				break;
			case 0x00:
				if (b2 == 0x00 && b3 == 0xFE && b4 == 0xFF) {
					detectedCharset = CHARSET_UTF_32BE;
				} else if (b2 == 0x00 && b3 == 0xFF && b4 == 0xFE) {
					detectedCharset = CHARSET_X_ISO_10646_UCS_4_2143;
				}
				break;
			case 0xFF:
				if (b2 == 0xFE && b3 == 0x00 && b4 == 0x00) {
					detectedCharset = CHARSET_UTF_32LE;
				} else if (b2 == 0xFE) {
					detectedCharset = CHARSET_UTF_16LE;
				}
				break;
			} // swich end
		}
		return detectedCharset;
	}
	
	/**
	 * Devuelve true en caso de que estés accediendo al curso porque puedas verlo después de cerrado
	 * Se tienen que cumplir las siguientes condiciones:
	 * 	Check del lmsprefs a true
	 *  &&
	 *  (
	 *  	fecha ejecución de curso pasada
	 *  	||
	 *  	fechas de los módulos cerradas (si están abiertas se está haciendo de forma normal)
	 *  	||
	 *  	allowFinishDate < ahora (la fecha de fin permitida tiene que haber pasado para entrar sólo en modo observador)
	 *  	||
	 *  	passedDate != null && no queden intentos (hayas finalizado el curso y no te queden intentos)
	 *  ) 
	 * @param companyId
	 * @param course
	 * @param userId
	 * @return true si está en modo observador, false en caso contrario
	 */
	
	public static boolean hasPermissionAccessCourseFinished(long companyId, long groupId, long courseId, long userId){
		log.debug(":::hasPermissionAccessCourseFinished:::companyId: " + companyId + " - groupId: "+ groupId + " - userId: " + userId);
		LmsPrefs lmsPrefs = null;
		try {
			lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(lmsPrefs == null || !lmsPrefs.getViewCoursesFinished()){
			return false;
		}
		
		log.debug(":::hasPermissionAccessCourseFinished:::lmsPrefs viewCoursesFinished: " + lmsPrefs.getViewCoursesFinished());
		
		Date now = new Date();
		
		Course course = null;
		try {
			course = CourseLocalServiceUtil.getCourse(courseId);
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(log.isDebugEnabled() && course != null){
			log.debug(":::hasPermissionAccessCourseFinished:::executionEndDate: " + course.getExecutionEndDate());
		}
		
		if(course != null && course.getExecutionEndDate() != null && now.after(course.getExecutionEndDate())){
			return true;
		}
		
		Date lastModuleDate = null;
		
		//Ahora comprobamos si se cumple alguna de las otras tres condiciones
		try {
			for(Module module:ModuleLocalServiceUtil.findAllInGroup(groupId)){
				if(lastModuleDate==null){
					lastModuleDate=module.getEndDate();
				} else if(module.getEndDate()!=null && lastModuleDate.before(module.getEndDate())){
					lastModuleDate=module.getEndDate();
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Si hay equipos 
		try {
			List<Team> teams = TeamLocalServiceUtil.getUserTeams(userId, groupId);
			if(teams!=null && teams.size()>0){
				for(Team team : teams){
					Schedule schedule = null;
					try {
						schedule = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());
						if(schedule!=null){
							log.debug(":::hasPermissionAccessCourseFinished::--ExistTime--::scheduleEndDate: " + lastModuleDate);
							lastModuleDate = schedule.getEndDate();
							break;
						}
					} catch (SystemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}			
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		log.debug(":::hasPermissionAccessCourseFinished:::lastModuleDate: " + lastModuleDate);
		
		if(lastModuleDate != null && lastModuleDate.before(now)){
			log.debug(":::hasPermissionAccessCourseFinished:::lastModuleDateBefore: " + lastModuleDate != null && lastModuleDate.before(now));
			return true;
		}
		
		//Ahora comprobamos la condición de allowFinishDate
		CourseResult courseResult = null;
		try {
			courseResult = CourseResultLocalServiceUtil.getByUserAndCourse(courseId, userId);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(courseResult != null){
			log.debug(":::hasPermissionAccessCourseFinished:::courseResult allowFinishDate: " + courseResult.getAllowFinishDate());
		}
		
		if(courseResult != null && courseResult.getAllowFinishDate() != null && courseResult.getAllowFinishDate().before(now)){
			log.debug(":::hasPermissionAccessCourseFinished:::courseResult allowFinishDate pasada ");
			return true;
		}
		
		//Ahora comprobamos que lo haya finalizado y que no tenga intentos
		if(courseResult == null || courseResult.getPassedDate() == null){
			return false;
		}
		
		log.debug(":::hasPermissionAccessCourseFinished:::courseResult passedDate: " + courseResult.getPassedDate());
		
		return !CourseLocalServiceUtil.hasUserTries(courseId, userId);
	}
	
	public static void saveStringToFile(String fileName, String text){
		
		StringBuffer sb = new StringBuffer(PropsUtil.get("java.io.tmpdir"));
		sb.append(File.separator);
		sb.append("custom_logs"); //Directorio para los ficheros.
		
		File dir = new File(sb.toString());
	    if(!dir.exists()){
	    	dir.mkdir();
	    }
	    	    
	    //Nombre del informe
		sb.append(File.separator);
		sb.append(fileName);
		

		Calendar cal = Calendar.getInstance();

		try {
		 	BufferedWriter out = new BufferedWriter(new FileWriter(sb.toString(),true));
		 	out.append("\n"+cal.getTime()+" - "+text);
		 	out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
}
