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
 * This class is a wrapper for {@link LearningActivity}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivity
 * @generated
 */
public class LearningActivityWrapper implements LearningActivity,
	ModelWrapper<LearningActivity> {
	public LearningActivityWrapper(LearningActivity learningActivity) {
		_learningActivity = learningActivity;
	}

	public Class<?> getModelClass() {
		return LearningActivity.class;
	}

	public String getModelClassName() {
		return LearningActivity.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("actId", getActId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("typeId", getTypeId());
		attributes.put("startdate", getStartdate());
		attributes.put("enddate", getEnddate());
		attributes.put("precedence", getPrecedence());
		attributes.put("tries", getTries());
		attributes.put("passpuntuation", getPasspuntuation());
		attributes.put("priority", getPriority());
		attributes.put("moduleId", getModuleId());
		attributes.put("extracontent", getExtracontent());
		attributes.put("feedbackCorrect", getFeedbackCorrect());
		attributes.put("feedbackNoCorrect", getFeedbackNoCorrect());
		attributes.put("weightinmodule", getWeightinmodule());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
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

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Date startdate = (Date)attributes.get("startdate");

		if (startdate != null) {
			setStartdate(startdate);
		}

		Date enddate = (Date)attributes.get("enddate");

		if (enddate != null) {
			setEnddate(enddate);
		}

		Long precedence = (Long)attributes.get("precedence");

		if (precedence != null) {
			setPrecedence(precedence);
		}

		Long tries = (Long)attributes.get("tries");

		if (tries != null) {
			setTries(tries);
		}

		Integer passpuntuation = (Integer)attributes.get("passpuntuation");

		if (passpuntuation != null) {
			setPasspuntuation(passpuntuation);
		}

		Long priority = (Long)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Long moduleId = (Long)attributes.get("moduleId");

		if (moduleId != null) {
			setModuleId(moduleId);
		}

		String extracontent = (String)attributes.get("extracontent");

		if (extracontent != null) {
			setExtracontent(extracontent);
		}

		String feedbackCorrect = (String)attributes.get("feedbackCorrect");

		if (feedbackCorrect != null) {
			setFeedbackCorrect(feedbackCorrect);
		}

		String feedbackNoCorrect = (String)attributes.get("feedbackNoCorrect");

		if (feedbackNoCorrect != null) {
			setFeedbackNoCorrect(feedbackNoCorrect);
		}

		Long weightinmodule = (Long)attributes.get("weightinmodule");

		if (weightinmodule != null) {
			setWeightinmodule(weightinmodule);
		}
	}

	/**
	* Returns the primary key of this learning activity.
	*
	* @return the primary key of this learning activity
	*/
	public long getPrimaryKey() {
		return _learningActivity.getPrimaryKey();
	}

	/**
	* Sets the primary key of this learning activity.
	*
	* @param primaryKey the primary key of this learning activity
	*/
	public void setPrimaryKey(long primaryKey) {
		_learningActivity.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this learning activity.
	*
	* @return the uuid of this learning activity
	*/
	public java.lang.String getUuid() {
		return _learningActivity.getUuid();
	}

	/**
	* Sets the uuid of this learning activity.
	*
	* @param uuid the uuid of this learning activity
	*/
	public void setUuid(java.lang.String uuid) {
		_learningActivity.setUuid(uuid);
	}

	/**
	* Returns the act ID of this learning activity.
	*
	* @return the act ID of this learning activity
	*/
	public long getActId() {
		return _learningActivity.getActId();
	}

	/**
	* Sets the act ID of this learning activity.
	*
	* @param actId the act ID of this learning activity
	*/
	public void setActId(long actId) {
		_learningActivity.setActId(actId);
	}

	/**
	* Returns the company ID of this learning activity.
	*
	* @return the company ID of this learning activity
	*/
	public long getCompanyId() {
		return _learningActivity.getCompanyId();
	}

	/**
	* Sets the company ID of this learning activity.
	*
	* @param companyId the company ID of this learning activity
	*/
	public void setCompanyId(long companyId) {
		_learningActivity.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this learning activity.
	*
	* @return the user ID of this learning activity
	*/
	public long getUserId() {
		return _learningActivity.getUserId();
	}

	/**
	* Sets the user ID of this learning activity.
	*
	* @param userId the user ID of this learning activity
	*/
	public void setUserId(long userId) {
		_learningActivity.setUserId(userId);
	}

	/**
	* Returns the user uuid of this learning activity.
	*
	* @return the user uuid of this learning activity
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivity.getUserUuid();
	}

	/**
	* Sets the user uuid of this learning activity.
	*
	* @param userUuid the user uuid of this learning activity
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_learningActivity.setUserUuid(userUuid);
	}

	/**
	* Returns the group ID of this learning activity.
	*
	* @return the group ID of this learning activity
	*/
	public long getGroupId() {
		return _learningActivity.getGroupId();
	}

	/**
	* Sets the group ID of this learning activity.
	*
	* @param groupId the group ID of this learning activity
	*/
	public void setGroupId(long groupId) {
		_learningActivity.setGroupId(groupId);
	}

	/**
	* Returns the user name of this learning activity.
	*
	* @return the user name of this learning activity
	*/
	public java.lang.String getUserName() {
		return _learningActivity.getUserName();
	}

	/**
	* Sets the user name of this learning activity.
	*
	* @param userName the user name of this learning activity
	*/
	public void setUserName(java.lang.String userName) {
		_learningActivity.setUserName(userName);
	}

	/**
	* Returns the create date of this learning activity.
	*
	* @return the create date of this learning activity
	*/
	public java.util.Date getCreateDate() {
		return _learningActivity.getCreateDate();
	}

	/**
	* Sets the create date of this learning activity.
	*
	* @param createDate the create date of this learning activity
	*/
	public void setCreateDate(java.util.Date createDate) {
		_learningActivity.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this learning activity.
	*
	* @return the modified date of this learning activity
	*/
	public java.util.Date getModifiedDate() {
		return _learningActivity.getModifiedDate();
	}

	/**
	* Sets the modified date of this learning activity.
	*
	* @param modifiedDate the modified date of this learning activity
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_learningActivity.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the status of this learning activity.
	*
	* @return the status of this learning activity
	*/
	public int getStatus() {
		return _learningActivity.getStatus();
	}

	/**
	* Sets the status of this learning activity.
	*
	* @param status the status of this learning activity
	*/
	public void setStatus(int status) {
		_learningActivity.setStatus(status);
	}

	/**
	* Returns the status by user ID of this learning activity.
	*
	* @return the status by user ID of this learning activity
	*/
	public long getStatusByUserId() {
		return _learningActivity.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this learning activity.
	*
	* @param statusByUserId the status by user ID of this learning activity
	*/
	public void setStatusByUserId(long statusByUserId) {
		_learningActivity.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this learning activity.
	*
	* @return the status by user uuid of this learning activity
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivity.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this learning activity.
	*
	* @param statusByUserUuid the status by user uuid of this learning activity
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_learningActivity.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this learning activity.
	*
	* @return the status by user name of this learning activity
	*/
	public java.lang.String getStatusByUserName() {
		return _learningActivity.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this learning activity.
	*
	* @param statusByUserName the status by user name of this learning activity
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_learningActivity.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this learning activity.
	*
	* @return the status date of this learning activity
	*/
	public java.util.Date getStatusDate() {
		return _learningActivity.getStatusDate();
	}

	/**
	* Sets the status date of this learning activity.
	*
	* @param statusDate the status date of this learning activity
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_learningActivity.setStatusDate(statusDate);
	}

	/**
	* Returns the title of this learning activity.
	*
	* @return the title of this learning activity
	*/
	public java.lang.String getTitle() {
		return _learningActivity.getTitle();
	}

	/**
	* Returns the localized title of this learning activity in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this learning activity
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _learningActivity.getTitle(locale);
	}

	/**
	* Returns the localized title of this learning activity in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this learning activity. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _learningActivity.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this learning activity in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this learning activity
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _learningActivity.getTitle(languageId);
	}

	/**
	* Returns the localized title of this learning activity in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this learning activity
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _learningActivity.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _learningActivity.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _learningActivity.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this learning activity.
	*
	* @return the locales and localized titles of this learning activity
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _learningActivity.getTitleMap();
	}

	/**
	* Sets the title of this learning activity.
	*
	* @param title the title of this learning activity
	*/
	public void setTitle(java.lang.String title) {
		_learningActivity.setTitle(title);
	}

	/**
	* Sets the localized title of this learning activity in the language.
	*
	* @param title the localized title of this learning activity
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_learningActivity.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this learning activity in the language, and sets the default locale.
	*
	* @param title the localized title of this learning activity
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_learningActivity.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_learningActivity.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this learning activity from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this learning activity
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_learningActivity.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this learning activity from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this learning activity
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_learningActivity.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this learning activity.
	*
	* @return the description of this learning activity
	*/
	public java.lang.String getDescription() {
		return _learningActivity.getDescription();
	}

	/**
	* Returns the localized description of this learning activity in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this learning activity
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _learningActivity.getDescription(locale);
	}

	/**
	* Returns the localized description of this learning activity in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this learning activity. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _learningActivity.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this learning activity in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this learning activity
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _learningActivity.getDescription(languageId);
	}

	/**
	* Returns the localized description of this learning activity in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this learning activity
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _learningActivity.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _learningActivity.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _learningActivity.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this learning activity.
	*
	* @return the locales and localized descriptions of this learning activity
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _learningActivity.getDescriptionMap();
	}

	/**
	* Sets the description of this learning activity.
	*
	* @param description the description of this learning activity
	*/
	public void setDescription(java.lang.String description) {
		_learningActivity.setDescription(description);
	}

	/**
	* Sets the localized description of this learning activity in the language.
	*
	* @param description the localized description of this learning activity
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_learningActivity.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this learning activity in the language, and sets the default locale.
	*
	* @param description the localized description of this learning activity
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_learningActivity.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_learningActivity.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this learning activity from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this learning activity
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_learningActivity.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this learning activity from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this learning activity
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_learningActivity.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type ID of this learning activity.
	*
	* @return the type ID of this learning activity
	*/
	public int getTypeId() {
		return _learningActivity.getTypeId();
	}

	/**
	* Sets the type ID of this learning activity.
	*
	* @param typeId the type ID of this learning activity
	*/
	public void setTypeId(int typeId) {
		_learningActivity.setTypeId(typeId);
	}

	/**
	* Returns the startdate of this learning activity.
	*
	* @return the startdate of this learning activity
	*/
	public java.util.Date getStartdate() {
		return _learningActivity.getStartdate();
	}

	/**
	* Sets the startdate of this learning activity.
	*
	* @param startdate the startdate of this learning activity
	*/
	public void setStartdate(java.util.Date startdate) {
		_learningActivity.setStartdate(startdate);
	}

	/**
	* Returns the enddate of this learning activity.
	*
	* @return the enddate of this learning activity
	*/
	public java.util.Date getEnddate() {
		return _learningActivity.getEnddate();
	}

	/**
	* Sets the enddate of this learning activity.
	*
	* @param enddate the enddate of this learning activity
	*/
	public void setEnddate(java.util.Date enddate) {
		_learningActivity.setEnddate(enddate);
	}

	/**
	* Returns the precedence of this learning activity.
	*
	* @return the precedence of this learning activity
	*/
	public long getPrecedence() {
		return _learningActivity.getPrecedence();
	}

	/**
	* Sets the precedence of this learning activity.
	*
	* @param precedence the precedence of this learning activity
	*/
	public void setPrecedence(long precedence) {
		_learningActivity.setPrecedence(precedence);
	}

	/**
	* Returns the tries of this learning activity.
	*
	* @return the tries of this learning activity
	*/
	public long getTries() {
		return _learningActivity.getTries();
	}

	/**
	* Sets the tries of this learning activity.
	*
	* @param tries the tries of this learning activity
	*/
	public void setTries(long tries) {
		_learningActivity.setTries(tries);
	}

	/**
	* Returns the passpuntuation of this learning activity.
	*
	* @return the passpuntuation of this learning activity
	*/
	public int getPasspuntuation() {
		return _learningActivity.getPasspuntuation();
	}

	/**
	* Sets the passpuntuation of this learning activity.
	*
	* @param passpuntuation the passpuntuation of this learning activity
	*/
	public void setPasspuntuation(int passpuntuation) {
		_learningActivity.setPasspuntuation(passpuntuation);
	}

	/**
	* Returns the priority of this learning activity.
	*
	* @return the priority of this learning activity
	*/
	public long getPriority() {
		return _learningActivity.getPriority();
	}

	/**
	* Sets the priority of this learning activity.
	*
	* @param priority the priority of this learning activity
	*/
	public void setPriority(long priority) {
		_learningActivity.setPriority(priority);
	}

	/**
	* Returns the module ID of this learning activity.
	*
	* @return the module ID of this learning activity
	*/
	public long getModuleId() {
		return _learningActivity.getModuleId();
	}

	/**
	* Sets the module ID of this learning activity.
	*
	* @param moduleId the module ID of this learning activity
	*/
	public void setModuleId(long moduleId) {
		_learningActivity.setModuleId(moduleId);
	}

	/**
	* Returns the extracontent of this learning activity.
	*
	* @return the extracontent of this learning activity
	*/
	public java.lang.String getExtracontent() {
		return _learningActivity.getExtracontent();
	}

	/**
	* Sets the extracontent of this learning activity.
	*
	* @param extracontent the extracontent of this learning activity
	*/
	public void setExtracontent(java.lang.String extracontent) {
		_learningActivity.setExtracontent(extracontent);
	}

	/**
	* Returns the feedback correct of this learning activity.
	*
	* @return the feedback correct of this learning activity
	*/
	public java.lang.String getFeedbackCorrect() {
		return _learningActivity.getFeedbackCorrect();
	}

	/**
	* Sets the feedback correct of this learning activity.
	*
	* @param feedbackCorrect the feedback correct of this learning activity
	*/
	public void setFeedbackCorrect(java.lang.String feedbackCorrect) {
		_learningActivity.setFeedbackCorrect(feedbackCorrect);
	}

	/**
	* Returns the feedback no correct of this learning activity.
	*
	* @return the feedback no correct of this learning activity
	*/
	public java.lang.String getFeedbackNoCorrect() {
		return _learningActivity.getFeedbackNoCorrect();
	}

	/**
	* Sets the feedback no correct of this learning activity.
	*
	* @param feedbackNoCorrect the feedback no correct of this learning activity
	*/
	public void setFeedbackNoCorrect(java.lang.String feedbackNoCorrect) {
		_learningActivity.setFeedbackNoCorrect(feedbackNoCorrect);
	}

	/**
	* Returns the weightinmodule of this learning activity.
	*
	* @return the weightinmodule of this learning activity
	*/
	public long getWeightinmodule() {
		return _learningActivity.getWeightinmodule();
	}

	/**
	* Sets the weightinmodule of this learning activity.
	*
	* @param weightinmodule the weightinmodule of this learning activity
	*/
	public void setWeightinmodule(long weightinmodule) {
		_learningActivity.setWeightinmodule(weightinmodule);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _learningActivity.getApproved();
	}

	/**
	* Returns <code>true</code> if this learning activity is approved.
	*
	* @return <code>true</code> if this learning activity is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _learningActivity.isApproved();
	}

	/**
	* Returns <code>true</code> if this learning activity is denied.
	*
	* @return <code>true</code> if this learning activity is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _learningActivity.isDenied();
	}

	/**
	* Returns <code>true</code> if this learning activity is a draft.
	*
	* @return <code>true</code> if this learning activity is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _learningActivity.isDraft();
	}

	/**
	* Returns <code>true</code> if this learning activity is expired.
	*
	* @return <code>true</code> if this learning activity is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _learningActivity.isExpired();
	}

	/**
	* Returns <code>true</code> if this learning activity is inactive.
	*
	* @return <code>true</code> if this learning activity is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _learningActivity.isInactive();
	}

	/**
	* Returns <code>true</code> if this learning activity is incomplete.
	*
	* @return <code>true</code> if this learning activity is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _learningActivity.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this learning activity is pending.
	*
	* @return <code>true</code> if this learning activity is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _learningActivity.isPending();
	}

	/**
	* Returns <code>true</code> if this learning activity is scheduled.
	*
	* @return <code>true</code> if this learning activity is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _learningActivity.isScheduled();
	}

	public boolean isNew() {
		return _learningActivity.isNew();
	}

	public void setNew(boolean n) {
		_learningActivity.setNew(n);
	}

	public boolean isCachedModel() {
		return _learningActivity.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_learningActivity.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _learningActivity.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _learningActivity.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_learningActivity.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _learningActivity.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_learningActivity.setExpandoBridgeAttributes(serviceContext);
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_learningActivity.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new LearningActivityWrapper((LearningActivity)_learningActivity.clone());
	}

	public int compareTo(
		com.liferay.lms.model.LearningActivity learningActivity) {
		return _learningActivity.compareTo(learningActivity);
	}

	@Override
	public int hashCode() {
		return _learningActivity.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.LearningActivity> toCacheModel() {
		return _learningActivity.toCacheModel();
	}

	public com.liferay.lms.model.LearningActivity toEscapedModel() {
		return new LearningActivityWrapper(_learningActivity.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _learningActivity.toString();
	}

	public java.lang.String toXmlString() {
		return _learningActivity.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivity.persist();
	}

	public boolean isNullStartDate() {
		return _learningActivity.isNullStartDate();
	}

	public boolean isNullEndDate() {
		return _learningActivity.isNullEndDate();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LearningActivity getWrappedLearningActivity() {
		return _learningActivity;
	}

	public LearningActivity getWrappedModel() {
		return _learningActivity;
	}

	public void resetOriginalValues() {
		_learningActivity.resetOriginalValues();
	}

	private LearningActivity _learningActivity;
}