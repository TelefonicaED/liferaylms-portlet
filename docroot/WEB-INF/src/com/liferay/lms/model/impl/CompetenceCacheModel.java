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

import com.liferay.lms.model.Competence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Competence in entity cache.
 *
 * @author TLS
 * @see Competence
 * @generated
 */
public class CompetenceCacheModel implements CacheModel<Competence>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", competenceId=");
		sb.append(competenceId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", page=");
		sb.append(page);
		sb.append(", generateCertificate=");
		sb.append(generateCertificate);
		sb.append(", diplomaTemplate=");
		sb.append(diplomaTemplate);
		sb.append("}");

		return sb.toString();
	}

	public Competence toEntityModel() {
		CompetenceImpl competenceImpl = new CompetenceImpl();

		if (uuid == null) {
			competenceImpl.setUuid(StringPool.BLANK);
		}
		else {
			competenceImpl.setUuid(uuid);
		}

		competenceImpl.setCompetenceId(competenceId);
		competenceImpl.setCompanyId(companyId);
		competenceImpl.setGroupId(groupId);
		competenceImpl.setUserId(userId);
		competenceImpl.setStatus(status);
		competenceImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			competenceImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			competenceImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			competenceImpl.setStatusDate(null);
		}
		else {
			competenceImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			competenceImpl.setTitle(StringPool.BLANK);
		}
		else {
			competenceImpl.setTitle(title);
		}

		if (description == null) {
			competenceImpl.setDescription(StringPool.BLANK);
		}
		else {
			competenceImpl.setDescription(description);
		}

		if (page == null) {
			competenceImpl.setPage(StringPool.BLANK);
		}
		else {
			competenceImpl.setPage(page);
		}

		competenceImpl.setGenerateCertificate(generateCertificate);

		if (diplomaTemplate == null) {
			competenceImpl.setDiplomaTemplate(StringPool.BLANK);
		}
		else {
			competenceImpl.setDiplomaTemplate(diplomaTemplate);
		}

		competenceImpl.resetOriginalValues();

		return competenceImpl;
	}

	public String uuid;
	public long competenceId;
	public long companyId;
	public long groupId;
	public long userId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public String page;
	public boolean generateCertificate;
	public String diplomaTemplate;
}