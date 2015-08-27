<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);
	
	long entryId=ParamUtil.getLong(request, "assertId");
	String sco=ParamUtil.getString(request, "sco","");
	if(entryId==0){
		entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "assetEntry"), 0);
	}
	if(sco.equals("")){
		sco = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "assetEntry"), "");
	}
	LiferayPortletURL backUrl = PortletURLFactoryUtil.create(request, PortalUtil.getJsSafePortletId("lmsactivitieslist"+
				PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);
	backUrl.setWindowState(LiferayWindowState.POP_UP);
	backUrl.setParameter("actionEditing", String.valueOf(true));	
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));	
	backUrl.setParameter("jspPage", "/html/editactivity/editactivity.jsp");
	backUrl.setParameter("assertId", String.valueOf(entryId));	
	
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());	
	PortletURL path = assetRenderer.getURLEdit((LiferayPortletRequest)renderRequest,(LiferayPortletResponse)renderResponse); 
    path.setWindowState(LiferayWindowState.POP_UP);
    
%>
<script type="text/javascript">
<!--
	function <portlet:namespace />back(source) {
		if ((!!window.postMessage)&&(window.parent != window)) {
			AUI().use('json-stringify', function(A){
				parent.postMessage(JSON.stringify({name:'resizeWidthActivity',
        							   width:'750px'}), window.location.origin);
			});
		}

	}

	AUI().ready('aui-resize-iframe', function(A) {
		A.one('#<portlet:namespace/>editor').plug(A.Plugin.ResizeIframe);
	});

//-->
</script>

<liferay-ui:icon image="back" message="back" url="<%=backUrl.toString() %>" label="true" onClick="<%=renderResponse.getNamespace()+\"back(this)\" %>" />
<liferay-ui:header title="<%=AssetRendererFactoryRegistryUtil.
								getAssetRendererFactoryByClassName(LearningActivity.class.getName()).
								getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale()).get(Long.valueOf(learningActivity.getTypeId()))%>"></liferay-ui:header>
<liferay-ui:header title="<%=learningActivity.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>
<iframe id="<portlet:namespace/>editor" src="<%=path.toString() %>" frameBorder="0" scrolling="no" width="100%" height="0"></iframe>

<aui:button-row>		
	<liferay-util:include page="/html/scormactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
</aui:button-row>