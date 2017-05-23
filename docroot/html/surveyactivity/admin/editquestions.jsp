<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%

	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);

	LiferayPortletURL backUrl = PortletURLFactoryUtil.create(request, PortalUtil.getJsSafePortletId("editactivity"+
				PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);
	backUrl.setWindowState(LiferayWindowState.NORMAL);
	backUrl.setParameter("actionEditingActivity", StringPool.TRUE);
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));	
	backUrl.setParameter("resModuleId", String.valueOf(learningActivity.getModuleId()));
	backUrl.setParameter("jspPage", "/html/editactivity/editactivity.jsp");
	request.setAttribute("backUrl", backUrl.toString());
	
	TestQuestionLocalServiceUtil.checkWeights(learningActivity.getActId());

	String orderByType = (String)request.getAttribute("orderByType");
	if(orderByType==null) {
		orderByType="asc";
	}
%>
<liferay-util:include page="/html/surveyactivity/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<script type="text/javascript">
<!--
AUI().ready(function(A) {
	A.one('.newitem2 a').focus();
	A.one("#<portlet:namespace/>TabsBack").scrollIntoView();
});
//-->
			
Liferay.provide(
        window,
        '<portlet:namespace />newQuestion',
        function(typeId) {
			var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%= LiferayWindowState.NORMAL.toString() %>');
			renderUrl.setPortletId('surveyactivity_WAR_liferaylmsportlet');
			renderUrl.setParameter('jspPage','/html/surveyactivity/admin/editquestion.jsp');
			renderUrl.setParameter('questionTypeId', typeId);
			renderUrl.setParameter('message', Liferay.Language.get('surveyactivity.editquestions.newquestion'));
			renderUrl.setParameter('actionEditingDetails', true);
			renderUrl.setParameter('resId', "<%=String.valueOf(learningActivity.getActId()) %>");
			renderUrl.setParameter('backUrl', '<%= currentURL %>');
			location.href=renderUrl.toString();
        },
        ['liferay-portlet-url']
    );
	
</script>
<div class="container-toolbar">
	<liferay-ui:icon-menu align="left" direction="down" extended="false" showExpanded="false" showWhenSingleIcon="false" message="execativity.editquestions.newquestion" cssClass="bt_new" showArrow="false">
	<%
		List<QuestionType> qtypes = new QuestionTypeRegistry().getQuestionTypes();
		String[] allowedTypes = PropsUtil.getArray("lms.questions.allowed.for."+learningActivity.getTypeId());
	 	List<String> allowedTypesList = new ArrayList<String>();
		if (allowedTypes != null) {
			allowedTypesList = ListUtil.fromArray(allowedTypes);
		}
		for(QuestionType qt:qtypes){
			if (allowedTypesList.contains(String.valueOf(qt.getTypeId()))) {
	%>
		<liferay-ui:icon message="<%=qt.getTitle(themeDisplay.getLocale()) %>" url="#" onClick="<%=renderResponse.getNamespace()+\"newQuestion(\"+qt.getTypeId()+\");\" %>"/>
	<%	
			}
		}
	%>
	</liferay-ui:icon-menu>
	<liferay-ui:icon-menu align="right" direction="down" extended="false" showWhenSingleIcon="false" cssClass='bt_importexport' message="import-export" showArrow="true">
		<portlet:renderURL var="importquestionsURL">
			<portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
			<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>"></portlet:param>	
			<portlet:param name="jspPage" value="/html/surveyactivity/admin/importquestions.jsp"></portlet:param>
		</portlet:renderURL>
		<liferay-ui:icon
		image="add"
		label="<%= true %>"
		message="surveyactivity.editquestions.importquestions"
		url='<%= importquestionsURL %>'
		/>
		<portlet:renderURL var="importquestionsXmlURL">
			<portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
			<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>"></portlet:param>	
			<portlet:param name="jspPage" value="/html/surveyactivity/admin/importquestionsXml.jsp"></portlet:param>
		</portlet:renderURL>
		<liferay-ui:icon
		image="add"
		label="<%= true %>"
		message="surveyactivity.editquestions.importquestions.xml"
		url='<%= importquestionsXmlURL %>'
		/>
		
		<liferay-portlet:resourceURL var="exportResultsCsvURL" >
			<portlet:param name="action" value="export"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon image="export" label="<%= true %>" message="execativity.editquestions.exportcsv" method="get" url="<%=exportResultsCsvURL%>" />
		
		<liferay-portlet:resourceURL var="exportURL" >
			<portlet:param name="action" value="exportQuestions"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon image="export" label="<%= true %>" message="surveyactivity.editquestions.exportcsv" method="get" url="<%=exportURL%>" />
		
		<liferay-portlet:resourceURL var="exportXmlURL" >
			<portlet:param name="action" value="exportXml"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon image="export" label="<%= true %>" message="surveyactivity.editquestions.exportXml" method="get" url="<%=exportXmlURL%>" />
		
	</liferay-ui:icon-menu>
</div>
<%
	PortletURL editQuestionsURL = renderResponse.createRenderURL();
	editQuestionsURL.setParameter("jspPage","/html/surveyactivity/admin/editquestions.jsp");
	editQuestionsURL.setParameter("resId",Long.toString(learningActivity.getActId()));
	editQuestionsURL.setParameter("actionEditingDetails",StringPool.TRUE);
%>
<liferay-ui:search-container emptyResultsMessage="there-are-no-questions"
 delta="10" iteratorURL="<%=editQuestionsURL%>">
	<liferay-ui:search-container-results>
	<%
	List listaAux = (List)request.getAttribute("listaAux");
	if(listaAux==null){
		listaAux = (List<TestQuestion>)TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
	}
	List<TestQuestion>listaTotal = ListUtil.copy(listaAux);
	BeanComparator beanComparator = new BeanComparator("weight");
	if(orderByType.equals("asc")){
		Collections.sort(listaTotal, beanComparator);
	 }
	else {
		Collections.sort(listaTotal, Collections.reverseOrder(beanComparator));
	 }
	pageContext.setAttribute("results", ListUtil.subList(listaTotal,searchContainer.getStart(),
			searchContainer.getEnd()));
	pageContext.setAttribute("total", listaTotal.size());
	%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.TestQuestion" keyProperty="actId" modelVar="activity">
		<liferay-ui:search-container-column-text name="text">
		<% String titleQuestion = HtmlUtil.stripHtml(activity.getText());
			if(titleQuestion.length() > 80) titleQuestion = titleQuestion.substring(0, 80) + " ...";%>
			<%=titleQuestion %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-jsp path="/html/surveyactivity/admin/admin_actions.jsp" align="right"/>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:button-row cssClass="buttons_content">
<%
List<TestQuestion> lista = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
if(lista.size()>=2){ %>	
<liferay-portlet:renderURL var="orderURL" portletName="surveyactivity_WAR_liferaylmsportlet">
		<liferay-portlet:param name="mvcPath" value="/html/surveyactivity/admin/orderQuestions.jsp" />
		<liferay-portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
		<liferay-portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />
		<liferay-portlet:param name="backUrl" value="<%= currentURL %>"/>
	</liferay-portlet:renderURL>
	<aui:button name="order" href="<%=orderURL%>" value="execativity.editquestions.orderquestions"></aui:button>
	<% } %>
	<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
</aui:button-row>