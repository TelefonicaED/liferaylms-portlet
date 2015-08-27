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
<%@ include file="/init.jsp" %>

<%
	boolean fichero = false;
	boolean textoenr = false;
	boolean existTries = false;
	boolean disabled = true;
	
	LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
	if(learningActivity != null) {
		if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
			fichero = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"fichero"));
			textoenr = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"textoenr")); 
		}
	}
	
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())){
		disabled = false;
	}
%>
<p><span class="label" style="font-weight:bold;"><liferay-ui:message key="onlinetaskactivity.permitStudents"/></span></p>
<aui:input type="checkbox" name="fichero" label="onlinetaskactivity.save.file" checked="<%=fichero %>" disabled='<%=disabled %>' inlineField="true"></aui:input>
<aui:input type="checkbox" name="textoenr" label="onlinetaskactivity.richTech" checked="<%=textoenr %>" disabled='<%=disabled %>' inlineField="true"></aui:input>
