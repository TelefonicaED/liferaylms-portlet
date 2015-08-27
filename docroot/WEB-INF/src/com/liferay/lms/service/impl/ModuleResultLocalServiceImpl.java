/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.ModuleResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupUtil;

/**
 * The implementation of the module result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ModuleResultLocalService} interface.
 * </p>
 * 
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ModuleResultLocalServiceUtil} to access the module result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.ModuleResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.ModuleResultLocalServiceUtil
 */
public class ModuleResultLocalServiceImpl extends ModuleResultLocalServiceBaseImpl {

	public ModuleResult getByModuleAndUser(long moduleId, long userId)
		throws SystemException {

		ModuleResult moduleResult = moduleResultPersistence.fetchBymu(userId, moduleId);
		return moduleResult;	
	}

	/**
	 * No deber�a haber nunca m�s de un result para el mismo usuario y modulo.
	 * Se hace para eliminar los duplicados.
	 * @param moduleId
	 * @param userId
	 * @return La lista de todos los moduleresult del usuario
	 * @throws SystemException
	 */
	public List<ModuleResult> getListModuleResultByModuleAndUser(long moduleId, long userId) throws SystemException {
				
		List<ModuleResult> res = new ArrayList<ModuleResult>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(ModuleResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId));
	
		List<ModuleResult> results = moduleResultPersistence.findWithDynamicQuery(consulta);

		if(results!=null && !results.isEmpty()){
			res = results;
		}
				
		return res;
	}
	
	public long countByModule(long moduleId)
		throws SystemException {

		return moduleResultPersistence.countBym(moduleId);
	}

	public long countByModuleOnlyStudents(long companyId, long courseGropupCreatedId, long moduleId)
			throws SystemException {
		
		long res = 0;
		List<User> students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(ModuleResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId));
		
		if(Validator.isNotNull(students) && students.size() > 0) {
			Criterion criterion = null;
			for (int i = 0; i < students.size(); i++) {
				if(i==0) {
					criterion = RestrictionsFactoryUtil.like("userId", students.get(i).getUserId());
				} else {
					criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("userId", students.get(i).getUserId()));
				}
			}
			if(Validator.isNotNull(criterion)) {
				consulta.add(criterion);
				
				List<ModuleResult> results = moduleResultPersistence.findWithDynamicQuery(consulta);
				if(results!=null && !results.isEmpty()) {
					res = results.size();
				}
			}
		}
		
		return res;
	}

	
	public long countByModulePassed(long moduleId, boolean passed)
		throws SystemException {

		return moduleResultPersistence.countBymp(moduleId, passed);
	}
	
	
	public long countByModulePassedOnlyStudents(long companyId, long courseGropupCreatedId, long moduleId, boolean passed)
			throws SystemException {

		long res = 0;
		List<User> students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(ModuleResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId));
		
		if(Validator.isNotNull(students) && students.size() > 0) {
			Criterion criterion = null;
			for (int i = 0; i < students.size(); i++) {
				if(i==0) {
					criterion = RestrictionsFactoryUtil.like("userId", students.get(i).getUserId());
				} else {
					criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("userId", students.get(i).getUserId()));
				}
			}
			if(Validator.isNotNull(criterion)) {
				criterion=RestrictionsFactoryUtil.and(criterion,
						RestrictionsFactoryUtil.eq("passed",new Boolean (true)));
				
				consulta.add(criterion);
				
				List<ModuleResult> results = moduleResultPersistence.findWithDynamicQuery(consulta);
				if(results!=null && !results.isEmpty()) {
					res = results.size();
				}
			}
		}
		
		return res;
	}


	public void update(LearningActivityResult lactr)
		throws PortalException, SystemException {

		ModuleResult moduleResult = null;
		long actId = lactr.getActId();
		long userId = lactr.getUserId();
		LearningActivity learningActivity = learningActivityLocalService.getLearningActivity(actId);
		// Si el Weight es mayor que cero (obligatoria) entonces calcula, sino
		// no.
		// Se elimina la restricci�n de calcular solo en las obligatorias, se
		// calcula ent todas las que se terminen.
		long moduleId = learningActivity.getModuleId();
		if (moduleResultPersistence.countBymu(userId, moduleId) > 0) 
		{
			moduleResult = moduleResultPersistence.fetchBymu(userId, moduleId, false);
		}
		else 
		{
			moduleResult = moduleResultPersistence.create(counterLocalService.increment(ModuleResult.class.getName()));
			moduleResult.setModuleId(moduleId);
			moduleResult.setPassed(false);
			moduleResult.setUserId(userId);
			moduleResult.setStartDate(lactr.getStartDate());
			moduleResult.setResult(0);
			moduleResultPersistence.update(moduleResult, true);

		}
		if (learningActivity.getModuleId() > 0 && /*
												 * learningActivity.
												 * getWeightinmodule()>0 &&
												 */lactr.getEndDate()!=null) 
		{
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LearningActivity.class, (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					"portletClassLoader"));
			Criterion crit;
			crit = PropertyFactoryUtil.forName("weightinmodule").gt(new Long(0));
			dynamicQuery.add(crit);
			Criterion crit2;
			crit2 = PropertyFactoryUtil.forName("moduleId").eq(moduleId);
			dynamicQuery.add(crit2);
			java.util.List<LearningActivity> activities = learningActivityLocalService.dynamicQuery(dynamicQuery);
			long passedNumber = 0;
			for (LearningActivity activity : activities) {
				if (learningActivityResultLocalService.existsLearningActivityResult(activity.getActId(), userId)) {
					if (learningActivityResultLocalService.getByActIdAndUserId(activity.getActId(), userId).getPassed()) {
						passedNumber++;
					}
				}
			}
			if (activities.size() > 0) {
				moduleResult.setResult(100 * passedNumber / activities.size());
			}
			if (passedNumber == activities.size()) {
				moduleResult.setResult(100);
				moduleResult.setPassed(true);
				moduleResult.setPassedDate(lactr.getEndDate());
			}
			moduleResultPersistence.update(moduleResult, true);
			courseResultLocalService.update(moduleResult);
			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), ModuleResult.class.getName(), 
					moduleResult.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			}else{
				if(moduleResult!=null){
					Module module = modulePersistence.fetchByPrimaryKey(moduleResult.getModuleId());
					if(module!=null){
						AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), ModuleResult.class.getName(), 
								moduleResult.getPrimaryKey(), module.getUserId(), AuditConstants.UPDATE, null);
					}
				}
				
			}
			
			
		}
		
	}
	public int updateAllUsers(long groupId, long moduleId) throws PortalException, SystemException {
		
		//Obtenemos la lista de users del curso.
		//List<User> usersList = UserLocalServiceUtil.getUserGroupUsers(groupId);
		List<User> usersList = GroupUtil.getUsers(groupId);
				
		int changes = 0;
		
		System.out.println("groupId: "+groupId+", moduleId: "+moduleId+", alumnos en el curso: "+usersList.size());
		
		System.out.println("........ START ............");
		for(User user : usersList){
			if(update(moduleId, user.getUserId())){
				changes++;
			}
		}
		System.out.println("Cambiaron "+ changes +" de "+usersList.size()+" alumnos.");
		System.out.println("........ END ............");
		
		return changes;
	}
	
	public void updateAllCoursesAllModulesAllUsers() throws PortalException, SystemException {
		
		int changes = 0;
		
		List<ModuleResult> moduleResultList = ModuleResultLocalServiceUtil.getModuleResults(0, ModuleResultLocalServiceUtil.getModuleResultsCount());
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		System.out.println("........ START ............");
		
		int restantes = moduleResultList.size();
		
		for(ModuleResult mo : moduleResultList){
						
			System.out.println("   :: "+mo.getModuleId()+" :: Restantes: "+ (restantes--));
			
			if(update(mo.getModuleId(), mo.getUserId())){
				changes++;
			}
			
			System.out.println("-----------------------------------------------------------------------");
			
		}
		System.out.println("  Cambiaron "+ changes +" alumnos.");
		
		end = Calendar.getInstance();
		System.out.println(" ## Time start ## "+start.getTime());
		System.out.println(" ## Time end   ## "+end.getTime());
		System.out.println("........ END ............");
	}
	
	public boolean update(long moduleId, long userId) throws PortalException, SystemException {
	
		boolean passedModule = true;
		long totalActivities = 0;
		long activitiesPassed = 0;
		
		ModuleResult moduleResult = null;
		
		//Actualizar el resultado del modulo en bd.
		
		System.out.println("User::"+userId+"::moduleId"+moduleId);
		//Obtenemos el moduleResult que tiene el usuario.Si no lo tiene, no lo creamos.
		if (moduleResultPersistence.countBymu(userId, moduleId) > 0) {
			moduleResult = moduleResultPersistence.findBymu(userId, moduleId);
		}else{
			moduleResult = moduleResultPersistence.create(counterLocalService.increment(ModuleResult.class.getName()));
			moduleResult.setModuleId(moduleId);
			moduleResult.setPassed(false);
			moduleResult.setUserId(userId);
			moduleResult.setResult(0);
		}

		if(moduleResult!=null){
			System.out.println("Update!");
			
			List<LearningActivity> learnActList = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);

			for(LearningActivity activity : learnActList){
				
				//Si la actividad no es opcional.
				if(activity.getWeightinmodule() != 0){
					
					totalActivities++;
					
					LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), userId);

					if(result != null && result.isPassed()){
						 
						activitiesPassed++;
						 
					} else {
						
						passedModule = false;
						
					}
					
				}
				
			}
			
			if(learnActList.size() == 0){
				passedModule = false;
			}

			//Indicamos la media y el resultado del m�dulo.
			long result = 0;
			if(totalActivities > 0){
				
				result = activitiesPassed * 100 / totalActivities;
			}
			
			//S�lo actualizamos si cambia el resultado.
			if(moduleResult.getResult() < result){
				
				//Traza
				User user = UserLocalServiceUtil.getUser(userId);
				//System.out.println("    *** USER: "+ user.getFullName() +" ("+ userId +")  ***\n           resultOLD: "+moduleResult.getResult()+", passedOLD: "+moduleResult.getPassed()+"\n           resultNEW: "+result+", passedNEW: "+passedModule);
				Module m = ModuleLocalServiceUtil.getModule(moduleId);
				Course c = CourseLocalServiceUtil.getCourseByGroupCreatedId(m.getGroupId());
				
				String text = (moduleResult.getResult()<result)?"Sube":"Baja";
				try{
					System.out.println(c.getTitle(Locale.getDefault()) +" ("+ c.getCourseId() +")|"+m.getTitle(Locale.getDefault())+" ("+m.getModuleId()+")|"+moduleResult.getMrId()+"|"+user.getFullName() +" ("+ userId +")|"+moduleResult.getPassed()+"|"+passedModule+"|"+moduleResult.getResult() +"|"+result+"|"+text);
				}catch (Exception e){System.out.println("ERROR: moduleID: "+moduleId+", userID: "+userId);}
				
				moduleResult.setResult(result);
				moduleResult.setPassed(passedModule);

				//Update en la bd.
				moduleResultPersistence.update(moduleResult, true);
				
				//Actualizar el resultado del curso.
				courseResultLocalService.update(moduleResult);

				//auditing
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				if(serviceContext!=null){
					AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), ModuleResult.class.getName(), 
						moduleResult.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
				}else{
					if(c!=null){
						AuditingLogFactory.audit(c.getCompanyId(), c.getGroupId(), ModuleResult.class.getName(), 
								moduleResult.getPrimaryKey(), c.getUserId(), AuditConstants.UPDATE, null);
					}
				}
				
				return true;
			}else if(moduleResult.getResult() > result){
				User user = UserLocalServiceUtil.getUser(userId);
				Module m = ModuleLocalServiceUtil.getModule(moduleId);
				Course c = CourseLocalServiceUtil.getCourseByGroupCreatedId(m.getGroupId());
				
				System.out.println(c.getTitle(Locale.getDefault()) +" ("+ c.getCourseId() +")|"+m.getTitle(Locale.getDefault())+" ("+m.getModuleId()+")|"+moduleResult.getMrId()+"|"+user.getFullName() +" ("+ userId +")|"+moduleResult.getPassed()+"|"+passedModule+"|"+moduleResult.getResult() +"|"+result+"|Baja");
			}
		}

		return false;

			
	}
	
	
	
}
