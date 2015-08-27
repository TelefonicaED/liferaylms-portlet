
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portlet.announcements.model.AnnouncementsDelivery" %>
<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
SearchEntry entry = (SearchEntry)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

AnnouncementsDelivery delivery = (AnnouncementsDelivery)row.getObject();

int index = entry.getIndex();

String param = "announcementsType" + delivery.getType();
boolean defaultValue = false;
boolean disabled = false;

if (index == 1) {
	param += "Email";
	defaultValue = delivery.isEmail();
}
%>

<aui:input disabled="<%= disabled %>" label="" name='<%="checkbox_" + String.valueOf(delivery.getDeliveryId())%>' type="checkbox" value="<%= defaultValue %>" />
