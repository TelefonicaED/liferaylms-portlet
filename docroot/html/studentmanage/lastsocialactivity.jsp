<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portlet.social.model.SocialActivity"%>
<%@page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Image"%>
<%@page import="com.liferay.portal.service.ImageLocalServiceUtil"%>
<%@include file="/init.jsp" %>
<%@page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%@page import="java.text.Format"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil"%>
<%@page import="com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.model.SocialActivityFeedEntry"%>

<%
PortletURL portletURL = renderResponse.createRenderURL();
long userId=ParamUtil.getLong(request, "userId",0);
portletURL.setParameter("jspPage","/html/studentmanage/lastsocialactivity.jsp");
portletURL.setParameter("userId",Long.toString(userId));
User theUser=UserLocalServiceUtil.getUser(userId);

	String returnurl=ParamUtil.getString(request,"returnurl","");
	User usuario=UserLocalServiceUtil.getUser(userId);
	String title = LanguageUtil.get(pageContext,"results") +" "+ usuario.getFullName();
%>

<liferay-ui:header title="<%= title %>" backURL="<%=returnurl %>"></liferay-ui:header>
<liferay-ui:search-container emptyResultsMessage="studentmanagment.none.socialactivity" delta="20" deltaConfigurable="true" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%
List<SocialActivity> activities = SocialActivityLocalServiceUtil.getUserActivities(userId,searchContainer.getStart(), searchContainer.getEnd());
results=activities;
total= SocialActivityLocalServiceUtil.getUserActivitiesCount(userId);

pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);

%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row className="com.liferay.portlet.social.model.SocialActivity" keyProperty="activityId" modelVar="activity">
<%
SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);	
%>
<liferay-ui:search-container-column-text>
<%=dateFormatDateTime.format(activity.getCreateDate()) %>
</liferay-ui:search-container-column-text>
<liferay-ui:search-container-column-text>
<%
				if (activityFeedEntry != null) 
				{
				
				%>
				<%= activityFeedEntry.getTitle() %>
			<div class="activity-body">
				<span class="time"><%= dateFormatDate.format(activity.getCreateDate()) %></span>
		
				<%--= activityFeedEntry.getBody() --%>
			</div>
	
			<%
				}
				else
				{
					if(activity.getClassName().equals(LearningActivity.class.getName()))
					{
						LearningActivity la=LearningActivityLocalServiceUtil.fetchLearningActivity(activity.getClassPK());
						if(la!=null)
						{
						%>
						<%=theUser.getFullName()%> editó la actividad <%=la.getTitle(themeDisplay.getLocale()) %> 
						<%
						}
						else
						{
							%>
							borrada.
							<%
						}
					}
					else
					{
					%>
					<%=activity.getClassName()%>: <%=activity.getClassPK()%>
					<%
					}
				}

%>
</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />

</liferay-ui:search-container>