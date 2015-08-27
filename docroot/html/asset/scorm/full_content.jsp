<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>

<%@ include file="/init.jsp" %>

<% SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");

if (request.getAttribute("learningTry") == null) { %>
<script type="text/javascript">
	localStorage.removeItem('scormpool');
</script>
<% }

if (Validator.isNotNull(scorm.getDescription()) && request.getAttribute("learningTry") == null) 
{
%>


<div class="asset-description"><%=scorm.getDescription() %></div>

<%
} 


String scoshow= ParamUtil.getString(request, "scoshow","");

String urlIndex=themeDisplay.getPortalURL()+this.getServletContext().getContextPath()+
"/scorm/"+Long.toString(scorm.getCompanyId())+"/"+Long.toString(scorm.getGroupId())+"/"+scorm.getUuid()+"/imsmanifest.xml?scoshow="+scoshow;
String iconsDir = "/liferaylms-portlet";
String conTema = PropsUtil.get("scorm.icons.theme");
try {
	if (conTema != null && Boolean.valueOf(conTema)) {
		iconsDir = themeDisplay.getPathThemeImages()+"/custom";
	}
} catch(Exception e) {
	
}
%>

   <script type="text/javascript">
     function InitPlayer() {
       PlayerConfiguration.Debug = false;
       PlayerConfiguration.StorageSupport = true;

       PlayerConfiguration.TreeMinusIcon = "<%= iconsDir %>/icons/scorm/minus.gif";
       PlayerConfiguration.TreePlusIcon = "<%= iconsDir %>/icons/scorm/plus.gif";
       PlayerConfiguration.TreeLeafIcon = "<%= iconsDir %>/icons/scorm/leaf.gif";
       PlayerConfiguration.TreeActiveIcon = "<%= iconsDir %>/icons/scorm/select.gif";

       PlayerConfiguration.BtnPreviousLabel = "<liferay-ui:message key="scorm.previous" />";
       PlayerConfiguration.BtnContinueLabel = "<liferay-ui:message key="scorm.next" />";
       PlayerConfiguration.BtnExitLabel = "<liferay-ui:message key="scorm.exit" />";
     
       PlayerConfiguration.BtnExitAllLabel = "<liferay-ui:message key="activity.try.exit" />";
       
           PlayerConfiguration.BtnAbandonLabel = "<liferay-ui:message key="scorm.abandon" />";
       PlayerConfiguration.BtnAbandonAllLabel = "<liferay-ui:message key="scorm.abandonall" />";
       PlayerConfiguration.BtnSuspendAllLabel = "<liferay-ui:message key="scorm.suspendall" />";

       //manifest by URL   
       Run.ManifestByURL("<%=urlIndex%>", false);
     }
     
     AUI().ready(function() {
    	 InitPlayer();
   	 });
     
  </script>
  	<%
	if(renderRequest.getWindowState().toString().equals("normal"))
	{
	%>
	<div class="placeholder_normal">
  	<div id="placeholder_clicker" style="display: none"></div>
	<div class="placeholder_menu" style="width: 99%;">
 		<div id="placeholder_navigationContainer"></div>
 		<div id="placeholder_treeContainer"></div>
 	<%}else{%>
 	<div class="placeholder_popup">
  	<div id="placeholder_clicker" style="display: none"></div>
 		<div id="placeholder_treeContainer" class="scorm-treeContainer-popup">	
 	<%}%>
 	
	</div>

	<div id="placeholder_treecontentContainer">
		
		<%
		if(renderRequest.getWindowState().toString().equals("normal"))
		{
			
		%>
	    	<div id="placeholder_contentIFrame" style="height: 680px; width: 100%;">
	          <iframe id="contentIFrame" style="height:680px; width:100%" allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true" ></iframe>
	    </div>
		
	    <%
		}
	    else
	    {
	    	%>
	    	<script>
	 
	    	window.onresize=function(event)
	    	{
	    	    var html = document.documentElement;
	            height=html.clientHeight;
	    		var nav1=document.getElementById("placeholder_navigationContainer").clientHeight;
	    		var nav2=document.getElementById("placeholder_navigationContainer2").clientHeight;
	    		var iframeHeight=height-nav1-nav2-20;
	    		iframeHeight=iframeHeight+"px";
	    		document.getElementById("placeholder_contentIFrame").style.height=iframeHeight;
	    		document.getElementById("contentIFrame").style.height=iframeHeight;
	    	}
	    	</script>
	    	<div id="placeholder_contentIFrame" style="width:100%" >
	          <iframe id="contentIFrame" style="height:688px;width:100%" allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true" ></iframe>
	    </div>
	    
	    	<%
	    	} 
	    	%>
	</div>
</div>
	<div id="placeholder_navigationContainer2"></div>

