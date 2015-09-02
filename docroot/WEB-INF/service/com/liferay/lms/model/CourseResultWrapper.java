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
 * This class is a wrapper for {@link CourseResult}.
 * </p>
 *
 * @author    TLS
 * @see       CourseResult
 * @generated
 */
public class CourseResultWrapper implements CourseResult,
	ModelWrapper<CourseResult> {
	public CourseResultWrapper(CourseResult courseResult) {
		_courseResult = courseResult;
	}

	public Class<?> getModelClass() {
		return CourseResult.class;
	}

	public String getModelClassName() {
		return CourseResult.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("crId", getCrId());
		attributes.put("courseId", getCourseId());
		attributes.put("result", getResult());
		attributes.put("comments", getComments());
		attributes.put("userId", getUserId());
		attributes.put("passed", getPassed());
		attributes.put("startDate", getStartDate());
		attributes.put("passedDate", getPassedDate());
		attributes.put("allowStartDate", getAllowStartDate());
		attributes.put("allowFinishDate", getAllowFinishDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long crId = (Long)attributes.get("crId");

		if (crId != null) {
			setCrId(crId);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date passedDate = (Date)attributes.get("passedDate");

		if (passedDate != null) {
			setPassedDate(passedDate);
		}

		Date allowStartDate = (Date)attributes.get("allowStartDate");

		if (allowStartDate != null) {
			setAllowStartDate(allowStartDate);
		}

		Date allowFinishDate = (Date)attributes.get("allowFinishDate");

		if (allowFinishDate != null) {
			setAllowFinishDate(allowFinishDate);
		}
	}

	/**
	* Returns the primary key of this course result.
	*
	* @return the primary key of this course result
	*/
	public long getPrimaryKey() {
		return _courseResult.getPrimaryKey();
	}

	/**
	* Sets the primary key of this course result.
	*
	* @param primaryKey the primary key of this course result
	*/
	public void setPrimaryKey(long primaryKey) {
		_courseResult.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the cr ID of this course result.
	*
	* @return the cr ID of this course result
	*/
	public long getCrId() {
		return _courseResult.getCrId();
	}

	/**
	* Sets the cr ID of this course result.
	*
	* @param crId the cr ID of this course result
	*/
	public void setCrId(long crId) {
		_courseResult.setCrId(crId);
	}

	/**
	* Returns the course ID of this course result.
	*
	* @return the course ID of this course result
	*/
	public long getCourseId() {
		return _courseResult.getCourseId();
	}

	/**
	* Sets the course ID of this course result.
	*
	* @param courseId the course ID of this course result
	*/
	public void setCourseId(long courseId) {
		_courseResult.setCourseId(courseId);
	}

	/**
	* Returns the result of this course result.
	*
	* @return the result of this course result
	*/
	public long getResult() {
		return _courseResult.getResult();
	}

	/**
	* Sets the result of this course result.
	*
	* @param result the result of this course result
	*/
	public void setResult(long result) {
		_courseResult.setResult(result);
	}

	/**
	* Returns the comments of this course result.
	*
	* @return the comments of this course result
	*/
	public java.lang.String getComments() {
		return _courseResult.getComments();
	}

	/**
	* Sets the comments of this course result.
	*
	* @param comments the comments of this course result
	*/
	public void setComments(java.lang.String comments) {
		_courseResult.setComments(comments);
	}

	/**
	* Returns the user ID of this course result.
	*
	* @return the user ID of this course result
	*/
	public long getUserId() {
		return _courseResult.getUserId();
	}

	/**
	* Sets the user ID of this course result.
	*
	* @param userId the user ID of this course result
	*/
	public void setUserId(long userId) {
		_courseResult.setUserId(userId);
	}

	/**
	* Returns the user uuid of this course result.
	*
	* @return the user uuid of this course result
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResult.getUserUuid();
	}

	/**
	* Sets the user uuid of this course result.
	*
	* @param userUuid the user uuid of this course result
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_courseResult.setUserUuid(userUuid);
	}

	/**
	* Returns the passed of this course result.
	*
	* @return the passed of this course result
	*/
	public boolean getPassed() {
		return _courseResult.getPassed();
	}

	/**
	* Returns <code>true</code> if this course result is passed.
	*
	* @return <code>true</code> if this course result is passed; <code>false</code> otherwise
	*/
	public boolean isPassed() {
		return _courseResult.isPassed();
	}

	/**
	* Sets whether this course result is passed.
	*
	* @param passed the passed of this course result
	*/
	public void setPassed(boolean passed) {
		_courseResult.setPassed(passed);
	}

	/**
	* Returns the start date of this course result.
	*
	* @return the start date of this course result
	*/
	public java.util.Date getStartDate() {
		return _courseResult.getStartDate();
	}

	/**
	* Sets the start date of this course result.
	*
	* @param startDate the start date of this course result
	*/
	public void setStartDate(java.util.Date startDate) {
		_courseResult.setStartDate(startDate);
	}

	/**
	* Returns the passed date of this course result.
	*
	* @return the passed date of this course result
	*/
	public java.util.Date getPassedDate() {
		return _courseResult.getPassedDate();
	}

	/**
	* Sets the passed date of this course result.
	*
	* @param passedDate the passed date of this course result
	*/
	public void setPassedDate(java.util.Date passedDate) {
		_courseResult.setPassedDate(passedDate);
	}

	/**
	* Returns the allow start date of this course result.
	*
	* @return the allow start date of this course result
	*/
	public java.util.Date getAllowStartDate() {
		return _courseResult.getAllowStartDate();
	}

	/**
	* Sets the allow start date of this course result.
	*
	* @param allowStartDate the allow start date of this course result
	*/
	public void setAllowStartDate(java.util.Date allowStartDate) {
		_courseResult.setAllowStartDate(allowStartDate);
	}

	/**
	* Returns the allow finish date of this course result.
	*
	* @return the allow finish date of this course result
	*/
	public java.util.Date getAllowFinishDate() {
		return _courseResult.getAllowFinishDate();
	}

	/**
	* Sets the allow finish date of this course result.
	*
	* @param allowFinishDate the allow finish date of this course result
	*/
	public void setAllowFinishDate(java.util.Date allowFinishDate) {
		_courseResult.setAllowFinishDate(allowFinishDate);
	}

	public boolean isNew() {
		return _courseResult.isNew();
	}

	public void setNew(boolean n) {
		_courseResult.setNew(n);
	}

	public boolean isCachedModel() {
		return _courseResult.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_courseResult.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _courseResult.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _courseResult.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_courseResult.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _courseResult.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_courseResult.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CourseResultWrapper((CourseResult)_courseResult.clone());
	}

	public int compareTo(com.liferay.lms.model.CourseResult courseResult) {
		return _courseResult.compareTo(courseResult);
	}

	@Override
	public int hashCode() {
		return _courseResult.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.CourseResult> toCacheModel() {
		return _courseResult.toCacheModel();
	}

	public com.liferay.lms.model.CourseResult toEscapedModel() {
		return new CourseResultWrapper(_courseResult.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _courseResult.toString();
	}

	public java.lang.String toXmlString() {
		return _courseResult.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_courseResult.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public CourseResult getWrappedCourseResult() {
		return _courseResult;
	}

	public CourseResult getWrappedModel() {
		return _courseResult;
	}

	public void resetOriginalValues() {
		_courseResult.resetOriginalValues();
	}

	private CourseResult _courseResult;
}