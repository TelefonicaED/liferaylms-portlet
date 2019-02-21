package com.liferay.lms.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletURLFactoryUtil;


public class TestAssetRenderer extends LearningActivityBaseAssetRenderer {

	private static Log log = LogFactoryUtil.getLog(TestAssetRenderer.class);
	
	public TestAssetRenderer(LearningActivity learningactivity, 
			TestLearningActivityType testLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,testLearningActivityType,false);
	}
	

	@Override
	protected String getMvcPathView(long userId,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {

		LearningActivity learningActivity = getLearningactivity();
		
		boolean onlyPreview = Boolean.valueOf(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "showOnlyPreview", "false"));
		long learningActivityTries = learningActivity.getTries();
		int userTries = LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(learningActivity.getActId(), userId);
		boolean userHasTried = Validator.isNotNull(userTries) && userTries>0;

		if(log.isDebugEnabled()){
			log.debug("::getMvcPathView:: onlyPreview :: " + onlyPreview);
			log.debug("::getMvcPathView:: learningActivityTries :: " + learningActivityTries);
			log.debug("::getMvcPathView:: userHasTried :: " + userHasTried);
		}

		if((learningActivityTries>0 && !userHasTried) || onlyPreview) {
			if(log.isDebugEnabled())
				log.debug("::getMvcPathView::/html/execactivity/test/preview.jsp");
			return "/html/execactivity/test/preview.jsp";
		}
		else {
			if(log.isDebugEnabled())
				log.debug("::getMvcPathView::/html/execactivity/test/view.jsp");
			return "/html/execactivity/test/view.jsp";
		}
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
