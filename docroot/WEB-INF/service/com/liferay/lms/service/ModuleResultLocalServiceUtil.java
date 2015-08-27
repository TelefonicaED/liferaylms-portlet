/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.lms.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the module result local service. This utility wraps {@link com.liferay.lms.service.impl.ModuleResultLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see ModuleResultLocalService
 * @see com.liferay.lms.service.base.ModuleResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.ModuleResultLocalServiceImpl
 * @generated
 */
public class ModuleResultLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.ModuleResultLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the module result to the database. Also notifies the appropriate model listeners.
	*
	* @param moduleResult the module result
	* @return the module result that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult addModuleResult(
		com.liferay.lms.model.ModuleResult moduleResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addModuleResult(moduleResult);
	}

	/**
	* Creates a new module result with the primary key. Does not add the module result to the database.
	*
	* @param mrId the primary key for the new module result
	* @return the new module result
	*/
	public static com.liferay.lms.model.ModuleResult createModuleResult(
		long mrId) {
		return getService().createModuleResult(mrId);
	}

	/**
	* Deletes the module result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mrId the primary key of the module result
	* @return the module result that was removed
	* @throws PortalException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult deleteModuleResult(
		long mrId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteModuleResult(mrId);
	}

	/**
	* Deletes the module result from the database. Also notifies the appropriate model listeners.
	*
	* @param moduleResult the module result
	* @return the module result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult deleteModuleResult(
		com.liferay.lms.model.ModuleResult moduleResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteModuleResult(moduleResult);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.lms.model.ModuleResult fetchModuleResult(
		long mrId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchModuleResult(mrId);
	}

	/**
	* Returns the module result with the primary key.
	*
	* @param mrId the primary key of the module result
	* @return the module result
	* @throws PortalException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult getModuleResult(long mrId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getModuleResult(mrId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the module results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @return the range of module results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.ModuleResult> getModuleResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getModuleResults(start, end);
	}

	/**
	* Returns the number of module results.
	*
	* @return the number of module results
	* @throws SystemException if a system exception occurred
	*/
	public static int getModuleResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getModuleResultsCount();
	}

	/**
	* Updates the module result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param moduleResult the module result
	* @return the module result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult updateModuleResult(
		com.liferay.lms.model.ModuleResult moduleResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateModuleResult(moduleResult);
	}

	/**
	* Updates the module result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param moduleResult the module result
	* @param merge whether to merge the module result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the module result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult updateModuleResult(
		com.liferay.lms.model.ModuleResult moduleResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateModuleResult(moduleResult, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.liferay.lms.model.ModuleResult getByModuleAndUser(
		long moduleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByModuleAndUser(moduleId, userId);
	}

	/**
	* No deberÃ¯Â¿Â½a haber nunca mÃ¯Â¿Â½s de un result para el mismo usuario y modulo.
	* Se hace para eliminar los duplicados.
	*
	* @param moduleId
	* @param userId
	* @return La lista de todos los moduleresult del usuario
	* @throws SystemException
	*/
	public static java.util.List<com.liferay.lms.model.ModuleResult> getListModuleResultByModuleAndUser(
		long moduleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getListModuleResultByModuleAndUser(moduleId, userId);
	}

	public static long countByModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByModule(moduleId);
	}

	public static long countByModuleOnlyStudents(long companyId,
		long courseGropupCreatedId, long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countByModuleOnlyStudents(companyId, courseGropupCreatedId,
			moduleId);
	}

	public static long countByModulePassed(long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByModulePassed(moduleId, passed);
	}

	public static long countByModulePassedOnlyStudents(long companyId,
		long courseGropupCreatedId, long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countByModulePassedOnlyStudents(companyId,
			courseGropupCreatedId, moduleId, passed);
	}

	public static void update(
		com.liferay.lms.model.LearningActivityResult lactr)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().update(lactr);
	}

	public static int updateAllUsers(long groupId, long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAllUsers(groupId, moduleId);
	}

	public static void updateAllCoursesAllModulesAllUsers()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateAllCoursesAllModulesAllUsers();
	}

	public static boolean update(long moduleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(moduleId, userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static ModuleResultLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ModuleResultLocalService.class.getName());

			if (invokableLocalService instanceof ModuleResultLocalService) {
				_service = (ModuleResultLocalService)invokableLocalService;
			}
			else {
				_service = new ModuleResultLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(ModuleResultLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(ModuleResultLocalService service) {
	}

	private static ModuleResultLocalService _service;
}