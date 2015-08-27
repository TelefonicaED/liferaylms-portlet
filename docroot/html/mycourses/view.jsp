<%@page import="java.net.URLEncoder"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>

<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>

<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%@ include file="/init.jsp"%>

<%

PortletPreferences prefs = null;
String portletResource = ParamUtil.getString(request, "portletResource");
if (Validator.isNotNull(portletResource)) {
    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
	prefs = renderRequest.getPreferences();
}
String myCoursesOrder=prefs.getValue("myCoursesOrder","");
Integer courseOrder = 0;
if (!myCoursesOrder.equals("")) courseOrder = Integer.parseInt(myCoursesOrder); 

java.util.List<Group> groupsNoOrder = GroupLocalServiceUtil.getUserGroups(themeDisplay.getUserId());
java.util.List<Group> groups = new ArrayList<Group>();
groups.addAll(groupsNoOrder);

//ordenamos la lista
if (courseOrder == 0){
	
	Collections.sort(groups, new Comparator<Group>() {
	    public int compare(Group g1, Group g2) {
	    	Group e1 = (Group) g1;
	    	Group e2 = (Group) g2;
	    	try{
	    		Course c1 = CourseLocalServiceUtil.fetchByGroupCreatedId(g1.getGroupId());
	    		Course c2 = CourseLocalServiceUtil.fetchByGroupCreatedId(g2.getGroupId());
	    		if (c1 == null)	return 1;
	    		else if (c2 == null) return -1;
	    		return Long.valueOf(c1.getCourseId()).compareTo(c2.getCourseId());
	    	}catch (Exception e){
	    		return 1;
	    	}
	          
	    }  
	});
}else if (courseOrder == 1){
	
	Collections.sort(groups, new Comparator<Group>() {
	    public int compare(Group g1, Group g2) {
	    	try{
	    		return g1.getName().compareToIgnoreCase(g2.getName());
	    	}catch (Exception e){
	    		return 1;
	    	}
	          
	    }
	});
}else if (courseOrder == 2){
	
	Collections.sort(groups, new Comparator<Group>() {
	    public int compare(Group g1, Group g2) {
	    	Group e1 = (Group) g1;
	    	Group e2 = (Group) g2;
	    	try{
	    		return g2.getName().compareToIgnoreCase(g1.getName());
	    	}catch (Exception e){
	    		return 1;
	    	}
	          
	    }  
	});
}else if (courseOrder == 3){
	
	Collections.sort(groups, new Comparator<Group>() {
	    public int compare(Group g1, Group g2) {
	    	try{
	    		java.util.List<Module> lm1 = ModuleLocalServiceUtil.findAllInGroup(g1.getGroupId());
	    		java.util.List<Module> lm2 = ModuleLocalServiceUtil.findAllInGroup(g2.getGroupId());
	    		
	    		Date lm1Date = new Date(Long.MAX_VALUE);
	    		for(Module m1:lm1){
	    			Date m1Date = m1.getStartDate();
	    			if (m1Date != null && m1Date.compareTo(lm1Date) < 0){
	    				lm1Date = m1Date;
	    			}
	    		}
	    		
	    		Date lm2Date = new Date(Long.MAX_VALUE);
	    		for(Module m2:lm2){
	    			Date m2Date = m2.getStartDate();
	    			if (m2Date != null && m2Date.compareTo(lm2Date) < 0){
	    				lm2Date = m2Date;
	    			}
	    		}
	    		
	    		return lm1Date.compareTo(lm2Date);
	    	}catch (Exception e){
	    		return 1;
	    	}
	          
	    }  
	});
}else if (courseOrder == 4){
	
	Collections.sort(groups, new Comparator<Group>() {
	    public int compare(Group g1, Group g2) {
	    	try{
	    		java.util.List<Module> lm1 = ModuleLocalServiceUtil.findAllInGroup(g1.getGroupId());
	    		java.util.List<Module> lm2 = ModuleLocalServiceUtil.findAllInGroup(g2.getGroupId());
	    		
	    		Date lm1Date = new Date(Long.MAX_VALUE);
	    		for(Module m1:lm1){
	    			Date m1Date = m1.getEndDate();
	    			if (m1Date != null && m1Date.compareTo(lm1Date) < 0){
	    				lm1Date = m1Date;
	    			}
	    		}
	    		
	    		Date lm2Date = new Date(Long.MAX_VALUE);
	    		for(Module m2:lm2){
	    			Date m2Date = m2.getEndDate();
	    			if (m2Date != null && m2Date.compareTo(lm2Date) < 0){
	    				lm2Date = m2Date;
	    			}
	    		}
	    		
	    		return lm1Date.compareTo(lm2Date);
	    	}catch (Exception e){
	    		return 1;
	    	}
	          
	    }  
	});
}


Locale loc = response.getLocale();
int courses = 0;
for(Group groupCourse:groups)
{
	
	
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(groupCourse.getGroupId());
	if(course!=null&&!course.isClosed())
	{
	long passed=ModuleLocalServiceUtil.modulesUserPassed(groupCourse.getGroupId(),themeDisplay.getUserId());
	long modulescount=ModuleLocalServiceUtil.countByGroupId(groupCourse.getGroupId());
	Group groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
	if(modulescount>0 ||permissionChecker.hasPermission(groupCourse.getGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ADD_MODULE"))
	{
		%>
		<div class="course option-more">
		<%
		String url = themeDisplay.getPortalURL() +"/"+ loc.getLanguage() +"/web/"+ groupsel.getFriendlyURL();
		String usuarioSuplantado = themeDisplay.getDoAsUserId(); //Si estamos suplantando a un usuario
	    	String doAsUserId = "";
	     
	     if(usuarioSuplantado.length() > 0){
	    	 doAsUserId = "?doAsUserId=".concat(URLEncoder.encode(usuarioSuplantado,"UTF-8"));
	     }
	     
	  
	     url+=doAsUserId;  
		
		
		if (Validator.isNotNull(course.getIcon())) {
			long logoId = course.getIcon();
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(logoId);
			%>
			<a href='<%=url%>' class="course-title">
				<img src="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK) %>">
				<%=course.getTitle(themeDisplay.getLocale()) %>
			</a>
			<%
		} else if(groupCourse.getPublicLayoutSet().getLogo())
		{
			long logoId = groupCourse.getPublicLayoutSet().getLogoId();
			%>
			
			<a href='<%=url%>' class="course-title">
				<img src="/image/layout_set_logo?img_id=<%=logoId%>">
				<%=course.getTitle(themeDisplay.getLocale()) %>
			</a>
			<%
		} else {
			%>
			<a class="course-no-image" href='<%=url%>'><%=course.getTitle(themeDisplay.getLocale()) %></a>
		<% }
				
		%>
			 <span class="challenges"><%=passed %>/<%= modulescount%><span class="ch-text"><liferay-ui:message key="finished.modules" /></span></span><span class="ico-desplegable"></span>
			<div class="collapsable"  style="display:none;">
				<table class="moduleList">
		<%
			java.util.List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(groupCourse.getGroupId());
			
				for(Module theModule:theModules)
				{
					ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
					long done=0;
					if(moduleResult!=null)
					{
						done=moduleResult.getResult();
					}
					%>

					<tr>
			
					<td class="title">
					<%
						long retoplid=themeDisplay.getPlid();
						for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(groupCourse.getGroupId(),false))
						{
					
							if(theLayout.getFriendlyURL().equals("/reto"))
							{
								retoplid=theLayout.getPlid();
								
							}
						}
						
						%>
						<liferay-portlet:renderURL plid="<%=retoplid %>" portletName="lmsactivitieslist_WAR_liferaylmsportlet" var="gotoModuleURL">
						<liferay-portlet:param name="moduleId" value="<%=Long.toString(theModule.getModuleId()) %>"></liferay-portlet:param>
						</liferay-portlet:renderURL>
						<%if(!ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId()))
							{
							%>
					<a href="<%=gotoModuleURL.toString()%>"><%=theModule.getTitle(themeDisplay.getLocale()) %></a>
					<%
							}
						else
						{
							%>
							<a><%=theModule.getTitle(themeDisplay.getLocale()) %></a>
							<%
						}
					%>
					</td>
				<td class="result">
					
					<%=done %>% <liferay-ui:message key="test.done" />
					</td>
			
					</tr>
					
				<%
				}
				%>
				</table>
			</div>
		</div>
<%
		courses++;
		}
	}
}

if(courses==0){
	%><liferay-ui:message key="there-are-no-courses" /><%
}

%>
	
