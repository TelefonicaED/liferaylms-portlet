<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp"%>
<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
long currentModuleId=0;
long actId=ParamUtil.getLong(request,"actId",0);
Module theModule=null;
if(moduleId!=0)
{
	theModule=ModuleLocalServiceUtil.getModule(moduleId);
}
else
{
	if(actId!=0)
	{
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		theModule=ModuleLocalServiceUtil.getModule(larn.getModuleId());
	}
	else
	{
		List<Module> modules=(List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(modules.size()>0)
		{
			theModule=modules.get(0);
		}
	}
}
if(theModule!=null)
{
	%>
		<liferay-ui:header title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" />
	<%
    ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(), themeDisplay.getUserId());
    if(mr!=null)
    {
    	%>
    	Hemos empezado
    	<%
    }
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>