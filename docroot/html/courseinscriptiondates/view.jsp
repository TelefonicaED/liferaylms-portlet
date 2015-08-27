<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%

Group grupo = GroupLocalServiceUtil.fetchGroup(themeDisplay.getScopeGroupId());
Course curso = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(grupo.getType()==GroupConstants.TYPE_SITE_OPEN && curso != null){
	Date today = new Date();
	Date inscriptionStartDate = curso.getStartDate();
	Date inscriptionEndDate = curso.getEndDate();
	if(today.before(inscriptionStartDate)){
		//mostrar fecha de inicio
%>
		<div class="inscription-dates"><liferay-ui:message key="start-inscription-date"/>: <%=dateFormatDate.format(inscriptionStartDate) %></div>
<%
	}else if(inscriptionStartDate.before(today) && today.before(inscriptionEndDate)){
		//mostrar fecha de fin
%>
		<div class="inscription-dates"><liferay-ui:message key="end-inscription-date"/>: <%=dateFormatDate.format(inscriptionEndDate) %></div>
<%
	}else{
		//mostrar mensaje
%>
		<div class="inscription-dates"><liferay-ui:message key="inscripcion.date.pass"/></div>
<%
	}
}else{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}

%>