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
import com.liferay.lms.service.persistence.ModuleResultFinderUtil;
import com.liferay.lms.service.persistence.ModuleResultUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

	private static Log log = LogFactoryUtil.getLog(ModuleResultLocalServiceImpl.class);
	
	public ModuleResult getByModuleAndUser(long moduleId, long userId)throws SystemException {

		ModuleResult moduleResult = moduleResultPersistence.fetchBymu(userId, moduleId);
		return moduleResult;	
	}
	
	/**
	 * Get all module results of the module given.
	 * @param moduleId Id of the module
	 * @return List of module results of the module.
	 */
	public List<ModuleResult>  getByModuleId(long moduleId)  {

		List<ModuleResult> moduleResults = new ArrayList<ModuleResult>();
		try{
			moduleResults = moduleResultPersistence.findBym(moduleId);
		}catch(SystemException e){
			e.printStackTrace();
		}
		return moduleResults;	
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
	
	public List<ModuleResult> getByUserId(long userId){
		List<ModuleResult> res = new ArrayList<ModuleResult>();		
		try {
			res = ModuleResultUtil.findByUserId(userId);
		} catch (SystemException e) {
			e.printStackTrace();
		}				
		return res;
	}
	
	
	public long countByModule(long moduleId)
			throws SystemException {

			return moduleResultPersistence.countBym(moduleId);
		}

		/**
		 * Devuelve los estudiantes que han comenzado el m�dulo. Si se tienen ya los ids de los usuarios excluidos (profesores y editores) se
		 * deber� llamar a countByModuleOnlyStudents(long moduleId, long[] userIds)
		 * @param companyId id del company del curso
		 * @param courseGroupCreatedId id del group del curso
		 * @param moduleId id del m�dulo del que queremos los estudiantes que han comenzado
		 * @return n�mero de estudiantes que han iniciado el m�dulo
		 * @throws SystemException
		 */
		public long countByModuleOnlyStudents(long companyId, long courseGroupCreatedId, long moduleId) throws SystemException{
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
			long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
			return countStudentsByModuleIdUserExcludedIdsStarted(moduleId, userExcludedIds);
		}

		/**
		 * @deprecated Deprecado por eficiencia, se debe llamar a los m�todos countByModuleOnlyStudents(long companyId, long courseGroupCreatedId, long moduleId)
		 * o countByModuleOnlyStudents(long moduleId ,long[] userIds) ya que se calcula en base a los usuarios excluidos
		 * @param companyId id del company del curso
		 * @param courseGroupCreatedId id del group del curso
		 * @param moduleId  id del m�dulo del que queremos los estudiantes que han comenzado
		 * @param _students Lista de estudiantes, si viene vac�a se calcula dentro
		 * @return n�mero de estudiantes que han iniciado el modulo
		 * @throws SystemException
		 */
		@Deprecated
		public long countByModuleOnlyStudents(long companyId, long courseGropupCreatedId, long moduleId ,List<User> _students)
				throws SystemException {
			
			List<User> students = null;
			// Se prepara el metodo para recibir un Listado de estudiantes especificos, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
			if(Validator.isNotNull(_students) && _students.size() > 0)
				students = _students;
			else
				students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
			
			if(students != null && students.size() > 0){
				long[] userIds = new long[students.size()];
				for(int i = 0; i < students.size(); i++){
					userIds[i] = students.get(i).getUserId();
				}
				return moduleResultPersistence.countByModuleIdMultipleUserId(moduleId, userIds);
			}
			return 0;
		}

		
		public long countByModulePassed(long moduleId, boolean passed)
			throws SystemException {

			return moduleResultPersistence.countBymp(moduleId, passed);
		}
		
		/**
		 * Devuelve los estudiantes que han aprobado o suspendido (en el caso de suspenso no tiene en cuenta que hayan finalizado) el m�dulo. 
		 * Si se tienen ya los ids de los usuarios excluidos (profesores y editores) se deber� llamar a 
		 * countByModulePassedOnlyStudents(long moduleId, boolean passed, long[] userIds)
		 * @param companyId id del company del curso
		 * @param courseGroupCreatedId id del group del curso
		 * @param moduleId id del m�dulo del que queremos los estudiantes que han comenzado
		 * @param passed Si queremos los que han aprobado el modulo o no
		 * @return n�mero de estudiantes que han aprobado el modulo (en caso de passed = true) o de los que lo han suspendido o todav�a no lo han termiando (passed = false)
		 * @throws SystemException
		 */
		public long countByModulePassedOnlyStudents(long companyId, long courseGroupCreatedId, long moduleId, boolean passed) throws SystemException {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
			long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
			
			return countByModulePassedOnlyStudents(moduleId, passed, userExcludedIds);
		}
		
		/**
		 * @deprecated Deprecado por eficiencia, se debe llamar a los m�todos countByModulePassedOnlyStudents(long companyId, long courseGroupCreatedId, long moduleId, boolean passed)
		 * o countByModulePassedOnlyStudents(long moduleId, boolean passed, long[] userIds) ya que se calcula en base a los usuarios excluidos
		 * @param companyId id del company del curso
		 * @param courseGroupCreatedId id del group del curso
		 * @param moduleId  id del m�dulo del que queremos los estudiantes
		 * @param passed Si queremos los que han aprobado el modulo o no
		 * @param _students Lista de estudiantes, si viene vac�a se calcula dentro
		 * @return n�mero de estudiantes que han aprobado el curso (en caso de passed = true) o de los que lo han suspendido o todav�a no lo han termiando (passed = false)
		 * @throws SystemException
		 */
		@Deprecated
		public long countByModulePassedOnlyStudents(long companyId, long courseGroupCreatedId, long moduleId, boolean passed, List<User> _students)
				throws SystemException {

			List<User> students = null;
			// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
			if(Validator.isNotNull(_students) && _students.size() > 0)
				students = _students;
			else
				students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGroupCreatedId);
			
			if(students != null && students.size() > 0){
				long[] userIds = new long[students.size()];
				for(int i = 0; i < students.size(); i++){
					userIds[i] = students.get(i).getUserId();
				}
				return moduleResultPersistence.countByModuleIdPassedMultipleUserId(moduleId, passed, userIds);
			}
			return 0;
		}
		
		/**
		 * Devuelve los estudiantes que han aprobado o suspendido (en el caso de suspenso no tiene en cuenta que hayan finalizado o no) el m�dulo. 
		 * @param moduleId id del m�dulo del que queremos los estudiantes
		 * @param passed Si queremos los que han aprobado el modulo o no
		 * @param userExcludedIds ids de usuarios excluidos (profesores y editores) 
		 * @return n�mero de estudiantes que han aprobado el modulo (en caso de passed = true) o de los que lo han suspendido o todav�a no lo han termiando (passed = false)
		 * @throws SystemException
		 */
		public int countByModulePassedOnlyStudents(long moduleId, boolean passed, long[] userExcludedIds) throws SystemException{
			if(userExcludedIds != null && userExcludedIds.length > 0){
				return moduleResultPersistence.countByModuleIdPassedNotMultipleUserId(moduleId, passed, userExcludedIds);
			}else{
				return moduleResultPersistence.countBymp(moduleId, passed);
			}
		}
		
		/**
		 * Cuenta los estudiantes que han iniciado el modulo: solo llamar si se tiene la lista de usuarios excluidos
		 * @param moduleId id del m�dulo
		 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
		 * @return n�mero de estudiantes que han comenzado el modulo
		 * @throws SystemException
		 */
		public int countStudentsByModuleIdUserExcludedIdsStarted(long moduleId, long[] userExcludedIds) throws SystemException{
			
			if(userExcludedIds != null && userExcludedIds.length > 0){
				return moduleResultPersistence.countByModuleIdNotMultipleUserId(moduleId, userExcludedIds);

			}else{
				return moduleResultPersistence.countBym(moduleId);
			}
		}
		
		/**
		 * Cuenta los estudiantes que han finalizado el modulo: solo llamar si se tiene la lista de usuarios excluidos
		 * @param moduleId id del m�dulo
		 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
		 * @return n�mero de estudiantes que han finalizado el m�dulo
		 * @throws SystemException
		 */
		
		public int countStudentsByModuleIdUserExcludedIdsFinished(long moduleId, long[] userExcludedIds) throws SystemException{
			
			if(userExcludedIds != null && userExcludedIds.length > 0){
				return moduleResultPersistence.countByModuleIdNotMultipleUserIdFinished(moduleId, null, userExcludedIds);
			}else{
				return moduleResultPersistence.countByModuleIdFinished(moduleId, null);
			}
		}
		
		/**
		 * Cuenta los estudiantes que han iniciado el modulo, esta funci�n est� pensada para pasar una lista de estudiantes filtrada
		 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByModuleIdUserExcludedIdsStarted
		 * @param moduleId id del m�dulo
		 * @param userIds ids de los usuarios filtrados
		 * @return n�mero de estudiantes que han comenzado el modulo
		 * @throws SystemException
		 */
		public int countStudentsByModuleIdUserIdsStarted(long moduleId, long[] userIds) throws SystemException{
			
			if(userIds != null && userIds.length > 0){
				return moduleResultPersistence.countByModuleIdMultipleUserId(moduleId, userIds);

			}else{
				return 0;
			}
		}
		
		/**
		 * Cuenta los estudiantes que han finalizado el modulo, esta funci�n est� pensada para pasar una lista de estudiantes filtrada
		 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByModuleIdUserExcludedIdsFinished
		 * @param moduleId id del m�dulo
		 * @param userIds ids de los usuarios filtrados
		 * @return n�mero de estudiantes que han finalizado el m�dulo
		 * @throws SystemException
		 */
		
		public int countStudentsByModuleIdUserIdsFinished(long moduleId, long[] userIds) throws SystemException{
			
			if(userIds != null && userIds.length > 0){
				return moduleResultPersistence.countByModuleIdMultipleUserIdFinished(moduleId, null, userIds);
			}else{
				return 0;
			}
		}
		
		/**
		 * Cuenta los estudiantes que han finalizado el modulo y lo hayan aprobado
		 * @param moduleId id del m�dulo
		 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
		 * @return n�mero de estudiantes que han finalizado y aprobado el m�dulo
		 * @throws SystemException
		 */
		
		public int countStudentsByModuleIdUserExcludedIdsPassed(long moduleId, long[] userExcludedIds) throws SystemException{
			
			if(userExcludedIds != null && userExcludedIds.length > 0){
				return moduleResultPersistence.countByModuleIdPassedNotMultipleUserIdFinished(moduleId, true, null, userExcludedIds);
			}else{
				return moduleResultPersistence.countByModuleIdPassedFinished(moduleId, true, null);
			}
		}
		
		/**
		 * Cuenta los estudiantes que han finalizado el modulo y lo hayan suspendido
		 * @param moduleId id del m�dulo
		 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
		 * @return n�mero de estudiantes que han finalizado y suspendido el m�dulo
		 * @throws SystemException
		 */
		
		public int countStudentsByModuleIdUserExcludedIdsFailed(long moduleId, long[] userExcludedIds) throws SystemException{
			
			if(userExcludedIds != null && userExcludedIds.length > 0){
				return moduleResultPersistence.countByModuleIdPassedNotMultipleUserIdFinished(moduleId, false, null, userExcludedIds);

			}else{
				return moduleResultPersistence.countByModuleIdPassedFinished(moduleId, false, null);
			}
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
			
			moduleResult= getAndCreateIfNotExists( userId,  moduleId,lactr.getStartDate());
			log.debug("****Modulo "+learningActivity.getModuleId() );
			log.debug("****REsult End Date "+lactr.getEndDate());
			if (learningActivity.getModuleId() > 0 && lactr.getEndDate()!=null) 
			{
				log.debug("****Recalculamos Modulo");
				calculateModuleResult(moduleResult);
			}
			
		}
	
	public int updateAllUsers(long groupId, long moduleId) throws PortalException, SystemException {
		
		//Obtenemos la lista de users del curso.
		//List<User> usersList = UserLocalServiceUtil.getUserGroupUsers(groupId);
		List<User> usersList = GroupUtil.getUsers(groupId);
				
		int changes = 0;
		
		log.debug("groupId: "+groupId+", moduleId: "+moduleId+", alumnos en el curso: "+usersList.size());
		
		log.debug("........ START ............");
		for(User user : usersList){
			if(update(moduleId, user.getUserId())){
				changes++;
			}
		}
		log.debug("Cambiaron "+ changes +" de "+usersList.size()+" alumnos.");
		log.debug("........ END ............");
		
		return changes;
	}
	
	public void updateAllCoursesAllModulesAllUsers() throws PortalException, SystemException {
		
		int changes = 0;
		
		List<ModuleResult> moduleResultList = ModuleResultLocalServiceUtil.getModuleResults(0, ModuleResultLocalServiceUtil.getModuleResultsCount());
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		log.debug("........ START ............");
		
		int restantes = moduleResultList.size();
		
		for(ModuleResult mo : moduleResultList){
						
			log.debug("   :: "+mo.getModuleId()+" :: Restantes: "+ (restantes--));
			
			if(update(mo.getModuleId(), mo.getUserId())){
				changes++;
			}
			
			log.debug("-----------------------------------------------------------------------");
			
		}
		log.debug("  Cambiaron "+ changes +" alumnos.");
		
		end = Calendar.getInstance();
		log.debug(" ## Time start ## "+start.getTime());
		log.debug(" ## Time end   ## "+end.getTime());
		log.debug("........ END ............");
	}
	
	public boolean update(long moduleId, long userId) throws PortalException, SystemException {
	
		boolean passedModule = true;
		long totalActivities = 0;
		long activitiesPassed = 0;
		
		ModuleResult moduleResult = null;
		
		//Actualizar el resultado del modulo en bd.
		
		log.debug("User::"+userId+"::moduleId"+moduleId);
		//Obtenemos el moduleResult que tiene el usuario.Si no lo tiene, no lo creamos.
		if (moduleResultPersistence.countBymu(userId, moduleId) > 0) {
			moduleResult = moduleResultPersistence.findBymu(userId, moduleId);
		}else{
			moduleResult = moduleResultPersistence.create(counterLocalService.increment(ModuleResult.class.getName()));
			moduleResult.setModuleId(moduleId);
			moduleResult.setPassed(false);
			moduleResult.setUserId(userId);
			moduleResult.setResult(0);
			moduleResult.setStartDate(new Date());
		}

		if(moduleResult!=null){
			log.debug("Update!");
			
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

			//Indicamos la media y el resultado del modulo.
			long result = 0;
			if(totalActivities > 0){
				
				result = activitiesPassed * 100 / totalActivities;
			}
			
			//Solo actualizamos si cambia el resultado.
			if(moduleResult.getResult() < result){
				
				//Traza
				User user = UserLocalServiceUtil.getUser(userId);
				//log.debug("    *** USER: "+ user.getFullName() +" ("+ userId +")  ***\n           resultOLD: "+moduleResult.getResult()+", passedOLD: "+moduleResult.getPassed()+"\n           resultNEW: "+result+", passedNEW: "+passedModule);
				Module m = ModuleLocalServiceUtil.getModule(moduleId);
				Course c = CourseLocalServiceUtil.getCourseByGroupCreatedId(m.getGroupId());
				
				String text = (moduleResult.getResult()<result)?"Sube":"Baja";
				try{
					log.debug(c.getTitle(Locale.getDefault()) +" ("+ c.getCourseId() +")|"+m.getTitle(Locale.getDefault())+" ("+m.getModuleId()+")|"+moduleResult.getMrId()+"|"+user.getFullName() +" ("+ userId +")|"+moduleResult.getPassed()+"|"+passedModule+"|"+moduleResult.getResult() +"|"+result+"|"+text);
				}catch (Exception e){log.debug("ERROR: moduleID: "+moduleId+", userID: "+userId);}
				
				moduleResult.setResult(result);
				moduleResult.setPassed(passedModule);

				//Update en la bd.
				moduleResultPersistence.update(moduleResult, false);
				
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
			}
		}

		return false;
			
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

		//Indicamos la media y el resultado del m�dulo.
		long result = 0;
		if(totalActivities > 0){
			
			result = activitiesPassed * 100 / totalActivities;
		}
		if(result>0)
		{
			//Vamos a ver si tiene un sistema de evaluaci?e m?o 
			CourseEvalRegistry cer=new CourseEvalRegistry();
			Course course=courseLocalService.fetchByGroupCreatedId(module.getGroupId());
			long courseEvalTypeId=course.getCourseEvalId();
			CourseEval ceval=cer.getCourseEval(courseEvalTypeId);
			if(ceval.hasModuleResultCalculator())
			{
				result=ceval.calculateModuleResult(module.getModuleId(), moduleResult.getUserId());
			}
		}
		
		//S�lo actualizamos si cambia el resultado.
		
		log.debug("Vamos a ver si actualizamos...");
		log.debug("Module result "+moduleResult.getResult());
		log.debug("Result "+result);
		log.debug("PassedModule "+passedModule);
		log.debug("Module Result passed "+moduleResult.getPassed());
		
		if(moduleResult.getResult() <= result || (passedModule&&!moduleResult.getPassed()))
		{	
			
			log.debug("Actualizamos curso");
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
			
			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), ModuleResult.class.getName(), 
					moduleResult.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			}else{
				if(moduleResult!=null){
					if(module!=null){
						AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), ModuleResult.class.getName(), 
								moduleResult.getPrimaryKey(), module.getUserId(), AuditConstants.UPDATE, null);
					}
				}
				
			}
		}
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
			//Detect if is the first module
			try 
			{
				Module module=moduleLocalService.getModule(moduleId);
		        List<Module> modules= moduleLocalService.findAllInGroup(module.getGroupId());
			    boolean isthefirst=true;
		        for(Module module2:modules)
			    {
			    	if(moduleResultPersistence.countBymu(userId, module2.getModuleId()) > 0)
			    	{
			    		isthefirst=false;
			    		break;
			    	}
			    }
		        if(isthefirst)
		        {
		        	Course course=courseLocalService.fetchByGroupCreatedId(module.getGroupId());
		        	if(course!=null)
		        	{
		        		AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
		        			course.getCourseId(), userId, AuditConstants.STARTED, null);
				
		        	}
		        }
				moduleResult = moduleResultPersistence.create(counterLocalService.increment(ModuleResult.class.getName()));
				moduleResult.setModuleId(moduleId);
				moduleResult.setPassed(false);
				moduleResult.setUserId(userId);
				moduleResult.setStartDate(startDate);
				moduleResult.setResult(0);
				moduleResultPersistence.update(moduleResult, true);
			} 
			catch (PortalException e) 
			{
				throw new SystemException(e);
			}

		}
		return moduleResult;
	}
}
