<%@page import="com.liferay.lms.auditing.AuditConstants"%>
<%@page import="com.liferay.lms.learningactivity.ResourceInternalLearningActivityType"%>
<%@page import="com.liferay.lms.auditing.AuditingLogFactory"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<div class="container-activity">

<%
long actId=ParamUtil.getLong(request,"actId",0);

if(actId==0 )
{
	
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{

	LearningActivity learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	
	%>
		<h2 class="description-title"><%=learnact.getTitle(themeDisplay.getLocale())%></h2>
		<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
		<div class="description"><%=learnact.getDescription(themeDisplay.getLocale()) %></div>	
	<%
	
	if(learnact.getTypeId()!=7 )
	{
		
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		if(!LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
		{
			if(!permissionChecker.hasPermission(learnact.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE) ||
					!permissionChecker.hasOwnerPermission(learnact.getCompanyId(), LearningActivity.class.getName(), actId, learnact.getUserId(), ActionKeys.UPDATE)){
				ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
	
				LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				learningTry.setEndDate(new java.util.Date(System.currentTimeMillis()));
				learningTry.setResult(100);
				LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);
			}
		}
		if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
		{
			
			long entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"assetEntry"),0);
			
			if(entryId!=0){
				AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
				AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
				AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
				String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
				
				if(permissionChecker.hasPermission(entry.getGroupId(), entry.getClassName(), entry.getClassPK(), ActionKeys.VIEW))
				{ %>
					<liferay-util:include  page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
				<%
				}else{
					%>
					<div class="portlet-msg-error">
						<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource"/>
					</div>
					<%
				}
			}
		}
	}
}
%>
</div>