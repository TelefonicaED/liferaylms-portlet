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

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityResultModel;
import com.liferay.lms.model.LearningActivityResultSoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the LearningActivityResult service. Represents a row in the &quot;Lms_LearningActivityResult&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.lms.model.LearningActivityResultModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LearningActivityResultImpl}.
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultImpl
 * @see com.liferay.lms.model.LearningActivityResult
 * @see com.liferay.lms.model.LearningActivityResultModel
 * @generated
 */
public class LearningActivityResultModelImpl extends BaseModelImpl<LearningActivityResult>
	implements LearningActivityResultModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a learning activity result model instance should use the {@link com.liferay.lms.model.LearningActivityResult} interface instead.
	 */
	public static final String TABLE_NAME = "Lms_LearningActivityResult";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "larId", Types.BIGINT },
			{ "actId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "result", Types.BIGINT },
			{ "startDate", Types.TIMESTAMP },
			{ "endDate", Types.TIMESTAMP },
			{ "latId", Types.BIGINT },
			{ "comments", Types.VARCHAR },
			{ "passed", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table Lms_LearningActivityResult (uuid_ VARCHAR(75) null,larId LONG not null primary key,actId LONG,userId LONG,result LONG,startDate DATE null,endDate DATE null,latId LONG,comments TEXT null,passed BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table Lms_LearningActivityResult";
	public static final String ORDER_BY_JPQL = " ORDER BY learningActivityResult.larId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Lms_LearningActivityResult.larId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.lms.model.LearningActivityResult"),
			false);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.lms.model.LearningActivityResult"),
			false);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.lms.model.LearningActivityResult"),
			true);
	public static long ACTID_COLUMN_BITMASK = 1L;
	public static long ENDDATE_COLUMN_BITMASK = 2L;
	public static long PASSED_COLUMN_BITMASK = 4L;
	public static long USERID_COLUMN_BITMASK = 8L;
	public static long UUID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static LearningActivityResult toModel(
		LearningActivityResultSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		LearningActivityResult model = new LearningActivityResultImpl();

		model.setUuid(soapModel.getUuid());
		model.setLarId(soapModel.getLarId());
		model.setActId(soapModel.getActId());
		model.setUserId(soapModel.getUserId());
		model.setResult(soapModel.getResult());
		model.setStartDate(soapModel.getStartDate());
		model.setEndDate(soapModel.getEndDate());
		model.setLatId(soapModel.getLatId());
		model.setComments(soapModel.getComments());
		model.setPassed(soapModel.getPassed());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<LearningActivityResult> toModels(
		LearningActivityResultSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<LearningActivityResult> models = new ArrayList<LearningActivityResult>(soapModels.length);

		for (LearningActivityResultSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.lms.model.LearningActivityResult"));

	public LearningActivityResultModelImpl() {
	}

	public long getPrimaryKey() {
		return _larId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLarId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_larId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return LearningActivityResult.class;
	}

	public String getModelClassName() {
		return LearningActivityResult.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("larId", getLarId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("result", getResult());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("latId", getLatId());
		attributes.put("comments", getComments());
		attributes.put("passed", getPassed());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long larId = (Long)attributes.get("larId");

		if (larId != null) {
			setLarId(larId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}
	}

	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getLarId() {
		return _larId;
	}

	public void setLarId(long larId) {
		_columnBitmask = -1L;

		_larId = larId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_columnBitmask |= ACTID_COLUMN_BITMASK;

		if (!_setOriginalActId) {
			_setOriginalActId = true;

			_originalActId = _actId;
		}

		_actId = actId;
	}

	public long getOriginalActId() {
		return _originalActId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_columnBitmask |= ENDDATE_COLUMN_BITMASK;

		if (_originalEndDate == null) {
			_originalEndDate = _endDate;
		}

		_endDate = endDate;
	}

	public Date getOriginalEndDate() {
		return _originalEndDate;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public String getComments() {
		if (_comments == null) {
			return StringPool.BLANK;
		}
		else {
			return _comments;
		}
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public boolean getPassed() {
		return _passed;
	}

	public boolean isPassed() {
		return _passed;
	}

	public void setPassed(boolean passed) {
		_columnBitmask |= PASSED_COLUMN_BITMASK;

		if (!_setOriginalPassed) {
			_setOriginalPassed = true;

			_originalPassed = _passed;
		}

		_passed = passed;
	}

	public boolean getOriginalPassed() {
		return _originalPassed;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			LearningActivityResult.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LearningActivityResult toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (LearningActivityResult)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public Object clone() {
		LearningActivityResultImpl learningActivityResultImpl = new LearningActivityResultImpl();

		learningActivityResultImpl.setUuid(getUuid());
		learningActivityResultImpl.setLarId(getLarId());
		learningActivityResultImpl.setActId(getActId());
		learningActivityResultImpl.setUserId(getUserId());
		learningActivityResultImpl.setResult(getResult());
		learningActivityResultImpl.setStartDate(getStartDate());
		learningActivityResultImpl.setEndDate(getEndDate());
		learningActivityResultImpl.setLatId(getLatId());
		learningActivityResultImpl.setComments(getComments());
		learningActivityResultImpl.setPassed(getPassed());

		learningActivityResultImpl.resetOriginalValues();

		return learningActivityResultImpl;
	}

	public int compareTo(LearningActivityResult learningActivityResult) {
		int value = 0;

		if (getLarId() < learningActivityResult.getLarId()) {
			value = -1;
		}
		else if (getLarId() > learningActivityResult.getLarId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		LearningActivityResult learningActivityResult = null;

		try {
			learningActivityResult = (LearningActivityResult)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = learningActivityResult.getPrimaryKey();

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
	public void resetOriginalValues() {
		LearningActivityResultModelImpl learningActivityResultModelImpl = this;

		learningActivityResultModelImpl._originalUuid = learningActivityResultModelImpl._uuid;

		learningActivityResultModelImpl._originalActId = learningActivityResultModelImpl._actId;

		learningActivityResultModelImpl._setOriginalActId = false;

		learningActivityResultModelImpl._originalUserId = learningActivityResultModelImpl._userId;

		learningActivityResultModelImpl._setOriginalUserId = false;

		learningActivityResultModelImpl._originalEndDate = learningActivityResultModelImpl._endDate;

		learningActivityResultModelImpl._originalPassed = learningActivityResultModelImpl._passed;

		learningActivityResultModelImpl._setOriginalPassed = false;

		learningActivityResultModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<LearningActivityResult> toCacheModel() {
		LearningActivityResultCacheModel learningActivityResultCacheModel = new LearningActivityResultCacheModel();

		learningActivityResultCacheModel.uuid = getUuid();

		String uuid = learningActivityResultCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			learningActivityResultCacheModel.uuid = null;
		}

		learningActivityResultCacheModel.larId = getLarId();

		learningActivityResultCacheModel.actId = getActId();

		learningActivityResultCacheModel.userId = getUserId();

		learningActivityResultCacheModel.result = getResult();

		Date startDate = getStartDate();

		if (startDate != null) {
			learningActivityResultCacheModel.startDate = startDate.getTime();
		}
		else {
			learningActivityResultCacheModel.startDate = Long.MIN_VALUE;
		}

		Date endDate = getEndDate();

		if (endDate != null) {
			learningActivityResultCacheModel.endDate = endDate.getTime();
		}
		else {
			learningActivityResultCacheModel.endDate = Long.MIN_VALUE;
		}

		learningActivityResultCacheModel.latId = getLatId();

		learningActivityResultCacheModel.comments = getComments();

		String comments = learningActivityResultCacheModel.comments;

		if ((comments != null) && (comments.length() == 0)) {
			learningActivityResultCacheModel.comments = null;
		}

		learningActivityResultCacheModel.passed = getPassed();

		return learningActivityResultCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", larId=");
		sb.append(getLarId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", latId=");
		sb.append(getLatId());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", passed=");
		sb.append(getPassed());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.LearningActivityResult");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>larId</column-name><column-value><![CDATA[");
		sb.append(getLarId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>latId</column-name><column-value><![CDATA[");
		sb.append(getLatId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passed</column-name><column-value><![CDATA[");
		sb.append(getPassed());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = LearningActivityResult.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			LearningActivityResult.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _larId;
	private long _actId;
	private long _originalActId;
	private boolean _setOriginalActId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _result;
	private Date _startDate;
	private Date _endDate;
	private Date _originalEndDate;
	private long _latId;
	private String _comments;
	private boolean _passed;
	private boolean _originalPassed;
	private boolean _setOriginalPassed;
	private long _columnBitmask;
	private LearningActivityResult _escapedModelProxy;
}