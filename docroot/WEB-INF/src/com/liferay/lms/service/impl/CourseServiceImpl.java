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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liferay.lms.asset.LearningActivityBaseAssetRenderer;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.base.CourseServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;


/**
 * The implementation of the course remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseServiceUtil} to access the course remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseServiceBaseImpl
 * @see com.liferay.lms.service.CourseServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class CourseServiceImpl extends CourseServiceBaseImpl {
	private static Log log = LogFactoryUtil.getLog(CourseServiceImpl.class);
	
	@JSONWebService
	public java.util.List<Course> getCoursesOfGroup(long groupId) throws SystemException
	{
		return coursePersistence.filterFindByGroupId(groupId);
		
	}
	@JSONWebService
	public Course createCourse(long groupId,String title, String description,boolean published,String summary,int evaluationmethod,int calificationType,int template,int registermethod,int maxusers, Date startregistrationdate,Date endregistrationdate) throws PortalException, SystemException
	{
		return createCourse(groupId, title, description, published, summary, evaluationmethod, calificationType, template, registermethod, maxusers, startregistrationdate, endregistrationdate,null,null);
	}
	
	
	@JSONWebService
	public Course createCourse(long groupId,String title, String description,boolean published,String summary,int evaluationmethod,int calificationType,int template,int registermethod,int maxusers, Date startregistrationdate,Date endregistrationdate, Date startExecutionDate, Date endExecutionDate) throws PortalException, SystemException
	{
		User user=getUser();
		
		java.util.Date ahora=new java.util.Date(System.currentTimeMillis());
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		serviceContext.setUserId(user.getUserId());
		serviceContext.setScopeGroupId(groupId);
		
		if( getPermissionChecker().hasPermission(groupId, "com.liferay.lms.coursemodel",groupId,"ADD_COURSE"))
		{
			Course course = CourseLocalServiceUtil.addCourse(title, description, summary,  StringPool.BLANK, user.getLocale(), ahora, startregistrationdate, endregistrationdate, startExecutionDate, endExecutionDate, template, registermethod, evaluationmethod, calificationType, maxusers, serviceContext, false);
			
			com.liferay.lms.service.CourseLocalServiceUtil.setVisible(course.getCourseId(), published);
			return course;
		}
		else
		{
			return null;
		}
	}
	
	
	@JSONWebService
	public Course createCourse(String title, String description,boolean published,String summary,int evaluationmethod,int calificationType,int template,int registermethod,int maxusers, Date startregistrationdate,Date endregistrationdate) throws PortalException, SystemException
	{
		String groupName = GroupConstants.GUEST;
		long companyId = PortalUtil.getDefaultCompanyId();
		
		long guestGroupId = GroupLocalServiceUtil.getGroup(companyId, groupName).getGroupId();
		return createCourse(guestGroupId, title, description, published, summary, evaluationmethod, calificationType, template, registermethod, maxusers, startregistrationdate, endregistrationdate);
	}
	@JSONWebService
	public java.util.List<Course> getCourses() throws SystemException, PortalException
	{
		String groupName = GroupConstants.GUEST;
		 long companyId = PortalUtil.getDefaultCompanyId();
		 long guestGroupId = GroupLocalServiceUtil.getGroup(companyId, groupName).getGroupId();
		 return coursePersistence.filterFindByGroupId(guestGroupId);
			
		
	}
	@JSONWebService
	public java.util.List<String> getCourseStudents(long courseId) throws PortalException, SystemException
	{
		User user=getUser();
		
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(course.getCompanyId()==user.getCompanyId())
		{
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
		
		Role commmanager=RoleLocalServiceUtil.getRole(course.getCompanyId(), RoleConstants.SITE_MEMBER) ;
		java.util.List<String> users=new java.util.ArrayList<String>();
		long createdGroupId=course.getGroupCreatedId();
		java.util.List<User> userst=UserLocalServiceUtil.getGroupUsers(createdGroupId);
		
		for(User usert:userst)
		{
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(usert.getUserId(),createdGroupId);
			boolean remove =false;
			for(UserGroupRole ugr:userGroupRoles){
				if(ugr.getRoleId()==prefs.getEditorRole()||ugr.getRoleId()==prefs.getTeacherRole()){
					remove = true;
					break;
				}
			}
			if(!remove){
				users.add(usert.getScreenName());
			}
		}
		return users;
		}
		return null;
	}
	@JSONWebService
	public java.util.List<String> getCourseTeachers(long courseId) throws PortalException, SystemException
	{
		User user=getUser();
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		long createdGroupId=course.getGroupCreatedId();
		if(course.getCompanyId()==user.getCompanyId())
		{
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
			List<UserGroupRole> ugrs=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(createdGroupId, prefs.getTeacherRole());
			List<String> users=new java.util.ArrayList<String>();
			for(UserGroupRole ugr:ugrs)
			{
				users.add(ugr.getUser().getScreenName());
			}
			return users;
		}
		return null;
	}
	@JSONWebService
	public java.util.List<String> getCourseEditors(long courseId) throws PortalException, SystemException
	{
		User user=getUser();
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		long createdGroupId=course.getGroupCreatedId();
		if(course.getCompanyId()==user.getCompanyId())
		{
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
			List<UserGroupRole> ugrs=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(createdGroupId, prefs.getEditorRole());
			List<String> users=new java.util.ArrayList<String>();
			for(UserGroupRole ugr:ugrs)
			{
				users.add(ugr.getUser().getScreenName());
			}
			return users;
		}
		return null;
	}
	@JSONWebService
	public void addStudentToCourse(long courseId,String login) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.fetchCourse(courseId);
		User user = userLocalService.fetchUserByScreenName(serviceContext.getCompanyId(), login);
		if(course!=null && user!=null){
			if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
			{
				UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
						course.getGroupCreatedId(), RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId());
				if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
					GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
				}

				//auditing
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.REGISTER, null);
			}
		}
	}
	@JSONWebService
	public void addStudentToCourseWithDates(long courseId,String login,Date allowStartDate,Date allowFinishDate) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
			User user = userLocalService.getUserByScreenName(serviceContext.getCompanyId(), login);
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId());
			
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
				//sendEmail(user,course);
			}
			
			CourseResult courseResult=courseResultLocalService.getCourseResultByCourseAndUser(courseId, user.getUserId());
			if(courseResult==null)
			{
				courseResultLocalService.create(courseId, user.getUserId(), allowStartDate, allowFinishDate);
			}
			else
			{
				courseResult.setAllowStartDate(allowStartDate);
				courseResult.setAllowFinishDate(allowFinishDate);
				courseResultLocalService.updateCourseResult(courseResult);
			}
			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.REGISTER, null);		 
		}
	}
	@JSONWebService
	public void editUserInscriptionDates(long courseId,String login,Date allowStartDate,Date allowFinishDate) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		User user=userLocalService.getUserByScreenName(serviceContext.getCompanyId(), login);
		editUserInscriptionDates(courseId, user.getUserId(), allowStartDate, allowFinishDate);
	}
	public void editUserInscriptionDates(long courseId,long userId,Date allowStartDate,Date allowFinishDate) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
			User user = userLocalService.getUser(userId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				return;
			}		
			CourseResult courseResult=courseResultLocalService.getCourseResultByCourseAndUser(courseId, user.getUserId());
			if(courseResult==null)
			{
				courseResultLocalService.create(courseId, user.getUserId(), allowStartDate, allowFinishDate);
			}
			else
			{
				courseResult.setAllowStartDate(allowStartDate);
				courseResult.setAllowFinishDate(allowFinishDate);
				courseResultLocalService.updateCourseResult(courseResult);
			}
			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);		 
		}
	}
	@JSONWebService
	public void addTeacherToCourse(long courseId,String login) throws PortalException, SystemException
	{ 
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
		
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getTeacherRole());
			
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
				//sendEmail(user,course);
			}

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.REGISTER, null);
			
		
		}
	}
	@JSONWebService
	public void addEditorToCourse(long courseId,String login) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
		
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getEditorRole());
			
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
				//sendEmail(user,course);
			}

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.REGISTER, null);
		
		}
	}
	@JSONWebService
	public void removeStudentFromCourse(long courseId,String login) throws PrincipalException, PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
		
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			GroupLocalServiceUtil.unsetUserGroups(user.getUserId(),new long[] { course.getGroupCreatedId() });

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UNREGISTER, null);
			
		
		}
	}
	@JSONWebService
	public void removeTeacherFromCourse(long courseId,String login) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
		
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getTeacherRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UNREGISTER, null);
			
		
		}
	}
	@JSONWebService
	public void removeEditorFromCourse(long courseId,String login) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS")&& ! course.isClosed())
		{
		
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getEditorRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UNREGISTER, null);
			
		
		}
	}
	@JSONWebService
	public long getUserResult(long courseId,String login) throws PortalException, SystemException
	{ 
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);	
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,"ASSIGN_MEMBERS"))
		{
			
		   CourseResult courseResult=courseResultLocalService.getCourseResultByCourseAndUser(courseId, user.getUserId());
		   return courseResult.getResult();
		}
		return 0;
	}

	@JSONWebService
	public java.util.List<Course> myCourses() throws PortalException, SystemException
	{
		User usuario= this.getUser();
		java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(usuario.getUserId());
		java.util.List<Course> results=new java.util.ArrayList<Course>();
		
		for(Group groupCourse:groups)
		{
			
			
			Course course=courseLocalService.fetchByGroupCreatedId(groupCourse.getGroupId());
			if(course!=null)
			{
				results.add(course);
			}
		}
		return results;

	}

	private long [] getGruposFromExpando(Long companyId, String [] userGroupNames) throws PortalException, SystemException
	{
		List<Long> userGroupIds = new ArrayList<Long>();
		if (userGroupNames != null) {
			for (String ugn : userGroupNames) {
				UserGroup ug = UserGroupLocalServiceUtil.getUserGroup(companyId, ugn);
				userGroupIds.add(ug.getUserGroupId());
			}
		}
		return ArrayUtil.toArray(userGroupIds.toArray(new Long[0]));
	}
	
	@JSONWebService
	public void addUser(String login, String firstName,String lastName,String email) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		int rolesItr=0;	
		Date birthday = new Date(0);					
		User creatorUser=getUser();		
		long[] roles=new long[0];
		UserServiceUtil.addUser(serviceContext.getCompanyId(), false, /*password1*/login, /*password2*/login, false, login, 
					email, new Long(0), "", creatorUser.getLocale(), firstName, StringPool.BLANK, lastName, -1, -1, 
					creatorUser.isMale(), birthday.getMonth(), birthday.getDay(), birthday.getYear()+1900 , StringPool.BLANK, null, null, null, roles, 
					false, ServiceContextThreadLocal.getServiceContext());
		
		
	}
	@JSONWebService
	public void updateUser(String login, String firstName,String lastName,String email) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
			User user = UserLocalServiceUtil.getUserByScreenName(
					serviceContext.getCompanyId(), login);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(email);
			List<UserGroup> userGroups = user.getUserGroups();
			long[] userArray = new long[] { user.getUserId() };
			if(PortalPermissionUtil.contains(
					getPermissionChecker(), ActionKeys.ADD_USER))
			{
				UserLocalServiceUtil.updateUser(user);
			}
	
	}
	
	@JSONWebService
	public boolean existsCourseName(Long companyId, Long groupId, String groupName) {
		
		try {
			return CourseLocalServiceUtil.existsCourseName(companyId, groupId, groupName);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@JSONWebService
	public String getLogoUrl(Long courseId) {
		Course course = null;
		Group generatedGroup = null;
		try {
			course = courseLocalService.getCourse(courseId);
			generatedGroup = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId());
		} catch (SystemException e) {
			return "";
		} catch (PortalException e) {
			return "";
		}
		if (Validator.isNotNull(course.getIcon())) {
			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(course.getIcon());
				return DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
			} catch (SystemException e) {
				return "";
			} catch (PortalException e) {
				return "";
			}

		} else if(generatedGroup.getPublicLayoutSet().getLogo())
		{
			long logoId = generatedGroup.getPublicLayoutSet().getLogoId();
			return "/image/layout_set_logo?img_id="+logoId;
		}
		return "";
	}

	@JSONWebService
	public java.util.List<Course> getCoursesParents(long groupId) 
			throws SystemException, PortalException
	{
		return coursePersistence.filterFindByGroupIdParentCourseId(groupId, 0);
	}
	@JSONWebService
	public java.util.List<Course> getChildCourses(long courseId) throws SystemException, PortalException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),courseId,ActionKeys.VIEW))
		{	
			return coursePersistence.findByParentCourseId(courseId);
		
		}
		return null;
	}
	
	@JSONWebService
	public int getStudentsFromCourseCount(long courseId){
		int students = 0;
		try{
			
			Course course = courseLocalService.fetchCourse(courseId);
			if (getPermissionChecker() == null
					|| course == null
					|| !(getPermissionChecker().hasPermission(course.getGroupId(), Course.class.getName(),
							course.getCourseId(), "VIEW") || getPermissionChecker()
							.hasOwnerPermission(course.getCompanyId(), Course.class.getName(), course.getCourseId(),
									course.getUserId(), "VIEW"))) {
				return students;
			}
			
			students=courseLocalService.getStudentsFromCourseCount(courseId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return students;
		
	}
	
	@JSONWebService
	public List<Course> getPublicCoursesByCompanyId(Long companyId, int start, int end) throws PrincipalException{
		
		Long classNameId = ClassNameLocalServiceUtil.getClassNameId(Course.class.getName());
		
		if(classNameId!=null){
			try {
				DynamicQuery dq = DynamicQueryFactoryUtil.forClass(AssetEntry.class);
				dq.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
				dq.add(PropertyFactoryUtil.forName("classNameId").eq(classNameId));
				dq.add(PropertyFactoryUtil.forName("visible").eq(true));
				dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("classPK")));
				List<Long> results;
				
				if(start<0 && end<0){
					results = (List<Long>)assetEntryLocalService.dynamicQuery(dq);
				}else{
					results = (List<Long>)assetEntryLocalService.dynamicQuery(dq, start, end);
				}
				
				
				
				

				List<Course> courses = new ArrayList<Course>();
				for(Long courseId : results){
					Course course = coursePersistence.fetchByPrimaryKey(courseId);
					if (!(getPermissionChecker() == null
								|| course == null
								|| !(getPermissionChecker().hasPermission(course.getGroupId(), Course.class.getName(),
										course.getCourseId(), "VIEW") || getPermissionChecker()
										.hasOwnerPermission(course.getCompanyId(), Course.class.getName(), course.getCourseId(),
												course.getUserId(), "VIEW")))) {
							courses.add(course);
					}
				}
				
				return courses;
				
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public long getPlidActivityViewer(long groupId) throws PortalException, SystemException{
		long plid = 0;
		int i = 0;
	
		List<Layout> layouts = layoutService.getLayouts(groupId, false);
		while(plid == 0 && i < layouts.size()){
			
			if(layouts.get(i).getTypeSettings().contains(LearningActivityBaseAssetRenderer.ACTIVITY_VIEWER_PORTLET_ID)){
				plid = layouts.get(i).getPlid();
			}
			i++;
		}
		
		return plid;
	}
	
	public String getFriendlyURLTeachers(long groupId) throws PortalException, SystemException{
		String friendlyURL = null;
		int i = 0;
	
		List<Layout> layouts = layoutService.getLayouts(groupId, false);
		while(Validator.isNull(friendlyURL) && i < layouts.size()){
			
			if(layouts.get(i).getTypeSettings().contains(LearningActivityBaseAssetRenderer.TEACHERS_PORTLET_ID)){
				friendlyURL = layouts.get(i).getFriendlyURL();
			}
			i++;
		}
		
		return friendlyURL;
	}

}