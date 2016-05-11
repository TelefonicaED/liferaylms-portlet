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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CourseCompetence}.
 * </p>
 *
 * @author    TLS
 * @see       CourseCompetence
 * @generated
 */
public class CourseCompetenceWrapper implements CourseCompetence,
	ModelWrapper<CourseCompetence> {
	public CourseCompetenceWrapper(CourseCompetence courseCompetence) {
		_courseCompetence = courseCompetence;
	}

	public Class<?> getModelClass() {
		return CourseCompetence.class;
	}

	public String getModelClassName() {
		return CourseCompetence.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CourcompetenceId", getCourcompetenceId());
		attributes.put("courseId", getCourseId());
		attributes.put("competenceId", getCompetenceId());
		attributes.put("condition", getCondition());

		return attributes;
	}

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

	/**
	* Returns the primary key of this course competence.
	*
	* @return the primary key of this course competence
	*/
	public long getPrimaryKey() {
		return _courseCompetence.getPrimaryKey();
	}

	/**
	* Sets the primary key of this course competence.
	*
	* @param primaryKey the primary key of this course competence
	*/
	public void setPrimaryKey(long primaryKey) {
		_courseCompetence.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this course competence.
	*
	* @return the uuid of this course competence
	*/
	public java.lang.String getUuid() {
		return _courseCompetence.getUuid();
	}

	/**
	* Sets the uuid of this course competence.
	*
	* @param uuid the uuid of this course competence
	*/
	public void setUuid(java.lang.String uuid) {
		_courseCompetence.setUuid(uuid);
	}

	/**
	* Returns the courcompetence ID of this course competence.
	*
	* @return the courcompetence ID of this course competence
	*/
	public long getCourcompetenceId() {
		return _courseCompetence.getCourcompetenceId();
	}

	/**
	* Sets the courcompetence ID of this course competence.
	*
	* @param CourcompetenceId the courcompetence ID of this course competence
	*/
	public void setCourcompetenceId(long CourcompetenceId) {
		_courseCompetence.setCourcompetenceId(CourcompetenceId);
	}

	/**
	* Returns the course ID of this course competence.
	*
	* @return the course ID of this course competence
	*/
	public long getCourseId() {
		return _courseCompetence.getCourseId();
	}

	/**
	* Sets the course ID of this course competence.
	*
	* @param courseId the course ID of this course competence
	*/
	public void setCourseId(long courseId) {
		_courseCompetence.setCourseId(courseId);
	}

	/**
	* Returns the competence ID of this course competence.
	*
	* @return the competence ID of this course competence
	*/
	public long getCompetenceId() {
		return _courseCompetence.getCompetenceId();
	}

	/**
	* Sets the competence ID of this course competence.
	*
	* @param competenceId the competence ID of this course competence
	*/
	public void setCompetenceId(long competenceId) {
		_courseCompetence.setCompetenceId(competenceId);
	}

	/**
	* Returns the condition of this course competence.
	*
	* @return the condition of this course competence
	*/
	public boolean getCondition() {
		return _courseCompetence.getCondition();
	}

	/**
	* Returns <code>true</code> if this course competence is condition.
	*
	* @return <code>true</code> if this course competence is condition; <code>false</code> otherwise
	*/
	public boolean isCondition() {
		return _courseCompetence.isCondition();
	}

	/**
	* Sets whether this course competence is condition.
	*
	* @param condition the condition of this course competence
	*/
	public void setCondition(boolean condition) {
		_courseCompetence.setCondition(condition);
	}

	public boolean isNew() {
		return _courseCompetence.isNew();
	}

	public void setNew(boolean n) {
		_courseCompetence.setNew(n);
	}

	public boolean isCachedModel() {
		return _courseCompetence.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_courseCompetence.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _courseCompetence.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _courseCompetence.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_courseCompetence.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _courseCompetence.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_courseCompetence.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CourseCompetenceWrapper((CourseCompetence)_courseCompetence.clone());
	}

	public int compareTo(
		com.liferay.lms.model.CourseCompetence courseCompetence) {
		return _courseCompetence.compareTo(courseCompetence);
	}

	@Override
	public int hashCode() {
		return _courseCompetence.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.CourseCompetence> toCacheModel() {
		return _courseCompetence.toCacheModel();
	}

	public com.liferay.lms.model.CourseCompetence toEscapedModel() {
		return new CourseCompetenceWrapper(_courseCompetence.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _courseCompetence.toString();
	}

	public java.lang.String toXmlString() {
		return _courseCompetence.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_courseCompetence.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public CourseCompetence getWrappedCourseCompetence() {
		return _courseCompetence;
	}

	public CourseCompetence getWrappedModel() {
		return _courseCompetence;
	}

	public void resetOriginalValues() {
		_courseCompetence.resetOriginalValues();
	}

	private CourseCompetence _courseCompetence;
}