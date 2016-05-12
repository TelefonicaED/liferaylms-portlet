package com.liferay.lms.util.searchterms;

import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletRequest;

public class UserSearchTerms extends UserDisplayTerms {

	public UserSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		emailAddress = DAOParamUtil.getString(portletRequest, EMAIL_ADDRESS);
		firstName = DAOParamUtil.getString(portletRequest, FIRST_NAME);
		lastName = DAOParamUtil.getString(portletRequest, LAST_NAME);
		screenName = DAOParamUtil.getString(portletRequest, SCREEN_NAME);
		status = ParamUtil.getInteger(portletRequest, STATUS, WorkflowConstants.STATUS_APPROVED);
		teamId = ParamUtil.getLong(portletRequest, TEAM,0);		
	}

}