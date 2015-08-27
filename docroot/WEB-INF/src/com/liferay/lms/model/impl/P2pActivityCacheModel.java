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

import com.liferay.lms.model.P2pActivity;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing P2pActivity in entity cache.
 *
 * @author TLS
 * @see P2pActivity
 * @generated
 */
public class P2pActivityCacheModel implements CacheModel<P2pActivity>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", p2pActivityId=");
		sb.append(p2pActivityId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", countCorrections=");
		sb.append(countCorrections);
		sb.append(", description=");
		sb.append(description);
		sb.append(", date=");
		sb.append(date);
		sb.append(", asignationsCompleted=");
		sb.append(asignationsCompleted);
		sb.append("}");

		return sb.toString();
	}

	public P2pActivity toEntityModel() {
		P2pActivityImpl p2pActivityImpl = new P2pActivityImpl();

		if (uuid == null) {
			p2pActivityImpl.setUuid(StringPool.BLANK);
		}
		else {
			p2pActivityImpl.setUuid(uuid);
		}

		p2pActivityImpl.setP2pActivityId(p2pActivityId);
		p2pActivityImpl.setActId(actId);
		p2pActivityImpl.setUserId(userId);
		p2pActivityImpl.setFileEntryId(fileEntryId);
		p2pActivityImpl.setCountCorrections(countCorrections);

		if (description == null) {
			p2pActivityImpl.setDescription(StringPool.BLANK);
		}
		else {
			p2pActivityImpl.setDescription(description);
		}

		if (date == Long.MIN_VALUE) {
			p2pActivityImpl.setDate(null);
		}
		else {
			p2pActivityImpl.setDate(new Date(date));
		}

		p2pActivityImpl.setAsignationsCompleted(asignationsCompleted);

		p2pActivityImpl.resetOriginalValues();

		return p2pActivityImpl;
	}

	public String uuid;
	public long p2pActivityId;
	public long actId;
	public long userId;
	public long fileEntryId;
	public long countCorrections;
	public String description;
	public long date;
	public boolean asignationsCompleted;
}