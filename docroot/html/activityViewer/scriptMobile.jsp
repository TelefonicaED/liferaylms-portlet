<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp" %>

<%
boolean enableLinks = ParamUtil.getBoolean(request, "enableLinks", false);
%>

<c:set var="enableLinks" value="<%=enableLinks%>"/>

<script type="text/javascript">

	var enableLinks = ${enableLinks};

	AUI().ready('event',function(A) {
		A.setInterval(function(){
				Liferay.Session.extend();
			}, 
			Liferay.Session._getWarningTime() - 60000);


		A.all('A[target=\'_blank\']').
			each( function( anchor ) {
				var href = anchor.get('href');
				var classes = anchor.get('className');
				if(!enableLinks || classes==null || (classes!=null && !classes.includes("tablet-link"))){
					anchor.setAttrs({
						target: '_self',
						href: '#'
					});
					anchor.on('click', 
						function(event){
							window.parent.postMessage({href:href}, '*');
					});	
					
				}				
			});
	});
</script>