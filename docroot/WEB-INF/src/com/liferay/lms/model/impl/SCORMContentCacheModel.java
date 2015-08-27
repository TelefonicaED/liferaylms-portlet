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

import com.liferay.lms.model.SCORMContent;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SCORMContent in entity cache.
 *
 * @author TLS
 * @see SCORMContent
 * @generated
 */
public class SCORMContentCacheModel implements CacheModel<SCORMContent>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", scormId=");
		sb.append(scormId);
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
		sb.append(", index=");
		sb.append(index);
		sb.append(", ciphered=");
		sb.append(ciphered);
		sb.append("}");

		return sb.toString();
	}

	public SCORMContent toEntityModel() {
		SCORMContentImpl scormContentImpl = new SCORMContentImpl();

		if (uuid == null) {
			scormContentImpl.setUuid(StringPool.BLANK);
		}
		else {
			scormContentImpl.setUuid(uuid);
		}

		scormContentImpl.setScormId(scormId);
		scormContentImpl.setCompanyId(companyId);
		scormContentImpl.setGroupId(groupId);
		scormContentImpl.setUserId(userId);
		scormContentImpl.setStatus(status);
		scormContentImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			scormContentImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			scormContentImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			scormContentImpl.setStatusDate(null);
		}
		else {
			scormContentImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			scormContentImpl.setTitle(StringPool.BLANK);
		}
		else {
			scormContentImpl.setTitle(title);
		}

		if (description == null) {
			scormContentImpl.setDescription(StringPool.BLANK);
		}
		else {
			scormContentImpl.setDescription(description);
		}

		if (index == null) {
			scormContentImpl.setIndex(StringPool.BLANK);
		}
		else {
			scormContentImpl.setIndex(index);
		}

		scormContentImpl.setCiphered(ciphered);

		scormContentImpl.resetOriginalValues();

		return scormContentImpl;
	}

	public String uuid;
	public long scormId;
	public long companyId;
	public long groupId;
	public long userId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public String index;
	public boolean ciphered;
}