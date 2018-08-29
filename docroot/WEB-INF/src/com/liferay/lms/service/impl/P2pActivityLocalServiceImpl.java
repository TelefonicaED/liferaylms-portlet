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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.base.P2pActivityLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.P2pActivityFinderUtil;
import com.liferay.lms.service.persistence.P2pActivityUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.TeamLocalService;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the p2p activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.P2pActivityLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.P2pActivityLocalServiceUtil} to access the p2p activity local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.P2pActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.P2pActivityLocalServiceUtil
 */
public class P2pActivityLocalServiceImpl extends P2pActivityLocalServiceBaseImpl {
	
	@BeanReference(type = TeamLocalService.class)
	protected TeamLocalService teamLocalService;
	
	private static Log log = LogFactoryUtil.getLog(P2pActivityLocalServiceImpl.class);
	
	
	public P2pActivity findByActIdAndUserId(long actId, long userId)
			throws SystemException {
		try{
			List<P2pActivity> myp2ps = p2pActivityPersistence.findByActIdAndUserId(actId, userId);

			
			
			if(!myp2ps.isEmpty()){
				for(P2pActivity myActivity : myp2ps){
					//auditing
					ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
					if(serviceContext!=null){
						AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
								myActivity.getActId(), serviceContext.getUserId(), AuditConstants.GET, null);
					}else{
						LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
						if(la!=null){
							AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivity.class.getName(), 
									myActivity.getActId(), la.getUserId(), AuditConstants.GET, null);
						}
					}
					
					return myActivity;
				}
			}
			return null;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public boolean existP2pAct(long actId, long userId)
			throws SystemException {
		try{
			List<P2pActivity> myp2ps = p2pActivityPersistence.findByActIdAndUserId(actId, userId);

			if(!myp2ps.isEmpty() && myp2ps.size()>0){
				return true;
			}
			return false;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("No P2pActivity. exitP2pAct");
				_log.error(e.getMessage());
			}
			return false;
		}
	}
	public List<P2pActivity> findByActId(long actId)
			throws SystemException {
		try{
			List<P2pActivity> pas = p2pActivityPersistence.findByActId(actId);

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
			
			return pas;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public List<P2pActivity> findByActId(long actId, int start, int end)
			throws SystemException {
		try{
			return p2pActivityPersistence.findByActId(actId, start, end);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActId(Start-End)");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	public int countByActId(long actId){
		try {
			return P2pActivityUtil.countByActId(actId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	@SuppressWarnings("unchecked")
	public List<P2pActivity> findByActIdOrderByP2pId(long actId)
			throws SystemException {
		try{
			
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
			DynamicQuery dq=DynamicQueryFactoryUtil.forClass(P2pActivity.class, classLoader);
			Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
			dq.add(criterion);
			Order createOrder=OrderFactoryUtil.getOrderFactory().asc("p2pActivityId");
			dq.addOrder(createOrder);

			List<P2pActivity> modulesp=(List<P2pActivity>)P2pActivityLocalServiceUtil.dynamicQuery(dq);

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
			
			return modulesp;
			

		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActIdOrderByP2pId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsersToCorrectP2P(long actId, long userId, int numUsers, Calendar calendar) throws SystemException, PortalException{
		List<User> users = new ArrayList<User>();
		
		Calendar calendarStar = Calendar.getInstance();
		calendarStar.setTime(calendar.getTime());
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(calendar.getTime());

		calendarStar.set(Calendar.HOUR_OF_DAY, 0);
		calendarStar.set(Calendar.MINUTE, 0);
		calendarStar.set(Calendar.SECOND, 0);
		
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);
		
		int selected = 0;
	
		//Seleccionamos las actividades p2p entre ayer y antes de ayer.
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("userId").ne(userId))
				.add(PropertyFactoryUtil.forName("date").between(calendarStar.getTime(), calendarEnd.getTime()))
				.addOrder(OrderFactoryUtil.getOrderFactory().asc("countCorrections"));
	
		List<P2pActivity> activities = (List<P2pActivity>)p2pActivityPersistence.findWithDynamicQuery(consulta);

		for(P2pActivity activity:activities){

			Long uId = activity.getUserId();
			User u = UserLocalServiceUtil.getUserById(uId.longValue());
			int correctionsAsigned = P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToP2P(activity.getP2pActivityId());

			if(u!=null && !users.contains(u) ){
				users.add(u);
				selected++;
			}
			if(selected >= numUsers){
				return users;
			}
		}
		
		//Si no tenemos suficientes usuarios, buscamos otras 24 horas atras. Llamada recursiva con un dia menos.
		LearningActivity l = LearningActivityLocalServiceUtil.getLearningActivity(actId);

		Calendar calendarAct = Calendar.getInstance();
		calendarAct.setTime(calendar.getTime());

		calendarAct.set(Calendar.HOUR_OF_DAY,23);
		calendarAct.set(Calendar.MINUTE, 59);
		calendarAct.set(Calendar.SECOND, 59);
		
		Calendar dayBefore = Calendar.getInstance();
		dayBefore.setTime(calendarAct.getTime());
		dayBefore.set(Calendar.DAY_OF_YEAR, dayBefore.get(Calendar.DAY_OF_YEAR)-1);
		
		Calendar endDay =  Calendar.getInstance();
		endDay.setTime(l.getStartdate());
		
		//Paramos la recursividad cuando no tengamos mas dias en los que buscar.
		if(l.getStartdate().compareTo(dayBefore.getTime())<=0){
			
			List<User> usersBefore = getUsersToCorrectP2P(actId, userId, numUsers, dayBefore);
			
			for(User usu:usersBefore){
				if(usu!=null && !users.contains(usu))
					users.add(usu);
				
				selected++;
				if(selected >= numUsers){
					return users;
				}
			}
		}
		
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<P2pActivity> getP2pActivitiesToCorrect(long actId, long p2pActivityId, int numValidaciones) throws SystemException, PortalException{
		return getP2pActivitiesToCorrect(actId, p2pActivityId, numValidaciones, false);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Obtiene las actividades para realizar las correcciones de las actividades P2P
	 *  
	 * @param actId Id de la learningActivity de la p2p
	 * @param p2pActivityId Id de la entrega de la p2p
	 * @param numValidaciones Numero de validaciones configuradas en la actividad
	 * @param courseAssignation Si la asignación es por equipo o por curso.
	 * @return Listado de las asignaciones que hay que realizar
	 * @throws SystemException Si hay algún error
	 * @throws PortalException Si hay algún error
	 */
	public List<P2pActivity> getP2pActivitiesToCorrect(long actId, long p2pActivityId, int numValidaciones, boolean teamAssignation) throws SystemException, PortalException{
		List<P2pActivity> res = new ArrayList<P2pActivity>();
		
		LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
		P2pActivity p2pActivity = p2pActivityPersistence.fetchByPrimaryKey(p2pActivityId);
		
		List<P2pActivity> activities = null;
		if(p2pActivity!=null && la!=null){
			if(teamAssignation){
				List<Team> groupTeams = null;
				try{
					groupTeams = TeamLocalServiceUtil.getGroupTeams(la.getGroupId());
				}catch(SystemException e){
					log.debug(e);
				}
				//Si hay equipos, realizamos las asignaciones por equipos. Si no hay equipos, por grupo.
				if(groupTeams!=null && groupTeams.size()>0){
					List<Team> userTeams = teamLocalService.getUserTeams(p2pActivity.getUserId(), la.getGroupId());
					if(userTeams!=null && userTeams.size()>0){
						//Caso de asignacion por equipos.
						log.debug("******* USER ID "+p2pActivity.getUserId()  + " -->  ASIGNACIÓN POR EQUIPOS ");
						activities =P2pActivityFinderUtil.findByTeam(actId, p2pActivityId, userTeams, 0, numValidaciones);
						
					}else{
						//Caso de asignación suelta entre miembros sueltos del curso (sin equipos)
						log.debug("******* USER ID "+p2pActivity.getUserId()  + " -->  ASIGNACIÓN POR USUARIOS SUELTOS ");
						activities = P2pActivityFinderUtil.findByUserWithoutTeamActivities(actId, p2pActivityId, la.getGroupId(), 0, numValidaciones);
					}
				}else{
					log.debug("******* USER ID "+p2pActivity.getUserId()  + " -->  ASIGNACIÓN POR GRUPO (CURSO SIN EQUIPOS)");
					activities = P2pActivityFinderUtil.findByGroup(actId, p2pActivityId, 0, numValidaciones);
				}
			}else{
				log.debug("******* USER ID "+p2pActivity.getUserId()  + " -->  ASIGNACIÓN POR GRUPO ");
				activities = P2pActivityFinderUtil.findByGroup(actId, p2pActivityId, 0, numValidaciones);
			}
		}
			
		
		//Si no es null ni esta vacia, la asignamos para devolver, sino devolveremos vacia.
		if(activities!=null && !activities.isEmpty()){
			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
						actId, serviceContext.getUserId(), AuditConstants.GET, null);
			}else{
				
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivity.class.getName(), 
							actId, la.getUserId(), AuditConstants.GET, null);
				}
			}
			
			res = activities;
		}
				
		return res;
	}
	
	public List<P2pActivity> findByUserId(long userId) {
		 List<P2pActivity> activities = new ArrayList<P2pActivity>();
		try{
			return p2pActivityPersistence.findByUserId(userId);
		}catch(Exception e){			
		}
		return activities;
	}
	
	@SuppressWarnings("unchecked")
	public List<P2pActivity> getP2PActivitiesInDay(Calendar calendar) throws SystemException, PortalException{
		List<P2pActivity> activities = new ArrayList<P2pActivity>();
		List<P2pActivity> res = new ArrayList<P2pActivity>();
		
		GregorianCalendar calendarStar =  new GregorianCalendar();
		calendarStar.setTime(calendar.getTime());
		GregorianCalendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(calendar.getTime());

		calendarStar.set(Calendar.MILLISECOND, 0);
		calendarStar.set(Calendar.SECOND, 0);
		calendarStar.set(Calendar.MINUTE, 0);
		calendarStar.set(Calendar.HOUR_OF_DAY, 0);
		
		calendarEnd.set(Calendar.MILLISECOND, 0);
		calendarEnd.set(Calendar.SECOND, 59);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);

		int numAsigns = 3;
	
		//Seleccionamos las actividades p2p entre ayer y antes de ayer.
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("date").between(calendarStar.getTime(), calendarEnd.getTime()));
		
		activities = (List<P2pActivity>)p2pActivityPersistence.findWithDynamicQuery(consulta);
		
		//Creamos una lista con solo las p2pactivities que les falten asignar correctores.
		for(P2pActivity activity:activities){

			String validations = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"validaciones");
			
			try {
				numAsigns = Integer.valueOf(validations);
			} catch (Exception e) {}
			
			int correctionsAsigned = P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToP2P(activity.getP2pActivityId());
			if(correctionsAsigned < numAsigns ){
				res.add(activity);
			}
		}

		return res;
	}
	
	@SuppressWarnings("unchecked")
	public int getNumCorrectsByDayP2P(long actId, Calendar calendar) throws SystemException, PortalException{
		int res = 0;

		Calendar calendarStar = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();

		calendarStar.set(Calendar.HOUR_OF_DAY, 0);
		calendarStar.set(Calendar.MINUTE, 0);
		calendarStar.set(Calendar.SECOND, 0);
		
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);
		
		//Seleccionamos las actividades p2p entre ayer y antes de ayer.
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId))
				.add(PropertyFactoryUtil.forName("date").between(calendarStar, calendarEnd))
				.addOrder(OrderFactoryUtil.getOrderFactory().asc("countCorrections"));
	
		List<P2pActivity> activities = (List<P2pActivity>)p2pActivityPersistence.findWithDynamicQuery(consulta,0,1);

		for(P2pActivity activity:activities){
			if(activity!=null)
				return (int)activity.getCountCorrections();
		}
		return res;
	}
	
	@Override
	public P2pActivity addP2pActivity(P2pActivity newp2pAct)
			throws SystemException {
		try{
			
			long actP2PId = CounterLocalServiceUtil.increment(P2pActivity.class.getName());
			
			P2pActivity fileobj = p2pActivityPersistence.create(actP2PId);
			
			fileobj.setActId(newp2pAct.getActId());
			fileobj.setP2pActivityId(actP2PId);
			fileobj.setCountCorrections(0);
			fileobj.setDate(newp2pAct.getDate());
			fileobj.setDescription(newp2pAct.getDescription());
			fileobj.setFileEntryId(newp2pAct.getFileEntryId());
			fileobj.setUserId(newp2pAct.getUserId());

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
						newp2pAct.getActId(), serviceContext.getUserId(), AuditConstants.ADD, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(newp2pAct.getActId());
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivity.class.getName(), 
							newp2pAct.getActId(), la.getUserId(), AuditConstants.ADD, null);
				}
			}

			return p2pActivityPersistence.update(fileobj, false);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error doing P2pActivityLocalService.addP2pActivity");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	@Override
	public P2pActivity updateP2pActivity(P2pActivity newp2pAct)
			throws SystemException {
		try{
			P2pActivity p2pActivity = p2pActivityPersistence.update(newp2pAct, false);

			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), P2pActivity.class.getName(), 
						newp2pAct.getActId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(newp2pAct.getActId());
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), P2pActivityCorrections.class.getName(), 
							newp2pAct.getActId(), la.getUserId(), AuditConstants.UPDATE, null);
				}
			}
			
			return p2pActivity;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error doing P2pActivityLocalService.updateP2pActivity");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	
	
	public List<P2pActivity> getP2pActivitiesByAssignationsCompleted(boolean assignationsCompleted){
		List<P2pActivity> p2pActivities = new ArrayList<P2pActivity>();
		try{
			p2pActivities = p2pActivityPersistence.findByasignationsCompleted(assignationsCompleted);
		}catch(Exception e){
			e.printStackTrace();
		}
		return p2pActivities;
	}
	
	private static Log _log = LogFactoryUtil.getLog(P2pActivity.class);
}