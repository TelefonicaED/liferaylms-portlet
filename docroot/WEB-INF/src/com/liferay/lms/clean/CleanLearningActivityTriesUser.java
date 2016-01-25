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

public class CleanLearningActivityTriesUser extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	private LearningActivity la = null;
	private User user = null;
	
	public CleanLearningActivityTriesUser(){
		super();
	}

	@SuppressWarnings("unchecked")
	public void process() throws Exception{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
		
		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class,classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(la.getActId()))
				.add(PropertyFactoryUtil.forName("userId").eq(user.getUserId()));

		//List<LearningActivityResult> results = LearningActivityResultUtil.findWithDynamicQuery(dq);
		List<LearningActivityResult> results = LearningActivityResultLocalServiceUtil.dynamicQuery(dq);
		for(LearningActivityResult result:results){
			if(log.isDebugEnabled())log.debug(" result : " + result.getActId()+", result: "+result.getUserId() +", passed: "+result.getPassed() );

			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(result.getActId(), result.getUserId());
			
			for(LearningActivityTry ltry:tries){
				if(log.isDebugEnabled())log.debug("   try : " + ltry.getLatId()+" - "+ltry.getResult());
				processTry(ltry);
			}
			
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
		}
		
		ModuleResultLocalServiceUtil.update(la.getModuleId(),user.getUserId());
		
		ModuleResult mr = ModuleResultLocalServiceUtil.getByModuleAndUser(la.getModuleId(), user.getUserId());
		
		CourseResultLocalServiceUtil.update(mr);
		

		
		
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		Message responseMessage = MessageBusUtil.createResponseMessage(message);

		responseMessage.setPayload("RECEIVED");

		try{
			this.la = (LearningActivity)message.get("learningActivity");
			this.user = (User)message.get("user");
			User userc = (User)message.get("userc");

			createInstance(la.getCompanyId(),la.getGroupId(),userc.getUserId(), la.getModuleId(), la.getActId());
			
			if(log.isDebugEnabled())log.debug(" LearningActivity: " + la.getTitle(Locale.getDefault()) + " - " + la.getActId() + " - " +user.getFullName());
			
			process();
			MessageBusUtil.sendMessage(responseMessage.getDestinationName(), responseMessage);

		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		} finally {
			endInstance();
		}
		
		
	}

}
