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

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.base.LearningActivityServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

/**
 * The implementation of the learning activity remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityServiceUtil} to access the learning activity remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class LearningActivityServiceImpl extends LearningActivityServiceBaseImpl 
{
	public java.util.List<LearningActivity> getLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.filterFindByg(groupId, 0, 1000);
	}
	@JSONWebService
	public java.util.List<LearningActivity> getLearningActivitiesOfModule(long moduleId) throws SystemException
	{
		
		return learningActivityPersistence.findBym(moduleId, 0, 1000);
		
	}
	public void deleteLearningactivity (LearningActivity lernact) throws SystemException,
	PortalException {
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.DELETE))
		{
			learningActivityLocalService.deleteLearningactivity(lernact);

			//auditing
			if(lernact!=null){
				AuditingLogFactory.audit(lernact.getCompanyId(), lernact.getGroupId(), Course.class.getName(), lernact.getPrimaryKey(), lernact.getUserId(), AuditConstants.DELETE, null);
				SocialActivityLocalServiceUtil.addActivity(getUserId(), lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(), com.liferay.lms.social.LearningActivityKeys.DELETE_ENTRY, "", lernact.getUserId());
				
			}
			
		}
	}
	public void deleteLearningactivity (long actId) throws SystemException,
	PortalException {
		LearningActivity lernact=this.getLearningActivity(actId);
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.DELETE)|| getPermissionChecker().hasOwnerPermission(lernact.getCompanyId(), LearningActivity.class.getName(), lernact.getActId(),lernact.getUserId(),
						ActionKeys.DELETE))
		{
			learningActivityLocalService.deleteLearningactivity(lernact);

			//auditing
			if(lernact!=null){
				AuditingLogFactory.audit(lernact.getCompanyId(), lernact.getGroupId(), Course.class.getName(), lernact.getPrimaryKey(), lernact.getUserId(), AuditConstants.DELETE, null);
				SocialActivityLocalServiceUtil.addActivity(getUserId(), lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(), com.liferay.lms.social.LearningActivityKeys.DELETE_ENTRY, "", lernact.getUserId());
				
			}
		}
	}
	@JSONWebService
	public LearningActivity getLearningActivity(long actId) throws PortalException, SystemException 
	{
   		LearningActivity lernact=learningActivityLocalService.getLearningActivity(actId);	 	

	   	if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.VIEW))
		{
	   	 	return lernact;
		}
		else
		{
			return null;
		}
	}
	public LearningActivity addLearningActivity (String title, String description, java.util.Date createDate,java.util.Date startDate,java.util.Date endDate, int typeId,long tries,int passpuntuation,long moduleId,
			ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		if( getPermissionChecker().hasPermission(serviceContext.getScopeGroupId(),  LearningActivity.class.getName(),0,ActionKeys.ADD_ENTRY))
		{
			LearningActivity lernact = learningActivityLocalService.addLearningActivity(title, description, createDate, startDate, endDate, typeId, tries, passpuntuation,moduleId, "",  null, null, serviceContext);

			//auditing
			if(lernact!=null){
				AuditingLogFactory.audit(lernact.getCompanyId(), lernact.getGroupId(), Course.class.getName(), lernact.getPrimaryKey(), lernact.getUserId(), AuditConstants.ADD, null);
				SocialActivityLocalServiceUtil.addActivity(serviceContext.getUserId(), lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(), com.liferay.lms.social.LearningActivityKeys.ADD_ENTRY, "", lernact.getUserId());
				
			}
			
			return lernact;
		}
		else
		{
			return null;
		}
	}
	public LearningActivity modLearningActivity (LearningActivity lernact, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
			lernact =  learningActivityLocalService.modLearningActivity(lernact, serviceContext);
			
			//auditing
			if(lernact!=null){
				AuditingLogFactory.audit(lernact.getCompanyId(), lernact.getGroupId(), Course.class.getName(), lernact.getPrimaryKey(), lernact.getUserId(), AuditConstants.UPDATE, null);
				SocialActivityLocalServiceUtil.addActivity(serviceContext.getUserId(), lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(), com.liferay.lms.social.LearningActivityKeys.UPDATE_ENTRY, "", lernact.getUserId());
				
			}
		
			return lernact;
		}
		else
		{
			return null;
		}
	}
	public LearningActivity modLearningActivity (LearningActivity lernact)
			throws SystemException, PortalException {
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
		return learningActivityLocalService.modLearningActivity(lernact);
		}
		else
		{
			return null;
		}
	}
	public LearningActivity modLearningActivity (long actId,String title, String description, java.util.Date createDate,java.util.Date startDate,java.util.Date endDate, int typeId,long tries,int passpuntuation,long moduleId,
			ServiceContext serviceContext) throws SystemException, 
			PortalException
	{
		if( getPermissionChecker().hasPermission(serviceContext.getScopeGroupId(), LearningActivity.class.getName(), actId,
				ActionKeys.UPDATE))
		{
			LearningActivity lernact = learningActivityLocalService.modLearningActivity(actId, title, description, createDate, startDate, endDate, typeId, tries,passpuntuation, moduleId,  "",  null, null, serviceContext);
			
			//auditing
			if(lernact!=null){
				AuditingLogFactory.audit(lernact.getCompanyId(), lernact.getGroupId(), Course.class.getName(), lernact.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
				SocialActivityLocalServiceUtil.addActivity(serviceContext.getUserId(), lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(), com.liferay.lms.social.LearningActivityKeys.UPDATE_ENTRY, "", lernact.getUserId());
			}
		
			return lernact;
		}
		else
		{
			return null;
		}
	}
	@JSONWebService
	public boolean isLocked(long actId) throws Exception
	{
		User user=this.getUser();
		return learningActivityLocalService.islocked(actId, user.getUserId());
	}
	
}