<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.taglib.portlet.RenderURLTag"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<%@ include file="/init.jsp" %>

<% 
Boolean isTablet = ParamUtil.getBoolean(renderRequest, "isTablet", false);
if(isTablet){%>

	<script src="//code.jquery.com/jquery-1.7.2.min.js" type="text/javascript"></script> 
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/liferaylms-portlet/js/jquery.ui.touch-punch.min.js" type="text/javascript"></script> 
	<script src="/liferaylms-portlet/js/mouse.js" type="text/javascript"></script> 
		
		<script>

		setTimeout(function(){
			
			var hasTestJsCharged = false;
			var arrayScripts = document.getElementsByTagName("script");

			for(var i=0;i<arrayScripts.length;i++){
				if(arrayScripts[i].src.indexOf("test.js")!=-1){
					hasTestJsCharged=true;
					break;
				}
			}
			
			if(!hasTestJsCharged){
				var newScript = document.createElement('script');
				newScript.type = 'text/javascript';
				newScript.src = "/liferaylms-portlet/js/test.js";
				document.getElementsByTagName("head")[0].appendChild(newScript);
			}
		
		
		
		}, 1000);
		
			
		</script>
		
<%}%>


	<div class="container-activity">
<%

		boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
		boolean hasFreeQuestion = false;
		long actId=ParamUtil.getLong(request,"actId",0);
		boolean improve =ParamUtil.getBoolean(request, "improve",false);
		long userId = themeDisplay.getUserId();
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

		//Obtener si puede hacer un intento de mejorar el resultado.
		boolean improving = false;
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
		
		if(result != null){
			int done =  LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,userId);
			LearningActivity act=LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			if(result.getResult() < 100 && !LearningActivityLocalServiceUtil.islocked(actId, userId) && LearningActivityResultLocalServiceUtil.userPassed(actId, userId) && (done < act.getTries() || act.getTries() == 0)){
				improving = true;
			}
		}
	
		if(actId==0)
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		else{
			TestQuestionLocalServiceUtil.checkWeights(actId);
			LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
			long typeId=activity.getTypeId();
	
			if( typeId==0 && (!LearningActivityLocalServiceUtil.islocked(actId,userId)
				|| permissionChecker.hasPermission( activity.getGroupId(),LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
				|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK"))){

				
				List<TestQuestion> questionList = TestQuestionLocalServiceUtil.getQuestions(activity.getActId());
				Iterator<TestQuestion> questionListIt = questionList.iterator();
				while(questionListIt.hasNext()){
					TestQuestion q = questionListIt.next();
					if(q.getQuestionType() == 2){
						hasFreeQuestion = true;
						break;
					}
				}
				
				if(isTeacher){
					String popupcorrection="javascript:"+renderResponse.getNamespace() + "showPopupGetReport();";
				%>
					<portlet:renderURL var="goToCorrection" >
	<portlet:param name="jspPage" value="/html/execactivity/test/correction.jsp" />
	<portlet:param name="actId" value="<%=Long.toString(activity.getActId() )%>" />
	<portlet:param name="courseId" value="<%=Long.toString(course.getCourseId() )%>" />
</portlet:renderURL>
					<aui:button name="importButton" type="button" value="action.CORRECT"  last="true" href="<%= goToCorrection.toString() %>" ></aui:button>
					
				<%}
				
				if((!LearningActivityLocalServiceUtil.islocked(actId,userId)
					|| permissionChecker.hasPermission( activity.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
					|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK"))){		
					
					if((!improve) && (LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))){
						request.setAttribute("learningActivity",activity);
						request.setAttribute("larntry",LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId));
%>
						<liferay-util:include page="/html/execactivity/test/results.jsp" servletContext="<%=this.getServletContext() %>">
							<liferay-util:param value="<%=Long.toString(activity.getActId()) %>" name="actId"/>
						</liferay-util:include>
<% 
					} else if (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId) 
						|| permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(),actId, ActionKeys.UPDATE)
						|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")
			    		|| improving ){%>
			    		<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
				<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
				<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
				<%
						
						
						String navigateParam = ParamUtil.getString(renderRequest, "navigate");
						String passwordParam = ParamUtil.getString(renderRequest, "password",StringPool.BLANK).trim();
						String password = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "password"),StringPool.BLANK).trim();
						
						if((Validator.isNotNull(navigateParam))||(StringPool.BLANK.equals(password))||(passwordParam.equals(password))){
							ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
							long activityTimestamp=0;
							long timestamp=0;			
							LearningActivityTry learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(actId,userId);
			
							//Comprobar si tenemos un try sin fecha de fin, para continuar en ese try.
							if(learningTry == null)
								learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
							else{
								activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"timeStamp"));
								timestamp=activityTimestamp*1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
							}
			
							if((activityTimestamp!=0)&&(timestamp<0)){
								request.setAttribute("learningActivity",activity);
								request.setAttribute("larntry",learningTry);
%>
								<liferay-util:include page="/html/execactivity/test/expired.jsp" servletContext="<%=this.getServletContext() %>">
  									<liferay-util:param value="<%=Long.toString(activity.getActId()) %>" name="actId"/>
  								</liferay-util:include>  	
<%
							}else{	
								List<TestQuestion> questiones=TestQuestionLocalServiceUtil.getQuestions(actId);
								List<TestQuestion> questions = ListUtil.copy(questiones);
								BeanComparator beanComparator = new BeanComparator("weight");
								Collections.sort(questions, beanComparator);
								Object  [] arg =  new Object[]{activity.getPasspuntuation()};
			
								if (activity.getPasspuntuation()>0){ 
%>
									<p class="msg_pass"><liferay-ui:message key="execativity.test.try.pass.puntuation" arguments="<%=arg %>" /></p>
<% 
								} 
%>
								<portlet:actionURL name="correct" var="correctURL">
									<portlet:param name="actId" value="<%=Long.toString(actId) %>" ></portlet:param>
									<portlet:param name="latId" value="<%=Long.toString(learningTry.getLatId()) %>"></portlet:param>
								</portlet:actionURL>
  								  								
								<script type="text/javascript">
								<!--
<%
									if(activityTimestamp == 0){
										activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"timeStamp"));
									}	
			
									if(activityTimestamp !=0){ 
										if(timestamp==0) {
				   							timestamp=activityTimestamp*1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
										}
%>
										AUI().ready('liferay-notice', 'collection', function(A) {

											var TestActivity = function(options) {
												var instance = this;
												instance.timeout=options.timeout||false;
												instance.warningText=options.warningText||'Timeout Warning: <span class="countdown-timer"/>';
												instance.expiredText=options.expiredText||'Text timeout';
												instance.onClose=options.onClose;
							
												instance.banner=null;
												if(instance.timeout) {
													instance.banner=new Liferay.Notice({content:instance.warningText,closeText:false,toggleText:false});
													instance.countdowntext=instance.banner.one('.countdown-timer');
													if(instance.countdowntext){
														instance.countdowntext.text(instance._formatTime(instance.timeout));
													}
	
													var interval=1000;
													instance.finishtime = new Date().getTime()+instance.timeout;
	
													instance._countdownTimer = setInterval(
														function() {
					
															var currentTimeout = instance.finishtime-new Date().getTime();
					
															if (currentTimeout > 0) {
																instance.countdowntext.text(instance._formatTime(currentTimeout));
															}
															else {
																instance.banner.html(instance.expiredText);
																instance.banner.toggleClass('popup-alert-notice').toggleClass('popup-alert-warning');
																
																if (instance._countdownTimer) {
																	clearInterval(instance._countdownTimer);
																}
					
																if (instance.onClose) {
																	instance.onClose();
																}
															}
														},
														interval
													);
												}
											};

							TestActivity.prototype = {
								_formatNumber: function(num) {
									var instance = this;
							
									if (num <= 9) {
										num = '0' + num;
									}
		
									return num;
								},
								_formatTime: function(time) {
									var instance = this;
		
									time = Math.floor(time/1000);
		
									var hours = Math.floor(time/3600);
									time = time%3600;
		
									var minutes = Math.floor(time/60);
									time = time%60;
		
									var seconds = Math.floor(time);
									
									return A.Array.map([hours,minutes,seconds], instance._formatNumber).join(':');
									
								}
		
							};
					
							new TestActivity({timeout:<%=Long.toString(timestamp)%>,
											  warningText:'<liferay-ui:message key="execActivity.timeout.warning" />',
											  expiredText:'<liferay-ui:message key="execActivity.timeout" />',
											  onClose:function(){
												document.getElementById('<portlet:namespace/>formulario').submit();
											}});
		
							});

				<% } %>
				
				Liferay.provide(
				        window,
				        '<portlet:namespace />questionValidation',
				        function(question) {
							var A = AUI();
							var questionValidators = {
								questiontype_options : function(question) {
									return (question.all('div.answer input[type="radio"]:checked').size() > 0);
								},
								questiontype_multioptions : function(question) {
									return (question.all('div.answer input[type="checkbox"]:checked').size() > 0);
								},
								questiontype_freetext : function(question) {
									return (A.Lang.trim(question.one('div.answer textarea').val()) != '');
								},
								questiontype_fillblank : function(question) {
									var texts = question.all(':text');
									var validTexts = true;
									texts.each(function(node) {
										validTexts = validTexts && (A.Lang.trim(node.val()) != '');
									});
									
									var selecteds = question.all(':selected');
									var validSelecteds = true;
									selecteds.each(function(node) {
										validSelecteds = validSelecteds && (node.val() != '');
									});
									
									var validCheckeds = (question.all(':radio:checked').size() == question.all('.multichoice').size());
									
									return validTexts && validSelecteds && validCheckeds;
								},
								questiontype_sortable : function(question) {
									return true;
								},
								questiontype_draganddrop : function(question) {
									return (question.all('div.drop > input[type="hidden"][value="-1"]').size() == 0);
								},
								
							};
							
							var clases = question.getAttribute('class').split(" ");
							var questiontypevalidator = '';
							for ( var i = 0; i < clases.length; i++) {
								var clase = clases[i];
								if (clase.indexOf('questiontype_') == 0) {
									questiontypevalidator = clase;
									break;
								}
							}
							if (questionValidators[questiontypevalidator] != null) {
								var resultado = questionValidators[questiontypevalidator](question);
								return resultado;
							} else {
								return true;
							}
							
				        },
				        ['node', 'aui-dialog', 'event', 'node-event-simulate']
				        );
				
					Liferay.provide(
					        window,
					        '<portlet:namespace />popConfirm',
					        function(content, boton) {
								var A = AUI();
							
								window.<portlet:namespace />confirmDialog = new A.Dialog(
								    {
								        title: '<liferay-ui:message key="execactivity.confirm.title"/>',
								        bodyContent: content,
								        buttons: [
								                  {
								                	  label: '<liferay-ui:message key="ok"/>',
								                	  handler: function() {
								                		  A.one('#<portlet:namespace/>formulario').detach('submit');
								                		  document.getElementById('<portlet:namespace/>formulario').submit();
								                		  <portlet:namespace />confirmDialog.close();
								                	  }
								                  },
								                  {
								                	  label: '<liferay-ui:message key="cancel"/>',
								                	  handler: function() {
								                		  <portlet:namespace />confirmDialog.close();
								                	  }
								                  }
								                  ],
								        width: 'auto',
								        height: 'auto',
								        resizable: false,
								        draggable: false,
								        close: true,
								        destroyOnClose: true,
								        centered: true,
								        modal: true
								    }
								).render();
								
					        },
					        ['node', 'aui-dialog', 'event', 'node-event-simulate']
					    );
				
					Liferay.provide(
					        window,
					        '<portlet:namespace/>submitForm',
					function(e, navigate) {
						var returnValue = true;
						
						var A = AUI();
					    var questions = A.all('div.question-page-current div.question');
	
					    if (navigate != 'backward') {
						    for (var i = 0; i < questions.size(); i++) {
						    	var question = questions.item(i);
						    	var validQuestion = <portlet:namespace />questionValidation(question);
						    	if (typeof validQuestion == 'undefined') {
						    		validQuestion = <portlet:namespace />questionValidation(question);
						    	}
						    	if (!validQuestion) {
						    		returnValue = false;
						    		break;
						    	}
							}
							
						    if (!returnValue) {
						    	if (e.target) {
						    		targ = e.target.blur();
						    	} else if (e.srcElement) {
						    		targ = e.srcElement.blur();
						    	}
						    	<%= renderResponse.getNamespace() %>popConfirm('<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "execativity.test.questions.without.response")) %>', e.srcElement);
				    		}
					    }
						
						if (navigate == 'backward' || navigate == 'forward') {
							A.one('#<portlet:namespace/>navigate').val(navigate);

							var page = A.one('.question-page-current');
							var n = page.attr('id').split("-");
							if (navigate == 'backward') {
								n[n.length - 1]--;
							} else {
								n[n.length - 1]++;
							}
							var valor = A.one('#'+n.join("-")).one('.question > input').val();
							A.one('#<portlet:namespace/>currentQuestionId').val(valor);
						} else {
							A.one('#<portlet:namespace/>navigate').val("");
							A.one('#<portlet:namespace/>currentQuestionId').val("0");
						}
						if (returnValue) {
							submitForm('#<portlet:namespace/>formulario');
						}
					},
					['node', 'aui-dialog', 'event', 'node-event-simulate']
				);
				
			//-->
			</script>			

			<aui:form name="formulario" action="<%=correctURL %>" method="post" onSubmit="javascript:return false;">
<!-- De momento se comenta la numeración -->
<!-- 			<script type="text/javascript"> -->
<!--  				AUI().ready(function(A) { -->
<!--  					//Numeramos las preguntas -->
<!--  					var preguntas = A.all(".questiontext > p"); -->
<!--  					var numPregunta = 1; -->
					
<!--  					preguntas.each(function(node){ -->
<!--  						node.html(numPregunta + ') ' + node.html()); -->
<!--  						numPregunta++; -->
<!--  					}); -->
<!--  				}); -->
<!-- 			</script> -->
			
			<%
			long random = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"random"));
			long currentQuestionId = 0;
			if (learningTry != null && Validator.isXml(learningTry.getTryResultData())) {
				String tryResultData = learningTry.getTryResultData();
				Document docQuestions = SAXReaderUtil.read(tryResultData);
				List<Element> xmlQuestions = docQuestions.getRootElement().elements("question");
				
				Map<Long, TestQuestion> questionMap = new HashMap<Long, TestQuestion>();
				for (TestQuestion question : questions) {
					questionMap.put(question.getQuestionId(), question);
				}
				questions = new ArrayList<TestQuestion>();
				for (Element xmlQuestion : xmlQuestions) {
					String questionIdString = xmlQuestion.attributeValue("id");
					if (Validator.isNotNull(questionIdString) && Validator.isNumber(questionIdString)) {
						Long questionId = Long.valueOf(questionIdString);
						TestQuestion testQuestion = questionMap.get(questionId);
						if (testQuestion != null) {
							questions.add(testQuestion);
							String currentString = xmlQuestion.attributeValue("current");
							if ("true".equals(currentString)) {
								currentQuestionId = questionId;
							}
						}
					}
				}
				random = questions.size();
			} else {
				if (random != 0){
					questions = new ArrayList<TestQuestion>(questions);
					Collections.shuffle(questions);	
					if (random > questions.size()){
						random=questions.size();
					}
				}
				else{
					random=questions.size();
				}
			}
			
			long questionsPerPage = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "questionsPerPage"));
			
			boolean showPrevious = false;
			boolean showNext = false;
						
			long limitChunk = questionsPerPage == 0 ? random : questionsPerPage;
			if (limitChunk == 0) {
				limitChunk = 1;
			}
			
			long currentPage = 0;
			long totalPages = (random / limitChunk) + (random % limitChunk != 0 ? 1 : 0);
			
			for(int index = 0; index < random; index += limitChunk) {
				boolean questionPageCurrent = false;
				StringBuilder sb = new StringBuilder();
				
				for(int jndex = 0; jndex < Math.min(limitChunk, random - index); jndex++) {
					TestQuestion question = questions.get(jndex + index);
					QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
					if (question.getQuestionId() == currentQuestionId) {
						questionPageCurrent = true;
					}
					qt.setLocale(themeDisplay.getLocale());
					Document document = null;
					if (Validator.isNotNull(learningTry.getTryResultData())) {
						document = SAXReaderUtil.read(learningTry.getTryResultData());
					}
					sb.append(qt.getHtmlView(question.getQuestionId(), themeDisplay, document));
				} 
				boolean markAsCurrentPage = questionPageCurrent || (currentQuestionId == 0 && index == 0);
				if (markAsCurrentPage && index != 0) {
					showPrevious = true;
				}
				if (markAsCurrentPage && (index + limitChunk < random)) {
					showNext = true;
				}
				if (markAsCurrentPage) {
					currentPage = (questionsPerPage == 0 ? 0 : index / questionsPerPage) + 1;
				}
				%>
				<div class='question-page<%= markAsCurrentPage ? " question-page-current" : "" %>' id="question-page-<%= questionsPerPage == 0 ? 0 : index / questionsPerPage %>">
				<%= sb.toString() %>
				</div>
				<%
			}
			
				if (questions.size() > 0){
					if (questionsPerPage == 0) { %>
					<aui:button type="button" value="submit" onClick='<%=renderResponse.getNamespace() + "submitForm(event, null);" %>' ></aui:button>
				<% } else { %>
				
				<div id="testactivity-navigator" class="taglib-page-iterator">
				<% if (showPrevious) { %>
					<div id="testactivity-navigator-previous">
						<aui:button type="button" value="execactivity.editActivity.questionsPerPage.previous" onClick='<%=renderResponse.getNamespace() + "submitForm(event, \'backward\');" %>' ></aui:button>
					</div>
					<% }
				if ((showPrevious || showNext) && currentPage >= 1) { %>
					<div id="testactivity-navigator-pages">
						<p><%= currentPage + " / " + totalPages %></p>
						<div id="testactivity-navigator-progress">
							<% 
							long width_frame = 10000 / totalPages;
							for (int i = 1; i <= totalPages; i++) {
								boolean browsed = (i <= currentPage);
								%>
							<div id="testactivity-navigator-progress-frame-<%= i %>" class='testactivity-navigator-progress-frame <%= browsed ? "testactivity-navigator-progress-frame-browsed" : "testactivity-navigator-progress-frame-not-browsed" %>' style='width: <%= (width_frame / 100) + "." + (width_frame % 100) %>%'></div>
							<% } %>
						</div>
					</div>
				<% } %>
				<div id="testactivity-navigator-next">
				<% if (showNext) { %>
					<aui:button type="button" value="execactivity.editActivity.questionsPerPage.next" onClick='<%=renderResponse.getNamespace() + "submitForm(event, \'forward\');" %>' ></aui:button>
				<% } else { %>
					<aui:button type="button" value="submit" onClick='<%=renderResponse.getNamespace() + "submitForm(event, null);" %>' ></aui:button>
				<% } %>
				</div>
				</div>
				
				<% } %>
				
				<%if(isTablet){%>
					<aui:input type="hidden" name="isTablet" value="<%= true %>"/>
				<%} %>
				
				<aui:input type="hidden" name="currentQuestionId" value="<%= currentQuestionId %>"/>
				<aui:input type="hidden" name="navigate" value=""/>
				<aui:input type="hidden" name="improve" value="<%= new Boolean(improve).toString() %>" />
					<%}  else {%>
					<p class="color_tercero negrita"><liferay-ui:message key="execativity.test.no.question" /></p>
				<% }  %>
			
			</aui:form>

			<%
			}
			}
			else{
				PortletURL passwordURL = renderResponse.createRenderURL();
				if(improve) {
					passwordURL.setParameter("improve", "true");		
				}			
			%>
			
				<aui:form action="<%=passwordURL%>" method="post">
					<aui:input type="text" name="password" label="execActivity.options.password" />
					<% if(!StringPool.BLANK.equals(passwordParam)){ %>
					<div class='portlet-msg-error'>
						<liferay-ui:message key="execActivity.bad.password" />
					</div>
					<% } %>
					<aui:button type="submit" />
					
				</aui:form>
			<%	
			}
		}
		//Si no ha pasado el test, ni tiene mas intentos.
		else{
			if(hasFreeQuestion){
				Object  [] arguments =  new Object[]{result.getResult()};
				Object  [] arg =  new Object[]{activity.getPasspuntuation()};
				%>
				<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
				<p><liferay-ui:message key="test-done" /></p>
				<p>Esperar la corrección del profesor</p>
				<%
			}else{
				//LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
				Object  [] arguments =  new Object[]{result.getResult()};
				Object  [] arg =  new Object[]{activity.getPasspuntuation()};
				%>
				<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
				<p><liferay-ui:message key="test-done" /></p>
				<p><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></p>
				<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=arg %>" /></p>
				<p><liferay-ui:message key="your-result-no-more-tries" /></p>
				<%
			}
			
			
		}
		
		}
	}
}

%>
</div>