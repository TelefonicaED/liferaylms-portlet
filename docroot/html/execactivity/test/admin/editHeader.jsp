<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");
	long typeId = ParamUtil.getLong(request, "typeId", -1);
	QuestionType qtype = new QuestionTypeRegistry().getQuestionType(typeId);
	String message = ParamUtil.get(request, "message", "");
	message += (qtype != null)?
					(!"".equals(message)?
							" "+qtype.getTitle(themeDisplay.getLocale())
							:qtype.getTitle(themeDisplay.getLocale()))
					:"";
	message = (!"".equals(message))?" ("+message+")":"";
	if(learningActivity!=null) {
%>
		<liferay-ui:header title="<%=learningActivity.getTitle(themeDisplay.getLocale()) + message %>" backURL="<%= (request.getAttribute(\"backUrl\")!=null)?(String)request.getAttribute(\"backUrl\"):\"\" %>"></liferay-ui:header>
		
<%
	}
%>