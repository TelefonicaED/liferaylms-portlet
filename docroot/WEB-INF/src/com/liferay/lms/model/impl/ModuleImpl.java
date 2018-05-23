/**
 * Copyright (c)2013 Telefonica Learning Services. All rights reserved.
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

package com.liferay.lms.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The extended model implementation for the Module service. Represents a row in the &quot;Lms_Module&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.Module} interface.
 * </p>
 *
 * @author TLS
 */
public class ModuleImpl extends ModuleBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a module model instance should use the {@link com.liferay.lms.model.Module} interface instead.
	 */
	private static Log log = LogFactoryUtil.getLog(ModuleImpl.class);
	
	public ModuleImpl() {
	}
	public Module getParentModule() throws SystemException, PortalException
	{
		
		Module module = null;
		try{
			Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(this.getGroupId());
			if(course!=null)
			{
				Course parentCourse=course.getParentCourse();
				if(parentCourse!=null)
				{
					module = ModuleLocalServiceUtil.getModuleByUuidAndGroupId(this.getUuid(), parentCourse.getGroupCreatedId());
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
			log.debug(e);
		}
		return module;
	}
	
	public boolean isLocked(long userId){
		
		Date now = new Date(System.currentTimeMillis());

		Date startDate = this.getStartDate();
		Date endDate = this.getEndDate();
		
		try {
			List<Team> teams = TeamLocalServiceUtil.getUserTeams(userId, this.getGroupId());
			if(teams!=null && teams.size()>0){
				for(Team team : teams){
					Schedule schedule = null;
					try {
						schedule = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());
						if(schedule!=null){
							startDate=schedule.getStartDate();
							endDate = schedule.getEndDate();
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
		

		try {
			User user = UserLocalServiceUtil.fetchUser(userId);
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);
			if(!permissionChecker.hasPermission(this.getGroupId(), Module.class.getName(), this.getModuleId(), "ACCESS")){
				return true;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		if(!((endDate!=null&&endDate.after(now)) &&(startDate!=null&&startDate.before(now)))){
			return true;
		}
		
		if(this.getPrecedence()>0) {
			try {
				return !ModuleLocalServiceUtil.isUserPassed(this.getPrecedence(), userId);
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
	
	public List<LearningActivity> getListVisiblesLearningActivities(long companyId, PermissionChecker permissionChecker){
		try {
			List<LearningActivity> listLearningActivity = LearningActivityServiceUtil.getLearningActivitiesOfModule(getModuleId());
			List<LearningActivity> res2 = null;
			if(listLearningActivity != null && listLearningActivity.size()>0){
				res2 = new ArrayList<LearningActivity>();
				res2.addAll(listLearningActivity);
				Role siteMemberRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.SITE_MEMBER);
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
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	public List<LearningActivity> getListLearningActivities(){
		try {
			return LearningActivityServiceUtil.getLearningActivitiesOfModule(getModuleId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}