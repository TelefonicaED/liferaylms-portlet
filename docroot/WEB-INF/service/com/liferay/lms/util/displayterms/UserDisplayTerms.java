package com.liferay.lms.util.displayterms;

import java.util.List;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class UserDisplayTerms extends DisplayTerms{

	public static final String EMAIL_ADDRESS = "emailAddress";

	public static final String FIRST_NAME = "firstName";

	public static final String LAST_NAME = "lastName";

	public static final String SCREEN_NAME = "screenName";

	public static final String STATUS = "status";
	
	public static final String KEYWORDS = "keywords";

	public static final String TEAM = "team";
	
	public UserDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String statusString = ParamUtil.getString(portletRequest, STATUS);

		if (Validator.isNotNull(statusString)) {
			status = GetterUtil.getInteger(statusString);
		}

		emailAddress = ParamUtil.getString(portletRequest, EMAIL_ADDRESS);
		firstName = ParamUtil.getString(portletRequest, FIRST_NAME);
		lastName = ParamUtil.getString(portletRequest, LAST_NAME);
		screenName = ParamUtil.getString(portletRequest, SCREEN_NAME);
		keywords = ParamUtil.getString(portletRequest, KEYWORDS);
		teamId = ParamUtil.getLong(portletRequest, TEAM);
		
		try {
			userTeams = TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if(getTeamId()>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), getTeamId())||userTeams.size()==0)){		
				team=TeamLocalServiceUtil.fetchTeam(getTeamId());	
			}else{
				if(userTeams!=null&& userTeams.size()>0){
					team=userTeams.get(0);	
					setTeamId(team.getTeamId());
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userTeams == null || userTeams.size()==0){
			hasNullTeam=true;
			try {
				userTeams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public String getKeywords() {
		return keywords;
	}

	
	public long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(long teamId){
		this.teamId = teamId;
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

	public static String getKEYWORDS(){
		return KEYWORDS;
	}

	public boolean isHasNullTeam() {
		return hasNullTeam;
	}

	public void setHasNullTeam(boolean hasNullTeam) {
		this.hasNullTeam = hasNullTeam;
	}
	
	public List<Team> getUserTeams(){
		return userTeams;
	}
	
	public void setUserTeams(List<Team> userTeams){
		this.userTeams = userTeams;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public void setTeam(Team team){
		this.team = team;
	}

	protected String emailAddress;
	protected String firstName;
	protected String lastName;
	protected String screenName;
	protected String keywords;
	protected int status;
	protected long teamId;
	protected boolean hasNullTeam;
	protected List<Team> userTeams;
	protected Team team;
}
