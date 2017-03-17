<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="java.util.Properties"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portlet.PortletURLUtil"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.theme.PortletDisplay"%>
<%@page import="javax.portlet.PortletMode"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%

List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
long searchGroupId=themeDisplay.getScopeGroupId();
if(course!=null)
{
	searchGroupId=course.getGroupId();
}

Properties props = PropsUtil.getProperties("lms.scorm.assettypes", true);
List<String> allowedAssetTypes = new ArrayList<String>();
for (Object key:props.keySet()) {
	allowedAssetTypes.addAll(ListUtil.toList(props.get(key).toString().split(",")));
}

String title =  ParamUtil.getString(request, "title", "");
String description = ParamUtil.getString(request, "description","");
String passPuntuation = ParamUtil.getString(request,"passpuntuation","0");


long resId = ParamUtil.getLong(request,"resId",0);
long resModuleId = ParamUtil.getLong(request,"resModuleId",0);
String message = "new-activity-scorm";
if(resId > 0){
	message = LearningActivityLocalServiceUtil.fetchLearningActivity(resId).getTitle(themeDisplay.getLocale());
}
%>

<liferay-portlet:renderURL var="backURL" >
	<liferay-portlet:param name="mvcPath" value="/html/editactivity/editactivity.jsp" />
	<liferay-portlet:param name="resId" value="<%=String.valueOf(resId) %>" />
	<liferay-portlet:param name="resModuleId" value="<%=String.valueOf(resModuleId) %>" />
	<liferay-portlet:param name="type" value="9" />
	<liferay-portlet:param name="title" value="<%=String.valueOf(title) %>" />
	<liferay-portlet:param name="description" value="<%=String.valueOf(description) %>" />
	<liferay-portlet:param name="passpuntuation" value="<%=String.valueOf(passPuntuation) %>" />
</liferay-portlet:renderURL>


<liferay-ui:header 
	title="<%=message %>" 
	backURL="<%=backURL  %>"
	localizeTitle="<%=resId <= 0 %>"
/>





<liferay-portlet:renderURL var="selectResource">
	<liferay-portlet:param name="jspPage" value="/html/scormactivity/admin/searchresults.jsp"/>
	<liferay-portlet:param name="title" value="<%=String.valueOf(title) %>" />
	<liferay-portlet:param name="description" value="<%=String.valueOf(description) %>" />
	<liferay-portlet:param name="passpuntuation" value="<%=String.valueOf(passPuntuation) %>" />
</liferay-portlet:renderURL>
<aui:form name="ressearch" action="<%=selectResource %>" method="POST">

<aui:select name="className" label="asset-type">
<% 

for(String className:allowedAssetTypes)
{	
	String assettypename=LanguageUtil.get(pageContext, "model.resource." + className);	
	%>
	<aui:option value="<%=className%>" label="<%=assettypename%>"></aui:option>
	<%
	
}
%>
</aui:select>

<aui:input type="hidden" name="resId" value="<%=resId%>"/>
<aui:input type="hidden" name="resModuleId" value="<%=resModuleId %>"/>

<aui:input name="keywords" size="20" type="text"/>

<aui:input name="groupId" type="hidden" value="<%=Long.toString(searchGroupId) %>" />

<aui:field-wrapper label="categories" helpMessage="scormactivity.categories.helpmessage">
	<%@ include file="/html/scormactivity/admin/catselector.jspf" %>
</aui:field-wrapper>

<liferay-ui:panel id="panel_tags" title="tags" collapsible="true" defaultState="closed">
	<liferay-ui:asset-tags-selector ></liferay-ui:asset-tags-selector>
</liferay-ui:panel>

<aui:button-row>
	<aui:button type="submit" value="search" />
</aui:button-row>
</aui:form>
