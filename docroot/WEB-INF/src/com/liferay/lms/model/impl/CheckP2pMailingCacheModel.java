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

import com.liferay.lms.model.CheckP2pMailing;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing CheckP2pMailing in entity cache.
 *
 * @author TLS
 * @see CheckP2pMailing
 * @generated
 */
public class CheckP2pMailingCacheModel implements CacheModel<CheckP2pMailing>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{checkP2pId=");
		sb.append(checkP2pId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", date=");
		sb.append(date);
		sb.append("}");

		return sb.toString();
	}

	public CheckP2pMailing toEntityModel() {
		CheckP2pMailingImpl checkP2pMailingImpl = new CheckP2pMailingImpl();

		checkP2pMailingImpl.setCheckP2pId(checkP2pId);
		checkP2pMailingImpl.setActId(actId);

		if (date == Long.MIN_VALUE) {
			checkP2pMailingImpl.setDate(null);
		}
		else {
			checkP2pMailingImpl.setDate(new Date(date));
		}

		checkP2pMailingImpl.resetOriginalValues();

		return checkP2pMailingImpl;
	}

	public long checkP2pId;
	public long actId;
	public long date;
}