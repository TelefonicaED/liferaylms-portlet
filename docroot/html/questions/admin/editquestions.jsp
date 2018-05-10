<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.ExecActivity"%>
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
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>

<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);

	LiferayPortletURL backUrl = null;

	if(learningActivity.getModuleId()>0){
		backUrl = PortletURLFactoryUtil.create(request, PortalUtil.getJsSafePortletId("editactivity"+
				PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		backUrl.setWindowState(LiferayWindowState.NORMAL);
		backUrl.setParameter("actId", String.valueOf(learningActivity.getActId()));
		backUrl.setParameter("actionEditingActivity", StringPool.TRUE);
		backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));	
		backUrl.setParameter("resModuleId", String.valueOf(learningActivity.getModuleId()));
		backUrl.setParameter("jspPage", "/html/editactivity/editactivity.jsp");
		request.setAttribute("backUrl", backUrl.toString());
		
	}else{
		AssetRendererFactory laf = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(learningActivity.getActId(), 0);

			String urlEdit = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse).toString();
			request.setAttribute("backUrl", urlEdit);
		}
	}
	

	
	String orderByCol =  (String)request.getAttribute("orderByCol");
	if(orderByCol==null)
		orderByCol="weight";
	String orderByType = (String)request.getAttribute("orderByType");
	if(orderByType==null)
		orderByType="asc";
	TestQuestionLocalServiceUtil.checkWeights(learningActivity.getActId());
	
%>
<liferay-util:include page="/html/questions/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<script type="text/javascript">
			
	Liferay.provide(
        window,
        '<portlet:namespace />newQuestion',
        function(typeId) {
			var renderUrl = Liferay.PortletURL.createRenderURL();	
			<% if(learningActivity.getModuleId()>0){ %>
				renderUrl.setWindowState('<%= LiferayWindowState.NORMAL.toString() %>');
			<% }else{ %>
				renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
			<% } %>
			
			renderUrl.setPortletId('<%=themeDisplay.getPortletDisplay().getId()%>');
			renderUrl.setParameter('jspPage','/html/questions/admin/editQuestion.jsp');
			renderUrl.setParameter('questionTypeId', typeId);
			renderUrl.setParameter('message', Liferay.Language.get('execactivity.editquestions.newquestion'));
			renderUrl.setParameter('actionEditingDetails', true);
			renderUrl.setParameter('resId', "<%=String.valueOf(learningActivity.getActId()) %>");
			renderUrl.setParameter('backUrl', '<%=currentURL %>');
			location.href=renderUrl.toString();
        },
        ['liferay-portlet-url']
    );
	
</script>
<div class="container-toolbar">
	<liferay-ui:icon-menu  align="left" direction="down" extended="false" showWhenSingleIcon="false" message="execativity.editquestions.newquestion" cssClass="bt_new" showArrow="true" >
	</liferay-ui:icon-menu>
	
	<%
		boolean isMultiple = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"isMultiple"));
		LearningActivityType activityType = new LearningActivityTypeRegistry().getLearningActivityType(learningActivity.getTypeId());
		long searchGroupId = themeDisplay.getCompanyGroupId();
		boolean allowsBank = activityType.allowsBank();
		if(allowsBank){
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
			boolean isCourse = true;
			if (course==null){
				isCourse = false;
			}
			boolean useBank = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"isBank"));
			
			if(isCourse){
				searchGroupId = course.getGroupId();
			}
	%>
			<c:if test="<%=isCourse%>">
				<script type="text/javascript">
					function modifyIsBank(value) {
						var A = AUI();
						if (value==='true'){
							A.all('#<portlet:namespace />normal-questions').hide();
							A.one('#<portlet:namespace />banks-questions').setStyle('display', 'block');
							A.one('.portlet-msg-info').setStyle('display', 'none');
						}else{
							A.all('#<portlet:namespace />normal-questions').show();
							A.one('#<portlet:namespace />banks-questions').setStyle('display', 'none');
							A.one('.portlet-msg-info').setStyle('display', 'block');
						}
					}
				</script>
				<div class="banks-support">
					<aui:select id="banks-support" cssClass="banks-support" name="banks-support" label="execativity.editquestions.banksquestions" onChange="modifyIsBank(this.value)">
						<aui:option value="false" selected="<%=!useBank %>"><liferay-ui:message key='editactivity.mandatory.no'/></aui:option>
						<aui:option value="true" selected="<%=useBank %>"><liferay-ui:message key='editactivity.mandatory.yes'/></aui:option>
					</aui:select>
				</div>
				<c:if test="<%=useBank%>">
					<script>
						AUI().ready(function(A) {
							A.all('#<portlet:namespace />normal-questions').hide();
							A.one('#<portlet:namespace />banks-questions').setStyle('display', 'block');
							A.one('.portlet-msg-info').setStyle('display', 'none');
						});
					</script>
				</c:if>
			</c:if>
		<%} %>
	<div id="<portlet:namespace />normal-questions">
		<liferay-ui:icon-menu  align="left" direction="down" extended="false" showWhenSingleIcon="false" message="execativity.editquestions.newquestion" cssClass="bt_new" showArrow="true">
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
				<portlet:param name="jspPage" value="/html/questions/admin/importquestions.jsp"></portlet:param>
			</portlet:renderURL>
			<liferay-ui:icon image="add" label="<%= true %>" message="execativity.editquestions.importquestions" url='<%= importquestionsURL %>'/>
			
			<liferay-portlet:resourceURL var="exportResultsCsvURL" >
				<portlet:param name="action" value="exportResultsCsv"/>
				<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
			</liferay-portlet:resourceURL>
			<liferay-ui:icon image="export" label="<%= true %>" message="execativity.editquestions.exportcsv" method="get" url="<%=exportResultsCsvURL%>" />
			
			<liferay-portlet:resourceURL var="exportXmlURL" >
				<portlet:param name="action" value="exportXml"/>
				<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
			</liferay-portlet:resourceURL>
			<liferay-ui:icon image="export" label="<%= true %>" message="execativity.editquestions.exportXml" method="get" url="<%=exportXmlURL%>" />
		</liferay-ui:icon-menu>
	</div>
	
	<c:if test="<%=allowsBank %>">
		<%String CategoryIds = LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"categoriesId"); %>
		<div id="<portlet:namespace />banks-questions" style="display:none">
		
			<portlet:actionURL var="setBankTestURL" name="setBankTest" >
				<portlet:param name="editing" value="<%=StringPool.TRUE %>"/>
			</portlet:actionURL>
			<aui:form name='fm' action="<%=setBankTestURL%>" method='post'>
				<aui:input name="redirect" type="hidden" value="<%=currentURL %>" />
				<aui:input name="is-bank" type="hidden" value="true"/>
				<aui:input name="actId" type="hidden" value="<%=Long.toString(learningActivity.getActId()) %>"/>
				<aui:field-wrapper label="execativity.editquestions.banks.categoryselections" helpMessage="execativity.editquestions.banks.categoryselections">
					<%@ include file="/html/questions/admin/catselector.jspf" %>
				</aui:field-wrapper>
				<aui:input id="<portlet:namespace />banks-multipleselections" name="banks-multipleselections" checked="<%=isMultiple %>" type="checkbox" 
					label="execativity.editquestions.banks.multipleselections" helpMessage="execativity.editquestions.banks.multipleselections.helpmessage"  ignoreRequestValue="true" />
				<aui:button type="submit" value="courseadmin.importuserrole.save"/>
			</aui:form>
		</div>
	</c:if>
</div>
<%
	PortletURL editQuestionsURL = renderResponse.createRenderURL();
	editQuestionsURL.setParameter("jspPage","/html/questions/admin/editquestions.jsp");
	editQuestionsURL.setParameter("resId",Long.toString(learningActivity.getActId()));
	editQuestionsURL.setParameter("actionEditingDetails",StringPool.TRUE);
%>
<div id="<portlet:namespace />normal-questions">
	<liferay-ui:search-container emptyResultsMessage="there-are-no-questions" delta="10" iteratorURL="<%=editQuestionsURL%>">
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
		<liferay-ui:search-container-row className="com.liferay.lms.model.TestQuestion" keyProperty="questionId" modelVar="activity">
			<liferay-ui:search-container-column-text name="text">
			<% String titleQuestion = HtmlUtil.stripHtml(activity.getText());
				if(titleQuestion.length() > 80) titleQuestion = titleQuestion.substring(0, 80) + " ...";%>
				<%=titleQuestion %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="questionType">
				<%=(new QuestionTypeRegistry().getQuestionType(activity.getQuestionType())).getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-jsp path="/html/questions/admin/admin_actions.jsp" align="right"/>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>

<liferay-ui:error key="error-selector-categories-empty" message="execativity.editquestions.banks.error.caterogiesempty" />
<liferay-ui:error key="error-not-results" message="execativity.editquestions.banks.error.not-results" />
<liferay-ui:success key="data-exist-for-these-categories" message="activity-saved-successfully" />

<aui:button-row cssClass="buttons_content">
<%
	List<TestQuestion> lista = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
	Boolean showOrderQuestions = (Boolean)request.getAttribute("showOrderQuestions");
	if(lista.size()>=2 && (showOrderQuestions != null && showOrderQuestions)){ %>	

		<liferay-portlet:renderURL var="orderURL" >
			<liferay-portlet:param name="mvcPath" value="/html/questions/admin/orderQuestions.jsp" />
			<liferay-portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
			<liferay-portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />
			<liferay-portlet:param name="backUrl" value="<%= currentURL %>"/>
		</liferay-portlet:renderURL>
		<aui:button name="order" href="<%=orderURL%>" value="execativity.editquestions.orderquestions"></aui:button>
<%
	} 
%>
	<liferay-util:include page="/html/questions/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
</aui:button-row>