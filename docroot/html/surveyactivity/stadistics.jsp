<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.learningactivity.SurveyLearningActivityType"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.model.SurveyResult"%>
<%@page import="com.liferay.lms.service.SurveyResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>


<%@ include file="/init.jsp"%>

<%
	if (ParamUtil.getLong(request, "actId", 0) == 0) 
	{
		renderRequest.setAttribute( WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} 
	else
	{	
		String uuid = ParamUtil.getString(renderRequest, "UUID",null);
		
		long actId = ParamUtil.getLong(request, "actId",0);
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	%>
		<portlet:renderURL var="backURL">
			<portlet:param name="jspPage" value="/html/surveyactivity/view.jsp"></portlet:param>
		</portlet:renderURL>
		<liferay-ui:header
					backURL="${backURL }"
					localizeTitle="<%= false %>"
					title=""
				/>
				
		<liferay-portlet:resourceURL var="stadisticsReportURL" >
			<portlet:param name="action" value="stadisticsReport"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		
		<liferay-portlet:renderURL var="renderURL">
			<portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
			<portlet:param name="jspPage" value="/html/surveyactivity/stadistics.jsp"></portlet:param>
		</liferay-portlet:renderURL>
		
		<div id="${renderResponse.getNamespace()}generate_report" class="rightButton">
			<liferay-ui:icon image="export" label="<%= true %>" message="download-content" method="get" url="javascript:${renderResponse.getNamespace()}exportReport();" />
		</div>
		<div id="${renderResponse.getNamespace()}generating_report" class="aui-helper-hidden">
			<div class="message_generating_report"><liferay-ui:message key="generatingreport"/></div>
		</div>
		<div id="${renderResponse.getNamespace()}download_report" class="aui-helper-hidden"></div>
		
		
		<div class="surveyactivity stadistics">
		
			<%
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
			List<SurveyResult> listaResultadosEncuesta = SurveyResultLocalServiceUtil.getSurveyResultByActId(actId);
			long total;
			long totalAnswer;
			String textoAux;
			String percent;
			DecimalFormat df = new DecimalFormat("###.##");;
			String texto;
			long participants = LearningActivityResultLocalServiceUtil.countByActId(actId);
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(learningActivity.getGroupId());
			long courseUsers = CourseResultLocalServiceUtil.countByCourseId(course.getCourseId());
			double passPercent =  ((double)participants/(double)courseUsers)*100;
			percent  = df.format(passPercent);
			%>
		
			<h2><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getTitle(themeDisplay.getLocale()) %></h2>
			<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
			<div class="description"><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getDescription(themeDisplay.getLocale()) %></div>
		
			<div  class="questiontext"><span class="participation color_tercero"><liferay-ui:message key="surveyactivity.stadistics.participants" /><%= participants %></span></div>	
			<div  class="questiontext"><span class="participation color_tercero"><liferay-ui:message key="surveyactivity.stadistics.participation-rate" /><%= percent %>%</span></div>	
			
			
			
		
			<% 
			for(TestQuestion question:questions){
				total = SurveyResultLocalServiceUtil.countByQuestionId(question.getQuestionId());
			%>
			<div class="question">
				<div  class="questiontext">
					<p><%=question.getText() %></p>
				</div>
				<span class="total color_tercero"><liferay-ui:message key="surveyactivity.stadistics.total" />: <%= total %></span>		
				<%
				
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				if(testAnswers!=null && testAnswers.size()>0){
					for(TestAnswer answer:testAnswers){
						textoAux = HtmlUtil.extractText(answer.getAnswer());
						texto = textoAux.length() > 50 ? textoAux.substring(0,50)+"..." : textoAux;
						totalAnswer = SurveyResultLocalServiceUtil.countByQuestionIdAndAnswerId(question.getQuestionId(), answer.getAnswerId());
						//percent = df.format(SurveyResultLocalServiceUtil.getPercentageByQuestionIdAndAnswerId(question.getQuestionId(), answer.getAnswerId(), total));
						if(total>0){
							percent = df.format(100*(double)totalAnswer/(double)total);	
						}else{
							percent = "0";
						}
						
					%>
						<div class="answer">
							<%=texto %>
							<span class="porcentaje negrita"><liferay-ui:message key="surveyactivity.stadistics.answers" /> <%=totalAnswer %> (<%=percent %>%)</span>
						</div>
					<%
					}
				}else{
					if(listaResultadosEncuesta!=null && listaResultadosEncuesta.size()>0){
					%>
						<div class="answer">
								<p><liferay-ui:message key="surveyactivity.stadistics.freeanswer" /></p>
						</div>
					<% }
				}
				%>
			</div>
			<%
			} 
			%>
		</div>
		
	
	
	
	<script>
	
	
		function <portlet:namespace/>exportReport(){
	
			$('#<portlet:namespace />generating_report').removeClass("aui-helper-hidden");
			$('#<portlet:namespace />generate_report').addClass("aui-helper-hidden");
			var exportResourceURL = "${renderURL}";
			var action ="stadisticsReport";
	
			$.ajax({
				dataType: 'json',
				url:'${stadisticsReportURL}',
			    cache:false,
				data: {
					status : status,
					creatingThread : true,
					action : action
				},
				success: function(data){
					if(data){
						//location.href=exportResourceURL+'&UUID='+data.UUID+'&action='+action;
						<portlet:namespace />readThreadState(data.UUID);
					}else{
						alert("Error generando el archivo");
						$('#<portlet:namespace />generating_report').addClass("aui-helper-hidden");
						$('#<portlet:namespace />generate_report').removeClass("aui-helper-hidden");
					}
				},
				error: function(){
					alert("Error al generar el archivo");
					$('#<portlet:namespace />generating_report').addClass("aui-helper-hidden");
					$('#<portlet:namespace />generate_report').removeClass("aui-helper-hidden");
				}
			});
	
}

function <portlet:namespace />downloadReport(url){
	location.href = url;
	$('#<portlet:namespace />download_report').addClass("aui-helper-hidden");
	$('#<portlet:namespace />generate_report').removeClass("aui-helper-hidden");
	//location.href='${renderURL}&UUID=null';
}



</script>

<% if(uuid!=null && uuid.trim()!="") {
	%>
	<script>	
		$('#<portlet:namespace />download_report').addClass("aui-helper-hidden");
		$('#<portlet:namespace />generate_report').addClass("aui-helper-hidden");
		$('#<portlet:namespace />generating_report').removeClass("aui-helper-hidden");
		$(document).ready(function(){
			<portlet:namespace />readThreadState(<%=uuid%>);
		});
		
		
	</script>
	<%	
		} 
	} 
	%>
	
	<script>
		function <portlet:namespace />readThreadState(uuid){
			$.ajax({
				dataType: 'json',
				url: '${stadisticsReportURL}',
				action :  "${action}",
			    cache:false,
				data: {
					UUID : uuid
					},
				success: function(data){
					if(data){						
				    	if(!data.threadF){		
				    		$('#<portlet:namespace />generating_report').removeClass("aui-helper-hidden");
				    		$('#<portlet:namespace />download_report').addClass("aui-helper-hidden");
				    		setTimeout(<portlet:namespace />readThreadState(uuid),2000);
				    		
				    	}else{	
				    		//location.href='${exportResourceURL}&file=' + data.file + '&contentType=' + data.contentType + '&UUID=' + data.UUID + '&action='+data.action;
				    		$('#<portlet:namespace />download_report').empty();
							$('#<portlet:namespace />generating_report').addClass("aui-helper-hidden");
							var downloadReport = Liferay.Language.get('download-report');
							$('#<portlet:namespace />download_report').append('<button type="button" id="link_download" onClick="javascript:<portlet:namespace />downloadReport(\'${stadisticsReportURL}&file=' + data.file +'&fileName=' + data.fileName + '&contentType=' + data.contentType + '&UUID=' + data.UUID + '&action='+data.action+'\'); " >'+downloadReport+'</button>');
							$('#<portlet:namespace />download_report').removeClass("aui-helper-hidden");
				    	}
					}else{
						alert("Error en el readThreadState");
					}
				},
				error: function(){		
					$('#<portlet:namespace />generating_report').addClass("aui-helper-hidden");
		    		$('#<portlet:namespace />generate_report').removeClass("aui-helper-hidden");
				}
			});		
		}	
	</script>