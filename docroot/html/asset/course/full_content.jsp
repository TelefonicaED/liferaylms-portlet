<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<div class="coursedasset">
<%
Course course=(Course)request.getAttribute("course");
AssetEntry asset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
Group generatedGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
Group university=GroupLocalServiceUtil.getGroup(course.getGroupId());
%>
<div class="courselogodiv">
<%
if (Validator.isNotNull(course.getIcon())) {
	long logoId = course.getIcon();
	FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(logoId);
	%>
	<img class="courselogo" src="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK) %>" alt="<%= course.getTitle(themeDisplay.getLocale()) %>"/>
	
	<%
} 
else 
{
	if(generatedGroup.getPublicLayoutSet().getLogo())
	
	{
		long logoId = generatedGroup.getPublicLayoutSet().getLogoId();
		%>
		<img class="courselogo" src="/image/layout_set_logo?img_id=<%=logoId%>" alt="<%= course.getTitle(themeDisplay.getLocale()) %>"/>
		
		<%
	}
	else
	{
		%>
		<liferay-ui:icon
							image='<%= "../file_system/large/course" %>'
							label="<%= false %>"
							message=""	
							alt="<%= course.getTitle(themeDisplay.getLocale()) %>"
						/>
		<%
	}
	}
%>
</div>

<div class="description">
<%=asset.getSummary() %>
</div>
<%

String uniurl="#";
if(university.getPublicLayoutsPageCount()>0&&university.getGroupId()!=themeDisplay.getScopeGroupId())
{
uniurl=themeDisplay.getPortalURL() +"/"+ themeDisplay.getLocale().getLanguage()+"/web"+university.getFriendlyURL();

%>
<a class="university" href="<%=uniurl%>"><%=university.getName() %></a>
<%
}
%>
</div>
