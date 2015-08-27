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

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String actDisplayDate = "";
	String actEndDate ="";
	Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	java.util.List<LearningActivity> activities = new java.util.ArrayList<LearningActivity>();
	
	if(moduleId == 0){
		
		if(actId != 0){
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			moduleId = larn.getModuleId();
		}
	}
	
	if(moduleId != 0)
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
		
		if(actId != 0){
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			if (larn.getCreateDate()!=null){
				actDisplayDate=dateFormat.format(larn.getCreateDate());
			}
			else{
				actDisplayDate=dateFormat.format(theModule.getStartDate());
			}
			if (larn.getEnddate()!=null){
				actEndDate=dateFormat.format(larn.getEnddate());
			}
			else{
				actEndDate=dateFormat.format(theModule.getEndDate());
			}
	%>
	<div id="displayDate">
		<span class="date-column">
			<liferay-ui:message key="activityDates.startDate"/>
		</span>
		<span class="date">
			<%=actDisplayDate%>
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span class="date-column" >
			<liferay-ui:message key="activityDates.endDate"/>
		</span>
		<span class="date">
			<%=actEndDate%>
		</span>
	</div>
<%	
		}
	}// fin for activities
%>