<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
String competenceImageURL=CompetenceLocalServiceUtil.getBGImageURL(themeDisplay.getScopeGroupId(), request);
%>
<portlet:renderURL var="cancelURL">
			</portlet:renderURL>
<portlet:actionURL var="saveImageURL" name="saveImage" />	

<liferay-ui:header backURL="<%=cancelURL%>" showBackURL="<%=Boolean.TRUE%>" title="" />
		
<aui:form name="fm" action="<%=saveImageURL%>"  method="post" enctype="multipart/form-data">
<aui:input type="hidden" name="grupId" value="<%=themeDisplay.getScopeGroupId() %>"/>
<aui:input name="fileName" label="image" id="fileName" type="file" value="" >
				<aui:validator name="acceptFiles">'jpg, jpeg, png, gif'</aui:validator>
</aui:input>
<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancelURL %>" type="cancel" />
	</aui:button-row>
</aui:form>