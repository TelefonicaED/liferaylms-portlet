<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.model.MembershipRequest"%>
<%@ include file="/init.jsp" %>

<liferay-ui:error key="course.full" message="course.full" />

<liferay-ui:message key="inscription.petition" />: ${total}  <liferay-ui:message key="inscription.totalUsers" />: ${numberUsers}/${maxUsers}
<%SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy"); %>
<liferay-ui:search-container  delta='10' >
	<liferay-ui:search-container-results>
	<%
		int containerStart;
		int containerEnd;
		try {
			containerStart = ParamUtil.getInteger(request, "containerStart");
			containerEnd = ParamUtil.getInteger(request, "containerEnd");
		} catch (Exception e) {
			containerStart = searchContainer.getStart();
			containerEnd = searchContainer.getEnd();
		}
		if (containerStart <=0) {
			containerStart = searchContainer.getStart();
			containerEnd = searchContainer.getEnd(); 
		}
	
		List<MembershipRequest> memberships = (List<MembershipRequest>)request.getAttribute("memberships");

		results = ListUtil.subList(memberships, containerStart, containerEnd);
		
		pageContext.setAttribute("results",results);
		pageContext.setAttribute("total", memberships.size());	
	%>
	</liferay-ui:search-container-results>
	
	
	<liferay-ui:search-container-row
		className="com.liferay.portal.model.MembershipRequest"
		keyProperty="membershipRequestId"
		modelVar="membershipRequest"
	>
		<%
		User usert = UserLocalServiceUtil.getUser(membershipRequest.getUserId());
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