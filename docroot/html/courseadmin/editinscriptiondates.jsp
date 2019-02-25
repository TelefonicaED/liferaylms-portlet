<%@page import="com.liferay.taglib.ui.LanguageTag"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
long courseId=ParamUtil.getLong(request, "courseId", 0);
long userId=ParamUtil.getLong(request, "userId", 0);
CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(courseId, userId);
String description="", startCalendarClass="", endCalendarClass="";
SimpleDateFormat formatDay    = new SimpleDateFormat("dd");
formatDay.setTimeZone(timeZone);
SimpleDateFormat formatMonth    = new SimpleDateFormat("MM");
formatMonth.setTimeZone(timeZone);
SimpleDateFormat formatYear    = new SimpleDateFormat("yyyy");
formatYear.setTimeZone(timeZone);
SimpleDateFormat formatHour   = new SimpleDateFormat("HH");
formatHour.setTimeZone(timeZone);
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
formatMin.setTimeZone(timeZone);
Date today=new Date(System.currentTimeMillis());
int startDay=Integer.parseInt(formatDay.format(today));
int startMonth=Integer.parseInt(formatMonth.format(today))-1;
int startYear=Integer.parseInt(formatYear.format(today));
int endDay=Integer.parseInt(formatDay.format(today));
int endMonth=Integer.parseInt(formatMonth.format(today))-1;
int endYear=Integer.parseInt(formatYear.format(today))+1;
boolean startdateenabled=false;
boolean stopdateenabled=false;
if(courseResult!=null)
{
	if(!Validator.isNull(courseResult.getAllowStartDate())){
		Date startDate = courseResult.getAllowStartDate();
		startdateenabled=true;
		startDay=Integer.parseInt(formatDay.format(startDate));
		startMonth=Integer.parseInt(formatMonth.format(startDate))-1;
		startYear=Integer.parseInt(formatYear.format(startDate));
	}
	
	if(!Validator.isNull(courseResult.getAllowFinishDate())){
		stopdateenabled=true;
		Date endDate = courseResult.getAllowFinishDate();
		endDay=Integer.parseInt(formatDay.format(endDate));
		endMonth=Integer.parseInt(formatMonth.format(endDate))-1;
		endYear=Integer.parseInt(formatYear.format(endDate));
	}
}
%>

<portlet:actionURL var="editInscriptionDatesURL" name="editInscriptionDates" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
<portlet:param name="jspPage" value="/html/courseadmin/editinscriptiondates.jsp"/>
</portlet:actionURL>

<portlet:renderURL var="cancel" />
<aui:form name="fm" role="form" action="<%=editInscriptionDatesURL%>"  method="post">
<aui:field-wrapper label="start-date">
<aui:input name="courseId" type="hidden" value="<%=courseId %>" />
<aui:input name="userId" type="hidden" value="<%=userId %>" />
<aui:input id="startdate" name="startdate-enabled" checked="<%=startdateenabled %>"  type="checkbox" label="editActivity.startdate.enabled"  />
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon" 
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
					 </aui:field-wrapper>
					 
					 <aui:field-wrapper label="end-date">
<aui:input id="stopdate" name="stopdate-enabled" checked="<%=stopdateenabled %>"  type="checkbox" label="editActivity.stopdate.enabled" />
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
					 </aui:field-wrapper>
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
</aui:form>