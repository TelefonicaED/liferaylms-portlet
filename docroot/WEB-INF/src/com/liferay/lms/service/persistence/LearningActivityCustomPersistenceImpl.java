/**
 * 
 */
package com.liferay.lms.service.persistence;

import java.util.Date;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The persistence implementation for the learning activity service.
 *
 * <p>
 * Custom persistence class to update last update date column
 * </p>
 *
 * @author TLS
 * @see LearningActivityPersistenceImpl
 * @see LearningActivityUtil
 * @generated
 */
public class LearningActivityCustomPersistenceImpl extends
		LearningActivityPersistenceImpl {
	
	@Override
	public LearningActivity updateImpl(LearningActivity learningActivity,
			boolean merge) throws SystemException {
		Date now = new Date();
		learningActivity = toUnwrappedModel(learningActivity);
		Date createDate = learningActivity.getCreateDate();
		Date modifiedDate = learningActivity.getModifiedDate();
		if(learningActivity.isNew()) {
			learningActivity.setCreateDate(now);
		}
		learningActivity.setModifiedDate(now);
		
		try {
			return super.updateImpl(learningActivity, merge);
		} catch (SystemException e) {
			learningActivity.setCreateDate(createDate);
			learningActivity.setModifiedDate(modifiedDate);
			throw e;
		} catch (RuntimeException e) {
			learningActivity.setCreateDate(createDate);
			learningActivity.setModifiedDate(modifiedDate);
			throw e;
		}
	}

}
