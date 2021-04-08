<%@page import="com.liferay.lms.CourseTypeFactoryRegistry"%>
<%@ include file="/init.jsp" %>


<liferay-ui:success key="coursetypeadmin.success.delete" message="coursetypeadmin.success.delete" />
<liferay-ui:error key="coursetypeadmin.error.delete" message="coursetypeadmin.error.delete" />
<liferay-ui:error key="coursetypeadmin.error.coursetype-not-found" message="coursetypeadmin.error.coursetype-not-found" />
<liferay-ui:success key="coursetypeadmin.success.add-new-coursetype" message="coursetypeadmin.success.add-new-coursetype" />
<liferay-ui:success key="coursetypeadmin.success.update-coursetype" message="coursetypeadmin.success.update-coursetype" />

<aui:button-row>
	<aui:button type="button" value="coursetypeadmin.new-coursetype" onClick="${editCourseTypeURL }"/>
</aui:button-row>


<liferay-ui:search-container
	searchContainer="${searchContainer }"
	iteratorURL="${searchContainer.iteratorURL}" >

	 <liferay-ui:search-container-results 
		 total="${searchContainer.total}" 
		 results="${searchContainer.results}"
		/>
	
	 <liferay-ui:search-container-row 
	 	modelVar="courseType" 
		className="com.liferay.lms.model.CourseType"
		keyProperty="courseTypeId" >
		
		<liferay-ui:search-container-column-text name="name">
			${courseType.getName(themeDisplay.locale) }
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="type">
			<%
			CourseTypeFactoryRegistry registry = new CourseTypeFactoryRegistry();
			if(registry.getCourseTypeFactory(courseType.getClassNameId())!=null){
				%>
				<%= registry.getCourseTypeFactory(courseType.getClassNameId()).getTitle(locale)%>
				<% 	
			}else{
				
			}%>
			
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="">
			<liferay-ui:icon-menu showExpanded="false" showWhenSingleIcon="true">
				<c:set var="actionURL" value="javascript:${renderResponse.getNamespace()}editCourseType('${courseType.courseTypeId }');"/>
					 <liferay-ui:icon 
 						image="edit" 
 					 	url="${actionURL}" /> 
 				<c:if test="${!courseType.hasCourses() && courseType.classNameId == 0}">
					<c:set var="actionURL" value="javascript:${renderResponse.getNamespace()}deleteCourseType('${courseType.courseTypeId }');"/> 
	 				   <liferay-ui:icon 
	 						image="delete" 
	 						url="${actionURL}" /> 
 				</c:if>
 				<c:if test="${courseType.active }">
 					<portlet:actionURL name="saveCourseTypeActive" var="saveCourseTypeActiveURL">
						<portlet:param name="courseTypeId" value="${courseType.courseTypeId }" />
						<portlet:param name="active" value="false" />
					</portlet:actionURL>
					<liferay-ui:icon 
	 						image="inactive" 
	 						url="${saveCourseTypeActiveURL}" /> 
 				</c:if>
 				<c:if test="${!courseType.active }">
 					<portlet:actionURL name="saveCourseTypeActive" var="saveCourseTypeActiveURL">
						<portlet:param name="courseTypeId" value="${courseType.courseTypeId }" />
						<portlet:param name="active" value="true" />
					</portlet:actionURL>
					<liferay-ui:icon 
	 						image="active" 
	 						url="${saveCourseTypeActiveURL}" /> 
 				</c:if>
 			</liferay-ui:icon-menu> 
		</liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />

</liferay-ui:search-container>

<aui:form name="fmActions" action="" role="form" method="POST">
	<aui:input type="hidden" name="courseTypeId" value=""/>
</aui:form>

<script>
	function <portlet:namespace />editCourseType(courseTypeId){
		document.getElementById('<portlet:namespace />courseTypeId').value=courseTypeId;
		document.getElementById('<portlet:namespace />fmActions').action='${editCourseTypeURL }';
		document.getElementById('<portlet:namespace />fmActions').submit();
	}
	function <portlet:namespace />deleteCourseType(courseTypeId){
		if(confirm(Liferay.Language.get("coursetypeadmin.confirm.delete"))){
			document.getElementById('<portlet:namespace />courseTypeId').value=courseTypeId;
			document.getElementById('<portlet:namespace />fmActions').action='${deleteCourseTypeURL }';
			document.getElementById('<portlet:namespace />fmActions').submit();
		}
	}
</script>
	