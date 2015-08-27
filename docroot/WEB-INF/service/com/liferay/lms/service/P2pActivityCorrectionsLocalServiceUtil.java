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
 * The utility for the p2p activity corrections local service. This utility wraps {@link com.liferay.lms.service.impl.P2pActivityCorrectionsLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see P2pActivityCorrectionsLocalService
 * @see com.liferay.lms.service.base.P2pActivityCorrectionsLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.P2pActivityCorrectionsLocalServiceImpl
 * @generated
 */
public class P2pActivityCorrectionsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.P2pActivityCorrectionsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the p2p activity corrections to the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	* @return the p2p activity corrections that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections addP2pActivityCorrections(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addP2pActivityCorrections(p2pActivityCorrections);
	}

	/**
	* Creates a new p2p activity corrections with the primary key. Does not add the p2p activity corrections to the database.
	*
	* @param p2pActivityCorrectionsId the primary key for the new p2p activity corrections
	* @return the new p2p activity corrections
	*/
	public static com.liferay.lms.model.P2pActivityCorrections createP2pActivityCorrections(
		long p2pActivityCorrectionsId) {
		return getService()
				   .createP2pActivityCorrections(p2pActivityCorrectionsId);
	}

	/**
	* Deletes the p2p activity corrections with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections that was removed
	* @throws PortalException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections deleteP2pActivityCorrections(
		long p2pActivityCorrectionsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteP2pActivityCorrections(p2pActivityCorrectionsId);
	}

	/**
	* Deletes the p2p activity corrections from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	* @return the p2p activity corrections that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections deleteP2pActivityCorrections(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteP2pActivityCorrections(p2pActivityCorrections);
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

	public static com.liferay.lms.model.P2pActivityCorrections fetchP2pActivityCorrections(
		long p2pActivityCorrectionsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchP2pActivityCorrections(p2pActivityCorrectionsId);
	}

	/**
	* Returns the p2p activity corrections with the primary key.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections
	* @throws PortalException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections getP2pActivityCorrections(
		long p2pActivityCorrectionsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getP2pActivityCorrections(p2pActivityCorrectionsId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the p2p activity correctionses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> getP2pActivityCorrectionses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getP2pActivityCorrectionses(start, end);
	}

	/**
	* Returns the number of p2p activity correctionses.
	*
	* @return the number of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int getP2pActivityCorrectionsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getP2pActivityCorrectionsesCount();
	}

	/**
	* Updates the p2p activity corrections in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	* @return the p2p activity corrections that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections updateP2pActivityCorrections(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateP2pActivityCorrections(p2pActivityCorrections);
	}

	/**
	* Updates the p2p activity corrections in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	* @param merge whether to merge the p2p activity corrections with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the p2p activity corrections that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections updateP2pActivityCorrections(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateP2pActivityCorrections(p2pActivityCorrections, merge);
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

	public static com.liferay.lms.model.P2pActivityCorrections findByP2pActivityIdAndUserId(
		java.lang.Long p2pActivityId, java.lang.Long userId) {
		return getService().findByP2pActivityIdAndUserId(p2pActivityId, userId);
	}

	public static boolean exitsCorP2p(java.lang.Long p2pActivityId,
		java.lang.Long userId) {
		return getService().exitsCorP2p(p2pActivityId, userId);
	}

	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		java.lang.Long p2pActivityId) {
		return getService().findByP2pActivityId(p2pActivityId);
	}

	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdIdAndUserId(
		java.lang.Long actId, java.lang.Long userId) {
		return getService().findByActIdIdAndUserId(actId, userId);
	}

	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserIdOrderByDate(
		java.lang.Long actId, java.lang.Long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findByActIdAndUserIdOrderByDate(actId, userId);
	}

	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserIdOrderById(
		java.lang.Long actId, java.lang.Long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findByActIdAndUserIdOrderById(actId, userId);
	}

	public static com.liferay.lms.model.P2pActivityCorrections addorUpdateP2pActivityCorrections(
		com.liferay.lms.model.P2pActivityCorrections p2pActCor) {
		return getService().addorUpdateP2pActivityCorrections(p2pActCor);
	}

	public static void asignP2PCorrectionsToUsers(long actId, long p2pActId,
		java.util.List<com.liferay.portal.model.User> usersList)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().asignP2PCorrectionsToUsers(actId, p2pActId, usersList);
	}

	public static void asignCorrectionsToP2PActivities(long actId,
		long p2pActivityId, int numValidaciones,
		java.util.List<com.liferay.lms.model.P2pActivity> activityList,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.asignCorrectionsToP2PActivities(actId, p2pActivityId,
			numValidaciones, activityList, userId);
	}

	public static int getNumCorrectionsAsignToP2P(long p2pActId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNumCorrectionsAsignToP2P(p2pActId);
	}

	public static int getNumCorrectionsAsignToUser(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNumCorrectionsAsignToUser(actId, userId);
	}

	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> getCorrectionsDoneByUserInP2PActivity(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCorrectionsDoneByUserInP2PActivity(actId, userId);
	}

	/**
	* Para saber si el usuario ya ha realizado todas las correcciones que se indica en el extracontent.
	*/
	public static boolean areAllCorrectionsDoneByUserInP2PActivity(long actId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .areAllCorrectionsDoneByUserInP2PActivity(actId, userId);
	}

	/**
	* Obtenemos la lista de correcciones que se le asignaron a una tarea p2p.
	*
	* @param p2pActivityId
	* @return
	* @throws SystemException
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> getCorrectionByP2PActivity(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCorrectionByP2PActivity(p2pActivityId);
	}

	/**
	* A partir del id de una actividad, obtenemos la media de resultados que ha obtenido en ellas.
	* En las correcciones que se han realizado y tiene fecha de realizaciÃ¯Â¿Â½n.
	*
	* @param p2pActivityId
	* @return la media de results
	*/
	public static long getAVGCorrectionsResults(long p2pActivityId) {
		return getService().getAVGCorrectionsResults(p2pActivityId);
	}

	public static boolean isP2PAsignationDone(long p2pActivityId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isP2PAsignationDone(p2pActivityId, userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static P2pActivityCorrectionsLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					P2pActivityCorrectionsLocalService.class.getName());

			if (invokableLocalService instanceof P2pActivityCorrectionsLocalService) {
				_service = (P2pActivityCorrectionsLocalService)invokableLocalService;
			}
			else {
				_service = new P2pActivityCorrectionsLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(P2pActivityCorrectionsLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(P2pActivityCorrectionsLocalService service) {
	}

	private static P2pActivityCorrectionsLocalService _service;
}