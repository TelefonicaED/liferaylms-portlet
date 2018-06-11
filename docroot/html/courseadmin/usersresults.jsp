<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.liferay.lms.util.searchcontainer.UserSearchContainer"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %>


<script type="text/javascript">



	YUI.add('<portlet:namespace />user-model', function(A) {
	    A.<portlet:namespace />UserModel = A.Base.create('<portlet:namespace />UserModel', A.Model, [], {
	    }, {
	        ATTRS: {
	            name: {
	                value: ''
	            }
	        }
	    });

	    A.<portlet:namespace />UserModelList = A.Base.create('<portlet:namespace />UserModelList', A.ModelList, [], {
	        comparator: function (model) {
	            return model.get('name');
	        },
	        model: A.<portlet:namespace />UserModel
	    });
	
	}, '' ,{requires:['model-list']});
	
	
	function <portlet:namespace />addUser(userId, userName, todos){
		AUI().use('node-base','<portlet:namespace />user-model', function(A) {
			var existingUser=window.<portlet:namespace />selectedUsers.getById(userId);
			if(existingUser!=null){
				window.<portlet:namespace />selectedUsers.remove(existingUser);
			}
			window.<portlet:namespace />selectedUsers.add(
					new A.<portlet:namespace />UserModel({id:userId,name:'<li class="yui3-widget aui-component aui-textboxlistentry aui-textboxlistentry-focused" tabindex="0"><span class="aui-textboxlistentry-content"><span class="aui-textboxlistentry-text">'+userName+'<span class="aui-icon aui-icon-close aui-textboxlistentry-close" onClick="<portlet:namespace />deleteUser('+userId+','+todos+')" /></span></span></li>'}));
			
			
			var selectedUsers = '';
			window.<portlet:namespace />selectedUsers.each(function(value){selectedUsers+=value.get('name'); });
			if(todos) {
				A.one('#<portlet:namespace />selected_users').setContent('<li class="yui3-widget aui-component aui-textboxlistentry aui-textboxlistentry-focused" tabindex="0"><span class="aui-textboxlistentry-content"><span class="aui-textboxlistentry-text">&nbsp;&nbsp;&nbsp;&nbsp;Todos</span></span></li>');
			} else {
				A.one('#<portlet:namespace />selected_users').setContent(selectedUsers);
			}
			
			A.one('#<portlet:namespace />to').val(window.<portlet:namespace />selectedUsers.get('id').toString());
			A.all('#<portlet:namespace />addUser_'+userId).each(function(addUserDiv){addUserDiv.hide(); });  
			A.all('#<portlet:namespace />deleteUser_'+userId).each(function(deleteUserDiv){ deleteUserDiv.show(); });		
			
			var addUserElement = A.one('#<portlet:namespace />addUser_'+userId);
			if (addUserElement != null) {
				addUserElement.ancestor('tr').addClass('taglib-search-iterator-highlighted');
			}
			
			var checkIfEmpty = A.one('#<portlet:namespace />to').val();
 			if (checkIfEmpty != "") {
 				var x = document.getElementsByClassName("assign-form");
 				if(x[0].style.display == "none"){
 					x[0].style.display = ""
 				}
 				A.one('.assign-form').show();
			} else {
				A.one('.assign-form').hide();
			}
		});			
	}
	
	
	function <portlet:namespace />deleteUser(userId,todos){
		AUI().use('node-base','<portlet:namespace />user-model', function(A) {
			var existingUser=window.<portlet:namespace />selectedUsers.getById(userId);
			if(existingUser!=null){
				window.<portlet:namespace />selectedUsers.remove(existingUser);
			}	
			
			var selectedUsers = '';
			window.<portlet:namespace />selectedUsers.each(function(value){selectedUsers+=value.get('name');});
			A.one('#<portlet:namespace />selected_users').setContent(selectedUsers);
			A.one('#<portlet:namespace />to').val(window.<portlet:namespace />selectedUsers.get('id').toString());
			A.all('#<portlet:namespace />addUser_'+userId).each(function(addUserDiv){ addUserDiv.show(); });  
			A.all('#<portlet:namespace />deleteUser_'+userId).each(function(deleteUserDiv){deleteUserDiv.hide(); });
			
			var addUserElement = A.one('#<portlet:namespace />addUser_'+userId);
			if (addUserElement != null) {
				addUserElement.ancestor('tr').removeClass('taglib-search-iterator-highlighted');
			}
			
			var checkIfEmpty = A.one('#<portlet:namespace />to').val();			
 			if (checkIfEmpty != "") {
 				A.one('.assign-form').show();
			} else {
				A.one('.assign-form').hide();
			}
		});		
	}
	
	function <portlet:namespace />changeSelection(){
		
		AUI().use('node-base','<portlet:namespace />user-model', function(A) {
			if (A.one('input:radio[name=<portlet:namespace />radio_to]:checked').get('value')=='all') {
				window.<portlet:namespace />selectedUsers.each(
					function(userModel){
						A.all('#<portlet:namespace />addUser_'+userModel.get('id')).each(function(addUserDiv){ addUserDiv.show(); });  
						A.all('#<portlet:namespace />deleteUser_'+userModel.get('id')).each(function(deleteUserDiv){ deleteUserDiv.hide(); });  
					}
				);
				window.<portlet:namespace />selectedUsers.reset();
				A.one('#<portlet:namespace />to').val ('');
				A.one('#<portlet:namespace />selected_users').setContent(Liferay.Language.get("all"));
				A.one('#<portlet:namespace />student_search').hide();
				A.one('#<portlet:namespace />search_box').hide();
			}
			else if (A.one('input:radio[name=<portlet:namespace />radio_to]:checked').get('value')=='student') {
				A.one('#<portlet:namespace />selected_users').setContent('');
				A.one('#<portlet:namespace />student_search').show();
				A.one('#<portlet:namespace />search_box').show();
			}
			
		});
		
	}

	AUI().ready('node-base','<portlet:namespace />user-model', function(A) {
		// Escondamos por defecto el formulario
		var form = document.getElementsByClassName("assign-form");
		form[0].style.display = 'none';
		window.<portlet:namespace />selectedUsers = new A.<portlet:namespace />UserModelList();
		
		var searchContainer = A.one('#<%=renderResponse.getNamespace() %>usersSearchContainerSearchContainer').ancestor('.lfr-search-container');
		searchContainer.on('ajaxLoaded',function(){
			window.<portlet:namespace />selectedUsers.each(
				function(userModel){
					A.all('#<portlet:namespace />addUser_'+userModel.get('id')).each(function(addUserDiv){ addUserDiv.hide(); });  
					A.all('#<portlet:namespace />deleteUser_'+userModel.get('id')).each(function(deleteUserDiv){ deleteUserDiv.show(); }); 
					
				}
			);
		});
		
	});
</script>


<liferay-portlet:renderURL var="backURL" >
	<portlet:param name="view" value="role-members-tab" />
	<portlet:param name="courseId" value="${course.courseId}" />
	<portlet:param name="roleId" value="${roleId }" />
	<portlet:param name="tabs1" value="${tab}" />
	<portlet:param name="backToEdit" value="${backToEdit}" />
	<c:if test="${backToEdit}">
		<portlet:param name="redirectOfEdit" value="${redirectOfEdit}"/>
	</c:if>
</liferay-portlet:renderURL>


<liferay-ui:header title="${course.getTitle(themeDisplay.getLocale())}" backURL="${backURL }"></liferay-ui:header>

<c:if test="${not empty role}">
	<h2>${role.getTitle(themeDisplay.getLocale())}</h2>
</c:if>

<liferay-portlet:renderURL var="searchURL" >
	<portlet:param name="view" value="users-results" />
	<portlet:param name="courseId" value="${course.courseId}" />
	<portlet:param name="roleId" value="${roleId }" />
	<portlet:param name="tabs1" value="${tab}" />
	<portlet:param name="backToEdit" value="${backToEdit}" />
	<c:if test="${backToEdit}">
		<portlet:param name="redirectOfEdit" value="${redirectOfEdit}"/>
	</c:if>
</liferay-portlet:renderURL>

<liferay-portlet:actionURL name="addAllUsers" var="addAllUsersURL">
			<liferay-portlet:param name="view" value="role-members-tab"/>
			<liferay-portlet:param name="courseId" value="${course.courseId }"/>
			<liferay-portlet:param name="roleId" value="${roleId }"/>
			<liferay-portlet:param name="tabs1" value="${tab}"/>
			<liferay-portlet:param name="backToEdit" value="${backToEdit}" />
			<c:if test="${backToEdit}">
				<liferay-portlet:param name="redirectOfEdit" value='${redirectOfEdit}'/>
			</c:if>
</liferay-portlet:actionURL>



<aui:form name="fm" action="${searchURL }" method="POST">
	<liferay-ui:search-container searchContainer="${searchContainer}" id="usersSearchContainerSearchContainer"
		iteratorURL="${searchContainer.iteratorURL}" emptyResultsMessage="there-are-no-users"
 		delta="10" deltaConfigurable="true">
	
	
	
		<liferay-ui:search-form page="/html/search/usersSearchform.jsp"
			searchContainer="${searchContainer}"
			servletContext="<%= this.getServletConfig().getServletContext() %>" />

		<div class="container-buttons">
			<aui:button name="buttonAddAll" value="courseadmin.add-all-users" onClick="${renderResponse.getNamespace()}addAllUsers();"/>
		</div> 

		<liferay-ui:search-container-results total="${searchContainer.total }"
			results="${searchContainer.results }" >
			<input type="hidden" id="allSelected" value="false" />
		</liferay-ui:search-container-results>
			
			
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" 	keyProperty="userId" 	modelVar="userToAdd">
		<liferay-ui:search-container-column-text>
			<liferay-ui:user-display userId="${userToAdd.userId}"></liferay-ui:user-display>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="screen-name"	property="screenName">
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text
			name="email-address"	property="emailAddress"	/>
		
		<liferay-ui:search-container-column-text>
				<a id="<portlet:namespace />addUser_${userToAdd.getUserId()}" onClick="<portlet:namespace />addUser(<%=userToAdd.getUserId() %>, '<%=StringEscapeUtils.escapeJavaScript(userToAdd.getFullName()) %>', false)" style="Cursor:pointer;" >
				<liferay-ui:message key="select" /></a>
				<a id="<portlet:namespace />deleteUser_${userToAdd.getUserId()}" class="aui-helper-hidden" onClick="<portlet:namespace />deleteUser(<%=userToAdd.getUserId() %>, false)" style="Cursor:pointer;" >
				<liferay-ui:message key="unselect" /></a>	
		</liferay-ui:search-container-column-text>
			
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
	
	<script type="text/javascript">
			<!--
			
			
				function <portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A) {
					
					
					var searchContainer = A.one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
					
					function <portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url){
	
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
										portlet.placeAfter('<div class="portlet-msg-error">'+Liferay.Language.get("there-was-an-unexpected-error.-please-refresh-the-current-page")+'</div>');
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


<aui:form name="addAllUserFm" action="${addAllUsersURL }" method="POST">
	<aui:input name="addAllUsersScreenName" value="" type="hidden"/>
	<aui:input name="addAllUsersFirstName" value="" type="hidden"/>
	<aui:input name="addAllUsersLastName" value="" type="hidden"/>
	<aui:input name="addAllUsersEmailAddress" value="" type="hidden"/>
	<aui:input name="addAllUsersAdvancedSearch" value="" type="hidden"/>
	<aui:input name="addAllUsersAndSearch" value="" type="hidden"/>
	<aui:input name="addAllUsersKeywords" value="" type="hidden"/>
	<aui:input name="addAllUsersTeam" value="" type="hidden"/>
</aui:form>


		<script type="text/javascript">
			AUI().ready(
			  'aui-tooltip',
			  function(A) {
			    new A.Tooltip(
			      {
			        bodyContent: Liferay.Language.get("courseadmin.tooltip.add-all-users"),
			        trigger: '#<portlet:namespace />buttonAddAll'
			      }
			    ).render();
			  }
			);
			
			function <portlet:namespace />addAllUsers(){
				$('#<portlet:namespace />addAllUsersFirstName').val($('#<portlet:namespace />firstName').val());
				$('#<portlet:namespace />addAllUsersLastName').val($('#<portlet:namespace />lastName').val());
				$('#<portlet:namespace />addAllUsersScreenName').val($('#<portlet:namespace />screenName').val());
				$('#<portlet:namespace />addAllUsersEmailAddress').val($('#<portlet:namespace />emailAddress').val());
				$('#<portlet:namespace />addAllUsersAndSearch').val($('#<portlet:namespace />andOperator').val());
				$('#<portlet:namespace />addAllUsersTeam').val($('#<portlet:namespace />team').val());
				$('#<portlet:namespace />addAllUsersKeywords').val(document.getElementsByName("<portlet:namespace />keywords")[0].value);
				$('#<portlet:namespace />addAllUsersAdvancedSearch').val(document.getElementsByName("<portlet:namespace />advancedSearch")[0].value);
				document.<portlet:namespace />addAllUserFm.submit();
			}
		</script>
	
	<liferay-portlet:actionURL name="addUserRole" var="addUserRoleURL">
		<liferay-portlet:param name="view" value="role-members-tab"/>
		<liferay-portlet:param name="courseId" value="${course.courseId}"/>
		<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
		<liferay-portlet:param name="roleId" value="${roleId }"/>
		<liferay-portlet:param name="tabs1" value="${tab }"/>
		<liferay-portlet:param name="backToEdit" value="${backToEdit }" />
		<c:if test="${backToEdit }">
			<liferay-portlet:param name="redirectOfEdit" value='${redirectOfEdit }'/>
		</c:if>
	</liferay-portlet:actionURL>
	
	<div class="assign-form">
		<aui:form action="<%=addUserRoleURL %>" method="POST">
			<aui:input type="hidden" name="to" id="to" value="" />
				<div class="to">
					<liferay-ui:message key="users-to-be-added" />
					<div class="aui-helper-clearfix aui-textboxlistentry-holder" id="<portlet:namespace />selected_users" ><liferay-ui:message key="no-users"/></div>
				</div>		
				<aui:button-row>
					<aui:button type="submit" value="assign-member" label="assign-member" class="submit"></aui:button>
				</aui:button-row>	
		</aui:form>
	</div>
</div>
