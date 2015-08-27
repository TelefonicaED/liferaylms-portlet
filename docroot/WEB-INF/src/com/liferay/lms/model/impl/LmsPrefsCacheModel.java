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

import com.liferay.lms.model.LmsPrefs;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing LmsPrefs in entity cache.
 *
 * @author TLS
 * @see LmsPrefs
 * @generated
 */
public class LmsPrefsCacheModel implements CacheModel<LmsPrefs>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{companyId=");
		sb.append(companyId);
		sb.append(", teacherRole=");
		sb.append(teacherRole);
		sb.append(", editorRole=");
		sb.append(editorRole);
		sb.append(", lmsTemplates=");
		sb.append(lmsTemplates);
		sb.append(", activities=");
		sb.append(activities);
		sb.append(", courseevals=");
		sb.append(courseevals);
		sb.append(", scoretranslators=");
		sb.append(scoretranslators);
		sb.append(", usersResults=");
		sb.append(usersResults);
		sb.append("}");

		return sb.toString();
	}

	public LmsPrefs toEntityModel() {
		LmsPrefsImpl lmsPrefsImpl = new LmsPrefsImpl();

		lmsPrefsImpl.setCompanyId(companyId);
		lmsPrefsImpl.setTeacherRole(teacherRole);
		lmsPrefsImpl.setEditorRole(editorRole);

		if (lmsTemplates == null) {
			lmsPrefsImpl.setLmsTemplates(StringPool.BLANK);
		}
		else {
			lmsPrefsImpl.setLmsTemplates(lmsTemplates);
		}

		if (activities == null) {
			lmsPrefsImpl.setActivities(StringPool.BLANK);
		}
		else {
			lmsPrefsImpl.setActivities(activities);
		}

		if (courseevals == null) {
			lmsPrefsImpl.setCourseevals(StringPool.BLANK);
		}
		else {
			lmsPrefsImpl.setCourseevals(courseevals);
		}

		if (scoretranslators == null) {
			lmsPrefsImpl.setScoretranslators(StringPool.BLANK);
		}
		else {
			lmsPrefsImpl.setScoretranslators(scoretranslators);
		}

		lmsPrefsImpl.setUsersResults(usersResults);

		lmsPrefsImpl.resetOriginalValues();

		return lmsPrefsImpl;
	}

	public long companyId;
	public long teacherRole;
	public long editorRole;
	public String lmsTemplates;
	public String activities;
	public String courseevals;
	public String scoretranslators;
	public long usersResults;
}