<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.model.MembershipRequest"%>
<%@ include file="/init.jsp" %>

<liferay-ui:error key="course.full" message="course.full" />

<liferay-ui:message key="inscription.petition" />: ${searchContainer.total}  <liferay-ui:message key="inscription.totalUsers" />: ${numberUsers}${maxUsers}
<%SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy"); %>

<c:if test="${searchContainer.total > 0}">
	<liferay-ui:search-container  searchContainer="${searchContainer}" iteratorURL="${searchContainer.iteratorURL}">
		<liferay-ui:search-container-results results="${searchContainer.results}" total="${searchContainer.total}"/>
		<liferay-ui:search-container-row
			className="com.liferay.portal.model.MembershipRequest"
			keyProperty="membershipRequestId"
			modelVar="membershipRequest"
		>
			<%
			User usert = UserLocalServiceUtil.fetchUser(membershipRequest.getUserId());
			%>
			<liferay-ui:search-container-column-text
				name="inscriptionadmin.user"
			    value="<%= usert.getFullName() %>"
				align="left"
			/>
			<liferay-ui:search-container-column-text
				name="inscriptionadmin.date"
			    value="<%= sdf.format(membershipRequest.getCreateDate()) %>"
				align="left"
			/>
			<liferay-ui:search-container-column-jsp path="/html/inscriptionadmin/actions.jsp" align="right" />
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>