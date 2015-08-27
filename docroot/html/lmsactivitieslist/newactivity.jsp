<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@ include file="/init.jsp"%>

<script type="text/javascript">
<!--
AUI().ready(
    function(A) {
		A.all('img[onblur*=hide]').each(function(img){
			img.after(['blur','mouseout'],function(event){ 
				Liferay.Portal.ToolTip._cached.destroy();
				Liferay.Portal.ToolTip._cached=null;
			 });
		});
    }
);
//-->
</script>

<ul class="activity-list">
<%
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	long[] invisibleTypes = StringUtil.split(PropsUtil.get("lms.learningactivity.invisibles"), StringPool.COMMA,-1L);
	long[] orderedIds = StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getActivities(), StringPool.COMMA, -1L);
	int currentLearningActivityType=0;
	for(LearningActivityType learningActivityType:learningActivityTypeRegistry.getLearningActivityTypesForCreating())
	{
		if(learningActivityType != null && !ArrayUtil.contains(invisibleTypes, learningActivityType.getTypeId())) {
%>	
	<liferay-portlet:renderURL var="newactivityURL">
		<liferay-portlet:param name="editing" value="<%=StringPool.TRUE %>" />
		<liferay-portlet:param name="resId" value="0" />
		<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
		<liferay-portlet:param name="type" value="<%=Long.toString(learningActivityType.getTypeId()) %>" />
	</liferay-portlet:renderURL>
	
	<liferay-util:buffer var="activityMessage">
	    <%=LanguageUtil.get(themeDisplay.getLocale(), learningActivityType.getName()) %>
	    <span class="activity-help">
			<liferay-ui:icon-help message="<%=learningActivityType.getDescription() %>"  />
		</span>
	</liferay-util:buffer>

	<li class="activity_<%=learningActivityType.getTypeId()%>">
		<liferay-ui:icon image="add" label="<%=true%>" message="<%=activityMessage %>" url="<%=newactivityURL%>" cssClass="activity-icon" />
	</li>
<%
		}
	}
%>
</ul>
