<%@page import="com.liferay.portal.service.ResourceActionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionChecker"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseType"%>
<%@page import="com.liferay.lms.service.CourseTypeLocalServiceUtil"%>
<%@ include file="/init.jsp"%>

<script type="text/javascript">
<!--
AUI().ready(
    function(A) {
		A.all('img[onblur*=hide]').each(function(img){
			img.after(['blur','mouseout'],function(event){ 
				Liferay.Portal.ToolTip._cached.destroy();
				Liferay.Portal.ToolTip._cached=null;
			 });
		});
    }
);
//-->
</script>

<ul class="activity-list">
<h1><liferay-ui:message key="content"></liferay-ui:message> </h1>
<%
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	long courseTypeId = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId()).getClassTypeId();
	List<Long> listLearningActivityTypes = null;
	if(courseTypeId>0){
		CourseType courseType = CourseTypeLocalServiceUtil.getCourseType(courseTypeId);
		listLearningActivityTypes = courseType.getLearningActivityTypeIds();
	}
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	long[] invisibleTypes = StringUtil.split(PropsUtil.get("lms.learningactivity.invisibles"), StringPool.COMMA,-1L);
	long[] orderedIds = StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getActivities(), StringPool.COMMA, -1L);
	int currentLearningActivityType=0;
	for(LearningActivityType learningActivityType:learningActivityTypeRegistry.getLearningActivityTypesForCreating())
	{
		if(learningActivityType != null && !ArrayUtil.contains(invisibleTypes, learningActivityType.getTypeId()) && 
				((course==null && learningActivityType.allowsBank()) || course!=null ) ){
			
			boolean hasPermission = true;
			if(ResourceActionLocalServiceUtil.fetchResourceAction(learningActivityType.getClassName(), "ADD_ACTIVITY")!=null){
				hasPermission = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), learningActivityType.getClassName(), themeDisplay.getScopeGroupId(), "ADD_ACTIVITY");
			}
			
			if(hasPermission && (learningActivityType.getTypeId()==9||learningActivityType.getTypeId()==2||learningActivityType.getTypeId()==7) 
					&& (listLearningActivityTypes==null || listLearningActivityTypes.size()==0 || listLearningActivityTypes.contains(learningActivityType.getTypeId()))){

				%>	
					<liferay-portlet:renderURL var="newactivityURL">
						<liferay-portlet:param name="editing" value="<%=StringPool.TRUE %>" />
						<liferay-portlet:param name="resId" value="0" />
						<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
						<liferay-portlet:param name="type" value="<%=Long.toString(learningActivityType.getTypeId()) %>" />
					</liferay-portlet:renderURL>
					
					<liferay-util:buffer var="activityMessage">
					    <%=LanguageUtil.get(themeDisplay.getLocale(), learningActivityType.getName()) %>
					    <span class="activity-help">
							<liferay-ui:icon-help message="<%=learningActivityType.getDescription() %>"  />
						</span>
					</liferay-util:buffer>
				
					<li class="activity_<%=learningActivityType.getTypeId()%>">
						<liferay-ui:icon image="add" label="<%=true%>" message="<%=activityMessage %>" url="<%=newactivityURL%>" cssClass="activity-icon" />
					</li>
				<%
						}
			}
	}%>
	
	<span class="separacion"></span><h1><liferay-ui:message key="activities"></liferay-ui:message> </h1>
	<%for(LearningActivityType learningActivityType:learningActivityTypeRegistry.getLearningActivityTypesForCreating())
	{
		if(learningActivityType != null && !ArrayUtil.contains(invisibleTypes, learningActivityType.getTypeId())
				&& (course==null && learningActivityType.allowsBank() || course!=null )) {
			boolean hasPermission = true;
			if(ResourceActionLocalServiceUtil.fetchResourceAction(learningActivityType.getClassName(), "ADD_ACTIVITY")!=null){
				hasPermission = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), learningActivityType.getClassName(), themeDisplay.getScopeGroupId(), "ADD_ACTIVITY");
			}
			if(hasPermission && learningActivityType.getTypeId()!=9 && learningActivityType.getTypeId()!=2 && learningActivityType.getTypeId()!=7
					&& (listLearningActivityTypes==null || listLearningActivityTypes.size()==0 || listLearningActivityTypes.contains(learningActivityType.getTypeId()))){

%>	
	<liferay-portlet:renderURL var="newactivityURLAux">
		<liferay-portlet:param name="editing" value="<%=StringPool.TRUE %>" />
		<liferay-portlet:param name="resId" value="0" />
		<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
		<liferay-portlet:param name="type" value="<%=Long.toString(learningActivityType.getTypeId()) %>" />
	</liferay-portlet:renderURL>
	
	<liferay-util:buffer var="activityMessageAux">
	    <%=LanguageUtil.get(themeDisplay.getLocale(), learningActivityType.getName()) %>
	    <span class="activity-help">
			<liferay-ui:icon-help message="<%=learningActivityType.getDescription() %>"  />
		</span>
	</liferay-util:buffer>

	<li class="activity_<%=learningActivityType.getTypeId()%>">
		<liferay-ui:icon image="add" label="<%=true%>" message="<%=activityMessageAux %>" url="<%=newactivityURLAux%>" cssClass="activity-icon" />
	</li>
<%
		}
			}
	}
	
	
%>
</ul>
