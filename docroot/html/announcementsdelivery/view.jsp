<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portlet.announcements.model.AnnouncementsDelivery" %>
<%@page import="com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil" %>
<%
long userId = themeDisplay.getUserId(); 
List<AnnouncementsDelivery> deliveries = null;

if (request.getRemoteUser() != null) {
	deliveries = AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(userId);
%>

<h3><liferay-ui:message key="announcements-deliver" /></h3>

<liferay-ui:message key="select-the-delivery-options-for-announcements" />
<portlet:actionURL name="changeDelivery" var="deliveriesURL">
</portlet:actionURL>
<br /><br />
<aui:form name="delivery" action="<%=deliveriesURL.toString() %>" method="post" role="form">
<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= deliveries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.announcements.model.AnnouncementsDelivery"
		escapedModel="<%= true %>"
		keyProperty="deliveryId"
		modelVar="delivery"
	>
		<liferay-ui:search-container-column-text
			name="type"
			value="<%= delivery.getType() %>"
		/>
		<liferay-ui:search-container-column-jsp
			name="email"
			path="/html/announcementsdelivery/checkbox.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
<aui:button-row>
	<aui:button type="submit" value="modify"/>
</aui:button-row>
</aui:form>
<%}%>