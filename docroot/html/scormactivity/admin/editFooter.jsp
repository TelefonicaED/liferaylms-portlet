<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	if(request.getAttribute("activity")!=null) {
		LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
%>
	<script type="text/javascript">
	<!--
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />closeWindow',
	        function() {
		        
				if ((!!window.postMessage)&&(window.parent != window)) {
					if (!window.location.origin){
						window.location.origin = window.location.protocol+"//"+window.location.host;
					}

					if(AUI().UA.ie==0) {
						parent.postMessage({name:'closeActivity',
							                moduleId:<%=Long.toString(learningActivity.getModuleId())%>,
							                actId:<%=Long.toString(learningActivity.getActId())%>}, window.location.origin);
					}
					else {
						parent.postMessage(JSON.stringify({name:'closeActivity',
			                							   moduleId:<%=Long.toString(learningActivity.getModuleId())%>,
			                							   actId:<%=Long.toString(learningActivity.getActId())%>}), window.location.origin);
					}
				}
				else {
					window.location.href='<liferay-portlet:renderURL portletName="<%=PortalUtil.getJsSafePortletId(\"lmsactivitieslist\"+
							PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()) %>" />';
				}
	        }
	    );
	    
	//-->
	</script>
	<aui:button onClick="<%=renderResponse.getNamespace()+\"closeWindow()\" %>" type="cancel" value="canceledition"/>
<%
	}
%>