<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>

<%@ include file="/init.jsp"%>
<%
String timeTodoRefresh = (String) renderRequest.getAttribute("refreshPageEachXSeg");
%>


<liferay-portlet:resourceURL var="refreshURL">
	<liferay-portlet:param name="action" value="search"/>
	<liferay-portlet:param name="ajaxAction" value="refresh"/>
	<liferay-portlet:param name="showExtraContent" value="${showExtraContent}"/>
	<liferay-portlet:param name="deltaParam" value="${searchContainer.delta}"/>
	<liferay-portlet:param name="className" value="${classNameValue}"/>
	<liferay-portlet:param name="startYear" value="${startYear}"/>
	<liferay-portlet:param name="startMonth" value="${startMonth}"/>
	<liferay-portlet:param name="startDay" value="${startDay}"/>
	<liferay-portlet:param name="endYear" value="${endYear}"/>
	<liferay-portlet:param name="endMonth" value="${endMonth}"/>
	<liferay-portlet:param name="endDay" value="${endDay}"/>
	
</liferay-portlet:resourceURL>

<aui:form action="${refreshURL}" method="post" name="refreshFm" role="search">

</aui:form>

<script>
var timeTodoRefreshInseg = <%=timeTodoRefresh%> * 1000;

if(timeTodoRefreshInseg < 10000){
	timeTodoRefreshInseg = 10000;
}
setTimeout(function() { <portlet:namespace />refresh(); }, timeTodoRefreshInseg);
 
function <portlet:namespace />refresh(){
	AUI( ).use( 'aui-io-request', function( A ) {
		var portletBody = A.one( '#p_p_id<portlet:namespace/> .portlet-content' );
		if( portletBody == null ) {
			portletBody = A.one( '#p_p_id<portlet:namespace/> .portlet-borderless-container' );
		}
		portletBody.setStyle( 'position', 'relative' );
		A.io.request( '${refreshURL}', {
			method: 'post',
			form: { 
				id: '<portlet:namespace/>refreshFm' 
			}, 
			on: { 
				success: function( ) {
					var data = this.get( 'responseData' );
					A.one( '#<portlet:namespace/>search-results' ).html( data );
				}
			}
		} );
	} );
	
	setTimeout(function() { <portlet:namespace />refresh(); }, timeTodoRefreshInseg);	
	
}
 
</script>
<portlet:renderURL var="searchURL">
</portlet:renderURL>





<aui:form action="${searchURL}" method="post" name="search" role="search">
	<aui:layout>
		<c:if test="${showTypeSearcher}">
			<aui:column columnWidth="20">
				<aui:select name="className" label="type" cssClass="type-selector">
					<aui:option label="" value="" selected="${empty className}" />
					<c:forEach items="${classnames}" var="classNameValue">
						<aui:option label="${classNameValue}" value="${classNameValue}" selected="${classNameValue eq className}" />
					</c:forEach>
				</aui:select>
			</aui:column>
		</c:if>
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




<div id="${renderResponse.getNamespace()}search-results" class="table-overflow table-absolute">
		<%@ include file="/html/asynchronousprocessdashboard/search-container.jsp" %>
	
</div>
