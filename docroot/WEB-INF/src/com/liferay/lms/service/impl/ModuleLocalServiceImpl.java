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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.asset.LearningActivityBaseAssetRenderer;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.ModuleLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.ModuleUtil;
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
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
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
	
	public List<Module> findAllInGroup(long groupId, int start, int end) throws SystemException {
		return ModuleUtil.filterFindByGroupId(groupId, start, end);
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

	@Deprecated
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

	@Indexable(type=IndexableType.REINDEX)
	public void goUpModule(long moduleId, long userIdAction ) throws SystemException
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
			log.debug("Módulo con id: "+theModule.getModuleId()+" ha sido movido hacia arriba por el usuario: "+theModule.getUserId());

			AuditingLogFactory.audit(theModule.getCompanyId(), theModule.getGroupId(), Module.class.getName(), 
					moduleId,userIdAction, AuditConstants.UPDATE, "MODULE_UP");
		}
		
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public void goDownModule(long moduleId , long userIdAction) throws SystemException
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
			log.debug("Módulo con id: "+theModule.getModuleId()+" ha sido movido hacia abajo por el usuario: "+theModule.getUserId());

			AuditingLogFactory.audit(theModule.getCompanyId(), theModule.getGroupId(), Module.class.getName(), 
					moduleId, userIdAction, AuditConstants.UPDATE, "MODULE_DOWN");
		}
		
	}
	
	public void moveModule(long modId, long previusMod, long nextMod, long userIdAction) throws SystemException {
		Module actualMod = (modId>0)?modulePersistence.fetchByPrimaryKey(modId):null;
		Module finalPrevMod = (previusMod>0)?modulePersistence.fetchByPrimaryKey(previusMod):null;
		Module finalNextMod = (nextMod>0)?modulePersistence.fetchByPrimaryKey(nextMod):null;
		//Elemento subido
		if(finalNextMod!=null && actualMod.getOrdern() > finalNextMod.getOrdern()){
			Module prevAct = getPreviusModule(actualMod);
			while(prevAct != null && actualMod.getOrdern() > finalNextMod.getOrdern()){
				goUpModule(modId,userIdAction);
				actualMod = modulePersistence.fetchByPrimaryKey(modId);
				prevAct = getPreviusModule(actualMod);
			}
			//auditing
			log.debug("Módulo con id: "+actualMod.getModuleId()+" ha sido movido hacia arriba por el usuario: "+actualMod.getUserId());
			AuditingLogFactory.audit(actualMod.getCompanyId(), actualMod.getGroupId(), Module.class.getName(), 
					modId, userIdAction, AuditConstants.UPDATE, "MODULE_UP");
		//Elemento bajado
		}else if(finalPrevMod!=null && actualMod.getOrdern() < finalPrevMod.getOrdern()){
			Module nexMod = getNextModule(actualMod);
			while (nexMod != null && actualMod.getOrdern() < finalPrevMod.getOrdern()){
				goDownModule(modId,userIdAction);
				actualMod = modulePersistence.fetchByPrimaryKey(modId);
				nexMod = getNextModule(actualMod);
			}
			log.debug("Módulo con id: "+actualMod.getModuleId()+" ha sido movido hacia abajo por el usuario: "+actualMod.getUserId());

			//auditing
			AuditingLogFactory.audit(actualMod.getCompanyId(), actualMod.getGroupId(), Module.class.getName(), 
					modId, userIdAction, AuditConstants.UPDATE, "MODULE_DOWN");
		}

	}
	@Indexable(type=IndexableType.REINDEX)
	public Module addmodule(Module module) throws SystemException, PortalException{
		//Al duplicar el curso o crear ediciones no se copian ni los tags ni las categorías
		return addmodule(module, null);
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Module addmodule (Module validmodule, ServiceContext serviceContext) throws SystemException, PortalException {
		log.debug(":::::::::::::::addmodule::::::::::::::::::::");
	    Module fileobj = modulePersistence.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    if(Validator.isNotNull(validmodule.getUuid())){
	    	fileobj.setUuid(validmodule.getUuid());
	    }
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
	    fileobj.setPrecedence(validmodule.getPrecedence());
	    try {
	    	Role siteMember = RoleLocalServiceUtil.fetchRole(validmodule.getCompanyId(), RoleConstants.SITE_MEMBER);
	    	ResourcePermissionLocalServiceUtil.setResourcePermissions(validmodule.getCompanyId(), 
	    			Module.class.getName(),ResourceConstants.SCOPE_GROUP, String.valueOf(fileobj.getModuleId()),  siteMember.getRoleId(),  new String[]{"VIEW","ACCESS"});
	    	
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
	    
	    if(serviceContext != null)
	    	fileobj.setExpandoBridgeAttributes(serviceContext);

	    Module module = modulePersistence.update(fileobj, false);
	    
	    //AssetEntry
	    long[] categoryIds = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetCategoryIds() : null;
	    String[] tagNames = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetTagNames() : null;
	    AssetEntry assetEntry = assetEntryLocalService.updateEntry(module.getUserId(), module.getGroupId(), 
	    		Module.class.getName(), module.getModuleId(), module.getUuid(), 0, categoryIds,
	    		tagNames, true, module.getStartDate(), module.getEndDate(),
	    		new Date(System.currentTimeMillis()), null,	ContentTypes.TEXT_HTML, module.getTitle(),
	    		module.getDescription(), module.getDescription(), null, null, 0, 0, null, false);
	    
	    //Index
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		indexer.reindex(module);

		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				validmodule.getModuleId(), module.getUserId(), AuditConstants.ADD, null);
	    
	    return module;
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule (Module fileobj, Module validmodule) throws SystemException, PortalException {
		log.debug(":::::::::::::::addmodule::::::::::::::::::::");

	    fileobj.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
	    fileobj.setStartDate(validmodule.getStartDate());
	    fileobj.setEndDate(validmodule.getEndDate());
	    fileobj.setTitle(validmodule.getTitle());
	    fileobj.setDescription(validmodule.getDescription());
	    fileobj.setOrdern(fileobj.getModuleId());
	    fileobj.setIcon(validmodule.getIcon());
	    fileobj.setPrecedence(validmodule.getPrecedence());

	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "title");
	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "description");

	    Module module = modulePersistence.update(fileobj, false);

	    //Index
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		indexer.reindex(module);

		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				validmodule.getModuleId(), module.getUserId(), AuditConstants.UPDATE, null);
	    
	    return module;
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Module addModule(Long companyId, Long groupId, Long userId, 
			String title, String description,
			Date startDate, Date endDate, Long ordern) throws SystemException, PortalException{
		return addModule(companyId, groupId, userId, title, description, startDate, endDate, ordern, null);
	}
	@Indexable(type=IndexableType.REINDEX)
	public Module addModule(Long companyId, Long groupId, Long userId, 
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Date startDate, Date endDate, Long ordern) throws SystemException, PortalException{
		return addModule(companyId, groupId, userId, titleMap, descriptionMap, startDate, endDate, ordern, null);
	}
	@Indexable(type=IndexableType.REINDEX)
	public Module addModule(Long companyId, Long groupId, Long userId, 
			String title, String description,
			Date startDate, Date endDate, Long ordern, ServiceContext serviceContext) throws SystemException, PortalException {
	
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		titleMap.put(serviceContext.getLocale(), title);
		
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		descriptionMap.put(serviceContext.getLocale(), description);
		
		return addModule(companyId, groupId, userId, titleMap, descriptionMap, startDate, endDate, ordern, serviceContext);
	}
	@Indexable(type=IndexableType.REINDEX)
	public Module addModule(Long companyId, Long groupId, Long userId, 
			Map<Locale,String> titleMap, Map<Locale,String> descriptionMap,
			Date startDate, Date endDate, Long ordern, ServiceContext serviceContext) throws SystemException, PortalException {
		Module fileobj = modulePersistence.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    fileobj.setCompanyId(companyId);
	    fileobj.setGroupId(groupId);
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
	    fileobj.setTitleMap(titleMap);
	    fileobj.setDescriptionMap(descriptionMap);
	    fileobj.setOrdern(ordern != null ? ordern : fileobj.getModuleId());
	    
	    try {
	    	
	    	 Role siteMember = RoleLocalServiceUtil.fetchRole(companyId, RoleConstants.SITE_MEMBER);
		     ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, 
		     			Module.class.getName(),ResourceConstants.SCOPE_GROUP, String.valueOf(fileobj.getModuleId()),  siteMember.getRoleId(),  new String[]{"VIEW","ACCESS"});
		    
			resourceLocalService.addResources(
					companyId, groupId, userId,
					Module.class.getName(), fileobj.getPrimaryKey(), 
					false, true, true);
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}

	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "title");
	    fileobj = LmsLocaleUtil.checkDefaultLocale(Module.class, fileobj, "description");
	    
	    if(serviceContext != null)
	    	fileobj.setExpandoBridgeAttributes(serviceContext);
		
	    Module module = modulePersistence.update(fileobj, false);
	    
	    //AssetEntry
	    long[] categoryIds = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetCategoryIds() : null;
	    String[] tagNames = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetTagNames() : null;
	    AssetEntry assetEntry = assetEntryLocalService.updateEntry(module.getUserId(), module.getGroupId(), 
	    		Module.class.getName(), module.getModuleId(), module.getUuid(), 0, categoryIds,
	    		tagNames, true, module.getStartDate(), module.getEndDate(),
	    		new Date(System.currentTimeMillis()), null, ContentTypes.TEXT_HTML, module.getTitle(), module.getDescription(),
	    		module.getDescription(), null, null, 0, 0, null, false);
	    
	    //Index
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		indexer.reindex(module);
	    
		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				fileobj.getModuleId(), module.getUserId(), AuditConstants.ADD, null);
		
		return module;
	}
	
	public void remove(Module fileobj, long userIdAction) throws SystemException, PortalException {
		log.debug("::: remove module :::");
		
		//Remove from lucene
		Indexer indexer = IndexerRegistryUtil.getIndexer(Module.class);
		indexer.delete(fileobj);
		
		try {
			resourceLocalService.deleteResource(
					fileobj.getCompanyId(), Module.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, fileobj.getPrimaryKey());
		} catch (PortalException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isInfoEnabled())log.info(e.getMessage());
			throw new SystemException(e);
		}
		assetEntryLocalService.deleteEntry(Module.class.getName(), fileobj.getModuleId());
		modulePersistence.remove(fileobj);

		//auditing
		AuditingLogFactory.audit(fileobj.getCompanyId(), fileobj.getGroupId(), Module.class.getName(), 
				fileobj.getModuleId(), userIdAction, AuditConstants.DELETE, null);
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule(Module module, ServiceContext serviceContext) throws PortalException, SystemException{
		return updateModule(module, true, serviceContext);
	}

	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule(Module module, long userIdAction) throws SystemException, PortalException{
		return updateModule(module, userIdAction, null);
	}
	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule(Module module, long userIdAction, ServiceContext serviceContext) throws SystemException, PortalException {
		
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "title");
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "description");
		module.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		if(serviceContext != null)
			module.setExpandoBridgeAttributes(serviceContext);
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
		
	    //AssetEntry
	    long[] categoryIds = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetCategoryIds() : null;
	    String[] tagNames = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetTagNames() : null;
	    AssetEntry assetEntry = assetEntryLocalService.updateEntry(module.getUserId(), module.getGroupId(), 
	    		Module.class.getName(), module.getModuleId(), module.getUuid(), 0, categoryIds,
	    		tagNames, true, module.getStartDate(), module.getEndDate(),
	    		new Date(System.currentTimeMillis()), null,	ContentTypes.TEXT_HTML, module.getTitle(), module.getDescription(),
	    		module.getDescription(), null, null, 0, 0, null, false);
	    
	    //Index
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		indexer.reindex(module);
		
		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				module.getModuleId(), userIdAction, AuditConstants.UPDATE, null);
		
		return module;
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule(Module module, boolean merge) throws SystemException {
		Module updatedModule = null;
		try {
			updatedModule = updateModule(module, merge, null);
		} catch (PortalException e) {
			e.printStackTrace();
		}
		return updatedModule;
	}
	@Indexable(type=IndexableType.REINDEX)
	public Module updateModule(Module module, boolean merge, ServiceContext serviceContext) throws SystemException, PortalException {
		
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "title");
		module = LmsLocaleUtil.checkDefaultLocale(Module.class, module, "description");
		module.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		if(serviceContext != null)
			module.setExpandoBridgeAttributes(serviceContext);
		module = super.updateModule(module, merge);
		
	    //AssetEntry
	    long[] categoryIds = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetCategoryIds() : null;
	    String[] tagNames = (Validator.isNotNull(serviceContext)) ? serviceContext.getAssetTagNames() : null;
		AssetEntry assetEntry = assetEntryLocalService.updateEntry(module.getUserId(), module.getGroupId(), 
				Module.class.getName(), module.getModuleId(), module.getUuid(), 0, categoryIds,
				tagNames, true, module.getStartDate(), module.getEndDate(),
				new Date(System.currentTimeMillis()), null,	ContentTypes.TEXT_HTML, module.getTitle(), module.getDescription(),
				module.getDescription(), null, null, 0, 0, null, false);
	    
	    //Index
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Module.class);
		indexer.reindex(module);

		//auditing
		AuditingLogFactory.audit(module.getCompanyId(), module.getGroupId(), Module.class.getName(), 
				module.getModuleId(), module.getUserId(), AuditConstants.UPDATE, null);
		
		return module;
	}
	
	public boolean isUserPassed(long moduleId,long userId) throws SystemException
	{
		ModuleResult moduleResult=moduleResultLocalService.getByModuleAndUser(moduleId, userId);
		return moduleResult!=null && moduleResult.getPassed();
	}
	
	public boolean isUserFinished(long moduleId,long userId) throws SystemException, PortalException {
		if(isUserPassed(moduleId, userId)) {
			return true;
		}
		if(userTimeFinished(moduleId, userId)){
			return true;
		}
		List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
		boolean finished = false;
		for(LearningActivity activity : activities){	
			if(activity.getWeightinmodule()!=0){
				LearningActivityTry activityTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(activity.getActId(), userId);
				if(activityTry!=null){
					if(activityTry.getEndUserDate()!=null){
						finished = true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		if(finished) {
			return true;
		}
		return false;
	}
	
	public boolean userTimeFinished(long moduleId,long userId) throws SystemException, PortalException
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
		ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(), userId);
		if(mr!=null && !mr.getPassed()&&mr.getStartDate()!=null)
		{
			long courtesyTime=GetterUtil.getLong(PropsUtil.get("lms.module.courtesytime.miliseconds"),0);
			long usedTime=System.currentTimeMillis()-mr.getStartDate().getTime();
			if(theModule.getAllowedTime()!=0)
			{
				if ( theModule.getAllowedTime() + courtesyTime - usedTime < 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Se mira si el módulo está bloqueado, NO SE TIENE EN CUENTA EL CURSO, PARA SABER SI TIENES BLOQUEADO EL CURSO LLAMAR AL ISLOCKED DEL CURSO
	 * Se harán las siguientes comprobaciones:
	 * - Si tienes permisos 
	 * - Que hayas superado el módulo precedente
	 * - Fecha inicio/fin del módulo (teniendo en cuenta las convocatorias del usuario)
	 * @param moduleId Id del módulo
	 * @param userId Id del usuario
	 * @return true si el módulo está bloqueado, false en caso contrario
	 */
	public boolean isLocked(long moduleId,long userId) throws Exception{
		
		Module module = null;
		try {
			module = ModuleLocalServiceUtil.getModule(moduleId);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(module == null){
			return true;
		}
		
		return module.isLocked(userId);

	}
	
	public long countByGroupId(long groupId) throws SystemException
	{
		return ModuleUtil.countByGroupId(groupId);
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
	
	public List<Module> getModulesByCompanyId(long companyId) throws SystemException{
		return modulePersistence.findByCompanyId(companyId);
	}
	
}