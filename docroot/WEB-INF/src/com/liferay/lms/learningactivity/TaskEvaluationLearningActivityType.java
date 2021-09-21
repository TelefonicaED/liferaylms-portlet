package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.Iterator;

import com.liferay.lms.asset.TaskEvaluationAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskEvaluationLearningActivityType extends BaseLearningActivityType {
	
	private static Log log = LogFactoryUtil.getLog(TaskEvaluationLearningActivityType.class);

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"evaluationtaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	public static long TYPE_ID = 8;
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) throws SystemException, PortalException {
		return new TaskEvaluationAssetRenderer(larn,this);
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	@Override
	public String getName() {
		
		return "learningactivity.evaluation";
	}
	
	@Override
	public String getClassName() {
		return getClass().getName();
	}
	
	
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		return TYPE_ID;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.evaluation.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean canBeLinked(){
		return false;
	}
	
	@Override
	public long getDefaultTries() {
		return 1;
	}
	
	@Override
	public String importExtraContent(LearningActivity newLarn, Long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws PortalException, IOException,
			DocumentException, SystemException {
			
		log.debug("extracontent: " + newLarn.getExtracontent());
		
		Document document = SAXReaderUtil.read(newLarn.getExtracontent());
		
		Element rootElement = document.getRootElement();

		for(Element key:rootElement.elements()){

			if(key.getName().contains("firedDate") || key.getName().contains("publishDate")){
				rootElement.remove(key);
			}
		}

		String extraContent = document.formattedString();
		
		log.debug("extracontent changed: " + extraContent);
		
		newLarn.setExtracontent(extraContent);
		
		LearningActivityLocalServiceUtil.updateLearningActivity(newLarn);


		return extraContent;
	}
	
	@Override
	public void copyActivity(LearningActivity oldActivity, LearningActivity newActivity, ServiceContext serviceContext){
		super.copyActivity(oldActivity, newActivity, serviceContext);
	
		try {
			Document document = SAXReaderUtil.read(newActivity.getExtracontent());
			
			Element rootElement = document.getRootElement();

			for(Element key:rootElement.elements()){

				if(key.getName().contains("firedDate") || key.getName().contains("publishDate")){
					rootElement.remove(key);
				}
			}

			String extraContent = document.formattedString();
			
			log.debug("extracontent changed: " + extraContent);
			
			newActivity.setExtracontent(extraContent);
			
			LearningActivityLocalServiceUtil.updateLearningActivity(newActivity);
		} catch (DocumentException | SystemException | IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void copyActivityFinish(LearningActivity oldActivity, LearningActivity newActivity, ServiceContext serviceContext) throws SystemException{
		super.copyActivityFinish(oldActivity, newActivity, serviceContext);
		try{
			Document document = null;
			Element evaluationXML = null;
			Iterator<Element> activitiesElementItr = null;
			Element activity = null;
			long newValue;
			LearningActivity originActivity = null;
			LearningActivity destinationActivity = null;
			if((newActivity.getExtracontent()!=null)&&(newActivity.getExtracontent().length()!=0)) {	
				//Element activitiesElement = SAXReaderUtil.read(evaluationActivity.getExtracontent()).getRootElement().element("activities");
				document =SAXReaderUtil.read(newActivity.getExtracontent());
				if(log.isDebugEnabled())log.debug(" --- OLD EXTRA CONTENT "+document.formattedString());
				evaluationXML = document.getRootElement();
				if(log.isDebugEnabled())log.debug("--- OLD Evaluation Element "+evaluationXML.asXML());
				Element activitiesElement = evaluationXML.element("activities");
				if(log.isDebugEnabled())log.debug("--- OLD Activities Element "+activitiesElement.asXML());
				if(activitiesElement!=null){
					activitiesElementItr = activitiesElement.elementIterator();
					while(activitiesElementItr.hasNext()) {
						activity =activitiesElementItr.next();
						if(log.isDebugEnabled())log.debug("-- Activity "+ activity);
						if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
							try{
								long oldActId = Long.parseLong(activity.attribute("id").getValue());
								originActivity = LearningActivityLocalServiceUtil.getLearningActivity(oldActId);
								destinationActivity = LearningActivityLocalServiceUtil.getLearningActivityByUuidAndGroupId(originActivity.getUuid(), newActivity.getGroupId());
								if(log.isDebugEnabled())log.debug("Old Value "+Long.parseLong(activity.attribute("id").getValue()));
								newValue = destinationActivity.getActId();
								if(log.isDebugEnabled())log.debug("New Value "+ newValue);
								activity.attribute("id").setValue(String.valueOf(newValue));
							}
							catch(NumberFormatException | PortalException e){
								if(log.isDebugEnabled())e.printStackTrace();
							}
						}
						if(log.isDebugEnabled())log.debug("-- Activity Changed "+ activity.asXML());
					}		
					if(log.isDebugEnabled())log.debug("--- NEW Activities Element "+activitiesElement.asXML());
				}

				if(log.isDebugEnabled()){
					log.debug("--- NEW Evaluation Element "+evaluationXML.asXML());
					log.debug(" --- NEW EXTRA CONTENT "+document.formattedString());
				}
				newActivity.setExtracontent(document.formattedString());
				LearningActivityLocalServiceUtil.updateLearningActivity(newActivity);
				   
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
