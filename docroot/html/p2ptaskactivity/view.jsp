
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp" %>
<div class="container-activity">
<script type="text/javascript">
<!--
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />changeDiv',
	        function(id) {
				var A = AUI();

				if(id=="1"){				
					A.one("#capa1").setStyle('display','block');
					A.one("#capa2").setStyle('display','none');
					A.one("#capa3").setStyle('display','none');
					A.one("#span1").addClass('selected');
					A.one("#span2").removeClass('selected');
					A.one("#span3").removeClass('selected');
				}else if(id=="2"){
					A.one("#capa1").setStyle('display','none');
					A.one("#capa2").setStyle('display','block');
					A.one("#capa3").setStyle('display','none');
					A.one("#span1").removeClass('selected');
					A.one("#span2").addClass('selected');
					A.one("#span3").removeClass('selected');
				}
				else if(id=="3"){
					A.one("#capa1").setStyle('display','none');
					A.one("#capa2").setStyle('display','none');
					A.one("#capa3").setStyle('display','block');
					A.one("#span1").removeClass('selected');
					A.one("#span2").removeClass('selected');
					A.one("#span3").addClass('selected');
				}	
							
	        },
	        ['node']
	    );

//-->
</script>

<%
Boolean isTablet = ParamUtil.getBoolean(renderRequest, "isTablet", false);

long actId=ParamUtil.getLong(request,"actId",0);

boolean uploadCorrect = ParamUtil.getBoolean(request,"uploadCorrect", false);
String uploadCorrectString = request.getParameter("uploadCorrect");
String correctionsCompletedString = request.getParameter("correctionsCompleted");

if(actId==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=activity.getTypeId();
	long latId = ParamUtil.getLong(request,"latId",0);
	if(latId==0){
		if(LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(actId, themeDisplay.getUserId())>0){
			List<LearningActivityTry> latList = LearningActivityTryLocalServiceUtil.
					getLearningActivityTryByActUser(actId, themeDisplay.getUserId());
			if(!latList.isEmpty())
			{
				for(LearningActivityTry lat :latList){
					latId = lat.getLatId();
				}
			}
		}
	}

	if(typeId==3)
	{
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")&&isTablet){
%>
			<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
			<%--<h3  class="description-h3"><liferay-ui:message key="description" /></h3> --%>
			<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
			
			<liferay-portlet:renderURL var="correctionPages">
				<liferay-portlet:param name="actId" value="<%=Long.toString(actId) %>" />
				<liferay-portlet:param name="jspPage" value="/html/p2ptaskactivity/revisions.jsp" />
			</liferay-portlet:renderURL>
			
			<%
					String urlCorrections = "self.location = '"+correctionPages.toString()+"';";
				%>
				<div class="container-buttons lms-valoraciones">
					<aui:button style="margin-top:10px" value="p2ptask-see-corrections" onClick="<%=urlCorrections.toString() %>" type="button" />
				</div>
				<%
			
		
		}else{
		%>
			<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
			<%--<h3  class="description-h3"><liferay-ui:message key="description" /></h3> --%>
			<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
			<%
			Long userId = themeDisplay.getUserId();
			P2pActivity myp2pActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
			
			//request.setAttribute("myp2pActivity", myp2pActivity);
			request.setAttribute("actId", actId);
			request.setAttribute("latId", latId);
			
			LearningActivityResult learnResult = 
					LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId,themeDisplay.getUserId());
			if(myp2pActivity==null){
			%>
			<div class="steps">
				<span id="span1" class="selected"><liferay-ui:message key="p2ptask-step1" />&nbsp;>&nbsp;</span>
				<span id="span2"><liferay-ui:message key="p2ptask-step2" />&nbsp;>&nbsp;</span>
				<span id="span3"><liferay-ui:message key="p2ptask-step3" /></span>
			</div>
			<div class="preg_content" id="capa1">
				<jsp:include page="inc/uploadActivity.jsp" >
					<jsp:param value="<%=uploadCorrectString %>" name="uploadCorrect"/>
				</jsp:include>
			</div>
			<div class="preg_content" id="capa2" style="display:none"></div>
			<div class="preg_content" id="capa3" style="display:none"></div>
			<%
			}
			else{
				String classCSS2="";
				String classCSS3="";
				String passed="";
				String javascript="";
				Long showRevision = ParamUtil.getLong(request, "showRevision",0);
				if(!learnResult.getPassed()){
					classCSS2="selected";
					if(showRevision==1)
						javascript=renderResponse.getNamespace()+"changeDiv(3);";
					else
						javascript=renderResponse.getNamespace()+"changeDiv(2);";
				}
				else{
					classCSS3="selected";
					javascript=renderResponse.getNamespace()+"changeDiv(3);";
					passed="done";
				}
				
				//request.setAttribute("myp2pActivity", myp2pActivity);
				request.setAttribute("actId", actId);
				request.setAttribute("latId", latId);
				
			%>
			<div class="steps">
				<span id="span1" onclick="<portlet:namespace />changeDiv(1)" class="clicable done"><liferay-ui:message key="p2ptask-step1" />&nbsp;>&nbsp;</span>
				<span id="span2" class="<%=classCSS2 %> clicable <%=passed%>" onclick="<portlet:namespace />changeDiv(2)"><liferay-ui:message key="p2ptask-step2" />&nbsp;>&nbsp;</span>
				<span id="span3" class="<%=classCSS3 %> clicable" onclick="<portlet:namespace />changeDiv(3)"><liferay-ui:message key="p2ptask-step3" /></span>
			</div>
			<div class="preg_content" id="capa1" style="display:none">
				<jsp:include page="inc/uploadActivity.jsp" />
			</div>
			<div class="preg_content" id="capa2" style="display:none">
				<jsp:include page="inc/correctActivities.jsp" >
					<jsp:param value="<%=correctionsCompletedString %>" name="correctionsCompleted"/>
				</jsp:include>
			</div>
			<div class="preg_content" id="capa3" style="display:none">
				<jsp:include page="inc/myCorrections.jsp" />
			</div>
			<script type="text/javascript">
			<%=javascript%>
			</script>
			<%
			}
			%>
			
			<liferay-portlet:renderURL var="correctionPage">
				<liferay-portlet:param name="actId" value="<%=Long.toString(actId) %>" />
				<liferay-portlet:param name="jspPage" value="/html/p2ptaskactivity/revisions.jsp" />
			</liferay-portlet:renderURL>
			
			<%
			if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
				String urlCorrection = "self.location = '"+correctionPage.toString()+"';";
				%>
				<div class="container-buttons lms-valoraciones">
					<aui:button style="margin-top:10px" value="p2ptask-see-corrections" onClick="<%=urlCorrection.toString() %>" type="button" />
				</div>
				<%
			}
		}
	}
	else
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
}
	%>
</div>