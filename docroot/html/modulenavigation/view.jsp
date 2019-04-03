<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
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
	long currentActId = ParamUtil.getLong(request,"actId",0);
	
	long firstActId = Long.parseLong(renderRequest.getAttribute("firstActId").toString());
	long lastActId = Long.parseLong(renderRequest.getAttribute("lastActId").toString());
	

	if(moduleId != 0)
	{
		Module module = ModuleLocalServiceUtil.getModule(moduleId);
	
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),	Module.class.getName(), moduleId, "ADD_LACT")
				|| !module.isLocked(themeDisplay.getUserId())){

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
					} else if(!previousModule.isLocked(themeDisplay.getUserId())){
						showPreviousModule = true;
					} else {
						previousModule = ModuleLocalServiceUtil.getPreviusModule(previousModule.getModuleId());
					}
				}
				
				long actId = 0L;
				//Obtengo por una dynamic query la primera actividad del siguiente modulo
				if (previousModule != null){
					DynamicQuery query = DynamicQueryFactoryUtil.forClass(LearningActivity.class);
					query.add(RestrictionsFactoryUtil.eq("moduleId", previousModule.getModuleId()));
					query.addOrder(OrderFactoryUtil.desc("priority"));				
					List<LearningActivity> lActivitiesByModule  = LearningActivityLocalServiceUtil.dynamicQuery(query);
					if (lActivitiesByModule!= null && lActivitiesByModule.size()>0){
						if (themeDisplay.getPermissionChecker().hasPermission(lActivitiesByModule.get(0).getGroupId(),LearningActivity.class.getName(),lActivitiesByModule.get(0).getActId(), ActionKeys.VIEW)){
							actId = lActivitiesByModule.get(0).getActId();
						}
					}
				}
								
				if(showPreviousModule && (currentActId == firstActId)){
					LiferayPortletURL  previousModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
					previousModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					//previousModuleURL.removePublicRenderParameter("actId");
					if (actId == 0L){
						previousModuleURL.removePublicRenderParameter("actId");
					}else{
						previousModuleURL.setParameter("actId", Long.toString(actId));
					}
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
					} else if(!nextModule.isLocked(themeDisplay.getUserId())){
						showNextModule = true;
					} else {
						nextModule = ModuleLocalServiceUtil.getNextModule(nextModule.getModuleId());
					}
				}

				
				//long actId = 0L;
				//Obtengo por una dynamic query la primera actividad del siguiente modulo
				/*if (nextModule != null){
					DynamicQuery query = DynamicQueryFactoryUtil.forClass(LearningActivity.class);
					query.add(RestrictionsFactoryUtil.eq("moduleId", nextModule.getModuleId()));
					query.addOrder(OrderFactoryUtil.asc("priority"));				
					List<LearningActivity> lActivitiesByModule  = LearningActivityLocalServiceUtil.dynamicQuery(query);
					if (lActivitiesByModule!= null && lActivitiesByModule.size()>0){
						if (themeDisplay.getPermissionChecker().hasPermission(lActivitiesByModule.get(0).getGroupId(),LearningActivity.class.getName(),lActivitiesByModule.get(0).getActId(), ActionKeys.VIEW)){
							actId = lActivitiesByModule.get(0).getActId();
						}
					}			
				}*/
				
				if(showNextModule && (currentActId == lastActId)){
					LiferayPortletURL  nextModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
					nextModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					//if (actId == 0L){
						nextModuleURL.removePublicRenderParameter("actId");
					/*}else{
						nextModuleURL.setParameter("actId", Long.toString(actId));
					}*/
					
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