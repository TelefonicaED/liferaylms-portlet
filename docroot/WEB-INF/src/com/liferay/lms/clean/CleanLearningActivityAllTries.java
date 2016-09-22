package com.liferay.lms.clean;

import java.util.List;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanLearningActivityAllTries implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityAllTries.class);
	private LearningActivity la = null;
	private User user = null;
	
	public CleanLearningActivityAllTries(){
		super();
	}

	public void process() throws Exception{
		if(log.isDebugEnabled())log.debug("CleanLearningActivityAllTries process. ActId:"+la.getActId());
		
		AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityTry.class.getName(), la.getActId(), user.getUserId(), AuditConstants.DELETE, "Borrardo masivo de intentos");

		List<LearningActivityResult> results = LearningActivityResultLocalServiceUtil.getByActId(la.getActId());
		if(log.isDebugEnabled())log.debug("results LearningActivityResultLocalServiceUtil.dynamicQuery "+results.size());
		for(LearningActivityResult result:results){
			if(log.isDebugEnabled())log.debug(" result : " + result.getActId()+", result: "+result.getUserId() +", passed: "+result.getPassed() );
			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(result.getActId(), result.getUserId());
			
			for(LearningActivityTry trie:tries){
				if(log.isDebugEnabled())log.debug("Delete try : " + trie.getLatId()+" - "+trie.getResult());
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(trie);
			}
			
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
			
		}
		
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		if(log.isDebugEnabled())log.debug("CleanLearningActivityAllTries receive");
		Message responseMessage = MessageBusUtil.createResponseMessage(message);
		responseMessage.setPayload("RECEIVED");
		try{
			this.la = (LearningActivity)message.get("learningActivity");	
			this.user = (User)message.get("userc");
			process();
			MessageBusUtil.sendMessage(responseMessage.getDestinationName(), responseMessage);
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			
			e.printStackTrace();
		}
	}

}
