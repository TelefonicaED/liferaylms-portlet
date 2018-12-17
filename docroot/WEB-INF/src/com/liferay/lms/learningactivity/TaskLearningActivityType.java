package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TaskAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskLearningActivityType extends BaseLearningActivityType 
{

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"execactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	@Override
	public long getDefaultScore() {
		return 50;
	}


	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public boolean isTriesConfigurable() {
		return true;
	}
	@Override
	public long getDefaultTries() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return true;
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new TaskAssetRenderer(learningactivity);
	}


	@Override
	public String getName() {
		
		return "learningactivity.task";
	}

	@Override
	public String getClassName(){
		return getClass().getName();
	}

	@Override
	public long getTypeId() {
		return 1;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.task.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
	
	@Override
	public boolean canBeLinked(){
		return false;
	}
	
}
