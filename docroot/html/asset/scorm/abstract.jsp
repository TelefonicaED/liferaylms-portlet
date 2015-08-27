<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@ include file="/init.jsp" %>
<%
int abstractLength = 200;

SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");
%>
<div class="asset-resource-info">
	<liferay-ui:icon
					alt="ico-preview"
					image='<%= "../file_system/large/scorm" %>'
					label="<%= false %>"
					message="<%= scorm.getTitle() %>"	
				/>
	
	<p class="asset-description">
	<%= StringUtil.shorten(scorm.getDescription() , abstractLength)%>
</p>
</div>