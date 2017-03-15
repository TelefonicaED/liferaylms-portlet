<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@ include file="/init.jsp" %>

<%
long groupId 	= ParamUtil.getLong(request, "groupId",-1); 
String cerotonvalue="";
if(groupId > 0){
	cerotonvalue = PrefsPropsUtil.getString(groupId, "ceroton-value");
}
%>

<aui:input type="text" name="ceroton-value" label="ceroton_ct.ceroton-value" value="<%=cerotonvalue %>" />