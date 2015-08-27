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


import java.util.Date;
import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.base.ModuleLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.LmsLocaleUtil;

/**
 * The implementation of the module local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.moduleLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.moduleLocalServiceUtil} to access the module local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.moduleLocalServiceBaseImpl
 * @see com.liferay.lms.service.moduleLocalServiceUtil
 */
public class ModuleLocalServiceImpl extends ModuleLocalServiceBaseImpl {
	Log log = LogFactoryUtil.getLog(ModuleLocalServiceImpl.class);

	public List<Module> findAllInUser(long userId)throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByUserId(userId);
		return list;
	}

	public List<Module> findAllInUser(long userId, OrderByComparator orderByComparator) throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByUserId(userId, QueryUtil.ALL_POS,QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	public List<Module> findAllInGroup(long groupId) throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByGroupId(groupId);
		return list;
	}

	public List<Module> findAllInGroup(long groupId, OrderByComparator orderByComparator) throws SystemException{
		List <Module> list = (List<Module>) modulePersistence.findByGroupId(groupId,QueryUtil.ALL_POS,QueryUtil.ALL_POS, orderByComparator);
		return list;
	}
	
	public Module findFirstInGroup(long groupId) throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByGroupId(groupId,0,1);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public Module findFirstInGroup(long groupId, OrderByComparator orderByComparator) throws SystemException{
		List <Module> list = (List<Module>) modulePersistence.findByGroupId(groupId,0,1,orderByComparator);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public int countInGroup(long groupId) throws SystemException{
		return modulePersistence.countByGroupId(groupId);
	}

	public List<Module> findAllInUserAndGroup(long userId, long groupId) throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByUserIdGroupId(userId, groupId);
		return list;
	}

	public List<Module> findAllInUserAndGroup(long userId, long groupId, OrderByComparator orderByComparator) throws SystemException {
		List<Module> list = (List<Module>) modulePersistence.findByUserIdGroupId(userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
		return list;
	}


	public Module getPreviusModule(long moduleId) throws SystemException
	{
		Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
		return getPreviusModule(theModule);
	}

	public Module getPreviusModule(Module theModule) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(Module.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("ordern").lt(theModule.getOrdern());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("groupId").eq(theModule.getGroupId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().desc("ordern");
		dq.addOrder(createOrder);

		@SuppressWarnings("unchecked")
		List<Module> modulesp=(List<Module>)moduleLocalService.dynamicQuery(dq,0,1);
		if(modulesp!=null&& modulesp.size()>0)
		{
			return modulesp.get(0);
		}
		else
		{
			return null;
		}
	}
	public Module getNextModule(long moduleId) throws SystemException
	{
		Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
		return getNextModule(theModule);
	}

	public Module getNextModule(Module theModule) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(Module.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("ordern").gt(theModule.getOrdern());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("groupId").eq(theModule.getGroupId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().asc("ordern");
		dq.addOrder(createOrder);

		@SuppressWarnings("unchecked")
		List<Module> modulesp=(List<Module>)moduleLocalService.dynamicQuery(dq,0,1);
		if(modulesp!=null&& modulesp.size()>0)
		{
			return modulesp.get(0);
		}
		else
		{
			return null;
		}
	}

	public void goUpModule(long moduleId ) throws SystemException
	{
		Module previusModule=getPreviusModule(moduleId);
		if(previusModule!=null)
		{
			
			Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
			long priority=theModule.getOrdern();
			theModule.setOrdern(previusModule.getOrdern());
			previusModule.setOrdern(priority);
			theModule.setModifiedDate(new Date(System.currentTimeMillis()));
			previusModule.setModifiedDate(new Date(System.currentTimeMillis()));
			modulePersistence.update(theModule, true);
			modulePersistence.update(previusModule, true);

			//auditing
			System.out.println("Módulo con id: "+theModule.getModuleId()+" ha sido movido hacia arriba por el usuario: "+theModule.getUserId());

			AuditingLogFactory.audit(theModule.getCompanyId(), theModule.getGroupId(), Module.class.getName(), 
					moduleId, theModule.getUserId(), AuditConstants.UPDATE, "MODULE_UP");
		}
		
	}
	public void goDownModule(long moduleId ) throws SystemException
	{
		Module nextModule=getNextModule(moduleId);
		if(nextModule!=null)
		{
			
			Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
			long priority=theModule.getOrdern();
			theModule.setOrdern(nextModule.getOrdern());
			nextModule.setOrdern(priority);
			theModule.setModifiedDate(new Date(System.currentTimeMillis()));
			nextModule.setModifiedDate(new Date(System.currentTimeMillis()));
			modulePersistence.update(theModule, true);			
			modulePersistence.update(nextModule, true);

			//auditing
			System.out.println("Módulo con id: "+theModule.getModuleId()+" ha sido movido hacia abajo por el usuario: "+theModule.getUserId());

			AuditingLogFactory.audit(theModule.getCompanyId(), theModule.getGroupId(), Module.class.getName(), 
					moduleId, theModule.getUserId(), AuditConstants.UPDATE, "MODULE_DOWN");
		}
		
	}
	
	public void moveModule(long modId, long previusMod, long nextMod) throws SystemException {
		Module actualMod = (modId>0)?modulePersistence.fetchByPrimaryKey(modId):null;
		Module finalPrevMod = (previusMod>0)?modulePersistence.fetchByPrimaryKey(previusMod):null;
		Module finalNextMod = (nextMod>0)?modulePersistence.fetchByPrimaryKey(nextMod):null;
		//Elemento subido
		if(finalNextMod!=null && actualMod.getOrdern() > finalNextMod.getOrdern()){
			Module prevAct = getPreviusModule(actualMod);
			while(prevAct != null && actualMod.getOrdern() > finalNextMod.getOrdern()){
				goUpModule(modId);
				actualMod = modulePersistence.fetchByPrimaryKey(modId);
				prevAct = getPreviusModule(actualMod);
			}
			//auditing
			System.out.println("Módulo con id: "+actualMod.getModuleId()+" ha sido movido hacia arriba por el usuario: "+actualMod.getUserId());
			AuditingLogFactory.audit(actualMod.getCompanyId(), actualMod.getGroupId(), Module.class.getName(), 
					modId, actualMod.getUserId(), AuditConstants.UPDATE, "MODULE_UP");
		//Elemento bajado
		}else if(finalPrevMod!=null && actualMod.getOrdern() < finalPrevMod.getOrdern()){
			Module nexMod = getNextModule(actualMod);
			while (nexMod != null && actualMod.getOrdern() < finalPrevMod.getOrdern()){
				goDownModule(modId);
				actualMod = modulePersistence.fetchByPrimaryKey(modId);
				nexMod = getNextModule(actualMod);
			}
			System.out.println("Módulo con id: "+actualMod.getModuleId()+" ha sido movido hacia abajo por el usuario: "+actualMod.getUserId());

			//auditing
			AuditingLogFactory.audit(actualMod.getCompanyId(), actualMod.getGroupId(), Module.class.getName(), 
					modId, actualMod.getUserId(), AuditConstants.UPDATE, "MODULE_DOWN");
		}

	}
	
	public Module addmodule (Module validmodule) throws SystemException {
	    Module fileobj = modulePersistence.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    fileobj.setCompanyId(validmodule.getCompanyId());
	    fileobj.setGroupId(validmodule.getGroupId());
	    fileobj.setUserId(validmodule.getUserId());
	    try {
	    	fileobj.setUserName(userLocalService.getUser(validmodule.getUserId()).getFullName());
	    } catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}
	    fileobj.setCreateDate(new java.util.Date(System.currentTimeMillis()));
	    fileobj.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
	    fileobj.setStartDate(validmodule.getStartDate());
	    fileobj.setEndDate(validmodule.getEndDate());
	    fileobj.setTitle(validmodule.getTitle());
	    fileobj.setDescription(validmodule.getDescription());
	    fileobj.setOrdern(fileobj.getModuleId());
	    fileobj.setIcon(validmodule.getIcon());
	    try {
			resourceLocalService.addResources(
					validmodule.getCompanyId(), validmodule.getGroupId(), validmodule.getUserId(),
			Module.class.getName(), validmodule.getPrimaryKey(), false,
			true, true);
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}

	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "title");
	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "description");

	    Module module = modulePersistence.update(fileobj, false);

		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				validmodule.getModuleId(), module.getUserId(), AuditConstants.ADD, null);
	    
	    return module;
	}
	
	public Module addModule(Long companyId, Long courseId, Long userId, 
			String title, String description,
			Date startDate, Date endDate, Long ordern) throws SystemException {
		Module fileobj = modulePersistence.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    fileobj.setCompanyId(companyId);
	    fileobj.setGroupId(courseId);
	    fileobj.setUserId(userId);
	    try {
	    	fileobj.setUserName(userLocalService.getUser(userId).getFullName());
	    } catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}
	    fileobj.setCreateDate(new java.util.Date(System.currentTimeMillis()));
	    fileobj.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
	    fileobj.setStartDate(startDate);
	    fileobj.setEndDate(endDate);
	    fileobj.setTitle(title);
	    fileobj.setDescription(description);
	    fileobj.setOrdern(ordern != null ? ordern : fileobj.getModuleId());
	    
	    try {
			resourceLocalService.addResources(
					companyId, courseId, userId,
					Module.class.getName(), fileobj.getPrimaryKey(), 
					false, true, true);
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}

	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "title");
	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "description");
		
	    Module module = modulePersistence.update(fileobj, false);
	    
		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				fileobj.getModuleId(), module.getUserId(), AuditConstants.ADD, null);
		
		return module;
	}

	public void remove(Module fileobj) throws SystemException {

//		modulePersistence.remove(fileobj);
		try {
			resourceLocalService.deleteResource(
					fileobj.getCompanyId(), Module.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, fileobj.getPrimaryKey());
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}
		modulePersistence.remove(fileobj);

		//auditing
		AuditingLogFactory.audit(fileobj.getCompanyId(), fileobj.getGroupId(), Module.class.getName(), 
				fileobj.getModuleId(), fileobj.getUserId(), AuditConstants.DELETE, null);
	}

	@Override
	public Module updateModule(Module module) throws SystemException {
		
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "title");
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "description");
		module.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		try {
			if(resourceLocalService.getResource(module.getCompanyId(), Module.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,Long.toString( module.getPrimaryKey()))==null)
					{
				resourceLocalService.addResources(
						module.getCompanyId(), module.getGroupId(), module.getUserId(),
				Module.class.getName(), module.getPrimaryKey(), false,
				true, true);
					}
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}
		module = super.updateModule(module);
		
		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				module.getModuleId(), module.getUserId(), AuditConstants.UPDATE, null);
		
		return module;
	}

	@Override
	public Module updateModule(Module module, boolean merge) throws SystemException {
		
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "title");
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "description");
		module.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		module = super.updateModule(module, merge);

		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				module.getModuleId(), module.getUserId(), AuditConstants.UPDATE, null);
		
		return module;
	}
	public boolean isUserPassed(long moduleId,long userId) throws SystemException
	{
		ModuleResult moduleResult=moduleResultLocalService.getByModuleAndUser(moduleId, userId);
		if(moduleResult==null ||!moduleResult.getPassed())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean isLocked(long moduleId,long userId) throws Exception
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
		java.util.Date now=new java.util.Date(System.currentTimeMillis());
		Course course=courseLocalService.fetchByGroupCreatedId(theModule.getGroupId());
		if(!UserLocalServiceUtil.hasGroupUser(theModule.getGroupId(), userId) && !PortalUtil.isOmniadmin(userId))
		{
			return true;
		}
		User user=UserLocalServiceUtil.getUser(userId);
		
		PermissionChecker permissionChecker=PermissionCheckerFactoryUtil.create(user);
		if(!permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.ACCESS))
		{
			return true;
		}
       
		if(!((theModule.getEndDate()!=null&&theModule.getEndDate().after(now)) &&(theModule.getStartDate()!=null&&theModule.getStartDate().before(now))))
		{
			return true;
		}
		if(theModule.getPrecedence()>0)
		{
			return !isUserPassed(theModule.getPrecedence(), userId);
		}
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
        if(courseResult!=null)
        {
	        if(((courseResult.getAllowFinishDate()!=null&&courseResult.getAllowFinishDate().before(now)) ||(courseResult.getAllowStartDate()!=null&&courseResult.getAllowStartDate().after(now))))
			{
				return true;
			}
        }
		return false;
	}
	public long countByGroupId(long groupId) throws SystemException
	{
		return modulePersistence.countByGroupId(groupId);
	}
	public long usersStarted(long moduleId) throws SystemException
	{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, classLoader);
		java.util.List<Long> actIds=LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(moduleId);
		long result=0;
		Criterion crit;
		crit = PropertyFactoryUtil.forName("actId").in(actIds.toArray());
		dq.add(crit);
		dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("userId")));
		result=LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq);
		return result;
	}
	public long modulesUserPassed(long groupId, long userId) throws SystemException
	{
		java.util.List<Module> themodules=moduleLocalService.findAllInGroup(groupId);
		long count=0;
		for(Module theModule:themodules)
		{
			if(moduleLocalService.isUserPassed(theModule.getModuleId(), userId))
			{
				count++;
			}
		}
		return count;
	}
}