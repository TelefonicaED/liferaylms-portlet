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

package com.liferay.lms.model.impl;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The extended model implementation for the AsynchronousProcessAudit service. Represents a row in the &quot;Lms_AsynchronousProcessAudit&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.AsynchronousProcessAudit} interface.
 * </p>
 *
 * @author TLS
 */
public class AsynchronousProcessAuditImpl
	extends AsynchronousProcessAuditBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a asynchronous process audit model instance should use the {@link com.liferay.lms.model.AsynchronousProcessAudit} interface instead.
	 */
	public AsynchronousProcessAuditImpl() {
	}
	
    /**
     * Devuelve el screenName del usuario que realizo la accion
     * 
     * @return screenname del usuario o string vacia
     */
    public String getUserScreenName()
    {
        String screenName = StringPool.BLANK;

        try {
            User user = UserLocalServiceUtil.getUser(this.getUserId());
            screenName = user.getScreenName();
        } catch (PortalException e) {
            _log.debug("User not found. UserId = " + this.getUserId());
        } catch (SystemException e) {
            _log.error(e, e);
        }

        return screenName;
    }
	
	private static Log _log = LogFactoryUtil.getLog(AsynchronousProcessAuditImpl.class);
}