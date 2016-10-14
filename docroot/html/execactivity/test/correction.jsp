<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalService"%>
<%@page import="com.liferay.lms.service.impl.TestQuestionLocalServiceImpl"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>

<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@include file="/init.jsp" %>

<liferay-portlet:renderURL var="setGrades" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
							<portlet:param name="ajaxAction" value="setGrades" />      
						   	<portlet:param name="jspPage" value="/html/execactivity/test/admin/popups/grades.jsp" />           
</liferay-portlet:renderURL>
<aui:script>
Liferay.provide(
        window,
        '<portlet:namespace />refreshPortlet',
        function() {
		    <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
            var curPortletBoundaryId = '#p_p_id<portlet:namespace />showPopupActivity';
        
            Liferay.Portlet.refresh(curPortletBoundaryId);
        },
        ['aui-dialog','aui-dialog-iframe']
    );
    
</aui:script>

<script type="text/javascript">

function <portlet:namespace />showPopupActivity(studentId, actId, actType) {
	
	var url = '/html/gradebook/popups/activity.jsp';
	AUI().use('aui-dialog','liferay-portlet-url', function(A){
		var renderUrl = Liferay.PortletURL.createRenderURL();							
		renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
		renderUrl.setPortletId('<%=portletDisplay.getId()%>');
		renderUrl.setParameter('actId', actId);
		renderUrl.setParameter('studentId', studentId);
		renderUrl.setParameter('actType', actType);
		renderUrl.setParameter('jspPage', url);

		window.<portlet:namespace />popupActivity = new A.Dialog({
			id:'<portlet:namespace />showPopupActivity',
            title: '<liferay-ui:message key="coursestats.modulestats.activity" />',
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
		renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
		renderUrl.setPortletId('<%=portletDisplay.getId()%>');
		renderUrl.setParameter('actId', actId);
		renderUrl.setParameter('studentId', studentId);
		renderUrl.setParameter('jspPage', '/html/execactivity/test/admin/popups/grades.jsp');

		window.<portlet:namespace />popupGrades = new A.Dialog({
			id:'<portlet:namespace />showPopupGrades',
            title: '<liferay-ui:message key="offlinetaskactivity.set.grades" />',
            centered: true,
            modal: true,
            width: 370,
            height: 300,
            after: {   
	          	close: function(event){ 
	          		document.location.reload();
            	}
            }
        }).plug(A.Plugin.IO, {
            uri: renderUrl.toString()
        }).render();
		window.<portlet:namespace />popupGrades.show();   
	});
}


</script>
<%
long actId=ParamUtil.getLong(request, "actId",0);
long courseId=ParamUtil.getLong(request, "courseId",0);
Course course = CourseLocalServiceUtil.getCourse(courseId);
LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);

String firstName = ParamUtil.getString(request, "first-name","");
String lastName = ParamUtil.getString(request, "last-name","");
String screenName = ParamUtil.getString(request, "screen-name","");
String emailAddress = ParamUtil.getString(request, "email-address","");



//List<User> listaUsuarioTotal = UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId());
LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
params.put("usersGroups", new Long(course.getGroupCreatedId())); 
OrderByComparator comparator = null;
boolean andOperator = true;

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/execactivity/test/correction.jsp");
portletURL.setParameter("actId",Long.toString(actId));
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("first-name",firstName);
portletURL.setParameter("last-name",lastName);
portletURL.setParameter("screen-name",screenName);
portletURL.setParameter("email-address",emailAddress);

SearchContainer<User> userSearchContainer = new SearchContainer<User>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
		ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_DELTA_PARAM,SearchContainer.DEFAULT_DELTA), portletURL, 
		null,  LanguageUtil.get(pageContext, "no-results"));

List<User> users = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName,"", lastName,screenName, emailAddress, 
		-1, params, andOperator, userSearchContainer.getStart(),userSearchContainer.getEnd(), comparator);

int totalUsers = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), firstName,"", lastName,screenName, emailAddress, 
		-1, params, andOperator);
userSearchContainer.setResults(users);
userSearchContainer.setTotal(totalUsers);


%>

	<aui:form name="searchFm" action="<%=renderResponse.createRenderURL() %>" method="POST">
		<div class="taglib-search-toggle">
			<div class="taglib-search-toggle-advanced">
				<aui:input type="hidden" name="actId" value="<%=actId %>"/>
				<aui:input type="hidden" name="courseId" value="<%=courseId %>"/>
				<aui:input type="hidden" name="jspPage" value="/html/execactivity/test/correction.jsp"/>
				
				<aui:fieldset>
					<aui:input type="text" name="first-name" value="<%=firstName %>" inlineField="true"  />
					<aui:input type="text" name="last-name" value="<%=lastName %>" inlineField="true"  />
					<aui:input type="text" name="screen-name" value="<%=screenName %>" inlineField="true"  />
					<aui:input type="text" name="email-address" value="<%=lastName %>" inlineField="true"  />
				</aui:fieldset>
				
				<aui:button type="submit" value="search"/>
			</div>
		</div>
	</aui:form>

	<liferay-ui:search-container 
		searchContainer="<%=userSearchContainer %>" 
		iteratorURL="<%=userSearchContainer.getIteratorURL() %>"
		delta="<%=userSearchContainer.getDelta()%>"
		>
					
				<liferay-ui:search-container-results 
					total="<%=userSearchContainer.getTotal() %>" 
					results="<%=userSearchContainer.getResults() %>"
				/>



	<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="u">
		<%
			boolean hasFreeQuestion = false;
			
			List<TestQuestion> questionList = null;
			LearningActivityTry larntry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, u.getUserId());
			if (GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"random"))==0){
				questionList=TestQuestionLocalServiceUtil.getQuestions(actId);
			}
			else{
				questionList= new ArrayList<TestQuestion>();
				
				if(larntry != null && Validator.isNotNull(larntry.getTryResultData())){
					try{
						Iterator<Element> nodeItr = SAXReaderUtil.read(larntry.getTryResultData()).getRootElement().elementIterator();
						while(nodeItr.hasNext()) {
							Element element = nodeItr.next();
					         if("question".equals(element.getName())) {
					        	 questionList.add(TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(element.attributeValue("id"))));
					         }
					    }
					}catch(Exception e){
						e.printStackTrace();
					}
				}	
			}
			
			
			Iterator<TestQuestion> questionListIt = questionList.iterator();
			while(questionListIt.hasNext()){
				TestQuestion q = questionListIt.next();
				if(q.getQuestionType() == 2){
					hasFreeQuestion = true;
					break;
				}
			}
			
			
			
			LearningActivityResult laResult =  LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, u.getUserId());
			String result= "-";
			String status="not-started";
			if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(actId, u.getUserId())){
				status="started";
				LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, u.getUserId());
				if(activity.getTypeId() == 8){
					result= (learningActivityResult!=null)?LearningActivityResultLocalServiceUtil.translateResult(themeDisplay.getLocale(), learningActivityResult.getResult(), activity.getGroupId()):"";
				}else{
					result = ""+laResult.getResult();
				}
				if(learningActivityResult.getEndDate()!=null){
					status="not-passed"	;
				}
				if(learningActivityResult.isPassed()){
					status="passed"	;
				}
			}			
		%>
	
	
		<liferay-ui:search-container-column-text name="name">
			${u.getFullName()}		
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text name="execactivity.manualCorrect">
			
		<%if(hasFreeQuestion){
			if(status.equals("not-started")){
			%>
				<liferay-ui:message key = "editactivity.mandatory.yes"/> / <liferay-ui:message key = "not-started"/>
				
			<%}else{%>
				<liferay-ui:message key = "editactivity.mandatory.yes"/>
			<%}%>		
		
		<%}else{
			if(status.equals("not-started")){%>
				<liferay-ui:message key = "editactivity.mandatory.no"/> / <liferay-ui:message key = "not-started"/>
			<%}else{%>
				<liferay-ui:message key = "editactivity.mandatory.no"/>
			<%}%>
		
		<%}%>
			
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text name="execactivity.notes">
			<%=result %>
			<%
			if(status.equals("passed")){%>
			 	<liferay-ui:icon image="checked" message="passed"></liferay-ui:icon>
			<%} else if(status.equals("not-passed")){%>
			 	<liferay-ui:icon image="close" message="not-passed"></liferay-ui:icon>
			<%} else if(status.equals("started")){%>
		 		<liferay-ui:icon image="unchecked" message="unchecked"></liferay-ui:icon>
		 	<%}
			
 			if(!status.equals("not-started")){
 				if((PermissionCheckerFactoryUtil.create(themeDisplay.getUser())).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){
 					if(status.equals("passed") || status.equals("not-passed") || hasFreeQuestion){
 					%>
		 				<liferay-ui:icon image="edit" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupGrades("+Long.toString(u.getUserId())+","+String.valueOf(activity.getActId())+");" %>' />
			 		<% 
 					}
			 		String typesThatCanBeSeen = "0,3,6";
			 		if(typesThatCanBeSeen.contains(String.valueOf(activity.getTypeId()))){
			 			%>
			 			<liferay-ui:icon image="view" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupActivity("+Long.toString(u.getUserId())+","+String.valueOf(activity.getActId())+","+String.valueOf(activity.getTypeId())+");" %>'/>
			 		<%}
		  		}
 			}%>
		
		</liferay-ui:search-container-column-text>

	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator searchContainer="<%=userSearchContainer %>"/>

</liferay-ui:search-container>
