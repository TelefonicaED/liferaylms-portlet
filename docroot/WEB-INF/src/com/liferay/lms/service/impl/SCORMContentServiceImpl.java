/**
 * 2013 TELEFONICA LEARNING SERVICES. All rights reserved.
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

import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.base.SCORMContentServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;

/**
 * The implementation of the s c o r m content remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.SCORMContentService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.SCORMContentServiceBaseImpl
 * @see com.liferay.lms.service.SCORMContentServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class SCORMContentServiceImpl extends SCORMContentServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.SCORMContentServiceUtil} to access the s c o r m content remote service.
	 */
	public java.util.List<SCORMContent> getSCORMContentOfGroup(long groupId) throws SystemException
	{
		return scormContentPersistence.filterFindByGroupId(groupId);
	}
}