<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@page import="com.liferay.lms.asset.LearningActivityBaseAssetRenderer"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%
	String portletId = (String)request.getAttribute(LearningActivityBaseAssetRenderer.TEMPLATE_PORTLET_ID);
	String templateJSP = (String)request.getAttribute(LearningActivityBaseAssetRenderer.TEMPLATE_JSP);
%>
<liferay-util:include page="<%= templateJSP %>" portletId="<%= portletId %>" />
