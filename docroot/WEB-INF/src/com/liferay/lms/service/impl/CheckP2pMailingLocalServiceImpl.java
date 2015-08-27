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


import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.CheckP2pMailing;
import com.liferay.lms.service.base.CheckP2pMailingLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the check p2p mailing local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CheckP2pMailingLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CheckP2pMailingLocalServiceUtil} to access the check p2p mailing local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CheckP2pMailingLocalServiceBaseImpl
 * @see com.liferay.lms.service.CheckP2pMailingLocalServiceUtil
 */
public class CheckP2pMailingLocalServiceImpl extends CheckP2pMailingLocalServiceBaseImpl {
	
	@Override
	public CheckP2pMailing addCheckP2pMailing(CheckP2pMailing newCheckP2pMail)
			throws SystemException {
		try{
			
			long CheckP2pMailingId = CounterLocalServiceUtil.increment(CheckP2pMailing.class.getName());
			
			CheckP2pMailing fileobj = checkP2pMailingPersistence.create(CheckP2pMailingId);
			
			fileobj.setActId(newCheckP2pMail.getActId());
			fileobj.setDate(new java.util.Date(System.currentTimeMillis()));

			return checkP2pMailingPersistence.update(fileobj, false);
			
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting CheckP2pMailingLocalService.addCheckP2pMailing");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	public CheckP2pMailing findByActId(long actId)
			throws SystemException {
		try{
			
			return checkP2pMailingPersistence.fetchByactId(actId);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting CheckP2pMailingLocalService.findByActId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	private static Log _log = LogFactoryUtil.getLog(CheckP2pMailing.class);
}