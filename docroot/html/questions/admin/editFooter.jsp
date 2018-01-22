<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	if(request.getAttribute("activity")!=null) {
		LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
%>
	<aui:button onClick="<%= (request.getAttribute(\"backUrl\")!=null)?(String)request.getAttribute(\"backUrl\"):\"\" %>" type="cancel" value="canceledition"/>
<%
	}
%>