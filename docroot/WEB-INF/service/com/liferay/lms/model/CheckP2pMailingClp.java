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

package com.liferay.lms.model;

import com.liferay.lms.service.CheckP2pMailingLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class CheckP2pMailingClp extends BaseModelImpl<CheckP2pMailing>
	implements CheckP2pMailing {
	public CheckP2pMailingClp() {
	}

	public Class<?> getModelClass() {
		return CheckP2pMailing.class;
	}

	public String getModelClassName() {
		return CheckP2pMailing.class.getName();
	}

	public long getPrimaryKey() {
		return _checkP2pId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCheckP2pId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_checkP2pId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("checkP2pId", getCheckP2pId());
		attributes.put("actId", getActId());
		attributes.put("date", getDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long checkP2pId = (Long)attributes.get("checkP2pId");

		if (checkP2pId != null) {
			setCheckP2pId(checkP2pId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}
	}

	public long getCheckP2pId() {
		return _checkP2pId;
	}

	public void setCheckP2pId(long checkP2pId) {
		_checkP2pId = checkP2pId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public BaseModel<?> getCheckP2pMailingRemoteModel() {
		return _checkP2pMailingRemoteModel;
	}

	public void setCheckP2pMailingRemoteModel(
		BaseModel<?> checkP2pMailingRemoteModel) {
		_checkP2pMailingRemoteModel = checkP2pMailingRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			CheckP2pMailingLocalServiceUtil.addCheckP2pMailing(this);
		}
		else {
			CheckP2pMailingLocalServiceUtil.updateCheckP2pMailing(this);
		}
	}

	@Override
	public CheckP2pMailing toEscapedModel() {
		return (CheckP2pMailing)Proxy.newProxyInstance(CheckP2pMailing.class.getClassLoader(),
			new Class[] { CheckP2pMailing.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		CheckP2pMailingClp clone = new CheckP2pMailingClp();

		clone.setCheckP2pId(getCheckP2pId());
		clone.setActId(getActId());
		clone.setDate(getDate());

		return clone;
	}

	public int compareTo(CheckP2pMailing checkP2pMailing) {
		long primaryKey = checkP2pMailing.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CheckP2pMailingClp checkP2pMailing = null;

		try {
			checkP2pMailing = (CheckP2pMailingClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = checkP2pMailing.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{checkP2pId=");
		sb.append(getCheckP2pId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", date=");
		sb.append(getDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.CheckP2pMailing");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>checkP2pId</column-name><column-value><![CDATA[");
		sb.append(getCheckP2pId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>date</column-name><column-value><![CDATA[");
		sb.append(getDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _checkP2pId;
	private long _actId;
	private Date _date;
	private BaseModel<?> _checkP2pMailingRemoteModel;
}