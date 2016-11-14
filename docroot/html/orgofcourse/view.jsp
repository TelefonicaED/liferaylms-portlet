<%@page import="com.liferay.portal.NoSuchGroupException"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
         Group university=null;
          try
          {
          university=GroupLocalServiceUtil.getGroup(course.getGroupId());
          }
          catch(NoSuchGroupException e)
          {
        	  e.printStackTrace();
          }
          if(university!=null)
          {
          long logoId = university.getPublicLayoutSet().getLogoId();
          String uniurl="#";
          if(university.getPublicLayoutsPageCount()>0)
          {
                uniurl="/web"+university.getFriendlyURL();
          }

          %>
          <div class="university">
         <!--  <a  href="<%=uniurl%>"><h1 class="header-title"><%=university.getName() %></h1></a>-->
          <div class="content">
          <a  href="<%=uniurl%>"><img class="universitylogo" title="<%=university.getName() %>"  alt="<%=university.getName() %>" src="/image/layout_set_logo?img_id=<%=logoId%>">
           </a>
                </div>


        </div>
<%
          }
}
else
{
        renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
