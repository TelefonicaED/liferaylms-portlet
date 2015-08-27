package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.SCORMLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class SCORMAssetRenderer extends LearningActivityBaseAssetRenderer {

	public SCORMAssetRenderer(LearningActivity learningactivity,
			SCORMLearningActivityType scormLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,scormLearningActivityType,false);
	}
}
