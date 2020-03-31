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
		log.info("Actualizando version a 4.2.0");

	
		DB db = DBFactoryUtil.getDB();	
		
		String updateCourseResult = "ALTER TABLE `lms_courseresult` ADD COLUMN `registrationDate` DATETIME NULL AFTER `passed`, "
				+ "ADD COLUMN `companyId` BIGINT(20) NULL AFTER `extraData`, "
				+ "ADD COLUMN `userModifiedId` BIGINT(20) NULL AFTER `companyId`;";
		
		log.info("Alter table lms_courseresult -->> Add companyId, userModifiedId");
		try {
			db.runSQL(updateCourseResult);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
	}
	
}