package com.liferay.lms;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;


public class courseupdateresult extends MVCPortlet {
 
	@SuppressWarnings("unchecked")
	@ProcessAction(name = "updateResult")
	public void updateResult(ActionRequest request, ActionResponse response) throws Exception {
		
		String courseId = request.getParameter("courseId");
		
		if(courseId != null && !courseId.equals("")){
			
			int changes = 0;
			String trace = "";
			
			Course course= CourseLocalServiceUtil.getCourse(Long.valueOf(courseId));
						
			try {
				//Si nos llega un curso que existe.
				if(course!=null)
				{
					int calculated = 0;
					
					trace += " course: "+course.getTitle(Locale.getDefault())+" ("+ course.getCourseId() +")\n"; 
					
					//Obtenemos todos los usuarios del curso.
					List<User> usersList = UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId());
					
					//Obtenemos todos los modulos del curso.
					List<Module> moduleList = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
					
					for(User user:usersList){
						
						calculated++;
						if(calculated % 200 == 0){
							System.out.print(".");
						}
						
						boolean passed = true;
						long passedCount = 0;
						long result = 0;
						
						for(Module mod:moduleList){
														
							//Obtenemos todos los resultados que ha obtenidos el usuario del curso.
							ModuleResult moduleResult = ModuleResultLocalServiceUtil.getByModuleAndUser(mod.getModuleId(), user.getUserId());
							
							//trace += "     module: "+mod.getTitle(Locale.getDefault())+" ("+ mod.getModuleId() +")\n"; 
							if(moduleResult != null && moduleResult.isPassed()){
								passedCount++;
								
								//trace += "       moduleResult: "+moduleResult.getResult()+" ("+moduleResult.isPassed() +")\n";
							}else{
								passed = false;
							}
								
						} //end for Module
						
						if(moduleList.size() > 0)
						{
							//Calculamos el porcentaje del curso realizado.
							result = 100 * passedCount / moduleList.size();
						}
						
						//Actualizamos el resultado del curso en base de datos.
						CourseResult courseResult;
						try {
							
							courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(),user.getUserId());

							//Si no exist�a el resultado del curso.
							if(courseResult==null){
								courseResult = CourseResultLocalServiceUtil.createCourseResult(CounterLocalServiceUtil.increment(CourseResult.class.getName()));
								courseResult.setStartDate(new Date());
								courseResult.setUserId(user.getUserId());
								courseResult.setCourseId(course.getCourseId());
								
								//CourseResultLocalServiceUtil.updateCourseResult(courseResult, false);
							}
							
							//Solo actualizamos si mejora el resultado o si pasa de suspenso a aprobado.
							if(courseResult.getResult() < result ){
								//Asignamos los nuevos valores.
								courseResult.setResult(result);
								courseResult.setPassed(passed);
								courseResult.setPassedDate(new Date());

								CourseResultLocalServiceUtil.update(courseResult);
								
								changes++;
								
								//trace += "\n  ** updated!"+", changes: "+changes+"\n";
								
								trace += "    user: "+user.getFullName()+" ("+ user.getUserId() +")\n"; 
								trace += "      result: "+result+" = 100 * "+passedCount +" / "+moduleList.size()+"\n";
								trace += "        courseResult: "+courseResult.getResult()+", passed: "+ courseResult.getPassed() +"\n";
							}
							
						} catch (Exception e) {
							trace += "  -- ERROR: "+e.getMessage()+"\n";
							e.printStackTrace();
						}

					}// fin for Users	
				}//fin if course
			} catch (Exception e) {
				trace += "  -- ERROR: "+e.getMessage()+"\n";
				e.printStackTrace();
			}	

			LiferaylmsUtil.saveStringToFile("CourseUpdate.txt", trace+"\n Cambios: "+changes);
		}
		
		
	}

}
