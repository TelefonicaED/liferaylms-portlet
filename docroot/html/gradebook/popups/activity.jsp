<%@ include file="/init.jsp" %>
<%  Integer actType = ParamUtil.getInteger(request,"actType");

if(actType == 0){ %> <!-- Test -->
	<jsp:include page="/html/gradebook/popups/testResult.jsp" />
<%} else if(actType == 3){%> <!-- Taskp2p -->
	<jsp:include page="/html/gradebook/popups/taskp2pResult.jsp" />
<%} else if(actType == 6){%> <!-- Online -->
	<jsp:include page="/html/gradebook/popups/onlineResult.jsp" />
<%}%>