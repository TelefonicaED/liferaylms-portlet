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
 * This class is a wrapper for {@link Module}.
 * </p>
 *
 * @author    TLS
 * @see       Module
 * @generated
 */
public class ModuleWrapper implements Module, ModelWrapper<Module> {
	public ModuleWrapper(Module module) {
		_module = module;
	}

	public Class<?> getModelClass() {
		return Module.class;
	}

	public String getModelClassName() {
		return Module.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("moduleId", getModuleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("ordern", getOrdern());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("icon", getIcon());
		attributes.put("precedence", getPrecedence());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long moduleId = (Long)attributes.get("moduleId");

		if (moduleId != null) {
			setModuleId(moduleId);
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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long ordern = (Long)attributes.get("ordern");

		if (ordern != null) {
			setOrdern(ordern);
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

		Long precedence = (Long)attributes.get("precedence");

		if (precedence != null) {
			setPrecedence(precedence);
		}
	}

	/**
	* Returns the primary key of this module.
	*
	* @return the primary key of this module
	*/
	public long getPrimaryKey() {
		return _module.getPrimaryKey();
	}

	/**
	* Sets the primary key of this module.
	*
	* @param primaryKey the primary key of this module
	*/
	public void setPrimaryKey(long primaryKey) {
		_module.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this module.
	*
	* @return the uuid of this module
	*/
	public java.lang.String getUuid() {
		return _module.getUuid();
	}

	/**
	* Sets the uuid of this module.
	*
	* @param uuid the uuid of this module
	*/
	public void setUuid(java.lang.String uuid) {
		_module.setUuid(uuid);
	}

	/**
	* Returns the module ID of this module.
	*
	* @return the module ID of this module
	*/
	public long getModuleId() {
		return _module.getModuleId();
	}

	/**
	* Sets the module ID of this module.
	*
	* @param moduleId the module ID of this module
	*/
	public void setModuleId(long moduleId) {
		_module.setModuleId(moduleId);
	}

	/**
	* Returns the company ID of this module.
	*
	* @return the company ID of this module
	*/
	public long getCompanyId() {
		return _module.getCompanyId();
	}

	/**
	* Sets the company ID of this module.
	*
	* @param companyId the company ID of this module
	*/
	public void setCompanyId(long companyId) {
		_module.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this module.
	*
	* @return the group ID of this module
	*/
	public long getGroupId() {
		return _module.getGroupId();
	}

	/**
	* Sets the group ID of this module.
	*
	* @param groupId the group ID of this module
	*/
	public void setGroupId(long groupId) {
		_module.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this module.
	*
	* @return the user ID of this module
	*/
	public long getUserId() {
		return _module.getUserId();
	}

	/**
	* Sets the user ID of this module.
	*
	* @param userId the user ID of this module
	*/
	public void setUserId(long userId) {
		_module.setUserId(userId);
	}

	/**
	* Returns the user uuid of this module.
	*
	* @return the user uuid of this module
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _module.getUserUuid();
	}

	/**
	* Sets the user uuid of this module.
	*
	* @param userUuid the user uuid of this module
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_module.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this module.
	*
	* @return the user name of this module
	*/
	public java.lang.String getUserName() {
		return _module.getUserName();
	}

	/**
	* Sets the user name of this module.
	*
	* @param userName the user name of this module
	*/
	public void setUserName(java.lang.String userName) {
		_module.setUserName(userName);
	}

	/**
	* Returns the create date of this module.
	*
	* @return the create date of this module
	*/
	public java.util.Date getCreateDate() {
		return _module.getCreateDate();
	}

	/**
	* Sets the create date of this module.
	*
	* @param createDate the create date of this module
	*/
	public void setCreateDate(java.util.Date createDate) {
		_module.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this module.
	*
	* @return the modified date of this module
	*/
	public java.util.Date getModifiedDate() {
		return _module.getModifiedDate();
	}

	/**
	* Sets the modified date of this module.
	*
	* @param modifiedDate the modified date of this module
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_module.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the title of this module.
	*
	* @return the title of this module
	*/
	public java.lang.String getTitle() {
		return _module.getTitle();
	}

	/**
	* Returns the localized title of this module in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this module
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _module.getTitle(locale);
	}

	/**
	* Returns the localized title of this module in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this module. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _module.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this module in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this module
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _module.getTitle(languageId);
	}

	/**
	* Returns the localized title of this module in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this module
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _module.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _module.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _module.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this module.
	*
	* @return the locales and localized titles of this module
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _module.getTitleMap();
	}

	/**
	* Sets the title of this module.
	*
	* @param title the title of this module
	*/
	public void setTitle(java.lang.String title) {
		_module.setTitle(title);
	}

	/**
	* Sets the localized title of this module in the language.
	*
	* @param title the localized title of this module
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_module.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this module in the language, and sets the default locale.
	*
	* @param title the localized title of this module
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_module.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_module.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this module from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this module
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_module.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this module from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this module
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_module.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this module.
	*
	* @return the description of this module
	*/
	public java.lang.String getDescription() {
		return _module.getDescription();
	}

	/**
	* Returns the localized description of this module in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this module
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _module.getDescription(locale);
	}

	/**
	* Returns the localized description of this module in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this module. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _module.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this module in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this module
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _module.getDescription(languageId);
	}

	/**
	* Returns the localized description of this module in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this module
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _module.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _module.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _module.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this module.
	*
	* @return the locales and localized descriptions of this module
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _module.getDescriptionMap();
	}

	/**
	* Sets the description of this module.
	*
	* @param description the description of this module
	*/
	public void setDescription(java.lang.String description) {
		_module.setDescription(description);
	}

	/**
	* Sets the localized description of this module in the language.
	*
	* @param description the localized description of this module
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_module.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this module in the language, and sets the default locale.
	*
	* @param description the localized description of this module
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_module.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_module.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this module from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this module
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_module.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this module from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this module
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_module.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the ordern of this module.
	*
	* @return the ordern of this module
	*/
	public long getOrdern() {
		return _module.getOrdern();
	}

	/**
	* Sets the ordern of this module.
	*
	* @param ordern the ordern of this module
	*/
	public void setOrdern(long ordern) {
		_module.setOrdern(ordern);
	}

	/**
	* Returns the start date of this module.
	*
	* @return the start date of this module
	*/
	public java.util.Date getStartDate() {
		return _module.getStartDate();
	}

	/**
	* Sets the start date of this module.
	*
	* @param startDate the start date of this module
	*/
	public void setStartDate(java.util.Date startDate) {
		_module.setStartDate(startDate);
	}

	/**
	* Returns the end date of this module.
	*
	* @return the end date of this module
	*/
	public java.util.Date getEndDate() {
		return _module.getEndDate();
	}

	/**
	* Sets the end date of this module.
	*
	* @param endDate the end date of this module
	*/
	public void setEndDate(java.util.Date endDate) {
		_module.setEndDate(endDate);
	}

	/**
	* Returns the icon of this module.
	*
	* @return the icon of this module
	*/
	public long getIcon() {
		return _module.getIcon();
	}

	/**
	* Sets the icon of this module.
	*
	* @param icon the icon of this module
	*/
	public void setIcon(long icon) {
		_module.setIcon(icon);
	}

	/**
	* Returns the precedence of this module.
	*
	* @return the precedence of this module
	*/
	public long getPrecedence() {
		return _module.getPrecedence();
	}

	/**
	* Sets the precedence of this module.
	*
	* @param precedence the precedence of this module
	*/
	public void setPrecedence(long precedence) {
		_module.setPrecedence(precedence);
	}

	public boolean isNew() {
		return _module.isNew();
	}

	public void setNew(boolean n) {
		_module.setNew(n);
	}

	public boolean isCachedModel() {
		return _module.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_module.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _module.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _module.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_module.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _module.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_module.setExpandoBridgeAttributes(serviceContext);
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_module.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new ModuleWrapper((Module)_module.clone());
	}

	public int compareTo(com.liferay.lms.model.Module module) {
		return _module.compareTo(module);
	}

	@Override
	public int hashCode() {
		return _module.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.Module> toCacheModel() {
		return _module.toCacheModel();
	}

	public com.liferay.lms.model.Module toEscapedModel() {
		return new ModuleWrapper(_module.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _module.toString();
	}

	public java.lang.String toXmlString() {
		return _module.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_module.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Module getWrappedModule() {
		return _module;
	}

	public Module getWrappedModel() {
		return _module;
	}

	public void resetOriginalValues() {
		_module.resetOriginalValues();
	}

	private Module _module;
}