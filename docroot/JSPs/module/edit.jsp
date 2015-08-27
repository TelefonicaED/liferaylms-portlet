
<%@include file="../init.jsp" %>

<liferay-ui:success key="module-prefs-success" message="module-prefs-success" />

<form name="setmodulePref" action="<portlet:actionURL name="setmodulePref" />" method="POST">
<table border="0">
	<tbody>
		<tr>
			<td>
				<liferay-ui:message key="module-rows-per-page" />*<br>
				<input type="text" name="module-rows-per-page" value="<%=prefs.getValue("module-rows-per-page","") %>" size="5" />
				<liferay-ui:error key="module-rows-per-page-required" message="module-rows-per-page-required" />
				<liferay-ui:error key="module-rows-per-page-invalid" message="module-rows-per-page-invalid" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="module-date-format" />*<br>
				<input type="text" name="module-date-format" value="<%=prefs.getValue("module-date-format","")%>" size="45" />
				<liferay-ui:error key="module-date-format-required" message="module-date-format-required" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="module-datetime-format" />*<br>
				<input type="text" name="module-datetime-format" value="<%=prefs.getValue("module-datetime-format","")%>" size="45" />
				<liferay-ui:error key="module-datetime-format-required" message="module-datetime-format-required" />
			</td>
		</tr>
	</tbody>
</table>
<input type="submit" value="submit" />
</form>
