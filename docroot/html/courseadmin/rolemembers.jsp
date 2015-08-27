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
<%
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
boolean backToEdit = ParamUtil.getBoolean(request, "backToEdit");
String redirectOfEdit = ParamUtil.getString(request, "redirectOfEdit");
Course course=CourseLocalServiceUtil.getCourse(courseId);
long createdGroupId=course.getGroupCreatedId();
Role role=RoleLocalServiceUtil.getRole(roleId);
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(locale);
String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(locale);
String tab=StringPool.BLANK;
if(roleId==commmanager.getRoleId()){
	tab =  LanguageUtil.get(pageContext,"courseadmin.adminactions.students");
}else if(roleId==prefs.getEditorRole()){
	tab = editorName;
}else{
	tab = teacherName;
}
java.util.List<User> users=new java.util.ArrayList<User>();

if(roleId!=commmanager.getRoleId())
{
List<UserGroupRole> ugrs=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(createdGroupId, roleId);

users=new java.util.ArrayList<User>();
for(UserGroupRole ugr:ugrs)
{
	users.add(ugr.getUser());
}
}
else
{
	java.util.List<User> userst=UserLocalServiceUtil.getGroupUsers(createdGroupId);
	
	for(User usert:userst)
	{
		List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(usert.getUserId(),createdGroupId);
		boolean remove =false;
		for(UserGroupRole ugr:userGroupRoles){
			if(ugr.getRoleId()==prefs.getEditorRole()||ugr.getRoleId()==prefs.getTeacherRole()){
				remove = true;
				break;
			}
		}
		if(!remove){
			users.add(usert);
		}
	}
}

%>
<div class="container-toolbar" >
	<%@ include file="inc/toolbar.jspf" %>
</div>

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
<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/rolememberstab.jsp");
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("backToEdit",Boolean.toString(backToEdit));
if(backToEdit){
	portletURL.setParameter("redirectOfEdit",redirectOfEdit);
}
portletURL.setParameter("roleId",Long.toString(roleId));
portletURL.setParameter("tabs1",tab);
%>

<liferay-ui:search-container emptyResultsMessage="there-are-no-users"
 delta="10" deltaConfigurable="true" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%

List<User> orderedUsers = new ArrayList<User>();
orderedUsers.addAll(users);
Collections.sort(orderedUsers, new Comparator<User>() {
    @Override
    public int compare(final User object1, final User object2) {
        return object1.getFullName().toLowerCase().compareTo(object2.getFullName().toLowerCase());
    }
} );

results = ListUtil.subList(orderedUsers, searchContainer.getStart(),
searchContainer.getEnd());
if(results.size()<=0){
	results = ListUtil.subList(orderedUsers,0,searchContainer.getDelta());
}
total = users.size();
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.portal.model.User"
keyProperty="userId"
modelVar="user">
<liferay-ui:search-container-column-text>
<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
</liferay-ui:search-container-column-text>
<liferay-ui:search-container-column-text>

<liferay-ui:icon-menu>
<%if(roleId==commmanager.getRoleId())
{
%>
<liferay-portlet:renderURL  var="editInscriptionDatesURL"  windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/courseadmin/editinscriptiondates.jsp"/>
	<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"/>
	<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
</liferay-portlet:renderURL>

<%
String inscrURL="javascript:changedates('" + editInscriptionDatesURL + "')";

%>

<liferay-ui:icon image="calendar" url='<%=inscrURL %>' label="dates"></liferay-ui:icon>
<%
}
%>
<liferay-portlet:actionURL name="removeUserRole" var="removeUserRoleURL">
	<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp"/>
	<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"/>
	<portlet:param name="backToEdit" value="<%=Boolean.toString(backToEdit) %>" />
	<c:if test="<%=backToEdit %>">
		<portlet:param name="redirectOfEdit" value='<%=redirectOfEdit %>'/>
	</c:if>
	<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
	<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"/>
	<liferay-portlet:param name="tabs1" value="<%=tab %>"/>
</liferay-portlet:actionURL>
<liferay-ui:icon-delete url="<%=removeUserRoleURL+\"&cur=\"+searchContainer.getCur() %>" label="delete"></liferay-ui:icon-delete>
</liferay-ui:icon-menu>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />

</liferay-ui:search-container>

