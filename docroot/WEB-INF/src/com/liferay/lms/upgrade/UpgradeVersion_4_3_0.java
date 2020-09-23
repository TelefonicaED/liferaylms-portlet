package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_3_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_3_0.class);
	
	
	public int getThreshold() {
		return 430;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.3.0");
		
		DB db = DBFactoryUtil.getDB();	
		
		String updateLearningActivity = "ALTER TABLE `lms_learningactivity` ADD COLUMN `improve` TINYINT(4) NULL DEFAULT FALSE AFTER `linkedActivityId`;";
		
		log.info("Alter table lms_learningactivity -->> Add improve");
		try {
			db.runSQL(updateLearningActivity);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 

		List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesByType((int)TestLearningActivityType.TYPE_ID);
		boolean improve = false;
		for(LearningActivity activity: activities){
			improve = Boolean.parseBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(activity, "improve", "false"));
			activity.setImprove(improve);
			LearningActivityLocalServiceUtil.updateLearningActivity(activity);
		}
	}
	
	
}