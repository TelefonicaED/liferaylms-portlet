<%@page import="com.liferay.lms.auditing.AuditConstants"%>
<%@page import="com.liferay.lms.auditing.AuditingLogFactory"%>
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
<%@ include file="/init.jsp" %>
<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
long actId=ParamUtil.getLong(request,"actId",0);
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
if(actId==0)
{
	
	Module theModule=null;
	if(moduleId!=0)
	{
		theModule=ModuleLocalServiceUtil.getModule(moduleId);
	}
	else
	{
		java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(modules.size()>0)
		{
			theModule=modules.get(0);
		}
	}
	if(theModule!=null)
	{
		long groupId = theModule.getGroupId();
		String name = Module.class.getName();
		String primKey = String.valueOf(theModule.getPrimaryKey());
		if(permissionChecker.hasPermission(groupId, name, primKey, ActionKeys.UPDATE)||!ModuleLocalServiceUtil.isLocked(theModule.getPrimaryKey(),themeDisplay.getUserId()))
		{
			AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					Module.class.getName(),theModule.getModuleId(), themeDisplay.getUserId(), AuditConstants.VIEW,"");
			
		%>
		
		<div id="moduleDescription">
		<%=theModule.getDescription(themeDisplay.getLocale()) %>
		</div>
		
		
	
		<%
		}
		else
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}
	else
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);

}
%>