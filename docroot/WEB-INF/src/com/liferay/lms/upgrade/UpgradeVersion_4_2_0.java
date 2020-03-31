package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_2_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_2_0.class);
	
	
	public int getThreshold() {
		return 420;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.1.0");

		String updateCourseResult = "ALTER TABLE `lms_courseresult` ADD COLUMN `registrationDate` DATETIME NULL AFTER `passed`;";
	
		DB db = DBFactoryUtil.getDB();
		log.info("Alter table lms_courseresult -->> Add registrationDate");
		try {
			db.runSQL(updateCourseResult);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
	}
	
}