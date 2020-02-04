package com.liferay.lms.upgrade;


import java.util.List;

import com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_1_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_1_0.class);
	
	
	public int getThreshold() {
		return 410;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.1.0");

		List<LearningActivity> learningActivities = LearningActivityLocalServiceUtil.getLearningActivitiesByType((int)TaskEvaluationLearningActivityType.TYPE_ID);
		
		for(LearningActivity learningActivity: learningActivities){
			learningActivity.setTries(1);
			LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity);
		}
	}
	
}