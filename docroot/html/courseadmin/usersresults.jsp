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

<%
PortletPreferences preferences = null;
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
preferences = renderRequest.getPreferences();
}
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
Role role=RoleLocalServiceUtil.getRole(roleId);
Course course=CourseLocalServiceUtil.getCourse(courseId);
boolean backToEdit = ParamUtil.getBoolean(request, "backToEdit");
String redirectOfEdit = ParamUtil.getString(request, "redirectOfEdit");
String firstName = ParamUtil.getString(request,"firstName");
String lastName = ParamUtil.getString(request,"lastName");
String screenName = ParamUtil.getString(request,"screenName");	
String emailAddress = ParamUtil.getString(request,"emailAddress");
boolean andSearch = ParamUtil.getBoolean(request,"andSearch",true);

LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(locale);
String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(locale);
String tab="";
if(roleId==commmanager.getRoleId()){
	tab =  LanguageUtil.get(pageContext,"courseadmin.adminactions.students");
}else if(roleId==prefs.getEditorRole()){
	tab = editorName;
}else{
	tab = teacherName;
}

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/usersresults.jsp");
//portletURL.setParameter("paginator","true");	
portletURL.setParameter("firstName", firstName); 
portletURL.setParameter("lastName", lastName);
portletURL.setParameter("screenName", screenName);
portletURL.setParameter("emailAddress", emailAddress);
portletURL.setParameter("andSearch",Boolean.toString(andSearch));
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("roleId",Long.toString(roleId));
portletURL.setParameter("backToEdit",Boolean.toString(backToEdit));
if(backToEdit) {
	portletURL.setParameter("backToEdit",redirectOfEdit);
}

%>


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
				/* A.one('#<portlet:namespace />selected_users').setContent("TODOS"); */
				A.one('#<portlet:namespace />selected_users').setContent('<li class="yui3-widget aui-component aui-textboxlistentry aui-textboxlistentry-focused" tabindex="0"><span class="aui-textboxlistentry-content"><span class="aui-textboxlistentry-text">&nbsp;&nbsp;&nbsp;&nbsp;Todos</span></span></li>');
			} else {
				A.one('#<portlet:namespace />selected_users').setContent(selectedUsers);
			}
			
			A.one('#<portlet:namespace />to').val(window.<portlet:namespace />selectedUsers.get('id').toString());
			A.all('#<portlet:namespace />addUser_'+userId).each(function(addUserDiv){ addUserDiv.hide(); });  
			A.all('#<portlet:namespace />deleteUser_'+userId).each(function(deleteUserDiv){ deleteUserDiv.show(); });		
			
			var addUserElement = A.one('#_courseadmin_WAR_liferaylmsportlet_addUser_'+userId);
			if (addUserElement != null) {
				addUserElement.ancestor('tr').addClass('taglib-search-iterator-highlighted');
			}
			
			var checkIfEmpty = A.one('#_courseadmin_WAR_liferaylmsportlet_to').val();
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
			A.all('#<portlet:namespace />deleteUser_'+userId).each(function(deleteUserDiv){ deleteUserDiv.hide(); });
			
			var addUserElement = A.one('#_courseadmin_WAR_liferaylmsportlet_addUser_'+userId);
			if (addUserElement != null) {
				addUserElement.ancestor('tr').removeClass('taglib-search-iterator-highlighted');
			}
			
			var checkIfEmpty = A.one('#_courseadmin_WAR_liferaylmsportlet_to').val();			
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
				A.one('#<portlet:namespace />selected_users').setContent('<liferay-ui:message key="all"/>');
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
		A.one('.assign-form').hide();
		
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
	<portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp" />
	<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
	<portlet:param name="roleId" value="<%=Long.toString(roleId) %>" />
	<portlet:param name="tabs1" value="<%=tab %>" />
	<portlet:param name="backToEdit" value="<%=Boolean.toString(backToEdit) %>" />
	<c:if test="<%=backToEdit %>">
		<portlet:param name="redirectOfEdit" value='<%=redirectOfEdit %>'/>
	</c:if>
</liferay-portlet:renderURL>
<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>
<h2><%=role.getTitle(themeDisplay.getLocale()) %></h2>
<jsp:include page="/html/courseadmin/search_form.jsp" />

<div id="<portlet:namespace />student_search">

	<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true"  >
	
	   	<liferay-ui:search-container-results>
	   	
	   	
	<%
		String middleName = null;
	
		if (Validator.isNull(firstName)) {
			firstName = null;
		}
		else {
			firstName = addWildcards(firstName);
		}
		
		if (Validator.isNull(lastName)) {
			lastName = null;
		}
		else {
			lastName = addWildcards(lastName);
		}
		
		if (Validator.isNull(screenName)) {
			screenName = null;
		}
		else {
			screenName = addWildcards(screenName);
		}
		
		if (Validator.isNull(emailAddress)) {
			emailAddress = null;
		}
		else {
			emailAddress = addWildcards(emailAddress);
		}
		
		LinkedHashMap<String,Object> params=new LinkedHashMap<String,Object>();			
		
	
		OrderByComparator obc = new UserFirstNameComparator(true);
		
		params.put("notInCourseRole",new CustomSQLParam("WHERE User_.userId NOT IN "+
		                                                " (SELECT UserGroupRole.userId "+
		                                                "  FROM UserGroupRole "+
		                                                "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))",new Long[]{course.getGroupCreatedId(),roleId}));
		/*
			if (new Long(roleId).equals(prefs.getTeacherRole()) || new Long(roleId).equals(prefs.getEditorRole())) {
				params.put("inRole", new CustomSQLParam("WHERE User_.userId IN (SELECT UserGroupRole.userId "+
		            "  FROM UserGroupRole "+
		            "  WHERE UserGroupRole.roleId = ?)", new Long[]{roleId}));
			}
		*/
		boolean showOnlyOrganizationUsers = preferences.getValue("showOnlyOrganizationUsers", "false").equals("true");
		List <User> userListPage = new LinkedList<User>();
		long usersLimit = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId()).getUsersResults();
		
		if (showOnlyOrganizationUsers) {
			
			if (organization != null) {
				//System.out.println("Muestro los usuarios de la organización");
				//System.out.println(organization.getOrganizationId());
				//System.out.println(organization.getName());
				userListPage = UserLocalServiceUtil.getOrganizationUsers(organization.getOrganizationId());
				/* pageContext.setAttribute("results", userListPage); */
				pageContext.setAttribute("results", ListUtil.subList(userListPage, searchContainer.getStart(), searchContainer.getEnd()));
		    	pageContext.setAttribute("total", userListPage.size());
			} else {
				List<Organization> organizationsOfUerList = themeDisplay.getUser().getOrganizations();
				Iterator<Organization> it = organizationsOfUerList.iterator();
				List<User> listaUsuariosEnOrganizacion = null;
				while (it.hasNext()) {
					Organization org = it.next();
					listaUsuariosEnOrganizacion = UserLocalServiceUtil.getOrganizationUsers(org.getOrganizationId());
					Iterator<User> it2 = listaUsuariosEnOrganizacion.iterator();
					while (it2.hasNext()) {
						
						userListPage.add(it2.next());
					}
				}
				/* pageContext.setAttribute("results", userListPage); */
				pageContext.setAttribute("results", ListUtil.subList(userListPage, searchContainer.getStart(), searchContainer.getEnd()));
	    		pageContext.setAttribute("total", userListPage.size());
			}

			//OrganizationLocalServiceUtil.getOrganization(companyId, name);
		} else {
			/* userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, 0, params, andSearch, searchContainer.getStart(), searchContainer.getEnd(), obc); */
			userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, 0, params, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
			int userCount =  UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, 0, params, andSearch);
			
			/* pageContext.setAttribute("results", userListPage);  */
			pageContext.setAttribute("results", ListUtil.subList(userListPage, searchContainer.getStart(), searchContainer.getEnd()));
		    pageContext.setAttribute("total", userCount);

		}
		
		if (userListPage.size() > usersLimit){
			pageContext.setAttribute("results", null);
		    pageContext.setAttribute("total", 0);
		   	searchContainer.setEmptyResultsMessage(LanguageUtil.format(pageContext,"there-are-many-results", new Object[]{usersLimit}));
		}
		else{
			for(User us:userListPage){
				
				%> <input type="hidden" name="studentIdNameHidden" value='<%= us.getUserId() + "," + us.getFullName() %>' /> <%
					
			} 
		}
	
	%>
		<input type="hidden" id="allSelected" value="false" />
		</liferay-ui:search-container-results>
		
		<div class="container-buttons">
			<aui:input type="checkbox" name="all" label="misc.search.all" checked="<%=false %>" onClick="setAllStudents()" style="Cursor:pointer;"/>
		</div>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User"
	     		keyProperty="userId"
	     		modelVar="user">
	<liferay-ui:search-container-column-text name="user">
	<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
	</liferay-ui:search-container-column-text> 
	<liferay-ui:search-container-column-text>
		<a id="<portlet:namespace />addUser_<%=user.getUserId() %>" onClick="<portlet:namespace />addUser(<%=user.getUserId() %>, '<%=user.getFullName() %>', false)" style="Cursor:pointer;" >
		<liferay-ui:message key="select" /></a>
		<a id="<portlet:namespace />deleteUser_<%=user.getUserId() %>" class="aui-helper-hidden" onClick="<portlet:namespace />deleteUser(<%=user.getUserId() %>, false)" style="Cursor:pointer;" >
		<liferay-ui:message key="groupmailing.deselect" /></a>	
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
	
		function setAllStudents(){
			var allUsers = document.getElementsByName("studentIdNameHidden");
			var allSelected =new String(document.getElementById("allSelected").value);
			var todos = true;
			if(allSelected=="false"){				
				for(var i = 0; i < allUsers.length; i++) {
				    var userData = allUsers[i].value.split(",");
				    var userId = userData[0];
				    var userName = userData[1];
				    /* <portlet:namespace />addUser(userId,userName); */
					<portlet:namespace />addUser(userId,userName,todos);
				};
				document.getElementById("allSelected").value='true';
			}
			else
			{
				for(var i = 0; i < allUsers.length; i++) {
				    var userData = allUsers[i].value.split(",");
				    var userId = userData[0];
				    var userName = userData[1];
				    /* <portlet:namespace />deleteUser(userId,userName); */
					<portlet:namespace />deleteUser(userId,todos);
				};
				document.getElementById("allSelected").value='false';			
			}
		};
	
			</script>
	</liferay-ui:search-container>
	
	<liferay-portlet:actionURL name="addUserRole" var="addUserRoleURL">
		<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp"/>
		<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"/>
		<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
		<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"/>
		<liferay-portlet:param name="tabs1" value="<%=tab %>"/>
		<liferay-portlet:param name="backToEdit" value="<%=Boolean.toString(backToEdit) %>" />
		<c:if test="<%=backToEdit %>">
			<liferay-portlet:param name="redirectOfEdit" value='<%=redirectOfEdit %>'/>
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
<%!
	public static String addWildcards(String value)
	{
		if (value == null) return null;
		if (value.length() == 1) return "%" + value + "%";
		if (value.charAt(0) != '%') value = "%" + value;
		if (value.charAt(value.length() - 1) != '%') value = value + "%";
		return value;
	}
%>
