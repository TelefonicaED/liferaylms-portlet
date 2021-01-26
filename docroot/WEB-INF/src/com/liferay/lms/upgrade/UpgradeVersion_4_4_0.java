package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_4_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_4_0.class);
	
	
	public int getThreshold() {
		return 440;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.4.0");
		DB db = DBFactoryUtil.getDB();	
		
		String updateCourse = "ALTER TABLE `lms_course` ADD COLUMN `welcomeAddToCalendar` TINYINT(4) NULL DEFAULT NULL AFTER `welcome`;";
			
			log.info("Alter table lms_course -->> Add welcomeAddToCalendar");
			try {
				db.runSQL(updateCourse);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			} 
	}
	
	
}