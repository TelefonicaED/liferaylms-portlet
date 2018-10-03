<%@page import="com.liferay.lms.course.inscriptiontype.UserDaysInscriptionType"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@ include file="/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId",-1); 
String days="";
if(groupId > 0){
	days = PrefsPropsUtil.getString(groupId, "inscription-days");
}
%>

<aui:input type="text" name="inscriptionDays" label="inscription.days" value="<%=days %>" >
	<aui:validator name="number"/>
	<aui:validator name="min">'0'</aui:validator>
</aui:input>