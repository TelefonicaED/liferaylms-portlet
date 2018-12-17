<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page	import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page	import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.Criterion"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@ include file="/init.jsp" %>

<%
	boolean fichero = false;
	boolean textoenr = false;
	boolean existTries = false;
	boolean disabled = true;
	long additionalFileId = 0;
	String additionalFileName = null;
	StringBuilder sb = null;
	
	LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
	if(learningActivity != null) {
		if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
			fichero = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"fichero"));
			textoenr = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"textoenr"));
			additionalFileId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"additionalFile"), 0);
		}
	}
	
	if(additionalFileId>0){
		FileEntry additionalFile = DLAppLocalServiceUtil.getFileEntry(additionalFileId);
		additionalFileName = additionalFile.getTitle();
		
		sb = new StringBuilder(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathContext());
		sb.append("/documents/");
		sb.append(additionalFile.getGroupId());
		sb.append(StringPool.SLASH);
		sb.append(additionalFile.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(additionalFile.getTitle())));	
	}
	
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())){
		disabled = false;
	}
%>
<p><span class="label" style="font-weight:bold;"><liferay-ui:message key="onlinetaskactivity.permitStudents"/></span></p>
<aui:input type="checkbox" name="fichero" label="onlinetaskactivity.save.file" checked="<%=fichero %>" disabled='<%=disabled %>' inlineField="true"></aui:input>
<aui:input type="checkbox" name="textoenr" label="onlinetaskactivity.richTech" checked="<%=textoenr %>" disabled='<%=disabled %>' inlineField="true"></aui:input>
<aui:input type="file" name="additionalFile" label="complementary-file" value=""/>
	<c:if test="<%=additionalFileName!=null %>">
		<a target="_blank" href="<%=sb.toString()%>"><%=additionalFileName%></a>
		<aui:input type="checkbox" name="deleteAdditionalFile" label="delete"/>
	</c:if>