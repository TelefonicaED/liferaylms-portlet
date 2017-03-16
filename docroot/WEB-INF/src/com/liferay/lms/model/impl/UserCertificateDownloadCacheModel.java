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

package com.liferay.lms.model.impl;

import com.liferay.lms.model.UserCertificateDownload;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing UserCertificateDownload in entity cache.
 *
 * @author TLS
 * @see UserCertificateDownload
 * @generated
 */
public class UserCertificateDownloadCacheModel implements CacheModel<UserCertificateDownload>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{userId=");
		sb.append(userId);
		sb.append(", courseId=");
		sb.append(courseId);
		sb.append(", competenceId=");
		sb.append(competenceId);
		sb.append(", downloadDate=");
		sb.append(downloadDate);
		sb.append("}");

		return sb.toString();
	}

	public UserCertificateDownload toEntityModel() {
		UserCertificateDownloadImpl userCertificateDownloadImpl = new UserCertificateDownloadImpl();

		userCertificateDownloadImpl.setUserId(userId);
		userCertificateDownloadImpl.setCourseId(courseId);
		userCertificateDownloadImpl.setCompetenceId(competenceId);

		if (downloadDate == Long.MIN_VALUE) {
			userCertificateDownloadImpl.setDownloadDate(null);
		}
		else {
			userCertificateDownloadImpl.setDownloadDate(new Date(downloadDate));
		}

		userCertificateDownloadImpl.resetOriginalValues();

		return userCertificateDownloadImpl;
	}

	public long userId;
	public long courseId;
	public long competenceId;
	public long downloadDate;
}