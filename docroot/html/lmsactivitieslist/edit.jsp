
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@include file="/init.jsp" %>

<portlet:actionURL name="saveEditPreference" var="actionURL"></portlet:actionURL>
<form action="<%=actionURL %>" method="POST">

	<div><span><liferay-ui:message key="com.liferay.lms.activitiesList.edit.label"/></span></div>
	<%
	
		String portletId = PortalUtil.getPortletId(request);
		String layoutId = String.valueOf(themeDisplay.getPlid());
		String preferenceLmsActivitiesList = PrefsPropsUtil.getString(company.getCompanyId(),layoutId+"_"+portletId+"_lmsActivitiesList",StringPool.BLANK);
		List<String> modulesList = new ArrayList<String>();
		modulesList = Arrays.asList(preferenceLmsActivitiesList.split(","));
		
		List<Module> modules =  ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		for (Module module: modules){
			
			boolean moduleSelected = modulesList.contains(String.valueOf(module.getModuleId()));
	%>
		<div><aui:input checked="<%=moduleSelected%>" type="checkbox" name="modules" label="<%=module.getTitle(themeDisplay.getLocale())%>" value="<%=module.getModuleId()%>"/></div>
	<%
		}
	%>
	<aui:button type="submit" value="save"/>
</form>