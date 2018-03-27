<%@ include file="/init.jsp" %>

 
<jsp:useBean id="LiferaylmsUtil" class="com.tls.lms.util.LiferaylmsUtil"/>
<jsp:useBean id="LearningActivityResultLocalServiceUtil" class="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"/>
<jsp:useBean id="LearningActivityLocalServiceUtil" class="com.liferay.lms.service.LearningActivityLocalServiceUtil"/>

<jsp:useBean id="ct" type="com.liferay.lms.learningactivity.calificationtype.CalificationType" scope="request"/>

<liferay-ui:error key="result-bad-format" message="<%=LanguageUtil.format(themeDisplay.getLocale(), \"result.must-be-between\", new Object[]{ct.getMinValue(themeDisplay.getScopeGroupId()),ct.getMaxValue(themeDisplay.getScopeGroupId())})%>" />
<liferay-ui:error key="grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
<liferay-ui:success key="grades.updating" message="offlinetaskactivity.correct.saved" />
<liferay-ui:panel-container > 
		
	<aui:form name="fm" action="${renderURL}" method="post">
	
		<%@ include file="/html/search/userSearch.jsp" %>
		<aui:button-row>
			<aui:button name="searchUsers" value="search" type="submit" />
		</aui:button-row>
	</aui:form>
		
	<c:if test="${not empty team }">
		<liferay-ui:header title="${team.name}" showBackURL="false" localizeTitle="false"/>
	</c:if>
	<c:if test="${not empty modules }">
		<c:set var="fila" value="0"/>
		
		<c:forEach items="${modules }" var="module">
			<liferay-ui:panel id="${module.moduleId }" title="${module.getTitle(themeDisplay.locale) }" collapsible="true" extended="true" defaultState="${fila == 0 ? 'open' : 'collapsed' }">
				<liferay-portlet:resourceURL var="exportURL" >
					<portlet:param name="action" value="export"/>
					<portlet:param name="moduleId" value="${module.moduleId }"/>
					<portlet:param name="teamId" value="${teamId }" />
				</liferay-portlet:resourceURL>
				
				<liferay-ui:icon image="export" label="true" message="offlinetaskactivity.csv.export" method="get" url="${exportURL}" />
	
				<liferay-portlet:renderURL var="returnurl">
					<liferay-portlet:param name="jspPage" value="/html/gradebook/view.jsp" />
				</liferay-portlet:renderURL>
				
				<div class="table-overflow table-absolute">
					<liferay-ui:search-container searchContainer="${searchContainers.get(fila)}"  iteratorURL="${searchContainers.get(fila).iteratorURL}" >
					
						<liferay-ui:search-container-results results="${searchContainers.get(fila).results}" total="${searchContainers.get(fila).total}" />
					
						<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="userSearch">
							<liferay-portlet:renderURL var="userDetailsURL">
								<liferay-portlet:param name="view" value="user-details"/>
								<liferay-portlet:param name="userId" value="${userSearch.userId }"/>
							</liferay-portlet:renderURL>
							
							<liferay-ui:search-container-column-text name="student-name" cssClass="col-absolute-1">
								<liferay-ui:user-display userId="${userSearch.userId}" url="${userDetailsURL}"/>
							</liferay-ui:search-container-column-text>
							
							<c:set var="activities" value="${module.getListVisiblesLearningActivities(themeDisplay.companyId, permissionChecker) }"/>
							
							<c:set var="absoluteClass" value="col-absolute-2" />
							
							<c:forEach items="${activities}" var="learningActivity">
								
								<portlet:renderURL var="viewUrlPopGrades" windowState="<%= LiferayWindowState.POP_UP.toString() %>">   
									<portlet:param name="actId" value="${learningActivity.actId }" />      
								    <portlet:param name="jspPage" value="/html/gradebook/popups/grades.jsp" />           
								</portlet:renderURL>
							
								<liferay-ui:search-container-column-text cssClass="number-column ${absoluteClass }" name = "${learningActivity.getTitle(themeDisplay.getLocale())}" 
									align="center">
									
									<c:set var="learningActivityResult" value="${LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.actId, userSearch.userId) }" />
									
									<c:if test="${not empty learningActivityResult }">
										${ct.translate(themeDisplay.locale, themeDisplay.scopeGroupId, learningActivityResult.result)}
									</c:if>
									<c:if test="${empty learningActivityResult }">
										-
									</c:if>
									
									<c:choose>
										<c:when test="${not empty learningActivityResult && learningActivityResult.passed }">
											<liferay-ui:icon image="checked" message="passed"/>
										</c:when>
										<c:when test="${not empty learningActivityResult && not empty learningActivityResult.endDate && !learningActivityResult.passed }">
											<liferay-ui:icon image="close" message="not-passed"/>
										</c:when>
										<c:when test="${not empty learningActivityResult }">
											<liferay-ui:icon image="unchecked" message="unchecked"/>
										</c:when>
									</c:choose>
									<c:if test="${not empty learningActivityResult && permissionChecker.hasPermission(themeDisplay.scopeGroupId, 'com.liferay.lms.model', themeDisplay.scopeGroupId, 'VIEW_RESULTS') }">
										<c:if test="${not empty learningActivityResult.endDate }">
											<liferay-ui:icon image="edit" url='javascript:${renderResponse.namespace}showPopupGrades("${userSearch.userId }","${learningActivity.actId}");' />
										</c:if>
										<c:set var="learningActivityType" value="${learningActivity.getLearningActivityType() }" />
										<c:if test="${learningActivityType.canBeSeenResults() }">
											<liferay-ui:icon image="view" url='javascript:${renderResponse.namespace}showPopupActivity("${userSearch.userId }","${learningActivity.actId}","${learningActivity.typeId}");'/>
										</c:if>
									</c:if>
								</liferay-ui:search-container-column-text>
								<c:set var="absoluteClass" value="col-absolute" />
							</c:forEach>
						</liferay-ui:search-container-row>
					 	<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</div>
			</liferay-ui:panel>
			<c:set var="fila" value="${fila+1 }"/>
		</c:forEach>
	</c:if>
</liferay-ui:panel-container>
<script type="text/javascript">
	function <portlet:namespace />showPopupActivity(studentId, actId, actType) {
		
		var url = '/html/gradebook/popups/activity.jsp';
		AUI().use('aui-dialog','liferay-portlet-url', function(A){
			var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
			renderUrl.setPortletId('<%=portletDisplay.getId()%>');
			renderUrl.setParameter('actId', actId);
			renderUrl.setParameter('studentId', studentId);
			renderUrl.setParameter('actType', actType);
			renderUrl.setParameter('jspPage', url);
			window.<portlet:namespace />popupActivity = new A.Dialog({
				id:'<portlet:namespace />showPopupActivity',
	            title: Liferay.Language.get("coursestats.modulestats.activity"),
	            centered: true,
	            modal: true,
	            width: 700,
	            height: 800,
	            after: {   
		          	close: function(event){ 
		          		document.location.reload();
	            	}
	            }
	        }).plug(A.Plugin.IO, {
	            uri: renderUrl.toString()
	        }).render();
			window.<portlet:namespace />popupActivity.show();   
		});
    }
	
	function <portlet:namespace />doClosePopupActivity(){
	    AUI().use('aui-dialog', function(A) {
	    	window.<portlet:namespace />popupActivity.close();
	    });
	}
	
	function <portlet:namespace />showPopupGrades(studentId, actId) {
		AUI().use('aui-dialog','liferay-portlet-url', function(A){
			var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
			renderUrl.setPortletId('<%=portletDisplay.getId()%>');
			renderUrl.setParameter('actId', actId);
			renderUrl.setParameter('studentId', studentId);
			renderUrl.setParameter('jspPage', '/html/gradebook/popups/grades.jsp');
			window.<portlet:namespace />popupGrades = new A.Dialog({
				id:'<portlet:namespace />showPopupGrades',
	            title: Liferay.Language.get("offlinetaskactivity.set.grades"),
	            centered: true,
	            modal: true,
	            width: 370,
	            height: 300
	        }).plug(A.Plugin.IO, {
	            uri: renderUrl.toString()
	        }).render();
			window.<portlet:namespace />popupGrades.show();   
		});
    }
    
	function <portlet:namespace />doClosePopupGrades(){
	    AUI().use('aui-dialog', function(A) {
	    	window.<portlet:namespace />popupGrades.close();
	    });
	}
</script>