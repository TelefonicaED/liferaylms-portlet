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
	}
	
}

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
	String dateFormated = (result.getEndDate()!=null)? " ( "+dateFormat.format(result.getEndDate())+" )":"";
%>
 <aui:a href="" label="<%= UserLocalServiceUtil.getUserById(ParamUtil.getLong(renderRequest, \"studentId\")).getFullName() + dateFormated   %>"></aui:a>
<%
}
 if(richtext!=null) { %>
	<aui:field-wrapper label="onlinetaskactivity.text" name="panelLabel" >
		<liferay-ui:panel-container >
			<liferay-ui:panel id="panelId" title="" collapsible="false" extended="false" >
				<%=richtext %>
				<aui:input type="hidden" name="panelLabel"/>
			</liferay-ui:panel>
		</liferay-ui:panel-container >
	</aui:field-wrapper>
<% } 
 if(text!=null) {
%>
	<aui:input type="textarea" cols="60" rows="10" name="text" label="onlinetaskactivity.text" value='<%=text %>' readonly='readonly'/>
<% } 
 if(urlFile!=null) {
%>
	<p class="label"><liferay-ui:message key="onlinetaskactivity.grades.attach"/></p>
	<div class="doc_descarga">
		<span><%=titleFile+"&nbsp;("+ sizeKbFile +"Kb)&nbsp;"%></span>
		<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="onlinetaskactivity.grades.download"/></a>
	</div>
<% } 

if(!ownGrade){
%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
	    <aui:input type="text" name="result" helpMessage="<%=LanguageUtil.format(pageContext, \"onlinetaskactivity.grades.resultMessage\", new Object[]{activity.getPasspuntuation()})%>"  label="onlinetaskactivity.grades" value='<%=((result!=null)&&(result.getEndDate()!=null))?Long.toString(result.getResult()):"" %>' />
	    <liferay-ui:error key="onlinetaskactivity.grades.result-bad-format" message="onlinetaskactivity.grades.result-bad-format" />

		<aui:input type="textarea" cols="75" rows="6" helpMessage="<%=LanguageUtil.get(pageContext, \"onlinetaskactivity.grades.commentsMessage\")%>" maxLength="350" name="comments" label="onlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'>
			<aui:validator name="range">[0, 350]</aui:validator>
		</aui:input>
	</aui:fieldset>
	<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
	</aui:button-row>
	<liferay-ui:success key="onlinetaskactivity.grades.updating" message="onlinetaskactivity.correct.saved" />
	<liferay-ui:error key="onlinetaskactivity.grades.bad-updating" message="onlinetaskactivity.grades.bad-updating" />
</aui:form>

<% } 
   else {%>
	<aui:button-row>
		<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button"><liferay-ui:message key="close" /></button>
	</aui:button-row>
<% } %>
