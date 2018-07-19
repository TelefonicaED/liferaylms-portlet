package com.liferay.lms.util.displayterms;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;

public class UserDisplayTerms extends DisplayTerms{

	public static final String EMAIL_ADDRESS = "emailAddress";

	public static final String FIRST_NAME = "firstName";

	public static final String LAST_NAME = "lastName";

	public static final String SCREEN_NAME = "screenName";

	public static final String STATUS = "status";
	
	public static final String KEYWORDS = "keywords";

	public static final String TEAM = "team";
	
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
	protected boolean showScreenName;
	protected boolean showEmailAddress;
	private long companyId;
	
	private static Log log = LogFactoryUtil.getLog(UserDisplayTerms.class);
	
	public UserDisplayTerms(PortletRequest portletRequest) {		
		this(portletRequest, -1);
	}
	
	public UserDisplayTerms(PortletRequest portletRequest, long groupId) {
		super(portletRequest);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		this.companyId = themeDisplay.getCompanyId();
		if(groupId == -1){
			groupId = themeDisplay.getScopeGroupId();
		}

		status = ParamUtil.getInteger(portletRequest, STATUS, WorkflowConstants.STATUS_APPROVED);

		emailAddress = ParamUtil.getString(portletRequest, EMAIL_ADDRESS);
		firstName = ParamUtil.getString(portletRequest, FIRST_NAME);
		lastName = ParamUtil.getString(portletRequest, LAST_NAME);
		screenName = ParamUtil.getString(portletRequest, SCREEN_NAME);
		keywords = ParamUtil.getString(portletRequest, KEYWORDS);
		teamId = ParamUtil.getLong(portletRequest, TEAM);
		
		try {
			userTeams = TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), groupId);
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
				userTeams=TeamLocalServiceUtil.getGroupTeams(groupId);
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.showEmailAddress = true;
		this.showScreenName = true;
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

	public boolean isShowScreenName() {
		return showScreenName;
	}

	public void setShowScreenName(boolean showScreenName) {
		this.showScreenName = showScreenName;
	}

	public boolean isShowEmailAddress() {
		return showEmailAddress;
	}

	public void setShowEmailAddress(boolean showEmailAddress) {
		this.showEmailAddress = showEmailAddress;
	}
	
	private long[] getTeamIds(){
		long[] teamIds = null;
		if(teamId > 0){
			teamIds = new long[1];
			teamIds[0] = teamId;
		}
		return teamIds;
	}
	
	@Override
	public boolean isAndOperator(){
		if(isAdvancedSearch()){
			return andOperator;
		}else{
			boolean andOperator = false;
			if(Validator.isNull(getKeywords())){
				andOperator = true;
			}
			return andOperator;
		}
	}
	
	public List<User> getStudents(long courseId, int start, int end){
		List<User> listStudents = null;

		if(isAdvancedSearch()){				
			listStudents = CourseLocalServiceUtil.getStudentsFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
													status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
		}else{
			listStudents = CourseLocalServiceUtil.getStudentsFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(), 
					status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
			
		}
		return listStudents;
	}
	
	public int countStudents(long courseId){
		int numStudents = 0;
		
		if(isAdvancedSearch()){			

			numStudents = CourseLocalServiceUtil.countStudentsFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
					status,	getTeamIds(), isAndOperator());
		}else{
			numStudents = CourseLocalServiceUtil.countStudentsFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(),
					status, getTeamIds(), isAndOperator());
		}
		
		return numStudents;
	}
	
	public List<User> getTeachers(long courseId, int start, int end){
		List<User> listTeachers = null;

		if(isAdvancedSearch()){				
			listTeachers = CourseLocalServiceUtil.getTeachersFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
					status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
		}else{
			listTeachers = CourseLocalServiceUtil.getTeachersFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(), 
					status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
			
		}
		return listTeachers;
	}
	
	private OrderByComparator getOrderByComparator(){
		OrderByComparator obc = null;
		try {
			PortletPreferences portalPreferences = PortalPreferencesLocalServiceUtil.getPreferences(companyId, companyId, 1);
			if(Boolean.parseBoolean(portalPreferences.getValue("users.first.last.name", "false"))){
				obc = new UserLastNameComparator(true);
				log.debug("order by last name");
			}else{
				obc = new UserFirstNameComparator(true);
				log.debug("order by first name");
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			obc = new UserLastNameComparator(true);
		}
		return obc;
	}
	
	public int countTeachers(long courseId){
		int numTeachers = 0;
		
		if(isAdvancedSearch()){			

			numTeachers = CourseLocalServiceUtil.countTeachersFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
					status,	getTeamIds(), isAndOperator());
		}else{
			numTeachers = CourseLocalServiceUtil.countTeachersFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(),
					status, getTeamIds(), isAndOperator());
		}
		
		return numTeachers;
	}
	
	public List<User> getEditors(long courseId, int start, int end){
		List<User> listEditors = null;
		
		if(isAdvancedSearch()){				
			listEditors = CourseLocalServiceUtil.getEditorsFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
					status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
		}else{
			listEditors = CourseLocalServiceUtil.getEditorsFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(), 
					status, getTeamIds(), isAndOperator(), start, end, getOrderByComparator());
			
		}
		return listEditors;
	}
	
	public int countEditors(long courseId){
		int numEditors = 0;
		
		if(isAdvancedSearch()){			

			numEditors = CourseLocalServiceUtil.countEditorsFromCourse(courseId, companyId, getScreenName(), getFirstName(), getLastName(), getEmailAddress(), 
					status,	getTeamIds(), isAndOperator());
		}else{
			numEditors = CourseLocalServiceUtil.countEditorsFromCourse(courseId, companyId, getKeywords(), getKeywords(), getKeywords(), getKeywords(),
					status, getTeamIds(), isAndOperator());
		}
		
		return numEditors;
	}

}
