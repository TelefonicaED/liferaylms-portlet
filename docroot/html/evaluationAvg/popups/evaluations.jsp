<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<%
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
	
	JSONObject courseEvalModel = null;
	try{
		courseEvalModel = courseEval.getEvaluationModel(course);
	}
	catch(Throwable e){
		courseEvalModel = JSONFactoryUtil.createJSONObject();
	}

	if(!courseEvalModel.has("evaluations")){
		courseEvalModel.put("evaluations", JSONFactoryUtil.createJSONArray());
	}
	
	boolean showModuleColumn = LearningActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
    	    add(PropertyFactoryUtil.forName("typeId").eq(8)).
    	    add(PropertyFactoryUtil.forName("moduleId").ne(0L)))>0;

	PortletURL viewEvaluationsURL = renderResponse.createRenderURL();
	viewEvaluationsURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
	viewEvaluationsURL.setParameter("jspPage","/html/evaluationAvg/popups/evaluations.jsp");

%>

<script type="text/javascript">
<!--

	function <portlet:namespace />doClosePopupEvaluations()
	{
	    AUI().use('aui-dialog', function(A) {
	    	A.DialogManager.closeByChild('#<portlet:namespace />showPopupEvaluations');
	    });
	}

	YUI.add('<portlet:namespace />eval-model', function(A) {
	    A.<portlet:namespace />EvaluationModel = A.Base.create('<portlet:namespace />EvaluationModel', A.Model, [], {
	    }, {
	        ATTRS: {
				weight: {
					value: 0
				}
	        }
	    });

	    A.<portlet:namespace />EvaluationModelList = A.Base.create('<portlet:namespace />EvaluationModelList', A.ModelList, [], {
	        comparator: function (model) {
	            return model.get('id');
	        },
	        model: A.<portlet:namespace />EvaluationModel
	    });
	
	}, '' ,{requires:['model-list']});

	function <portlet:namespace />addEvaluation(evalId, evalWeight){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingEvaluation=window.<portlet:namespace />selectedEvaluations.getById(evalId);
			if(existingEvaluation!=null){
				window.<portlet:namespace />selectedEvaluations.remove(existingEvaluation);
			}	
			window.<portlet:namespace />selectedEvaluations.add(
					new A.<portlet:namespace />EvaluationModel({id:evalId,weight:evalWeight}));
		});			
	}
	
	function <portlet:namespace />deleteEvaluation(evalId){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingEvaluation=window.<portlet:namespace />selectedEvaluations.getById(evalId);
			if(existingEvaluation!=null){
				window.<portlet:namespace />selectedEvaluations.remove(existingEvaluation);
			}		
		});		
	}

	function <portlet:namespace />createValidator(){
		AUI().use('aui-form-validator', function(A) {

			if(!!window.<portlet:namespace />validateEvaluations){
				delete window.<portlet:namespace />validateEvaluations;
			}

			var rules = {			
					<portlet:namespace />passPuntuation: {
						required: true,
						number: true,
						range: [0,100]
					}
				};

			var fieldStrings = {			
		        	<portlet:namespace />passPuntuation: {
		        		required: Liferay.Language.get("evaluationAvg.passPuntuation.required"),
		        		number: Liferay.Language.get("evaluationAvg.passPuntuation.number"),
		        		range: Liferay.Language.get("evaluationAvg.passPuntuation.range")     		
		            }
				};

			A.all('#<portlet:namespace />evaluations * input').each(
				function(evaluationWeight){
					if(evaluationWeight.get('id').indexOf('<portlet:namespace />weight_')==0){
						
						rules[evaluationWeight.get('id')] = {
								number: true,
								range: [0,100]
							};

						fieldStrings[evaluationWeight.get('id')] = {
								number: Liferay.Language.get("evaluationAvg.weight.number"),
				        		range: Liferay.Language.get("evaluationAvg.weight.range")  
							};

						evaluationWeight.on('input',function(){
								if(evaluationWeight.get('value')=='') {
									var divError = A.one('#'+evaluationWeight.get('name')+'Error');
									divError.removeClass('portlet-msg-error');
									divError.setContent('');
									<portlet:namespace />deleteEvaluation(evalId);
								}
								else{
									window.<portlet:namespace />validateEvaluations.validateField(evaluationWeight.get('name'));
									var evalId=evaluationWeight.get('id').substring('<portlet:namespace />weight_'.length);
									if(!window.<portlet:namespace />validateEvaluations.getFieldError(window.<portlet:namespace />validateEvaluations.getField(evaluationWeight.get('name')))){	
										<portlet:namespace />addEvaluation(evalId, evaluationWeight.get('value'));
									}
									else {
										<portlet:namespace />deleteEvaluation(evalId);
									}
								}
								var divResult = A.one('#<portlet:namespace />evaluationResult');
								divResult.removeClass('portlet-msg-error');
								divResult.removeClass('portlet-msg-success');
								divResult.setContent('');
							});
					}
			});
					
			window.<portlet:namespace />validateEvaluations = new A.FormValidator({
				boundingBox: '#<portlet:namespace />evaluations',
				validateOnBlur: true,
				validateOnInput: true,
				selectText: true,
				showMessages: false,
				containerErrorClass: '',
				errorClass: '',
				rules: rules,
		        fieldStrings: fieldStrings,
				
				on: {		
		            errorField: function(event) {
		            	var instance = this;
						var field = event.validator.field;
						var divError = A.one('#'+field.get('name')+'Error');
						if(divError) {
							divError.addClass('portlet-msg-error');
							divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
						}
		            },		
		            validField: function(event) {
						var divError = A.one('#'+event.validator.field.get('name')+'Error');
						if(divError) {
							divError.removeClass('portlet-msg-error');
							divError.setContent('');
						}
		            }
				}
			});	

		});		
	}

	function <portlet:namespace />validatelearningActivitiesSearchContainerSearchContainer(){
		var _return=true;
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			if(window.<portlet:namespace />validateEvaluations.hasErrors()) {
				_return=confirm('<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "evaluationAvg.confirm.message")) %>');
			}
		});		
		return _return;	
	}

	function <portlet:namespace />doSaveEvaluations(){
		AUI().use('node-base','json-stringify','<portlet:namespace />eval-model','aui-io-request', function(A) {
			if(!window.<portlet:namespace />validateEvaluations.hasErrors()){
                var output= new Object();			
				<% if(courseEval.getNeedPassPuntuation()) { %>
				   output['passPuntuation']=A.one('#<portlet:namespace />passPuntuation').get('value');
				<% }%>

				var outputEvaluations= new Array();
				var itrOutputEvaluations=0;
				window.<portlet:namespace />selectedEvaluations.each(
					function(evalModel){
						outputEvaluations[itrOutputEvaluations++]={id:evalModel.get('id'),weight:evalModel.get('weight')};
					}
				);	
				output['evaluations']=outputEvaluations;

				A.io.request(
						'<portlet:actionURL name="saveEvalModel" />',
						{
							data: {model:A.JSON.stringify(output)},
							dataType: 'json',
							on: {
								failure: function(event, id, obj) {
									var portlet = A.one('#p_p_id<portlet:namespace />');
									portlet.hide();
									portlet.placeAfter('<div class="portlet-msg-error"><liferay-ui:message key="there-was-an-unexpected-error.-please-refresh-the-current-page"/></div>');
								},
								success: function(event, id, obj) {
									var responseData = this.get('responseData');
									var divResult = A.one('#<portlet:namespace />evaluationResult');
									if(responseData.responseCode==1){
										divResult.removeClass('portlet-msg-error');
										divResult.addClass('portlet-msg-success');
									}
									else{
										divResult.addClass('portlet-msg-error');
										divResult.removeClass('portlet-msg-success');
									}
									divResult.setContent('');
									
									var messageList = A.Node.create('<ul></ul>');
									if(responseData.messages.length==1){
										divResult.setContent(responseData.messages[0]);
									}
									else {
										for (var i = 0; i < responseData.messages.length; i++) {
											var messageItem = A.Node.create('<li></li>');  
											messageItem.setContent(responseData.messages[i]);   
											messageList.appendChild(messageItem);
										}
										divResult.appendChild(messageList);
									}

								}
							}
						}
					);
				
			}
		});	
	}

	AUI().ready('node-base','<portlet:namespace />eval-model', function(A) {
		window.<portlet:namespace />selectedEvaluations = new A.<portlet:namespace />EvaluationModelList();
		window.<portlet:namespace />selectedEvaluations.add(<%=courseEvalModel.getJSONArray("evaluations").toString() %>);

		var searchContainer = A.one('#<%=renderResponse.getNamespace() %>learningActivitiesSearchContainerSearchContainer').ancestor('.lfr-search-container');

		searchContainer.on('ajaxLoaded',function(){

			window.<portlet:namespace />selectedEvaluations.each(
				function(evalModel){
					A.all('#<portlet:namespace />weight_'+evalModel.get('id')).each(function(evalModelInput){
						 evalModelInput.set('value',evalModel.get('weight')); });  					 
				}
			);
			<portlet:namespace />createValidator();	

			var divResult = A.one('#<portlet:namespace />evaluationResult');
			divResult.removeClass('portlet-msg-error');
			divResult.removeClass('portlet-msg-success');
			divResult.setContent('');
		});

		searchContainer.fire('ajaxLoaded');
	});

//-->
</script>

<aui:form name="evaluations" role="form">
<% if(courseEval.getNeedPassPuntuation()) { 
%>
	<aui:fieldset>
	    <aui:input type="text" name="passPuntuation" label="evaluationAvg.passPuntuation"
	               value='<%= courseEvalModel.getLong("passPuntuation") %>' disabled='<%=courseEvalModel.has("firedDate") %>' />
	    <div id="<portlet:namespace />passPuntuationError" class="<%=(SessionErrors.contains(renderRequest, "evaluationAvg.passPuntuation.result-bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "evaluationAvg.passPuntuation.result-bad-format"))?
	    			LanguageUtil.get(pageContext,"evaluationAvg.passPuntuation.result-bad-format"):StringPool.BLANK %>
	    </div>
	</aui:fieldset>
<% } %>

	<liferay-util:buffer var="weightHelp">
		<liferay-ui:icon-help message="evaluationAvg.evaluation.weight.help" />
	</liferay-util:buffer>

	<liferay-ui:search-container iteratorURL="<%=viewEvaluationsURL%>" emptyResultsMessage="there-are-no-results" delta="5" deltaConfigurable="true" >

	   	<liferay-ui:search-container-results>
			<%
				pageContext.setAttribute("results", LearningActivityLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
			    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
			    	    add(PropertyFactoryUtil.forName("typeId").eq(8)).
			    	    addOrder(OrderFactoryUtil.asc("priority")), 
			    	searchContainer.getStart(), searchContainer.getEnd()));
			
			    pageContext.setAttribute("total",(int)LearningActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
				    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
				    	    add(PropertyFactoryUtil.forName("typeId").eq(8))
			    		));			
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity" keyProperty="actId" modelVar="evaluation">
			<c:if test="<%=showModuleColumn %>">
				<liferay-ui:search-container-column-text name="evaluationAvg.evaluation.module" title="evaluationAvg.evaluation.module">
					<c:choose>
						<c:when test="<%=Validator.isNotNull(evaluation.getModuleId()) %>">
							<%=GetterUtil.getString(ModuleLocalServiceUtil.getModule(evaluation.getModuleId()).getTitle(themeDisplay.getLocale())) %>
						</c:when>
						<c:otherwise>
							--
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:if>
			<liferay-ui:search-container-column-text name="evaluation" title="evaluationAvg.evaluation.name">
				<%=evaluation.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="evaluationAvg.evaluation.weight" title="evaluationAvg.evaluation.weight"  align="right">
				<aui:input type="text" label="<%=weightHelp %>" inlineLabel="right" name="<%=\"weight_\"+evaluation.getActId() %>" size="3" disabled='<%=(courseEvalModel.has("firedDate")) %>'>  </aui:input>
				<div id="<portlet:namespace />weight_<%=evaluation.getActId() %>Error" class="<%=(SessionErrors.contains(renderRequest, "evaluationAvg.weight_"+evaluation.getActId()+".bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    														<%=(SessionErrors.contains(renderRequest, "evaluationAvg.weight_"+evaluation.getActId()+".bad-format"))?
	    															LanguageUtil.get(pageContext,"evaluationAvg.weight.bad-format"):StringPool.BLANK %>
	    		</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

	 	<liferay-ui:search-iterator />
		
		<script type="text/javascript">
		<!--
			function <portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A) {

				var searchContainer = A.one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
				
				function <portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url){
				     
					if((!!<portlet:namespace />validate<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer)&&
					     (<portlet:namespace />validate<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer())) {
						var params = {};
						var urlPieces = url.split('?');
						if (urlPieces.length > 1) {
							params = A.QueryString.parse(urlPieces[1]);
							params.p_p_state='<%=LiferayWindowState.EXCLUSIVE.toString() %>';
							url = urlPieces[0];
						}

						A.io.request(
							url,
							{
								data: params,
								dataType: 'html',
								on: {
									failure: function(event, id, obj) {
										var portlet = A.one('#p_p_id<portlet:namespace />');
										portlet.hide();
										portlet.placeAfter('<div class="portlet-msg-error"><liferay-ui:message key="there-was-an-unexpected-error.-please-refresh-the-current-page"/></div>');
									},
									success: function(event, id, obj) {
										searchContainer.setContent(A.Node.create(this.get('responseData')).one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container').getContent ());
										<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A);
										searchContainer.fire('ajaxLoaded');
									}
								}
							}
						);
					}
				}
				
				<portlet:namespace /><%= searchContainer.getCurParam() %>updateCur = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
					%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getCurParam()) 
						%>&<%= renderResponse.getNamespace() + searchContainer.getCurParam() %>=' + A.one(box).val());
				};

				<portlet:namespace /><%= searchContainer.getDeltaParam() %>updateDelta = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
						%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getDeltaParam()) 
							%>&<%= renderResponse.getNamespace() + searchContainer.getDeltaParam() %>=' + A.one(box).val());
				};

				searchContainer.all('.taglib-page-iterator').each(
					function(pageIterator){
						pageIterator.all('a').each(
							function(anchor){
								var url=anchor.get('href');
								anchor.set('href','#');
							    anchor.on('click',
									function(){
							    		<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url);
								    }
							    );
							}
						);
					}
				);

			};

			AUI().ready('aui-io-request','querystring-parse','aui-parse-content',<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer);
		//-->
		</script>

	</liferay-ui:search-container>
</aui:form>

<c:if test="<%=!courseEvalModel.has(\"firedDate\") %>">
	<aui:button-row>
		
		<button name="Save" value="save" onclick="<portlet:namespace />doSaveEvaluations();" type="button">
			<liferay-ui:message key="offlinetaskactivity.save" />
		</button>
		<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupEvaluations();" type="button">
			<liferay-ui:message key="offlinetaskactivity.cancel" />
		</button>
	</aui:button-row>
</c:if>
<div id="<portlet:namespace />evaluationResult" class="<%=(SessionErrors.contains(renderRequest, "evaluationAvg.bad-updating"))?
									   "portlet-msg-error":((SessionMessages.contains(renderRequest, "evaluationAvg.updating"))?
									   "portlet-msg-success":StringPool.BLANK) %>">
	<%=(SessionErrors.contains(renderRequest, "evaluationAvg.bad-updating"))?LanguageUtil.get(pageContext,"evaluationAvg.bad-updating"):
	    ((SessionMessages.contains(renderRequest, "evaluationAvg.updating"))?LanguageUtil.get(pageContext,"evaluationAvg.updating"):StringPool.BLANK) %>
</div>
