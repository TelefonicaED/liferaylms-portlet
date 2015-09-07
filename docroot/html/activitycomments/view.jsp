<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>

<%


if (themeDisplay.isSignedIn())

{
	long moduleId			= ParamUtil.getLong(request,"moduleId",0);
	long actId				= ParamUtil.getLong(request,"actId",0);
	boolean actionEditing	= ParamUtil.getBoolean(request,"actionEditing",false);
	
	if(actId != 0){
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		
			


if(larn!=null&&larn.getCommentsActivated()==true && permissionChecker.hasPermission(larn.getGroupId(),  LearningActivity.class.getName(),larn.getActId(),ActionKeys.VIEW))
{
	
%>



<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" >	
</portlet:actionURL>
<liferay-ui:discussion className="<%= LearningActivity.class.getName() %>"
	classPK="<%= larn.getPrimaryKey() %>"
	formAction="<%= discussionURL %>" 
	formName="fm_1"
	ratingsEnabled="false" 
	redirect="<%= currentURL %>"
	subject="<%= larn.getTitle(themeDisplay.getLocale(), true) %>" 
	userId="<%= larn.getUserId() %>" /> 



<% 
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
}
	else
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
} // no signed 
else
{
renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
