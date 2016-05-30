<%@page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.util.JS"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>

<div class="container-toolbar" >
	<%@ include file="inc/toolbar.jspf" %>
</div>
<% 
long roleId=ParamUtil.getLong(request, "roleId",0);
System.out.println("---ROLEID  "+roleId);
%>
<script>
function changedates(theURI)
{
AUI().use(function(A)
		{ var resizeInterval; 
		Liferay.Util.openWindow( 
				{ 
					id: '_courseadmin_WAR_liferaylmsportlet_showPopupInsctiptionDates', 
					title: '<%=LanguageUtil.get(pageContext, "calendar")%>',
					dialog: 
					{
					   width: 550,
					  height:400, modal:true,
					  centered:true, 
					  after: 
					  { visibleChange: function(evt){ var instance = evt.target; if(instance.get('visible')){ instance.centered(); } else { delete Liferay.Util.Window._map['_courseadmin_WAR_liferaylmsportlet_showPopupInsctiptionDates']; Liferay.Portlet.refresh(A.one('#p_p_id_courseadmin_WAR_liferaylmsportlet_'),{'p_t_lifecycle':0}); } }, init: function(evt){ var instance = evt.target; resizeInterval = A.setInterval(function(){ if(instance.get('y')<0){ instance.set('y',0); } }, 100); }, bodyContentChange: function(evt){ var instance = evt.target, bodyContent = instance.get('bodyContent'); if(!bodyContent.item) { if((!!bodyContent.get('tagName').toLowerCase)&&(bodyContent.get('tagName').toLowerCase()=='iframe')){ bodyContent.on('load',function(evt){ var messageDiv = evt.target.get('contentWindow.document').one('.portlet-msg-success'); if(messageDiv!=null){ var portlet = A.one('#p_p_id_courseadmin_WAR_liferaylmsportlet_'); Liferay.onceAfter(portlet.portletId + ':portletRefreshed', function(evt){ evt.portlet.one('.portlet-body * .portlet-body'). prepend('<div class="portlet-msg-success">'+messageDiv.getContent()+'</div>'); }); instance.close(); } }); } } }, destroy: function(evt){ A.clearInterval(resizeInterval); } } },
					  uri: theURI 
					  } 
				);
		});
}
--></script>







<aui:form name="fm" action="${searchURL }" method="POST">
	<liferay-ui:search-container searchContainer="${searchContainer}"
		iteratorURL="${searchContainer.iteratorURL}" emptyResultsMessage="there-are-no-users"
 		delta="10" deltaConfigurable="true">


		<liferay-ui:search-form page="/html/shared/usersSearchform.jsp"
			searchContainer="${searchContainer}"
			servletContext="<%= this.getServletConfig().getServletContext() %>" />

		<liferay-ui:search-container-results total="${searchContainer.total }"
			results="${searchContainer.results }" />
			
			
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" 	keyProperty="userId" 	modelVar="courseUser">
		<liferay-ui:search-container-column-text>
			<liferay-ui:user-display userId="${courseUser.userId}"></liferay-ui:user-display>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="screen-name"	property="screenName">
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text
			name="email-address"	property="emailAddress"	/>
		<liferay-ui:search-container-column-text>
			
			<liferay-ui:icon-menu>
			<c:if test="${commManagerRole}">
			
			<liferay-portlet:renderURL  var="editInscriptionDatesURL"  windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<liferay-portlet:param name="jspPage" value="/html/courseadmin/editinscriptiondates.jsp"/>
				<liferay-portlet:param name="courseId" value="${courseId}"/>
				<liferay-portlet:param name="userId" value="${courseUser.userId }"/>
			</liferay-portlet:renderURL>
			
			<c:if test="${showCalendar}">
					<liferay-ui:icon image="calendar" url="javascript:changedates('${editInscriptionDatesURL}')" label="dates"></liferay-ui:icon>
			</c:if>
			
			</c:if>
			<liferay-portlet:actionURL name="removeUserRole" var="removeUserRoleURL">
				<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp"/>
				<liferay-portlet:param name="courseId" value="${courseId}"/>
				<portlet:param name="backToEdit" value="${backToEdit}" />
				<c:if test="${backToEdit}">
					<portlet:param name="redirectOfEdit" value='${redirectOfEdit}'/>
				</c:if>
				<liferay-portlet:param name="userId" value="${courseUser.userId }"/>
				<liferay-portlet:param name="roleId" value="${roleId}"/>
				<liferay-portlet:param name="tabs1" value="${tab}"/>
			</liferay-portlet:actionURL>
			<liferay-ui:icon-delete url="${removeUserRoleURL}&cur=${searchContainer.cur}" label="delete"></liferay-ui:icon-delete>
			</liferay-ui:icon-menu>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

