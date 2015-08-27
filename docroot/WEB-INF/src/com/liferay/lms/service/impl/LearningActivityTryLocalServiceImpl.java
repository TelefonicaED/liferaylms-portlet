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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.liferay.lms.NoSuchLearningActivityException;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityTryLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the learning activity try local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityTryLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityTryLocalServiceUtil} to access the learning activity try local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityTryLocalServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityTryLocalServiceUtil
 */
public class LearningActivityTryLocalServiceImpl
	extends LearningActivityTryLocalServiceBaseImpl {
	@Override
	public LearningActivityTry updateLearningActivityTry(
			LearningActivityTry learningActivityTry) throws SystemException {
		
		return updateLearningActivityTry(learningActivityTry,true);
	}
	public long getLearningActivityTryByActUserCount(long actId,long userId) throws SystemException
	{
		return learningActivityTryPersistence.countByact_u(actId, userId);
	}
	public void deleteUserTries(long actId,long userId) throws SystemException, PortalException
	{
		java.util.List<LearningActivityTry> userTries=learningActivityTryPersistence.findByact_u(actId, userId);
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		for(LearningActivityTry userTry:userTries)
		{
			this.deleteLearningActivityTry(userTry.getLatId());
			
			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityTry.class.getName(), 
					actId, serviceContext.getUserId(), AuditConstants.DELETE, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityTry.class.getName(), 
						actId, la.getUserId(), AuditConstants.DELETE, null);
				}
			}
		}
		if(learningActivityResultLocalService.existsLearningActivityResult(actId, userId))
		{
			LearningActivityResult res=learningActivityResultLocalService.getByActIdAndUserId(actId, userId);
			res.setResult(0);
			res.setPassed(false);
			res.setEndDate(null);
			learningActivityResultLocalService.updateLearningActivityResult(res);
			if(larn.getWeightinmodule()>0)
			{
				ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(larn.getModuleId(), userId);
				if(mr!=null)
				{
					mr.setPassed(false);
					ModuleResultLocalServiceUtil.updateModuleResult(mr);
				}
			}
		}
		
	}
	
	public java.util.List<LearningActivityTry> getLearningActivityTryByActUser(long actId,long userId) throws SystemException
	{
		return learningActivityTryPersistence.findByact_u(actId, userId);
	}
	@Override
	public LearningActivityTry updateLearningActivityTry(
			LearningActivityTry learningActivityTry, boolean merge)
			throws SystemException{
		try
		{
		if(learningActivityTry.getEndDate()!=null)
		{
			LearningActivityResultLocalServiceUtil.update(learningActivityTry)	;
		}
		return super.updateLearningActivityTry(learningActivityTry, merge);
	}
		catch(PortalException e)
		{
			throw new SystemException(e);
		}
	}

	public LearningActivityTry createLearningActivityTry(long actId,ServiceContext serviceContext) throws SystemException, PortalException
	{
		LearningActivity learningActivity = learningActivityPersistence.fetchByPrimaryKey(actId);
		
		if(Validator.isNull(learningActivity)) {
			throw new NoSuchLearningActivityException();
		}
		
		LearningActivityTry larnt =
			learningActivityTryPersistence.create(counterLocalService.increment(
					LearningActivityTry.class.getName()));
		larnt.setUserId(serviceContext.getUserId());
		larnt.setActId(actId);
		larnt.setStartDate(serviceContext.getCreateDate(null));
		learningActivityTryPersistence.update(larnt, true);
		learningActivityResultLocalService.update(larnt);
		
		courseResultLocalService.softInitializeByGroupIdAndUserId(learningActivity.getGroupId(), serviceContext.getUserId());

		//auditing
		AuditingLogFactory.audit(learningActivity.getCompanyId(), learningActivity.getGroupId(), LearningActivityTry.class.getName(), 
				actId, serviceContext.getUserId(), AuditConstants.ADD, null);
		
		return larnt;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsersByLearningActivity(long actId) throws SystemException, PortalException
	{ 			
		List<User> users = new ArrayList<User>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, classLoader)
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)));
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			Long uId = activity.getUserId();
			User u = UserLocalServiceUtil.getUserById(uId.longValue());
			if(u!=null && !users.contains(u))
				users.add(u);
		}
		return users;		
	}
	
	@SuppressWarnings("unchecked")
	public LearningActivityTry getLastLearningActivityTryByActivityAndUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
				"portletClassLoader"))
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
					.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
					.addOrder(PropertyFactoryUtil.forName("endDate").desc());
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			//Necesitamos la primera, que est� ordenada por la �ltima realizada.
			return activity;
		}
		return null;		
	}
	@SuppressWarnings("unchecked")	
	public LearningActivityTry createOrDuplicateLast(long actId,ServiceContext serviceContext) throws SystemException, PortalException
	{ 	
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
				"portletClassLoader"))
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
					.add(PropertyFactoryUtil.forName("userId").eq(new Long(serviceContext.getUserId())))
					.addOrder(PropertyFactoryUtil.forName("startDate").desc());
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);
		LearningActivityTry  lastTry=null;
		if(activities!=null && activities.size()>0)
		{
		   lastTry=activities.get(0);
		}
		if(lastTry==null)
		{
			return createLearningActivityTry(actId, serviceContext);
		}
		else
		{
			LearningActivityTry newTry=createLearningActivityTry(actId, serviceContext);
			newTry.setResult(lastTry.getResult());
			newTry.setTryData(lastTry.getTryData());
			newTry.setTryResultData(lastTry.getTryResultData());
			updateLearningActivityTry(newTry);
			return newTry;
		}
		
	}
	@SuppressWarnings("unchecked")
	public LearningActivityTry getLearningActivityTryNotFinishedByActUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
				"portletClassLoader"))
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
					.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
					.add(PropertyFactoryUtil.forName("endDate").isNull())
					.addOrder(PropertyFactoryUtil.forName("startDate").desc());
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			//Necesitamos la primera, que est� ordenada por la �ltima realizada.
			return activity;
		}
		return null;		
	}
	
	public int getTriesCountByActivityAndUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		return learningActivityTryPersistence.countByact_u(actId, userId);	
	}
	
	public int getTriesCountByActivity(long actId) throws SystemException, PortalException
	{ 			
		return learningActivityTryPersistence.countByact(actId);	
	}
	
	public HashMap<Long, Long> getMapTryResultData(long actId,long userId) throws SystemException, PortalException 
	{
		HashMap<Long, Long> answersMap = new HashMap<Long, Long>();
		LearningActivityTry actTry = getLastLearningActivityTryByActivityAndUser(actId, userId);
		String xml = actTry.getTryResultData();
		
		if(xml.equals(""))
			return answersMap;
			
		Document document;
		try {
			document = SAXReaderUtil.read(xml);
			Element rootElement = document.getRootElement();
			
			for(Element question:rootElement.elements("question")){
				for(Element answer:question.elements("answer")){
	    			answersMap.put(Long.valueOf(question.attributeValue("id")), Long.valueOf(answer.attributeValue("id"))) ;
				}
			}
		} catch (DocumentException e) {
		}
		
		return answersMap;
	}
	
	@SuppressWarnings("unchecked")
	public boolean canUserDoANewTry(long actId,long userId) throws Exception{
		
		//Si ya ha pasado el test, no puede hacer m�s intentos.
		if(LearningActivityResultLocalServiceUtil.userPassed(actId, userId))
		{
			return false;
		}
		if(LearningActivityLocalServiceUtil.islocked(actId, userId))
		{
			return false;
		}
		
		//Mirar si los intentos que tiene son menores de los intentos posibles
		int userTries = getTriesCountByActivityAndUser(actId,userId);
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		Long activityTries=activity.getTries();
		if(activityTries==0)
		{
			return true;
		}
		
		//Mirar que alguno de sus intentos no se terminase.
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
				.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
				.add(PropertyFactoryUtil.forName("endDate").isNull());
				
		long opened = learningActivityTryPersistence.countWithDynamicQuery(consulta);
			
		//Si tiene menos intentos de los que se puede hacer.
		return Long.valueOf(userTries-opened) < activityTries;
	}
	
	@SuppressWarnings("unchecked")
	public boolean areThereTriesNotFromEditors(LearningActivity activity) throws Exception
	{ 		
		boolean resp = false;
		List<User> users = getUsersByLearningActivity(activity.getActId());
		for(User usu:users){
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(usu);
			if(!(permissionChecker.hasPermission(activity.getGroupId(),"com.liferay.lms.model",activity.getGroupId(),"UPDATE_ACTIVE")||
					permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),ActionKeys.UPDATE)||
					permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(),ActionKeys.UPDATE))){
				resp = true;
				break;
			}
		}
		return resp;		
	}
}