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
 * The utility for the module local service. This utility wraps {@link com.liferay.lms.service.impl.ModuleLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see ModuleLocalService
 * @see com.liferay.lms.service.base.ModuleLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.ModuleLocalServiceImpl
 * @generated
 */
public class ModuleLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.ModuleLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the module to the database. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module addModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addModule(module);
	}

	/**
	* Creates a new module with the primary key. Does not add the module to the database.
	*
	* @param moduleId the primary key for the new module
	* @return the new module
	*/
	public static com.liferay.lms.model.Module createModule(long moduleId) {
		return getService().createModule(moduleId);
	}

	/**
	* Deletes the module with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param moduleId the primary key of the module
	* @return the module that was removed
	* @throws PortalException if a module with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module deleteModule(long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteModule(moduleId);
	}

	/**
	* Deletes the module from the database. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module deleteModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteModule(module);
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

	public static com.liferay.lms.model.Module fetchModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchModule(moduleId);
	}

	/**
	* Returns the module with the primary key.
	*
	* @param moduleId the primary key of the module
	* @return the module
	* @throws PortalException if a module with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module getModule(long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getModule(moduleId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the module with the UUID in the group.
	*
	* @param uuid the UUID of module
	* @param groupId the group id of the module
	* @return the module
	* @throws PortalException if a module with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module getModuleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getModuleByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the modules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of modules
	* @param end the upper bound of the range of modules (not inclusive)
	* @return the range of modules
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Module> getModules(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getModules(start, end);
	}

	/**
	* Returns the number of modules.
	*
	* @return the number of modules
	* @throws SystemException if a system exception occurred
	*/
	public static int getModulesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getModulesCount();
	}

	/**
	* Updates the module in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module updateModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateModule(module);
	}

	/**
	* Updates the module in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @param merge whether to merge the module with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the module that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Module updateModule(
		com.liferay.lms.model.Module module, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateModule(module, merge);
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

	public static java.util.List<com.liferay.lms.model.Module> findAllInUser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUser(userId);
	}

	public static java.util.List<com.liferay.lms.model.Module> findAllInUser(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUser(userId, orderByComparator);
	}

	public static java.util.List<com.liferay.lms.model.Module> findAllInGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInGroup(groupId);
	}

	public static java.util.List<com.liferay.lms.model.Module> findAllInGroup(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInGroup(groupId, orderByComparator);
	}

	public static com.liferay.lms.model.Module findFirstInGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findFirstInGroup(groupId);
	}

	public static com.liferay.lms.model.Module findFirstInGroup(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findFirstInGroup(groupId, orderByComparator);
	}

	public static int countInGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countInGroup(groupId);
	}

	public static java.util.List<com.liferay.lms.model.Module> findAllInUserAndGroup(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findAllInUserAndGroup(userId, groupId);
	}

	public static java.util.List<com.liferay.lms.model.Module> findAllInUserAndGroup(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .findAllInUserAndGroup(userId, groupId, orderByComparator);
	}

	public static com.liferay.lms.model.Module getPreviusModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPreviusModule(moduleId);
	}

	public static com.liferay.lms.model.Module getPreviusModule(
		com.liferay.lms.model.Module theModule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPreviusModule(theModule);
	}

	public static com.liferay.lms.model.Module getNextModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNextModule(moduleId);
	}

	public static com.liferay.lms.model.Module getNextModule(
		com.liferay.lms.model.Module theModule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNextModule(theModule);
	}

	public static void goUpModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().goUpModule(moduleId);
	}

	public static void goDownModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().goDownModule(moduleId);
	}

	public static void moveModule(long modId, long previusMod, long nextMod)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().moveModule(modId, previusMod, nextMod);
	}

	public static com.liferay.lms.model.Module addmodule(
		com.liferay.lms.model.Module validmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addmodule(validmodule);
	}

	public static com.liferay.lms.model.Module addModule(
		java.lang.Long companyId, java.lang.Long courseId,
		java.lang.Long userId, java.lang.String title,
		java.lang.String description, java.util.Date startDate,
		java.util.Date endDate, java.lang.Long ordern)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addModule(companyId, courseId, userId, title, description,
			startDate, endDate, ordern);
	}

	public static void remove(com.liferay.lms.model.Module fileobj)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().remove(fileobj);
	}

	public static boolean isUserPassed(long moduleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isUserPassed(moduleId, userId);
	}

	public static boolean isLocked(long moduleId, long userId)
		throws java.lang.Exception {
		return getService().isLocked(moduleId, userId);
	}

	public static long countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByGroupId(groupId);
	}

	public static long usersStarted(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().usersStarted(moduleId);
	}

	public static long modulesUserPassed(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().modulesUserPassed(groupId, userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static ModuleLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ModuleLocalService.class.getName());

			if (invokableLocalService instanceof ModuleLocalService) {
				_service = (ModuleLocalService)invokableLocalService;
			}
			else {
				_service = new ModuleLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(ModuleLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(ModuleLocalService service) {
	}

	private static ModuleLocalService _service;
}