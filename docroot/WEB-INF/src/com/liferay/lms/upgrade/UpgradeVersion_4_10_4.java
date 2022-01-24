package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_10_4 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_10_4.class);

	
	public int getThreshold() {
		return 4104;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.10.4"); 
	
		DB db = DBFactoryUtil.getDB();	
		
		String updateCourseResult = "ALTER TABLE `lms_courseresult` ADD COLUMN `unRegistrationDate` DATETIME NULL AFTER `registrationDate`; ";
		
		log.info("Alter table lms_courseresult -->> Add unRegistrationDate");
		try {
			db.runSQL(updateCourseResult);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
	}


}
