<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Properties"%>

<%@ include file="/init.jsp"%>


<c:forEach items="${names}" var="name" >
	<h1 class="description-title">
		<span>${name}</span>
	</h1>
	<h2 class="description-title">
		<c:set var="props" value="${liferay[name]}" />
		<c:if test="${not empty props}">
			<c:if test="${not empty props['liferay-versions']}">
				Liferay: ${props['liferay-versions']}
			</c:if>
			<c:if test="${not empty props['module-incremental-version']}">
				Ver: ${props['module-incremental-version']}
			</c:if>
		</c:if>
	</h2>
	<c:set var="props" value="${properties[name]}" />
	<c:if test="${not empty props}">
		<liferay-ui:message key="database"/>
		<c:if test="${not empty props['build.date']}">
			<div><span class="label"><liferay-ui:message key="build-date"/>:${props['build.date']}</span></div>
			<div><span class="label"><liferay-ui:message key="build-number"/>:${props['build.number']}</span></div>
			<div><span class="label"><liferay-ui:message key="build-upgrade"/>:${props['build.auto.upgrade']}</span></div>
		</c:if>
	</c:if> 
</c:forEach>
