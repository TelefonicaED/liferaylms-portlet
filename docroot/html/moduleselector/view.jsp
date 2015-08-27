<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp"%>
<%! 
	private static final String LMS_ACTIVITIES_LIST_PORTLET_ID = 
			PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
%>
<% 
	Date now=new Date();
	PortletPreferences lmsactivitieslistPreferences = PortletPreferencesFactoryUtil.getPortletPreferences(request, LMS_ACTIVITIES_LIST_PORTLET_ID);
	boolean numerateModules = GetterUtil.getBoolean(lmsactivitieslistPreferences.getValue("numerateModules", StringPool.FALSE));
	List<Module> allModules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	List<Module> modules = new ArrayList<Module>(allModules.size());
	boolean actionEditing = ParamUtil.getBoolean(request, "actionEditing");
	boolean canAccessLock = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK");
	for(Module module:allModules) {
		boolean canAccess = (Validator.isNull(module.getStartDate()))?true:!module.getStartDate().after(now);
		boolean moduleEditing = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), themeDisplay.getScopeGroupId(), ActionKeys.UPDATE);
		if((actionEditing&&moduleEditing)||(canAccess&&(canAccessLock||!ModuleLocalServiceUtil.isLocked(module.getModuleId(),themeDisplay.getUserId())))) {
			modules.add(module);
		}
	}
%>
<div class="modules_selector_container">
	<c:if test="<%=!modules.isEmpty() %>">
		<c:choose>
			<c:when test="<%=modules.size()==1 %>">
				<% 
					String moduleTitle = modules.get(0).getTitle(themeDisplay.getLocale());
				%>
				<aui:field-wrapper label="<%=(numerateModules)?LanguageUtil.format(pageContext, \"moduleTitle.chapter\", new Object[]{1,moduleTitle}):moduleTitle%>"/>
			</c:when>
			<c:otherwise>
				<aui:select name="moduleSelect" inlineLabel="true" label="" cssClass="modules_selector_list">
				<%
					int themeId=1;
					long moduleId = ParamUtil.getLong(request, "moduleId", modules.get(0).getModuleId());
					for(Module module:modules){
						String moduleTitle = module.getTitle(themeDisplay.getLocale());
						LiferayPortletURL gotoModuleURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(renderRequest),
							PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName()),
							themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);		
						gotoModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
						gotoModuleURL.setWindowState(WindowState.NORMAL);
						gotoModuleURL.removePublicRenderParameter("actId");
						gotoModuleURL.setParameter("moduleId", Long.toString(module.getModuleId()));
						gotoModuleURL.setParameter("themeId", Long.toString(themeId));
						gotoModuleURL.setParameter("actionEditing", Boolean.toString(actionEditing));
					%>
						<aui:option selected="<%= module.getModuleId()==moduleId %>" 
							label="<%=(numerateModules)?LanguageUtil.format(pageContext, \"moduleTitle.chapter\", new Object[]{themeId++,moduleTitle}):moduleTitle%>" 
							value="<%=gotoModuleURL.toString() %>" />
						
					<%
					}
				%>
				</aui:select>
				<aui:button name="go" value="go" onClick="<%=\"AUI().use(function(A){ location.href = A.one('#\"+renderResponse.getNamespace()+\"moduleSelect > :selected').val(); });\" %>"/>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
