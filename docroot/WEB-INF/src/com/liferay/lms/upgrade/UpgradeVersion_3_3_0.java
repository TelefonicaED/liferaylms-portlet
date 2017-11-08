package com.liferay.lms.upgrade;


import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
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

public class UpgradeVersion_3_3_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_3_3_0.class);
	
	public int getThreshold() {
		return 330;
	}

	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 3.3");

		
		/*********************************************************************************************/
		/*****************AÑADIMOS PERMISOS VER RESULTADOS Y ELIMINAR INTENTOS AL TUTOR***************/
		/***************AÑADIMOS PERMISOS VER RESULTADOS Y CAMBIAR VISIBILIDAD AL EDITOR**************/
		/*********************************************************************************************/
		try {
			List<String> actionIds = new ArrayList<String>();
			actionIds.add("VIEW_RESULTS");
			actionIds.add("CHANGE_VISIBILITY");
			actionIds.add("CHANGE_ALL_VISIBILITY");
			actionIds.add("DELETE_TRIES");
			ResourceActionLocalServiceUtil.checkResourceActions(
					LearningActivity.class.getName(), actionIds);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Company> listCompanies = CompanyLocalServiceUtil.getCompanies();
		
		LmsPrefs lmsPrefs = null;
		long teacherRoleId = 0;
		long editorRoleId = 0;
		
/*		List<Group> listGroup = null;
		List<ResourcePermission> listResourcePermission = null;
	*/	
		for(Company company: listCompanies){
			log.info("Permisos para company: " + company.getCompanyId());
			try {
				lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(company.getCompanyId());
				teacherRoleId = lmsPrefs.getTeacherRole();
				editorRoleId = lmsPrefs.getEditorRole();
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), LearningActivity.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", teacherRoleId, "VIEW_RESULTS");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), LearningActivity.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", teacherRoleId, "DELETE_TRIES");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), LearningActivity.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, "VIEW_RESULTS");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), LearningActivity.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, "CHANGE_ALL_VISIBILITY");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ResourcePermissionLocalServiceUtil.addResourcePermission(company.getCompanyId(), LearningActivity.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", editorRoleId, "CHANGE_VISIBILITY");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*********************************************************************************************/
			/****************FIN AÑADIR PERMISOS VER RESULTADOS Y ELIMINAR INTENTOS AL TUTOR**************/
			/**************FIN AÑADIR PERMISOS VER RESULTADOS Y CAMBIAR VISIBILIDAD AL EDITOR*************/
			/*********************************************************************************************/
			
			
			/*********************************************************************************************/
			/*****************LIMITAMOS VISIBILIDAD DE PORTLET CHANGE EDITING MODE A ROLES****************/
			/*********************************************************************************************/
			
		/*	listGroup = GroupLocalServiceUtil.getCompanyGroups(company.getCompanyId(), -1, -1);
			for(Group group: listGroup){
				
				listResourcePermission = ResourcePermissionLocalServiceUtil.getResourceResourcePermissions(company.getCompanyId(), group.getGroupId(), "com.liferay.lms.model", String.valueOf(group.getGroupId()));
				
				for(ResourcePermission resourcePermission: listResourcePermission){

				}
			}*/
			
		}

	}
	
	
}