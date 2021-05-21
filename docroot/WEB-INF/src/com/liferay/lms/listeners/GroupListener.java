package com.liferay.lms.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.InternetAddress;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class GroupListener extends BaseModelListener<Group> {
	Log log = LogFactoryUtil.getLog(GroupListener.class);

	@Override
	public void onAfterAddAssociation(Object classPK,
			String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		if(log.isDebugEnabled())log.debug("onAfterAddAssociation");
		
		long groupId = GetterUtil.getLong(classPK);
		long userId = GetterUtil.getLong(associationClassPK);
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			if((User.class.getName().equals(associationClassName))&&
				(Validator.isNotNull(course))){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(), userId, PrincipalThreadLocal.getUserId(), AuditConstants.REGISTER, null);
				
				//Comprobamos que no sea tutor o editor para no enviar el correo
				LmsPrefs lmsPrefs = null;
				try {
					lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
				} catch (PortalException e1) {
					e1.printStackTrace();
				}
				
				boolean tutorRole = lmsPrefs != null && lmsPrefs.getTeacherRole() > 0 
						&& UserGroupRoleLocalServiceUtil.hasUserGroupRole(userId, groupId, lmsPrefs.getTeacherRole());
				boolean editorRole = lmsPrefs != null && lmsPrefs.getEditorRole() > 0 
						&& UserGroupRoleLocalServiceUtil.hasUserGroupRole(userId, groupId, lmsPrefs.getEditorRole());
				
				if(log.isDebugEnabled())log.debug("tutorRole: " + tutorRole);
				if(log.isDebugEnabled())log.debug("editorRole: " + editorRole);
				
				if(log.isDebugEnabled())log.debug("preferencia tutorRole: " + PrefsPropsUtil.getBoolean(course.getCompanyId(), LmsConstant.SEND_MAIL_TO_TUTORS, true));
				if(log.isDebugEnabled())log.debug("preferencia editorRole: " + PrefsPropsUtil.getBoolean(course.getCompanyId(), LmsConstant.SEND_MAIL_TO_EDITORS, true));
				
				//Si no es tutor o editor, es alumno, así que creamos el courseresult
				if(CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), userId) == null) {
					CourseResultLocalServiceUtil.addCourseResult(PrincipalThreadLocal.getUserId(), course.getCourseId(), userId);
				}
				
				if(course!=null&&course.isWelcome()&&course.getWelcomeMsg()!=null&&!StringPool.BLANK.equals(course.getWelcomeMsg())){
					
					User user = null;
					try {
						user = UserLocalServiceUtil.getUser(userId);
					} catch (PortalException e) {
					}
					
					if(user!=null && ((!tutorRole && !editorRole) 
							|| (tutorRole && PrefsPropsUtil.getBoolean(course.getCompanyId(), LmsConstant.SEND_MAIL_TO_TUTORS, true)
							|| (editorRole && PrefsPropsUtil.getBoolean(course.getCompanyId(), LmsConstant.SEND_MAIL_TO_EDITORS, true))))){

				    	String fromName = PrefsPropsUtil.getString(course.getCompanyId(),
								PropsKeys.ADMIN_EMAIL_FROM_NAME);
						String fromAddress = PrefsPropsUtil.getString(course.getCompanyId(),
								PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
				    	String emailTo = user.getEmailAddress();

						try{

							InternetAddress from = new InternetAddress(fromAddress, fromName);
							
							Company company = CompanyLocalServiceUtil.getCompany(course.getCompanyId());
							
					    	String url = PortalUtil.getPortalURL(company.getVirtualHostname(), 80, false);
					    	//QUITANDO PUERTOS
							String[] urls = url.split(":");
							url = urls[0] + ":" +urls[1];  
							log.debug("url: " + url);
							
					    	String urlcourse = url+"/web"+course.getFriendlyURL();
					    	String subject = new String();
					    
					    	if(course.getWelcomeSubject()!=null&&!StringPool.BLANK.equals(course.getWelcomeSubject())){
						    	subject = course.getWelcomeSubject();
					    	}else{
						    	subject = LanguageUtil.format(user.getLocale(),"welcome-subject", new String[]{course.getTitle(user.getLocale())});

					    	}
					    	
					    	String userRole = LanguageUtil.get(user.getLocale(), "courseadmin.adminactions.students");
					    	if(tutorRole){
					    		Role role = RoleLocalServiceUtil.getRole(lmsPrefs.getTeacherRole());
					    		userRole = role.getTitle(user.getLocale());
					    	}else if(editorRole){
					    		Role role = RoleLocalServiceUtil.getRole(lmsPrefs.getEditorRole());
					    		userRole = role.getTitle(user.getLocale());
					    	}
					    	
					    	subject = StringUtil.replace(subject, "[$ROLE$]", userRole);
					    	
					    	String body = StringUtil.replace(
				    			course.getWelcomeMsg(),
				    			new String[] {"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]","[$PORTAL_URL$]","[$TO_ADDRESS$]","[$TO_NAME$]","[$USER_SCREENNAME$]","[$TITLE_COURSE$]","[$ROLE$]","[$TO_FIRSTNAME$]"},
				    			new String[] {fromAddress, fromName, urlcourse, url, user.getEmailAddress(), user.getFullName(),user.getScreenName(),course.getTitle(user.getLocale()), userRole, user.getFirstName()});
				    	
							if(log.isDebugEnabled()){
								log.debug(from);
								log.debug(emailTo);
								log.debug(subject);
								log.debug(body);
							}
							
													
							//Envio auditoria
							Message messageAudit=new Message();
							messageAudit.put("auditing", "TRUE");
							messageAudit.put("groupId", course.getGroupCreatedId());
							messageAudit.put("subject", subject);
							messageAudit.put("body", 	body);
							messageAudit.setResponseId("1111");
							
							try {
								MessageBusUtil.sendSynchronousMessage("lms/mailing", messageAudit, 1000);
							} catch (MessageBusException e) {
								if (log.isDebugEnabled())
									log.debug(e.getMessage());
							}
							
							//Envio el correo
							Message message=new Message();

							message.put("to", emailTo);

							message.put("subject", 	subject);
							message.put("body", 	body);
							message.put("groupId", 	course.getGroupCreatedId());
							message.put("userId",  	user.getUserId());
							message.put("testing", 	StringPool.FALSE);
							message.put("community", course.getTitle(user.getLocale()));
							message.put("type", 	"COURSE_INSCRIPTION");
							message.put("url", 		url);
							message.put("urlcourse",urlcourse);		

							
							if(course.getWelcomeAddToCalendar()){
								String courseTitle = course.getTitle(user.getLocale());
								
								courseTitle = "<a href='"+urlcourse+"'  target='_blank'>" + courseTitle + "</a>" ;
								
								log.debug("::::courseTitle::: " + courseTitle);
								
								String name = LanguageUtil.get(user.getLocale(), "calendar.course-start") + StringPool.COLON + StringPool.NBSP + courseTitle;
								
								
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
								format.setTimeZone(user.getTimeZone());
								String time = format.format(course.getExecutionStartDate());
									
								
									
								Date startDate =course.getExecutionStartDate();
								try {
									startDate = format.parse(time);
								} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								
								log.debug("START DATE "+startDate);
								Calendar calendar = Calendar.getInstance(user.getTimeZone());
								calendar.setTime(course.getExecutionStartDate());
								calendar.add(Calendar.HOUR_OF_DAY,1);
								Date endDate = calendar.getTime();
								
								
								
								
					
								
								// initialise as an all-day event..
								VEvent courseEvent = new VEvent();
								
								
								DtStart start = new DtStart(new net.fortuna.ical4j.model.DateTime(startDate));
								DtEnd end = new DtEnd(new net.fortuna.ical4j.model.DateTime(endDate));
								courseEvent.getProperties().add(start);
								courseEvent.getProperties().add(end);
								courseEvent.getProperties().add(new Summary(course.getTitle(user.getLocale())));								
								courseEvent.getProperties().add(new Description(HtmlUtil.extractText(name)));
								UidGenerator ug = new UidGenerator("1");
								courseEvent.getProperties().add(ug.generateUid());
				
						
								// Generacion Ical
								net.fortuna.ical4j.model.Calendar iCal = new net.fortuna.ical4j.model.Calendar();
								iCal.getProperties().add(new ProdId("-//Liferay Inc//Liferay Portal 6.1.1//EN"));
								iCal.getProperties().add(Version.VERSION_2_0);
								iCal.getProperties().add(CalScale.GREGORIAN);
								iCal.getProperties().add(Method.PUBLISH);
								TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
								
								net.fortuna.ical4j.model.TimeZone timeZone = registry.getTimeZone(user.getTimeZone().getID());
								iCal.getComponents().add(timeZone.getVTimeZone());
								
								iCal.getComponents().add(courseEvent);
								
								
								log.debug("EVENT "+courseEvent.toString());
								
								// Descargar el fichero
								FileOutputStream writer;
								try {
									File[] attachments = new File[1];
									String[] attachmentNames = new String[1];
									
									
									String fileName = LanguageUtil.get(user.getLocale(), "course.invite-outlook")+".ics";

									File file = FileUtil.createTempFile("ics");
									
									writer = new FileOutputStream(file);
									
							
									
									try {
										final CalendarOutputter outputter=new CalendarOutputter();
										outputter.output(iCal,writer);
									} catch (Exception e) {
										e.printStackTrace();
									}

									writer.flush();
									writer.close();
									attachments[0] =  file;
									attachmentNames[0] = fileName;
									log.debug("ADDING ATTACHMENT "+attachments.length);
									message.put("attachments", attachments);
									message.put("attachmentNames", attachmentNames);
								}catch (Exception e){
									e.printStackTrace();
								}
								
								
								
								
								
								
							}
							MessageBusUtil.sendMessage("lms/mailing", message);
							
						}
						catch(Exception ex){
							if(log.isDebugEnabled()) ex.printStackTrace();
						}	
					}
				}
			}
			
		} catch (SystemException e) {
			throw new ModelListenerException(e);
		}
	}
	
	@Override
	public void onAfterRemoveAssociation(Object classPK,
			String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		
		if(log.isDebugEnabled())log.debug("onAfterRemoveAssociation");
		
		long groupId = GetterUtil.getLong(classPK);
		long userId = GetterUtil.getLong(associationClassPK);
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			if((User.class.getName().equals(associationClassName))&&
				(Validator.isNotNull(course))){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(), userId, PrincipalThreadLocal.getUserId(), AuditConstants.UNREGISTER, null);
				
				if(course!=null&&course.isGoodbye()&&course.getGoodbyeMsg()!=null&&!StringPool.BLANK.equals(course.getGoodbyeMsg())){
					if(log.isDebugEnabled())log.debug("course.courseId: " + course.getCourseId());
					if(log.isDebugEnabled())log.debug("course.isGoodbye(): " + course.isGoodbye());
					if(log.isDebugEnabled())log.debug("course.getGoodbyeMsg(): " + course.getGoodbyeMsg());
					User user = null;
					Company company = null;
					try {
						user = UserLocalServiceUtil.getUser(userId);
						company = CompanyLocalServiceUtil.getCompany(course.getCompanyId());
					} catch (PortalException e) {
					}
					
					LmsPrefs lmsPrefs = null;
					try {
						lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
					} catch (PortalException e1) {
						e1.printStackTrace();
					}
					
					boolean tutorRole = lmsPrefs != null && lmsPrefs.getTeacherRole() > 0 
							&& UserGroupRoleLocalServiceUtil.hasUserGroupRole(userId, groupId, lmsPrefs.getTeacherRole());
					boolean editorRole = lmsPrefs != null && lmsPrefs.getEditorRole() > 0 
							&& UserGroupRoleLocalServiceUtil.hasUserGroupRole(userId, groupId, lmsPrefs.getEditorRole());
					
					if(log.isDebugEnabled())log.debug("tutorRole: " + tutorRole);
					if(log.isDebugEnabled())log.debug("editorRole: " + editorRole);
					
					if(log.isDebugEnabled())log.debug("preferencia tutorRole: " + PrefsPropsUtil.getBoolean(company.getCompanyId(), LmsConstant.SEND_MAIL_TO_TUTORS, true));
					if(log.isDebugEnabled())log.debug("preferencia editorRole: " + PrefsPropsUtil.getBoolean(company.getCompanyId(), LmsConstant.SEND_MAIL_TO_EDITORS, true));
					
					
					if(user!=null&&company!=null && ((!tutorRole && !editorRole) 
							|| (tutorRole && PrefsPropsUtil.getBoolean(company.getCompanyId(), LmsConstant.SEND_MAIL_TO_TUTORS, true)
							|| (editorRole && PrefsPropsUtil.getBoolean(company.getCompanyId(), LmsConstant.SEND_MAIL_TO_EDITORS, true))))){

				    	String fromName = PrefsPropsUtil.getString(course.getCompanyId(),
								PropsKeys.ADMIN_EMAIL_FROM_NAME);
						String fromAddress = PrefsPropsUtil.getString(course.getCompanyId(),
								PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
				    	String emailTo = user.getEmailAddress();
				    	String nameTo = user.getFullName();

						try{
							InternetAddress to = new InternetAddress(emailTo, nameTo);
							InternetAddress from = new InternetAddress(fromAddress, fromName);
							
					    	String url = PortalUtil.getPortalURL(company.getVirtualHostname(), 80, false);
					    	//QUITANDO PUERTOS
							String[] urls = url.split(":");
							url = urls[0] + ":" +urls[1];  // http:prueba.es:8080		
							log.debug("url: " + url);
					    	
					    	String urlcourse = url+"/web"+course.getFriendlyURL();
					    	String subject = new String();
					    	
					    	if(course.getGoodbyeSubject()!=null&&!StringPool.BLANK.equals(course.getGoodbyeSubject())){
						    	subject = course.getGoodbyeSubject();
					    	}else{
						    	subject = LanguageUtil.format(user.getLocale(),"goodbye-subject", new String[]{course.getTitle(user.getLocale())});

					    	}
					    	
					    	String userRole = LanguageUtil.get(user.getLocale(), "courseadmin.adminactions.students");
					    	if(tutorRole){
					    		Role role = RoleLocalServiceUtil.getRole(lmsPrefs.getTeacherRole());
					    		userRole = role.getTitle(user.getLocale());
					    	}else if(editorRole){
					    		Role role = RoleLocalServiceUtil.getRole(lmsPrefs.getEditorRole());
					    		userRole = role.getTitle(user.getLocale());
					    	}
					    	
					    	subject = StringUtil.replace(subject, "[$ROLE$]", userRole);
					    	
					    	String body = StringUtil.replace(
				    			course.getGoodbyeMsg(),
				    			new String[] {"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]","[$PORTAL_URL$]","[$TO_ADDRESS$]","[$TO_NAME$]","[$USER_SCREENNAME$]","[$TITLE_COURSE$]","[$ROLE$]","[$TO_FIRSTNAME$]"},
				    			new String[] {fromAddress, fromName, urlcourse, url, user.getEmailAddress(), user.getFullName(),user.getScreenName(),course.getTitle(user.getLocale()), userRole, user.getFirstName()});
				    	
					    	
							if(log.isDebugEnabled()){
								log.debug(from);
								log.debug(to);
								log.debug(subject);
								log.debug(body);
							}
							
							//Envio el correo (Preparado para cuando se quiera mandar al mailing)
							Message message=new Message();

							message.put("to", emailTo);
							message.put("subject", 	subject);
							message.put("body", 	body);
							message.put("groupId", 	course.getGroupCreatedId());
							message.put("userId",  	user.getUserId());
							message.put("testing", 	StringPool.FALSE);
							message.put("community", course.getTitle(user.getLocale()));
							message.put("type", 	"COURSE_INSCRIPTION");
							message.put("url", 		url);
							message.put("urlcourse",urlcourse);		

							
							
							

							
							
							MessageBusUtil.sendMessage("lms/mailing", message);
						}
						catch(Exception ex)
						{
							if(log.isDebugEnabled())ex.printStackTrace();
						}		
					}
				}
				
			}
		} catch (SystemException e) {
			throw new ModelListenerException(e);
		}
	}
}
