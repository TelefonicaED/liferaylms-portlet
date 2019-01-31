<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ include file="/init.jsp"%>

<liferay-portlet:actionURL name="saveDeregister" var="saveDeregisterURL"/>	

<aui:form action="${saveDeregisterURL}" role="form">
	<liferay-ui:success key="saveDeregisterOK" message="deregister-mail.save-deregister-mail-ok"/>
	<liferay-ui:error key="saveDeregisterKO" message="deregister-mail.save-deregister-mail-error"/>
	<aui:input name="deregister" type="checkbox" label="deregister-mail.label" value="${emailVerified}" helpMessage="deregister-mail.help"></aui:input>
	<aui:button name="save" type="submit"/>
</aui:form>
