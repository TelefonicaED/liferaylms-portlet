<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module"%>
<%@ page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%


ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Module module=null;
if(row==null)
{
	long moduleId=ParamUtil.getLong(request, "modId");
	module=ModuleLocalServiceUtil.getModule(moduleId);
}
else
{
	module = (Module)row.getObject();
}

long groupId = module.getGroupId();
String name = Module.class.getName();
String primKey = String.valueOf(module.getPrimaryKey());

%>

	<%if(permissionChecker.hasPermission(groupId, name, primKey, ActionKeys.UPDATE))
	{	
	%>
		<liferay-portlet:renderURL var="editmoduleURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<liferay-portlet:param name="popUpAction" value="editmodule" />
			<liferay-portlet:param name="view" value="editmodule" />
			<liferay-portlet:param name="resourcePrimKey" value="<%=primKey %>" />
		</liferay-portlet:renderURL>
	<%
	String taglibEditURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960,modal:true,destroyOnClose: true}, id: 'editModule', title: '" +
	ResourceActionsUtil.getModelResource(locale, Module.class.getName()) + "', uri:'" + HtmlUtil.escapeURL(editmoduleURL) + "'});";
	%>
		<liferay-ui:icon image="edit" url="<%=taglibEditURL%>" />

		<%
	}
	%>
	<%if(permissionChecker.hasPermission(groupId, name, primKey, ActionKeys.DELETE))
	{
		
	%>
		<portlet:actionURL name="deletemodule" var="deletemoduleURL">
			<portlet:param name="resourcePrimKey" value="<%= primKey %>" />
			<portlet:param name="actId" value="0" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%=deletemoduleURL.toString() %>" />
<%
	}
	%>
	<%if(permissionChecker.hasPermission(groupId, name, primKey, ActionKeys.UPDATE))
	{
		
	%>
		<portlet:actionURL name="upmodule" var="upURL">
<portlet:param name="moduleId" value="<%=primKey %>" />
		<portlet:param name="actId" value="0" />
		</portlet:actionURL>
		<portlet:actionURL name="downmodule" var="downURL">
<portlet:param name="moduleId" value="<%=primKey %>" />
		<portlet:param name="actId" value="0" />
	</portlet:actionURL>

<liferay-ui:icon image="bottom"  url="<%=downURL.toString() %>" />
<liferay-ui:icon image="top"  url="<%=upURL.toString() %>" />

	<%
	}
	%>


