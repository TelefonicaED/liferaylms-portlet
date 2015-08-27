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
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.base.LearningActivityResultServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;

/**
 * The implementation of the learning activity result remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityResultService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityResultServiceUtil} to access the learning activity result remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityResultServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityResultServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class LearningActivityResultServiceImpl
	extends LearningActivityResultServiceBaseImpl 
	{
	@JSONWebService
	public LearningActivityResult getByActId(long actId) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.getByActIdAndUserId(actId, user.getUserId());
	}
	@JSONWebService
	public LearningActivityResult getByActIdAndUser(long actId,String login) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.getByActIdAndUserId(actId, user.getUserId());
	}
	@JSONWebService
	public boolean userPassed(long actId) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.userPassed(actId, user.getUserId());
	}
	@JSONWebService
	public boolean userLoginPassed(long actId,String login) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.userPassed(actId, user.getUserId());
	}
	
	public LearningActivityResult update(long latId, long result, String tryResultData) throws PortalException, SystemException
	{
		User user=this.getUser();
		LearningActivityResult lar = learningActivityResultLocalService.update(latId, result, tryResultData, user.getUserId());

		//auditing
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		if(serviceContext!=null){
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
				latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
		}else{
			if(lar!=null){
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(lar.getActId());
				if(la!=null){
					AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
							latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
				}
			}
		}
		
		return lar;
	}
	
	public LearningActivityResult update(long latId, String tryResultData) throws PortalException, SystemException
	{
		User user=this.getUser();
		LearningActivityResult lar = learningActivityResultLocalService.update(latId, tryResultData, user.getUserId());

		//auditing
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		if(serviceContext!=null){
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
				latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
		}else{
			if(lar!=null){
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(lar.getActId());
				if(la!=null){
					AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
							latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
				}
			}
		}
		
		return lar;
	}
	
	public LearningActivityResult update(long latId, String tryResultData, String imsmanifest) throws PortalException, SystemException
	{
		User user=this.getUser();
		LearningActivityResult lar = learningActivityResultLocalService.update(latId, tryResultData, imsmanifest, user.getUserId());

		//auditing
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		if(serviceContext!=null){
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
				latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
		}else{
			if(lar!=null){
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(lar.getActId());
				if(la!=null){
					AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
							latId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
				}
			}
		}
		
		return lar;
	}
	public LearningActivityResult updateFinishTry(long latId, String tryResultData, String imsmanifest) throws PortalException, SystemException
	{
		LearningActivityTry learningActivityTry = learningActivityTryLocalService.getLearningActivityTry(latId);
		learningActivityTry.setEndDate(new java.util.Date(System.currentTimeMillis()));
		learningActivityTryLocalService.updateLearningActivityTry(learningActivityTry);
		
		return update( latId,  tryResultData,  imsmanifest);
	}
}