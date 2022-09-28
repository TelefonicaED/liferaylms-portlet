
<%@page import="com.liferay.lms.course.inscriptiontype.InscriptionType"%>
<%@page import="com.liferay.lms.course.inscriptiontype.InscriptionTypeRegistry"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.lms.util.LmsConstant"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeServiceUtil"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.lms.util.LmsConstant"%>
<%@ include file="/init.jsp"%>
<%
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
if(prefs!=null){
	long editorRoleId=prefs.getEditorRole();
	Role editor=RoleLocalServiceUtil.getRole(editorRoleId);
	long inspectorRoleId=prefs.getInspectorRole();
	Role inspector=RoleLocalServiceUtil.getRole(inspectorRoleId);
	long teacherRoleId=prefs.getTeacherRole();
	Role teacher=RoleLocalServiceUtil.getRole(teacherRoleId);
	boolean showCompletedOpenCoursesInProgress = false;
	boolean allowWeightlessMandatoryActivities = false;
	boolean linkResources = false;
	boolean checkExecutionDate = false;
	boolean showOptionTest = false;
	try {
	    showCompletedOpenCoursesInProgress = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_COMPLETED_OPEN_COURSES_INPROGRESS);
	    allowWeightlessMandatoryActivities = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.ALLOW_WEIGHTLESS_MANDATORY_ACTIVITIES); 
	    linkResources = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.RESOURCE_INTERNAL_DOCUMENT_LINKED);
		checkExecutionDate = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.CHECK_EXECUTION_DATE);
		showOptionTest = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.PREFS_SHOW_OPTION_TEST);
	} catch (SystemException e) {
		e.printStackTrace();
	}			
	List<Long> layoutSetTemplateIds = ListUtil.toList(StringUtil.split(prefs.getLmsTemplates(),",",0L));
	List<Long> activityids = ListUtil.toList(StringUtil.split(prefs.getActivities(), ",", 0L));
	List<Long> courseEvalIds = ListUtil.toList(StringUtil.split(prefs.getCourseevals(),",",0L));
	List <Long> calificationTypeIds = ListUtil.toList(StringUtil.split(prefs.getScoretranslators(),",",0L));	
	List<Long> inscriptionTypeIds = ListUtil.toList(StringUtil.split(prefs.getInscriptionTypes(),",",0L));

	%>
	
	<liferay-ui:success message="your-request-completed-successfully" key="ok" />
	<liferay-ui:success message="lms-configuration.upgrade-ok" key="upgrade-ok" />
	<c:if test="${not empty counter}">
		<div class="portlet-msg-success"><liferay-ui:message key="groups-changed" arguments="<%=new String[]{request.getParameter(\"counter\")} %>" /></div>
	</c:if>
	
	
	<liferay-portlet:actionURL name="changeSettings" var="changeSettingsURL"/>
	
	<aui:form action="<%=changeSettingsURL %>" method="POST" role="form">
	<aui:input type="hidden" name="redirect" value="<%= currentURL %>" />
	
	<liferay-ui:header title="lms-activities"/>
	<ul id="lms-sortable-activities-contentor">
	<%
	
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	List<LearningActivityType> learningActivityTypes = learningActivityTypeRegistry.getLearningActivityTypes();
	LearningActivityType [] learningActivityTypesCopy = new LearningActivityType[learningActivityTypes.size()];
	Set<Long> learningActivityTypeIds = new HashSet<Long>();
	Map<Long, Integer> mapaLats = new HashMap<Long, Integer>();
	
	for (LearningActivityType learningActivityType : learningActivityTypes) {
		learningActivityTypeIds.add(learningActivityType.getTypeId());
	}
	
	int index = 0;
	for (Long curActId : activityids)
	{
		if (learningActivityTypeIds.contains(curActId)) {
			mapaLats.put(curActId, index++);
		}
	}
	
	for (LearningActivityType learningActivityType : learningActivityTypes) {
		Integer orderInt = mapaLats.get(learningActivityType.getTypeId());
		if (orderInt != null) {
			learningActivityTypesCopy[orderInt] = learningActivityType;
		} else {
			learningActivityTypesCopy[index++] = learningActivityType;
		}
	}
	
	for (LearningActivityType learningActivityType : learningActivityTypesCopy) {
		%>
		<li class="lms-sortable-activities">
			<aui:input type="checkbox" name="activities" label="<%= learningActivityType.getName() %>" checked="<%= (mapaLats.containsKey(learningActivityType.getTypeId())) %>" value="<%= learningActivityType.getTypeId() %>" />
		</li>
		<%
	}
	%>
	</ul>
	
	<liferay-ui:header title="allowed-site-templates" />
	<aui:field-wrapper>
	<%
	
	for(LayoutSetPrototype layoutsetproto:LayoutSetPrototypeLocalServiceUtil.search(themeDisplay.getCompanyId(),true,0, 1000000,null))
	{
		boolean checked=false;
		if(ArrayUtils.contains(layoutSetTemplateIds.toArray(), layoutsetproto.getLayoutSetPrototypeId()))
		{
			checked=true;
		}
		%>
		
		<aui:input type="checkbox" name="lmsTemplates" 
		label="<%=layoutsetproto.getName(themeDisplay.getLocale())  %>" checked="<%=checked %>" value="<%=layoutsetproto.getLayoutSetPrototypeId()%>" />
		<%
	}
	%>
	</aui:field-wrapper>
	
	<liferay-ui:header title="course-correction-method" />
	<aui:field-wrapper>
	<%
	CourseEvalRegistry courseEvalRegistry = new CourseEvalRegistry();
	for(CourseEval courseEval:courseEvalRegistry.getCourseEvals())
	{
		boolean checked=false;
		String writechecked="false";
		if(courseEvalIds!=null &&courseEvalIds.size()>0 && ArrayUtil.contains(courseEvalIds.toArray(), courseEval.getTypeId()))
		{
			checked=true;
			writechecked="true";
		}
		%>
		
		<aui:input type="checkbox" name="courseEvals" 
		label="<%=courseEval.getName()  %>" checked="<%=checked %>" value="<%=courseEval.getTypeId()%>" />
		<%
	}
	%>
	</aui:field-wrapper>
	
	<liferay-ui:header title="calificationType" />
	<aui:field-wrapper>
	<%
	CalificationTypeRegistry calificationTypeRegistry = new CalificationTypeRegistry();
	for(CalificationType calificationType :calificationTypeRegistry.getCalificationTypes())
	{
		boolean checked=false;
		String writechecked="false";
		if(calificationTypeIds!=null &&calificationTypeIds.size()>0 && ArrayUtils.contains(calificationTypeIds.toArray(), calificationType.getTypeId()))
		{
			checked=true;
			writechecked="true";
		}
		%>
		
		<aui:input type="checkbox" name="calificationTypes" 
		label="<%=LanguageUtil.get(locale, calificationType.getTitle(locale))  %>" checked="<%=checked %>" value="<%=calificationType.getTypeId()%>" />
		<%
	}
	%>
	</aui:field-wrapper>
	
	<liferay-ui:header title="inscription-type" />
	<aui:field-wrapper>
	<%
	InscriptionTypeRegistry inscriptionTypeRegistry = new InscriptionTypeRegistry();
	for(InscriptionType inscriptionType :inscriptionTypeRegistry.getInscriptionTypes()){
		boolean checked=false;
		String writechecked="false";
		if(inscriptionTypeIds!=null &&inscriptionTypeIds.size()>0 && ArrayUtils.contains(inscriptionTypeIds.toArray(), inscriptionType.getTypeId())){
			checked=true;
			writechecked="true";
		}
		%>
		
		<aui:input type="checkbox" name="inscriptionTypes" 
			label="<%=LanguageUtil.get(locale, inscriptionType.getTitle(locale))  %>" checked="<%=checked %>" value="<%=inscriptionType.getTypeId()%>" 
			disabled="<%=!inscriptionType.isActive(themeDisplay.getCompanyId()) %>"/>
		<%
	}
	%>
	</aui:field-wrapper>
	
	<liferay-ui:header title="inscription-configuration" />
	<aui:field-wrapper>
	<%boolean showButtonInscriptionAll = true;
	boolean showButtonUnsubscribeAll = true;
	try {
		showButtonInscriptionAll = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_BUTTON_INSCRIPTION_ALL, true);
		showButtonUnsubscribeAll = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_BUTTON_UNSUBSCRIBE_ALL, true);
	} catch (SystemException e) {
		e.printStackTrace();
	}
	%>
		<aui:input type="checkbox" name="checkExecutionDate" label="config.checkExecutionDate" value="<%=checkExecutionDate %>"/>
		<aui:input type="checkbox" name="showButtonInscriptionAll"	label="lms-prefs.show-button-inscription-all" checked="<%= showButtonInscriptionAll %>" />
		<aui:input type="checkbox" name="showButtonUnsubscribeAll"	label="lms-prefs.show-button-unsubscribe-all" checked="<%= showButtonUnsubscribeAll %>" />
	</aui:field-wrapper>
	
	<liferay-ui:header title="modules-and-activities" />
	<aui:field-wrapper>
	
	<%
		boolean showActivityClassification = true;
		boolean showModuleClassification = false;
		try {
			showActivityClassification = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_ACTIVITY_CLASSIFICATION, true);
			showModuleClassification = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_MODULE_CLASSIFICATION, false);
		} catch (SystemException e) {
			e.printStackTrace();
		}
	%>
		
		<aui:input type="checkbox" name="showModuleClassification"	label="show-module-classification" checked="<%= showModuleClassification %>" />
		<aui:input type="checkbox" name="showActivityClassification" label="show-activity-classification" checked="<%= showActivityClassification %>" />
		<aui:input type="checkbox" name="showHideActivity"	label="show-hide-activity" checked="<%=prefs.getShowHideActivity()%>" value="<%=prefs.getShowHideActivity()%>" />
		<aui:input type="checkbox" name="showOptionTest" label="lms.prefs.test.show-option-test" helpMessage="lms.prefs.test.show-option-test.help-message" checked="<%=showOptionTest %>" value="<%=showOptionTest %>" />
	</aui:field-wrapper>
	
	<liferay-ui:header title="configuration-courses" />
	<aui:field-wrapper>
		<%boolean sendMailToEditors = true;
		boolean sendMailToTutors = true;
		try {
			sendMailToEditors = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SEND_MAIL_TO_EDITORS, true);
			sendMailToTutors = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SEND_MAIL_TO_TUTORS, true);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		boolean accessCoursesExecutionDate = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.PREFS_ACCESS_COURSE_EXECUTION_DATES, false);
		boolean accessQualitySurveyExecutionDate = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.PREFS_ACCESS_QUALITY_SURVEY_EXECUTION_DATES, false);		
		boolean showInspectorRole = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.PREFS_SHOW_INSPECTOR_ROLE, false);
		%>
	
		<aui:input type="checkbox" name="accessCoursesExecutionDate" label="lms-prefs.access-courses-execution-date" 
			checked="<%= accessCoursesExecutionDate %>" helpMessage="lms-prefs.access-courses-execution-date.help-message"/>
	
		<aui:input type="checkbox" name="accessQualitySurveyExecutionDate" label="lms-prefs.access-qualitysurvey-execution-date" 
			checked="<%= accessQualitySurveyExecutionDate %>" helpMessage="lms-prefs.access-qualitysurvey-execution-date.help-message"/>
			
		<aui:input type="checkbox" name="showInspectorRole" label="lms-prefs.show-inspector-role" 
				checked="<%= showInspectorRole %>" helpMessage="lms-prefs.show-inspector-role.help-message"/>
	
		<aui:input type="checkbox" name="viewCoursesFinished"
			label="view-courses-finished" checked="<%=prefs.getViewCoursesFinished()%>" value="<%=prefs.getViewCoursesFinished()%>" 
			onClick="javascript:${renderResponse.namespace}changeViewCoursesFinished()"/>
			<div class='<%=prefs.getViewCoursesFinished() ? "" : "aui-helper-hidden" %>' id='<%=renderResponse.getNamespace() + "viewCourseFinishedTypeDiv" %>'>
				<aui:input id="viewCoursesFinishedAll" name="viewCourseFinishedType" value="<%=LmsConstant.VIEW_COURSE_FINISHED_TYPE_ALL %>"
				 	type="radio" label="view-courses-finished.all" checked="<%=LmsConstant.VIEW_COURSE_FINISHED_TYPE_ALL == prefs.getViewCoursesFinishedType() %>"/>
				<aui:input id="viewCoursesFinishedPassed" name="viewCourseFinishedType" value="<%=LmsConstant.VIEW_COURSE_FINISHED_TYPE_PASSED %>" 
					type="radio" label="view-courses-finished.passed" checked="<%=LmsConstant.VIEW_COURSE_FINISHED_TYPE_PASSED == prefs.getViewCoursesFinishedType() %>"/>
			</div>
		<aui:input type="checkbox" name="showCompletedOpenCoursesInProgress"
			label="lms-prefs.show-completed-openCourses-inProgress" checked="<%= showCompletedOpenCoursesInProgress %>" />
		<aui:input type="checkbox" name="allowWeightlessMandatoryActivities"
		 	label="lms-prefs.allow-weightless-mandatory-activities" checked="<%= allowWeightlessMandatoryActivities %>" 
		 	helpMessage="lms-prefs.allow-weightless-mandatory-activities.help"/>
		<aui:input type="checkbox" name="linkResources"
			label="link-internal-resources" checked="<%=linkResources%>" value="<%=linkResources%>" />
		<aui:input type="checkbox" name="sendMailToEditors"	label="lms-prefs.send-mail-to-editors" checked="<%= sendMailToEditors %>" />
		<aui:input type="checkbox" name="sendMailToTutors"	label="lms-prefs.send-mail-to-tutors" checked="<%= sendMailToTutors %>" />
		
	</aui:field-wrapper>

	<aui:field-wrapper>
		<aui:button type="submit" value="save" />
	</aui:field-wrapper>
	
	</aui:form>
	<%
}
%>


<script type="text/javascript">
	AUI().ready(
	    'aui-sortable',
	   function(A) {
	        window.<portlet:namespace/>lmsActivitiesSortable = new A.Sortable(
	            {
	                nodes: '.lms-sortable-activities'
	            }
	        );
	    }
	);
	function <portlet:namespace/>changeViewCoursesFinished(){
		var checked = document.getElementById('<portlet:namespace/>viewCourseFinishedType').value;
		if(checked == 'true'){
			document.getElementById('<portlet:namespace />viewCourseFinishedTypeDiv').className = "";
		}else{
			document.getElementById('<portlet:namespace />viewCourseFinishedTypeDiv').className = "aui-helper-hidden";
		}
	}
</script>