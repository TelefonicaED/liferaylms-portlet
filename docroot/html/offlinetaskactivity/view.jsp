<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.OfflineActivity"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%@ include file="/html/shared/isTablet.jsp" %>
<%
CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

%>

<liferay-ui:error key="result-bad-format" message="<%=LanguageUtil.format(themeDisplay.getLocale(), \"result.must-be-between\", new Object[]{ct.getMinValue(themeDisplay.getScopeGroupId()),ct.getMaxValue(themeDisplay.getScopeGroupId())})%>" />
<liferay-ui:error key="grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
<liferay-ui:success key="grades.updating" message="offlinetaskactivity.correct.saved" />

<%
	LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
	long actId = ParamUtil.getLong(request,"actId",0);
	int curValue = ParamUtil.getInteger(request,"curValue",1);
	String criteria = request.getParameter("criteria");
	String gradeFilter = request.getParameter("gradeFilter");

	if (criteria == null) criteria = "";	
	if (gradeFilter == null) gradeFilter = "";	
	
	if(actId==0)
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		if(activity.getTypeId() == 5){
			
			if(activity.canAccess(false, themeDisplay.getUser(), themeDisplay.getPermissionChecker())){
				
				LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
				Object  [] arguments=null;
		
				if(result!=null){	
					arguments =  new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), result.getResult())};
				}
		
				boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");	
		
			%>
	
				<div class="offlinetaskactivity view">
	
					<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
											
					<% if(isTeacher){ %>
										
					<portlet:renderURL var="viewUrlPopImportGrades" windowState="<%= LiferayWindowState.POP_UP.toString() %>">   
						<portlet:param name="actId" value="<%=String.valueOf(activity.getActId()) %>" />      
			            <portlet:param name="jspPage" value="/html/offlinetaskactivity/popups/importGrades.jsp" />          
			        </portlet:renderURL>
			        
					<portlet:renderURL var="viewUrlPopGrades" windowState="<%= LiferayWindowState.POP_UP.toString() %>">   
						<portlet:param name="actId" value="<%=String.valueOf(activity.getActId()) %>" />          
			            <portlet:param name="jspPage" value="/html/offlinetaskactivity/popups/grades.jsp" />           
			        </portlet:renderURL>

	
					<script type="text/javascript">
				    <!--
	
	
					    function <portlet:namespace />showPopupImportGrades()
					    {
							AUI().use('aui-dialog','liferay-portlet-url','event', function(A){
								window.<portlet:namespace />popupImportGrades = new A.Dialog({
									id:'<portlet:namespace />showPopupImportGrades',
						            title: '<liferay-message key="offlinetaskactivity.import.grades" />',
							    	centered: true,
						            modal: true,
						            width: 650,
						            height: 600,
						            after: {   
							          	close: function(event){ 
							          		document.getElementById('<portlet:namespace />studentsearch').submit();
						            	}
						            }
						        }).plug(A.Plugin.IO, {
						            uri: '<%= viewUrlPopImportGrades %>'
						        }).render();
								window.<portlet:namespace />popupImportGrades.show();   
							});
					    }
	
					    function <portlet:namespace />doClosePopupImportGrades()
					    {
					        AUI().use('aui-dialog', function(A) {
					        	window.<portlet:namespace />popupImportGrades.close();
					        });
					    }
	
	
					    function <portlet:namespace />doImportGrades()
					    {
							var importGradesDIV=document.getElementById('<portlet:namespace />import_frame').
												contentDocument.getElementById('<portlet:namespace />importErrors');
							if(importGradesDIV){
								document.getElementById('<portlet:namespace />importErrors').innerHTML=importGradesDIV.innerHTML;
							}
							else {
								document.getElementById('<portlet:namespace />importErrors').innerHTML='';
							}
					    }
	
					    function <portlet:namespace />showPopupGrades(studentId)
					    {
	
							AUI().use('aui-dialog','liferay-portlet-url', function(A){
								var renderUrl = Liferay.PortletURL.createRenderURL();							
								renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
								renderUrl.setPortletId('<%=portletDisplay.getId()%>');
								renderUrl.setParameter('actId', '<%=String.valueOf(activity.getActId()) %>');
								renderUrl.setParameter('studentId', studentId);
								renderUrl.setParameter('jspPage', '/html/offlinetaskactivity/popups/grades.jsp');
								renderUrl.setParameter('gradeFilter', '<%= ParamUtil.getString(renderRequest, "gradeFilter","") %>');
								renderUrl.setParameter('criteria', '<%=ParamUtil.getString(renderRequest, "criteria","") %>');
							    renderUrl.setParameter('curValue', <%= String.valueOf(curValue) %> )	
								window.<portlet:namespace />popupGrades = new A.Dialog({
									id:'<portlet:namespace />showPopupGrades',
						            title: '<%=LanguageUtil.format(pageContext, "offlinetaskactivity.set.grades", new Object[]{""})%>',
						            centered: true,
						            modal: true,
						            width: 600,
						            height: 350,
						            /*after: {   
							          	close: function(event){ 
							          		document.getElementById('<portlet:namespace />studentsearch').submit();
						            	}
						            }*/
						        }).plug(A.Plugin.IO, {
						            uri: renderUrl.toString(),
						            parseContent: true
						        }).render();
								window.<portlet:namespace />popupGrades.show();   
							});
					    }
	
					    function <portlet:namespace />doClosePopupGrades()
					    {
					        AUI().use('aui-dialog', function(A) {
					        	window.<portlet:namespace />popupGrades.close();
					        });
					    }

					</script>
	
					<div class="container-toolbar" >
						
						<liferay-ui:icon-menu cssClass='bt_importexport' direction="down" extended="<%= false %>" message="offlinetaskactivity.csv.export.import" showWhenSingleIcon="<%= true %>">
						
							<div>
								<liferay-portlet:resourceURL var="exportURL" >
									<portlet:param name="action" value="export"/>
									<portlet:param name="resId" value="<%=String.valueOf(activity.getActId()) %>"/>
									<portlet:param name="gradeFilter" value="<%=gradeFilter %>"/>
									<portlet:param name="criteria" value="<%=criteria %>"/>
								</liferay-portlet:resourceURL>
								<liferay-ui:icon image="export" label="<%= true %>" message="offlinetaskactivity.csv.export" method="get" url="<%=exportURL%>" />
							</div>
							<div>
								<liferay-ui:icon image="add" label="<%= true %>" message="offlinetaskactivity.import.grades" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupImportGrades();" %>'/>
							</div>
						</liferay-ui:icon-menu>
	
					</div>
					
					<% } %>
					<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
					<div class="description"><%=activity.getDescriptionFiltered(themeDisplay.getLocale(),true) %></div>
					
					
					<% if(isTeacher){ 
						PortletURL portletURL = renderResponse.createRenderURL();
						portletURL.setParameter("jspPage","/html/offlinetaskactivity/view.jsp");
						portletURL.setParameter("criteria", criteria); 
						portletURL.setParameter("cur", String.valueOf(curValue));
						portletURL.setParameter("gradeFilter", gradeFilter);
					
					%>
					
					<liferay-portlet:renderURL var="returnurl" />
					
					<h5><liferay-ui:message key="studentsearch"/></h5>
					<aui:form name="studentsearch" action="<%=returnurl %>" role="form" method="post">
						<aui:fieldset>
							<aui:column>
								<aui:input label="studentsearch.text.criteria" name="criteria" size="25" value="<%=criteria %>" />	
							</aui:column>	
							<aui:column>
								<aui:select label="offlinetaskactivity.status" name="gradeFilter" onchange='<%="document.getElementById(\'" + renderResponse.getNamespace() + "studentsearch\').submit();" %>'>
									<aui:option selected='<%= gradeFilter.equals("") %>' value=""><liferay-ui:message key="offlinetaskactivity.all" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("nocalification") %>' value="nocalification"><liferay-ui:message key="offlinetaskactivity.status.passed" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("passed") %>' value="passed"><liferay-ui:message key="offlinetaskactivity.passed" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("failed") %>' value="failed"><liferay-ui:message key="offlinetaskactivity.failed" /></aui:option>
								</aui:select>
							</aui:column>	
							<aui:column>
								<aui:button cssClass="inline-button" name="searchUsers" value="search" type="submit" />
							</aui:column>
						</aui:fieldset>
					</aui:form>
					
					
					<liferay-ui:search-container iteratorURL="<%=portletURL%>" curParam="curValue" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true">

				   	<liferay-ui:search-container-results>
						<%
						
							List<User> userListsOfCourse = OfflineActivity.getCalificationUsers(themeDisplay, actId, criteria, gradeFilter);
							List<User> userLists =  new ArrayList<User>(userListsOfCourse.size());
							
							for(User userOfCourse:userListsOfCourse){							
								if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){
									userLists.add(userOfCourse);
								}
							}	
							
							pageContext.setAttribute("results", ListUtil.subList(userLists, searchContainer.getStart(), searchContainer.getEnd()));
						    pageContext.setAttribute("total", userLists.size());	
							
						%>
					</liferay-ui:search-container-results>
					
					<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usuario">
					<liferay-ui:search-container-column-text name="name">
						<liferay-ui:user-display userId="<%=usuario.getUserId() %>"></liferay-ui:user-display>
					</liferay-ui:search-container-column-text>
					<liferay-ui:search-container-column-text name="calification">
						<% LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, usuario.getUserId()); 
						   if((learningActivityResult!=null)&&(learningActivityResult.getEndDate()!= null)) {	   
								   Object  [] arg =  new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), learningActivityResult.getResult()),ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),activity.getPasspuntuation())};
								   if(learningActivityResult.getPassed()){
									   %><liferay-ui:message key="offlinetaskactivity.student.passed"  arguments="<%=arg %>" /><%
								   }else {
									   %><liferay-ui:message key="offlinetaskactivity.student.failed"  arguments="<%=arg %>" /><%
								   }
							  
			               	}else{
								   %><liferay-ui:message key="offlinetaskactivity.student.without.qualification" /><% 
							}%>
			            <p class="see-more">
							<a href="javascript:<portlet:namespace />showPopupGrades(<%=Long.toString(usuario.getUserId()) %>);">
								<liferay-ui:message key="offlinetaskactivity.set.grades"/>
							</a>
						</p>
					</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
					
				 	<liferay-ui:search-iterator />
				 	
				</liferay-ui:search-container>
				
				
				<% } %>	
				
				<div class="nota"> 

				<%if(!isTeacher) {%>
					<h3><liferay-ui:message key="offlinetaskactivity.your-calification" /> </h3>
					<%if ((result!=null)&&(result.getEndDate()!=null)){ %>
						<p><liferay-ui:message key="offlinetaskactivity.your-result" arguments="<%=new Object[]{(arguments.length>0) ? arguments[0]:\"\"} %>" /></p>
						<p><liferay-ui:message key="offlinetaskactivity.needed-to-pass" arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), activity.getPasspuntuation())} %>" /></p>
					<%}else {%>
						<div class="nota_nocorregida"><liferay-ui:message key="offlinetaskactivity.not.qualificated.activity" /></div>
					<%}%>
					
					<h3><liferay-ui:message key="offlinetaskactivity.result.teachercoment" /> </h3>
					<%if ((result!=null)&&!"".equals(result.getComments().trim())){ %>
						<p><span class="destacado"><%=result.getComments() %></span></p>
					<% } else if(result==null){%>
						<p><liferay-ui:message key="offlinetaskactivity.no-teacher-comments-yet" /></p>
					<%}else {%>
						<p><liferay-ui:message key="offlinetaskactivity.no-teacher-comments" /></p>
					<%}
						}
					}
						%>
							</div>
						</div>
							<%
		}
	}
%>
</div>
