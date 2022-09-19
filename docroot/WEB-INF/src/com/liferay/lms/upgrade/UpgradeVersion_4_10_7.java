package com.liferay.lms.upgrade;


import java.io.IOException;
import java.sql.SQLException;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.RoleLocalServiceUtil;

public class UpgradeVersion_4_10_7 extends UpgradeProcess {
	
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_10_7.class);
	
	public int getThreshold() {
		return 4105;
	}

	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.10.7"); 
		
		String updateLmsPrefs = "ALTER TABLE lms_lmsprefs  ADD inspectorRole bigint after editorRole ;";
		 //Execute SQL Queries
		 DB db = DBFactoryUtil.getDB();
		log.warn("Crear inspectorRole en la tabla lms_lmsprefs");
		try {
			db.runSQL(updateLmsPrefs);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			log.debug(e);
		} 
		
		 updateLmsPrefs = "update lms_lmsprefs set lms_lmsprefs.inspectorRole = (select roleId from role_ where name = 'courseInspector');";
		 //Execute SQL Queries
		log.warn("Actualizar el rol inspector en la tabla lms_lmsprefs");
		try {
			db.runSQL(updateLmsPrefs);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			log.debug(e);
		} 
		
		
	}
}

