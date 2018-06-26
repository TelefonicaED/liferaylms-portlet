<%@page import="com.liferay.portal.service.LayoutSetLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutSet"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.model.ModelHintsUtil"%>

<%@ include file="/init.jsp" %>

<portlet:actionURL var="savecourseURL" name="saveCourse" />
<liferay-ui:success key="course-saved-successfully" message="successfully-saved" />
<liferay-ui:error key="title-required" message="title-required" />
<liferay-ui:error key="title-empty" message="title-empty" />
<liferay-ui:error key="title-repeated" message="title-repeated" />
<liferay-ui:error key="max-users-violated" message="max-users-violated" />
<liferay-ui:error key="duplicate-course" message="duplicate-course" />
<liferay-ui:error key="courseadmin.new.error.dateinterval" message="courseadmin.new.error.dateinterval" />
<liferay-ui:error key="courseadmin.error.welcmessage.maxlenght" message="courseadmin.error.welcmessage.maxlenght" />
<liferay-ui:error key="friendly-url-error" message="courseadmin.error.friendly-url" />

<%
String calificationTypeExtraContentError = ParamUtil.getString(request, "calificationTypeExtraContentError");
String courseDiplomaError = ParamUtil.getString(request, "courseDiplomaError");
%>
<liferay-ui:error key="calificationTypeExtraContentError" message="<%=calificationTypeExtraContentError %>" />
<liferay-ui:error key="courseDiplomaError" message="<%=courseDiplomaError %>" />
	<%
	String maxLengthTitle = GetterUtil.getString( ModelHintsUtil.getHints(Group.class.getName(), "name").get("max-length"),"");
	String courseTitle = "";
	
	String site = PropsUtil.get("lms.site.types");
	Set<Integer> sites = new HashSet<Integer>();
	if(Validator.isNotNull(site)){
		String[] ssites = site.split(",");
		for(int i=0;i<ssites.length;i++){
			try{
				sites.add(Integer.valueOf(ssites[i]));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	if (sites.isEmpty()) {
		sites.add(GroupConstants.TYPE_SITE_OPEN);
		sites.add(GroupConstants.TYPE_SITE_RESTRICTED);
		sites.add(GroupConstants.TYPE_SITE_PRIVATE);
	}
	
	String maxUsersError= ParamUtil.getString(request,"maxUsersError");
	if(SessionErrors.contains(renderRequest, "newCourseErrors")) { %>
		<div class="portlet-msg-error">
			<% 
				List<String> errors = (List<String>)SessionErrors.get(renderRequest, "newCourseErrors");
			   	if(errors.size()==1) {
				  	%><%=errors.get(0) %><%
			   	}	
			   else {
			%>
				<ul>
				<% for(String error : errors){ %>
				 	<li><%=error %></li>
				<% } %>
				</ul>
			<% }
			%>
		</div>
	<% }
	
	if((maxUsersError!=null&&!"".equals(maxUsersError))){
	%>
		<div class="portlet-msg-error"><%=maxUsersError %></div>
	<%} 

String publishPermission="PUBLISH";
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long courseId=ParamUtil.getLong(request, "courseId",0);
Course course=null;
boolean isCourseChild = false;
long templateParent = 0;
if(request.getAttribute("course")!=null){
	course=(Course)request.getAttribute("course");
}
else{
	if(courseId>0){
		course=CourseLocalServiceUtil.fetchCourse(courseId);
		if(course!=null){
			if(course.getParentCourseId()>0){
				isCourseChild=true;
			}
		}
	}
}	

long count = 0;
long countGroup = 0;
long countParentGroup = 0;
Group groupsel = null;


if(course != null || courseId > 0){
	
	countGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(course.getGroupCreatedId());
	countParentGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(course.getGroupId());
	count = countGroup + countParentGroup;

	groupsel = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
}

String description=ParamUtil.get(request, "description", "");
String icon = ParamUtil.get(request, "icon", "0");
SimpleDateFormat formatDay = new SimpleDateFormat("dd");
formatDay.setTimeZone(timeZone);
SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
formatMonth.setTimeZone(timeZone);
SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
formatYear.setTimeZone(timeZone);
SimpleDateFormat formatHour = new SimpleDateFormat("HH");
formatHour.setTimeZone(timeZone);
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
formatMin.setTimeZone(timeZone);
Date today=new Date(System.currentTimeMillis());
int startDay=ParamUtil.getInteger(request, "startDay", Integer.parseInt(formatDay.format(today)));
int startMonth=ParamUtil.getInteger(request, "startMonth", Integer.parseInt(formatMonth.format(today))-1);
int startYear=ParamUtil.getInteger(request, "startYear", Integer.parseInt(formatYear.format(today)));
int startHour=ParamUtil.getInteger(request, "startHour", Integer.parseInt(formatHour.format(today)));
int startMin=ParamUtil.getInteger(request, "startMin", Integer.parseInt(formatMin.format(today)));
int endDay=ParamUtil.getInteger(request, "stopDay", Integer.parseInt(formatDay.format(today)));
int endMonth=ParamUtil.getInteger(request, "stopMonth", Integer.parseInt(formatMonth.format(today))-1);
int endYear=ParamUtil.getInteger(request, "stopYear", Integer.parseInt(formatYear.format(today))+1);
int endHour=ParamUtil.getInteger(request, "stopHour", Integer.parseInt(formatHour.format(today)));
int endMin=ParamUtil.getInteger(request, "stopMin", Integer.parseInt(formatMin.format(today)));

int startExecutionDay=ParamUtil.getInteger(request, "startExecutionDay", Integer.parseInt(formatDay.format(today)));
int startExecutionMonth=ParamUtil.getInteger(request, "startExecutionMonth", Integer.parseInt(formatMonth.format(today))-1);
int startExecutionYear=ParamUtil.getInteger(request, "startExecutionYear", Integer.parseInt(formatYear.format(today)));
int startExecutionHour=ParamUtil.getInteger(request, "startExecutionHour", Integer.parseInt(formatHour.format(today)));
int startExecutionMin=ParamUtil.getInteger(request, "startExecutionMin", Integer.parseInt(formatMin.format(today)));
int endExecutionDay=ParamUtil.getInteger(request, "stopExecutionDay", Integer.parseInt(formatDay.format(today)));
int endExecutionMonth=ParamUtil.getInteger(request, "stopExecutionMonth", Integer.parseInt(formatMonth.format(today))-1);
int endExecutionYear=ParamUtil.getInteger(request, "stopExecutionYear", Integer.parseInt(formatYear.format(today))+1);
int endExecutionHour=ParamUtil.getInteger(request, "stopExecutionHour", Integer.parseInt(formatHour.format(today)));
int endExecutionMin=ParamUtil.getInteger(request, "stopExecutionMin", Integer.parseInt(formatMin.format(today)));


String summary="";
AssetEntry entry=null;
boolean visibleencatalogo=false;
int type = GroupConstants.TYPE_SITE_OPEN;
long maxUsers = 0;
Group groupCreated = null;
long assetEntryId=0;

PortletPreferences preferences = null;
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
	preferences = renderRequest.getPreferences();
}

boolean showClose 	= preferences.getValue("showClose",  "true").equals("true");
boolean showDelete 	= preferences.getValue("showDelete", "true").equals("true");
boolean showMembers = preferences.getValue("showMembers","true").equals("true");
boolean showExport 	= preferences.getValue("showExport", "true").equals("true");
boolean showImport	= preferences.getValue("showImport", "true").equals("true");
boolean showClone 	= preferences.getValue("showClone",  "true").equals("true");
boolean showGo 		= preferences.getValue("showGo", 	 "true").equals("true");
boolean showRegistrationType = preferences.getValue("showRegistrationType",  "true").equals("true");
boolean showMaxUsers = preferences.getValue("showMaxUsers", "true").equals("true");


boolean isInCourse = Boolean.FALSE;

boolean showCoursePermission = preferences.getValue("showCoursePermission", "true").equals("true");

String welcomeSubject= new String();
String goodbyeSubject = new String();
if(course!=null){
	groupCreated = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
	entry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
	assetEntryId=entry.getEntryId();
	visibleencatalogo=entry.getVisible();
	summary=entry.getSummary();
	courseId=course.getCourseId();
	description=course.getDescription(themeDisplay.getLocale());
	icon = course.getIcon()+"";
	startDay=Integer.parseInt(formatDay.format(course.getStartDate()));
	startMonth=Integer.parseInt(formatMonth.format(course.getStartDate()))-1;
	startYear=Integer.parseInt(formatYear.format(course.getStartDate()));
	startHour=Integer.parseInt(formatHour.format(course.getStartDate()));
	startMin=Integer.parseInt(formatMin.format(course.getStartDate()));
	endDay=Integer.parseInt(formatDay.format(course.getEndDate()));
	endMonth=Integer.parseInt(formatMonth.format(course.getEndDate()))-1;
	endYear=Integer.parseInt(formatYear.format(course.getEndDate()));
	endHour=Integer.parseInt(formatHour.format(course.getEndDate()));
	endMin=Integer.parseInt(formatMin.format(course.getEndDate()));
	
	if(course.getExecutionStartDate()!=null){
		startExecutionDay=Integer.parseInt(formatDay.format(course.getExecutionStartDate()));
		startExecutionMonth=Integer.parseInt(formatMonth.format(course.getExecutionStartDate()))-1;
		startExecutionYear=Integer.parseInt(formatYear.format(course.getExecutionStartDate()));
		startExecutionHour=Integer.parseInt(formatHour.format(course.getExecutionStartDate()));
		startExecutionMin=Integer.parseInt(formatMin.format(course.getExecutionStartDate()));
	}
	if(course.getExecutionEndDate()!=null){
		endExecutionDay=Integer.parseInt(formatDay.format(course.getExecutionEndDate()));
		endExecutionMonth=Integer.parseInt(formatMonth.format(course.getExecutionEndDate()))-1;
		endExecutionYear=Integer.parseInt(formatYear.format(course.getExecutionEndDate()));
		endExecutionHour=Integer.parseInt(formatHour.format(course.getExecutionEndDate()));
		endExecutionMin=Integer.parseInt(formatMin.format(course.getExecutionEndDate()));
	}
	
	type=course.getStatus(); //TODO
	welcomeSubject = course.getWelcomeSubject();
	goodbyeSubject = course.getGoodbyeSubject();
	maxUsers=course.getMaxusers();
	courseTitle = (String)course.getModelAttributes().get("title");
	isInCourse = (course.getGroupCreatedId() == themeDisplay.getScopeGroupId());
	%>
	<aui:model-context bean="<%= course %>" model="<%= Course.class %>" />
	<%
} else {
	%>
	<aui:model-context  model="<%= Course.class %>" />
	<%
}
%>
<liferay-ui:header title="<%= course != null ? course.getTitle(themeDisplay.getLocale()) : \"new-course\" %>" backURL="<%=backURL %>"></liferay-ui:header>
<c:if test="<%=course != null && course.getParentCourseId()<=0%>">
	<aui:fieldset>
		<liferay-ui:icon-menu>
			<%-- Ir al curso --%>
			<%if(showGo && groupsel != null && permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(),course.getCourseId(), ActionKeys.VIEW) && 
					!course.isClosed() && ( PortalUtil.isOmniadmin(themeDisplay.getUserId()) || UserLocalServiceUtil.hasGroupUser(course.getGroupCreatedId(), themeDisplay.getUserId())) && !isInCourse) {%>
				<liferay-ui:icon image="submit" message="courseadmin.adminactions.gotocourse" target="_top" url="<%=themeDisplay.getPortalURL() +\"/\"+ response.getLocale().getLanguage() +\"/web/\"+ groupsel.getFriendlyURL()%>" />
			<%}%>
			<%-- Asignar miembros --%>
			<%if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), courseId, "ASSIGN_MEMBERS") && ! course.isClosed() && showMembers){%>
				<portlet:renderURL var="memebersURL">
					<portlet:param name="courseId" value="<%=String.valueOf(courseId) %>" />
					<portlet:param name="backToEdit" value="<%=StringPool.TRUE %>" />
					<portlet:param name="redirectOfEdit" value='<%=ParamUtil.getString(request, "redirect", currentURL) %>'/>
					<portlet:param name="view" value="role-members-tab" />
				</portlet:renderURL>
				<liferay-ui:icon image="group" message="assign-member" url="<%=memebersURL.toString() %>" />
			<%}%>
			
			<%-- Competencias --%>
			<%if(count>0 && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), courseId,ActionKeys.UPDATE) && !course.isClosed() ){%>
				<portlet:renderURL var="competenceURL">
					<portlet:param name="groupId" value="<%=String.valueOf(course.getGroupCreatedId()) %>" />
					<portlet:param name="courseId" value="<%=String.valueOf(course.getCourseId()) %>" />
					<portlet:param name="view" value="competence-tab" />
				</portlet:renderURL>
				<liferay-ui:icon image="tag" message="competence.label" url="<%=competenceURL %>" />
			<%}%>
				
			<%-- Exportar curso --%>
			<%if(showExport && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),courseId,ActionKeys.UPDATE) && !isInCourse){%>
				<portlet:renderURL var="exportURL">
					<portlet:param name="groupId" value="<%=String.valueOf(course.getGroupCreatedId()) %>" />
					<portlet:param name="view" value="export" />
				</portlet:renderURL>
				<liferay-ui:icon image="download" message="courseadmin.adminactions.export" url="<%=exportURL %>" />	
			<%}%>
			
			<%-- Importar curso --%>
			<%if(showImport && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),courseId,ActionKeys.UPDATE) && !isInCourse){%>
				<portlet:renderURL var="importURL">
					<portlet:param name="groupId" value="<%=String.valueOf(course.getGroupCreatedId()) %>" />
					<portlet:param name="view" value="import" />
				</portlet:renderURL>
				<liferay-ui:icon image="post" message="courseadmin.adminactions.import" url="<%=importURL %>" />	
			<%}%>	
			
			<%-- Duplicar curso --%>
			<%if(showClone && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),courseId,ActionKeys.UPDATE) && !isInCourse){%>
				<portlet:renderURL var="cloneURL">
					<portlet:param name="groupId" value="<%=String.valueOf(course.getGroupCreatedId()) %>" />
					<portlet:param name="view" value="clone" />
				</portlet:renderURL>
				<liferay-ui:icon image="copy" message="courseadmin.adminactions.clone" url="<%=cloneURL%>" />	
			<%}%>	
			
			<%if(permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.PERMISSIONS) && ! course.isClosed() && !isInCourse){%>
				<%-- Permisos --%>
				<c:if test="${renderRequest.preferences.getValue('showPermission', 'true') }">
					<liferay-security:permissionsURL modelResource="<%=Course.class.getName() %>" modelResourceDescription="<%=course.getTitle(themeDisplay.getLocale()) %>"
						resourcePrimKey="<%= String.valueOf(course.getCourseId()) %>" var="permissionsURL" />
					<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />
				</c:if>	
			<%}%>
		
			<%-- Cerrar/Abrir --%>
			<%if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), courseId, ActionKeys.UPDATE) && ! course.isClosed() && showClose && !isInCourse){%>
				<portlet:actionURL name="closeCourse" var="closeURL">
					<portlet:param name="courseId" value="<%=String.valueOf(courseId) %>" />
					<portlet:param name="redirect" value='<%=ParamUtil.getString(request, "redirect", currentURL) %>'/>
				</portlet:actionURL>
				<liferay-ui:icon image="close" message="close" url="<%=closeURL.toString() %>" />
			<%}else if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), courseId, ActionKeys.UPDATE)&& course.isClosed() && !isInCourse){%>
				<portlet:actionURL name="openCourse" var="openURL">
					<portlet:param name="courseId" value="<%=String.valueOf(course) %>" />
					<portlet:param name="redirect" value='<%=ParamUtil.getString(request, "redirect", currentURL) %>'/>
				</portlet:actionURL>
			<%}%>
			
			<%-- Eliminar --%>
			<%if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), courseId, ActionKeys.DELETE) && ! course.isClosed() && showDelete && !isInCourse){%>
				<portlet:actionURL name="deleteCourse" var="deleteURL">
					<portlet:param name="courseId" value="<%=String.valueOf(courseId) %>" />
				</portlet:actionURL>
				<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
			<%}%>
			
		</liferay-ui:icon-menu>
	</aui:fieldset>
</c:if>

<aui:form name="fm" action="<%=savecourseURL%>"  method="post" enctype="multipart/form-data">

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="courseId" type="hidden" value="<%=courseId %>"/>

	<span class="aui-field-content" > 
		 
		 <span class="aui-field-element " > 
			 <label class="aui-field-label" for="<%=renderResponse.getNamespace()+"title"+StringPool.UNDERLINE+LanguageUtil.getLanguageId(LocaleUtil.getDefault()) %>"> 
			 	<liferay-ui:message key="title.required" /> 
			 </label> 
			  <liferay-ui:input-localized
				   cssClass="<%=renderResponse.getNamespace()+\"localized lfr-input-text\"%>" 
				   name="title" 
				   defaultLanguageId="<%=LanguageUtil.getLanguageId(LocaleUtil.getDefault()) %>"
				   xml="<%=courseTitle %>"
				   maxLength="<%=maxLengthTitle %>" required="true"/>
		 </span> 
	</span>
	
	<c:if test="<%=groupCreated!=null%>">
		<aui:input name="friendlyURL" label="courseadmin.friendly-url" value="<%=groupCreated.getFriendlyURL()%>" />
	</c:if>
	
	<c:if test="${renderRequest.preferences.getValue('showDescription', 'true') }">		
		<aui:field-wrapper label="description" name="description">
				<script type="text/javascript">
					function <portlet:namespace />onChangeDescription(val) {
				    	var A = AUI();
						A.one('#<portlet:namespace />description').set('value',val);
		        	}
				</script>
				<liferay-ui:input-editor name="description" width="100%" onChangeMethod="onChangeDescription" initMethod="initEditorDescription" />
				<script type="text/javascript">
	    		    function <portlet:namespace />initEditorDescription() { return "<%= UnicodeFormatter.toString(description) %>"; }
	    		</script>
		</aui:field-wrapper>
	</c:if>
	
	<%
	 boolean showCatalog = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("showcatalog", new String[]{StringPool.TRUE})[0],true);
	 boolean showCatalogForEdition = GetterUtil.getBoolean(renderRequest.getPreferences().getValue("showCourseCatalogForEditions", StringPool.FALSE),false);
	%>
	
	<c:if test="<%=((permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),0,publishPermission)) &&
					((!isCourseChild && showCatalog) || (isCourseChild && showCatalogForEdition))) %>">
		<aui:input type="checkbox" name="visible" label="published-in-catalog" value="<%=visibleencatalogo %>" />
	</c:if>

	<% boolean requiredCourseIcon = GetterUtil.getBoolean(PropsUtil.get("lms.course.icon.required"), false); %>
	<aui:input type="hidden" name="icon" >
		<% if (requiredCourseIcon) { %>
			<aui:validator errorMessage="course-icon-required" name="customRequiredCourseIcon1">(function(val, fieldNode, ruleValue) {return (AUI().one('#<portlet:namespace/>fileName').val() != 0 || val != null);})()</aui:validator>
		<% } %>
	</aui:input> 
	<liferay-ui:error key="error-file-size" message="error-file-size" />
	<script type="text/javascript">
		Liferay.provide(
			window,
			'<portlet:namespace/>toggleInputLogo',
			function() {
				var A = AUI();
				var discardCheckbox = A.one('#<portlet:namespace/>discardLogoCheckbox');
				var fileInput = A.one('#<portlet:namespace/>fileName');
				var iconCourse = A.one('#<portlet:namespace/>icon_course');
				if (discardCheckbox.attr('checked')) {
					fileInput.val('');
					fileInput.hide();
					iconCourse.hide();
				} else {
					fileInput.show();
					iconCourse.show();
				}
			},
			['node']
		);
	</script>
	
	<c:if test="${renderRequest.preferences.getValue('showIconCourse', 'true') }">
		<aui:field-wrapper cssClass="wrapper-icon-course">
			<% if (course != null && course.getIcon() != 0 && !requiredCourseIcon) { %>
					<aui:input type="checkbox" name="discardLogo" label="discard-course-icon" onClick='<%= renderResponse.getNamespace()+"toggleInputLogo()" %>'/>
				<% } %>
				<aui:input name="fileName" label="image" id="fileName" type="file" value="" >
					<aui:validator name="acceptFiles">'jpg, jpeg, png, gif'</aui:validator>
					<% if (requiredCourseIcon) { %>
						<aui:validator errorMessage="course-icon-required" name="customRequiredCourseIcon1">(function(val, fieldNode, ruleValue) {return (AUI().one('#<portlet:namespace/>icon').val() || val != null);})()</aui:validator>
					<% } %>
				</aui:input>
			<%	if(course != null && course.getIcon() != 0) {
				FileEntry image_=DLAppLocalServiceUtil.getFileEntry(course.getIcon());	%>
				<div class="container_ico_course">
					<img id="<portlet:namespace/>icon_course" alt="" class="ico_course" src="<%= DLUtil.getPreviewURL(image_, image_.getFileVersion(), themeDisplay, StringPool.BLANK) %>"/>
				</div>
				
			<%} %>
		</aui:field-wrapper>
		<liferay-ui:error key="course-icon-required" message="course-icon-required" />
		<liferay-ui:error key="error_number_format" message="error_number_format" />
	</c:if>
	<c:if test="${renderRequest.preferences.getValue('showResume', 'true') }">
		<aui:input type="textarea" cols="100" rows="4" name="summary" label="summary" value="<%=summary %>"/>
	</c:if>
	
	<c:if test="<%=!isCourseChild%>">
		<div id="<portlet:namespace/>diplomaContent">
			<%@include file="/html/courseadmin/inc/specificContent.jsp" %>
		</div>
	</c:if>
	
	
		<%
		List<Long> courseEvalIds = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getCourseevals(),",",0L));
		CourseEvalRegistry cer=new CourseEvalRegistry();
		CourseEval courseEval = null;
		if(courseEvalIds.size()>1){%>
			<aui:select name="courseEvalId" label="course-correction-method" helpMessage="<%=LanguageUtil.get(pageContext,\"course-correction-method-help\")%>" 
						onChange="<%=\"javascript:\"+renderResponse.getNamespace()+\"changeEvaluationMethod(this.value);AUI().use('aui-io-request','aui-parse-content','querystring',function(A){ \"+
								\"	var courseCombo = document.getElementById('\"+renderResponse.getNamespace()+\"courseEvalId'), \"+
								\"		currentCourseEvalId = courseCombo.options[courseCombo.selectedIndex].value, \"+
								\"		params = {}, \"+
								\"		urlPieces = '\"+
											UnicodeFormatter.toString(renderResponse.createRenderURL().toString()) +\"'.split('?'); \"+
								\"	if (urlPieces.length > 1) { \"+
								\"		params = A.QueryString.parse(urlPieces[1]); \"+
								\"		params.p_p_state='\"+LiferayWindowState.EXCLUSIVE.toString() +\"'; \"+
								((course==null)?StringPool.BLANK:
									\"	params.\"+renderResponse.getNamespace()+\"courseId=\"+course.getCourseId()+\"; \")+
								\"		params.\"+renderResponse.getNamespace()+\"courseEvalId=currentCourseEvalId; \"+
								\"		params.\"+renderResponse.getNamespace()+\"mvcPath='/html/courseadmin/editcourseeval.jsp'; \"+
								\"	} \"+
								\"	A.io.request( \"+
								\"		urlPieces[0], \"+
								\"		{ \"+
								\"			data: params, \"+
								\"			dataType: 'html', \"+
								\"			on: { \"+
								\"				failure: function(event, id, obj) { \"+
								\"					var portlet = A.one('#p_p_id\"+renderResponse.getNamespace()+\"'); \"+
								\"					portlet.hide(); \"+
								\"					portlet.placeAfter('<div class=\\\\'portlet-msg-error\\\\'>\"+
														UnicodeFormatter.toString(LanguageUtil.get(pageContext, 
														\"there-was-an-unexpected-error.-please-refresh-the-current-page\")) +\"</div>'); \"+
								\"				}, \"+
								\"				success: function(event, id, obj) { \"+
								\"					var courseEvalDetailsDiv = A.one('#\"+
															renderResponse.getNamespace()+\"courseEvalDetails'); \"+
								\"					courseEvalDetailsDiv.plug(A.Plugin.ParseContent); \"+ 
								\"					if(this.get('responseData')!=null){courseEvalDetailsDiv.html(this.get('responseData')); }else{ courseEvalDetailsDiv.html(''); } \"+ 
								\"				} \"+
								\"			} \"+
								\"		} \"+
								\"	); \"+
								\"}); \"%>">
			<%
			long courseEvalId = 0;
			if(Validator.isNull(renderRequest.getParameter("courseEvalId"))) {
				if((course!=null)&&(courseEvalIds.contains(course.getCourseEvalId()))) {
					courseEvalId = course.getCourseEvalId();
				}
				else {
					courseEvalId = courseEvalIds.get(0);
				}
			}
			else {
				courseEvalId = ParamUtil.getLong(renderRequest, "courseEvalId");
			}
	
			for(Long ce:courseEvalIds)
			{
				CourseEval cel = cer.getCourseEval(ce);
				if(ce == courseEvalId) {
					courseEval = cel;
					%>
					<aui:option value="<%=String.valueOf(ce)%>" selected="<%=true %>"><liferay-ui:message key="<%=cel.getName() %>" /></aui:option>
					<%				
				}
				else {
					%>
					<aui:option value="<%=String.valueOf(ce)%>" selected="<%=false %>"><liferay-ui:message key="<%=cel.getName() %>" /></aui:option>
					<%				
				}
			}
			%>
			</aui:select>
		<%
		}
		else{
			try{
				if(courseEvalIds.isEmpty()){
					courseEval = cer.getCourseEval(0);
				}
				else {
					courseEval = cer.getCourseEval(courseEvalIds.get(0));
				}
			}catch(Exception e){
				courseEval = cer.getCourseEval(0);
			}
			%>
			<aui:input name="courseEvalId" value="<%=courseEval.getTypeId()%>" type="hidden"/>
		<%}%>
		<div id="<portlet:namespace/>courseEvalDetails" >
			<liferay-util:include page="/html/courseadmin/editcourseeval.jsp" servletContext="<%=getServletContext() %>">
				<liferay-util:param name="courseId" value="<%=String.valueOf((course==null)?0:course.getCourseId())%>" />
				<liferay-util:param name="courseEvalId" value="<%=String.valueOf(courseEval.getTypeId())%>" />
			</liferay-util:include>
		</div>
	<%	
		String[] layusprsel=null;
		if(renderRequest.getPreferences().getValue("courseTemplates", null)!=null&&renderRequest.getPreferences().getValue("courseTemplates", null).length()>0)
		{
				layusprsel=renderRequest.getPreferences().getValue("courseTemplates", "").split(",");
		}

		String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
		if(layusprsel!=null &&layusprsel.length>0)
		{
			lspist=layusprsel;

		}
		if(lspist.length>1){
		%>
			<aui:select name="courseTemplate" label="course-template" disabled="<%=(course==null)?false:true %>">
			<%
			for(String lspis:lspist)
			{
				LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspis));
				%>
				<aui:option value="<%=lsp.getLayoutSetPrototypeId() %>" selected="<%=templateParent == lsp.getLayoutSetPrototypeId() %>"><%=lsp.getName(themeDisplay.getLocale()) %></aui:option>
				<%
			}
			%>
			</aui:select>
		<%
		}
		else{
			LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspist[0]));
		%>
			<aui:input name="courseTemplate" value="<%=lsp.getLayoutSetPrototypeId()%>" type="hidden"/>
		<%}
		
		List <Long> califications = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getScoretranslators(),",",0L));	
		CalificationTypeRegistry cal = new CalificationTypeRegistry();
		if(califications.size()>1){
			%>
				<aui:select name="calificationType" label="calificationType" onchange="${renderResponse.getNamespace()}changeCalificationType(this.value)">			
			<%
			for(Long ct:califications){
				boolean selected = false;
				CalificationType ctype = cal.getCalificationType(ct);
				if((course == null && PropsUtil.get("lms.calification.default.type").equals(String.valueOf(ct))) || (course != null && ct == course.getCalificationType()))
					selected = true;
				%>
					<aui:option value="<%=String.valueOf(ct)%>"  selected="<%=selected %>"><liferay-ui:message key="<%=ctype.getTitle(themeDisplay.getLocale()) %>" /></aui:option>
				<%
			}
			%>
				</aui:select>
				
			<%	
			for(Long ct:califications){
				boolean selected = false;
				CalificationType ctype = cal.getCalificationType(ct);
				if(Validator.isNotNull(ctype.getExpecificContentPage())){%>
					<div class="<%if(course == null || ct != course.getCalificationType()){%>aui-helper-hidden<%}%> especific_content_page" id="${renderResponse.getNamespace()}especific_content_page_<%=ctype.getTypeId()%>">
						<liferay-util:include page="<%=ctype.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>">
							<%if(course != null){ %>
								<liferay-util:param name="groupId" value="<%=Long.toString(course.getGroupCreatedId()) %>" />
							<%} %>	
						</liferay-util:include>	
					</div>
				<%
				}
			}
			%>	
				<script>
				function <portlet:namespace />changeCalificationType(typeId){
					$(".especific_content_page").addClass("aui-helper-hidden");
					$("#<portlet:namespace />especific_content_page_"+typeId).removeClass("aui-helper-hidden");
				}
				</script>
				
			<%
		}else{
			
			CalificationType ctype = null;
			try{
				if(califications.size()>0){
					ctype =cal.getCalificationType(califications.get(0));
				}
			}catch(Exception e){}
			%>
			<aui:input name="calificationType" value="<%=ctype==null?\"0\":ctype.getTypeId()%>" type="hidden"/>
			
			<%
			if(Validator.isNotNull(ctype.getExpecificContentPage())){%>
					<div class="especific_content_page" id="${renderResponse.getNamespace()}especific_content_page_<%=ctype.getTypeId()%>">
						<liferay-util:include page="<%=ctype.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>">
							<%if(course != null){ 
								String courseGroupId = Long.toString(course.getGroupCreatedId());
								%>
								<liferay-util:param name="groupId" value="<%=courseGroupId %>" />
							<%} %>
						</liferay-util:include>		
					</div>
				<%
			}
		}%>
	<% 
	boolean showInscriptionDate = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("showInscriptionDate", new String[]{StringPool.TRUE})[0],true);
	boolean showExecutionDate = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("showExecutionDate", new String[]{StringPool.TRUE})[0],true);
	int defaultStartYear = LiferaylmsUtil.defaultStartYear;
	if(course!=null && course.getStartDate()!=null){
		Calendar defaultStartDate = Calendar.getInstance();
		defaultStartDate.setTime(course.getStartDate());
		if(defaultStartYear>defaultStartDate.get(Calendar.YEAR)){
			defaultStartYear = defaultStartDate.get(Calendar.YEAR) - 10;
		}
			
	}
	
	%>
	
<liferay-ui:panel-container extended="false"  persistState="false">
   	  
   	  <liferay-ui:panel title="lms-inscription-configuration" collapsible="true" defaultState="closed" cssClass="<%=(showInscriptionDate||showMaxUsers)?StringPool.BLANK:\"aui-helper-hidden\" %>">
		<aui:field-wrapper name="inscriptionDate" label="start-inscription-date" cssClass="<%=(showInscriptionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="inscriptionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=defaultStartYear %>"  dayParam="startDay" monthParam="startMon"
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper name="endInscriptionDate" label="end-inscription-date"  cssClass="<%=(showInscriptionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="endInscriptionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
			 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time></br>
		</aui:field-wrapper>
	
			<c:choose>
	      		<c:when test="<%=(!showRegistrationType)||(sites!=null&&sites.size()==1)%>">
					<aui:input name="type" type="hidden" value="<%= sites.toArray()[0] %>" />
	      		</c:when>
	      		<c:otherwise>
					<aui:select name="type" label="registration-type" helpMessage="<%=LanguageUtil.get(pageContext,\"type-method-help\")%>">
						<c:choose>
				    		<c:when test="<%=groupCreated==null%>">
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_OPEN)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_OPEN %>" ><liferay-ui:message key="public" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_PRIVATE)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_PRIVATE %>" ><liferay-ui:message key="private" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_RESTRICTED)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_RESTRICTED %>" ><liferay-ui:message key="restricted" /></aui:option>
								</c:if>
				      		</c:when>
				      		<c:otherwise>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_OPEN)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_OPEN %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_OPEN %>" ><liferay-ui:message key="public" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_PRIVATE)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_PRIVATE %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_PRIVATE %>" ><liferay-ui:message key="private" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_RESTRICTED)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_RESTRICTED %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_RESTRICTED %>" ><liferay-ui:message key="restricted" /></aui:option>
								</c:if>
				      		</c:otherwise>
						</c:choose>
					</aui:select>
	      		</c:otherwise>
			</c:choose>
			
		<c:if test="<%=showMaxUsers %>">
			<aui:input name="maxUsers" label="num-of-users" type="text" value="<%=maxUsers %>" helpMessage="<%=LanguageUtil.get(pageContext,\"max-users-method-help\")%>">
				<aui:validator name="number"></aui:validator>
			</aui:input>
		</c:if>
	</liferay-ui:panel>
	
	<% 
	defaultStartYear = LiferaylmsUtil.defaultStartYear;
	if(course!=null && course.getExecutionStartDate()!=null){
		Calendar defaultStartDate = Calendar.getInstance();
		defaultStartDate.setTime(course.getExecutionStartDate());
		if(defaultStartYear>defaultStartDate.get(Calendar.YEAR)){
			defaultStartYear = defaultStartDate.get(Calendar.YEAR) - 10;
		}
			
	}
	
	%>
	
	<liferay-ui:panel title="lms-execution-configuration" collapsible="true" defaultState="closed" cssClass="<%=(showExecutionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
		<aui:field-wrapper name="executionDate" label="start-execution-date" cssClass="<%=(showExecutionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="executionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=defaultStartYear %>"  dayParam="startExecutionDay" monthParam="startExecutionMon"
					 yearParam="startExecutionYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startExecutionYear %>" monthValue="<%=startExecutionMonth %>" dayValue="<%=startExecutionDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startExecutionMin" amPmParam="startExecutionAMPM" hourParam="startExecutionHour" hourValue="<%=startExecutionHour %>" minuteValue="<%=startExecutionMin %>"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper name="endExecutionDate" label="end-execution-date"  cssClass="<%=(showExecutionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="endExecutionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopExecutionDay" monthParam="stopExecutionMon"
					 yearParam="stopExecutionYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endExecutionYear %>" monthValue="<%=endExecutionMonth %>" dayValue="<%=endExecutionDay %>"></liferay-ui:input-date>
			 <liferay-ui:input-time minuteParam="stopExecutionMin" amPmParam="stopExecutionAMPM" hourParam="stopExecutionHour"  hourValue="<%=endExecutionHour %>" minuteValue="<%=endExecutionMin %>"></liferay-ui:input-time></br>
		</aui:field-wrapper>
	</liferay-ui:panel> 
    
	
	<c:if test="<%=!isCourseChild || (isCourseChild && showCatalogForEdition)%>">
		<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
		<liferay-ui:custom-attributes-available className="<%= Course.class.getName() %>">
		<liferay-ui:custom-attribute-list 
			className="<%=com.liferay.lms.model.Course.class.getName()%>" classPK="<%=courseId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
		</liferay-ui:custom-attributes-available>
		<aui:input name="tags" type="assetTags" />
		<aui:input name="categories" type="assetCategories" />
		<aui:fieldset label="related-assets">
		<liferay-ui:input-asset-links
						className="<%= Course.class.getName() %>"
						classPK="<%= courseId %>" assetEntryId="<%=assetEntryId %>" 	/>
		</aui:fieldset>
		</liferay-ui:panel>
	</c:if>
	
	<c:if test="<%=courseId==0 && showCoursePermission%>">
		<liferay-ui:panel title="permissions" collapsible="true" defaultState="closed">
		
			<liferay-ui:input-permissions modelName="<%= com.liferay.lms.model.Course.class.getName() %>">
		    </liferay-ui:input-permissions>
		</liferay-ui:panel>
	</c:if>
		<%
			boolean active =(course!=null&&course.getWelcome()?true:false);  
			String welcomeMsg = (course!=null&&course.getWelcomeMsg()!=null?course.getWelcomeMsg():"");
		%>
		<c:if test="${renderRequest.preferences.getValue('showWelcomeMsg', 'true') }">
			<liferay-ui:panel title="welcome-msg" collapsible="true" defaultState='<%=active?"open":"closed" %>'>
				<aui:input type="checkbox" name="welcome" label="enabled" value='<%=active %>' onChange='<%= renderResponse.getNamespace()+"changeWelcome()" %>'/>
				
				<div id="containerWelcomeMsg" style='display:<%=active?"block":"none"%>'>
				
					<aui:input name="welcomeSubject" size="100"  type="text" label="welcome-subject" value="<%=welcomeSubject%>">
						<aui:validator name="maxLength">75</aui:validator>
					</aui:input>
				
					<aui:field-wrapper label="welcome-msg" name="welcome-msg">
					
					
					
						<script type="text/javascript">
							function <portlet:namespace />onChangeWelcomeMsg(val) {
					        	var A = AUI();
								A.one('#<portlet:namespace />welcomeMsg').set('value',val);
					        }
						</script>
						<liferay-ui:input-editor toolbarSet="slimmer" name="welcomeMsg" width="100%" onChangeMethod="onChangeWelcomeMsg" initMethod="initEditorWelcomeMsg" />
						<script type="text/javascript">
		    		    	function <portlet:namespace />initEditorWelcomeMsg() { return "<%= UnicodeFormatter.toString(welcomeMsg) %>"; }
		    			</script>
		    			
		    			
		    			
		    			
					</aui:field-wrapper>
					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>
		
						<dl>
							<dt>
								[$PAGE_URL$]
							</dt>
							<dd>
								<%= themeDisplay.getURLPortal()+"/web"+((course!=null&&course.getFriendlyURL()!=null)?course.getFriendlyURL():StringPool.BLANK) %>
							</dd>
							<dt>
								[$TITLE_COURSE$]
							</dt>
							<dd>
								<%=(course!=null?course.getTitle(themeDisplay.getLocale()):StringPool.BLANK) %>
							</dd>
							<dt>
								[$FROM_ADDRESS$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(PrefsPropsUtil.getString(themeDisplay.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS)) %>
							</dd>
							<dt>
								[$FROM_NAME$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(PrefsPropsUtil.getString(themeDisplay.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_NAME)) %>
							</dd>
							<dt>
								[$PORTAL_URL$]
							</dt>
							<dd>
								<%= company.getVirtualHostname() %>
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
							<dt>
								[$USER_SCREENNAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-screen-name" />
							</dd>
						</dl>
					</div>
				</div>
				
			</liferay-ui:panel>
		</c:if>
		
		<%
			boolean activeGoodbye =(course!=null&&course.getGoodbye()?true:false);  
			String goodbyeMsg = (course!=null&&course.getGoodbyeMsg()!=null?course.getGoodbyeMsg():"");
		%>
		
		<c:if test="${renderRequest.preferences.getValue('showGoodbyeMsg', 'true') }">
			<liferay-ui:panel title="goodbye-msg" collapsible="true" defaultState='<%=activeGoodbye?"open":"closed" %>'>
				<aui:input type="checkbox" name="goodbye" label="enabled" value='<%=activeGoodbye %>' onChange='<%= renderResponse.getNamespace()+"changeGoodbye()" %>'/>
				
				<div id="containerGoodbyeMsg" style='display:<%=activeGoodbye?"block":"none"%>'>
				
					<aui:input name="goodbyeSubject" size="100"  type="text" label="goodbye-subject" value="<%=goodbyeSubject%>">
						<aui:validator name="maxLength">75</aui:validator>
					</aui:input>
				
					<aui:field-wrapper label="goodbye-msg" name="goodbye-msg">
					
					
					
						<script type="text/javascript">
							function <portlet:namespace />onChangeGoodbyeMsg(val) {
					        	var A = AUI();
								A.one('#<portlet:namespace />goodbyeMsg').set('value',val);
					        }
						</script>
						<liferay-ui:input-editor toolbarSet="slimmer" name="goodbyeMsg" width="100%" onChangeMethod="onChangeGoodbyeMsg" initMethod="initEditorGoodbyeMsg" />
						<script type="text/javascript">
		    		    	function <portlet:namespace />initEditorGoodbyeMsg() { return "<%= UnicodeFormatter.toString(goodbyeMsg) %>"; }
		    			</script>
		    			
					</aui:field-wrapper>
					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>
		
						<dl>
							<dt>
								[$PAGE_URL$]
							</dt>
							<dd>
								<%= themeDisplay.getURLPortal()+"/web"+((course!=null&&course.getFriendlyURL()!=null)?course.getFriendlyURL():StringPool.BLANK) %>
							</dd>
							<dt>
								[$TITLE_COURSE$]
							</dt>
							<dd>
								<%=(course!=null?course.getTitle(themeDisplay.getLocale()):StringPool.BLANK) %>
							</dd>
							<dt>
								[$FROM_ADDRESS$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(PrefsPropsUtil.getString(themeDisplay.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS)) %>
							</dd>
							<dt>
								[$FROM_NAME$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(PrefsPropsUtil.getString(themeDisplay.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_NAME)) %>
							</dd>
							<dt>
								[$PORTAL_URL$]
							</dt>
							<dd>
								<%= company.getVirtualHostname() %>
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
							<dt>
								[$USER_SCREENNAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-screen-name" />
							</dd>
						</dl>
					</div>
				</div>
				
			</liferay-ui:panel>
		</c:if>
		
	</liferay-ui:panel-container>
	<c:choose>
		<c:when test="<%=isCourseChild%>">
			<liferay-portlet:renderURL var="cancelURL">
				<liferay-portlet:param name="view" value="editions"/>
				<liferay-portlet:param name="courseId" value="<%=String.valueOf(course.getParentCourseId())%>"/>
			</liferay-portlet:renderURL>
		</c:when>
		<c:otherwise>
			<portlet:renderURL var="cancelURL" />
		</c:otherwise>
	</c:choose>
	
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="${cancelURL }" type="cancel" />
	</aui:button-row>
</aui:form>

<% themeDisplay.setIncludeServiceJs(true); %>
<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>

<script type="text/javascript">
	function <portlet:namespace />changeWelcome(){
		var div = document.getElementById("containerWelcomeMsg");
		if(div.style.display&&div.style.display=='none'){
			div.style.display='block';
		}else{
			div.style.display='none';
		}
	}
	
	function <portlet:namespace />changeEvaluationMethod(courseEvalId){
		<%if(course!=null){	%>
			var currentCourseEval = <%=course.getCourseEvalId()%>;
			if(courseEvalId!=currentCourseEval){
				alert('<%=LanguageUtil.get(locale, "courseadmin.change-evaluation-method-alert")%>');	
			}
			
		<%}	%>
	}
	
	function <portlet:namespace />changeGoodbye(){
		var div = document.getElementById("containerGoodbyeMsg");
		if(div.style.display&&div.style.display=='none'){
			div.style.display='block';
		}else{
			div.style.display='none';
		}
	}
	
	function <portlet:namespace />checkduplicate(val, field){
		var courseId = document.getElementById('<portlet:namespace />courseId').value;
	   	return !Liferay.Service.Lms.Course.existsCourseName(
	   		{
	   			companyId: themeDisplay.getCompanyId(),
	   			courseId: (courseId > 0 ? courseId : null),
	   			groupName: val,
	   			serviceParameterTypes: JSON.stringify(['java.lang.Long', 'java.lang.Long', 'java.lang.String'])
	   		}
	   	);
	} 
</script>
