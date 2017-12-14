<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%
Boolean isInitTablet = ParamUtil.getBoolean(request, "isTablet", false);
String cssTabletClass="";
if(isInitTablet){
	cssTabletClass="tablet";
}
%>
<div class="container-activity <%=cssTabletClass%>">