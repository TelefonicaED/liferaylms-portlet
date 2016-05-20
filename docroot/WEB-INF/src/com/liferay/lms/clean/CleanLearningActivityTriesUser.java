package com.liferay.lms.clean;

import java.util.List;
import java.util.Locale;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.impl.CourseResultLocalServiceImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanLearningActivityTriesUser implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	private LearningActivity la = null;
	private User user = null;
	
	public CleanLearningActivityTriesUser(){
		super();
	}

	public void process() throws Exception{

		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(la.getActId(), user.getUserId());
		
		if(result != null){
			if(log.isDebugEnabled())log.debug(" result : " + result.getActId()+", result: "+result.getUserId() +", passed: "+result.getPassed() );

			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(result.getActId(), result.getUserId());
			
			for(LearningActivityTry trie:tries){
				if(log.isDebugEnabled())log.debug("Delete try : " + trie.getLatId()+" - "+trie.getResult());
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(trie);
			}
			
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
		}
		
		if(result.getEndDate() != null){//Solo actualizo el moduleResult y el courseResult si el usuario había terminado la actividad
			ModuleResultLocalServiceUtil.update(la.getModuleId(),user.getUserId());		
			ModuleResult mr = ModuleResultLocalServiceUtil.getByModuleAndUser(la.getModuleId(), user.getUserId());
			if(mr!=null)CourseResultLocalServiceUtil.update(mr);		
		}
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		Message responseMessage = MessageBusUtil.createResponseMessage(message);
		responseMessage.setPayload("RECEIVED_CLEAN_TRIES_USER");
		//System.out.println("Received in ClearningActivityTryUsersLMS");
		try{
			this.la = (LearningActivity)message.get("learningActivity");
			this.user = (User)message.get("user");
			
			if(log.isDebugEnabled())log.debug(" LearningActivity: " + la.getTitle(Locale.getDefault()) + " - " + la.getActId() + " - " +user.getFullName());
			
			process();
			
			MessageBusUtil.sendMessage(responseMessage.getDestinationName(), responseMessage);

		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			e.printStackTrace();
		} 
		
		
	}

}
