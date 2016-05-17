package com.liferay.lms.util.displayterms;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

public class UserDisplayTerms extends DisplayTerms{

	public static final String EMAIL_ADDRESS = "emailAddress";

	public static final String FIRST_NAME = "firstName";

	public static final String LAST_NAME = "lastName";

	public static final String SCREEN_NAME = "screenName";

	public static final String STATUS = "status";

	public static final String TEAM = "team";
	
	public UserDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		String statusString = ParamUtil.getString(portletRequest, STATUS);

		if (Validator.isNotNull(statusString)) {
			status = GetterUtil.getInteger(statusString);
		}

		emailAddress = ParamUtil.getString(portletRequest, EMAIL_ADDRESS);
		firstName = ParamUtil.getString(portletRequest, FIRST_NAME);
		lastName = ParamUtil.getString(portletRequest, LAST_NAME);
		screenName = ParamUtil.getString(portletRequest, SCREEN_NAME);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getScreenName() {
		return screenName;
	}

	public int getStatus() {
		return status;
	}

	public long getTeamId() {
		return teamId;
	}
	
	public boolean isActive() {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public static String getEMAIL_ADDRESS(){
		return EMAIL_ADDRESS;
	}
	
	public static String getFIRST_NAME(){
		return FIRST_NAME;
	}
	
	public static String getLAST_NAME(){
		return LAST_NAME;
	}
	
	public static String getSCREEN_NAME(){
		return SCREEN_NAME;
	}
	
	public static String getSTATUS(){
		return STATUS;
	}

	public static String getTEAM(){
		return TEAM;
	}


	protected String emailAddress;
	protected String firstName;
	protected String lastName;
	protected String screenName;
	protected int status;
	protected long teamId;
}
