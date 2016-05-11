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

import com.liferay.lms.service.SurveyResultLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class SurveyResultClp extends BaseModelImpl<SurveyResult>
	implements SurveyResult {
	public SurveyResultClp() {
	}

	public Class<?> getModelClass() {
		return SurveyResult.class;
	}

	public String getModelClassName() {
		return SurveyResult.class.getName();
	}

	public long getPrimaryKey() {
		return _surveyResultId;
	}

	public void setPrimaryKey(long primaryKey) {
		setSurveyResultId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_surveyResultId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("surveyResultId", getSurveyResultId());
		attributes.put("actId", getActId());
		attributes.put("latId", getLatId());
		attributes.put("questionId", getQuestionId());
		attributes.put("answerId", getAnswerId());
		attributes.put("userId", getUserId());
		attributes.put("freeAnswer", getFreeAnswer());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long surveyResultId = (Long)attributes.get("surveyResultId");

		if (surveyResultId != null) {
			setSurveyResultId(surveyResultId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long answerId = (Long)attributes.get("answerId");

		if (answerId != null) {
			setAnswerId(answerId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String freeAnswer = (String)attributes.get("freeAnswer");

		if (freeAnswer != null) {
			setFreeAnswer(freeAnswer);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSurveyResultId() {
		return _surveyResultId;
	}

	public void setSurveyResultId(long surveyResultId) {
		_surveyResultId = surveyResultId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public long getAnswerId() {
		return _answerId;
	}

	public void setAnswerId(long answerId) {
		_answerId = answerId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getFreeAnswer() {
		return _freeAnswer;
	}

	public void setFreeAnswer(String freeAnswer) {
		_freeAnswer = freeAnswer;
	}

	public BaseModel<?> getSurveyResultRemoteModel() {
		return _surveyResultRemoteModel;
	}

	public void setSurveyResultRemoteModel(BaseModel<?> surveyResultRemoteModel) {
		_surveyResultRemoteModel = surveyResultRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			SurveyResultLocalServiceUtil.addSurveyResult(this);
		}
		else {
			SurveyResultLocalServiceUtil.updateSurveyResult(this);
		}
	}

	@Override
	public SurveyResult toEscapedModel() {
		return (SurveyResult)Proxy.newProxyInstance(SurveyResult.class.getClassLoader(),
			new Class[] { SurveyResult.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		SurveyResultClp clone = new SurveyResultClp();

		clone.setUuid(getUuid());
		clone.setSurveyResultId(getSurveyResultId());
		clone.setActId(getActId());
		clone.setLatId(getLatId());
		clone.setQuestionId(getQuestionId());
		clone.setAnswerId(getAnswerId());
		clone.setUserId(getUserId());
		clone.setFreeAnswer(getFreeAnswer());

		return clone;
	}

	public int compareTo(SurveyResult surveyResult) {
		long primaryKey = surveyResult.getPrimaryKey();

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

		SurveyResultClp surveyResult = null;

		try {
			surveyResult = (SurveyResultClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = surveyResult.getPrimaryKey();

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
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", surveyResultId=");
		sb.append(getSurveyResultId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", latId=");
		sb.append(getLatId());
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", answerId=");
		sb.append(getAnswerId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", freeAnswer=");
		sb.append(getFreeAnswer());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.SurveyResult");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>surveyResultId</column-name><column-value><![CDATA[");
		sb.append(getSurveyResultId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>latId</column-name><column-value><![CDATA[");
		sb.append(getLatId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>answerId</column-name><column-value><![CDATA[");
		sb.append(getAnswerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>freeAnswer</column-name><column-value><![CDATA[");
		sb.append(getFreeAnswer());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _surveyResultId;
	private long _actId;
	private long _latId;
	private long _questionId;
	private long _answerId;
	private long _userId;
	private String _userUuid;
	private String _freeAnswer;
	private BaseModel<?> _surveyResultRemoteModel;
}