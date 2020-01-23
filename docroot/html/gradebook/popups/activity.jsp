<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@ include file="/init.jsp" %>
<%  Integer actType = ParamUtil.getInteger(request,"actType");

LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
LearningActivityType learningActivityType = learningActivityTypeRegistry.getLearningActivityType(actType);

%> 

<liferay-util:include portletId="<%=learningActivityType.getPortletId() %>" page="<%=learningActivityType.getSpecificResultsPage() %>"  >
	  <liferay-util:param name="studentId" value='<%=String.valueOf(ParamUtil.getLong(request, "studentId")) %>' />
	  <liferay-util:param name="actId" value='<%=String.valueOf(ParamUtil.getLong(request, "actId")) %>' />
</liferay-util:include> 	