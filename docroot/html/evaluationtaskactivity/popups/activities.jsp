<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.util.StringBundler"%>
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
	long actId = ParamUtil.getLong(request,"actId",0);
	boolean showPassPuntuation = ParamUtil.getBoolean(renderRequest, "showPassPuntuation");
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();

	JSONObject courseEvalModel = JSONFactoryUtil.createJSONObject();
	JSONArray activities = JSONFactoryUtil.createJSONArray();
	courseEvalModel.put("activities", activities);
	boolean hasFiredDate=false;
	try{
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {
			Element activitiesElement = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement().element("activities");
			
			if(activitiesElement!=null){
				Iterator<Element> activitiesElementItr = activitiesElement.elementIterator();
				while(activitiesElementItr.hasNext()) {
					Element activity =activitiesElementItr.next();
					if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
						try{
							JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
							jsonObject.put("id", Long.valueOf(activity.attribute("id").getValue()));
							jsonObject.put("weight", Long.valueOf(activity.getText()));
							activities.put(jsonObject);
						}
						catch(NumberFormatException e){}
					}
				}				
			}
			Element rootElement = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement();
			hasFiredDate = rootElement.element("firedDate")!=null;
		}
	}
	catch(Throwable e){
	}
	List<Module> moduleList = (List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	long moduleId=ParamUtil.get(request, "currModuleId",ParamUtil.get(request, "moduleId",GetterUtil.DEFAULT_LONG));
	if((!moduleList.isEmpty())&&(Validator.isNull(moduleId))){
		moduleId = moduleList.get(0).getModuleId();
	}
	JSONArray moduleItems = JSONFactoryUtil.createJSONArray();
	for(Module module:moduleList){
		JSONObject jsonModule = JSONFactoryUtil.createJSONObject();
		jsonModule.put("id", renderResponse.getNamespace()+"moduleTab_"+module.getModuleId());
		jsonModule.put("label", module.getTitle(themeDisplay.getLocale()));
		jsonModule.put("active", module.getModuleId()==moduleId);	
		moduleItems.put(jsonModule);
	}
		
	PortletURL viewActivitiesURL = renderResponse.createRenderURL();
	viewActivitiesURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
	viewActivitiesURL.setParameter("jspPage","/html/evaluationtaskactivity/popups/activities.jsp");
%>

<script type="text/javascript">
<!--

	function <portlet:namespace />doClosePopupActivities()
	{
	    AUI().use('aui-dialog', function(A) {
	    	A.DialogManager.closeByChild('#<portlet:namespace />showPopupActivities');
	    });
	}

	YUI.add('<portlet:namespace />eval-model', function(A) {
	    A.<portlet:namespace />ActivitieModel = A.Base.create('<portlet:namespace />ActivitiesModel', A.Model, [], {
	    }, {
	        ATTRS: {
				weight: {
					value: 0
				}
	        }
	    });

	    A.<portlet:namespace />ActivitiesModelList = A.Base.create('<portlet:namespace />ActivitiesModelList', A.ModelList, [], {
	        comparator: function (model) {
	            return model.get('id');
	        },
	        model: A.<portlet:namespace />ActivitieModel
	    });
	
	}, '' ,{requires:['model-list']});

	function <portlet:namespace />addActivities(evalId, evalWeight){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingActivities=window.<portlet:namespace />selectedActivities.getById(evalId);
			if(existingActivities!=null){
				window.<portlet:namespace />selectedActivities.remove(existingActivities);
			}	
			window.<portlet:namespace />selectedActivities.add(
					new A.<portlet:namespace />ActivitieModel({id:evalId,weight:evalWeight}));
		});			
	}
	
	function <portlet:namespace />deleteActivities(evalId){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingActivities=window.<portlet:namespace />selectedActivities.getById(evalId);
			if(existingActivities!=null){
				window.<portlet:namespace />selectedActivities.remove(existingActivities);
			}		
		});		
	}

	function <portlet:namespace />createValidator(){
		AUI().use('aui-form-validator', function(A) {

			if(!!window.<portlet:namespace />validateActivities){
				delete window.<portlet:namespace />validateActivities;
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
		        		required: Liferay.Language.get("evaluationtaskactivity.passPuntuation.required"),
		        		number: Liferay.Language.get("evaluationtaskactivity.passPuntuation.number"),
		        		range: Liferay.Language.get("evaluationtaskactivity.passPuntuation.range")     		
		            }
				};

			A.all('#<portlet:namespace />activities * input').each(
				function(evaluationWeight){
					if(evaluationWeight.get('id').indexOf('<portlet:namespace />weight_')==0){
						
						rules[evaluationWeight.get('id')] = {
								number: true,
								range: [0,100]
							};

						fieldStrings[evaluationWeight.get('id')] = {
								number: Liferay.Language.get("evaluationtaskactivity.weight.number"),
				        		range: Liferay.Language.get("evaluationtaskactivity.weight.range")  
							};

						evaluationWeight.on('input',function(){
								if(evaluationWeight.get('value')=='') {
									var divError = A.one('#'+evaluationWeight.get('name')+'Error');
									divError.removeClass('portlet-msg-error');
									divError.setContent('');
									<portlet:namespace />deleteActivities(evalId);
								}
								else{
									window.<portlet:namespace />validateActivities.validateField(evaluationWeight.get('name'));
									var evalId=evaluationWeight.get('id').substring('<portlet:namespace />weight_'.length);
									if(!window.<portlet:namespace />validateActivities.getFieldError(window.<portlet:namespace />validateActivities.getField(evaluationWeight.get('name')))){	
										<portlet:namespace />addActivities(evalId, evaluationWeight.get('value'));
									}
									else {
										<portlet:namespace />deleteActivities(evalId);
									}
								}
								var divResult = A.one('#<portlet:namespace />evaluationResult');
								divResult.removeClass('portlet-msg-error');
								divResult.removeClass('portlet-msg-success');
								divResult.setContent('');
							});
					}
			});
					
			window.<portlet:namespace />validateActivities = new A.FormValidator({
				boundingBox: '#<portlet:namespace />activities',
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

	function <portlet:namespace />validatelearningActivitiesSearchContainerSearchContainer(sourceEvent){
		var _return=true;

		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			if(window.<portlet:namespace />validateActivities.hasErrors()) {
				_return=confirm('<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "evaluationtaskactivity.confirm.message")) %>');
			}
		});		

		if((_return==false)&&(sourceEvent!=null)&&(!!sourceEvent.preventDefault)){
			sourceEvent.preventDefault();
		}
		
		return _return;	
	}

	function <portlet:namespace />doSaveActivities(){
		AUI().use('node-base','json-stringify','<portlet:namespace />eval-model','aui-io-request', function(A) {
			if(!window.<portlet:namespace />validateActivities.hasErrors()){
                var output= new Object();	
				<% if(showPassPuntuation) { %>
				   output['passPuntuation']=A.one('#<portlet:namespace />passPuntuation').get('value');
				<% }%>

				var outputActivities= new Array();
				var itrOutputActivities=0;
				window.<portlet:namespace />selectedActivities.each(
					function(evalModel){
						outputActivities[itrOutputActivities++]={id:evalModel.get('id'),weight:evalModel.get('weight')};
					}
				);	
				output['activities']=outputActivities;

				A.io.request(
						'<portlet:actionURL name="saveEvalModel" />',
						{
							data: {model:A.JSON.stringify(output)},
							dataType: 'json',
							on: {
								failure: function(event, id, obj) {
									var portlet = A.one('#p_p_id<portlet:namespace />');
									portlet.hide();
									portlet.placeAfter('<div class="portlet-msg-error">'+Liferay.Language.get("there-was-an-unexpected-error.-please-refresh-the-current-page")+'</div>');
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
										divResult.setContent(Liferay.Language.get("evaluationAvg.error.incorrectNumber"));
									}
									//divResult.setContent('');
									
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

	AUI().ready('node-base','<portlet:namespace />eval-model','aui-tabs', function(A) {

		window.<portlet:namespace />filterlearningActivitiesSearchContainerSearchContainer = {<portlet:namespace />currModuleId:<%=moduleId%>};

		window.<portlet:namespace />selectedActivities = new A.<portlet:namespace />ActivitiesModelList();
		window.<portlet:namespace />selectedActivities.add(<%=courseEvalModel.getJSONArray("activities").toString() %>);

		var searchContainer = A.one('#<%=renderResponse.getNamespace() %>learningActivitiesSearchContainerSearchContainer').ancestor('.lfr-search-container');
	
	    var tabView = new A.TabView(
	    	      {
	    	    	 items: <%=moduleItems.toString() %>,
	    	  		 contentBox: '#moduleTabs',
	    	  		 width:770
	    	      }
	    	    ).render();

		tabView.on(
				'activeTabChange',
				function(activeTabChangeEvent) {
					var moduleId = activeTabChangeEvent.details[0].newVal.get('id').substring(<%=(renderResponse.getNamespace()+"moduleTab_").length() %>);
					window.<portlet:namespace />filterlearningActivitiesSearchContainerSearchContainer = {<portlet:namespace />currModuleId:moduleId};
					searchContainer.fire('refreshEvaluation',activeTabChangeEvent);
				}
			);

		searchContainer.on('ajaxLoaded',function(sourceEvent){

			window.<portlet:namespace />selectedActivities.each(
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

		searchContainer.fire('ajaxLoaded',null);
	});

//-->
</script>

<aui:form name="activities" role="form">
	<% if(showPassPuntuation) {
	%>
		<aui:fieldset>
		    <aui:input type="text" name="passPuntuation" label="evaluationtaskactivity.passPuntuation"
		               value='<%= learningActivity.getPasspuntuation() %>' disabled='<%=hasFiredDate %>' />
		    <div id="<portlet:namespace />passPuntuationError" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.passPuntuation.result-bad-format"))?
		    														"portlet-msg-error":StringPool.BLANK %>">
		    	<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.passPuntuation.result-bad-format"))?
		    			LanguageUtil.get(pageContext,"evaluationAvg.passPuntuation.result-bad-format"):StringPool.BLANK %>
		    </div>
		</aui:fieldset>
	<% } %>
	<div id="moduleTabs"></div>
	
	<liferay-util:buffer var="weightHelp">
		<liferay-ui:icon-help message="evaluationtaskactivity.evaluation.weight.help" />
	</liferay-util:buffer>

	<liferay-ui:search-container iteratorURL="<%=viewActivitiesURL%>" emptyResultsMessage="there-are-no-results" delta="5" deltaConfigurable="true" >

	   	<liferay-ui:search-container-results>
			<%
				List<LearningActivity> notTeamActivities = LearningActivityLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
						add(PropertyFactoryUtil.forName("moduleId").eq(moduleId)).
			    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
			    	    add(PropertyFactoryUtil.forName("typeId").ne(8)));
				List<LearningActivity> lactivities = new ArrayList<LearningActivity>();
				for(LearningActivity la: notTeamActivities)
				{
					String team = LearningActivityLocalServiceUtil.getExtraContentValue(la.getActId(),"team");
					if(StringPool.BLANK.equals(team)){
						lactivities.add(la);
					}
				}
				pageContext.setAttribute("results",ListUtil.subList(lactivities, searchContainer.getStart(), searchContainer.getEnd()));
			    pageContext.setAttribute("total",lactivities.size());			
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity" keyProperty="actId" modelVar="activityToEvaluate">
			<liferay-ui:search-container-column-text name="<%=LanguageUtil.get(pageContext, \"evaluationtaskactivity.evaluation.name\") %>" translate="false">
				<%=activityToEvaluate.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="<%=LanguageUtil.get(pageContext, \"evaluationtaskactivity.evaluation.type\") %>" translate="false" cssClass="col-type" >
				<span class="activity-<%=activityToEvaluate.getTypeId() %>" ><liferay-ui:message key="<%=learningActivityTypeRegistry.getLearningActivityType(activityToEvaluate.getTypeId()).getName() %>" /></span>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="<%=LanguageUtil.get(pageContext, \"evaluationtaskactivity.evaluation.weight\") %>" translate="false" >
				<aui:input type="text" label="<%=weightHelp %>" inlineLabel="right" name="<%=\"weight_\"+activityToEvaluate.getActId() %>"  size="3" disabled="<%=hasFiredDate%>"/>
				<div id="<portlet:namespace />weight_<%=activityToEvaluate.getActId() %>Error" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.weight_"+activityToEvaluate.getActId()+".bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    														<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.weight_"+activityToEvaluate.getActId()+".bad-format"))?
	    															LanguageUtil.get(pageContext,"evaluationtaskactivity.weight.bad-format"):StringPool.BLANK %>
	    		</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

	 	<liferay-ui:search-iterator />
		
		<script type="text/javascript">
		<!--
			function <portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A) {

				var searchContainer = A.one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
				
				function <portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(sourceEvent,url){
				     
					if((!!<portlet:namespace />validate<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer)&&
					     (<portlet:namespace />validate<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(sourceEvent))) {
						var params = {};
						var urlPieces = url.split('?');
						if (urlPieces.length > 1) {
							params = A.QueryString.parse(urlPieces[1]);
							params.p_p_state='<%=LiferayWindowState.EXCLUSIVE.toString() %>';
							url = urlPieces[0];
						}

						if(window.<portlet:namespace />filter<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer){
							A.mix(params,window.<portlet:namespace />filter<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer,true);
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
										portlet.placeAfter('<div class="portlet-msg-error">'+Liferay.Language.get("there-was-an-unexpected-error.-please-refresh-the-current-page")+'</div>');
									},
									success: function(event, id, obj) {

										var responseSearchContainer = A.Node.create(this.get('responseData')).one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
										if(responseSearchContainer.hasClass('aui-helper-hidden')){
											if(!searchContainer.hasClass('aui-helper-hidden')){
												searchContainer.addClass('aui-helper-hidden');
												var noResults = searchContainer.ancestor().one('.portlet-msg-info');												
												if(noResults){
													noResults.removeClass('aui-helper-hidden'); 
												}
												else{
													searchContainer.ancestor().appendChild(A.Node.create('<div class=\'portlet-msg-info\' ><%=LanguageUtil.get(pageContext,searchContainer.getEmptyResultsMessage()) %></div>')); 
												}
											}

										}
										else {
											if(searchContainer.hasClass('aui-helper-hidden')){
												searchContainer.removeClass('aui-helper-hidden');
												var noResults = searchContainer.ancestor().one('.portlet-msg-info');												
												if(noResults){
													noResults.addClass('aui-helper-hidden'); 
												}
											}
										}

										searchContainer.setContent(responseSearchContainer.getContent());
										<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A);
										searchContainer.fire('ajaxLoaded',sourceEvent);
									}
								}
							}
						);
					}
				}

				searchContainer.detach('refreshEvaluation');
				searchContainer.on('refreshEvaluation',
					function(sourceEvent) {
						<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
						%>SearchContainer(sourceEvent,'<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getCurParam()) 
							%>');
					}

				);
				
				<portlet:namespace /><%= searchContainer.getCurParam() %>updateCur = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
					%>SearchContainer(box,'<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getCurParam()) 
						%>&<%= renderResponse.getNamespace() + searchContainer.getCurParam() %>=' + A.one(box).val());
				};

				<portlet:namespace /><%= searchContainer.getDeltaParam() %>updateDelta = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
						%>SearchContainer(box,'<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getDeltaParam()) 
							%>&<%= renderResponse.getNamespace() + searchContainer.getDeltaParam() %>=' + A.one(box).val());
				};

				searchContainer.all('.taglib-page-iterator').each(
					function(pageIterator){
						pageIterator.all('a').each(
							function(anchor){
								var url=anchor.get('href');
								anchor.set('href','#');
							    anchor.on('click',
									function(clickEvent){
							    		<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(clickEvent,url);
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

<c:if test="<%=!hasFiredDate %>">
	<aui:button-row>
		<button name="Save" value="save" onclick="<portlet:namespace />doSaveActivities();" type="button">
			<liferay-ui:message key="evaluationtaskactivity.save" />
		</button>
		<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupActivities();" type="button">
			<liferay-ui:message key="evaluationtaskactivity.cancel" />
		</button>
	
	</aui:button-row>
</c:if>
<div id="<portlet:namespace />evaluationResult" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.bad-updating"))?
									   "portlet-msg-error":((SessionMessages.contains(renderRequest, "evaluationtaskactivity.updating"))?
									   "portlet-msg-success":StringPool.BLANK) %>">
	<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.bad-updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.bad-updating"):
	    ((SessionMessages.contains(renderRequest, "evaluationtaskactivity.updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.updating"):StringPool.BLANK) %>
</div>
