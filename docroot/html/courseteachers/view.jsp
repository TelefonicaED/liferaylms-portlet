<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@ include file="/init.jsp" %>
<div class="teachers">
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

LmsPrefs lmsprefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
long teacherRoleId=lmsprefs.getTeacherRole();
java.util.List<UserGroupRole> ugrs=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(themeDisplay.getScopeGroupId(),teacherRoleId);
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
for(UserGroupRole ugr:ugrs)
{
	User teacher=UserLocalServiceUtil.getUser(ugr.getUserId());
	%>
	<div class="teacher">
	<liferay-ui:user-display displayStyle="2"  userId="<%=teacher.getUserId() %>">
<%-- 		<liferay-ui:custom-attributes-available className="<%= User.class.getName() %>" companyId="<%=themeDisplay.getCompanyId()%>">
			<liferay-ui:custom-attribute-list className="<%=User.class.getName()%>" classPK="<%=teacher.getPrimaryKey() %>" editable="false" label="false" ></liferay-ui:custom-attribute-list>
		</liferay-ui:custom-attributes-available>
		--%>
	</liferay-ui:user-display>
	</div>
	
	
	<%
}
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>

</div>