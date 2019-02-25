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
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

public class UpgradeVersion_3_7_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_3_7_0.class);
	private static String ADD_ACTIVITY = "ADD_ACTIVITY";
	
	public int getThreshold() {
		return 370;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 3.7");

		/*********************************************************************************************/
		/*****************   ALTER TABLE LMS_COURSE -->> DENIED INSCRIPTION MESSAGE   **************/
		/*********************************************************************************************/
		
		 String alterCourseDeniedInscription = "ALTER TABLE `lms_course` "+
				 	"ADD COLUMN `deniedInscription` TINYINT(4) NULL DEFAULT NULL AFTER `welcomeSubject`;";
		 
		 String alterCourseDeniedInscriptionSubject = "ALTER TABLE `lms_course` "+
				 	"ADD COLUMN `deniedInscriptionSubject` VARCHAR(75) NULL DEFAULT NULL AFTER `deniedInscription`;";
		
		 String alterCourseDeniedInscriptionMsg = "ALTER TABLE `lms_course` "+
				 	"ADD COLUMN `deniedInscriptionMsg` LONGTEXT NULL AFTER `deniedInscriptionSubject`;";
		 
		/*********************************************************************************************/
		/*****************   CREATE TABLES COURSE TYPES  **************/
		/*********************************************************************************************/
		
		String createTableCourseType = "CREATE TABLE IF NOT EXISTS `lms_coursetype` ("+
				"`courseTypeId` BIGINT(20) NOT NULL,"+
				"`companyId` BIGINT(20) NULL DEFAULT NULL,"+
				"`userId` BIGINT(20) NULL DEFAULT NULL,"+
				"`groupId` BIGINT(20) NULL DEFAULT NULL,"+
				"`userName` VARCHAR(75) NULL DEFAULT NULL,"+
				"`createDate` DATETIME NULL DEFAULT NULL,"+
				"`modifiedDate` DATETIME NULL DEFAULT NULL,"+
				"`name` LONGTEXT NULL,"+
				"`description` LONGTEXT NULL,"+
				"`iconId` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeId`),"+
				"INDEX `IX_B3E69260` (`companyId`),"+
				"INDEX `IX_9A6B92AC` (`courseTypeId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB;";
		
		String createTableCourseTypeCalificacionType = "CREATE TABLE IF NOT EXISTS `lms_coursetypecalificationtype` ("+
				"`courseTypeCalificationTypeId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`calificationType` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeCalificationTypeId`),"+
				"INDEX `IX_EB924F40` (`courseTypeCalificationTypeId`),"+
				"INDEX `IX_C2333876` (`courseTypeId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB ;";		
		
		String createTableCourseTypeCourseEval = "CREATE TABLE IF NOT EXISTS  `lms_coursetypecourseeval` ("+
				"`courseTypeEvalutationTypeId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`courseEvalId` VARCHAR(75) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeEvalutationTypeId`),"+
				"INDEX `IX_B8D93ACB` (`courseTypeEvalutationTypeId`),"+
				"INDEX `IX_AAF2A5A3` (`courseTypeId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB;";
		
		String createTableCourseTypeInscriptionType = "CREATE TABLE IF NOT EXISTS `lms_coursetypeinscriptiontype` ("+
				"`courseTypeInscriptionTypeId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`inscriptionType` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeInscriptionTypeId`),"+
				"INDEX `IX_E4E5DA7A` (`courseTypeId`),"+
				"INDEX `IX_14979B52` (`courseTypeInscriptionTypeId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB;";
		
		String createTableCourseTypeLearningActivity = "CREATE TABLE IF NOT EXISTS `lms_coursetypelearningactivity` ("+
				"`courseTypeLearningActivityId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`learningActivityTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeLearningActivityId`),"+
				"INDEX `IX_B878E519` (`courseTypeId`),"+
				"INDEX `IX_AC299F06` (`courseTypeLearningActivityId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB;";
		
		String createTableCourseTypeTemplate = "CREATE TABLE IF NOT EXISTS `lms_coursetypetemplate` ("+
				"`courseTypeTemplateId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`templateId` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeTemplateId`),"+
				"INDEX `IX_A40BAD46` (`courseTypeId`),"+
				"INDEX `IX_5BD857E0` (`courseTypeTemplateId`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB;";
		
		/*********************************************************************************************/
		/***************************Execute SQL Queries******************************/
		/*********************************************************************************************/
		DB db = DBFactoryUtil.getDB();
		log.info("Alter table lms_course -->> Add deniedDescription");
		try {
			db.runSQL(alterCourseDeniedInscription);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
		log.info("Alter table lms_course -->> Add deniedDescriptionSubject");
		try {
			db.runSQL(alterCourseDeniedInscriptionSubject);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Alter table lms_course -->> Add deniedInscriptionMsg");
		try {
			db.runSQL(alterCourseDeniedInscriptionMsg);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetype");
		try {
			db.runSQL(createTableCourseType);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetypecalificationtype");
		try {
			db.runSQL(createTableCourseTypeCalificacionType);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetypecourseeval");
		try {
			db.runSQL(createTableCourseTypeCourseEval);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetypeinscriptiontype");
		try {
			db.runSQL(createTableCourseTypeInscriptionType);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetypelearningactivity");
		try {
			db.runSQL(createTableCourseTypeLearningActivity);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		log.info("Create table lms_coursetypetemplate");
		try {
			db.runSQL(createTableCourseTypeTemplate);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		/*********************************************************************************************/
		/*****************   ASSET MODULES   **************/
		/*********************************************************************************************/
		
		log.info("::::::::::::ASSET MODULES:::::::::::::::::::::");
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		for(Module module : ModuleLocalServiceUtil.getModules(-1, -1)){
			try{
				AssetEntryLocalServiceUtil.updateEntry(module.getUserId(), module.getGroupId(),
						Module.class.getName(), module.getModuleId(), module.getUuid(), 0, null, null, true,
						module.getStartDate(), module.getEndDate(), new Date(System.currentTimeMillis()), null,
						ContentTypes.TEXT_HTML, module.getTitle(), module.getDescription(), module.getDescription(),
						null, null, 0, 0, null, false);
				indexer.reindex(module);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		/*********************************************************************************************/
		/*****************AÑADIMOS PERMISOS AÑADIR ACTIVIDAD AL EDITOR DE CURSOS**************/
		/*********************************************************************************************/
		
		//Comprobamos que los permisos existan
		try{
			List<String> actionIds =  new ArrayList<String>();
			actionIds.add("ADD_ACTIVITY");
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.ResourceExternalLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.ResourceInternalLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.SurveyLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.TaskOfflineLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.TaskOnlineLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.TaskP2PLearningActivityType", actionIds);
			ResourceActionLocalServiceUtil.checkResourceActions("com.liferay.lms.learningactivity.TestLearningActivityType", actionIds);
		
		}catch(Exception e){
			e.printStackTrace();
		}

		List<Company> listCompanies = CompanyLocalServiceUtil.getCompanies();
		
		LmsPrefs lmsPrefs = null;
		long editorRoleId = 0;
		
		for(Company company: listCompanies){
			log.info("Permisos para company: " + company.getCompanyId());
			try {
				lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(company.getCompanyId());
				editorRoleId = lmsPrefs.getEditorRole();
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.ResourceExternalLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				} 
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.ResourceInternalLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				} 
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.SurveyLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.TaskOfflineLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.TaskOnlineLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.TaskP2PLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), "com.liferay.lms.learningactivity.TestLearningActivityType", ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, ADD_ACTIVITY);
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
		}
	}
	
}