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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
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
	 * No deberï¿½a haber nunca mï¿½s de un result para el mismo usuario y modulo.
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

private ModuleResult getAndCreateIfNotExists(long userId, long moduleId,Date startDate) throws SystemException
{
	ModuleResult moduleResult = null;
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
		moduleResult.setStartDate(startDate);
		moduleResult.setResult(0);
		moduleResultPersistence.update(moduleResult, true);

	}
	return moduleResult;
}
	public void update(LearningActivityResult lactr)
		throws PortalException, SystemException {

		ModuleResult moduleResult = null;
		long actId = lactr.getActId();
		long userId = lactr.getUserId();
		LearningActivity learningActivity = learningActivityLocalService.getLearningActivity(actId);
		// Si el Weight es mayor que cero (obligatoria) entonces calcula, sino
		// no.
		// Se elimina la restricciï¿½n de calcular solo en las obligatorias, se
		// calcula ent todas las que se terminen.
		long moduleId = learningActivity.getModuleId();
		
		moduleResult= getAndCreateIfNotExists( userId,  moduleId,lactr.getStartDate());
		
		if (learningActivity.getModuleId() > 0 && /*
												 * learningActivity.
												 * getWeightinmodule()>0 &&
												 */lactr.getEndDate()!=null) 
		{
			
			calculateModuleResult(moduleResult);
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
	private void calculateModuleResult(ModuleResult moduleResult) throws PortalException, SystemException
	{
		List<LearningActivity> learnActList = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleResult.getModuleId());
		Module module=moduleLocalService.getModule(moduleResult.getModuleId());
		boolean passedModule = true;
		long totalActivities = 0;
		long activitiesPassed = 0;
        Date passedDate=new Date(0);
		for(LearningActivity activity : learnActList){
			
			//Si la actividad no es opcional.
			if(activity.getWeightinmodule() != 0){
				
				totalActivities++;
				
				LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), moduleResult.getUserId());

				if(result != null && result.isPassed())
				{
					
					activitiesPassed++;
					if(result.getEndDate()!=null)
					{
						if(passedDate.before(result.getEndDate()))
						{
							passedDate=result.getEndDate();
						}
					}
					 
				} else {
					
					passedModule = false;
					
				}
				
			}
			
		}
		
		if(learnActList.size() == 0){
			passedModule = false;
		}

		//Indicamos la media y el resultado del mï¿½dulo.
		long result = 0;
		if(totalActivities > 0){
			
			result = activitiesPassed * 100 / totalActivities;
		}
		if(result>0)
		{
			//Vamos a ver si tiene un sistema de evaluación de módulo 
			CourseEvalRegistry cer=new CourseEvalRegistry();
			Course course=courseLocalService.fetchByGroupCreatedId(module.getGroupId());
			long courseEvalTypeId=course.getCourseEvalId();
			CourseEval ceval=cer.getCourseEval(courseEvalTypeId);
			if(ceval.hasModuleResultCalculator())
			{
				result=ceval.calculateModuleResult(module.getModuleId(), moduleResult.getUserId());
			}
		}
		
		//Sï¿½lo actualizamos si cambia el resultado.
		if(moduleResult.getResult() < result)
		{	
			moduleResult.setResult(result);
			if(moduleResult.getPassed()==false)
			{
				moduleResult.setPassed(passedModule);
				if(passedModule==true)
				{
					moduleResult.setPassedDate(passedDate);
				}
			}
			//Update en la bd.
			moduleResultPersistence.update(moduleResult, true);
			//Actualizar el resultado del curso.
			courseResultLocalService.update(moduleResult);
		}
	}
	
	public Date calculateModuleResultStartDate(long moduleId, long userId) throws PortalException, SystemException
	{
		List<LearningActivity> learnActList = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
		Date startDate=null;
		for(LearningActivity activity : learnActList){
			
			//Si la actividad no es opcional.
			if(activity.getWeightinmodule() != 0)
			{						
				LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), userId);
				if(result != null)
				{
					if(startDate==null)
					{
						startDate=result.getStartDate();
					}
					else
					{
						if(result.getStartDate()!=null&&result.getStartDate().before(startDate))
						{
							startDate=result.getStartDate();
						}
					}
				} 			
			}
		}
		return startDate;
	}
	public boolean update(long moduleId, long userId) throws PortalException, SystemException 
	{
	
		Date startDate=calculateModuleResultStartDate(moduleId, userId);
		
		ModuleResult moduleResult = null;
		
		if(startDate!=null)
		{
			moduleResult=getAndCreateIfNotExists(userId, moduleId, startDate);
		}
		if(moduleResult!=null)
		{
	        calculateModuleResult(moduleResult);

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), ModuleResult.class.getName(), 
					moduleResult.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			}else{
				Module m = ModuleLocalServiceUtil.getModule(moduleId);
				Course c = CourseLocalServiceUtil.getCourseByGroupCreatedId(m.getGroupId());
				if(c!=null){
					AuditingLogFactory.audit(c.getCompanyId(), c.getGroupId(), ModuleResult.class.getName(), 
							moduleResult.getPrimaryKey(), c.getUserId(), AuditConstants.UPDATE, null);
				}
			}
			return true;
		}
		

		return false;

			
	}
	
	
	
}
