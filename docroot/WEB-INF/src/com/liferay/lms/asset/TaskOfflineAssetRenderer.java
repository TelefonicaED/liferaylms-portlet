package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.TaskOfflineLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class TaskOfflineAssetRenderer extends LearningActivityBaseAssetRenderer {

	public TaskOfflineAssetRenderer(LearningActivity learningactivity,
			TaskOfflineLearningActivityType taskOfflineLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,taskOfflineLearningActivityType,false);
	}
}
