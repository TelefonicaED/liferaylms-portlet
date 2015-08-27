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

package com.liferay.lms.service.http;

import com.liferay.lms.service.LearningActivityServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.lms.service.LearningActivityServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.lms.model.LearningActivitySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.lms.model.LearningActivity}, that is translated to a
 * {@link com.liferay.lms.model.LearningActivitySoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityServiceHttp
 * @see       com.liferay.lms.model.LearningActivitySoap
 * @see       com.liferay.lms.service.LearningActivityServiceUtil
 * @generated
 */
public class LearningActivityServiceSoap {
	public static com.liferay.lms.model.LearningActivitySoap[] getLearningActivitiesOfGroup(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.lms.model.LearningActivity> returnValue = LearningActivityServiceUtil.getLearningActivitiesOfGroup(groupId);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap[] getLearningActivitiesOfModule(
		long moduleId) throws RemoteException {
		try {
			java.util.List<com.liferay.lms.model.LearningActivity> returnValue = LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLearningactivity(
		com.liferay.lms.model.LearningActivitySoap lernact)
		throws RemoteException {
		try {
			LearningActivityServiceUtil.deleteLearningactivity(com.liferay.lms.model.impl.LearningActivityModelImpl.toModel(
					lernact));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLearningactivity(long actId)
		throws RemoteException {
		try {
			LearningActivityServiceUtil.deleteLearningactivity(actId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap getLearningActivity(
		long actId) throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivity returnValue = LearningActivityServiceUtil.getLearningActivity(actId);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap addLearningActivity(
		java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivity returnValue = LearningActivityServiceUtil.addLearningActivity(title,
					description, createDate, startDate, endDate, typeId, tries,
					passpuntuation, moduleId, serviceContext);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap modLearningActivity(
		com.liferay.lms.model.LearningActivitySoap lernact,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivity returnValue = LearningActivityServiceUtil.modLearningActivity(com.liferay.lms.model.impl.LearningActivityModelImpl.toModel(
						lernact), serviceContext);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap modLearningActivity(
		com.liferay.lms.model.LearningActivitySoap lernact)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivity returnValue = LearningActivityServiceUtil.modLearningActivity(com.liferay.lms.model.impl.LearningActivityModelImpl.toModel(
						lernact));

			return com.liferay.lms.model.LearningActivitySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivitySoap modLearningActivity(
		long actId, java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivity returnValue = LearningActivityServiceUtil.modLearningActivity(actId,
					title, description, createDate, startDate, endDate, typeId,
					tries, passpuntuation, moduleId, serviceContext);

			return com.liferay.lms.model.LearningActivitySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isLocked(long actId) throws RemoteException {
		try {
			boolean returnValue = LearningActivityServiceUtil.isLocked(actId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LearningActivityServiceSoap.class);
}