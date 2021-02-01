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

package com.liferay.lms.service.persistence;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.impl.LearningActivityImpl;

/**
 * The persistence implementation for the learning activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityPersistence
 * @see LearningActivityUtil
 * @generated
 */
public class CustomLearningActivityPersistenceImpl extends LearningActivityPersistenceImpl{
	@Override
	protected LearningActivity toUnwrappedModel(
			LearningActivity learningActivity) {
			if (learningActivity instanceof LearningActivityImpl) {
				return learningActivity;
			}

			LearningActivityImpl learningActivityImpl = new LearningActivityImpl();

			learningActivityImpl.setNew(learningActivity.isNew());
			learningActivityImpl.setPrimaryKey(learningActivity.getPrimaryKey());

			learningActivityImpl.setUuid(learningActivity.getUuid());
			learningActivityImpl.setActId(learningActivity.getActId());
			learningActivityImpl.setCompanyId(learningActivity.getCompanyId());
			learningActivityImpl.setUserId(learningActivity.getUserId());
			learningActivityImpl.setGroupId(learningActivity.getGroupId());
			learningActivityImpl.setUserName(learningActivity.getUserName());
			learningActivityImpl.setCreateDate(learningActivity.getCreateDate());
			learningActivityImpl.setModifiedDate(learningActivity.getModifiedDate());
			learningActivityImpl.setStatus(learningActivity.getStatus());
			learningActivityImpl.setStatusByUserId(learningActivity.getStatusByUserId());
			learningActivityImpl.setStatusByUserName(learningActivity.getStatusByUserName());
			learningActivityImpl.setStatusDate(learningActivity.getStatusDate());
			learningActivityImpl.setTitle(learningActivity.getTitle());
			learningActivityImpl.setDescription(learningActivity.getDescription());
			learningActivityImpl.setTypeId(learningActivity.getTypeId());
			learningActivityImpl.setStartdate(learningActivity.isNullStartDate() ? null : learningActivity.getStartdate());
			learningActivityImpl.setEnddate(learningActivity.isNullEndDate() ? null : learningActivity.getEnddate());
			learningActivityImpl.setPrecedence(learningActivity.getPrecedence());
			learningActivityImpl.setTries(learningActivity.getTries());
			learningActivityImpl.setPasspuntuation(learningActivity.getPasspuntuation());
			learningActivityImpl.setPriority(learningActivity.getPriority());
			learningActivityImpl.setModuleId(learningActivity.getModuleId());
			learningActivityImpl.setExtracontent(learningActivity.getExtracontent());
			learningActivityImpl.setFeedbackCorrect(learningActivity.getFeedbackCorrect());
			learningActivityImpl.setFeedbackNoCorrect(learningActivity.getFeedbackNoCorrect());
			learningActivityImpl.setWeightinmodule(learningActivity.getWeightinmodule());
			learningActivityImpl.setCommentsActivated(learningActivity.isCommentsActivated());
			learningActivityImpl.setLinkedActivityId(learningActivity.getLinkedActivityId());

			return learningActivityImpl;
		}
}