<%@page import="com.liferay.portal.model.MembershipRequest"%>
<%@include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
MembershipRequest msr = (MembershipRequest)row.getObject();
%>

<portlet:actionURL name="enroll" var="enrollURL" >
	<portlet:param name="msrId" value="<%= String.valueOf(msr.getMembershipRequestId())%>" />
</portlet:actionURL>
<portlet:actionURL name="denied" var="deniedURL" >
	<portlet:param name="msrId" value="<%= String.valueOf(msr.getMembershipRequestId()) %>" />
</portlet:actionURL>
<liferay-ui:icon-menu>
	<liferay-ui:icon image="add_user" message="acept" url="${enrollURL}" />
	<liferay-ui:icon image="close" message="deny" url="${deniedURL}" />
</liferay-ui:icon-menu>