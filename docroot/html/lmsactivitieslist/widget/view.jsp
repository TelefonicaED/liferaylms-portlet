<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp"%>

<%--
 	Estado del portlet para usar como mashup en otros portales.
 	Url de ejemplo:
 		http://localhost:8080/widget/web/nuevo-curso-2/reto/-/lmsactivitieslist_WAR_liferaylmsportlet
 	Si se quiere cargar un visor hay que envíar un mensaje html5.
 	Por ejemplo:
		window.postMessage({actId:4501,moduleId:3705,typeId:5}, window.location.origin);
 --%>

<liferay-portlet:renderURL portletName="offlinetaskactivity_WAR_liferaylmsportlet" var="openActivity" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="actId" value="4501"></liferay-portlet:param>
</liferay-portlet:renderURL>
<%
	JSONObject types = JSONFactoryUtil.createJSONObject();
	for(LearningActivityType type:new LearningActivityTypeRegistry().getLearningActivityTypes()){
		types.put(Long.toString(type.getTypeId()), type.getPortletId());
	}
%>

<script type="text/javascript">
<!--
window.<portlet:namespace />portletIds=<%=types.toString() %>;

function <portlet:namespace />onload(source) {

	AUI().use('node',function(A) {
	    if (source.Document && source.Document.body.scrollHeight) 
	        source.height = source.contentWindow.document.body.scrollHeight;
	    else if (source.contentDocument && source.contentDocument.body.scrollHeight) 
	        source.height = source.contentDocument.body.scrollHeight + 35;
	    else (source.contentDocument && source.contentDocument.body.offsetHeight) 
	        source.height = source.contentDocument.body.offsetHeight + 35;

		
	});
}

AUI().ready('domready','event','liferay-portlet-url',function(A) {
	
	A.one(window).on('message', 
		function(event){
			var activity=event._event.data,
				iframeContainer = A.one('#<portlet:namespace/>container'),
				renderUrl = Liferay.PortletURL.createRenderURL(),
				interval;							
			renderUrl.setWindowState('<%=LiferayWindowState.POP_UP.toString()%>');
			renderUrl.setPortletId(window.<portlet:namespace />portletIds[activity.typeId]);
			renderUrl.setParameter('actId',activity.actId);
			renderUrl.setParameter('moduleId',activity.moduleId);
			iframeContainer.on('load', 
				function(event){
					if(!!interval){
						A.clearInterval(interval);
						delete interval;
					}
					interval = A.setInterval(function(){ 
						iframeContainer.get('contentDocument').
							all('A[target=\'_blank\']').
								each( function( anchor ) {
									var href = anchor.get('href');
									anchor.setAttrs({
										target: '_self',
										href: '#'
									});
									anchor.on('click', 
										function(event){
											window.parent.postMessage({actId:activity.actId,moduleId:activity.moduleId,typeId:activity.typeId,href:href}, '*');
									});
							});
					}, 100);
				});

			iframeContainer.set('src',renderUrl.toString());
	});
	initializeKeepAlive();
});

Liferay.provide(
	window,
	'initializeKeepAlive',
	function() {
		var A = AUI();
	    A.setInterval(function() {sessionKeepAlive();}, Liferay.Session._getWarningTime() - 60000);
	},
	['node']
);

Liferay.provide(
    window,
    'sessionKeepAlive',
    function() {
        Liferay.Session.extend();
    },
    ['liferay-session']
);

//-->
</script>
<iframe id="<portlet:namespace/>container" src="javascript:false;" onload="<portlet:namespace />onload(this)" frameBorder="0" width="100%" height="100%" scrolling="no"></iframe>
