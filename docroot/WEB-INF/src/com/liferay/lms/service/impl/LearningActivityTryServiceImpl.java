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

import java.util.List;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.base.LearningActivityTryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the learning activity try remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityTryService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityTryServiceUtil} to access the learning activity try remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityTryServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityTryServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class LearningActivityTryServiceImpl
	extends LearningActivityTryServiceBaseImpl 
	{
	@JSONWebService
	public LearningActivityTry createLearningActivityTry(long actId,long userId) throws SystemException, PortalException
	{
		User user=this.getUser();
		if(user.getUserId()==userId)
		{
		LearningActivityTry lat=learningActivityTryPersistence.create(counterLocalService.increment(
				LearningActivityTry.class.getName()));
		lat.setActId(actId);
		lat.setUserId(userId);
		java.util.Date today=new java.util.Date(System.currentTimeMillis());
		lat.setStartDate(today);
		learningActivityTryPersistence.update(lat, true);
		
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		if(serviceContext!=null){
			courseResultLocalService.softInitializeByGroupIdAndUserId(serviceContext.getScopeGroupId(), serviceContext.getUserId());
		}
		
		//auditing
		if(serviceContext!=null){
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityTry.class.getName(), 
				actId, serviceContext.getUserId(), AuditConstants.ADD, null);
		}else{
			LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
			if(la!=null){
				AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityTry.class.getName(), 
						actId, la.getUserId(), AuditConstants.ADD, null);
			}
		}
		
		return lat;
		}
		return null;
	}
	@JSONWebService
	public List<LearningActivityTry> getLearningActivityTries(long actId,String login) throws SystemException, PortalException
	{
		
		User user=this.getUser();
		LearningActivity learnact=learningActivityLocalService.getLearningActivity(actId);
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		User usuario = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);	
		Course course=courseLocalService.getCourseByGroupCreatedId(learnact.getGroupId());
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ASSIGN_MEMBERS"))
		{
			return learningActivityTryLocalService.getLearningActivityTryByActUser(actId, usuario.getUserId());
		}
		return null;
	}
	
}
