package com.liferay.lms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ModuleUpdateResult
 */
public class ModuleUpdateResult extends MVCPortlet {
 
	@ProcessAction(name = "updateResult")
	public void updateResult(ActionRequest request, ActionResponse response) throws Exception {
		
		try {
			long moduleId = 0 ;
			int changes = 0;
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			String emailAddress = request.getParameter("email");
	
			try {
				moduleId = Long.valueOf(request.getParameter("moduleId"));
			} catch (Exception e) {/*e.printStackTrace();*/}
			
			String allUsers =request.getParameter("allusers");
			String allCourses =request.getParameter("allcourses");
			
			Module m = ModuleLocalServiceUtil.getModule(moduleId);
			
			//System.out.println("email: "+emailAddress+", moduleId: "+moduleId+", allUsers: "+allUsers);
			
			saveStringToFile("ModuleUpdate.txt", "START: "+m.getTitle(themeDisplay.getLocale())+" ("+m.getModuleId()+") "+", allUsers: "+allUsers+", email: "+emailAddress);
			
			
			if(allUsers!=null && allUsers.equals("true")){
				changes = ModuleResultLocalServiceUtil.updateAllUsers(m.getGroupId(), moduleId);
			}
			else if(allCourses!=null && allCourses.equals("true")){
				;//ModuleResultLocalServiceUtil.updateAllCoursesAllModulesAllUsers();
			}
			else if(!emailAddress.equals("")){
				User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), emailAddress);
				if(user!=null) {
					ModuleResultLocalServiceUtil.update(moduleId, user.getUserId());
				}
			}
			
			saveStringToFile("ModuleUpdate.txt", "END  : "+m.getTitle(themeDisplay.getLocale())+" ("+m.getModuleId()+")   cambios: "+changes);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void saveStringToFile(String fileName, String text){
		/*
		StringBuffer sb = new StringBuffer(PropsUtil.get("default.liferay.home"));
		sb.append(File.separator);
		sb.append("tomcat-6.0.29");
		sb.append(File.separator);
		sb.append("webapps");
		sb.append(File.separator);
		sb.append("custom_logs"); //Directorio para los ficheros.
		*/
		
		StringBuffer sb = new StringBuffer(PropsUtil.get("java.io.tmpdir"));
		sb.append(File.separator);
		sb.append("custom_logs"); //Directorio para los ficheros.
		
		File dir = new File(sb.toString());
	    if(!dir.exists()){
	    	dir.mkdir();
	    }
	    	    
	    //Nombre del informe
		sb.append(File.separator);
		sb.append(fileName);
		

		Calendar cal = Calendar.getInstance();

		try {
		 	BufferedWriter out = new BufferedWriter(new FileWriter(sb.toString(),true));
		 	out.append("\n"+cal.getTime()+" - "+text);
		 	out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}

}
