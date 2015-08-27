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

import java.util.Date;
import java.util.List;

import com.liferay.lms.model.AuditEntry;
import com.liferay.lms.service.base.AuditEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * The implementation of the audit entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.AuditEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.AuditEntryLocalServiceBaseImpl
 * @see com.liferay.lms.service.AuditEntryLocalServiceUtil
 */
public class AuditEntryLocalServiceImpl extends AuditEntryLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.AuditEntryLocalServiceUtil} to access the audit entry local service.
	 */
	public void addAuditEntry(long companyId,long groupId, String className,long classPK,long associationClassPK,long userId,String action,String extraData) throws SystemException
	{
		AuditEntry auditEntry=auditEntryPersistence.create(counterLocalService.increment(AuditEntry.class.getName()));
		auditEntry.setAuditDate(new java.util.Date(System.currentTimeMillis()));
		auditEntry.setCompanyId(companyId);
		auditEntry.setGroupId(groupId);
		auditEntry.setClassname(className);
		auditEntry.setUserId(userId);
		auditEntry.setAction(action);
		auditEntry.setExtradata(extraData);
		auditEntry.setClassPK(classPK);
		auditEntry.setAssociationClassPK(associationClassPK);
		auditEntryPersistence.update(auditEntry, true);
		
	}

	public void addAuditEntry(long companyId,long groupId, String className,long classPK,long userId,String action,String extraData) throws SystemException
	{
		addAuditEntry(companyId, groupId, className, classPK, GetterUtil.DEFAULT_LONG, userId, action, extraData);
	}

	public List<AuditEntry> search(long companyId, long groupId,String className,long classPK, long userId, Date startDate,Date endDate, int start, int end) throws SystemException
	{
		DynamicQuery dq=auditEntryLocalService.dynamicQuery();
		Criterion criterion=PropertyFactoryUtil.forName("companyId").eq(companyId);
		dq.add(criterion);
		if(groupId>0)
		{
			criterion=PropertyFactoryUtil.forName("groupId").eq(groupId);
			dq.add(criterion);
		}
		if(userId>0)
		{
			criterion=PropertyFactoryUtil.forName("userId").eq(userId);
			dq.add(criterion);
		}
		if(startDate!=null)
		{
			criterion=PropertyFactoryUtil.forName("auditDate").ge(startDate);
			dq.add(criterion);
		}
		if(endDate!=null)
		{
			criterion=PropertyFactoryUtil.forName("auditDate").le(endDate);
			dq.add(criterion);
		}
		if(className!=null)
		{
			criterion=PropertyFactoryUtil.forName("classname").eq(className);
			dq.add(criterion);
			if(classPK>0)
			{
				criterion=PropertyFactoryUtil.forName("classPK").eq(classPK);
				dq.add(criterion);
			}
		}
		dq.addOrder(OrderFactoryUtil.desc("auditDate"));
		return ((List<AuditEntry>)auditEntryLocalService.dynamicQuery(dq, start, end));
	}
	public long searchCount(long companyId, long groupId,String className,long classPK, long userId, Date startDate,Date endDate, int start, int end) throws SystemException
	{
		DynamicQuery dq=auditEntryLocalService.dynamicQuery();
		Criterion criterion=PropertyFactoryUtil.forName("companyId").eq(companyId);
		dq.add(criterion);
		if(groupId>0)
		{
			criterion=PropertyFactoryUtil.forName("groupId").eq(groupId);
			dq.add(criterion);
		}
		if(userId>0)
		{
			criterion=PropertyFactoryUtil.forName("userId").eq(userId);
			dq.add(criterion);
		}
		if(startDate!=null)
		{
			criterion=PropertyFactoryUtil.forName("auditDate").ge(startDate);
			dq.add(criterion);
		}
		if(endDate!=null)
		{
			criterion=PropertyFactoryUtil.forName("auditDate").le(endDate);
			dq.add(criterion);
		}
		if(className!=null)
		{
			criterion=PropertyFactoryUtil.forName("classname").eq(className);
			dq.add(criterion);
			if(classPK>0)
			{
				criterion=PropertyFactoryUtil.forName("classPK").eq(classPK);
				dq.add(criterion);
			}
		}
		return auditEntryLocalService.dynamicQueryCount(dq);
	}
}