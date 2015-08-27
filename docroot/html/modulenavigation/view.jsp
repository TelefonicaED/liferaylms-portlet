<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>

<%@ include file="/init.jsp" %>

<%
	long moduleId = ParamUtil.getLong(request,"moduleId",0);

	if(moduleId != 0)
	{
		Module module = ModuleLocalServiceUtil.getModule(moduleId);
	
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),	Module.class.getName(), moduleId, "ADD_LACT")
				|| !ModuleLocalServiceUtil.isLocked(module.getPrimaryKey(),themeDisplay.getUserId())){

			Module previousModule = ModuleLocalServiceUtil.getPreviusModule(moduleId);
			Module nextModule 	  = ModuleLocalServiceUtil.getNextModule(moduleId);
			
			PortletPreferences preferences = null;
			String portletResource = ParamUtil.getString(request, "portletResource");
			
			if (Validator.isNotNull(portletResource)) {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
			}else{
				preferences = renderRequest.getPreferences();
			}
			
			if(previousModule != null && (preferences.getValue("showPreviousModuleButton", "false")).equals("true")){

				boolean showPreviousModule = false;

				while(!showPreviousModule){
					
					if(previousModule == null){
						break;
					} else if(!ModuleLocalServiceUtil.isLocked(previousModule.getModuleId(),themeDisplay.getUserId())){
						showPreviousModule = true;
					} else {
						previousModule = ModuleLocalServiceUtil.getPreviusModule(previousModule.getModuleId());
					}
				}
				
				if(showPreviousModule){
					LiferayPortletURL  previousModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
					previousModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					previousModuleURL.removePublicRenderParameter("actId");
					previousModuleURL.setWindowState(WindowState.NORMAL);
					previousModuleURL.setParameter("moduleId", Long.toString(previousModule.getModuleId()));
					previousModuleURL.setParameter("themeId", Long.toString(ParamUtil.getLong(request,"themeId",1)-1));
					previousModuleURL.setPlid(themeDisplay.getPlid());
				%>
					<div id="previousmodule"><a href="<%=previousModuleURL.toString()%>"><liferay-ui:message key="moduleNavigator.previous" /></a></div>
				<%
				}
			}
			
			if(nextModule != null){
				
				boolean showNextModule = false;

				while(!showNextModule){
					
					if(nextModule == null){
						break;
					} else if(!ModuleLocalServiceUtil.isLocked(nextModule.getModuleId(),themeDisplay.getUserId())){
						showNextModule = true;
					} else {
						nextModule = ModuleLocalServiceUtil.getNextModule(nextModule.getModuleId());
					}
				}

				if(showNextModule){
					LiferayPortletURL  nextModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
					nextModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					nextModuleURL.removePublicRenderParameter("actId");
					nextModuleURL.setWindowState(WindowState.NORMAL);
					nextModuleURL.setParameter("moduleId", Long.toString(nextModule.getModuleId()));
					nextModuleURL.setParameter("themeId", Long.toString(ParamUtil.getLong(request,"themeId",0)+1));
					nextModuleURL.setPlid(themeDisplay.getPlid());
				%>
					<div id="nextmodule"><a href="<%=nextModuleURL.toString()%>"><liferay-ui:message key="moduleNavigator.next" /></a></div>
				<%
				}
			}
		}
	}
%>