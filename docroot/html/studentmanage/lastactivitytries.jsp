<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.AuditEntry"%>
<%@page import="com.liferay.lms.service.AuditEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@include file="/init.jsp" %>
<%
PortletURL portletURL = renderResponse.createRenderURL();
long userId=ParamUtil.getLong(request, "userId",0);
portletURL.setParameter("jspPage","/html/studentmanage/lastactivitytries.jsp");
portletURL.setParameter("userId",Long.toString(userId));


	String returnurl=ParamUtil.getString(request,"returnurl","");
	User usuario=UserLocalServiceUtil.getUser(userId);
	String title = LanguageUtil.get(pageContext,"results") +" "+ usuario.getFullName();
%>

<liferay-ui:header title="<%= title %>" backURL="<%=returnurl %>"></liferay-ui:header>
<liferay-ui:search-container emptyResultsMessage="studentmanagment.none.audit" delta="20" deltaConfigurable="true" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%

List<AuditEntry> activities =AuditEntryLocalServiceUtil.search(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivityTry.class.getName(), 0, userId, null, null, searchContainer.getStart(), searchContainer.getEnd());
results=activities;
total=(int) AuditEntryLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivityTry.class.getName(), 0, userId, null, null,0,0);

pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.AuditEntry" keyProperty="auditId" modelVar="auditEntry">
			
			<liferay-ui:search-container-column-text title="date">
			<%=auditEntry.getAuditDate() %>
			</liferay-ui:search-container-column-text>
					<liferay-ui:search-container-column-text title="activity">
					<%
					LearningActivityTry lat=LearningActivityTryLocalServiceUtil.fetchLearningActivityTry(auditEntry.getClassPK());
					if(lat!=null)
					{
						LearningActivity larn=LearningActivityLocalServiceUtil.fetchLearningActivity(lat.getActId());
						if(larn!=null)
						{
					%>
			<%=larn.getTitle(themeDisplay.getLocale()) %>
					<%
						}
					}
					%>
			</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />

</liferay-ui:search-container>