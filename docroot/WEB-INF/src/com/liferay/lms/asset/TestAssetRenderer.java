package com.liferay.lms.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletURLFactoryUtil;

public class TestAssetRenderer extends LearningActivityBaseAssetRenderer {

	public TestAssetRenderer(LearningActivity learningactivity, 
			TestLearningActivityType testLearningActivityType)
			throws SystemException, PortalException {
		super(learningactivity,testLearningActivityType,false);
	}
	

	@Override
	protected String getMvcPathView(long userId,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {

		if((getLearningactivity().getTries()==0)||
		   (Validator.isNotNull(LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(getLearningactivity().getActId(), userId)))) {
			return "/html/execactivity/test/view.jsp";
		}
		else {
			return "/html/execactivity/test/preview.jsp";
		}
	}
	
	@Override
	protected PortletURL getURLEditDetails(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,getPortletId(),getLayout().getPlid(),PortletRequest.RENDER_PHASE);	
		portletURL.setParameter("mvcPath", "/html/execactivity/test/admin/editquestions.jsp");
		portletURL.setParameter("actionEditingDetails", StringPool.TRUE);
		portletURL.setParameter("resId",Long.toString( getLearningactivity().getActId()));
	    return portletURL;
	}
}
