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

import com.liferay.lms.NoSuchUserCertificateDownloadException;
import com.liferay.lms.model.UserCertificateDownload;
import com.liferay.lms.service.base.UserCertificateDownloadLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.UserCertificateDownloadPK;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the user certificate download local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.UserCertificateDownloadLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.UserCertificateDownloadLocalServiceBaseImpl
 * @see com.liferay.lms.service.UserCertificateDownloadLocalServiceUtil
 */
public class UserCertificateDownloadLocalServiceImpl
	extends UserCertificateDownloadLocalServiceBaseImpl {
	
	private static Log log = LogFactoryUtil.getLog(UserCertificateDownloadLocalServiceImpl.class);
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.UserCertificateDownloadLocalServiceUtil} to access the user certificate download local service.
	 */
	
	public void addUserCertificateDownload(long userId, long courseId, long competenceId) throws SystemException {
		
		UserCertificateDownloadPK userCertificateDownloadPK = new UserCertificateDownloadPK(userId, courseId, competenceId);
		
		UserCertificateDownload userCertificationDownload = null;
		try {
			userCertificationDownload = userCertificateDownloadPersistence.findByPrimaryKey(userCertificateDownloadPK);
			
			log.info("El usuario " + userId + " ya se habia descargado con anterioridad el diploma, fecha=" + userCertificationDownload.getDownloadDate());
		} catch (NoSuchUserCertificateDownloadException e) {
			// Si no se encuentra, se a�ade
			userCertificationDownload = userCertificateDownloadPersistence.create(userCertificateDownloadPK);
			userCertificationDownload.setDownloadDate(new Date());
			
			userCertificateDownloadPersistence.update(userCertificationDownload, Boolean.TRUE);
		}
		
	}
	
}