package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class EvaluationPortlet extends MVCPortlet {
	
	public void gotoEvaluation(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {
		ThemeIdEvent themeIdEvent = new ThemeIdEvent();
		themeIdEvent.setModuleId(ThemeIdEvent.EVALUATION_THEME_ID);
		themeIdEvent.setThemeId(0);		
		actionResponse.setEvent(new QName("http://www.wemooc.com/" , "themeId"), themeIdEvent);
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Course course=null;
		try{
			course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());			
		}
		catch(SystemException e){}

		if((course!=null)&&(course.getCourseEvalId()==1)) {
			super.doView(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}


}

