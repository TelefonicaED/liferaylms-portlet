<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>

<%@ include file="/init.jsp"%>
<%
String timeTodoRefresh = (String) renderRequest.getAttribute("refreshPageEachXSeg");
%>
<script>
var timeTodoRefreshInseg = <%=timeTodoRefresh%> * 1000;
if(timeTodoRefreshInseg!=0){
	setTimeout(function() { refreshSearch(timeTodoRefreshInseg); }, timeTodoRefreshInseg);
}
function refreshSearch( timeTodoRefreshInseg ) {
   if(timeTodoRefreshInseg > 10000){
	   $('#<portlet:namespace />search').trigger("submit");
	   setTimeout(function() { refreshSearch( timeTodoRefreshInseg); }, timeTodoRefreshInseg);
   }else{
	   $('#<portlet:namespace />search').trigger("submit");
	   setTimeout(function() { refreshSearch( 10000); }, 10000);
   }
  
}
</script>
<portlet:renderURL var="searchURL">
</portlet:renderURL>

<aui:form action="${searchURL}" method="post" name="search" role="search">
	<aui:layout>
		<aui:column columnWidth="20">
			<aui:select name="className" label="type" cssClass="type-selector">
				<aui:option label="" value="" selected="${empty className}" />
				<c:forEach items="${classnames}" var="classNameValue">
					<aui:option label="${classNameValue}" value="${classNameValue}" selected="${classNameValue eq className}" />
				</c:forEach>
			</aui:select>
		</aui:column>
		<aui:column columnWidth="33">
		<aui:field-wrapper label="start-date" >
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}" 
				yearRangeStart="${defaultStartYear}" dayParam="startDay"
				monthParam="startMonth" yearParam="startYear" yearNullable="true"
				dayNullable="true" monthNullable="true" yearValue="${startYear}"
				monthValue="${startMonth}" dayValue="${startDay}"></liferay-ui:input-date>
		</aui:field-wrapper>
		</aui:column>
		<aui:column columnWidth="33">
		<aui:field-wrapper label="end-date">
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}"
				yearRangeStart="${defaultStartYear}" dayParam="endDay"
				monthParam="endMonth" yearParam="endYear" yearNullable="true"
				dayNullable="true" monthNullable="true" yearValue="${endYear}"
				monthValue="${endMonth}" dayValue="${endDay}"></liferay-ui:input-date>
		</aui:field-wrapper>
		</aui:column>
		<aui:column columnWidth="10">
			<aui:field-wrapper cssClass="btn-search">
				<aui:button type="submit" value="search"></aui:button>
			</aui:field-wrapper>
		</aui:column>
	</aui:layout>
</aui:form>




<div class="table-overflow table-absolute">
		
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
				<%=dateFormatDateTime.format(asynchronousProccessAudit.getCreateDate()) %> 
			</c:if>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="endDate">
			<c:if test="${not empty asynchronousProccessAudit.endDate}">
				<%=dateFormatDateTime.format(asynchronousProccessAudit.getEndDate())%>
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
		
		
			<c:choose>
				<c:when test="${not empty asynchronousProccessAudit.extraContent and showExtraContent}">
					<liferay-ui:search-container-column-text align="center" name="extraContent" >
							<%
							try{
								String extraContent = asynchronousProccessAudit.getExtraContent();
								
								if(!extraContent.isEmpty()){
									extraContent = extraContent.replace("\"","'");
									JSONObject obj = JSONFactoryUtil.createJSONObject(extraContent);
									String path = obj.getString("url");
									String portletId = obj.getString("portletId");
									String contentType = obj.getString("contentType");
									String pathControl = obj.getString("pathControl");
									String id = String.valueOf(asynchronousProccessAudit.getAsynchronousProcessAuditId());
									%>
									<liferay-util:include portletId="<%=portletId%>" page="<%=pathControl%>"  >
										  <liferay-util:param name="namespace" value="<%=portletId%>" />
										  <liferay-util:param name="path" value="<%=path%>" />
										  <liferay-util:param name="contentType" value="<%=contentType%>" />
										  <liferay-util:param name="id" value="<%=id%>" />
									</liferay-util:include> 	  
									<%	}
							}
							catch( Exception e){
								%>
								
								<%
							} 
							%>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
		
		
			</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
</div>
