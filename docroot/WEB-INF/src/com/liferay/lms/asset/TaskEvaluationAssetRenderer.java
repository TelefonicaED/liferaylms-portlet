package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class TaskEvaluationAssetRenderer extends LearningActivityBaseAssetRenderer {

	public TaskEvaluationAssetRenderer(LearningActivity learningactivity,
			TaskEvaluationLearningActivityType taskEvaluationLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,taskEvaluationLearningActivityType,false);
	}
}
