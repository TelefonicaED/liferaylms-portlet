package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.ResourceExternalLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class ResourceExternalAssetRenderer extends LearningActivityBaseAssetRenderer {

	public ResourceExternalAssetRenderer(LearningActivity learningactivity,
			ResourceExternalLearningActivityType resourceExternalLearningActivityType) throws SystemException, PortalException {
		super(learningactivity, resourceExternalLearningActivityType, false);		
	}
}
