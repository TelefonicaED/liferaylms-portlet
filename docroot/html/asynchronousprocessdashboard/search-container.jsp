<%@page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil"%>
<%@page import="java.text.Format"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@include file="/init-min.jsp" %>
<%
Format dateFormat = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>
		
	<liferay-ui:search-container searchContainer="${searchContainer}"
		iteratorURL="${searchContainer.iteratorURL}">
		<liferay-ui:search-container-results total="${searchContainer.total }"
			results="${searchContainer.results }" />
	
		<liferay-ui:search-container-row
			className="com.liferay.lms.model.AsynchronousProcessAudit"
			keyProperty="asynchronousProcessAuditId" modelVar="asynchronousProccessAudit">
	
		<liferay-ui:search-container-column-text name="type">
			<c:if test="${asynchronousProccessAudit.status eq 0}">
				<span class="status-not-started"></span>
			</c:if>
			
			<c:if test="${asynchronousProccessAudit.status eq 1}">
				<span class="status-started"></span>
			</c:if>
			<c:if test="${asynchronousProccessAudit.status eq 2}">
				<span class="status-ok"></span>
			</c:if>
			<c:if test="${asynchronousProccessAudit.status eq 3}">
				<span class="status-error"></span>
			</c:if>
			<liferay-ui:message key="${asynchronousProccessAudit.type}"/>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="class">
			<liferay-ui:message key="${asynchronousProccessAudit.className}"/> 
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="id">
			${asynchronousProccessAudit.classPK}
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="startDate">
			<c:if test="${not empty asynchronousProccessAudit.createDate}">
				<%=dateFormat.format(asynchronousProccessAudit.getCreateDate()) %> 
			</c:if>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="endDate">
			<c:if test="${not empty asynchronousProccessAudit.endDate}">
				<%=dateFormat.format(asynchronousProccessAudit.getEndDate())%>
			</c:if>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="message">
			<c:choose>
				<c:when test="${asynchronousProccessAudit.status eq 0}">
					<liferay-ui:message key="asynchronous-process-audit.process-not-started"/>
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="${asynchronousProccessAudit.statusMessage}"/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
			<c:if test="${showExtraContent}">
				<liferay-ui:search-container-column-text align="center" name="extraContent" >
					<c:if test="${not empty asynchronousProccessAudit.extraContent}">
						<jsp:useBean id="jsonFactoryUtil" class="com.liferay.portal.kernel.json.JSONFactoryUtil"/>
									<c:set var="obj" value="${jsonFactoryUtil.createJSONObject(asynchronousProccessAudit.extraContent)}"/>
										<liferay-util:include portletId="${obj.getString(\"portletId\")}" page="${obj.getString(\"pathControl\")}"  >
											  <liferay-util:param name="namespace" value="${obj.getString(\"portletId\")}" />
											  <liferay-util:param name="path" value="${obj.getString(\"url\")}" />
											  <liferay-util:param name="contentType" value="${obj.getString(\"contentType\")}" />
											  <liferay-util:param name="id" value="${asynchronousProccessAudit.asynchronousProcessAuditId}" />
										</liferay-util:include> 	  
									
							</c:if>
						</liferay-ui:search-container-column-text>
					</c:if>
		
		
			</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>