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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ModuleLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       ModuleLocalService
 * @generated
 */
public class ModuleLocalServiceWrapper implements ModuleLocalService,
	ServiceWrapper<ModuleLocalService> {
	public ModuleLocalServiceWrapper(ModuleLocalService moduleLocalService) {
		_moduleLocalService = moduleLocalService;
	}

	/**
	* Adds the module to the database. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module addModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.addModule(module);
	}

	/**
	* Creates a new module with the primary key. Does not add the module to the database.
	*
	* @param moduleId the primary key for the new module
	* @return the new module
	*/
	public com.liferay.lms.model.Module createModule(long moduleId) {
		return _moduleLocalService.createModule(moduleId);
	}

	/**
	* Deletes the module with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param moduleId the primary key of the module
	* @return the module that was removed
	* @throws PortalException if a module with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module deleteModule(long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.deleteModule(moduleId);
	}

	/**
	* Deletes the module from the database. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module deleteModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.deleteModule(module);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _moduleLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.Module fetchModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.fetchModule(moduleId);
	}

	/**
	* Returns the module with the primary key.
	*
	* @param moduleId the primary key of the module
	* @return the module
	* @throws PortalException if a module with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module getModule(long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getModule(moduleId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getPersistedModel(primaryKeyObj);
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
	public com.liferay.lms.model.Module getModuleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getModuleByUuidAndGroupId(uuid, groupId);
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
	public java.util.List<com.liferay.lms.model.Module> getModules(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getModules(start, end);
	}

	/**
	* Returns the number of modules.
	*
	* @return the number of modules
	* @throws SystemException if a system exception occurred
	*/
	public int getModulesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getModulesCount();
	}

	/**
	* Updates the module in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @return the module that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module updateModule(
		com.liferay.lms.model.Module module)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.updateModule(module);
	}

	/**
	* Updates the module in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param module the module
	* @param merge whether to merge the module with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the module that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Module updateModule(
		com.liferay.lms.model.Module module, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.updateModule(module, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _moduleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_moduleLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _moduleLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInUser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInUser(userId);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInUser(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInUser(userId, orderByComparator);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInGroup(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInGroup(groupId, orderByComparator);
	}

	public com.liferay.lms.model.Module findFirstInGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findFirstInGroup(groupId);
	}

	public com.liferay.lms.model.Module findFirstInGroup(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findFirstInGroup(groupId, orderByComparator);
	}

	public int countInGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.countInGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInUserAndGroup(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInUserAndGroup(userId, groupId);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInUserAndGroup(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.findAllInUserAndGroup(userId, groupId,
			orderByComparator);
	}

	public com.liferay.lms.model.Module getPreviusModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getPreviusModule(moduleId);
	}

	public com.liferay.lms.model.Module getPreviusModule(
		com.liferay.lms.model.Module theModule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getPreviusModule(theModule);
	}

	public com.liferay.lms.model.Module getNextModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getNextModule(moduleId);
	}

	public com.liferay.lms.model.Module getNextModule(
		com.liferay.lms.model.Module theModule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.getNextModule(theModule);
	}

	public void goUpModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_moduleLocalService.goUpModule(moduleId);
	}

	public void goDownModule(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_moduleLocalService.goDownModule(moduleId);
	}

	public void moveModule(long modId, long previusMod, long nextMod)
		throws com.liferay.portal.kernel.exception.SystemException {
		_moduleLocalService.moveModule(modId, previusMod, nextMod);
	}

	public com.liferay.lms.model.Module addmodule(
		com.liferay.lms.model.Module validmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.addmodule(validmodule);
	}

	public com.liferay.lms.model.Module addModule(java.lang.Long companyId,
		java.lang.Long courseId, java.lang.Long userId, java.lang.String title,
		java.lang.String description, java.util.Date startDate,
		java.util.Date endDate, java.lang.Long ordern)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.addModule(companyId, courseId, userId,
			title, description, startDate, endDate, ordern);
	}

	public void remove(com.liferay.lms.model.Module fileobj)
		throws com.liferay.portal.kernel.exception.SystemException {
		_moduleLocalService.remove(fileobj);
	}

	public boolean isUserPassed(long moduleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.isUserPassed(moduleId, userId);
	}

	public boolean isLocked(long moduleId, long userId)
		throws java.lang.Exception {
		return _moduleLocalService.isLocked(moduleId, userId);
	}

	public long countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.countByGroupId(groupId);
	}

	public long usersStarted(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.usersStarted(moduleId);
	}

	public long modulesUserPassed(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleLocalService.modulesUserPassed(groupId, userId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ModuleLocalService getWrappedModuleLocalService() {
		return _moduleLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedModuleLocalService(
		ModuleLocalService moduleLocalService) {
		_moduleLocalService = moduleLocalService;
	}

	public ModuleLocalService getWrappedService() {
		return _moduleLocalService;
	}

	public void setWrappedService(ModuleLocalService moduleLocalService) {
		_moduleLocalService = moduleLocalService;
	}

	private ModuleLocalService _moduleLocalService;
}