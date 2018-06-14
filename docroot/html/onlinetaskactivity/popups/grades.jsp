<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.DocumentException"%>
<%@page import="com.liferay.portal.kernel.xml.Node"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portlet.documentlibrary.FileSizeException"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.lms.OnlineActivity"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>

<%@ include file="/init.jsp" %>
<%


Boolean isLinkTabletOnlineGrade = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassOnlineGrade="";
if(isLinkTabletOnlineGrade){
	cssLinkTabletClassOnlineGrade="tablet-link";
}

LearningActivityTry lATry = null;
LearningActivityResult result = null;
boolean ownGrade=false;
LearningActivity activity = null;

if(renderRequest.getParameter("actId")!=null)
{
	activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest,"actId"));
	if(renderRequest.getParameter("studentId")!=null){
		ownGrade=false;
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));
		result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));	
	}
	else {
		ownGrade=true;	
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(renderRequest,"actId"), themeDisplay.getUserId());
		result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), themeDisplay.getUserId());
	}
	
}

String gradeFilter = ParamUtil.getString(renderRequest, "gradeFilter");
String criteria = ParamUtil.getString(renderRequest, "criteria");

CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

String urlFile=null;
String titleFile=null;
//String iconFile=null;
long sizeKbFile=0;

String text=null;
String richtext=null;

if(lATry!=null){
	try {
		Iterator<Node> nodeItr = SAXReaderUtil.read(lATry.getTryResultData()).getRootElement().nodeIterator();
		while(nodeItr.hasNext()) {
	         Node element = nodeItr.next();
	         if(OnlineActivity.FILE_XML.equals(element.getName())) {
        		try{
	        	 	DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(Long.parseLong(((Element)element).attributeValue("id")));
	    			urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid(); 
	    			titleFile = dlfile.getTitle();
	    			sizeKbFile = dlfile.getSize()/1024;
	    			//iconFile=DLUtil.getFileIcon(dlfile.getExtension());
        		}
        		catch(Throwable a){}
	         }
	         else if(OnlineActivity.RICH_TEXT_XML.equals(element.getName())) {
	        	 richtext=element.getText();
	         }	
	         else if(OnlineActivity.TEXT_XML.equals(element.getName())) {
	        	 text=element.getText(); 
	         }	
	    }	
	}
	catch(DocumentException de)
	{}
}
if(renderRequest.getParameter("studentId")!=null){
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	dateFormat.setTimeZone(timeZone);
	String dateFormated = (result.getEndDate()!=null)? " ( "+dateFormat.format(result.getEndDate())+" )":"";
%>
 <aui:a href="" label="<%= UserLocalServiceUtil.getUserById(ParamUtil.getLong(renderRequest, \"studentId\")).getFullName() + dateFormated   %>"></aui:a>
<%
}else{
	if (lATry != null){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		dateFormat.setTimeZone(timeZone);
		String dateFormated = (lATry.getStartDate()!=null)? " ( "+dateFormat.format(lATry.getStartDate())+" )":"";
%>
	<div class="grey-box">
		<p class="label"><liferay-ui:message key="onlinetaskactivity.export.date"/>: <%=dateFormated %> </p>
	</div>
<%
	}	
}
 if(richtext!=null) { %>
	<div class="grey-box">
		<aui:field-wrapper label="onlinetaskactivity.text" name="panelLabel" >
			<liferay-ui:panel-container >
				<liferay-ui:panel id="panelId" title="" collapsible="false" extended="false" >
					<%=richtext %>
					<aui:input type="hidden" name="panelLabel"/>
				</liferay-ui:panel>
			</liferay-ui:panel-container >
		</aui:field-wrapper>
	</div>
<% } 
 if(text!=null) {
%>
	<div class="grey-box">
		<aui:input type="textarea" cols="60" rows="10" name="text" label="onlinetaskactivity.text" value='<%=text %>' readonly='readonly'/>
	</div>
<% } 
 if(urlFile!=null) {
%>
	<div class="grey-box">
		<p class="label"><liferay-ui:message key="onlinetaskactivity.grades.attach"/></p>
		<div class="doc_descarga">
			<span><%=titleFile+"&nbsp;("+ sizeKbFile +"Kb)&nbsp;"%></span>
			<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassOnlineGrade %>" target="_blank"><liferay-ui:message key="onlinetaskactivity.grades.download"/></a>
		</div>
	</div>
<% } 

if(!ownGrade){
%>

<portlet:actionURL name="setGrades" var="updateGradesURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
	<portlet:param name="gradeFilter" value="<%=gradeFilter %>" />
	<portlet:param name="criteria" value="<%=criteria %>" />
</portlet:actionURL>

<aui:form  name="fn_grades" method="post" action="${updateGradesURL}" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
	    <aui:input type="text" name="result" label="offlinetaskactivity.grades" helpMessage="<%=LanguageUtil.format(pageContext, \"offlinetaskactivity.grades.resultMessage\", new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), activity.getPasspuntuation())})%>" value='<%=result!=null?ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),result.getResult()):"" %>'>
	    	<aui:validator name="number"></aui:validator>
	    	<aui:validator  name="custom"  errorMessage="<%=LanguageUtil.format(themeDisplay.getLocale(), \"result.must-be-between\", new Object[]{ct.getMinValue(themeDisplay.getScopeGroupId()),ct.getMaxValue(themeDisplay.getScopeGroupId())})%>"  >
				function (val, fieldNode, ruleValue) {
					var result = false;
					if (val >= <%=ct.getMinValue(themeDisplay.getScopeGroupId()) %> && val <= <%= ct.getMaxValue(themeDisplay.getScopeGroupId()) %>) {
						result = true;
					}
					return result;					
				}
			</aui:validator>
	    </aui:input>
		<aui:field-wrapper label="onlinetaskactivity.comments" name="comments" helpMessage="<%=LanguageUtil.get(pageContext, \"onlinetaskactivity.grades.commentsMessage\")%>">
			<script type="text/javascript">
				function <portlet:namespace />onChangeComments(val) {
			    	var A = AUI();
					A.one('#<portlet:namespace />comments').set('value',val);
	        	}
			</script>
			<liferay-ui:input-editor name="comments" width="100%" onChangeMethod="onChangeComments" initMethod="initEditorComments" />
			<script type="text/javascript">
    		    function <portlet:namespace />initEditorComments() { return "<%=((result!=null)&&(result.getComments()!=null))?UnicodeFormatter.toString(result.getComments()):"" %>"; }
    		</script>
		</aui:field-wrapper>
	</aui:fieldset>
	<aui:button-row>
		<aui:button name="Save" value="save" type="submit"/>
		<aui:button name="Close" value="cancel" onclick="${renderResponse.getNamespace()}doClosePopupGrades();" type="button" />
	</aui:button-row>

</aui:form>

<% } 
   else {%>
	<aui:button-row>
		<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button"><liferay-ui:message key="close" /></button>
	</aui:button-row>
<% } %>
