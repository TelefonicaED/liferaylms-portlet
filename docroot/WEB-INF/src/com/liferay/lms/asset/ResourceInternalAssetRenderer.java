package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.ResourceInternalLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class ResourceInternalAssetRenderer extends LearningActivityBaseAssetRenderer {

	public ResourceInternalAssetRenderer(LearningActivity learningactivity,
			ResourceInternalLearningActivityType resourceInternalLearningActivityType) throws SystemException, PortalException {
		super(learningactivity, resourceInternalLearningActivityType, false);		
	}
}
