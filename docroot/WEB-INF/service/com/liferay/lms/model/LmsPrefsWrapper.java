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
 * This class is a wrapper for {@link LmsPrefs}.
 * </p>
 *
 * @author    TLS
 * @see       LmsPrefs
 * @generated
 */
public class LmsPrefsWrapper implements LmsPrefs, ModelWrapper<LmsPrefs> {
	public LmsPrefsWrapper(LmsPrefs lmsPrefs) {
		_lmsPrefs = lmsPrefs;
	}

	public Class<?> getModelClass() {
		return LmsPrefs.class;
	}

	public String getModelClassName() {
		return LmsPrefs.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("companyId", getCompanyId());
		attributes.put("teacherRole", getTeacherRole());
		attributes.put("editorRole", getEditorRole());
		attributes.put("lmsTemplates", getLmsTemplates());
		attributes.put("activities", getActivities());
		attributes.put("courseevals", getCourseevals());
		attributes.put("scoretranslators", getScoretranslators());
		attributes.put("usersResults", getUsersResults());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long teacherRole = (Long)attributes.get("teacherRole");

		if (teacherRole != null) {
			setTeacherRole(teacherRole);
		}

		Long editorRole = (Long)attributes.get("editorRole");

		if (editorRole != null) {
			setEditorRole(editorRole);
		}

		String lmsTemplates = (String)attributes.get("lmsTemplates");

		if (lmsTemplates != null) {
			setLmsTemplates(lmsTemplates);
		}

		String activities = (String)attributes.get("activities");

		if (activities != null) {
			setActivities(activities);
		}

		String courseevals = (String)attributes.get("courseevals");

		if (courseevals != null) {
			setCourseevals(courseevals);
		}

		String scoretranslators = (String)attributes.get("scoretranslators");

		if (scoretranslators != null) {
			setScoretranslators(scoretranslators);
		}

		Long usersResults = (Long)attributes.get("usersResults");

		if (usersResults != null) {
			setUsersResults(usersResults);
		}
	}

	/**
	* Returns the primary key of this lms prefs.
	*
	* @return the primary key of this lms prefs
	*/
	public long getPrimaryKey() {
		return _lmsPrefs.getPrimaryKey();
	}

	/**
	* Sets the primary key of this lms prefs.
	*
	* @param primaryKey the primary key of this lms prefs
	*/
	public void setPrimaryKey(long primaryKey) {
		_lmsPrefs.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the company ID of this lms prefs.
	*
	* @return the company ID of this lms prefs
	*/
	public long getCompanyId() {
		return _lmsPrefs.getCompanyId();
	}

	/**
	* Sets the company ID of this lms prefs.
	*
	* @param companyId the company ID of this lms prefs
	*/
	public void setCompanyId(long companyId) {
		_lmsPrefs.setCompanyId(companyId);
	}

	/**
	* Returns the teacher role of this lms prefs.
	*
	* @return the teacher role of this lms prefs
	*/
	public long getTeacherRole() {
		return _lmsPrefs.getTeacherRole();
	}

	/**
	* Sets the teacher role of this lms prefs.
	*
	* @param teacherRole the teacher role of this lms prefs
	*/
	public void setTeacherRole(long teacherRole) {
		_lmsPrefs.setTeacherRole(teacherRole);
	}

	/**
	* Returns the editor role of this lms prefs.
	*
	* @return the editor role of this lms prefs
	*/
	public long getEditorRole() {
		return _lmsPrefs.getEditorRole();
	}

	/**
	* Sets the editor role of this lms prefs.
	*
	* @param editorRole the editor role of this lms prefs
	*/
	public void setEditorRole(long editorRole) {
		_lmsPrefs.setEditorRole(editorRole);
	}

	/**
	* Returns the lms templates of this lms prefs.
	*
	* @return the lms templates of this lms prefs
	*/
	public java.lang.String getLmsTemplates() {
		return _lmsPrefs.getLmsTemplates();
	}

	/**
	* Sets the lms templates of this lms prefs.
	*
	* @param lmsTemplates the lms templates of this lms prefs
	*/
	public void setLmsTemplates(java.lang.String lmsTemplates) {
		_lmsPrefs.setLmsTemplates(lmsTemplates);
	}

	/**
	* Returns the activities of this lms prefs.
	*
	* @return the activities of this lms prefs
	*/
	public java.lang.String getActivities() {
		return _lmsPrefs.getActivities();
	}

	/**
	* Sets the activities of this lms prefs.
	*
	* @param activities the activities of this lms prefs
	*/
	public void setActivities(java.lang.String activities) {
		_lmsPrefs.setActivities(activities);
	}

	/**
	* Returns the courseevals of this lms prefs.
	*
	* @return the courseevals of this lms prefs
	*/
	public java.lang.String getCourseevals() {
		return _lmsPrefs.getCourseevals();
	}

	/**
	* Sets the courseevals of this lms prefs.
	*
	* @param courseevals the courseevals of this lms prefs
	*/
	public void setCourseevals(java.lang.String courseevals) {
		_lmsPrefs.setCourseevals(courseevals);
	}

	/**
	* Returns the scoretranslators of this lms prefs.
	*
	* @return the scoretranslators of this lms prefs
	*/
	public java.lang.String getScoretranslators() {
		return _lmsPrefs.getScoretranslators();
	}

	/**
	* Sets the scoretranslators of this lms prefs.
	*
	* @param scoretranslators the scoretranslators of this lms prefs
	*/
	public void setScoretranslators(java.lang.String scoretranslators) {
		_lmsPrefs.setScoretranslators(scoretranslators);
	}

	/**
	* Returns the users results of this lms prefs.
	*
	* @return the users results of this lms prefs
	*/
	public long getUsersResults() {
		return _lmsPrefs.getUsersResults();
	}

	/**
	* Sets the users results of this lms prefs.
	*
	* @param usersResults the users results of this lms prefs
	*/
	public void setUsersResults(long usersResults) {
		_lmsPrefs.setUsersResults(usersResults);
	}

	public boolean isNew() {
		return _lmsPrefs.isNew();
	}

	public void setNew(boolean n) {
		_lmsPrefs.setNew(n);
	}

	public boolean isCachedModel() {
		return _lmsPrefs.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_lmsPrefs.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _lmsPrefs.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _lmsPrefs.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_lmsPrefs.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _lmsPrefs.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_lmsPrefs.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LmsPrefsWrapper((LmsPrefs)_lmsPrefs.clone());
	}

	public int compareTo(com.liferay.lms.model.LmsPrefs lmsPrefs) {
		return _lmsPrefs.compareTo(lmsPrefs);
	}

	@Override
	public int hashCode() {
		return _lmsPrefs.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.LmsPrefs> toCacheModel() {
		return _lmsPrefs.toCacheModel();
	}

	public com.liferay.lms.model.LmsPrefs toEscapedModel() {
		return new LmsPrefsWrapper(_lmsPrefs.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _lmsPrefs.toString();
	}

	public java.lang.String toXmlString() {
		return _lmsPrefs.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_lmsPrefs.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LmsPrefs getWrappedLmsPrefs() {
		return _lmsPrefs;
	}

	public LmsPrefs getWrappedModel() {
		return _lmsPrefs;
	}

	public void resetOriginalValues() {
		_lmsPrefs.resetOriginalValues();
	}

	private LmsPrefs _lmsPrefs;
}