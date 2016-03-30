<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat" %>

<%@ include file="/init.jsp" %>

<%	
	long moduleId			= ParamUtil.getLong(request,"moduleId",0);
	long actId				= ParamUtil.getLong(request,"actId",0);
	boolean actionEditing	= ParamUtil.getBoolean(request,"actionEditing",false);

	%>
	<portlet:actionURL var="discussionURL" name="addDiscussion">	
	</portlet:actionURL>
	<div id="activityCommet">
	<%
	if(actId != 0){
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(LearningActivity.class.getName(), actId);
		LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		if (assetEntry != null){
	%>		 
		 
		 <liferay-ui:discussion
				className="<%= LearningActivity.class.getName() %>"
				classPK="<%= actId %>"
				formAction="<%= discussionURL %>"
				formName="fm2"
				ratingsEnabled="false"
				redirect="<%= themeDisplay.getURLCurrent() %>"
				subject="<%= la.getTitle(themeDisplay.getLocale()) %>"
				userId="<%= themeDisplay.getUserId() %>"
				permissionClassName="com.liferay.lms.model.LearningActivityComments"
			/>
			
	<%		
		}
	}
	%>
	</div>
