/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.service.base.AsynchronousProcessAuditLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.AsynchronousProcessAuditFinderUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the asynchronous process audit local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.AsynchronousProcessAuditLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.AsynchronousProcessAuditLocalServiceBaseImpl
 * @see com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil
 */
public class AsynchronousProcessAuditLocalServiceImpl
	extends AsynchronousProcessAuditLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil} to access the asynchronous process audit local service.
	 */
	
	
	public List<String> getDistinctTypes(long companyId){
		return asynchronousProcessAuditFinder.getDistinctTypes(companyId);
	}
	
	
	public List<AsynchronousProcessAudit> getByCompanyIdClassNameIdCreateDate(long companyId, String type,long userId, Date startDate, Date endDate, int start, int end){
		List<AsynchronousProcessAudit> asynchronousProcessAudits = new ArrayList<AsynchronousProcessAudit>();
		try{
			asynchronousProcessAudits = AsynchronousProcessAuditFinderUtil.getByCompanyIdClassNameIdCreateDate(companyId, type, userId, startDate, endDate,start,end);
		}catch(Exception e){
			e.printStackTrace();
		}
		return asynchronousProcessAudits;
	}
	
	public int countByCompanyIdClassNameIdCreateDate(long companyId, String type, long userId, Date startDate, Date endDate){
		int asynchronousProcessAudits = 0;
		try{
			asynchronousProcessAudits = AsynchronousProcessAuditFinderUtil.countByCompanyIdClassNameIdCreateDate(companyId, type, userId, startDate, endDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return asynchronousProcessAudits;
	}
	
	public AsynchronousProcessAudit addAsynchronousProcessAudit(long companyId, long userId, String classNameValue, String type){
		AsynchronousProcessAudit asynchronousProcessAudit = null;
		try{
			asynchronousProcessAudit = asynchronousProcessAuditPersistence.create(counterLocalService.increment(AsynchronousProcessAudit.class.getName()));
			asynchronousProcessAudit.setCompanyId(companyId);
			asynchronousProcessAudit.setType(type);
			asynchronousProcessAudit.setUserId(userId);
			asynchronousProcessAudit.setClassName(classNameValue);
			asynchronousProcessAudit.setCreateDate(new Date());
			asynchronousProcessAudit.setStatus(LmsConstant.STATUS_NOT_STARTED);
			
			asynchronousProcessAudit =asynchronousProcessAuditPersistence.update(asynchronousProcessAudit, true);
			
			
			
		}catch(SystemException e){
			e.printStackTrace();
		}
		return asynchronousProcessAudit;
	}
	
	public AsynchronousProcessAudit addAsynchronousProcessAudit(long companyId, long userId, String classNameValue, long classNameId, long classPK, String type){
		AsynchronousProcessAudit asynchronousProcessAudit = null;
		try{
			asynchronousProcessAudit = asynchronousProcessAuditPersistence.create(counterLocalService.increment(AsynchronousProcessAudit.class.getName()));
			asynchronousProcessAudit.setCompanyId(companyId);
			asynchronousProcessAudit.setType(type);
			asynchronousProcessAudit.setUserId(userId);
			asynchronousProcessAudit.setClassName(classNameValue);
			asynchronousProcessAudit.setCreateDate(new Date());
			asynchronousProcessAudit.setStatus(LmsConstant.STATUS_NOT_STARTED);
			
			asynchronousProcessAudit =asynchronousProcessAuditPersistence.update(asynchronousProcessAudit, true);
			
			
			
		}catch(SystemException e){
			e.printStackTrace();
		}
		return asynchronousProcessAudit;
	}
	
	public AsynchronousProcessAudit updateProcessStatus(AsynchronousProcessAudit asynchronousProcessAudit, Date endDate, int status, String statusMessage) {
		try{
			if(asynchronousProcessAudit!=null){
				asynchronousProcessAudit.setStatus(status);
				asynchronousProcessAudit.setEndDate(endDate);
				asynchronousProcessAudit.setStatusMessage(statusMessage);
				asynchronousProcessAudit =asynchronousProcessAuditPersistence.update(asynchronousProcessAudit, true);
				
			}
		}catch(SystemException e){
			e.printStackTrace();
		}
		return asynchronousProcessAudit;
	}
}