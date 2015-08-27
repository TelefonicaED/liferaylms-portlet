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
import java.util.List;

import com.liferay.lms.P2PSendMailAsignation;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.base.P2pActivityCorrectionsLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;


/**
 * The implementation of the p2p activity corrections local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.P2pActivityCorrectionsLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil} to access the p2p activity corrections local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.P2pActivityCorrectionsLocalServiceBaseImpl
 * @see com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil
 */
public class P2pActivityCorrectionsLocalServiceImpl
	extends P2pActivityCorrectionsLocalServiceBaseImpl{
	
	public P2pActivityCorrections findByP2pActivityIdAndUserId(Long p2pActivityId,
			Long userId){
		
		try{
			List<P2pActivityCorrections> p2pList = p2pActivityCorrectionsPersistence.findByP2pActivityIdAndUserId(p2pActivityId, userId);
			if(!p2pList.isEmpty())
			{

				//auditing
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				if(serviceContext!=null){
					AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
						p2pActivityId, userId, AuditConstants.GET, null);
				}else{
					LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(p2pList.get(0).getActId());
					if(la!=null){
						AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivity.class.getName(), 
								p2pActivityId, userId, AuditConstants.GET, null);
					}
				}
				
				return p2pList.get(0);
			}else{
				return null;
			}
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByP2pActivityIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public boolean exitsCorP2p(Long p2pActivityId, Long userId){
		
		try{
			List<P2pActivityCorrections> p2pList = p2pActivityCorrectionsPersistence.findByP2pActivityIdAndUserId(p2pActivityId, userId);
			if(!p2pList.isEmpty() && p2pList.size()>0)
			{
				return true;
			}else{
				return false;
			}
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("P2pActivityCorrectionsLocalService.exitsCorP2p no exist correction with p2pActivityId:"+p2pActivityId+" - userId"+userId);
				_log.error(e.getMessage());
			}
			return false;
		}
	}
	public List<P2pActivityCorrections> findByP2pActivityId(Long p2pActivityId){
		
		try{
			 List<P2pActivityCorrections> p2pCorrections = p2pActivityCorrectionsPersistence.findByP2pActivityId(p2pActivityId);

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
					p2pActivityId, serviceContext.getUserId(), AuditConstants.GET, null);
			}
			
			return p2pCorrections;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByP2pActivityIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public List<P2pActivityCorrections> findByActIdIdAndUserId(Long actId,
			Long userId){
		
		try{

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
					actId, serviceContext.getUserId(), AuditConstants.GET, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivity.class.getName(), 
							actId, la.getUserId(), AuditConstants.GET, null);
				}
			}
			
			return p2pActivityCorrectionsPersistence.findByActIdAndUserId(actId, userId);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByActIdIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<P2pActivityCorrections> findByActIdAndUserIdOrderByDate(Long actId, Long userId) throws SystemException{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.addOrder(OrderFactoryUtil.getOrderFactory().desc("date"));
	
		List<P2pActivityCorrections> activities = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);

		return activities;

	}
	public List<P2pActivityCorrections> findByActIdAndUserIdOrderById(Long actId, Long userId) throws SystemException{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.addOrder(OrderFactoryUtil.getOrderFactory().desc("p2pActivityCorrectionsId"));
	
		List<P2pActivityCorrections> activities = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);

		return activities;

	}
	
	public P2pActivityCorrections addorUpdateP2pActivityCorrections(P2pActivityCorrections p2pActCor){
		
		try{
			
			long p2pActCorId = counterLocalService.increment(P2pActivityCorrections.class.getName());
			p2pActCor.setNew(true);
			p2pActCor.setP2pActivityCorrectionsId(p2pActCorId);
			
			P2pActivityCorrections pac = p2pActivityCorrectionsPersistence.update(p2pActCor, false);
			
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivityCorrections.class.getName(), 
					p2pActCorId, serviceContext.getUserId(), AuditConstants.ADD, null);
			
			return pac;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByActIdIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	public void asignP2PCorrectionsToUsers(long actId, long p2pActId, List<User> usersList) throws SystemException{
		
		int asigned = 0;
		
		int numAsigns = 3;
		String validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
		
		try {
			numAsigns = Integer.valueOf(validations);
		} catch (Exception e) {}
		
		LearningActivity learning = null;
		
		for(User user : usersList){		
			asigned = P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToP2P(p2pActId);
			
			//Comprobar que el p2p no tenga ya asignadas todas las correcciones y que el usuario actual no lo tenga asignado.
			if(numAsigns > asigned && !isP2PAsignationDone(p2pActId, user.getUserId())){
				
				long p2pActivityCorrectionsId = counterLocalService.increment(P2pActivityCorrections.class.getName());
				P2pActivityCorrections p2p = p2pActivityCorrectionsPersistence.create(p2pActivityCorrectionsId);
				p2p.setActId(actId);
				p2p.setUserId(user.getUserId());
				p2p.setDate(null);
				p2p.setDescription(null);
				p2p.setP2pActivityId(p2pActId);
				p2pActivityCorrectionsPersistence.update(p2p, true);
				
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivityCorrections.class.getName(), 
						p2pActivityCorrectionsId, serviceContext.getUserId(), AuditConstants.ADD, null);

				//Incrementamos el contador de correcciones que ha realizado.
				try {
					P2pActivity activity = P2pActivityLocalServiceUtil.getP2pActivity(p2pActId);
					activity.setCountCorrections(activity.getCountCorrections()+1);
					P2pActivityLocalServiceUtil.updateP2pActivity(activity);
					
					learning = LearningActivityLocalServiceUtil.getLearningActivity(activity.getActId());
				} catch (PortalException e) {}
				
				asigned++;

			}
		}
	}
	
public void asignCorrectionsToP2PActivities(long actId, long p2pActivityId,int numValidaciones, List<P2pActivity> activityList, long userId) throws SystemException{
		
		//Precondicion: Solo asignamos las tareas cuando tengamos todas las que necesitamos.	
		if(activityList == null || activityList.isEmpty() || activityList.size() < numValidaciones){
			return;
		}
	
		for(P2pActivity activity : activityList){		
	
			long p2pActivityCorrectionsId = counterLocalService.increment(P2pActivityCorrections.class.getName());
			P2pActivityCorrections p2p = p2pActivityCorrectionsPersistence.create(p2pActivityCorrectionsId);
			p2p.setActId(actId);
			p2p.setUserId(userId);
			p2p.setDate(null);
			p2p.setDescription(null);
			p2p.setP2pActivityId(activity.getP2pActivityId());
			p2pActivityCorrectionsPersistence.update(p2p, true);

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivityCorrections.class.getName(), 
					p2pActivityCorrectionsId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivityCorrections.class.getName(), 
							actId, la.getUserId(), AuditConstants.GET, null);
				}
			}

			//Incrementamos el contador de correcciones que se ha asignado para corregir.
			activity.setCountCorrections(activity.getCountCorrections()+1);
			P2pActivityLocalServiceUtil.updateP2pActivity(activity);
			
		}
		
		//Ponemos que ya estan realizadas las asignaciones para no tener que calcular de nuevo las asignaciones.
		try {
			P2pActivity p2pActivity = P2pActivityLocalServiceUtil.getP2pActivity(p2pActivityId);
			p2pActivity.setAsignationsCompleted(true);
			P2pActivityLocalServiceUtil.updateP2pActivity(p2pActivity);
			
			//Mandar email al usuario avisando de que ya puede corregir sus actividades.
			if(!LearningActivityLocalServiceUtil.islocked(actId, p2pActivity.getUserId())){

				try {
					LearningActivity learn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					User user = UserLocalServiceUtil.getUser(p2pActivity.getUserId());
					
					Group group = GroupLocalServiceUtil.getGroup(learn.getGroupId());
					Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());
					
					Course course= CourseLocalServiceUtil.getCourseByGroupCreatedId(learn.getGroupId());
					
					com.liferay.lms.model.Module module = ModuleLocalServiceUtil.getModule(learn.getModuleId());
					String courseFriendlyUrl = "";
					String courseTitle = "";
					String activityTitle = learn.getTitle(user.getLocale());
					String moduleTitle =  module.getTitle(user.getLocale());
					String portalUrl = PortalUtil.getPortalURL(company.getVirtualHostname(), PortalUtil.getPortalPort(), false);
					String pathPublic = PortalUtil.getPathFriendlyURLPublic();
					
					if(course != null){
						courseFriendlyUrl = portalUrl + pathPublic + course.getFriendlyURL();
						courseTitle = course.getTitle(user.getLocale());
					}
						
					String[] params={activityTitle, moduleTitle, courseTitle, courseFriendlyUrl};
					
					//Enviar los emails.
					P2PSendMailAsignation.sendMail(user.getEmailAddress(), user.getFullName(), params, user.getCompanyId(), user.getLocale());
		
				} catch (Exception e) {}
				
			} 			
			
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} 
	}
	
	@SuppressWarnings("unchecked")
	public int getNumCorrectionsAsignToP2P(long p2pActId) throws SystemException{
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("p2pActivityId").eq(p2pActId));
	
		List<P2pActivityCorrections> activities = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);

		return activities.size();
	}
	
	@SuppressWarnings("unchecked")
	public int getNumCorrectionsAsignToUser(long actId, long userId) throws SystemException{
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId));
	
		List<P2pActivityCorrections> activities = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);

		return activities.size();
	}
	
	@SuppressWarnings("unchecked")
	public List<P2pActivityCorrections> getCorrectionsDoneByUserInP2PActivity(long actId, long userId) throws SystemException{
		
		List<P2pActivityCorrections> res = new ArrayList<P2pActivityCorrections>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("date").isNotNull());
	
		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);
		
		if(list!=null){
			return list;
		}
		return res;
	}
	
	/**
	 * Para saber si el usuario ya ha realizado todas las correcciones que se indica en el extracontent.
	 */
	@SuppressWarnings("unchecked")
	public boolean areAllCorrectionsDoneByUserInP2PActivity(long actId, long userId) throws SystemException{
		
		boolean res = false;
		
		// Obtener las validaciones que tiene que tener la actividad.
		int numAsigns = 3;
		String validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId, "validaciones");
		try {numAsigns = Integer.valueOf(validations);} catch (Exception e) {}
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("date").isNotNull());
	
		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);
		
		if(list!=null){
			return list.size() >= numAsigns;
		}
		return res;
	}
	
	/**
	 * Obtenemos la lista de correcciones que se le asignaron a una tarea p2p.
	 * @param p2pActivityId
	 * @return
	 * @throws SystemException
	 */
	@SuppressWarnings("unchecked")
	public List<P2pActivityCorrections> getCorrectionByP2PActivity(long p2pActivityId) throws SystemException{
		//Para devolver una lista vacia en lugar de null.
		List<P2pActivityCorrections> res = new ArrayList<P2pActivityCorrections>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("p2pActivityId").eq(p2pActivityId));
	
		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);
		if(list!=null){
			return list;
		}
		
		return res;
	}
	

	/**
	 * A partir del id de una actividad, obtenemos la media de resultados que ha obtenido en ellas.
	 * En las correcciones que se han realizado y tiene fecha de realizaci�n.
	 * @param p2pActivityId
	 * @return la media de results
	 */
	public long getAVGCorrectionsResults(long p2pActivityId){
		long res = 0 ;
		
		try {
			List<P2pActivityCorrections> corrections = getCorrectionByP2PActivity(p2pActivityId);
			
			long numCorrected = 0;
			long totalResult = 0;
			for(P2pActivityCorrections correct: corrections){
				if(correct.getDate() != null){
					numCorrected++;
					totalResult = totalResult + correct.getResult();
				}
			}
			
			//Obtenemos la media del resultado.
			if(numCorrected > 0){
				res = totalResult / numCorrected;
			}
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isP2PAsignationDone(long p2pActivityId, long userId) throws SystemException{
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class, classLoader)
				.add(PropertyFactoryUtil.forName("p2pActivityId").eq(p2pActivityId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId));
	
		List<P2pActivityCorrections> activities = (List<P2pActivityCorrections>)p2pActivityCorrectionsPersistence.findWithDynamicQuery(consulta);
		
		return activities.size() != 0;
	}
	
	private static Log _log = LogFactoryUtil.getLog(P2pActivity.class);
}