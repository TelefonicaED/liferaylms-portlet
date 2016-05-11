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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserCompetence}.
 * </p>
 *
 * @author    TLS
 * @see       UserCompetence
 * @generated
 */
public class UserCompetenceWrapper implements UserCompetence,
	ModelWrapper<UserCompetence> {
	public UserCompetenceWrapper(UserCompetence userCompetence) {
		_userCompetence = userCompetence;
	}

	public Class<?> getModelClass() {
		return UserCompetence.class;
	}

	public String getModelClassName() {
		return UserCompetence.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("usercompId", getUsercompId());
		attributes.put("userId", getUserId());
		attributes.put("competenceId", getCompetenceId());
		attributes.put("compDate", getCompDate());
		attributes.put("courseId", getCourseId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long usercompId = (Long)attributes.get("usercompId");

		if (usercompId != null) {
			setUsercompId(usercompId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long competenceId = (Long)attributes.get("competenceId");

		if (competenceId != null) {
			setCompetenceId(competenceId);
		}

		Date compDate = (Date)attributes.get("compDate");

		if (compDate != null) {
			setCompDate(compDate);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}
	}

	/**
	* Returns the primary key of this user competence.
	*
	* @return the primary key of this user competence
	*/
	public long getPrimaryKey() {
		return _userCompetence.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user competence.
	*
	* @param primaryKey the primary key of this user competence
	*/
	public void setPrimaryKey(long primaryKey) {
		_userCompetence.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this user competence.
	*
	* @return the uuid of this user competence
	*/
	public java.lang.String getUuid() {
		return _userCompetence.getUuid();
	}

	/**
	* Sets the uuid of this user competence.
	*
	* @param uuid the uuid of this user competence
	*/
	public void setUuid(java.lang.String uuid) {
		_userCompetence.setUuid(uuid);
	}

	/**
	* Returns the usercomp ID of this user competence.
	*
	* @return the usercomp ID of this user competence
	*/
	public long getUsercompId() {
		return _userCompetence.getUsercompId();
	}

	/**
	* Sets the usercomp ID of this user competence.
	*
	* @param usercompId the usercomp ID of this user competence
	*/
	public void setUsercompId(long usercompId) {
		_userCompetence.setUsercompId(usercompId);
	}

	/**
	* Returns the user ID of this user competence.
	*
	* @return the user ID of this user competence
	*/
	public long getUserId() {
		return _userCompetence.getUserId();
	}

	/**
	* Sets the user ID of this user competence.
	*
	* @param userId the user ID of this user competence
	*/
	public void setUserId(long userId) {
		_userCompetence.setUserId(userId);
	}

	/**
	* Returns the user uuid of this user competence.
	*
	* @return the user uuid of this user competence
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userCompetence.getUserUuid();
	}

	/**
	* Sets the user uuid of this user competence.
	*
	* @param userUuid the user uuid of this user competence
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_userCompetence.setUserUuid(userUuid);
	}

	/**
	* Returns the competence ID of this user competence.
	*
	* @return the competence ID of this user competence
	*/
	public long getCompetenceId() {
		return _userCompetence.getCompetenceId();
	}

	/**
	* Sets the competence ID of this user competence.
	*
	* @param competenceId the competence ID of this user competence
	*/
	public void setCompetenceId(long competenceId) {
		_userCompetence.setCompetenceId(competenceId);
	}

	/**
	* Returns the comp date of this user competence.
	*
	* @return the comp date of this user competence
	*/
	public java.util.Date getCompDate() {
		return _userCompetence.getCompDate();
	}

	/**
	* Sets the comp date of this user competence.
	*
	* @param compDate the comp date of this user competence
	*/
	public void setCompDate(java.util.Date compDate) {
		_userCompetence.setCompDate(compDate);
	}

	/**
	* Returns the course ID of this user competence.
	*
	* @return the course ID of this user competence
	*/
	public long getCourseId() {
		return _userCompetence.getCourseId();
	}

	/**
	* Sets the course ID of this user competence.
	*
	* @param courseId the course ID of this user competence
	*/
	public void setCourseId(long courseId) {
		_userCompetence.setCourseId(courseId);
	}

	public boolean isNew() {
		return _userCompetence.isNew();
	}

	public void setNew(boolean n) {
		_userCompetence.setNew(n);
	}

	public boolean isCachedModel() {
		return _userCompetence.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userCompetence.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userCompetence.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userCompetence.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userCompetence.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userCompetence.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userCompetence.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserCompetenceWrapper((UserCompetence)_userCompetence.clone());
	}

	public int compareTo(com.liferay.lms.model.UserCompetence userCompetence) {
		return _userCompetence.compareTo(userCompetence);
	}

	@Override
	public int hashCode() {
		return _userCompetence.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.UserCompetence> toCacheModel() {
		return _userCompetence.toCacheModel();
	}

	public com.liferay.lms.model.UserCompetence toEscapedModel() {
		return new UserCompetenceWrapper(_userCompetence.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userCompetence.toString();
	}

	public java.lang.String toXmlString() {
		return _userCompetence.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userCompetence.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public UserCompetence getWrappedUserCompetence() {
		return _userCompetence;
	}

	public UserCompetence getWrappedModel() {
		return _userCompetence;
	}

	public void resetOriginalValues() {
		_userCompetence.resetOriginalValues();
	}

	private UserCompetence _userCompetence;
}