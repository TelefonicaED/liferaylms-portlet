
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.lms.EvaluationAvgPortlet"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>

<%@ include file="/init.jsp" %>
<%@ include file="/html/shared/isTablet.jsp" %>



<%
CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

%>

<liferay-ui:error key="result-bad-format" message="<%=LanguageUtil.format(themeDisplay.getLocale(), \"result.must-be-between\", new Object[]{ct.getMinValue(themeDisplay.getScopeGroupId()),ct.getMaxValue(themeDisplay.getScopeGroupId())})%>" />
<liferay-ui:error key="grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
<liferay-ui:success key="grades.updating" message="offlinetaskactivity.correct.saved" />



<%
long actId = ParamUtil.getLong(request,"actId",0);
String returnToFullPageURL = renderRequest.getParameter("returnToFullPageURL");

if(actId==0){
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}else{
	
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=learningActivity.getTypeId();
	if(typeId == 8){
		if(learningActivity.canAccess(false, themeDisplay.getUser(), themeDisplay.getPermissionChecker())){
	
			Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
			
			
			boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
			boolean hasFiredDate=false;
			boolean hasPublishDate=false;
			boolean hasActivities=false;
			try{
				if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {	
					Element rootElement = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement();
					hasFiredDate = rootElement.element("firedDate")!=null;
					hasPublishDate = rootElement.element("publishDate")!=null;
					hasActivities = rootElement.element("activities")!=null;
				}
			}catch(Throwable e){}
	%>
				<div class="evaluationAvg view">
					<h2 class="description-title"><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
					<c:if test="<%=Validator.isNotNull(learningActivity.getDescription(themeDisplay.getLocale())) %>">
						<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
						<div class="description"><%=learningActivity.getDescription(themeDisplay.getLocale()) %></div>
					</c:if>
			<%if(isTeacher){ 
				%>
					<portlet:renderURL var="viewEvaluationsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
			        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
			        	<c:if test="<%=WindowState.MAXIMIZED.equals(renderRequest.getWindowState()) %>">
			        		<portlet:param name="showPassPuntuation" value="<%=StringPool.TRUE %>" /> 
			        	</c:if>
			            <portlet:param name="jspPage" value="/html/evaluationtaskactivity/popups/activities.jsp" />           
			        </portlet:renderURL>
			        <portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
			        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
						<portlet:param name="ajaxAction" value="setGrades" />      
			            <portlet:param name="jspPage" value="/html/evaluationtaskactivity/popups/grades.jsp" />           
			        </portlet:renderURL>
			        
			        <div id="<portlet:namespace />calculateContents" class="aui-helper-hidden">
			        	<portlet:actionURL name="update" var="updateURL" >
		        		<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
		        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
		        		</c:if>
			        	</portlet:actionURL>
			        	<liferay-ui:message key="evaluationtaskactivity.calculate.confirm" />
				        <aui:button-row cssClass="container-buttons">
				        				        	<aui:button type="button" name="calculate" value="evaluationtaskactivity.calculate.acept"  
				        		onclick="<%= \"location.href='\"+updateURL+\"';\" %>"
				        	/>
				        	<aui:button type="button" name="cancel" value="cancel"  
				        		onclick="<%= \"AUI().use('aui-dialog', function(A) { A.DialogManager.closeByChild('#\"+renderResponse.getNamespace()+\"calculatePopup'); }); \" %>"
				        	/>
						</aui:button-row>
			        </div>
			        
			        <div id="<portlet:namespace />publishContents" class="aui-helper-hidden">
			        	<portlet:actionURL name="publish" var="publishURL" >
			        		<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
			        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
			        		</c:if>
			        	</portlet:actionURL>
			        	<liferay-ui:message key="evaluationtaskactivity.publish.confirm" />
				        <aui:button-row  cssClass="container-buttons">
				        	
				        	<aui:button type="button" name="publish" value="evaluationtaskactivity.publish"  
				        		onclick="<%= \"location.href='\"+publishURL+\"';\" %>"/>
				        		<aui:button type="button" name="cancel" value="cancel"  
				        		onclick="<%= \"AUI().use('aui-dialog', function(A) { A.DialogManager.closeByChild('#\"+renderResponse.getNamespace()+\"publishPopup'); }); \" %>"
				        	/>
				        
						</aui:button-row>
			        </div>
					
					<liferay-ui:icon
					image="add" cssClass="newitem2"
					label="<%= true %>"
					message="<%=(hasFiredDate)?\"evaluationTask.activity.evaluation.configuration.view\":((hasActivities)?\"evaluationtaskactivity.evaluation.configuration\":\"evaluationtaskactivity.evaluation.configuration.define\")%>"
					url="#"
					onClick="<%=\"	AUI().use('aui-dialog', function(A) {  new A.Dialog({ \"+
							\"			id:'\"+renderResponse.getNamespace()+\"showPopupActivities', \"+
							\"          title: ' \"+LanguageUtil.format(pageContext,(hasActivities)?\"evaluationtaskactivity.evaluations\":\"evaluationtaskactivity.evaluations.define\", new Object[]{}) +\"', \"+
							\"          modal: true, \"+
							\"    		width: 900, \"+
							\"          destroyOnClose: true, \"+
							\"          after: {    \"+
							\"		        render: function(event){  \"+
							\"			        var instance = this; \"+	
							\"			        instance.getStdModNode(A.WidgetStdMod.BODY).addClass('\"+
						   							((Portlet)request.getAttribute(WebKeys.RENDER_PORTLET)).getCssClassWrapper()+\"'); \"+
							\"		        }, \"+
							\"				contentUpdate: function(event){ \"+
							\"					var instance = this; \"+	
							\"					instance.centered(); \"+
							\"					if(instance.get('y')<0){ \"+
							\"						instance.set('y',0); \"+
							\"					} \"+			
							\"				}, \"+	
							\"	          	destroy: function(event){  \"+
							\"	          		Liferay.Portlet.refresh(A.one('#p_p_id\"+renderResponse.getNamespace()+\"'),{'p_t_lifecycle':0,'\"+renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY +\"':'\"+StringPool.TRUE +\"'}); \"+	
							\"            	} \"+
							\"            } \"+
							\"        }).plug(A.Plugin.IO, { \"+
							\"            uri: '\"+viewEvaluationsURL +\"', \"+
							\"            parseContent: true \"+
							\"        }).render().show();  \"+  
							\" }); \" %>"
					/>
					<% 
					if(!hasFiredDate) {
					   if(hasActivities) {
					%>
					<aui:button-row>
						<button name="Calculate" value="calculate" onclick="<%="AUI().use('aui-dialog', function(A) { "+
																			   " 	var dialog1 = new A.Dialog({ "+
																			   " 		id:'"+renderResponse.getNamespace()+"calculatePopup', "+
																			   " 		title: '"+LanguageUtil.get(pageContext, "evaluationtaskactivity.calculate")+"', "+
																			   " 		bodyContent: A.one('#"+renderResponse.getNamespace()+"calculateContents').getContent(), "+
																			   " 		height: 200, "+
																			   " 		width: 400, "+
																			   " 		modal: true, "+
																			   " 		centered: true "+
																			   " 	}).render().show(); "+
																			   "}); " %>" type="button">
							<liferay-ui:message key="evaluationtaskactivity.calculate" />
						</button>
					</aui:button-row>
							
					<% }}
					   else if(!hasPublishDate) {
					%>
					<aui:button-row>
						<button name="publish" value="publish" onclick="<%="AUI().use('aui-dialog', function(A) { "+
																		   " 	var dialog1 = new A.Dialog({ "+
																		   " 		id:'"+renderResponse.getNamespace()+"publishPopup', "+
																		   " 		title: '"+LanguageUtil.get(pageContext, "evaluationtaskactivity.publish")+"', "+
																		   " 		bodyContent: A.one('#"+renderResponse.getNamespace()+"publishContents').getContent(), "+
																		   " 		height: 200, "+
																		   " 		width: 400, "+
																		   " 		modal: true, "+
																		   " 		centered: true "+
																		   " 	}).render().show(); "+
																		   "}); " %>" type="button">
							<liferay-ui:message key="evaluationtaskactivity.publish" />
						</button>
					</aui:button-row>
							
					<% }
					
					String criteria = ParamUtil.get(request,"criteria",StringPool.BLANK);
					String gradeFilter = ParamUtil.get(request,"gradeFilter",StringPool.BLANK);
					PortletURL portletURL = renderResponse.createRenderURL();
					portletURL.setParameter("jspPage","/html/evaluationtaskactivity/view.jsp");
					portletURL.setParameter("criteria", criteria); 
					portletURL.setParameter("gradeFilter", gradeFilter);
					if(Validator.isNotNull(returnToFullPageURL)){
						portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
					}
					%>
					
					<liferay-portlet:renderURL var="returnurl" >
						<liferay-portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
						<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
		        			<liferay-portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
		        		</c:if>
					</liferay-portlet:renderURL>
					
					<h5><liferay-ui:message key="studentsearch"/></h5>
					<aui:form name="studentsearch" action="<%=returnurl %>" method="post" role="form">
						<aui:fieldset>
							<aui:column>
								<aui:input label="studentsearch.text.criteria" name="criteria" size="25" value="<%=criteria %>" />	
							</aui:column>	
							<aui:column>
								<aui:select label="evaluationtaskactivity.status" name="gradeFilter" onchange='<%="document.getElementById(\'" + renderResponse.getNamespace() + "studentsearch\').submit();" %>'>
									<aui:option selected='<%= gradeFilter.equals("") %>' value=""><liferay-ui:message key="evaluationtaskactivity.all" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("nocalification") %>' value="nocalification"><liferay-ui:message key="evaluationtaskactivity.status.passed" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("passed") %>' value="passed"><liferay-ui:message key="evaluationtaskactivity.passed" /></aui:option>
									<aui:option selected='<%= gradeFilter.equals("failed") %>' value="failed"><liferay-ui:message key="evaluationtaskactivity.failed" /></aui:option>
								</aui:select>
							</aui:column>	
							<aui:column>
								<aui:button cssClass="inline-button" name="searchUsers" value="search" type="submit" />
							</aui:column>
						</aui:fieldset>
					</aui:form>
						
					<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true">
					   	<liferay-ui:search-container-results>
						<%
							OrderByComparator obc = new UserFirstNameComparator(true);
							LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
							params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
													
							if(gradeFilter.equals("passed")) {
								params.put("passed",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_PASSED_SQL,course.getCourseId()));
							}else{
								if(gradeFilter.equals("failed")) {
									params.put("failed",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_FAIL_SQL,course.getCourseId()));
								}else{
									if (gradeFilter.equals("nocalification")) {
										params.put("nocalification",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_NO_CALIFICATION_SQL,course.getCourseId()));
									}
								}
							}
								

							List<User> userListsOfCourse = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
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
							<% 
								LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, usuario.getUserId());
								if(learningActivityResult!=null) {	   
									%>
									<div class="floatl">
									<%
									if(learningActivityResult.getPassed()){
										%><liferay-ui:message key="evaluationtaskactivity.student.passed"  arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), learningActivityResult.getResult())} %>" /><%
									}else {
										%><liferay-ui:message key="evaluationtaskactivity.student.failed"  arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), learningActivityResult.getResult()),ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), learningActivity.getPasspuntuation())} %>" /><%
									}
									%>
									</div>
									<%
									if(!hasPublishDate) {%>
										<portlet:actionURL name="reCalculate" var="reCalculateURL">
									   		<portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"/>
							        		<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
							        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
							        		</c:if>
									   	</portlet:actionURL>
									   	
									   	<div id="<portlet:namespace />recalculateContents_<%=Long.toString(usuario.getUserId()) %>" class="aui-helper-hidden">

										    <h1><%=usuario.getFullName() %></h1>
								        	<liferay-ui:message key="evaluationtaskactivity.recalculate.confirm" />
									        <aui:button-row  cssClass="container-buttons">
									      		  <aui:button type="button" name="acept" value="acept"  
									        		onclick="<%= \"location.href='\"+reCalculateURL+\"';\" %>"
									        	/>
									        	<aui:button type="button" name="cancel" value="cancel"  
									        		onclick="<%= \"AUI().use('aui-dialog', function(A) { A.DialogManager.closeByChild('#\"+renderResponse.getNamespace()+\"recalculatePopup_\"+usuario.getUserId()+\"'); }); \" %>"
									        	/>
									        	
											</aui:button-row>
								       </div>
								       <div class="floatr">
							           		<p class="see-more">
											<liferay-ui:icon-help message="evaluationtaskactivity.recalculate.help" />
											<a onClick="AUI().use('aui-dialog', function(A) {
											   	var dialog1 = new A.Dialog({
											   		id: '<portlet:namespace />recalculatePopup_<%=Long.toString(usuario.getUserId()) %>',
													title: '<liferay-ui:message key="evaluationtaskactivity.recalculate" />',
												    bodyContent: A.one('#<portlet:namespace />recalculateContents_<%=Long.toString(usuario.getUserId()) %>').getContent(),
												    height: 250,
												    width: 400,
												    modal: true,
												    centered: true
												}).render().show();
											});">
												<liferay-ui:message key="evaluationtaskactivity.recalculate"/>
											</a>
									    	<portlet:renderURL var="popupGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
										   		<portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"/>
										   		<portlet:param name="jspPage" value="/html/evaluationtaskactivity/popups/grades.jsp"/>
										   		<portlet:param name="criteria" value="<%=ParamUtil.getString(renderRequest, \"criteria\",\"\") %>"/>
										   		<portlet:param name="gradeFilter" value="<%=ParamUtil.getString(renderRequest, \"gradeFilter\",\"\") %>"/>
										   	</portlet:renderURL>
										   	</p>
											<p class="see-more">
											<liferay-ui:icon-help message="evaluationtaskactivity.set.grades.help" />
											<a onClick="<%="AUI().use('aui-dialog', function(A){ "+
												        "	new A.Dialog({ "+
												        "		id:'"+renderResponse.getNamespace()+"showPopupGrades', "+
												        "		title: '"+LanguageUtil.get(pageContext, "evaluationtaskactivity.set.grades")+"', "+
												        "		centered: true, "+
												        "		modal: true "+
												        "     }).plug(A.Plugin.IO, { "+
												        "         uri: '"+popupGradesURL+"', "+
												        "         parseContent: true "+
												        "     }).render().show(); "+   
												        "}); " %>">
												<liferay-ui:message key="evaluationtaskactivity.set.grades"/>
											</a>
										</p>
										</div>
									<%}else if(hasPublishDate && (!learningActivityResult.getComments().trim().equals(""))){%>
										<p class="see-more">
											<a onClick="AUI().use('aui-dialog', function(A) {
											   	var dialog1 = new A.Dialog({
													title: '<liferay-ui:message key="comments" />',
												    bodyContent: '<%=learningActivityResult.getComments() %>',
												    height: 200,
												    width: 400,
												    modal: true,
												    centered: true,
												    buttons: [{
												    	label: '<liferay-ui:message key="close" />',
												    	handler: function() {
												    		this.close();
												    	}
												    }]
												}).render().show();
											});">
												<liferay-ui:message key="evaluationtaskactivity.see-comments"/>
											</a>
										</p>
									<%} 
				               	}else{
									%><liferay-ui:message key="evaluationtaskactivity.student.without.qualification" /><% 
								}%>
							</liferay-ui:search-container-column-text>
						</liferay-ui:search-container-row>
					 	<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				<%}%>	
				<div class="nota"> 
				<%
				if(!isTeacher){ 
					if((result!=null)&&(hasPublishDate)){ %>
						<h2 class="description-title"><liferay-ui:message key="evaluationtaskactivity.result.title" /></h2>
						<p><liferay-ui:message key="evaluationtaskactivity.result.youresult" /> <span class="destacado"><%= (result!=null) ? ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),result.getResult()):"" %></span></p>
						<% 
						String resultadoNecesario = ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),learningActivity.getPasspuntuation());
						
						if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId())){%>
							<p class="nota_superado"><liferay-ui:message key="evaluationtaskactivity.result.pass"/></p>
						<%}else{%>
							<p class="nota_nosuperado"><liferay-ui:message key="evaluationtaskactivity.result.notpass.passPuntuation"  arguments="<%=new String[]{resultadoNecesario} %>" /></p>
						<%}
						if (!result.getComments().trim().equals("")){ %>
							<p><liferay-ui:message key="evaluationtaskactivity.result.teachercoment" /> 
	   						<div class="activity-comments"><%=result.getComments() %></div></p>
						<%} 
					}else {%>
						<div class="nota_nocorregida"><liferay-ui:message key="evaluationtaskactivity.not.qualificated.activity" /></div>
						<% 
					}
				}
			}
	}
}%>
			</div>
		</div>
</div>