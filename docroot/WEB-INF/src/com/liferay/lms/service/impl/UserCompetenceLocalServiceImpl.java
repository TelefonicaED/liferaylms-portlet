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

package com.liferay.lms.service.impl;

import java.util.List;

import javax.portlet.PortletURL;

import com.liferay.lms.NoSuchUserCompetenceException;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.base.UserCompetenceLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.PortletURLFactoryUtil;

/**
 * The implementation of the user competence local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.UserCompetenceLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.UserCompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.UserCompetenceLocalServiceUtil
 */
public class UserCompetenceLocalServiceImpl	extends UserCompetenceLocalServiceBaseImpl {
	
	public UserCompetence findByUserIdCompetenceId(long userId,long competenceId){
		try {
			return userCompetencePersistence.findByUserIdCompetenceId(userId, competenceId);
		} catch (NoSuchUserCompetenceException e) {
			return null;
		} catch (SystemException e) {
			return null;
		}
	}
	
	public UserCompetence findByUserIdCompetenceIdCourseId(long userId,long competenceId, long courseId){
		try {
			return userCompetencePersistence.fetchByUserIdCompetenceIdCourseId(userId, competenceId, courseId);
		}  catch (SystemException e) {
			return null;
		}
	}
	
	public UserCompetence findByUuid(String uuid) 
	{
		try {
			return userCompetencePersistence.fetchByUuid_First(uuid, null);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public String getCertificateURL(LiferayPortletResponse liferayPortletResponse,String uuid)
	{
		UserCompetence uc=findByUuid(uuid);
		if(uc!=null)
		{
			PortletURL resourcePortletURL=liferayPortletResponse.createResourceURL("usercompetence_WAR_liferaylmsportlet");
			resourcePortletURL.setParameter("uuid",uuid);
			return resourcePortletURL.toString();
		}
		else
		{
			return "";
		}
	}
	public List<UserCompetence> findBuUserId(long userId){
		try {
			return userCompetencePersistence.findByUserId(userId);
		} catch (SystemException e) {
			return null;
		}
	}

	public List<UserCompetence> findBuUserId(long userId,int start,int end){
		try {
			return userCompetencePersistence.findByUserId(userId,start,end);
		} catch (SystemException e) {
			return null;
		}
	}

	public int  countByUserId(long userId){
		try {
			return userCompetencePersistence.countByUserId(userId);
		} catch (SystemException e) {
			return 0;
		}
	}
}