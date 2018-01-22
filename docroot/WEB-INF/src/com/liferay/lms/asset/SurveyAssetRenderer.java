package com.liferay.lms.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import com.liferay.lms.learningactivity.SurveyLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletURLFactoryUtil;

public class SurveyAssetRenderer extends LearningActivityBaseAssetRenderer {

	public SurveyAssetRenderer(LearningActivity learningactivity,
			SurveyLearningActivityType surveyLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,surveyLearningActivityType,false);
	}
	
	@Override
	protected PortletURL getURLEditDetails(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,getPortletId(),getLayout().getPlid(),PortletRequest.RENDER_PHASE);	
		portletURL.setParameter("mvcPath", "/html/questions/admin/editquestions.jsp");
		portletURL.setParameter("actionEditingDetails", StringPool.TRUE);
	    portletURL.setParameter("resId",Long.toString( getLearningactivity().getActId()));
	    return portletURL;
	}
}
