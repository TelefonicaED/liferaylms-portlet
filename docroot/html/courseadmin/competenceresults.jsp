
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.lms.model.CourseCompetence"%>
<%@page import="com.liferay.lms.service.CourseCompetenceLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseOccur"%>
<%@page import="com.liferay.portal.kernel.search.Sort"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="javax.activation.FileDataSource"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQuery"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.search.SearchContextFactory"%>
<%@page import="com.liferay.portal.kernel.search.SearchEngineUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>

<%@include file="/init.jsp" %>

<%

long courseId=ParamUtil.getLong(request, "courseId",0);
boolean condition=ParamUtil.getBoolean(request, "condition",false);
Course course=CourseLocalServiceUtil.getCourse(courseId);
String tab = ParamUtil.getString(request, "tabs1","0");
String tabs1 = ParamUtil.getString(request, "tabs1","0");
String search = ParamUtil.getString(request, "any-term","");

if(tabs1.equals("1")){
	tabs1 = LanguageUtil.get(pageContext,"competences.necessary");
}else{
	tabs1 = LanguageUtil.get(pageContext,"competences.assigned");
}


%>
<liferay-portlet:renderURL var="backURL" >
	<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
	<portlet:param name="tabs1" value="<%=tabs1 %>" />
	<portlet:param name="view" value="competence-tab" />
</liferay-portlet:renderURL>

<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>

<liferay-portlet:renderURL var="buscarURL">
	<liferay-portlet:param name="view" value="competence-results" />
	<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
	<portlet:param name="condition" value="<%=String.valueOf(condition) %>" />
	<portlet:param name="tabs1" value="<%=tab %>" />
</liferay-portlet:renderURL>

<div class="npa_search_user"> 
<aui:form name="busqusu" role="search" action="<%=buscarURL %>" method="post">
	<aui:fieldset>
		
		<aui:input name="searchForm" type="hidden" value="true" />	
					
			<aui:column>	
				<aui:input label="freetext" name="any-term" size="20" value="" />
				<aui:button-row>
					<aui:button name="searchUsers" value="search" type="submit" />
				</aui:button-row>
			</aui:column>
		
	</aui:fieldset>
</aui:form>
</div>

<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("view","competence-results");
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("condition",String.valueOf(condition));
portletURL.setParameter("tabs1",tabs1);

%>

<liferay-ui:search-container iteratorURL="<%=portletURL %>"
   		emptyResultsMessage="there-are-no-results"
   		delta="10" deltaConfigurable="true" >
   	<liferay-ui:search-container-results>
   		<%
   			SearchContext sc = SearchContextFactory.getInstance(request);
   			//sc.setCompanyId(themeDisplay.getCompanyId());
   			//sc.setEntryClassNames(new String[] {Competence.class.getName()});

			//Indexer indexer = IndexerRegistryUtil.getIndexer(Competence.class.getName());
			//sc.setSearchEngineId(indexer.getSearchEngineId());
			
			BooleanQuery generalContent = BooleanQueryFactoryUtil.create(sc);
			generalContent.addRequiredTerm(Field.ENTRY_CLASS_NAME, Competence.class.getName());

			BooleanQuery groupQuery = BooleanQueryFactoryUtil.create(sc);
			groupQuery.addTerm(Field.GROUP_ID, course.getGroupId());
			groupQuery.addTerm(Field.GROUP_ID, course.getGroupCreatedId());

			if(!search.isEmpty()){
				//BooleanQuery contextTittle = BooleanQueryFactoryUtil.create(sc);

				BooleanQuery contextContent = BooleanQueryFactoryUtil.create(sc);
				contextContent.addTerm(Field.CONTENT, search+"*");
				contextContent.addTerm(Field.TITLE, search+"*");
			
				//generalContent.add(contextTittle, BooleanClauseOccur.SHOULD);
				generalContent.add(contextContent, BooleanClauseOccur.MUST);
			}
			
			generalContent.add(groupQuery, BooleanClauseOccur.MUST);

			Sort sort = new Sort(Field.TITLE, Sort.STRING_TYPE, true);
			Hits hits = SearchEngineUtil.search(themeDisplay.getCompanyId(), generalContent, sort,-1,-1);
			
			
			List<CourseCompetence> competencesOld = CourseCompetenceLocalServiceUtil.findBycourseId(courseId, condition);
			Set<Long> set = new HashSet<Long>();
			
			for(CourseCompetence cc : competencesOld){
				set.add(cc.getCompetenceId());
			}
			List<Document> documents = new ArrayList<Document>();
			for(Document doc : hits.getDocs()){
				Long id = Long.parseLong(doc.get(Field.CLASS_PK));
				if(!set.contains(id)){
					documents.add(doc);
				}
			}

			pageContext.setAttribute("results", ListUtil.subList(documents, searchContainer.getStart(), searchContainer.getEnd()) );
		    pageContext.setAttribute("total", documents.size());
   		%>
   	</liferay-ui:search-container-results>
   	<liferay-ui:search-container-row className="com.liferay.portal.kernel.search.Document"
     		keyProperty="UID"
     		modelVar="doc">
		<liferay-ui:search-container-column-text name="competence.label" >
			<%=LocalizationUtil.getLocalization(doc.get(Field.TITLE),themeDisplay.getLanguageId()) %>
		</liferay-ui:search-container-column-text> 
		<liferay-ui:search-container-column-text name="description" >
			<%=LocalizationUtil.getLocalization(doc.get(Field.CONTENT),themeDisplay.getLanguageId()) %>
		</liferay-ui:search-container-column-text> 
		<liferay-ui:search-container-column-text name="options" >
			<liferay-ui:icon-menu align="left" cssClass='lfr-toolbar-button add-button' direction="down" extended="<%= false %>"  message="add" showWhenSingleIcon="<%= false %>">
				<portlet:actionURL var="activateCompetence" name="activateCompetence">
					<portlet:param name="courseId" value="<%=String.valueOf(courseId) %>" />
					<portlet:param name="competenceId" value="<%=doc.get(Field.CLASS_PK) %>" />
					<portlet:param name="condition" value="<%=String.valueOf(condition) %>" />
					<portlet:param name="tab" value="<%=tab %>" />
				</portlet:actionURL>
				<liferay-ui:icon
					image="add" cssClass="newitem2"
					label="<%= true %>"
					message="add"
					url='<%= activateCompetence %>'
				/>
			</liferay-ui:icon-menu>
		</liferay-ui:search-container-column-text> 
    </liferay-ui:search-container-row>
 	<liferay-ui:search-iterator />
</liferay-ui:search-container>