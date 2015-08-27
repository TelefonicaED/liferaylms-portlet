
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>
<%
			
if(windowState.equals(LiferayWindowState.POP_UP))
{
	%>
	<script>
	Liferay.Util.getOpener().closePopup('editModule');
	Liferay.Util.getOpener().refreshPortlet();
	</script>
    <%
	
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>