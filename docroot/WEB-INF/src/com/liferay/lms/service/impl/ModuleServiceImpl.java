 /**
 * 2013 TELEFONICA LEARNING SERVICES. All rights reserved.
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
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.lms.asset.ModuleAssetRenderer;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.base.ModuleServiceBaseImpl;
import com.liferay.lms.service.persistence.ModuleUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;

/**
 * The implementation of the module remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ModuleService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.ModuleServiceBaseImpl
 * @see com.liferay.lms.service.ModuleServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class ModuleServiceImpl extends ModuleServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ModuleServiceUtil} to access the module remote service.
	 */
	@JSONWebService
	public List<Module> findAllInGroup(long groupId) throws SystemException, PortalException {
		List<Module> modules; 
		Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
		if (getPermissionChecker() == null
				|| course == null
				|| !(getPermissionChecker().hasPermission(course.getGroupId(), Course.class.getName(),
						course.getCourseId(), "VIEW") || getPermissionChecker()
						.hasOwnerPermission(course.getCompanyId(), Course.class.getName(), course.getCourseId(),
								course.getUserId(), "VIEW"))) {
			modules = new ArrayList<Module>();
		}else{
			modules= (List<Module>) modulePersistence.filterFindByGroupId(groupId);
		}		
		return modules;
	}
	@JSONWebService
	public List<Module> findAllInCourse(long courseId) throws SystemException, PortalException {
		User usuario= this.getUser();
		Course course=courseLocalService.getCourse(courseId);
		List<Module> list = (List<Module>) modulePersistence.filterFindByGroupId(course.getGroupCreatedId());
		return list;
	}
	
	@JSONWebService
	public boolean isLocked(long moduleId) throws Exception
	{
		User usuario= this.getUser();
		return moduleLocalService.isLocked(moduleId, usuario.getUserId());
	}
	@JSONWebService
	public boolean PassedByMe(long moduleId) throws SystemException, PortalException
	{
		User usuario= this.getUser();
		return moduleLocalService.isUserPassed(moduleId, usuario.getUserId());
	
	}
	@JSONWebService
	public boolean isUserPassed(long moduleId,String login) throws SystemException, PortalException
	{
		User usuario= this.getUser();
		Module module=moduleLocalService.getModule(moduleId);
		Course course=courseLocalService.getCourseByGroupCreatedId(module.getGroupId());
		if(getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ASSIGN_MEMBERS"))
		{
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);	
			return moduleLocalService.isUserPassed(moduleId, user.getUserId());
			
		}
		return false;
	
	}
	
	public PortletURL getURLModule(Module module, long plid, LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse) throws PortalException, SystemException, Exception{
		
		ModuleAssetRenderer moduleAssetRenderer = new ModuleAssetRenderer(module);

		return moduleAssetRenderer.getURLViewInContext(plid, liferayPortletRequest, liferayPortletResponse);
	}
}