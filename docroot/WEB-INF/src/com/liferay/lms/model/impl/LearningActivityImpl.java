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

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.descriptionfilter.DescriptionFilterRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * The extended model implementation for the LearningActivity service. Represents a row in the &quot;Lms_LearningActivity&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.LearningActivity} interface.
 * </p>
 *
 * @author TLS
 */
public class LearningActivityImpl extends LearningActivityBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a learning activity model instance should use the {@link com.liferay.lms.model.LearningActivity} interface instead.
	 */

	@Override
	public Date getStartdate() {
		Date startDate = super.getStartdate();
		if(Validator.isNull(startDate)) {
			try {
				Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
				if(Validator.isNotNull(module)) {
					return module.getStartDate();
				}
			}
			catch(SystemException systemException) {}

			return null;
		}
		else {
			return startDate;
		}
	}

	@Override
	public boolean isNullStartDate() {
		try {
			if(super.getStartdate()==null){
				return true;
			}
			
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			if(Validator.isNotNull(module)) {
				return module.getStartDate().equals(super.getStartdate());
			}else{
				return true;
			}
		}
		catch(SystemException systemException) {
			return true;
		}
	}

	@Override
	public Date getEnddate() {
		Date endDate = super.getEnddate();
		if(Validator.isNull(endDate)) {
			try {			
				Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
				if(Validator.isNotNull(module)) {
					return module.getEndDate();
				}
			}
			catch(SystemException systemException) {}

			return null;
		}
		else {
			return endDate;
		}
	}

	@Override
	public boolean isNullEndDate() {
		try {
			if(super.getEnddate()==null){
				return true;
			}
			
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			if(Validator.isNotNull(module)) {
				return module.getEndDate().equals(super.getEnddate());
			}else{
				return true;
			}
		}
		catch(SystemException systemException) {
			return true;
		}
	}
	public String getDescriptionFiltered(Locale locale, boolean useDefault)
    {
		return DescriptionFilterRegistry.filter(this.getDescription(locale, useDefault));
    }
    public String getDescriptionFiltered(String languageId, boolean useDefault)
    {
    	return DescriptionFilterRegistry.filter(this.getDescription(languageId, useDefault));
    		  
    }
	
	public Module getModule(){
		try {
			return ModuleLocalServiceUtil.getModule(getModuleId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isLocked(User user, PermissionChecker permissionChecker){
		if(!permissionChecker.hasPermission(this.getGroupId(),LearningActivity.class.getName(),	this.getActId(), ActionKeys.VIEW)){
			return true;
		}
		
		Date startDate = this.getStartdate();
		Date endDate = this.getEnddate();
		
		Date now = new Date(System.currentTimeMillis());
		
		try {
			List<Team> teams = TeamLocalServiceUtil.getUserTeams(user.getUserId(), this.getGroupId());
			if(teams!=null && teams.size()>0){
				Schedule schedule = null;
				for(Team team : teams){
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
		
		if((endDate!=null&&endDate.before(now)) ||(startDate!=null&&startDate.after(now))){
			return true;
		}
		if(this.getPrecedence()!=0){
			try {
				return !LearningActivityResultLocalServiceUtil.userPassed(this.getPrecedence(), user.getUserId());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;			
	}
	
	public boolean isLocked(User user){
		PermissionChecker permissionChecker = null;
		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.isLocked(user, permissionChecker);
	
	}
	
	public boolean isLocked(long userId){
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return this.isLocked(user);

	}
	
	/**
	 * Comprueba si se peude accceder a una actividad
	 * @param viewActivityFinish Si la actividad deja acceder coon el modo observador
	 * @return
	 */
	
	public boolean canAccess(boolean viewActivityFinish, User user, PermissionChecker permissionChecker){
		boolean canAccessLock = CourseLocalServiceUtil.canAccessLock(this.getGroupId(), user);
		boolean hasPermissionAccessCourseFinished = false;
		
		Course course;
		try {
			course = CourseLocalServiceUtil.fetchByGroupCreatedId(this.getGroupId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if(viewActivityFinish)
			hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(this.getCompanyId(), this.getGroupId(), course.getCourseId(), user.getUserId());
		
		return canAccess(viewActivityFinish, user, permissionChecker, canAccessLock, course, hasPermissionAccessCourseFinished);
		
	}
	
	/**
	 * Comprueba si se peude accceder a una actividad
	 * @param viewActivityFinish Si la actividad deja acceder coon el modo observador
	 * @return
	 */
	
	public boolean canAccess(boolean viewActivityFinish, User user, PermissionChecker permissionChecker, boolean canAccessLock, Course course, boolean hasPermissionAccessCourseFinished){
		if(canAccessLock){
			return true;
		}

		
		if(viewActivityFinish && hasPermissionAccessCourseFinished){
			return true;
		}

		//Primero comprobamos bloqueo de curso
		if(!course.isLocked(user, permissionChecker)){
			
			//Ahora comprobamos que no tengas bloqueado el módulo y si no lo tengo bloqueado ya comprobamos los bloqueos de la actividad
			Module module;
			try {
				module = ModuleLocalServiceUtil.getModule(this.getModuleId());
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		
			boolean activityIsLocked = isLocked(user, permissionChecker);
			
			if((!module.isLocked(user.getUserId()) && !activityIsLocked)
					|| permissionChecker.hasPermission( this.getGroupId(),LearningActivity.class.getName(), this.getActId(), ActionKeys.UPDATE)){
				return true;
			}
		}
		return false;		
	}
	
	public LearningActivityType getLearningActivityType(){
		return new LearningActivityTypeRegistry().getLearningActivityType(getTypeId());
	}
	
	public String getExtraContentValue(String key){
		try {
			return LearningActivityLocalServiceUtil.getExtraContentValue(this, key, null);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}