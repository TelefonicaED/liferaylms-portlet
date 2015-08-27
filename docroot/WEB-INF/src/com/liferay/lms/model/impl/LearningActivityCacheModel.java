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

import com.liferay.lms.model.LearningActivity;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LearningActivity in entity cache.
 *
 * @author TLS
 * @see LearningActivity
 * @generated
 */
public class LearningActivityCacheModel implements CacheModel<LearningActivity>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", startdate=");
		sb.append(startdate);
		sb.append(", enddate=");
		sb.append(enddate);
		sb.append(", precedence=");
		sb.append(precedence);
		sb.append(", tries=");
		sb.append(tries);
		sb.append(", passpuntuation=");
		sb.append(passpuntuation);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", moduleId=");
		sb.append(moduleId);
		sb.append(", extracontent=");
		sb.append(extracontent);
		sb.append(", feedbackCorrect=");
		sb.append(feedbackCorrect);
		sb.append(", feedbackNoCorrect=");
		sb.append(feedbackNoCorrect);
		sb.append(", weightinmodule=");
		sb.append(weightinmodule);
		sb.append("}");

		return sb.toString();
	}

	public LearningActivity toEntityModel() {
		LearningActivityImpl learningActivityImpl = new LearningActivityImpl();

		if (uuid == null) {
			learningActivityImpl.setUuid(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setUuid(uuid);
		}

		learningActivityImpl.setActId(actId);
		learningActivityImpl.setCompanyId(companyId);
		learningActivityImpl.setUserId(userId);
		learningActivityImpl.setGroupId(groupId);

		if (userName == null) {
			learningActivityImpl.setUserName(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			learningActivityImpl.setCreateDate(null);
		}
		else {
			learningActivityImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			learningActivityImpl.setModifiedDate(null);
		}
		else {
			learningActivityImpl.setModifiedDate(new Date(modifiedDate));
		}

		learningActivityImpl.setStatus(status);
		learningActivityImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			learningActivityImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			learningActivityImpl.setStatusDate(null);
		}
		else {
			learningActivityImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			learningActivityImpl.setTitle(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setTitle(title);
		}

		if (description == null) {
			learningActivityImpl.setDescription(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setDescription(description);
		}

		learningActivityImpl.setTypeId(typeId);

		if (startdate == Long.MIN_VALUE) {
			learningActivityImpl.setStartdate(null);
		}
		else {
			learningActivityImpl.setStartdate(new Date(startdate));
		}

		if (enddate == Long.MIN_VALUE) {
			learningActivityImpl.setEnddate(null);
		}
		else {
			learningActivityImpl.setEnddate(new Date(enddate));
		}

		learningActivityImpl.setPrecedence(precedence);
		learningActivityImpl.setTries(tries);
		learningActivityImpl.setPasspuntuation(passpuntuation);
		learningActivityImpl.setPriority(priority);
		learningActivityImpl.setModuleId(moduleId);

		if (extracontent == null) {
			learningActivityImpl.setExtracontent(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setExtracontent(extracontent);
		}

		if (feedbackCorrect == null) {
			learningActivityImpl.setFeedbackCorrect(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setFeedbackCorrect(feedbackCorrect);
		}

		if (feedbackNoCorrect == null) {
			learningActivityImpl.setFeedbackNoCorrect(StringPool.BLANK);
		}
		else {
			learningActivityImpl.setFeedbackNoCorrect(feedbackNoCorrect);
		}

		learningActivityImpl.setWeightinmodule(weightinmodule);

		learningActivityImpl.resetOriginalValues();

		return learningActivityImpl;
	}

	public String uuid;
	public long actId;
	public long companyId;
	public long userId;
	public long groupId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public int typeId;
	public long startdate;
	public long enddate;
	public long precedence;
	public long tries;
	public int passpuntuation;
	public long priority;
	public long moduleId;
	public String extracontent;
	public String feedbackCorrect;
	public String feedbackNoCorrect;
	public long weightinmodule;
}