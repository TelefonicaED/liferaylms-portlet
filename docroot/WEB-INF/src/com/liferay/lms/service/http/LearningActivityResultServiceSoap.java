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

import com.liferay.lms.service.LearningActivityResultServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.lms.service.LearningActivityResultServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.lms.model.LearningActivityResultSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.lms.model.LearningActivityResult}, that is translated to a
 * {@link com.liferay.lms.model.LearningActivityResultSoap}. Methods that SOAP cannot
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
 * @see       LearningActivityResultServiceHttp
 * @see       com.liferay.lms.model.LearningActivityResultSoap
 * @see       com.liferay.lms.service.LearningActivityResultServiceUtil
 * @generated
 */
public class LearningActivityResultServiceSoap {
	public static com.liferay.lms.model.LearningActivityResultSoap getByActId(
		long actId) throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.getByActId(actId);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivityResultSoap getByActIdAndUser(
		long actId, java.lang.String login) throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.getByActIdAndUser(actId,
					login);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean userPassed(long actId) throws RemoteException {
		try {
			boolean returnValue = LearningActivityResultServiceUtil.userPassed(actId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean userLoginPassed(long actId, java.lang.String login)
		throws RemoteException {
		try {
			boolean returnValue = LearningActivityResultServiceUtil.userLoginPassed(actId,
					login);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivityResultSoap update(
		long latId, long result, java.lang.String tryResultData)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.update(latId,
					result, tryResultData);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivityResultSoap update(
		long latId, java.lang.String tryResultData) throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.update(latId,
					tryResultData);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivityResultSoap update(
		long latId, java.lang.String tryResultData, java.lang.String imsmanifest)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.update(latId,
					tryResultData, imsmanifest);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.LearningActivityResultSoap updateFinishTry(
		long latId, java.lang.String tryResultData, java.lang.String imsmanifest)
		throws RemoteException {
		try {
			com.liferay.lms.model.LearningActivityResult returnValue = LearningActivityResultServiceUtil.updateFinishTry(latId,
					tryResultData, imsmanifest);

			return com.liferay.lms.model.LearningActivityResultSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LearningActivityResultServiceSoap.class);
}