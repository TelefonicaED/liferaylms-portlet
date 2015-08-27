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

import com.liferay.lms.model.P2pActivityCorrections;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing P2pActivityCorrections in entity cache.
 *
 * @author TLS
 * @see P2pActivityCorrections
 * @generated
 */
public class P2pActivityCorrectionsCacheModel implements CacheModel<P2pActivityCorrections>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", p2pActivityCorrectionsId=");
		sb.append(p2pActivityCorrectionsId);
		sb.append(", p2pActivityId=");
		sb.append(p2pActivityId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", description=");
		sb.append(description);
		sb.append(", date=");
		sb.append(date);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", result=");
		sb.append(result);
		sb.append("}");

		return sb.toString();
	}

	public P2pActivityCorrections toEntityModel() {
		P2pActivityCorrectionsImpl p2pActivityCorrectionsImpl = new P2pActivityCorrectionsImpl();

		if (uuid == null) {
			p2pActivityCorrectionsImpl.setUuid(StringPool.BLANK);
		}
		else {
			p2pActivityCorrectionsImpl.setUuid(uuid);
		}

		p2pActivityCorrectionsImpl.setP2pActivityCorrectionsId(p2pActivityCorrectionsId);
		p2pActivityCorrectionsImpl.setP2pActivityId(p2pActivityId);
		p2pActivityCorrectionsImpl.setUserId(userId);
		p2pActivityCorrectionsImpl.setActId(actId);

		if (description == null) {
			p2pActivityCorrectionsImpl.setDescription(StringPool.BLANK);
		}
		else {
			p2pActivityCorrectionsImpl.setDescription(description);
		}

		if (date == Long.MIN_VALUE) {
			p2pActivityCorrectionsImpl.setDate(null);
		}
		else {
			p2pActivityCorrectionsImpl.setDate(new Date(date));
		}

		p2pActivityCorrectionsImpl.setFileEntryId(fileEntryId);
		p2pActivityCorrectionsImpl.setResult(result);

		p2pActivityCorrectionsImpl.resetOriginalValues();

		return p2pActivityCorrectionsImpl;
	}

	public String uuid;
	public long p2pActivityCorrectionsId;
	public long p2pActivityId;
	public long userId;
	public long actId;
	public String description;
	public long date;
	public long fileEntryId;
	public long result;
}