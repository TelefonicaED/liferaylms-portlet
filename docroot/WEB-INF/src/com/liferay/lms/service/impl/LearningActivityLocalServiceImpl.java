/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.AnnouncementsFlagConstants;
import com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.LmsLocaleUtil;

/**
 * The implementation of the learning activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityLocalService} interface.
 * </p>
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityLocalServiceUtil} to access the learning activity local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityLocalServiceUtil
 */
public class LearningActivityLocalServiceImpl extends LearningActivityLocalServiceBaseImpl {
	Log log = LogFactoryUtil.getLog(LearningActivityLocalServiceImpl.class);


	public boolean islocked(long actId, long userId) throws Exception
	{
		LearningActivity larn =
				learningActivityPersistence.fetchByPrimaryKey(actId);
		java.util.Date now=new java.util.Date(System.currentTimeMillis());
		Course course=courseLocalService.getCourseByGroupCreatedId(larn.getGroupId());
		if(course.isClosed())
		{
			return true;
		}
		if(larn.getModuleId()>0&&moduleLocalService.isLocked(larn.getModuleId(), userId))
		{
			return true;
		}
		if((larn.getEnddate()!=null&&larn.getEnddate().before(now)) ||(larn.getStartdate()!=null&&larn.getStartdate().after(now)))
		{
			return true;
		}
		if(larn.getPrecedence()!=0)
		{
			return !LearningActivityResultLocalServiceUtil.userPassed(larn.getPrecedence(), userId);
		}
		return false;
	}
	@Override
	public LearningActivity addLearningActivity(LearningActivity learningActivity,ServiceContext serviceContext) throws SystemException, PortalException {

		LearningActivity retorno=this.addLearningActivity(learningActivity.getTitle(),
				learningActivity.getDescription(), learningActivity.getCreateDate(),
				learningActivity.getStartdate(), learningActivity.getEnddate(), learningActivity.getTypeId(),
				learningActivity.getTries(), learningActivity.getPasspuntuation(), learningActivity.getModuleId(), learningActivity.getExtracontent(),
				learningActivity.getFeedbackCorrect(), learningActivity.getFeedbackNoCorrect(), serviceContext);
		retorno.setPrecedence(learningActivity.getPrecedence());
		retorno.setPriority(learningActivity.getPriority());
		retorno.setWeightinmodule(learningActivity.getWeightinmodule());
		learningActivityPersistence.update(retorno, true);

		//auditing
		AuditingLogFactory.audit(retorno.getCompanyId(), retorno.getGroupId(), LearningActivity.class.getName(), retorno.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.ADD, null);
		boolean isNotificationActivated = PrefsPropsUtil.getBoolean(retorno.getCompanyId(), "lms.notifications.active");
		if(isNotificationActivated && learningActivity.getTypeId()!=8){
			List<User> listaUsuarios = userService.getGroupUsers(retorno.getGroupId());
			if(!listaUsuarios.isEmpty()){
			Iterator<User> it = listaUsuarios.iterator();
			while(it.hasNext()){
				User u = it.next();
				try {
					
					if(u.isActive() 
							&& !(PermissionCheckerFactoryUtil.create(u)).hasPermission(retorno.getGroupId(), "com.liferay.lms.model", retorno.getGroupId(), "VIEW_RESULTS")
							&& !retorno.isInactive()
							&& !retorno.isExpired()
							&& !moduleService.isLocked(retorno.getModuleId())
							&& !courseLocalService.getCourseByGroupCreatedId(retorno.getGroupId()).isInactive()
							&& !courseLocalService.getCourseByGroupCreatedId(retorno.getGroupId()).isExpired()
							&& !courseLocalService.getCourseByGroupCreatedId(retorno.getGroupId()).isClosed()){
						String courseTitle = courseLocalService.getCourseByGroupCreatedId(retorno.getGroupId()).getTitle(u.getLocale());
						String subject = LanguageUtil.format(u.getLocale(),"notif.modification.new.title", null);
						String body =LanguageUtil.format(u.getLocale(),"notif.modification.new.body", new String[]{retorno.getTitle(u.getLocale()),courseTitle});
						sendNotification(subject, body, "", "announcements.type.general", 1,serviceContext, retorno.getStartdate(), retorno.getEnddate(),u.getUserId());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}
		
		return retorno;
	}
	public LearningActivity addLearningActivity (String title, String description, 
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
			int typeId,long tries,int passpuntuation,long moduleId, String extracontent,
			String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
					throws SystemException, 
					PortalException {
		String titleAux = title;
		long userId=serviceContext.getUserId();
		LearningActivity larn = learningActivityPersistence.create(counterLocalService.increment(LearningActivity.class.getName()));
		larn.setCompanyId(serviceContext.getCompanyId());
		larn.setGroupId(serviceContext.getScopeGroupId());
		larn.setUserId(userId);

		larn.setUserName(userLocalService.getUser(userId).getFullName());
		larn.setGroupId(serviceContext.getScopeGroupId());
		larn.setDescription(description);
		larn.setTypeId(typeId);
		larn.setTitle(title);
		larn.setStartdate(startDate);
		larn.setCreateDate(new java.util.Date(System.currentTimeMillis()));
		larn.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		larn.setEnddate(endDate);
		larn.setTries(tries);
		larn.setPasspuntuation(passpuntuation);
		larn.setStatus(WorkflowConstants.STATUS_APPROVED);
		larn.setModuleId(moduleId);
		larn.setExtracontent(extracontent);
		larn.setPriority(larn.getActId());
		larn.setFeedbackCorrect(feedbackCorrect);
		larn.setFeedbackNoCorrect(feedbackNoCorrect);
		
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "title");
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "description");
		learningActivityPersistence.update(larn, true);

		resourceLocalService.addModelResources(larn, serviceContext);

		assetEntryLocalService.updateEntry(
				userId, larn.getGroupId(), LearningActivity.class.getName(),
				larn.getActId(), larn.getUuid(),typeId, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, null, null,
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, 
				larn.getTitle().length()<255 ? larn.getTitle():larn.getTitle(Locale.getDefault()),
						null, larn.getDescription(serviceContext.getLocale()),null, null, 0, 0,
						null, false);

		socialActivityLocalService.addUniqueActivity(
				larn.getUserId(), larn.getGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				0, StringPool.BLANK, 0);
		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.ADD, null);

		
		boolean isNotificationActivated = PrefsPropsUtil.getBoolean(larn.getCompanyId(), "lms.notifications.active");
		if(isNotificationActivated && larn.getTypeId()!=8){
			List<User> listaUsuarios = userService.getGroupUsers(larn.getGroupId());
			Iterator<User> it = listaUsuarios.iterator();
			while(it.hasNext()){
				User u = it.next();
				try {
					
					if(u.isActive() 
							&& !(PermissionCheckerFactoryUtil.create(u)).hasPermission(larn.getGroupId(), "com.liferay.lms.model", larn.getGroupId(), "VIEW_RESULTS")
							&& !larn.isInactive()
							&& !larn.isExpired()
							&& !moduleService.isLocked(larn.getModuleId())
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isInactive()
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isExpired()
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isClosed()){
						String courseTitle = courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).getTitle(u.getLocale());
						String subject = LanguageUtil.format(u.getLocale(),"notif.modification.new.title", null);
						String body =LanguageUtil.format(u.getLocale(),"notif.modification.new.body", new String[]{titleAux,courseTitle});
						sendNotification(subject, body, "", "announcements.type.general", 1,serviceContext, startDate, endDate,u.getUserId());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return larn;

	}
	
	public LearningActivity addLearningActivity(long userId, long groupId, int status, 
			Map<Locale, String> title, Map<Locale, String> description, 
			int typeId, Date startdate, Date enddate, 
			long precedence, long tries, int passpuntuation, 
			long moduleId, String extracontent, 
			String feedbackCorrect, String feedbackNoCorrect, 
			long weightinmodule, long teamId, ServiceContext serviceContext) throws PortalException, SystemException{
		
		User user = userLocalService.getUser(userId);
		String titleAux = title.get(user.getLocale());
		Date now = new Date();		
		LearningActivity learningActivity = learningActivityPersistence.create(counterLocalService.increment(LearningActivity.class.getName()));
		learningActivity.setCompanyId(user.getCompanyId());
		learningActivity.setGroupId(groupId);
		learningActivity.setUserId(user.getUserId());
		learningActivity.setUserName(user.getFullName());
		learningActivity.setCreateDate(serviceContext.getCreateDate(now));
		learningActivity.setModifiedDate(serviceContext.getModifiedDate(now));
		learningActivity.setStatus(status);
		learningActivity.setStatusByUserId(user.getUserId());
		learningActivity.setStatusByUserName(user.getFullName());
		learningActivity.setStatusDate(serviceContext.getModifiedDate(now));
		learningActivity.setTitleMap(title, serviceContext.getLocale());
		learningActivity.setDescriptionMap(description, serviceContext.getLocale());
		learningActivity.setTypeId(typeId);
		learningActivity.setStartdate(startdate);
		learningActivity.setEnddate(enddate);
		learningActivity.setPrecedence(precedence);
		learningActivity.setTries(tries);
		learningActivity.setPasspuntuation(passpuntuation);
		learningActivity.setPriority(learningActivity.getActId());
		learningActivity.setModuleId(moduleId);
		learningActivity.setExtracontent(extracontent);
		learningActivity.setFeedbackCorrect(feedbackCorrect);
		learningActivity.setFeedbackNoCorrect(feedbackNoCorrect);
		learningActivity.setWeightinmodule(weightinmodule);
		learningActivity.setExpandoBridgeAttributes(serviceContext);
		learningActivity = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, learningActivity, "title");
		learningActivity = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, learningActivity, "description");
		learningActivityPersistence.update(learningActivity, true);
		resourceLocalService.addModelResources(learningActivity, serviceContext);
		assetEntryLocalService.updateEntry(
				userId, learningActivity.getGroupId(), LearningActivity.class.getName(),
				learningActivity.getActId(), learningActivity.getUuid(),typeId, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, null, null,
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, 
				learningActivity.getTitle().length()<255 ? learningActivity.getTitle():learningActivity.getTitle(Locale.getDefault()),
						null, learningActivity.getDescription(serviceContext.getLocale()),null, null, 0, 0,
						null, false);

		socialActivityLocalService.addUniqueActivity(
				learningActivity.getUserId(), learningActivity.getGroupId(),
				LearningActivity.class.getName(), learningActivity.getActId(),
				0, StringPool.BLANK, 0);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER);
		
		if(Validator.isNull(teamId)){
			if((moduleId!=0)&&(GetterUtil.getBoolean(PrefsPropsUtil.getString("learningactivity.default.hidenewactivity", StringPool.FALSE)))){
				resourcePermissionLocalService.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(learningActivity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
			}
			else { 
				resourcePermissionLocalService.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(learningActivity.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
			}
		}
		else{
			Team team = teamLocalService.getTeam(teamId);
			Role teamMemberRole = roleLocalService.getTeamRole(team.getCompanyId(), team.getTeamId());
			if((moduleId!=0)&&(GetterUtil.getBoolean(PrefsPropsUtil.getString("learningactivity.default.hidenewactivity", StringPool.FALSE)))){
				resourcePermissionLocalService.removeResourcePermission(team.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(learningActivity.getActId()),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
			}else {
				resourcePermissionLocalService.setResourcePermissions(team.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(learningActivity.getActId()),teamMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
			}
		}
	
		//auditing
		AuditingLogFactory.audit(learningActivity.getCompanyId(), learningActivity.getGroupId(), LearningActivity.class.getName(), learningActivity.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.ADD, null);
	
		boolean isNotificationActivated = PrefsPropsUtil.getBoolean(learningActivity.getCompanyId(), "lms.notifications.active");
		if(isNotificationActivated && learningActivity.getTypeId()!=8){
			List<User> listaUsuarios = userService.getGroupUsers(learningActivity.getGroupId());
			Iterator<User> it = listaUsuarios.iterator();
			while(it.hasNext()){
				User u = it.next();
				try {
					
					if(u.isActive() 
							&& !(PermissionCheckerFactoryUtil.create(u)).hasPermission(learningActivity.getGroupId(), "com.liferay.lms.model", learningActivity.getGroupId(), "VIEW_RESULTS")
							&& !learningActivity.isInactive()
							&& !learningActivity.isExpired()
							&& !moduleService.isLocked(learningActivity.getModuleId())
							&& !courseLocalService.getCourseByGroupCreatedId(learningActivity.getGroupId()).isInactive()
							&& !courseLocalService.getCourseByGroupCreatedId(learningActivity.getGroupId()).isExpired()
							&& !courseLocalService.getCourseByGroupCreatedId(learningActivity.getGroupId()).isClosed()){
						String courseTitle = courseLocalService.getCourseByGroupCreatedId(learningActivity.getGroupId()).getTitle(u.getLocale());
						String subject = LanguageUtil.format(u.getLocale(),"notif.modification.new.title", null);
						String body =LanguageUtil.format(u.getLocale(),"notif.modification.new.body", new String[]{titleAux,courseTitle});
						sendNotification(subject, body, "", "announcements.type.general", 1,serviceContext, startdate, enddate,u.getUserId());
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
		}
		
		return learningActivity;
	}

	public LearningActivity modLearningActivity (long actId,String title, String description, java.util.Date createDate,java.util.Date startDate,java.util.Date endDate, int typeId,long tries,int passpuntuation,long moduleId,
			String extracontent, String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
					throws SystemException, 
					PortalException {

		
		long userId=serviceContext.getUserId();
		LearningActivity larn =this.getLearningActivity(actId);
		
		String titleAux = larn.getTitle(serviceContext.getLocale());
		
		larn.setCompanyId(serviceContext.getCompanyId());
		larn.setGroupId(serviceContext.getScopeGroupId());
		larn.setUserId(userId);
		larn.setDescription(description);
		larn.setTitle(title);
		larn.setStartdate(startDate);
		larn.setEnddate(endDate);
		larn.setTries(tries);
		larn.setPasspuntuation(passpuntuation);
		larn.setStatus(WorkflowConstants.STATUS_APPROVED);
		larn.setModuleId(moduleId);
		larn.setExtracontent(extracontent);
		larn.setFeedbackCorrect(feedbackCorrect);
		larn.setFeedbackNoCorrect(feedbackNoCorrect);
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "title");
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "description");
		learningActivityPersistence.update(larn, true);
		try
		{
			
			
			
			

			assetEntryLocalService.updateEntry(
					userId, larn.getGroupId(), LearningActivity.class.getName(),
					larn.getActId(), larn.getUuid(),larn.getTypeId(), serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(serviceContext.getLocale()),null, null, 0, 0,
					null, false);
			SocialActivityLocalServiceUtil.addActivity(
					larn.getUserId(), larn.getGroupId(),
					LearningActivity.class.getName(), larn.getActId(),
					1, StringPool.BLANK, 0);
		}
		catch(Exception e)
		{
		}

		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);

		boolean isNotificationActivated = PrefsPropsUtil.getBoolean(larn.getCompanyId(), "lms.notifications.active");

		if(isNotificationActivated && larn.getTypeId()!=8){
			List<User> listaUsuarios = userService.getGroupUsers(larn.getGroupId());
			Iterator<User> it = listaUsuarios.iterator();
			while(it.hasNext()){
				User u = it.next();
				try {
					
					if(u.isActive() 
							&& !(PermissionCheckerFactoryUtil.create(u)).hasPermission(larn.getGroupId(), "com.liferay.lms.model", larn.getGroupId(), "VIEW_RESULTS")
							&& !larn.isInactive()
							&& !larn.isExpired()
							&& !moduleService.isLocked(larn.getModuleId())
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isInactive()
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isExpired()
							&& !courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).isClosed()){
						String courseTitle = courseLocalService.getCourseByGroupCreatedId(larn.getGroupId()).getTitle(u.getLocale());
						String subject = LanguageUtil.format(u.getLocale(),"notif.modification.larn.title", null);
						String body =LanguageUtil.format(u.getLocale(),"notif.modification.larn.body", new String[]{titleAux,courseTitle});
						sendNotification(subject, body, "", "announcements.type.general", 1,serviceContext, startDate, endDate,u.getUserId());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
		return larn;
	}
	public LearningActivity modLearningActivity (LearningActivity larn, 
			ServiceContext serviceContext)
					throws SystemException, PortalException {

		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "title");
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "description");
		learningActivityPersistence.update(larn, false);
		long userId=serviceContext.getUserId();

		assetEntryLocalService.updateEntry(
				userId, larn.getGroupId(), LearningActivity.class.getName(),
				larn.getActId(), larn.getUuid(),larn.getTypeId(), serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, null, null, 
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(),null, null, 0, 0,
				null, false);
		SocialActivityLocalServiceUtil.addActivity(
				larn.getUserId(), larn.getGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				1, StringPool.BLANK, 0);

		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);

		return larn;

	}

	public LearningActivity modLearningActivity (LearningActivity larn) throws SystemException, PortalException {

		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "title");
		larn = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, larn, "description");
		learningActivityPersistence.update(larn, false);
		return larn;
	}

	public java.util.List<LearningActivity> getLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.findByg(groupId);
	}
	public java.util.List<LearningActivity> getMandatoryLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.findByg_m(groupId, 1);
	}
	public long countLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.countByg(groupId);
	}
	public java.util.List<LearningActivity> getLearningActivitiesOfGroupAndType(long groupId,int typeId) throws SystemException
	{
		return learningActivityPersistence.findByg_t(groupId, typeId);
	}
	public java.util.List<LearningActivity> getLearningActivitiesOfModule(long moduleId) throws SystemException
	{
		return learningActivityPersistence.findBym(moduleId, 0, 1000);
	}
	public java.util.List<Long> getLearningActivityIdsOfModule(long moduleId) throws SystemException
	{
		java.util.List<LearningActivity>larnacts= learningActivityPersistence.findBym(moduleId, 0, 1000);
		java.util.List<Long> result=new java.util.ArrayList<Long>();
		for(LearningActivity larn:larnacts)
		{
			result.add(larn.getActId());
		}
		return result;
	}
	public void deleteLearningactivity (LearningActivity lernact) throws SystemException,
	PortalException {
		long companyId = lernact.getCompanyId();
		assetEntryLocalService.deleteEntry(LearningActivity.class.getName(),lernact.getActId());
		resourceLocalService.deleteResource(
				companyId, LearningActivity.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, lernact.getPrimaryKey());
		assetEntryLocalService.deleteEntry(
				LearningActivity.class.getName(), lernact.getActId());
		learningActivityPersistence.remove(lernact);
		SocialActivityLocalServiceUtil.addActivity(
				lernact.getUserId(), lernact.getGroupId(),
				LearningActivity.class.getName(), lernact.getActId(),
				2, StringPool.BLANK, 0);
	}

	public LearningActivity getPreviusLearningActivity(long actId) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getPreviusLearningActivity(larn);


	}
	public LearningActivity getPreviusLearningActivity(LearningActivity larn) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");  
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("priority").lt(larn.getPriority());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("moduleId").eq(larn.getModuleId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().desc("priority");
		dq.addOrder(createOrder);

		@SuppressWarnings("unchecked")
		java.util.List<LearningActivity> larnsp=(java.util.List<LearningActivity>)learningActivityLocalService.dynamicQuery(dq,0,1);
		if(larnsp!=null&& larnsp.size()>0)
		{
			return larnsp.get(0);
		}
		else
		{
			return null;
		}
	}
	public void goUpLearningActivity(long actId ) throws SystemException
	{
		LearningActivity previusActivity=getPreviusLearningActivity(actId);
		if(previusActivity!=null)
		{

			LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
			long priority=larn.getPriority();
			larn.setPriority(previusActivity.getPriority());
			previusActivity.setPriority(priority);
			learningActivityPersistence.update(larn, true);
			learningActivityPersistence.update(previusActivity, true);

		}

	}
	public void goDownLearningActivity(long actId ) throws SystemException
	{
		LearningActivity previusActivity=getNextLearningActivity(actId);
		if(previusActivity!=null)
		{

			LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
			long priority=larn.getPriority();
			larn.setPriority(previusActivity.getPriority());
			previusActivity.setPriority(priority);
			learningActivityPersistence.update(larn, true);
			learningActivityPersistence.update(previusActivity, true);
		}

	}

	public void moveActivity(long actId, long previusAct, long nextAct) throws SystemException {
		LearningActivity actualAct = (actId>0)?learningActivityPersistence.fetchByPrimaryKey(actId):null;
		LearningActivity finalPrevAct = (previusAct>0)?learningActivityPersistence.fetchByPrimaryKey(previusAct):null;
		LearningActivity finalNextAct = (nextAct>0)?learningActivityPersistence.fetchByPrimaryKey(nextAct):null;

		//Elemento subido
		if(finalNextAct!=null && actualAct.getPriority() > finalNextAct.getPriority()){
			LearningActivity prevAct = getPreviusLearningActivity(actualAct);
			while(prevAct != null && actualAct.getPriority() > finalNextAct.getPriority()){
				goUpLearningActivity(actId);
				actualAct = learningActivityPersistence.fetchByPrimaryKey(actId);
				prevAct = getPreviusLearningActivity(actualAct);
			}
		//Elemento bajado
		}else if(finalPrevAct!=null && actualAct.getPriority() < finalPrevAct.getPriority()){
			LearningActivity nexAct = getNextLearningActivity(actualAct);
			while (nexAct != null && actualAct.getPriority() < finalPrevAct.getPriority()){
				goDownLearningActivity(actId);
				actualAct = learningActivityPersistence.fetchByPrimaryKey(actId);
				nexAct = getNextLearningActivity(actualAct);
			}
		}

	}

	public LearningActivity getNextLearningActivity(long actId ) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getNextLearningActivity(larn);

	}
	public LearningActivity getNextLearningActivity(LearningActivity larn) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("priority").gt(larn.getPriority());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("moduleId").eq(larn.getModuleId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().asc("priority");
		dq.addOrder(createOrder);

		@SuppressWarnings("unchecked")
		java.util.List<LearningActivity> larnsp=(java.util.List<LearningActivity>)learningActivityLocalService.dynamicQuery(dq,0,1);
		if(larnsp!=null&& larnsp.size()>0)
		{
			return larnsp.get(0);
		}
		else
		{
			return null;
		}
	}
	public void deleteLearningactivity (long actId) throws SystemException,
	PortalException {
		this.deleteLearningactivity(LearningActivityLocalServiceUtil.getLearningActivity(actId));
	}

	public String getExtraContentValue(long actId, String key,String defaultValue) throws SystemException
	{
		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){

				hashMap = convertXMLExtraContentToHashMap(actId);
				//Para evitar que retorne null si no existe la clave.
				if(hashMap.containsKey(key)){
					return hashMap.get(key);
				}
				else{
					return defaultValue;
				}
			}
		} catch (Exception e) {
		}

		return "";
	}
	public String getExtraContentValue(long actId, String key) throws SystemException{

		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){

				hashMap = convertXMLExtraContentToHashMap(actId);
				//Para evitar que retorne null si no existe la clave.
				if(hashMap.containsKey(key)){
					return hashMap.get(key);
				}
				else{
					return "";
				}
			}
		} catch (Exception e) {
		}

		return "";
	}
	
	public List<String> getExtraContentValues(long actId, String key) throws SystemException{
		List<String> extraContentValues = new LinkedList<String>();
		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){

				hashMap = convertXMLExtraContentToHashMap(actId);
				//Para evitar que retorne null si no existe la clave.
				
				
				Iterator<Map.Entry<String,String>> it = hashMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry<String,String> pair = (Map.Entry<String, String>)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        extraContentValues.add(pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
				
				
			}
		} catch (Exception e) {
		}

		return extraContentValues;
	}


	public void setExtraContentValue(long actId, String name, String val) throws SystemException{

		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){
				hashMap = convertXMLExtraContentToHashMap(actId);
				hashMap.put(name, val);
			}

			saveHashMapToXMLExtraContent(actId, hashMap);

		} catch (PortalException e) {
		}
	}

	public HashMap<String, String> convertXMLExtraContentToHashMap(long actId) throws SystemException, PortalException 
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String xml ="";

		try {			
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			if(activity != null  && !activity.getExtracontent().equals("")){
				xml = activity.getExtracontent();
			}
			else{
				return hashMap;
			}
			Document document;

			document = SAXReaderUtil.read(xml);
			Element rootElement = document.getRootElement();

			for(Element key:rootElement.elements()){

				if(key.getName().contains("document")){
					hashMap.put(key.getName(), key.attributeValue("id") );
				}else{
					hashMap.put(key.getName(), key.getText());
				}

			}

		} catch (DocumentException e) {
		}

		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	public void saveHashMapToXMLExtraContent(long actId, HashMap<String, String> map) throws SystemException, PortalException 
	{
		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			if(activity != null  && !map.isEmpty()){

				//Element resultadosXML=SAXReaderUtil.createElement("p2p");
				Element resultadosXML=SAXReaderUtil.createElement(getNameLearningActivity(activity.getTypeId()));
				Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

				Iterator it = map.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry e = (Map.Entry)it.next();
					Element eleXML=SAXReaderUtil.createElement(String.valueOf(e.getKey()));
					if(e.getKey().equals("document")){
						eleXML.addAttribute("id", String.valueOf(e.getValue()));
					}else{
						eleXML.addText(String.valueOf(e.getValue()));
					}
					resultadosXML.add(eleXML);
				}
				activity.setExtracontent(resultadosXMLDoc.formattedString());
				learningActivityPersistence.update(activity, true);
			}

		} catch (Exception e) {
		}
	}

	public boolean isLearningActivityDeleteTries(long typeId){
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		if(learningActivityTypeRegistry!=null){
			if(learningActivityTypeRegistry.getLearningActivityType(typeId)!=null){
				return learningActivityTypeRegistry.getLearningActivityType(typeId).hasDeleteTries();
			}
		}
		return false;
	}

	private String getNameLearningActivity(int type) throws SystemException{
		String res = "other";

		switch(type){
		//test
		case 0: res = "test"; break;
		//activity undefined
		case 1: res = "activity"; break;
		//resource
		case 2: res = "multimediaentry"; break;
		//taskp2p
		case 3: res = "p2p"; break;
		//survey
		case 4: res = "survey"; break;
		//offline
		case 5: res = "offline"; break;
		//online
		case 6: res = "online"; break;
		//resource internal
		case 7: res = "resourceInternal"; break;
		}

		return res;
	}
	
	public boolean canBeView(LearningActivity activity, long userId) throws Exception{
		if(activity == null) {
			return false;
		}
		
		return canBeView(activity, 
				PermissionCheckerFactoryUtil.create(
						UserLocalServiceUtil.getUser(userId)));
	}
	
	public boolean canBeView(LearningActivity activity, PermissionChecker permissionChecker) throws Exception{

		//Si tengo permiso de correct soy profesor y puedo siempre
		if((permissionChecker.hasPermission(activity.getGroupId(),"com.liferay.lms.model",activity.getGroupId(),"ACCESSLOCK"))||
				(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"CORRECT"))){
			return true;
		}
		
		// Si no soy de la comunidad no puedo acceder.
		if(UserLocalServiceUtil.hasGroupUser(activity.getGroupId(), permissionChecker.getUserId())) {
			Date today = new Date();
			Module module = ModuleLocalServiceUtil.getModule(activity.getModuleId());
			if(module.getStartDate() != null && module.getEndDate() != null){//xq la fecha en los modulos es obligatoria
				//Si estoy fuera del intervalo de fechas de la actividad, o del m�dulo en caso de no estar alguna definida en la actividad, es editable
				if(
					((activity.getStartdate()==null && (today.compareTo(module.getStartDate())<0))||
					(activity.getStartdate()!=null && (today.compareTo(activity.getStartdate())<0))) ||
					((activity.getEnddate()==null && (today.compareTo(module.getEndDate())>0))||
					(activity.getEnddate()!=null && (today.compareTo(activity.getEnddate())>0)))
				){
				  return true;
				}
				//Si estoy dentro del intervalo de fechas de la actividad, o del m�dulo en caso de no estar definida en la actividad, compruebo si existe ojo y si este est� cerrado, entonces es editable
				if(
					((activity.getStartdate()==null && (today.compareTo(module.getStartDate())>=0))||
					(activity.getStartdate()!=null && (today.compareTo(activity.getStartdate())>=0))) &&
					((activity.getEnddate()==null && (today.compareTo(module.getEndDate())<=0))||
					(activity.getEnddate()!=null && (today.compareTo(activity.getEnddate())<=0)))
				){
					if(PropsUtil.getProperties().getProperty("learningactivity.show.hideactivity")!=null &&
							Boolean.valueOf(PropsUtil.getProperties().getProperty("learningactivity.show.hideactivity"))){
						Role siteMemberRole = RoleLocalServiceUtil.getRole(activity.getCompanyId(), RoleConstants.SITE_MEMBER);
						if(!ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
								ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW)){
							return true;
						}
					}
				}
				
			}
		}
		
		
		return false;
	}
	
	public boolean canBeEdited(LearningActivity activity, long userId) throws Exception{
		if(activity == null) {
			return true;
		}
		
		return canBeEdited(activity, 
				PermissionCheckerFactoryUtil.create(
						UserLocalServiceUtil.getUser(userId)));
	}
	
	public boolean canBeEdited(LearningActivity activity, PermissionChecker permissionChecker) throws Exception{
		//Si tengo permiso de editar bloqueados, es editable
		if(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"UPDATE_ACTIVE")){
			return true;
		//Si tengo permiso de edici�n
		}else if(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),ActionKeys.UPDATE)||
				permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(),ActionKeys.UPDATE)){
			//y no hay intentos de la actividad por parte de alumnos
			if(!LearningActivityTryLocalServiceUtil.areThereTriesNotFromEditors(activity)){
				Date today = new Date();
				Module module = ModuleLocalServiceUtil.getModule(activity.getModuleId());
				if(module.getStartDate() != null && module.getEndDate() != null){//xq la fecha en los modulos es obligatoria
					//Si estoy fuera del intervalo de fechas de la actividad, o del m�dulo en caso de no estar alguna definida en la actividad, es editable
					if(
							(
									(activity.getStartdate()==null && (today.compareTo(module.getStartDate())<0)) 
									||
									(activity.getStartdate()!=null && (today.compareTo(activity.getStartdate())<0))
							) 
							||
							(
									(activity.getEnddate()==null && (today.compareTo(module.getEndDate())>0))
									||
									(activity.getEnddate()!=null && (today.compareTo(activity.getEnddate())>0))
							)
					){return true;}
					//Si estoy dentro del intervalo de fechas de la actividad, o del m�dulo en caso de no estar definida en la actividad, compruebo si existe ojo y si este est� cerrado, entonces es editable
					if(
							((activity.getStartdate()==null && (today.compareTo(module.getStartDate())>=0))||
							(activity.getStartdate()!=null && (today.compareTo(activity.getStartdate())>=0))) &&
							((activity.getEnddate()==null && (today.compareTo(module.getEndDate())<=0))||
							(activity.getEnddate()!=null && (today.compareTo(activity.getEnddate())<=0)))
					){
						if(PropsUtil.getProperties().getProperty("learningactivity.show.hideactivity")!=null &&
								Boolean.valueOf(PropsUtil.getProperties().getProperty("learningactivity.show.hideactivity"))){
							Role siteMemberRole = RoleLocalServiceUtil.getRole(activity.getCompanyId(), RoleConstants.SITE_MEMBER);
							if(!ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
									ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW)){
								return true;
							}
						}
					}
				}
			}
		}
		
		
		return false;
	}
	
	@Override
	@Indexable(type = IndexableType.REINDEX)
	public LearningActivity updateLearningActivity(LearningActivity learningActivity) throws SystemException {
		
		learningActivity = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, learningActivity, "title");
		learningActivity = LmsLocaleUtil.checkDefaultLocale(LearningActivity.class, learningActivity, "description");
		
		return super.updateLearningActivity(learningActivity);
	}
	
private void sendNotification(String title, String content, String url, String type, int priority,ServiceContext serviceContext, java.util.Date startDate,java.util.Date endDate, Long userId){
		
		//ThemeDisplay themeDisplay = (ThemeDisplay) serviceContext.getAttribute(WebKeys.THEME_DISPLAY);	
		//serviceContext.getA
		SimpleDateFormat formatDay = new SimpleDateFormat("dd");
		//formatDay.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
		//formatMonth.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		//formatYear.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatHour = new SimpleDateFormat("HH");
		//formatHour.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatMin = new SimpleDateFormat("mm");
		//formatMin.setTimeZone(themeDisplay.getTimeZone());
		
		Date today=new Date(System.currentTimeMillis());
		
		int displayDateDay=Integer.parseInt(formatDay.format(today));
		int displayDateMonth=Integer.parseInt(formatMonth.format(today))-1;
		int displayDateYear=Integer.parseInt(formatYear.format(today));
		int displayDateHour=Integer.parseInt(formatHour.format(today));
		int displayDateMinute=Integer.parseInt(formatMin.format(today));
		
		int expirationDateDay=Integer.parseInt(formatDay.format(today));
		int expirationDateMonth=Integer.parseInt(formatMonth.format(today))-1;
		int expirationDateYear=Integer.parseInt(formatYear.format(today))+1;
		int expirationDateHour=Integer.parseInt(formatHour.format(today));
		int expirationDateMinute=Integer.parseInt(formatMin.format(today));

		long classNameId=PortalUtil.getClassNameId(User.class.getName());
		//long classPK=serviceContext.getUserId();

		AnnouncementsEntry ae;
		try {
			//themeDisplay.getPli
			ae = AnnouncementsEntryServiceUtil.addEntry(
					serviceContext.getPlid(), 0, userId, title, content, url, type, 
			                            displayDateMonth, displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			                            expirationDateMonth, expirationDateDay, expirationDateYear, expirationDateHour, expirationDateMinute,
			                            priority, false);
			
			AnnouncementsFlagLocalServiceUtil.addFlag(userId,ae.getEntryId(),AnnouncementsFlagConstants.UNREAD);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		                            
	}

	
}