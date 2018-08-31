package com.liferay.lms.util.searchcontainer;

import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

public class UserSearchContainer extends SearchContainer<User> {

	static List<String> headerNames = new ArrayList<String>();
	static Map<String, String> orderableHeaders = new HashMap<String, String>();

	static {
		headerNames.add("first-name");
		headerNames.add("last-name");
		headerNames.add("screen-name");
		headerNames.add("email-address");

		orderableHeaders.put("first-name", "first-name");
		orderableHeaders.put("last-name", "last-name");
		orderableHeaders.put("screen-name", "screen-name");
		orderableHeaders.put("email-address", "email-address");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "no-users-were-found";

	public UserSearchContainer(PortletRequest portletRequest, PortletURL iteratorURL) {
		this(portletRequest, DEFAULT_CUR_PARAM, iteratorURL);
	}
	
	public UserSearchContainer(PortletRequest portletRequest, PortletURL iteratorURL, UserDisplayTerms displayTerms) {
		this(portletRequest, DEFAULT_CUR_PARAM, iteratorURL, displayTerms);
	}
	
	public UserSearchContainer(
			PortletRequest portletRequest, String curParam,
			PortletURL iteratorURL) {

			this(portletRequest, curParam, iteratorURL, new UserDisplayTerms(portletRequest));
			
		}

	public UserSearchContainer(
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL, UserDisplayTerms displayTerms) {

		super(
			portletRequest, displayTerms,
			null, curParam, DEFAULT_DELTA,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		PortletConfig portletConfig =
			(PortletConfig)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.USERS_ADMIN)) {
			displayTerms.setStatus(WorkflowConstants.STATUS_APPROVED);
		}


		iteratorURL.setParameter(
			UserDisplayTerms.STATUS, Integer.toString(displayTerms.getStatus()));
		iteratorURL.setParameter(
			UserDisplayTerms.EMAIL_ADDRESS, displayTerms.getEmailAddress());
		iteratorURL.setParameter(
			UserDisplayTerms.FIRST_NAME, displayTerms.getFirstName());
		iteratorURL.setParameter(
			UserDisplayTerms.LAST_NAME, displayTerms.getLastName());
		iteratorURL.setParameter(
			UserDisplayTerms.SCREEN_NAME, displayTerms.getScreenName());	
		iteratorURL.setParameter(
				UserDisplayTerms.TEAM, Long.toString(displayTerms.getTeamId()));
			
		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					PortletKeys.USERS_ADMIN, "users-order-by-col", orderByCol);
				preferences.setValue(
					PortletKeys.USERS_ADMIN, "users-order-by-type",
					orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					PortletKeys.USERS_ADMIN, "users-order-by-col", "last-name");
				orderByType = preferences.getValue(
					PortletKeys.USERS_ADMIN, "users-order-by-type", "asc");
			}

			OrderByComparator orderByComparator =
				UsersAdminUtil.getUserOrderByComparator(
					orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
		
		//Cargamos el displayTerms 
		
	}

	private static Log _log = LogFactoryUtil.getLog(UserSearchContainer.class);

}
