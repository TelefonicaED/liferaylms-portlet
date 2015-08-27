
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@include file="/init.jsp"%>

<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.model.Group"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>


<portlet:renderURL var="recargarURL">
</portlet:renderURL>

<%
/* Sacamos de portal-ext.properties las páginas que no se desean listar */
String hideLayouts = PrefsPropsUtil.getString("hidden.layouts");
List<String> hiddenLayouts = new ArrayList<String>();
if(hideLayouts != null && !hideLayouts.isEmpty()){
	
	StringTokenizer st = new StringTokenizer(hideLayouts, ",");
	while(st.hasMoreElements()){
		
		hiddenLayouts.add(st.nextToken());
	}
	
}else{
	
	/* Al menos tiene que ocultar esta página */
	hiddenLayouts.add("/reto");
}

String layoutidr=ParamUtil.getString(request, "layoutid","");
if(layoutidr.length()>0)
{
%>
	<script>
	location.href="<%=recargarURL%>";
	</script>
<%
}
else
{
	Layout actual=themeDisplay.getLayout();	
	Layout padre=actual;
	
	if(actual.getParentPlid()!=0)
	{
	 	padre=LayoutLocalServiceUtil.getLayout(actual.getParentPlid());
	}
	Group grupo=themeDisplay.getScopeGroup();
	List<Layout> loslayouts=LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),actual.isPrivateLayout());
	
	if(loslayouts.size()>2) // Debe de haber al menos 2 páginas porque la primera y la última (la del tutor/configuración donde está este portlet) no se pueden gestionar con este portlet
	{
%>
		<table width="100%" cellspacing="2">
		<tbody>
			<tr><td></td></tr>
<%

		Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		Role userRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.USER);
		for(int i=1;i<(loslayouts.size()-1);i++) // Evitamos la primera, que no es optativa, y la última que es la del tutor y la que contiene este portlet de administración 
		{
			Layout ellayout=loslayouts.get(i);	
			if(!hiddenLayouts.contains(ellayout.getFriendlyURL()))
			{
				boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), Layout.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(ellayout.getPlid()),new long[] {siteMemberRole.getRoleId(), userRole.getRoleId()}, ActionKeys.VIEW);

							
	%>
			<tr><td width="95%"><b><%=ellayout.getHTMLTitle(themeDisplay.getLocale()) %></b></td>
			<td>
			<portlet:actionURL name="changeLayout" var="changeLayoutURL">
				<portlet:param name="layoutid" value="<%=Long.toString(ellayout.getPlid()) %>"></portlet:param>
			</portlet:actionURL>
	
	<%
				if(!visible)
				{
	%>
					<liferay-ui:icon  image="deactivate" label="add" url="<%=changeLayoutURL %>" message="<%=LanguageUtil.get(pageContext,\"test.activate\")%>" />
	<%
				}
				else
				{
	%>
					<liferay-ui:icon image="activate" label="remove" url="<%=changeLayoutURL %>" message="<%=LanguageUtil.get(pageContext,\"test.desactivate\")%>" />
	<%
				}
	%>
			</td>
			</tr>
	<%
			}
		}
%>
		</tbody>
		</table>
<%
	}
}
%>

