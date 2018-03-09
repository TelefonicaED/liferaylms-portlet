package com.liferay.lms.upgrade;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;

public class UpgradeVersion_3_4_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_3_4_0.class);
	
	public int getThreshold() {
		return 340;
	}

	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 3.4"); 
		String createAsynchronousTable = "CREATE TABLE lms_asynchronousprocessaudit ("
				+ " asynchronousProcessAuditId BIGINT(20) not null primary key, "
				+ " companyId BIGINT(20), "
				+ " classNameId BIGINT(20), "
				+ " classPK BIGINT(20), "
				+ " userId BIGINT(20), "
				+ " createDate DATETIME, "
				+ " endDate DATETIME, "
				+ " status INTEGER, "
				+ " type_ VARCHAR(75), "
				+ " statusMessage LONGTEXT null "
				+ " ) "
				+ " COLLATE='utf8_general_ci' "
				+ "ENGINE=InnoDB;";
		 //Execute SQL Queries
		 DB db = DBFactoryUtil.getDB();
		 log.warn("Create table activity linked ");
		
		
		try {
			db.runSQL(createAsynchronousTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	
	
}