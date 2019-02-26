
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.EvaluationAvgPortlet"%>
<%@page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.lms.OfflineActivity"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<liferay-ui:error key="evaluationAvg.evaluation.error"
	message="evaluationAvg.evaluation.error" />
<%
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		String returnToFullPageURL = renderRequest.getParameter("returnToFullPageURL");
		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
		boolean needPassPuntuation = courseEval.getNeedPassPuntuation(); 
		long passPuntuation = (needPassPuntuation)?courseEval.getPassPuntuation(course):0;		
		boolean needPassAllModules = courseEval.getNeedPassAllModules();
		
		String passedStudentMessageKey=(needPassPuntuation)?"evaluationAvg.student.passed":"evaluationAvg.student.passed.noPass";
		String failedStudentMessageKey=(needPassPuntuation)?"evaluationAvg.student.failed":"evaluationAvg.student.failed.noPass";
		
		CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
		String resultadoNecesario = ct.translate(locale,themeDisplay.getScopeGroupId(), (needPassPuntuation)?courseEval.getPassPuntuation(course):0) + ct.getSuffix(themeDisplay.getScopeGroupId());
		
		
		
		CourseResult result = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), themeDisplay.getUserId());

		
		Long  [] arguments=null;
		
		if((Validator.isNotNull(result))&&(Validator.isNotNull(result.getPassedDate()))) {	
			arguments =  new Long[]{result.getResult()};
		}
					
			boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
					
		%>

			<div class="evaluationAvg view">
										
				<% if(isTeacher){ 
				
					JSONObject courseEvalModel = null;
					try{
						courseEvalModel = courseEval.getEvaluationModel(course);
					}
					catch(Throwable e){
						courseEvalModel = JSONFactoryUtil.createJSONObject();
					}

				%>

				<portlet:renderURL var="viewEvaluationsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
		            <portlet:param name="jspPage" value="/html/evaluationAvg/popups/evaluations.jsp" />           
		        </portlet:renderURL>
					        		        
		        <portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
					<portlet:param name="ajaxAction" value="setGrades" />      
		            <portlet:param name="jspPage" value="/html/evaluationAvg/popups/grades.jsp" />           
		        </portlet:renderURL>
		        
		        <div id="<portlet:namespace />calculateContents" class="aui-helper-hidden">
		        	<portlet:actionURL name="updateCourse" var="updateCourseURL" >
		        		<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
		        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
		        		</c:if>
		        	</portlet:actionURL>
		        	<liferay-ui:message key="evaluationtaskactivity.calculate.confirm" />
			        <aui:button-row  cssClass="container-buttons">
			      	  <aui:button type="button" name="calculate" value="evaluationAvg.calculate.acept"  
			        		onclick="<%= \"location.href='\"+updateCourseURL+\"';\" %>"
			        	/>
			        	<aui:button type="button" name="cancel" value="cancel"  
			        		onclick="<%= \"AUI().use('aui-dialog', function(A) { A.DialogManager.closeByChild('#\"+renderResponse.getNamespace()+\"calculatePopup'); }); \" %>"
			        	/>
			        	
					</aui:button-row>
		        </div>
				
				<% 
				   
				%>
				
				<liferay-ui:icon
				image="add" cssClass="newitem2"
				label="<%= true %>"
				message="<%=(courseEvalModel.has(\"firedDate\"))?\"evaluationTask.activity.evaluation.configuration.view\":((courseEvalModel.has(\"evaluations\"))?\"evaluationAvg.evaluation.configuration\":\"evaluationAvg.evaluation.configuration.define\") %>"
				url="#"
				onClick="<%=\"	AUI().use('aui-dialog', function(A) {  new A.Dialog({ \"+
						\"			id:'\"+renderResponse.getNamespace()+\"showPopupEvaluations', \"+
						\"          title: ' \"+LanguageUtil.get(pageContext,(courseEvalModel.has(\"evaluations\"))?\"evaluationAvg.evaluations\":\"evaluationAvg.evaluations.define\") +\"', \"+
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
						\"          } \"+
						\"        }).plug(A.Plugin.IO, { \"+
						\"            uri: '\"+viewEvaluationsURL +\"', \"+
						\"            parseContent: true \"+
						\"        }).render().show();  \"+  
						\" }); \" %>"
				/>
				<% 
				if(!courseEvalModel.has("firedDate")) {
				   if(courseEvalModel.has("evaluations")) {
				%>
				<aui:button-row>
					<button name="Calculate" value="calculate" onclick="<%="AUI().use('aui-dialog', function(A) { "+
																		   " 	var dialog1 = new A.Dialog({ "+
																		   " 		id:'<portlet:namespace />calculatePopup', "+
																		   " 		title: '"+LanguageUtil.get(pageContext, "evaluationAvg.calculate")+"', "+
																		   " 		bodyContent: A.one('#"+renderResponse.getNamespace()+"calculateContents').getContent(), "+
																		   " 		height: 200, "+
																		   " 		width: 400, "+
																		   " 		modal: true, "+
																		   " 		centered: true "+
																		   " 	}).render().show(); "+
																		   "}); " %>" type="button">
						<liferay-ui:message key="evaluationAvg.calculate" />
					</button>
				</aui:button-row>
						
				<% }}
				
				String criteria = ParamUtil.get(request,"criteria",StringPool.BLANK);
				String gradeFilter = ParamUtil.get(request,"gradeFilter",StringPool.BLANK);
				
				PortletURL portletURL = renderResponse.createRenderURL();
				portletURL.setParameter("jspPage","/html/evaluationAvg/view.jsp");
				portletURL.setParameter("criteria", criteria); 
				portletURL.setParameter("gradeFilter", gradeFilter);
				if(Validator.isNotNull(returnToFullPageURL)){
					portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
				}
				%>
				
				<liferay-portlet:renderURL var="returnurl" >
					<liferay-portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
					<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
	        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
	        		</c:if>
				</liferay-portlet:renderURL>
				
				<h5><liferay-ui:message key="studentsearch"/></h5>
				<aui:form name="studentsearch" action="<%=returnurl %>" method="post" role="search">
					<aui:fieldset>
						<aui:column>
							<aui:input label="studentsearch.text.criteria" name="criteria" size="25" value="<%=criteria %>" />	
						</aui:column>	
						<aui:column>
							<aui:select label="evaluationAvg.status" name="gradeFilter" onchange='<%="document.getElementById(\'" + renderResponse.getNamespace() + "studentsearch\').submit();" %>'>
								<aui:option selected='<%= gradeFilter.equals("") %>' value=""><liferay-ui:message key="evaluationAvg.all" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("nocalification") %>' value="nocalification"><liferay-ui:message key="evaluationAvg.status.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("passed") %>' value="passed"><liferay-ui:message key="evaluationAvg.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("failed") %>' value="failed"><liferay-ui:message key="evaluationAvg.failed" /></aui:option>
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
							}
							else {
								if(gradeFilter.equals("failed")) {
									params.put("failed",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_FAIL_SQL,course.getCourseId()));
								} else {
									if (gradeFilter.equals("nocalification")) {
										params.put("nocalification",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_NO_CALIFICATION_SQL,course.getCourseId()));
									}
								}
							}
							
							//if ((GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM))==6)&&(!ResourceBlockLocalServiceUtil.isSupported("com.liferay.lms.model"))){		
								
							//	params.put("notTeacher",new CustomSQLParam(EvaluationAvgPortlet.NOT_TEACHER_SQL,themeDisplay.getScopeGroupId()));
							//	List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
							//	int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria,  WorkflowConstants.STATUS_ANY, params);
							//	pageContext.setAttribute("results", userListPage);
							//    pageContext.setAttribute("total", userCount);
							//    
							//}
							//else{
						
								List<User> userListsOfCourse = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
								List<User> userLists =  new ArrayList<User>(userListsOfCourse.size());
								
								for(User userOfCourse:userListsOfCourse){							
									if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){
										userLists.add(userOfCourse);
									}
								}	
								
								pageContext.setAttribute("results", ListUtil.subList(userLists, searchContainer.getStart(), searchContainer.getEnd()));
							    pageContext.setAttribute("total", userLists.size());	
							//}
															

						%>
					</liferay-ui:search-container-results>
					
					<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usuario">
					<liferay-ui:search-container-column-text name="name">
						<liferay-ui:user-display userId="<%=usuario.getUserId() %>"></liferay-ui:user-display>
					</liferay-ui:search-container-column-text>
					<liferay-ui:search-container-column-text name="calification">
						<% CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), usuario.getUserId()); 
							if((Validator.isNotNull(courseResult))&&(Validator.isNotNull(courseResult.getPassedDate()))) {   	   
								   if(courseResult.getPassed()){									   
									   %><liferay-ui:message key="<%=passedStudentMessageKey %>"  arguments="<%=
											   new Object[]{ct.translate(themeDisplay.getLocale(), course.getGroupCreatedId(),courseResult.getResult())} %>" /><%
								   }else {
									   %><liferay-ui:message key="<%=failedStudentMessageKey %>"  arguments="<%=
											   new Object[]{ct.translate(themeDisplay.getLocale(), course.getGroupCreatedId(), courseResult.getResult()),resultadoNecesario} %>" /><%
								   }
								   %>

								   
								   <div id="<portlet:namespace />recalculateContents_<%=Long.toString(usuario.getUserId()) %>" class="aui-helper-hidden">
							        	<portlet:actionURL name="reCalculate" var="reCalculateURL">
									   		<portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"/>
							        		<c:if test="<%=Validator.isNotNull(returnToFullPageURL) %>" >
							        			<portlet:param name="returnToFullPageURL" value="<%=returnToFullPageURL %>"/>
							        		</c:if>
									    </portlet:actionURL>
									    <h1><%=user.getFullName() %></h1>
							        	<liferay-ui:message key="evaluationAvg.recalculate.confirm" />
								        <aui:button-row  cssClass="container-buttons">
								        	
								        	<aui:button type="button" name="acept" value="acept"  
								        		onclick="<%= \"location.href='\"+reCalculateURL+\"';\" %>"
								        	/>
								        	<aui:button type="button" name="cancel" value="cancel"  
								        		onclick="<%= \"AUI().use('aui-dialog', function(A) { A.DialogManager.closeByChild('#\"+renderResponse.getNamespace()+\"recalculatePopup_\"+usuario.getUserId()+\"'); }); \" %>"
								        	/>
										</aui:button-row>
							       </div>
						            <p class="see-more">
									<a onClick="AUI().use('aui-dialog', function(A) {
											    	var dialog1 = new A.Dialog({
											    		id: '<portlet:namespace />recalculatePopup_<%=Long.toString(usuario.getUserId()) %>',
											    		title: '<liferay-ui:message key="evaluationAvg.recalculate" />',
											    		bodyContent: A.one('#<portlet:namespace />recalculateContents_<%=Long.toString(usuario.getUserId()) %>').getContent(),
											    		height: 250,
											    		width: 400,
											    		modal: true,
											    		centered: true
											    	}).render().show();
											    });">
										<liferay-ui:message key="evaluationAvg.recalculate"/>
									</a> <liferay-ui:icon-help message="evaluationAvg.recalculate.help" />
									</p>
								    <p class="see-more">
								    <portlet:renderURL var="popupGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
								   		<portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
								   		<portlet:param name="jspPage" value="/html/evaluationAvg/popups/grades.jsp"/>
								   	</portlet:renderURL>
									<a onClick="<%="AUI().use('aui-dialog', function(A){ "+
											    "	new A.Dialog({ "+
											    "		id:'"+renderResponse.getNamespace()+"showPopupGrades', "+
											    "		title: '"+LanguageUtil.get(pageContext, "evaluationAvg.set.grades")+"', "+
											    "		centered: true, "+
											    "		modal: true, "+
											    "		after: { "+   
											    "		  	close: function(event){ "+ 
											    "		  		Liferay.Portlet.refresh(A.one('#p_p_id"+renderResponse.getNamespace()+"'),{'p_t_lifecycle':0,'"+renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY+"':'"+StringPool.TRUE+"'}); "+	
											    "		 	} "+
											    "		} "+
											    "     }).plug(A.Plugin.IO, { "+
											    "         uri: '"+popupGradesURL+"', "+
											    "         parseContent: true "+
											    "     }).render().show(); "+   
											    "}); " %>">
										<liferay-ui:message key="evaluationAvg.set.grades"/>
									</a> <liferay-ui:icon-help message="evaluationAvg.set.grades.help" />
									</p>
								<%
							  
			               	}else{
								   %><liferay-ui:message key="evaluationAvg.student.without.qualification" /><% 
							}%>

					</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
					
				 	<liferay-ui:search-iterator />
				 	
				</liferay-ui:search-container>
				
				
				<% } %>	
				
				<div class="nota"> 

<%
if(!isTeacher){ 
	if((Validator.isNotNull(result))&&(Validator.isNotNull(result.getPassedDate()))) { %>
		<h2><liferay-ui:message key="evaluationAvg.result.title" /></h2>
		<p><liferay-ui:message key="evaluationAvg.result.youresult" /> <span class="destacado"><%= (arguments.length>0) ? LearningActivityResultLocalServiceUtil.translateResult(locale, arguments[0], themeDisplay.getScopeGroupId()):"" %></span></p>
		<%
		if(result.isPassed()){
		%>
			<p class="nota_superado"><liferay-ui:message key="evaluationAvg.result.pass"/></p>
		<%
		}else{
	
			if(needPassPuntuation) {
		%>	
			<p class="nota_nosuperado"><liferay-ui:message key="evaluationAvg.result.notpass.passPuntuation"  arguments="<%=new String[]{resultadoNecesario} %>" /></p>
		<% 
			}else {
		%>
			<p class="nota_nosuperado"><liferay-ui:message key="evaluationAvg.result.notpass.notPassPuntuation" /></p>	
		<%
			}
		}
		if (!result.getComments().trim().equals("")){ %>
		<p>
			<liferay-ui:message key="evaluationAvg.result.teachercoment" /> 
			<div class="activity-comments"><%=result.getComments() %></div>
		</p>
		<% } 
	}else {
	%>
		<div class="nota_nocorregida"><liferay-ui:message key="evaluationAvg.not.qualificated.activity" /></div>
	<% 
	}	
}%>

</div>
			
</div>

