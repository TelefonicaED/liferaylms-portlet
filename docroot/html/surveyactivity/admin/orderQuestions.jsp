<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>

<%
String backUrl = ParamUtil.getString(request, "backUrl", currentURL);

LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
request.setAttribute("activity", learningActivity);

/*
LiferayPortletURL backUrl = PortletURLFactoryUtil.create(request, PortalUtil.getJsSafePortletId("lmsactivitieslist"+
		PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);
backUrl.setWindowState(LiferayWindowState.POP_UP);
backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));	
backUrl.setParameter("resModuleId", String.valueOf(learningActivity.getModuleId()));
backUrl.setParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
*/
request.setAttribute("backUrl", backUrl.toString());
PortletURL orderQuestionsURL = renderResponse.createRenderURL();
orderQuestionsURL.setParameter("jspPage","/html/surveyactivity/admin/orderQuestions.jsp");
orderQuestionsURL.setParameter("resId",Long.toString(learningActivity.getActId()));
orderQuestionsURL.setParameter("actionEditingDetails",StringPool.TRUE);
String orderByCol =  (String)request.getAttribute("orderByCol");
if(orderByCol==null)
	orderByCol="weight";
String orderByType = (String)request.getAttribute("orderByType");
if(orderByType==null)
	orderByType="asc";
%>
<liferay-util:include page="/html/surveyactivity/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<liferay-portlet:actionURL name="moveQuestion" var="moveQuestionURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString()%>" />

<script type="text/javascript">
AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {
    var prueba = A.one('div#<portlet:namespace/>orderableQuestionsSearchContainer > table > tbody > tr.lfr-template');
    if(prueba){
    	prueba.remove();
    }
	new A.Sortable(
		{
			container: A.one('#<portlet:namespace />orderableQuestions > table > tbody'),
		    nodes: 'div#<portlet:namespace/>orderableQuestionsSearchContainer > table > tbody > tr.results-row',
            after: {   
            	'drag:end': function(event){ 
            		
				    var node = event.target.get('node'),
			            prev = node.previous(),
			            next = node.next(),
			            movedPageId = parseInt(node.one('td.col-1').get('id').substr(<%=renderResponse.getNamespace().length()+32 %>),0),
		            	prevPageId = 0,
		            	nextPageId = 0;

			        if(prev){
			          prevPageId = parseInt(prev.one('td.col-1').get('id').substr(<%=renderResponse.getNamespace().length()+32 %>),0);
				    }

			        if(next){
			          nextPageId = parseInt(next.one('td.col-1').get('id').substr(<%=renderResponse.getNamespace().length()+32 %>),0);
				    }
					A.io.request('<%=moveQuestionURL %>', {  
						data: {
				            <portlet:namespace />pageId: movedPageId,
				            <portlet:namespace />prevPageId: prevPageId,
				            <portlet:namespace />nextPageId: nextPageId
				        },
					    dataType : 'html', 
					  on: {  
				  		success: function() {  
							 Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});
				        } 
					   }  
					});    
            	}              
            }
		}
	);
});
<%--
var prueba = A.one('tbody#<portlet:namespace/>questionContainer');
console.log(prueba.get('id'));
new A.Sortable(
		{
			container: A.one('tbody#<portlet:namespace />questionContainer'),
		    nodes: 'tr.results-row',
            after: {   
            	'drag:end': function(event){ 
            		
				    var node = event.target.get('node'),
			            prev = node.previous(),
			            next = node.next(),
			            movedPageId = parseInt(node.get('id').substr(<%="question-".length()%>),0),
		            	prevPageId = 0,
		            	nextPageId = 0;

			        if(prev){
			          prevPageId = parseInt(prev.get('id').substr(<%="question-".length()%>),0);
				    }

			        if(next){
			          nextPageId = parseInt(next.get('id').substr(<%="question-".length()%>),0);
				    }
					console.log(movedPageId);
					console.log(prevPageId);
					console.log(nextPageId);
					A.io.request('<%=moveQuestionURL %>', {  
						data: {
				            <portlet:namespace />pageId: movedPageId,
				            <portlet:namespace />prevPageId: prevPageId,
				            <portlet:namespace />nextPageId: nextPageId
				        },
					    dataType : 'html', 
					  on: {  
				  		success: function() {  
							 Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});
				        } 
					   }  
					});    
            	}              
            }
		}
	);
});	--%>
</script>
 
<liferay-ui:search-container emptyResultsMessage="there-are-no-questions"
 iteratorURL="<%=orderQuestionsURL%>" id="orderableQuestions" orderByCol="<%= orderByCol %>" orderByType="<%= orderByType %>">
	<liferay-ui:search-container-results>
	<%
	List listaAux = (List)request.getAttribute("listaAux");
	if(listaAux==null){
		listaAux = (List<TestQuestion>)TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
	}
	List<TestQuestion>listaTotal = ListUtil.copy(listaAux);
	BeanComparator beanComparator = new BeanComparator(orderByCol);
	if(orderByType.equals("asc")){
		Collections.sort(listaTotal, beanComparator);
	 }
	else {
		Collections.sort(listaTotal, Collections.reverseOrder(beanComparator));
	 }
	pageContext.setAttribute("results", ListUtil.subList(listaTotal,searchContainer.getStart(),searchContainer.getEnd()));
	pageContext.setAttribute("total", listaTotal.size());
	%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.TestQuestion" keyProperty="questionId" modelVar="activity" rowIdProperty="questionId">
		<liferay-ui:search-container-column-text name="text">
		<% String titleQuestion = HtmlUtil.stripHtml(activity.getText());
			if(titleQuestion.length() > 80) titleQuestion = titleQuestion.substring(0, 80) + " ...";%>
			<%=titleQuestion %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="questionType" align="middle">
			<%=(new QuestionTypeRegistry().getQuestionType(activity.getQuestionType())).getTitle(themeDisplay.getLocale()) %>
		</liferay-ui:search-container-column-text>
		
		<liferay-portlet:actionURL var="downQuestionURL" name="downquestion">
			<liferay-portlet:param name="actId" value="<%=String.valueOf(activity.getActId())%>"/>
			<liferay-portlet:param name="questionId" value="<%=String.valueOf(activity.getQuestionId())%>" />
		</liferay-portlet:actionURL>
		<%
		String downURLJavascript=
			"javascript:AUI().use('node','aui-io-request','aui-parse-content', function(A){  "+ 
					 "          A.io.request('"+ downQuestionURL.toString() +"', {  "+
					 "		      dataType : 'html', "+
					 "            on: {  "+
					 "             		success: function() {  "+
					 "						 Liferay.Portlet.refresh(A.one('#p_p_id"+renderResponse.getNamespace()+"'),{'p_t_lifecycle':0,'"+renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY +"':'"+StringPool.TRUE+"'});"+
					 "             }  "+
					 "            }  "+
					 "          });  "+	 
					 "		}); ";
		%>
		<liferay-ui:search-container-column-text cssClass="th_ico_down">
			<liferay-ui:icon image="bottom" message="down" url="#" onClick="<%=downURLJavascript %>"/>
		</liferay-ui:search-container-column-text>
		
		<liferay-portlet:actionURL var="upQuestionURL" name="upquestion">
			<liferay-portlet:param name="actId" value="<%=String.valueOf(activity.getActId())%>"/>
			<liferay-portlet:param name="questionId" value="<%=String.valueOf(activity.getQuestionId())%>" />
		</liferay-portlet:actionURL>
		<%
		String upURLJavascript=
				"javascript:AUI().use('node','aui-io-request','aui-parse-content', function(A){  "+
						 "          A.io.request('"+ upQuestionURL.toString() +"', {  "+
						 "		      dataType : 'html', "+
						 "            on: {  "+
						 "             		success: function() {  "+
						 "						 Liferay.Portlet.refresh(A.one('#p_p_id"+renderResponse.getNamespace()+"'),{'p_t_lifecycle':0,'"+renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY +"':'"+StringPool.TRUE+"'});"+
						 "             		}  "+
						 "	            }  "+
						 "          });  "+	 
						 "		}); ";
		%>		
		<liferay-ui:search-container-column-text cssClass="th_ico_up">
			<liferay-ui:icon image="top" message="up" url="#" onClick="<%=upURLJavascript %>"/>
		</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<%-- 
<table>
	<thead>
		<tr class="portlet-section-header results-header">
			<th class="col-1 col-text-first"><liferay-ui:message key="name" /></th>
			<th class="col-2 col-questiontype"><liferay-ui:message key="questiontype" /></th> 
			<th class="col-3 col-weight last"><liferay-ui:message key="weight" /></th> 
		</tr>
	</thead>
	<tbody id="<portlet:namespace/>questionContainer">
		<%
		int i=0; 
		List listaAux = (List)request.getAttribute("listaAux");
		if(listaAux==null){
			listaAux = (List<TestQuestion>)TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
		}
		List<TestQuestion>listaTotal = ListUtil.copy(listaAux);
		BeanComparator beanComparator = new BeanComparator(orderByCol);
		if(orderByType.equals("asc")){
			Collections.sort(listaTotal, beanComparator);
		 }
		else {
			Collections.sort(listaTotal, Collections.reverseOrder(beanComparator));
		 }
		QuestionTypeRegistry qtr = new QuestionTypeRegistry();
		for(TestQuestion tq : listaTotal){
		%>
		<tr class='<%=((i%2==0)?"portlet-section-body":"portlet-section-alternate") + " results-row" %>' id='<%="question-"+tq.getQuestionId()%>'> 
			<td class="align-left col-1 col-text first valign-middle" colspan="1">
				<p><%=tq.getText()%></p>
			</td>
			<td class="align-left col-2 col-questiontype valign-middle" colspan="1">
				<p><%=qtr.getQuestionType(tq.getQuestionType()).getTitle(locale) %></p>
			</td>
			<td class="align-left col-3 col-weight last sort-column valign-middle" colspan="1">
				<p><%=tq.getWeigth()%></p>
			</td>
		</tr>
		<%
			i++;
		} %>
	</tbody>	
</table>
--%>