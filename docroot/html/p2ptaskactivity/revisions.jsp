<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.portal.service.UserGroupLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.persistence.GroupUtil"%>
<%@page import="com.liferay.portal.service.GroupServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@include file="/init.jsp" %>

<%
	long actId=ParamUtil.getLong(request,"actId",0);
	String criteria = ParamUtil.getString(request,"criteria","");
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/p2ptaskactivity/revisions.jsp");
	portletURL.setParameter("criteria", criteria);
	portletURL.setParameter("delta", "10"); 
%>

<div class="student_search"> 

	<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/p2ptaskactivity/revisions.jsp"></portlet:param>
	</portlet:renderURL>

	<aui:form name="studentsearch" action="<%=buscarURL %>" cssClass="search_lms" method="post">
		<aui:fieldset>
			<aui:column>
				<aui:input label="studentsearch.criteria" name="criteria" size="20" value="<%=criteria %>" />	
			</aui:column>
			<aui:column cssClass="search_lms_button">
				<aui:button-row>
					<aui:button name="searchUsers" value="search" type="submit" />
				</aui:button-row>
			</aui:column>	
		</aui:fieldset>
	</aui:form>

	<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="5">

	   	<liferay-ui:search-container-results>
			<%
				String middleName = null;
		
				LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
				params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
				
				OrderByComparator obc = null;
				
				List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_APPROVED, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
				int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_APPROVED, params);
						
				pageContext.setAttribute("results", userListPage);
			    pageContext.setAttribute("total", userCount);
			    pageContext.setAttribute("delta", 10);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
		
		<portlet:renderURL var="verDetalle">
			<portlet:param name="jspPage" value="/html/p2ptaskactivity/detalleAct.jsp" />
			<portlet:param name="actId" value="<%=String.valueOf(actId) %>" />
			<portlet:param name="userId" value="<%=String.valueOf(user.getUserId()) %>" />
		</portlet:renderURL>
		
		<%
		String nameTit = LanguageUtil.get(pageContext, "name");
		%>
		
		<liferay-ui:search-container-column-text value="<%=user.getFullName()%>" name="<%=nameTit %>"  />
		<%
		boolean existP2p = P2pActivityLocalServiceUtil.existP2pAct(Long.valueOf(actId), Long.valueOf(user.getUserId()));
		boolean correctionCompleted = P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, user.getUserId());
		
		String textTaks = "";
		
		String textTaksTit = LanguageUtil.get(pageContext, "state");
		
		//Si se ha entregado la tarea
		if(existP2p && !correctionCompleted ){
			textTaks = LanguageUtil.get(pageContext, "p2ptask-incompleta");
		} else if(existP2p && correctionCompleted){
			textTaks = LanguageUtil.get(pageContext, "p2ptask-superada");
		}else{
			textTaks = LanguageUtil.get(pageContext, "p2ptask-nosuperada");
		}
		%>
		<liferay-ui:search-container-column-text value="<%=textTaks %>" name="<%=textTaksTit %>" />
		<%
		P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, Long.valueOf(user.getUserId()));

		String dateTit = LanguageUtil.get(pageContext, "date");
		
		Date dateDel;
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dFormat.setTimeZone(themeDisplay.getTimeZone());	
		String dateDelS = "";
		if(myP2PActivity != null){
			dateDel = myP2PActivity.getDate();
			dateDelS = dFormat.format(dateDel);
		}
		%>
		<liferay-ui:search-container-column-text value="<%=dateDelS %>" name="<%=dateTit %>" />
		<%
		if(existP2p){
			String urlResume = "self.location = '"+verDetalle+"';";
			String nameButton =LanguageUtil.get(pageContext, "p2ptask-see-task"); 
		%>
			<liferay-ui:search-container-column-button href="<%=urlResume %>"  name="<%=nameButton %>" />
		<%} else{%>
			<liferay-ui:search-container-column-text value="" />
		<%} %>
		</liferay-ui:search-container-row>
	 	<liferay-ui:search-iterator />
	</liferay-ui:search-container>
<portlet:renderURL var="back">
	<portlet:param name="jspPage" value="/html/p2ptaskactivity/view.jsp" />
	<portlet:param name="actId" value="<%=String.valueOf(actId) %>" />
</portlet:renderURL>
<%
String urlback = "self.location = '"+back+"';";
%>
<aui:button-row>
	<aui:button cssClass="floatl" value="back" type="button" onClick="<%=urlback %>" style="margin-top:10px" />
</aui:button-row>
</div>
