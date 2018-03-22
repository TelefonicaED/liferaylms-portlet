package com.liferay.lms.clean;

import java.util.Date;
import java.util.List;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.ActivityTriesDeleted;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
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

public class CleanLearningActivityTriesAllUsers implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesAllUsers.class);
	private LearningActivity la = null;
	private User user = null;
	private ActivityTriesDeleted activityTriesDeleted = null;
	private boolean onlyNotPassed = false;
	private AsynchronousProcessAudit process = null;
	private String statusMessage ="";
	private boolean error= false;
	
	
	public CleanLearningActivityTriesAllUsers(){
		super();
	}

	public void process() throws Exception{
		if(log.isDebugEnabled())log.debug("CleanLearningActivityTries process. ActId:"+la.getActId());
		
		
		List<LearningActivityResult> results = null;
		if(!onlyNotPassed){
			AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityTry.class.getName(), la.getActId(), user.getUserId(), AuditConstants.DELETE, "Borrardo masivo de intentos");

			results = LearningActivityResultLocalServiceUtil.getByActId(la.getActId());
		}else{
			AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityTry.class.getName(), la.getActId(), user.getUserId(), AuditConstants.DELETE, "Borrardo masivo de intentos no superados");

			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
			DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class,classLoader)
					.add(PropertyFactoryUtil.forName("actId").eq(la.getActId()))
					.add(PropertyFactoryUtil.forName("passed").ne(true));
			results = LearningActivityResultLocalServiceUtil.dynamicQuery(dq);
		}
		if(log.isDebugEnabled())log.debug("results LearningActivityResultLocalServiceUtil.dynamicQuery "+results.size());
		activityTriesDeleted.setStatus(LmsConstant.STATUS_IN_PROGRESS);
		ActivityTriesDeletedLocalServiceUtil.updateActivityTriesDeleted(activityTriesDeleted);
		
		//Cambiamos el estado de la tabla a en progreso
		
		for(LearningActivityResult result:results){
			if(log.isDebugEnabled())log.debug(" result : " + result.getActId()+", result: "+result.getUserId() +", passed: "+result.getPassed() );
			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(result.getActId(), result.getUserId());
			
			for(LearningActivityTry trie:tries){
				if(log.isDebugEnabled())log.debug("Delete try : " + trie.getLatId()+" - "+trie.getResult());
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(trie);
			}
			
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
			
		}
		//Cambiamos el estado a finalizado
		ActivityTriesDeletedLocalServiceUtil.updateFinish(activityTriesDeleted);
		
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		if(log.isDebugEnabled())log.debug("CleanLearningActivityTries receive");
		Message responseMessage = MessageBusUtil.createResponseMessage(message);
		responseMessage.setPayload("RECEIVED");
		try{
			this.la = (LearningActivity)message.get("learningActivity");	
			this.user = (User)message.get("userc");
			long processId = message.getLong("asynchronousProcessAuditId");
			
			//Rellenamos el proceso de auditoria
			process = AsynchronousProcessAuditLocalServiceUtil.fetchAsynchronousProcessAudit(processId);
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, null, LmsConstant.STATUS_IN_PROGRESS, "");
			process.setClassPK(la.getActId());
			process = AsynchronousProcessAuditLocalServiceUtil.updateAsynchronousProcessAudit(process);
			statusMessage ="";
			error = false;
			
			
			activityTriesDeleted = (ActivityTriesDeleted)message.get("activityTriesDeleted");
			onlyNotPassed = (Boolean)message.get("onlyNotPassed");
			process();
			MessageBusUtil.sendMessage(responseMessage.getDestinationName(), responseMessage);
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			error=true;
			statusMessage += e.getMessage() + "\n";
			e.printStackTrace();
		}
		
		Date endDate = new Date();
		if(!error){
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_FINISH, "asynchronous-proccess-audit.status-ok");
		}else{
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_ERROR, statusMessage);
		}
		
	}
	
	

}
