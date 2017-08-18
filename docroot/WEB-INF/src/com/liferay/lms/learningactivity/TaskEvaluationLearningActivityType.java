package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TaskEvaluationAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskEvaluationLearningActivityType extends BaseLearningActivityType {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"evaluationtaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
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
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		return 8;
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
}
