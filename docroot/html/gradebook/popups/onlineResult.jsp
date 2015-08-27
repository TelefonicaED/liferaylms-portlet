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
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp" %>
<%

LearningActivityTry lATry = null;
LearningActivityResult result = null;
boolean ownGrade=false;

long actId = ParamUtil.getLong(request,"actId",0 );
long studentId = ParamUtil.getLong(request,"studentId",0 );

if(renderRequest.getParameter("actId")!=null){
	LearningActivity learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(actId);	
	User owner = UserLocalServiceUtil.getUser(studentId);
	%><h2 class="description-title"><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
	<h3 class="description-h3"><liferay-ui:message key="description" /></h3>
	<div class="description"><%=learningActivity.getDescription(themeDisplay.getLocale()) %></div>
	<%
	if(renderRequest.getParameter("studentId")!=null){
		%><p class="sub-title"><liferay-ui:message key="p2ptask-done-by" /> <%=owner.getFullName()%></p><%
		ownGrade=false;
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
		result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, studentId);	
	}else {
		ownGrade=true;	
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, themeDisplay.getUserId());
	}	
}

String urlFile=null;
String titleFile=null;
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
        		}catch(Throwable a){}
	         }else if(OnlineActivity.RICH_TEXT_XML.equals(element.getName())) {
	        	 richtext=element.getText();
	         }else if(OnlineActivity.TEXT_XML.equals(element.getName())) {
	        	 text=element.getText(); 
	         }	
	    }	
	}catch(DocumentException de){}
}

 if(richtext!=null) { %>
	<aui:field-wrapper label="onlinetaskactivity.text" >
		<liferay-ui:panel-container >
			<liferay-ui:panel id="panelId" title="" collapsible="false" extended="false" ><%=richtext %></liferay-ui:panel>
		</liferay-ui:panel-container >
	</aui:field-wrapper>
<% } 
 if(text!=null) {%>
	<aui:input type="textarea" cols="100" rows="50" name="text" label="onlinetaskactivity.text" value='<%=text %>' readonly='readonly'/>
<% } 
 if(urlFile!=null) {%>
	<liferay-ui:icon image="export" label="<%= true %>" message='<%=titleFile+"&nbsp;("+ sizeKbFile +"Kb)&nbsp;"%>' method="get" url="<%=urlFile%>"  />
<% }  %>
