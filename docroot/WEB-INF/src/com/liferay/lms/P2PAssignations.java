package com.liferay.lms;

import java.util.Calendar;
import java.util.List;

import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;

public class P2PAssignations implements MessageListener {
	Log log = LogFactoryUtil.getLog(P2PAssignations.class);

	@Override
	public void receive(Message arg0) {
		
		asignCorrectionsToP2PActivities();
		
	}
	
	/* DEPRECATED */
	@Deprecated
	public void asignP2PActivities(){

		//Obtener el dia de ayer
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.DAY_OF_YEAR, yesterday.get(Calendar.DAY_OF_YEAR)-1);
		
		//Obtener la lista de actividades p2p de usuarios que entregaron la actividad en el dia.
		List<P2pActivity> activities = null;
		try {
			activities = P2pActivityLocalServiceUtil.getP2PActivitiesInDay(yesterday);
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(activities != null){
			//Recorrer la lista de usuario para asignar.
			long actId = 0;
			int numAsigns = 3;
			String validations =  null;
			List<User> users = null;
			for(P2pActivity activity:activities){
				actId = activity.getActId();
			
				//Obtener la lista de usuario que van a corregir la actividad
				numAsigns = 3;
				try {
					validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");

						numAsigns = Integer.valueOf(validations);
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					users = P2pActivityLocalServiceUtil.getUsersToCorrectP2P(actId, activity.getUserId(), numAsigns, yesterday);
					
					//Asignar al usuario que entrega sus correctores.
					P2pActivityCorrectionsLocalServiceUtil.asignP2PCorrectionsToUsers(actId, activity.getP2pActivityId(), users);
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

	}
	
	public void asignCorrectionsToP2PActivities(){
		if(log.isDebugEnabled())log.debug("Check P2P asign corrections");

		//Obtener la lista de actividades p2p de todos los usuarios.
		List<P2pActivity> activities = null;
		
		activities = P2pActivityLocalServiceUtil.getP2pActivitiesByAssignationsCompleted(false);
		if(activities != null){
			//Recorrer todas las activities.
			for(P2pActivity activity:activities){
				asignCorrectionP2PActivity(activity);
				
			}
		}
	
	}
	
	public void asignCorrectionP2PActivity(P2pActivity activity){
		long actId = activity.getActId();
		int activityAsignations = 0;	
		//Obtener las validaciones que tiene que tener la actividad.
		int numAsigns = 3;
		boolean teamAssignation = false;
		//Get assignation Number
		try {
			String validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
			if(Validator.isNotNull(validations)){
				numAsigns = Integer.valueOf(validations);
			}
		} catch (SystemException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//Get assignation type
		try {
			String assignationType = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"assignationType");
			if(Validator.isNotNull(assignationType)){
				teamAssignation = "team".equals(assignationType);
			}
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//Obtenemos las asignaciones que ya est�n realidas.
		try {
			activityAsignations =P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToUser(activity.getActId(),activity.getUserId());
		
			//Si la actividad no tiene asignadas todas las tareas que tiene que corregir.
			if( activityAsignations < numAsigns && !activity.isAsignationsCompleted()){

				List<P2pActivity> activitiesToAsign = P2pActivityLocalServiceUtil.getP2pActivitiesToCorrect(actId, activity.getP2pActivityId(), numAsigns - activityAsignations, teamAssignation);

				if(log.isDebugEnabled()){
					log.debug("P2P assign corrections to activity::"+activity.getActId()+"::"+activity.getUserId());
					for(P2pActivity p2pactivity : activitiesToAsign){
						log.debug("assign::"+p2pactivity.getUserId());
					}
				}
				
				//Asignar al usuario que entrega sus correctores.
				P2pActivityCorrectionsLocalServiceUtil.asignCorrectionsToP2PActivities(actId, activity.getP2pActivityId(),numAsigns - activityAsignations, activitiesToAsign, activity.getUserId());

			}else{
				activity.setAsignationsCompleted(true);
				P2pActivityLocalServiceUtil.updateP2pActivity(activity);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
