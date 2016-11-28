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
 * This class is a wrapper for {@link Course}.
 * </p>
 *
 * @author    TLS
 * @see       Course
 * @generated
 */
public class CourseWrapper implements Course, ModelWrapper<Course> {
	public CourseWrapper(Course course) {
		_course = course;
	}

	public Class<?> getModelClass() {
		return Course.class;
	}

	public String getModelClassName() {
		return Course.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("courseId", getCourseId());
		attributes.put("parentCourseId", getParentCourseId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("groupCreatedId", getGroupCreatedId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("icon", getIcon());
		attributes.put("CourseEvalId", getCourseEvalId());
		attributes.put("CourseExtraData", getCourseExtraData());
		attributes.put("closed", getClosed());
		attributes.put("maxusers", getMaxusers());
		attributes.put("calificationType", getCalificationType());
		attributes.put("welcome", getWelcome());
		attributes.put("welcomeMsg", getWelcomeMsg());
		attributes.put("welcomeSubject", getWelcomeSubject());
		attributes.put("goodbye", getGoodbye());
		attributes.put("goodbyeMsg", getGoodbyeMsg());
		attributes.put("goodbyeSubject", getGoodbyeSubject());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		Long parentCourseId = (Long)attributes.get("parentCourseId");

		if (parentCourseId != null) {
			setParentCourseId(parentCourseId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long groupCreatedId = (Long)attributes.get("groupCreatedId");

		if (groupCreatedId != null) {
			setGroupCreatedId(groupCreatedId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Long icon = (Long)attributes.get("icon");

		if (icon != null) {
			setIcon(icon);
		}

		Long CourseEvalId = (Long)attributes.get("CourseEvalId");

		if (CourseEvalId != null) {
			setCourseEvalId(CourseEvalId);
		}

		String CourseExtraData = (String)attributes.get("CourseExtraData");

		if (CourseExtraData != null) {
			setCourseExtraData(CourseExtraData);
		}

		Boolean closed = (Boolean)attributes.get("closed");

		if (closed != null) {
			setClosed(closed);
		}

		Long maxusers = (Long)attributes.get("maxusers");

		if (maxusers != null) {
			setMaxusers(maxusers);
		}

		Long calificationType = (Long)attributes.get("calificationType");

		if (calificationType != null) {
			setCalificationType(calificationType);
		}

		Boolean welcome = (Boolean)attributes.get("welcome");

		if (welcome != null) {
			setWelcome(welcome);
		}

		String welcomeMsg = (String)attributes.get("welcomeMsg");

		if (welcomeMsg != null) {
			setWelcomeMsg(welcomeMsg);
		}

		String welcomeSubject = (String)attributes.get("welcomeSubject");

		if (welcomeSubject != null) {
			setWelcomeSubject(welcomeSubject);
		}

		Boolean goodbye = (Boolean)attributes.get("goodbye");

		if (goodbye != null) {
			setGoodbye(goodbye);
		}

		String goodbyeMsg = (String)attributes.get("goodbyeMsg");

		if (goodbyeMsg != null) {
			setGoodbyeMsg(goodbyeMsg);
		}

		String goodbyeSubject = (String)attributes.get("goodbyeSubject");

		if (goodbyeSubject != null) {
			setGoodbyeSubject(goodbyeSubject);
		}
	}

	/**
	* Returns the primary key of this course.
	*
	* @return the primary key of this course
	*/
	public long getPrimaryKey() {
		return _course.getPrimaryKey();
	}

	/**
	* Sets the primary key of this course.
	*
	* @param primaryKey the primary key of this course
	*/
	public void setPrimaryKey(long primaryKey) {
		_course.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this course.
	*
	* @return the uuid of this course
	*/
	public java.lang.String getUuid() {
		return _course.getUuid();
	}

	/**
	* Sets the uuid of this course.
	*
	* @param uuid the uuid of this course
	*/
	public void setUuid(java.lang.String uuid) {
		_course.setUuid(uuid);
	}

	/**
	* Returns the course ID of this course.
	*
	* @return the course ID of this course
	*/
	public long getCourseId() {
		return _course.getCourseId();
	}

	/**
	* Sets the course ID of this course.
	*
	* @param courseId the course ID of this course
	*/
	public void setCourseId(long courseId) {
		_course.setCourseId(courseId);
	}

	/**
	* Returns the parent course ID of this course.
	*
	* @return the parent course ID of this course
	*/
	public long getParentCourseId() {
		return _course.getParentCourseId();
	}

	/**
	* Sets the parent course ID of this course.
	*
	* @param parentCourseId the parent course ID of this course
	*/
	public void setParentCourseId(long parentCourseId) {
		_course.setParentCourseId(parentCourseId);
	}

	/**
	* Returns the company ID of this course.
	*
	* @return the company ID of this course
	*/
	public long getCompanyId() {
		return _course.getCompanyId();
	}

	/**
	* Sets the company ID of this course.
	*
	* @param companyId the company ID of this course
	*/
	public void setCompanyId(long companyId) {
		_course.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this course.
	*
	* @return the group ID of this course
	*/
	public long getGroupId() {
		return _course.getGroupId();
	}

	/**
	* Sets the group ID of this course.
	*
	* @param groupId the group ID of this course
	*/
	public void setGroupId(long groupId) {
		_course.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this course.
	*
	* @return the user ID of this course
	*/
	public long getUserId() {
		return _course.getUserId();
	}

	/**
	* Sets the user ID of this course.
	*
	* @param userId the user ID of this course
	*/
	public void setUserId(long userId) {
		_course.setUserId(userId);
	}

	/**
	* Returns the user uuid of this course.
	*
	* @return the user uuid of this course
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _course.getUserUuid();
	}

	/**
	* Sets the user uuid of this course.
	*
	* @param userUuid the user uuid of this course
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_course.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this course.
	*
	* @return the user name of this course
	*/
	public java.lang.String getUserName() {
		return _course.getUserName();
	}

	/**
	* Sets the user name of this course.
	*
	* @param userName the user name of this course
	*/
	public void setUserName(java.lang.String userName) {
		_course.setUserName(userName);
	}

	/**
	* Returns the group created ID of this course.
	*
	* @return the group created ID of this course
	*/
	public long getGroupCreatedId() {
		return _course.getGroupCreatedId();
	}

	/**
	* Sets the group created ID of this course.
	*
	* @param groupCreatedId the group created ID of this course
	*/
	public void setGroupCreatedId(long groupCreatedId) {
		_course.setGroupCreatedId(groupCreatedId);
	}

	/**
	* Returns the create date of this course.
	*
	* @return the create date of this course
	*/
	public java.util.Date getCreateDate() {
		return _course.getCreateDate();
	}

	/**
	* Sets the create date of this course.
	*
	* @param createDate the create date of this course
	*/
	public void setCreateDate(java.util.Date createDate) {
		_course.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this course.
	*
	* @return the modified date of this course
	*/
	public java.util.Date getModifiedDate() {
		return _course.getModifiedDate();
	}

	/**
	* Sets the modified date of this course.
	*
	* @param modifiedDate the modified date of this course
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_course.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the status of this course.
	*
	* @return the status of this course
	*/
	public int getStatus() {
		return _course.getStatus();
	}

	/**
	* Sets the status of this course.
	*
	* @param status the status of this course
	*/
	public void setStatus(int status) {
		_course.setStatus(status);
	}

	/**
	* Returns the status by user ID of this course.
	*
	* @return the status by user ID of this course
	*/
	public long getStatusByUserId() {
		return _course.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this course.
	*
	* @param statusByUserId the status by user ID of this course
	*/
	public void setStatusByUserId(long statusByUserId) {
		_course.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this course.
	*
	* @return the status by user uuid of this course
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _course.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this course.
	*
	* @param statusByUserUuid the status by user uuid of this course
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_course.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this course.
	*
	* @return the status by user name of this course
	*/
	public java.lang.String getStatusByUserName() {
		return _course.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this course.
	*
	* @param statusByUserName the status by user name of this course
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_course.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this course.
	*
	* @return the status date of this course
	*/
	public java.util.Date getStatusDate() {
		return _course.getStatusDate();
	}

	/**
	* Sets the status date of this course.
	*
	* @param statusDate the status date of this course
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_course.setStatusDate(statusDate);
	}

	/**
	* Returns the title of this course.
	*
	* @return the title of this course
	*/
	public java.lang.String getTitle() {
		return _course.getTitle();
	}

	/**
	* Returns the localized title of this course in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this course
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _course.getTitle(locale);
	}

	/**
	* Returns the localized title of this course in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this course. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _course.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this course in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this course
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _course.getTitle(languageId);
	}

	/**
	* Returns the localized title of this course in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this course
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _course.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _course.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _course.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this course.
	*
	* @return the locales and localized titles of this course
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _course.getTitleMap();
	}

	/**
	* Sets the title of this course.
	*
	* @param title the title of this course
	*/
	public void setTitle(java.lang.String title) {
		_course.setTitle(title);
	}

	/**
	* Sets the localized title of this course in the language.
	*
	* @param title the localized title of this course
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_course.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this course in the language, and sets the default locale.
	*
	* @param title the localized title of this course
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_course.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_course.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this course from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this course
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_course.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this course from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this course
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_course.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this course.
	*
	* @return the description of this course
	*/
	public java.lang.String getDescription() {
		return _course.getDescription();
	}

	/**
	* Returns the localized description of this course in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this course
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _course.getDescription(locale);
	}

	/**
	* Returns the localized description of this course in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this course. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _course.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this course in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this course
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _course.getDescription(languageId);
	}

	/**
	* Returns the localized description of this course in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this course
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _course.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _course.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _course.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this course.
	*
	* @return the locales and localized descriptions of this course
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _course.getDescriptionMap();
	}

	/**
	* Sets the description of this course.
	*
	* @param description the description of this course
	*/
	public void setDescription(java.lang.String description) {
		_course.setDescription(description);
	}

	/**
	* Sets the localized description of this course in the language.
	*
	* @param description the localized description of this course
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_course.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this course in the language, and sets the default locale.
	*
	* @param description the localized description of this course
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_course.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_course.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this course from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this course
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_course.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this course from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this course
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_course.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the friendly u r l of this course.
	*
	* @return the friendly u r l of this course
	*/
	public java.lang.String getFriendlyURL() {
		return _course.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this course.
	*
	* @param friendlyURL the friendly u r l of this course
	*/
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_course.setFriendlyURL(friendlyURL);
	}

	/**
	* Returns the start date of this course.
	*
	* @return the start date of this course
	*/
	public java.util.Date getStartDate() {
		return _course.getStartDate();
	}

	/**
	* Sets the start date of this course.
	*
	* @param startDate the start date of this course
	*/
	public void setStartDate(java.util.Date startDate) {
		_course.setStartDate(startDate);
	}

	/**
	* Returns the end date of this course.
	*
	* @return the end date of this course
	*/
	public java.util.Date getEndDate() {
		return _course.getEndDate();
	}

	/**
	* Sets the end date of this course.
	*
	* @param endDate the end date of this course
	*/
	public void setEndDate(java.util.Date endDate) {
		_course.setEndDate(endDate);
	}

	/**
	* Returns the icon of this course.
	*
	* @return the icon of this course
	*/
	public long getIcon() {
		return _course.getIcon();
	}

	/**
	* Sets the icon of this course.
	*
	* @param icon the icon of this course
	*/
	public void setIcon(long icon) {
		_course.setIcon(icon);
	}

	/**
	* Returns the course eval ID of this course.
	*
	* @return the course eval ID of this course
	*/
	public long getCourseEvalId() {
		return _course.getCourseEvalId();
	}

	/**
	* Sets the course eval ID of this course.
	*
	* @param CourseEvalId the course eval ID of this course
	*/
	public void setCourseEvalId(long CourseEvalId) {
		_course.setCourseEvalId(CourseEvalId);
	}

	/**
	* Returns the course extra data of this course.
	*
	* @return the course extra data of this course
	*/
	public java.lang.String getCourseExtraData() {
		return _course.getCourseExtraData();
	}

	/**
	* Sets the course extra data of this course.
	*
	* @param CourseExtraData the course extra data of this course
	*/
	public void setCourseExtraData(java.lang.String CourseExtraData) {
		_course.setCourseExtraData(CourseExtraData);
	}

	/**
	* Returns the closed of this course.
	*
	* @return the closed of this course
	*/
	public boolean getClosed() {
		return _course.getClosed();
	}

	/**
	* Returns <code>true</code> if this course is closed.
	*
	* @return <code>true</code> if this course is closed; <code>false</code> otherwise
	*/
	public boolean isClosed() {
		return _course.isClosed();
	}

	/**
	* Sets whether this course is closed.
	*
	* @param closed the closed of this course
	*/
	public void setClosed(boolean closed) {
		_course.setClosed(closed);
	}

	/**
	* Returns the maxusers of this course.
	*
	* @return the maxusers of this course
	*/
	public long getMaxusers() {
		return _course.getMaxusers();
	}

	/**
	* Sets the maxusers of this course.
	*
	* @param maxusers the maxusers of this course
	*/
	public void setMaxusers(long maxusers) {
		_course.setMaxusers(maxusers);
	}

	/**
	* Returns the calification type of this course.
	*
	* @return the calification type of this course
	*/
	public long getCalificationType() {
		return _course.getCalificationType();
	}

	/**
	* Sets the calification type of this course.
	*
	* @param calificationType the calification type of this course
	*/
	public void setCalificationType(long calificationType) {
		_course.setCalificationType(calificationType);
	}

	/**
	* Returns the welcome of this course.
	*
	* @return the welcome of this course
	*/
	public boolean getWelcome() {
		return _course.getWelcome();
	}

	/**
	* Returns <code>true</code> if this course is welcome.
	*
	* @return <code>true</code> if this course is welcome; <code>false</code> otherwise
	*/
	public boolean isWelcome() {
		return _course.isWelcome();
	}

	/**
	* Sets whether this course is welcome.
	*
	* @param welcome the welcome of this course
	*/
	public void setWelcome(boolean welcome) {
		_course.setWelcome(welcome);
	}

	/**
	* Returns the welcome msg of this course.
	*
	* @return the welcome msg of this course
	*/
	public java.lang.String getWelcomeMsg() {
		return _course.getWelcomeMsg();
	}

	/**
	* Sets the welcome msg of this course.
	*
	* @param welcomeMsg the welcome msg of this course
	*/
	public void setWelcomeMsg(java.lang.String welcomeMsg) {
		_course.setWelcomeMsg(welcomeMsg);
	}

	/**
	* Returns the welcome subject of this course.
	*
	* @return the welcome subject of this course
	*/
	public java.lang.String getWelcomeSubject() {
		return _course.getWelcomeSubject();
	}

	/**
	* Sets the welcome subject of this course.
	*
	* @param welcomeSubject the welcome subject of this course
	*/
	public void setWelcomeSubject(java.lang.String welcomeSubject) {
		_course.setWelcomeSubject(welcomeSubject);
	}

	/**
	* Returns the goodbye of this course.
	*
	* @return the goodbye of this course
	*/
	public boolean getGoodbye() {
		return _course.getGoodbye();
	}

	/**
	* Returns <code>true</code> if this course is goodbye.
	*
	* @return <code>true</code> if this course is goodbye; <code>false</code> otherwise
	*/
	public boolean isGoodbye() {
		return _course.isGoodbye();
	}

	/**
	* Sets whether this course is goodbye.
	*
	* @param goodbye the goodbye of this course
	*/
	public void setGoodbye(boolean goodbye) {
		_course.setGoodbye(goodbye);
	}

	/**
	* Returns the goodbye msg of this course.
	*
	* @return the goodbye msg of this course
	*/
	public java.lang.String getGoodbyeMsg() {
		return _course.getGoodbyeMsg();
	}

	/**
	* Sets the goodbye msg of this course.
	*
	* @param goodbyeMsg the goodbye msg of this course
	*/
	public void setGoodbyeMsg(java.lang.String goodbyeMsg) {
		_course.setGoodbyeMsg(goodbyeMsg);
	}

	/**
	* Returns the goodbye subject of this course.
	*
	* @return the goodbye subject of this course
	*/
	public java.lang.String getGoodbyeSubject() {
		return _course.getGoodbyeSubject();
	}

	/**
	* Sets the goodbye subject of this course.
	*
	* @param goodbyeSubject the goodbye subject of this course
	*/
	public void setGoodbyeSubject(java.lang.String goodbyeSubject) {
		_course.setGoodbyeSubject(goodbyeSubject);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _course.getApproved();
	}

	/**
	* Returns <code>true</code> if this course is approved.
	*
	* @return <code>true</code> if this course is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _course.isApproved();
	}

	/**
	* Returns <code>true</code> if this course is denied.
	*
	* @return <code>true</code> if this course is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _course.isDenied();
	}

	/**
	* Returns <code>true</code> if this course is a draft.
	*
	* @return <code>true</code> if this course is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _course.isDraft();
	}

	/**
	* Returns <code>true</code> if this course is expired.
	*
	* @return <code>true</code> if this course is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _course.isExpired();
	}

	/**
	* Returns <code>true</code> if this course is inactive.
	*
	* @return <code>true</code> if this course is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _course.isInactive();
	}

	/**
	* Returns <code>true</code> if this course is incomplete.
	*
	* @return <code>true</code> if this course is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _course.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this course is pending.
	*
	* @return <code>true</code> if this course is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _course.isPending();
	}

	/**
	* Returns <code>true</code> if this course is scheduled.
	*
	* @return <code>true</code> if this course is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _course.isScheduled();
	}

	public boolean isNew() {
		return _course.isNew();
	}

	public void setNew(boolean n) {
		_course.setNew(n);
	}

	public boolean isCachedModel() {
		return _course.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_course.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _course.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _course.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_course.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _course.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_course.setExpandoBridgeAttributes(serviceContext);
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_course.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new CourseWrapper((Course)_course.clone());
	}

	public int compareTo(com.liferay.lms.model.Course course) {
		return _course.compareTo(course);
	}

	@Override
	public int hashCode() {
		return _course.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.Course> toCacheModel() {
		return _course.toCacheModel();
	}

	public com.liferay.lms.model.Course toEscapedModel() {
		return new CourseWrapper(_course.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _course.toString();
	}

	public java.lang.String toXmlString() {
		return _course.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_course.persist();
	}

	public com.liferay.lms.model.Course getParentCourse()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _course.getParentCourse();
	}

	public java.lang.String getImageURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _course.getImageURL(themeDisplay);
	}

	public double getAverageScore() {
		return _course.getAverageScore();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategoryIds() {
		return _course.getAssetCategoryIds();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTagIds() {
		return _course.getAssetTagIds();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _course.getGroup();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Course getWrappedCourse() {
		return _course;
	}

	public Course getWrappedModel() {
		return _course;
	}

	public void resetOriginalValues() {
		_course.resetOriginalValues();
	}

	private Course _course;
}