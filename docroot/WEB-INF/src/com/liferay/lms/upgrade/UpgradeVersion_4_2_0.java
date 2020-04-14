package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import com.liferay.lms.model.CourseType;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;

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
		
		List<CourseType> courseTypes = CourseTypeLocalServiceUtil.getCourseTypes(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<Long> templateIds = null;
		String templates = null;
		for(CourseType courseType: courseTypes){
			templateIds = courseType.getCourseTemplateIds();
			if(templateIds != null && templateIds.size() > 0){
				templates = StringUtil.merge(templateIds.toArray(new Long[templateIds.size()]),",");
				savePreference(LmsConstant.EDITION_TEMPLATE_IDS + "." + courseType.getCourseTypeId(), templates, courseType.getCompanyId());
			}
			
		}
	}
	
	private boolean savePreference(String key,String value, long companyId) throws SystemException {
		
		PortletPreferences prefs= PortalPreferencesLocalServiceUtil.getPreferences(companyId, companyId, 1);
		boolean error = false;
		if(!"".equals(key)&&!prefs.isReadOnly(key))
		{
			try {
				prefs.setValue(key, value);
			} catch (ReadOnlyException e) {
				e.printStackTrace();
				error=true;
			}
			try {
				prefs.store();
			} catch (ValidatorException e) {
				e.printStackTrace();
				error=true;
			} catch (IOException e) {
				e.printStackTrace();
				error=true;
			}
		}
		else
		{
			error=true;
		}
		return error;
	}
	
}