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

import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class CourseCompetenceClp extends BaseModelImpl<CourseCompetence>
	implements CourseCompetence {
	public CourseCompetenceClp() {
	}

	public Class<?> getModelClass() {
		return CourseCompetence.class;
	}

	public String getModelClassName() {
		return CourseCompetence.class.getName();
	}

	public long getPrimaryKey() {
		return _CourcompetenceId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCourcompetenceId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_CourcompetenceId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CourcompetenceId", getCourcompetenceId());
		attributes.put("courseId", getCourseId());
		attributes.put("competenceId", getCompetenceId());
		attributes.put("condition", getCondition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CourcompetenceId = (Long)attributes.get("CourcompetenceId");

		if (CourcompetenceId != null) {
			setCourcompetenceId(CourcompetenceId);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		Long competenceId = (Long)attributes.get("competenceId");

		if (competenceId != null) {
			setCompetenceId(competenceId);
		}

		Boolean condition = (Boolean)attributes.get("condition");

		if (condition != null) {
			setCondition(condition);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCourcompetenceId() {
		return _CourcompetenceId;
	}

	public void setCourcompetenceId(long CourcompetenceId) {
		_CourcompetenceId = CourcompetenceId;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	public long getCompetenceId() {
		return _competenceId;
	}

	public void setCompetenceId(long competenceId) {
		_competenceId = competenceId;
	}

	public boolean getCondition() {
		return _condition;
	}

	public boolean isCondition() {
		return _condition;
	}

	public void setCondition(boolean condition) {
		_condition = condition;
	}

	public BaseModel<?> getCourseCompetenceRemoteModel() {
		return _courseCompetenceRemoteModel;
	}

	public void setCourseCompetenceRemoteModel(
		BaseModel<?> courseCompetenceRemoteModel) {
		_courseCompetenceRemoteModel = courseCompetenceRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			CourseCompetenceLocalServiceUtil.addCourseCompetence(this);
		}
		else {
			CourseCompetenceLocalServiceUtil.updateCourseCompetence(this);
		}
	}

	@Override
	public CourseCompetence toEscapedModel() {
		return (CourseCompetence)Proxy.newProxyInstance(CourseCompetence.class.getClassLoader(),
			new Class[] { CourseCompetence.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		CourseCompetenceClp clone = new CourseCompetenceClp();

		clone.setUuid(getUuid());
		clone.setCourcompetenceId(getCourcompetenceId());
		clone.setCourseId(getCourseId());
		clone.setCompetenceId(getCompetenceId());
		clone.setCondition(getCondition());

		return clone;
	}

	public int compareTo(CourseCompetence courseCompetence) {
		long primaryKey = courseCompetence.getPrimaryKey();

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

		CourseCompetenceClp courseCompetence = null;

		try {
			courseCompetence = (CourseCompetenceClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = courseCompetence.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", CourcompetenceId=");
		sb.append(getCourcompetenceId());
		sb.append(", courseId=");
		sb.append(getCourseId());
		sb.append(", competenceId=");
		sb.append(getCompetenceId());
		sb.append(", condition=");
		sb.append(getCondition());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.CourseCompetence");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CourcompetenceId</column-name><column-value><![CDATA[");
		sb.append(getCourcompetenceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>courseId</column-name><column-value><![CDATA[");
		sb.append(getCourseId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>competenceId</column-name><column-value><![CDATA[");
		sb.append(getCompetenceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>condition</column-name><column-value><![CDATA[");
		sb.append(getCondition());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _CourcompetenceId;
	private long _courseId;
	private long _competenceId;
	private boolean _condition;
	private BaseModel<?> _courseCompetenceRemoteModel;
}