package com.liferay.lms.upgrade;


import java.io.IOException;
import java.sql.SQLException;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_3_6_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_3_6_0.class);
	
	public int getThreshold() {
		return 360;
	}

	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 3.6"); 
		String alterLmsPrefs = "ALTER TABLE `lms_lmsprefs` ADD COLUMN `inscriptionTypes` VARCHAR(75) NULL DEFAULT NULL AFTER `scoretranslators`;";
		
		//Execute SQL Queries
		DB db = DBFactoryUtil.getDB();
		log.warn("Alter table lmsprefs ");
		
		try {
			db.runSQL(alterLmsPrefs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String alterCourse = "ALTER TABLE `lms_course` ADD COLUMN `inscriptionType` BIGINT(20) NULL DEFAULT NULL AFTER `calificationType`;";
		
		//Execute SQL Queries
		log.warn("Alter table course ");
		
		try {
			db.runSQL(alterCourse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String updateCourse = "UPDATE `lms_course` SET `inscriptionType`='0';";
				
		//Execute SQL Queries
		log.warn("Update table course ");
		
		try {
			db.runSQL(updateCourse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String updatePrefs = "UPDATE `lms_lmsprefs` SET `inscriptionTypes`='0';";
		
		//Execute SQL Queries
		log.warn("Update table prefs ");
		
		try {
			db.runSQL(updatePrefs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	
	
}