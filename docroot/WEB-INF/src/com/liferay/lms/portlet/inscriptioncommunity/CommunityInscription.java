package com.liferay.lms.portlet.inscriptioncommunity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.course.inscriptiontype.InscriptionTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/** Portlet implementation class CommunityInscription */
public class CommunityInscription extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(CommunityInscription.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay  =(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			Course course= CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
			
			InscriptionTypeRegistry inscriptionTypeRegistry = new InscriptionTypeRegistry();
			InscriptionType inscriptionType = inscriptionTypeRegistry.getInscriptionType(course.getInscriptionType());
			renderRequest.setAttribute("inscriptionType", inscriptionType);
			
			log.debug("inscriptionTypeFactory: " + inscriptionType.getTypeId() + " - " + inscriptionType.getPortletId());
			if(!Validator.isNull(inscriptionType.getPortletId())) {
				StringBundler sb = new StringBundler();
				sb.append("<portlet-preferences >");
				sb.append("<preference>");
				sb.append("<name>");
				sb.append("portletSetupShowBorders");
				sb.append("</name>");
				sb.append("<value>");
				sb.append("false");
				sb.append("</value>");
				sb.append("</preference>");
				sb.append("</portlet-preferences>");
				renderRequest.setAttribute("inscriptionPortletName", inscriptionType.getPortletId());
				renderRequest.setAttribute("defaultPreferences", sb);
			}else if(themeDisplay.isSignedIn()){
				log.debug("usuario logado");
				HttpServletRequest renderServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
				HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(renderServletRequest);
				
				String inscriptionParam = servletRequest.getParameter("inscriptionOk");
				log.debug("inscriptionParam: " + inscriptionParam);
				if(Validator.isNotNull(inscriptionParam)){
					try{
						SessionMessages.add(renderRequest, "inscription-ok");
					}catch(Exception e){
						if(log.isDebugEnabled())e.printStackTrace();
					}
				}
				
				PortletURL unsubscribeURL = renderResponse.createActionURL();
				unsubscribeURL.setParameter("javax.portlet.action", "unsubscribe");
				renderRequest.setAttribute("unsubscribeURL", unsubscribeURL);
				
				//Comprobamos si estoy inscrita en el curso o en alguna convocatoria
				if(UserLocalServiceUtil.hasGroupUser(course.getGroupCreatedId(), themeDisplay.getUserId())) {
					//Ya estoy inscrito, mando el curso y que estoy inscrito
					log.debug("usuario registrado: " + course.getCourseId() );
					renderRequest.setAttribute("registredUser", true);
					renderRequest.setAttribute("course", course);
				}else {
					//Comprobamos si estoy inscrita en alguna de las hijas
					long courseParentId = course.getCourseId();
					log.debug("courseParentId: " + courseParentId);
					if(course.getParentCourseId() != LmsConstant.DEFAULT_PARENT_COURSE_ID) {
						courseParentId = course.getParentCourseId();
					}
					
					renderRequest.setAttribute("TYPE_SITE_OPEN", GroupConstants.TYPE_SITE_OPEN);
					renderRequest.setAttribute("TYPE_SITE_RESTRICTED", GroupConstants.TYPE_SITE_RESTRICTED);
					renderRequest.setAttribute("STATUS_PENDING", MembershipRequestConstants.STATUS_PENDING);
					renderRequest.setAttribute("STATUS_DENIED", MembershipRequestConstants.STATUS_DENIED);
					
					log.debug("courseParentId: " + courseParentId);
					List<Course> listChildCourses = CourseLocalServiceUtil.getChildsRegistredUser(courseParentId, themeDisplay.getUserId());
					if(listChildCourses != null && listChildCourses.size() > 0) {
						log.debug("estoy inscrito en una convocatoria hija: " + listChildCourses.get(0).getCourseId());
						//Estoy inscrita en alguna convocatoria de las hijas, mando qeu estoy inscrita y las convocatorias en las que estoy
						renderRequest.setAttribute("registredUser", true);
						renderRequest.setAttribute("listChildCourses", listChildCourses);
					}else {
						//Comprobamos los prerequisitos del curso padre (tanto si tiene convocatorias como si no)
						List<CourseCompetence> listCourseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId( 
								course.getParentCourseId() == 0 ? course.getCourseId() : course.getParentCourseId(), true);
						renderRequest.setAttribute("listCourseCompetences", listCourseCompetences);
						
						PortletURL enrollURL = renderResponse.createActionURL();
						enrollURL.setParameter("javax.portlet.action", "enroll");
						renderRequest.setAttribute("enrollURL", enrollURL);
						
						//Si es una convocatoria sabemos que no estoy inscrito en ninguna más así que paso el curso, además 
						//no puede tener inscripción por equipos porque sólo la tienen los padre
						if(course.getParentCourseId() != LmsConstant.DEFAULT_PARENT_COURSE_ID) {
							log.debug("Es convocatoria hija: " + course.getCourseId());
							renderRequest.setAttribute("course", course);
						}else {
							//Si es un curso padre y no tengo convocatorias hijas mando el curso, si no mando las hijas
							//Buscamos los cursos hijos abiertos o restringidos
							
							listChildCourses = CourseLocalServiceUtil.getOpenOrRestrictedChildCourses(course.getCourseId());
							
							if(course.getParentCourseId() == LmsConstant.DEFAULT_PARENT_COURSE_ID && (listChildCourses == null || listChildCourses.size() == 0)) {
								log.debug("Es convocatoria padre que no tiene hijos: " + course.getCourseId());
								//Mando sólo el curso hijo
								renderRequest.setAttribute("course", course);
								//Si es curso padre y no tiene cursos hijos puede haber inscripción por equipos
								List<Team> teams = TeamLocalServiceUtil.getGroupTeams(course.getGroupCreatedId());
								List<Schedule> schedules = new ArrayList<Schedule>();
								Schedule schedule = null;
								Date now = new Date();
								for(Team team : teams){
									schedule = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());	
									if(schedule!=null){
										renderRequest.setAttribute("hasTeams", true);
										log.debug("hasTeams");
										if(schedule.getStartDate().before(now)&&schedule.getEndDate().after(now)){
											schedules.add(schedule);
										}	
									}
								}
								renderRequest.setAttribute("schedules", schedules);
								log.debug("schedules: " + schedules.size());	
							}else {
								renderRequest.setAttribute("listChildCourses", listChildCourses);
								log.debug("childCourses: " + listChildCourses.size());
								//Paso también el curso padre
								renderRequest.setAttribute("course", course);
								log.debug("course: " + course.getCourseId());
							}
						}
					}
				}
			}
			
			super.doView(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}

	@ProcessAction(name = "enroll") 
	public void enroll(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException{
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if (!themeDisplay.isSignedIn()) {return;}
		
		long courseId = ParamUtil.getLong(actionRequest, "courseId");
		long teamId = ParamUtil.getLong(actionRequest, "teamId");
		
		log.debug("courseId: " + courseId);
		log.debug("teamId: " + teamId);
		
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		try {
			InscriptionTypeRegistry inscriptionTypeRegistry = new InscriptionTypeRegistry();
			
			InscriptionType inscriptionType = inscriptionTypeRegistry.getInscriptionType(course.getInscriptionType());
			
			inscriptionType.enrollUser(courseId, themeDisplay.getUserId(), teamId, serviceContext);
			if(course.getGroupCreatedId() != themeDisplay.getScopeGroupId()) {
				//Redirijo
				String url = themeDisplay.getPortalURL() + "/" + themeDisplay.getLocale().getLanguage() + "/web" + course.getFriendlyURL()+"?inscriptionOk=true";
	    		log.debug("Redirect to: "+url);
	    		actionResponse.sendRedirect(url);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SessionErrors.add(actionRequest, "error-enroll-user", e.getMessage());
		}
	}
	
	@ProcessAction(name="unsubscribe")
	public void unsubscribe(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException{
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if (!themeDisplay.isSignedIn()) {return;}
		
		long courseId = ParamUtil.getLong(actionRequest, "courseId");
		log.debug("InscriptionPortlet::unsubscribe::courseId::" + courseId);
		
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
		InscriptionTypeRegistry inscriptionTypeRegistry = new InscriptionTypeRegistry();
		InscriptionType inscriptionType = inscriptionTypeRegistry.getInscriptionType(course.getInscriptionType());
		
		boolean result = inscriptionType.unsubscribeUser(course, themeDisplay.getUserId());
		
		log.debug("InscriptionPortlet::unsubscribe::result::" + result);
		if(result) {
			SessionMessages.add(actionRequest, "unsusbscribe");
		}else{
			SessionErrors.add(actionRequest, "unsusbscribe");
		}
	}

}
