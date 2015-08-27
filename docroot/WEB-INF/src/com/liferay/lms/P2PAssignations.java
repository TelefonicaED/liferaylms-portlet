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
import com.liferay.portal.model.User;

public class P2PAssignations implements MessageListener {
	Log log = LogFactoryUtil.getLog(P2PAssignations.class);

	@Override
	public void receive(Message arg0) {
		
		asignCorrectionsToP2PActivities();
		
	}
	
	/* DEPRECATED */
	public void asignP2PActivities(){

		try {
			//Obtener el dia de ayer
			Calendar yesterday = Calendar.getInstance();
			yesterday.set(Calendar.DAY_OF_YEAR, yesterday.get(Calendar.DAY_OF_YEAR)-1);
			
			//Obtener la lista de actividades p2p de usuarios que entregaron la actividad en el dia.
			List<P2pActivity> activities = P2pActivityLocalServiceUtil.getP2PActivitiesInDay(yesterday);
			
			//Recorrer la lista de usuario para asignar.
			for(P2pActivity activity:activities){
				long actId = activity.getActId();
			
				//Obtener la lista de usuario que van a corregir la actividad
				int numAsigns = 3;
				String validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
				
				try {
					numAsigns = Integer.valueOf(validations);
				} catch (Exception e) {}
				
				List<User> users = P2pActivityLocalServiceUtil.getUsersToCorrectP2P(actId, activity.getUserId(), numAsigns, yesterday);
				
				//Asignar al usuario que entrega sus correctores.
				P2pActivityCorrectionsLocalServiceUtil.asignP2PCorrectionsToUsers(actId, activity.getP2pActivityId(), users);
			}

		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
	
	}
	
	public void asignCorrectionsToP2PActivities(){
		if(log.isDebugEnabled())log.debug("Check P2P asign corrections");

		try {
			Calendar day = Calendar.getInstance();
			//Obtener la lista de actividades p2p de todos los usuarios.
			List<P2pActivity> activities = null;//P2pActivityLocalServiceUtil.getP2pActivities(0, P2pActivityLocalServiceUtil.getP2pActivitiesCount());
			
			//Seleccionamos las actividades p2p entre ayer y antes de ayer.
			DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(P2pActivity.class)
					.add(PropertyFactoryUtil.forName("asignationsCompleted").eq(false));
			
			activities = (List<P2pActivity>)P2pActivityLocalServiceUtil.dynamicQuery(consulta);
			
			int numActivities = activities.size();
			
			//Recorrer todas las activities.
			for(P2pActivity activity:activities){
				long actId = activity.getActId();
				
				//Obtener las validaciones que tiene que tener la actividad.
				int numAsigns = 3;
				String validations = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
				
				try {
					numAsigns = Integer.valueOf(validations);
				} catch (Exception e) {}
				
				//Obtenemos las asignaciones que ya est�n realidas.
				int activityAsignations =P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToUser(activity.getActId(),activity.getUserId());

				//Si la actividad no tiene asignadas todas las tareas que tiene que correguir.
				if( activityAsignations < numAsigns && !activity.isAsignationsCompleted()){

					List<P2pActivity> activitiesToAsign = P2pActivityLocalServiceUtil.getP2pActivitiesToCorrect(actId, activity.getP2pActivityId(), numAsigns - activityAsignations);

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
				
			}

		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
	
	}

}
