package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.TaskOnlineLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class TaskOnlineAssetRenderer extends LearningActivityBaseAssetRenderer {

	public TaskOnlineAssetRenderer(LearningActivity learningactivity, 
			TaskOnlineLearningActivityType taskOnlineLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,taskOnlineLearningActivityType,false);
	}
}
