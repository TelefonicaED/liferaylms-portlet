package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.model.CourseTypeRelation;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.service.CourseTypeRelationLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

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
		
		String updateLearningActivity = "ALTER TABLE `lms_coursetype` ADD COLUMN `classNameId` BIGINT(20) NULL AFTER `iconId`;";
		
		log.info("Alter table lms_coursetype -->> Add classNameId");
		try {
			db.runSQL(updateLearningActivity);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		updateLearningActivity = "ALTER TABLE `lms_coursetype` ADD COLUMN `active_` TINYINT(4) NULL DEFAULT TRUE AFTER `classNameId`;";
		
		log.info("Alter table lms_coursetype -->> Add active");
		try {
			db.runSQL(updateLearningActivity);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		//Migramos los datos a la nueva tabla
		String createTableCourseTypeRelation = "CREATE TABLE IF NOT EXISTS `lms_coursetyperelation` ("+
				"`courseTypeRelationId` BIGINT(20) NOT NULL,"+
				"`courseTypeId` BIGINT(20) NULL DEFAULT NULL,"+
				"`classNameId` BIGINT(20) NULL DEFAULT NULL,"+
				"`classPK` BIGINT(20) NULL DEFAULT NULL,"+
				"PRIMARY KEY (`courseTypeRelationId`),"+
				"INDEX `IX_120D96C8` (`courseTypeId`),"+
				"INDEX `IX_BF34A786` (`courseTypeId`,`classNameId`),"+
				"UNIQUE INDEX `IX_DCE1431D` (`courseTypeId`,`classNameId`,`classPK`)"+
				")"+
				"COLLATE='utf8_general_ci' ENGINE=InnoDB ;";
		
		log.info("Create table lms_coursetype -->> Add active");
		try {
			db.runSQL(createTableCourseTypeRelation);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		String alterColumn = "ALTER TABLE lms_coursetyperelation MODIFY COLUMN courseTypeRelationId BIGINT AUTO_INCREMENT;";
		
		log.info("Alter table lms_coursetyperelation -->> AUTO_INCREMENT");
		try {
			db.runSQL(alterColumn);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		List<Company>companies = CompanyLocalServiceUtil.getCompanies();
		String[] templateIds = null;
		List<CourseType> courseTypes = null;
		long classNameId = PortalUtil.getClassNameId(Course.class);
		for(Company company: companies){
			courseTypes = CourseTypeLocalServiceUtil.getByCompanyId(company.getCompanyId());
			for(CourseType courseType: courseTypes){
				templateIds = PrefsPropsUtil.getStringArray(company.getCompanyId(), LmsConstant.EDITION_TEMPLATE_IDS + "." + courseType.getCourseTypeId(), ",");
				for(String templateId: templateIds){
					try{
						CourseTypeRelationLocalServiceUtil.addCourseTypeRelation(courseType.getCourseTypeId(), classNameId, Long.valueOf(templateId));	
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		String insert = "INSERT INTO lms_coursetyperelation (courseTypeId, classNameId, classPK) "
				+ "SELECT DISTINCT courseTypeId, " + PortalUtil.getClassNameId(CalificationType.class)+", calificationType FROM lms_coursetypecalificationtype;";
		
		log.info("Insert table lms_coursetyperelation -->> lms_coursetypecalificationtype");
		try {
			db.runSQL(insert);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		insert = "INSERT INTO lms_coursetyperelation (courseTypeId, classNameId, classPK) "
				+ "SELECT DISTINCT courseTypeId, " + PortalUtil.getClassNameId(CourseEval.class)+", courseEvalId FROM lms_coursetypecourseeval;";
		
		log.info("Insert table lms_coursetyperelation -->> lms_coursetypecourseeval");
		try {
			db.runSQL(insert);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		insert = "INSERT INTO lms_coursetyperelation (courseTypeId, classNameId, classPK) "
				+ "SELECT DISTINCT courseTypeId, " + PortalUtil.getClassNameId(InscriptionType.class)+", inscriptionType FROM lms_coursetypeinscriptiontype;";
		
		log.info("Insert table lms_coursetyperelation -->> lms_coursetypeinscriptiontype");
		try {
			db.runSQL(insert);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		insert = "INSERT INTO lms_coursetyperelation (courseTypeId, classNameId, classPK) "
				+ "SELECT DISTINCT courseTypeId, " + PortalUtil.getClassNameId(LearningActivityType.class)+", learningActivityTypeId FROM lms_coursetypelearningactivity;";
		
		log.info("Insert table lms_coursetyperelation -->> lms_coursetypelearningactivity");
		try {
			db.runSQL(insert);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		insert = "INSERT INTO lms_coursetyperelation (courseTypeId, classNameId, classPK) "
				+ "SELECT DISTINCT courseTypeId, " + PortalUtil.getClassNameId(LayoutSetPrototype.class)+", templateId FROM lms_coursetypetemplate;";
		
		
		
		log.info("Insert table lms_coursetyperelation -->> lms_coursetypetemplate");
		try {
			db.runSQL(insert);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		String update = "UPDATE counter SET currentId = "
				+ "(SELECT MAX(lms_coursetyperelation.courseTypeRelationId)+1 "
				+ "FROM lms_coursetyperelation) "
				+ "WHERE NAME='" + CourseTypeRelation.class.getName() + "';";
		
		log.info("Update table counter");
		try {
			db.runSQL(update);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}