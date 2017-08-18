package com.liferay.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;

public class UpgradeUtil {

	
	public static void upgrade() throws SystemException{
		 Role siteMember;
		    
			System.out.println("--- UPGRADING LMS TO 3.1.1 ");
			System.out.println("--- CREATING ACCESS PERMISSION TO MODULE ");
			
			List<String> actionIds = new ArrayList<String>();
			actionIds.add("ACCESS");
			ResourceActionLocalServiceUtil.checkResourceActions(
					Module.class.getName(), actionIds);
			
			System.out.println("--- SETTING ACCESS PERMISSION TO COURSE ADMINISTRATION");
		    List<Company> companys = CompanyLocalServiceUtil.getCompanies();
		    for(Company company : companys){
		    	try{
		    		 Role courseAdministrator = RoleLocalServiceUtil.getRole(company.getCompanyId(), "Administrador de cursos");
			    	 if(courseAdministrator!=null){
			    		  ResourcePermissionLocalServiceUtil.setResourcePermissions(company.getCompanyId(), 
					     			Module.class.getName(),ResourceConstants.SCOPE_COMPANY, String.valueOf(company.getCompanyId()),  courseAdministrator.getRoleId(),  new String[]{"ACCESS","VIEW","DELETE", "ADD_LACT","UPDATE","PERMISSIONS","SOFT_PERMISSIONS"});
			    	  }
		    	 }catch(Exception e){
		    		 System.out.println("No hay Administrador de cursos");
		    	}
		    			  
		    }
		    
		    
		    System.out.println("--- SETTING ACCESS PERMISSION TO MODULES ");
		    List<Module> modules = ModuleLocalServiceUtil.getModules(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			for(Module module : modules){
				try{
					System.out.println("--- MODULE  "+ module.getModuleId());
				    siteMember = RoleLocalServiceUtil.fetchRole(module.getCompanyId(), RoleConstants.SITE_MEMBER);
					if(siteMember!=null){
						ResourcePermissionLocalServiceUtil.setResourcePermissions(module.getCompanyId(), 
				     			Module.class.getName(),ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(module.getModuleId()),  siteMember.getRoleId(),  new String[]{"ACCESS","VIEW"});
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			 System.out.println("--- END SETTING ACCESS PERMISSION TO MODULES ");
			 
			 String createScormTables = "CREATE TABLE sco_scormcontent (  uuid_ VARCHAR(75) NULL DEFAULT NULL, "+
					 					"scormId BIGINT(20) NOT NULL,  companyId BIGINT(20) NULL DEFAULT NULL, "+
					 					"groupId BIGINT(20) NULL DEFAULT NULL,  userId BIGINT(20) NULL DEFAULT NULL, "+
					 					"status INT(11) NULL DEFAULT NULL,  statusByUserId BIGINT(20) NULL DEFAULT NULL, "+
					 					"statusByUserName VARCHAR(75) NULL DEFAULT NULL,  statusDate DATETIME NULL DEFAULT NULL, "+
					 					"title VARCHAR(75) NULL DEFAULT NULL,  description VARCHAR(75) NULL DEFAULT NULL, "+
					 					"index_ VARCHAR(75) NULL DEFAULT NULL,  ciphered TINYINT(4) NULL DEFAULT NULL, "+
					 					"PRIMARY KEY (scormId), UNIQUE INDEX IX_5DEB5C3B (uuid_,  groupId), "+
					 					"INDEX IX_78C3B643 (companyId), INDEX IX_153AF185 (groupId), "+
					 					"INDEX IX_884753FF (userId), INDEX IX_9EF32F8B (userId, groupId), "+
					 					"INDEX IX_E6F9214F (uuid_) ) COLLATE='utf8_general_ci' ENGINE=InnoDB ;";
			 String insertScormContent = "insert into sco_scormcontent select * from lms_scormcontent;";

			 //Execute SQL Queries
			 DB db = DBFactoryUtil.getDB();
			 try {
				db.runSQL(createScormTables);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				db.runSQL(insertScormContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
