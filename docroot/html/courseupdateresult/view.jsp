<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ include file="/init.jsp" %>

<script type="text/javascript">
	function openLogs(ruta){
		window.open(ruta, "File" , "width=1000,height=600,fullscreen=yes,scrollbars=yes");
	}
</script>

<portlet:actionURL name="updateResult" var="updateResultURL"/>

<aui:form name="form" action="<%=updateResultURL%>"  method="post">
	
	<aui:select label="Course" name="courseId" >
	
	<%
	java.util.List<Course> courses = CourseLocalServiceUtil.getCourses(0, CourseLocalServiceUtil.getCoursesCount());
		
	//ordenamos la lista por nombre de empleado  
    Collections.sort(courses, new Comparator() {  

        public int compare(Object o1, Object o2) {  
        	Course e1 = (Course) o1;  
        	Course e2 = (Course) o2;  
            return e1.getTitle(Locale.getDefault()).compareToIgnoreCase(e2.getTitle(Locale.getDefault()));  
        }  
    });  
	
	for(Course course:courses)
	{
		%>
			<aui:option value="<%=course.getCourseId() %>"><%=course.getTitle(themeDisplay.getLocale()) %></aui:option>
		<% 
	}
	%>

	</aui:select>
	<aui:button-row>
		<aui:button type="submit" name="acept"></aui:button>
	</aui:button-row>

</aui:form>

<div>
	<a onClick="openLogs('/custom_logs/CourseUpdate.txt')" style="Cursor:pointer;">CourseUpdate.txt</a>
</div>