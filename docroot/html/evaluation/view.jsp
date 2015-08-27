<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@include file="/init.jsp" %>
<div class="option-nota">
<% 
	LiferayPortletURL  gotoEvaluationURL = (LiferayPortletURL)renderResponse.createActionURL();	
	gotoEvaluationURL.setParameter(ActionRequest.ACTION_NAME, "gotoEvaluation");
	gotoEvaluationURL.removePublicRenderParameter("moduleId");
	gotoEvaluationURL.removePublicRenderParameter("actId");
	gotoEvaluationURL.setWindowState(WindowState.NORMAL);
	gotoEvaluationURL.setPlid(themeDisplay.getPlid());

%>
	<aui:a href="<%=gotoEvaluationURL.toString() %>"  label="evaluation.finalQualification"  />
</div>
