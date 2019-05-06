package com.liferay.lms.upgrade;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

public class UpgradeVersion_3_8_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_3_8_0.class);
	
	
	public int getThreshold() {
		return 380;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 3.8");

		/*********************************************************************************************/
		/*****************   ALTER TABLE LMS_ASYNCHRONOUSPROCESSAUDIT -->> EXTRA CONTENT   **************/
		/*********************************************************************************************/
		
		 String alterCourseDeniedInscription = "ALTER TABLE `lms_asynchronousprocessaudit` "+
				 	"ADD COLUMN `extraContent` LONGTEXT NULL  AFTER `statusMessage`;";
		 
		 
		/*********************************************************************************************/
		/***************************Execute SQL Queries******************************/
		/*********************************************************************************************/
		DB db = DBFactoryUtil.getDB();
		log.info("Alter table lms_asynchronousprocessaudit -->> Add extracontent");
		try {
			db.runSQL(alterCourseDeniedInscription);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
		
		//Damos el permiso de registrar al user
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();
		Role userRole = null;
		for(Company company: companies){
			log.info("Asignando permiso REGISTER al User en companyId: " + company.getCompanyId());
			userRole = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.USER);
			ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.model.Course", ResourceConstants.SCOPE_COMPANY, String.valueOf(company.getCompanyId()), userRole.getRoleId(), "REGISTER");
		}
		
	}
	
}