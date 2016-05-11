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
 * This class is a wrapper for {@link Competence}.
 * </p>
 *
 * @author    TLS
 * @see       Competence
 * @generated
 */
public class CompetenceWrapper implements Competence, ModelWrapper<Competence> {
	public CompetenceWrapper(Competence competence) {
		_competence = competence;
	}

	public Class<?> getModelClass() {
		return Competence.class;
	}

	public String getModelClassName() {
		return Competence.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("competenceId", getCompetenceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("page", getPage());
		attributes.put("generateCertificate", getGenerateCertificate());
		attributes.put("diplomaTemplate", getDiplomaTemplate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long competenceId = (Long)attributes.get("competenceId");

		if (competenceId != null) {
			setCompetenceId(competenceId);
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

		String page = (String)attributes.get("page");

		if (page != null) {
			setPage(page);
		}

		Boolean generateCertificate = (Boolean)attributes.get(
				"generateCertificate");

		if (generateCertificate != null) {
			setGenerateCertificate(generateCertificate);
		}

		String diplomaTemplate = (String)attributes.get("diplomaTemplate");

		if (diplomaTemplate != null) {
			setDiplomaTemplate(diplomaTemplate);
		}
	}

	/**
	* Returns the primary key of this competence.
	*
	* @return the primary key of this competence
	*/
	public long getPrimaryKey() {
		return _competence.getPrimaryKey();
	}

	/**
	* Sets the primary key of this competence.
	*
	* @param primaryKey the primary key of this competence
	*/
	public void setPrimaryKey(long primaryKey) {
		_competence.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this competence.
	*
	* @return the uuid of this competence
	*/
	public java.lang.String getUuid() {
		return _competence.getUuid();
	}

	/**
	* Sets the uuid of this competence.
	*
	* @param uuid the uuid of this competence
	*/
	public void setUuid(java.lang.String uuid) {
		_competence.setUuid(uuid);
	}

	/**
	* Returns the competence ID of this competence.
	*
	* @return the competence ID of this competence
	*/
	public long getCompetenceId() {
		return _competence.getCompetenceId();
	}

	/**
	* Sets the competence ID of this competence.
	*
	* @param competenceId the competence ID of this competence
	*/
	public void setCompetenceId(long competenceId) {
		_competence.setCompetenceId(competenceId);
	}

	/**
	* Returns the company ID of this competence.
	*
	* @return the company ID of this competence
	*/
	public long getCompanyId() {
		return _competence.getCompanyId();
	}

	/**
	* Sets the company ID of this competence.
	*
	* @param companyId the company ID of this competence
	*/
	public void setCompanyId(long companyId) {
		_competence.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this competence.
	*
	* @return the group ID of this competence
	*/
	public long getGroupId() {
		return _competence.getGroupId();
	}

	/**
	* Sets the group ID of this competence.
	*
	* @param groupId the group ID of this competence
	*/
	public void setGroupId(long groupId) {
		_competence.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this competence.
	*
	* @return the user ID of this competence
	*/
	public long getUserId() {
		return _competence.getUserId();
	}

	/**
	* Sets the user ID of this competence.
	*
	* @param userId the user ID of this competence
	*/
	public void setUserId(long userId) {
		_competence.setUserId(userId);
	}

	/**
	* Returns the user uuid of this competence.
	*
	* @return the user uuid of this competence
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competence.getUserUuid();
	}

	/**
	* Sets the user uuid of this competence.
	*
	* @param userUuid the user uuid of this competence
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_competence.setUserUuid(userUuid);
	}

	/**
	* Returns the status of this competence.
	*
	* @return the status of this competence
	*/
	public int getStatus() {
		return _competence.getStatus();
	}

	/**
	* Sets the status of this competence.
	*
	* @param status the status of this competence
	*/
	public void setStatus(int status) {
		_competence.setStatus(status);
	}

	/**
	* Returns the status by user ID of this competence.
	*
	* @return the status by user ID of this competence
	*/
	public long getStatusByUserId() {
		return _competence.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this competence.
	*
	* @param statusByUserId the status by user ID of this competence
	*/
	public void setStatusByUserId(long statusByUserId) {
		_competence.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this competence.
	*
	* @return the status by user uuid of this competence
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competence.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this competence.
	*
	* @param statusByUserUuid the status by user uuid of this competence
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_competence.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this competence.
	*
	* @return the status by user name of this competence
	*/
	public java.lang.String getStatusByUserName() {
		return _competence.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this competence.
	*
	* @param statusByUserName the status by user name of this competence
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_competence.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this competence.
	*
	* @return the status date of this competence
	*/
	public java.util.Date getStatusDate() {
		return _competence.getStatusDate();
	}

	/**
	* Sets the status date of this competence.
	*
	* @param statusDate the status date of this competence
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_competence.setStatusDate(statusDate);
	}

	/**
	* Returns the title of this competence.
	*
	* @return the title of this competence
	*/
	public java.lang.String getTitle() {
		return _competence.getTitle();
	}

	/**
	* Returns the localized title of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this competence
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _competence.getTitle(locale);
	}

	/**
	* Returns the localized title of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this competence. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _competence.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this competence
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _competence.getTitle(languageId);
	}

	/**
	* Returns the localized title of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this competence
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _competence.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _competence.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _competence.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this competence.
	*
	* @return the locales and localized titles of this competence
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _competence.getTitleMap();
	}

	/**
	* Sets the title of this competence.
	*
	* @param title the title of this competence
	*/
	public void setTitle(java.lang.String title) {
		_competence.setTitle(title);
	}

	/**
	* Sets the localized title of this competence in the language.
	*
	* @param title the localized title of this competence
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_competence.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this competence in the language, and sets the default locale.
	*
	* @param title the localized title of this competence
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_competence.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_competence.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this competence from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this competence
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_competence.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this competence from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this competence
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_competence.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this competence.
	*
	* @return the description of this competence
	*/
	public java.lang.String getDescription() {
		return _competence.getDescription();
	}

	/**
	* Returns the localized description of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this competence
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _competence.getDescription(locale);
	}

	/**
	* Returns the localized description of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this competence. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _competence.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this competence
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _competence.getDescription(languageId);
	}

	/**
	* Returns the localized description of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this competence
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _competence.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _competence.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _competence.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this competence.
	*
	* @return the locales and localized descriptions of this competence
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _competence.getDescriptionMap();
	}

	/**
	* Sets the description of this competence.
	*
	* @param description the description of this competence
	*/
	public void setDescription(java.lang.String description) {
		_competence.setDescription(description);
	}

	/**
	* Sets the localized description of this competence in the language.
	*
	* @param description the localized description of this competence
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_competence.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this competence in the language, and sets the default locale.
	*
	* @param description the localized description of this competence
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_competence.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_competence.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this competence from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this competence
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_competence.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this competence from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this competence
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_competence.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the page of this competence.
	*
	* @return the page of this competence
	*/
	public java.lang.String getPage() {
		return _competence.getPage();
	}

	/**
	* Sets the page of this competence.
	*
	* @param page the page of this competence
	*/
	public void setPage(java.lang.String page) {
		_competence.setPage(page);
	}

	/**
	* Returns the generate certificate of this competence.
	*
	* @return the generate certificate of this competence
	*/
	public boolean getGenerateCertificate() {
		return _competence.getGenerateCertificate();
	}

	/**
	* Returns <code>true</code> if this competence is generate certificate.
	*
	* @return <code>true</code> if this competence is generate certificate; <code>false</code> otherwise
	*/
	public boolean isGenerateCertificate() {
		return _competence.isGenerateCertificate();
	}

	/**
	* Sets whether this competence is generate certificate.
	*
	* @param generateCertificate the generate certificate of this competence
	*/
	public void setGenerateCertificate(boolean generateCertificate) {
		_competence.setGenerateCertificate(generateCertificate);
	}

	/**
	* Returns the diploma template of this competence.
	*
	* @return the diploma template of this competence
	*/
	public java.lang.String getDiplomaTemplate() {
		return _competence.getDiplomaTemplate();
	}

	/**
	* Returns the localized diploma template of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized diploma template of this competence
	*/
	public java.lang.String getDiplomaTemplate(java.util.Locale locale) {
		return _competence.getDiplomaTemplate(locale);
	}

	/**
	* Returns the localized diploma template of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized diploma template of this competence. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDiplomaTemplate(java.util.Locale locale,
		boolean useDefault) {
		return _competence.getDiplomaTemplate(locale, useDefault);
	}

	/**
	* Returns the localized diploma template of this competence in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized diploma template of this competence
	*/
	public java.lang.String getDiplomaTemplate(java.lang.String languageId) {
		return _competence.getDiplomaTemplate(languageId);
	}

	/**
	* Returns the localized diploma template of this competence in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized diploma template of this competence
	*/
	public java.lang.String getDiplomaTemplate(java.lang.String languageId,
		boolean useDefault) {
		return _competence.getDiplomaTemplate(languageId, useDefault);
	}

	public java.lang.String getDiplomaTemplateCurrentLanguageId() {
		return _competence.getDiplomaTemplateCurrentLanguageId();
	}

	public java.lang.String getDiplomaTemplateCurrentValue() {
		return _competence.getDiplomaTemplateCurrentValue();
	}

	/**
	* Returns a map of the locales and localized diploma templates of this competence.
	*
	* @return the locales and localized diploma templates of this competence
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDiplomaTemplateMap() {
		return _competence.getDiplomaTemplateMap();
	}

	/**
	* Sets the diploma template of this competence.
	*
	* @param diplomaTemplate the diploma template of this competence
	*/
	public void setDiplomaTemplate(java.lang.String diplomaTemplate) {
		_competence.setDiplomaTemplate(diplomaTemplate);
	}

	/**
	* Sets the localized diploma template of this competence in the language.
	*
	* @param diplomaTemplate the localized diploma template of this competence
	* @param locale the locale of the language
	*/
	public void setDiplomaTemplate(java.lang.String diplomaTemplate,
		java.util.Locale locale) {
		_competence.setDiplomaTemplate(diplomaTemplate, locale);
	}

	/**
	* Sets the localized diploma template of this competence in the language, and sets the default locale.
	*
	* @param diplomaTemplate the localized diploma template of this competence
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDiplomaTemplate(java.lang.String diplomaTemplate,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_competence.setDiplomaTemplate(diplomaTemplate, locale, defaultLocale);
	}

	public void setDiplomaTemplateCurrentLanguageId(java.lang.String languageId) {
		_competence.setDiplomaTemplateCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized diploma templates of this competence from the map of locales and localized diploma templates.
	*
	* @param diplomaTemplateMap the locales and localized diploma templates of this competence
	*/
	public void setDiplomaTemplateMap(
		java.util.Map<java.util.Locale, java.lang.String> diplomaTemplateMap) {
		_competence.setDiplomaTemplateMap(diplomaTemplateMap);
	}

	/**
	* Sets the localized diploma templates of this competence from the map of locales and localized diploma templates, and sets the default locale.
	*
	* @param diplomaTemplateMap the locales and localized diploma templates of this competence
	* @param defaultLocale the default locale
	*/
	public void setDiplomaTemplateMap(
		java.util.Map<java.util.Locale, java.lang.String> diplomaTemplateMap,
		java.util.Locale defaultLocale) {
		_competence.setDiplomaTemplateMap(diplomaTemplateMap, defaultLocale);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _competence.getApproved();
	}

	/**
	* Returns <code>true</code> if this competence is approved.
	*
	* @return <code>true</code> if this competence is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _competence.isApproved();
	}

	/**
	* Returns <code>true</code> if this competence is denied.
	*
	* @return <code>true</code> if this competence is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _competence.isDenied();
	}

	/**
	* Returns <code>true</code> if this competence is a draft.
	*
	* @return <code>true</code> if this competence is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _competence.isDraft();
	}

	/**
	* Returns <code>true</code> if this competence is expired.
	*
	* @return <code>true</code> if this competence is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _competence.isExpired();
	}

	/**
	* Returns <code>true</code> if this competence is inactive.
	*
	* @return <code>true</code> if this competence is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _competence.isInactive();
	}

	/**
	* Returns <code>true</code> if this competence is incomplete.
	*
	* @return <code>true</code> if this competence is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _competence.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this competence is pending.
	*
	* @return <code>true</code> if this competence is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _competence.isPending();
	}

	/**
	* Returns <code>true</code> if this competence is scheduled.
	*
	* @return <code>true</code> if this competence is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _competence.isScheduled();
	}

	public boolean isNew() {
		return _competence.isNew();
	}

	public void setNew(boolean n) {
		_competence.setNew(n);
	}

	public boolean isCachedModel() {
		return _competence.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_competence.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _competence.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _competence.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_competence.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _competence.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_competence.setExpandoBridgeAttributes(serviceContext);
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_competence.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new CompetenceWrapper((Competence)_competence.clone());
	}

	public int compareTo(com.liferay.lms.model.Competence competence) {
		return _competence.compareTo(competence);
	}

	@Override
	public int hashCode() {
		return _competence.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.Competence> toCacheModel() {
		return _competence.toCacheModel();
	}

	public com.liferay.lms.model.Competence toEscapedModel() {
		return new CompetenceWrapper(_competence.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _competence.toString();
	}

	public java.lang.String toXmlString() {
		return _competence.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_competence.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Competence getWrappedCompetence() {
		return _competence;
	}

	public Competence getWrappedModel() {
		return _competence;
	}

	public void resetOriginalValues() {
		_competence.resetOriginalValues();
	}

	private Competence _competence;
}