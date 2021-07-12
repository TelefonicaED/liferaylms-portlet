<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>

<%@ include file="/init-min.jsp" %>

<%	
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean showCourseStartDate = Boolean.parseBoolean(preferences.getValue("showCourseStartDate", "false"));
boolean showCourseEndDate   = Boolean.parseBoolean(preferences.getValue("showCourseEndDate", "false"));
boolean showCourseNumActity = Boolean.parseBoolean(preferences.getValue("showCourseNumActity", "false"));
boolean showCourseNumMale   = Boolean.parseBoolean(preferences.getValue("showCourseNumMale", "false"));
boolean showCourseNumFemale = Boolean.parseBoolean(preferences.getValue("showCourseNumFemale", "false"));
boolean showCourseAvgValoration = Boolean.parseBoolean(preferences.getValue("showCourseAvgValoration", "false"));


boolean showActivityStartDate = Boolean.parseBoolean(preferences.getValue("showActivityStartDate", "true"));
boolean showActivityEndDate = Boolean.parseBoolean(preferences.getValue("showActivityEndDate", "true"));
boolean showActivityTrialsAverage = Boolean.parseBoolean(preferences.getValue("showActivityTrialsAverage", "true"));
boolean showActivityMarksAverage = Boolean.parseBoolean(preferences.getValue("showActivityMarksAverage", "true"));
boolean showActivityPassMark = Boolean.parseBoolean(preferences.getValue("showActivityPassMark", "true"));
boolean showActivityTrialsNumber = Boolean.parseBoolean(preferences.getValue("showActivityTrialsNumber", "true"));
boolean showActivityPrecedence = Boolean.parseBoolean(preferences.getValue("showActivityPrecedence", "true"));
boolean showActivityType = Boolean.parseBoolean(preferences.getValue("showActivityType", "true"));
boolean showActivityObligatory = Boolean.parseBoolean(preferences.getValue("showActivityObligatory", "true"));

%>
<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<h2><liferay-ui:message key="coursestats.show-course-columns"/></h2>
	<aui:input type="checkbox" name="preferences--showCourseStartDate--" label="coursestats.start.date" value="<%=showCourseStartDate %>" checked="<%=showCourseStartDate %>"/>
	<aui:input type="checkbox" name="preferences--showCourseEndDate--" label="coursestats.end.date" value="<%=showCourseEndDate %>" checked="<%=showCourseEndDate %>"/>
	<aui:input type="checkbox" name="preferences--showCourseNumActity--" label="coursestats.activitiescounter" value="<%=showCourseNumActity %>" checked="<%=showCourseNumActity %>"/>
	<aui:input type="checkbox" name="preferences--showCourseNumMale--" label="coursestats.male" value="<%=showCourseNumMale %>" checked="<%=showCourseNumMale %>"/>
	<aui:input type="checkbox" name="preferences--showCourseNumFemale--" label="coursestats.female" value="<%=showCourseNumFemale %>" checked="<%=showCourseNumFemale %>"/>
	<aui:input type="checkbox" name="preferences--showCourseAvgValoration--" label="coursestats.avgvaloration" value="<%=showCourseAvgValoration%>" checked="<%=showCourseAvgValoration%>"/>


	<h2><liferay-ui:message key="coursestats.show-activity-columns"/></h2>
	<aui:input type="checkbox" name="preferences--showActivityStartDate--" label="coursestats.modulestats.activity.start" value="<%=showActivityStartDate %>" checked="<%=showActivityStartDate %>"/>
	<aui:input type="checkbox" name="preferences--showActivityEndDate--" label="coursestats.modulestats.activity.end" value="<%=showActivityEndDate %>" checked="<%=showActivityEndDate %>"/>
	<aui:input type="checkbox" name="preferences--showActivityTrialsAverage--" label="coursestats.modulestats.trials.average" value="<%=showActivityTrialsAverage %>" checked="<%=showActivityTrialsAverage %>"/>
	<aui:input type="checkbox" name="preferences--showActivityMarksAverage--" label="coursestats.modulestats.marks.average" value="<%=showActivityMarksAverage %>" checked="<%=showActivityMarksAverage %>"/>
	<aui:input type="checkbox" name="preferences--showActivityPassMark--" label="coursestats.modulestats.pass.mark" value="<%=showActivityPassMark %>" checked="<%=showActivityPassMark %>"/>
	<aui:input type="checkbox" name="preferences--showActivityTrialsNumber--" label="coursestats.modulestats.trials.numbers" value="<%=showActivityTrialsNumber %>" checked="<%=showActivityTrialsNumber %>"/>
	<aui:input type="checkbox" name="preferences--showActivityPrecedence--" label="coursestats.modulestats.act-precedence" value="<%=showActivityPrecedence %>" checked="<%=showActivityPrecedence %>"/>
	<aui:input type="checkbox" name="preferences--showActivityType--" label="coursestats.modulestats.type" value="<%=showActivityType %>" checked="<%=showActivityType %>"/>
	<aui:input type="checkbox" name="preferences--showActivityObligatory--" label="coursestats.modulestats.obligatory" value="<%=showActivityObligatory %>" checked="<%=showActivityObligatory %>"/>

	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>