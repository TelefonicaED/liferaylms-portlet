<%@page import="java.util.Locale"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@ include file="/init.jsp"%>
<%

java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(themeDisplay.getUserId());
Locale loc = response.getLocale();	
	%>
	
	<%
	
		for(Group theGroup:groups)
		{
			long coursescount=CourseLocalServiceUtil.countByGroupId(theGroup.getGroupId());
			if(coursescount>0&&theGroup.getType()==GroupConstants.TYPE_SITE_PRIVATE)
			{
			%>
			<div class="university">
			
			<%
			if(theGroup.getPublicLayoutSet().getLogo())
			{
				long logoId = theGroup.getPublicLayoutSet().getLogoId();
				%>
				<a href='<%=themeDisplay.getPortalURL() +"/"+ loc.getLanguage() +"/web/"+ theGroup.getFriendlyURL()%>'><img style="float:left" alt="<%=theGroup.getName() %>" src="/image/layout_set_logo?img_id=<%=logoId%>"></a>
				<%
			}
					
			%>
			</div>
<% 	
			}
		}
%>

