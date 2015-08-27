
package com.liferay.lms.asset;

import com.liferay.lms.learningactivity.TaskP2PLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class TaskP2PAssetRenderer extends LearningActivityBaseAssetRenderer {

	public TaskP2PAssetRenderer(LearningActivity learningactivity,
			TaskP2PLearningActivityType taskP2PLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,taskP2PLearningActivityType,false);
	}
}
