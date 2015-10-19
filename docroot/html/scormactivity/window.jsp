<%@page import="com.liferay.lms.learningactivity.scormcontent.ScormContenRegistry"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="javax.portlet.MimeResponse"%>
<%@page import="com.liferay.lms.learningactivity.scormcontent.ScormContentAsset"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
long actId = ParamUtil.getLong(request, "actId", 0L);
LearningActivityTry learningTry = (LearningActivityTry) request.getAttribute("learningTry");
if (learningTry == null) {
	long latId = ParamUtil.getLong(request, "latId", 0L);
	learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
	
}
request.setAttribute("learningTry", learningTry);
String scoshow= GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "sco"), "");
long entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "assetEntry"), 0);
boolean isDebugActive = Boolean.parseBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "scormDebug"));
			
if(entryId != 0)
{
	%>
	<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
<script type="text/javascript">
	localStorage.removeItem('scormpool');
	var tryResultDataOld = '<%= HtmlUtil.escapeJS(learningTry.getTryResultData()) %>';
	if (tryResultDataOld != '') 
	{
		localStorage.setItem('scormpool', tryResultDataOld);
	}
	var showscorm=true;
</script>
	<%
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	ScormContentAsset scormContentAsset=ScormContenRegistry.getScormContentAsset(entry.getClassName());
	
	if(scormContentAsset!=null)
	{
    	String manifestURL="";
		if("".equals(scoshow))
		{
			manifestURL=scormContentAsset.getManifestURL(renderRequest,entry);
		}
		else
		{
			manifestURL=scormContentAsset.getManifestURL(scoshow,renderRequest,entry);
		}
		%>
		<!-- init -->
		
	<%
String [] jss = {
								"/liferaylms-portlet/js/scorm/sscompat.js",
								"/liferaylms-portlet/js/scorm/sscorlib.js",
								"/liferaylms-portlet/js/scorm/ssfx.Core.js",
							 
								"/liferaylms-portlet/js/scorm/API_BASE.js",
							    "/liferaylms-portlet/js/scorm/API.js",
							    "/liferaylms-portlet/js/scorm/API_1484_11.js",

							    "/liferaylms-portlet/js/scorm/Controls.js",
								"/liferaylms-portlet/js/scorm/LocalStorage.js",
								"/liferaylms-portlet/js/scorm/Player.js",
								"/liferaylms-portlet/js/scorm/PersistenceStoragePatched.js"
						};
						for (int i = 0; i < jss.length; i++) {
							org.w3c.dom.Element jsLink = renderResponse.createElement("script");
							jsLink.setAttribute("type", "text/javascript");
							jsLink.setAttribute("src", jss[i]);
							jsLink.setTextContent(" "); // important
							renderResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsLink);
						}
						%>
<%
String urlIndex=manifestURL;
if (request.getAttribute("learningTry") == null) { %>
<script type="text/javascript">
	localStorage.removeItem('scormpool');
</script>
<% }

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
    	
       PlayerConfiguration.Debug = <%=isDebugActive%>;
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

<%

if(isDebugActive){

%>

	<div style="overflow: scroll; height: 150px">
		
<portlet:resourceURL var="exportDebugToFile"/>
<script type="text/javascript">
function callServeResource(){

 	var debugContentValue = document.getElementById('placeholder_Debugger').innerHTML
	 var element = document.createElement('a');
	  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(debugContentValue));
	  element.setAttribute('download', '<%= LearningActivityLocalServiceUtil.getLearningActivity(actId).getTitle(locale) %>_debug.html');

	  element.style.display = 'none';
	  document.body.appendChild(element);
	  element.click();
	  document.body.removeChild(element);
 	
 
}
</script>
<form name="fm" id="fm">
<input type="button" value="<liferay-ui:message key="debugScorm.download" />"  onclick="callServeResource()">
</form>
		<div id="placeholder_Debugger" style="width: 30%"></div>
		
	</div>
	
	<%} %>





<!-- end -->
		<%

	}
	else
	{
	String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT); 
	themeDisplay.setIncludeServiceJs(true); 

	%>

<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>">
<liferay-util:param name="scoshow" value="<%=scoshow%>" />
</liferay-util:include>
	<%
	}
	%>
<script type="text/javascript">
if(typeof scormembededmode == 'undefined')
{
	scormembededmode=false;
}

	var update_scorm = function(e) 
	{
        	
		var scormpool = localStorage['scormpool'];
		
		var serviceParameterTypes = [
	     	'long',
	    	'java.lang.String',
	    	'java.lang.String'
	    ];
					 		
	    var message = Liferay.Service.Lms.LearningActivityResult.update(
	    	{
	   			latId: <%= learningTry.getLatId() %>,
	   			tryResultData: scormpool,
	   			imsmanifest: Run.$1.xml,
	   			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	    	}
	    );
	      	
	    var exception = message.exception;	
	            
		if (!exception) {
			// Process Success - A LearningActivityResult returned
		} else {
			// Process Exception
		}
	};
	
	var finishedscorm=false;
	var finish_scorm = function(e) 
	{
		
		if(!finishedscorm)
	   	{
			
			var scormpool = localStorage['scormpool'];
			var serviceParameterTypes = [
		     	'long',
		    	'java.lang.String',
		    	'java.lang.String'
		    ];
			
		    var message = Liferay.Service.Lms.LearningActivityResult.updateFinishTry(
		    	{
		   			latId: <%= learningTry.getLatId() %>,
		   			tryResultData: scormpool,
		   			imsmanifest: Run.$1.xml,
		   			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
		    	}
		    );
		      	
		    var exception = message.exception;
		            
			if (!exception) {
				// Process Success - A LearningActivityResult returned
				if (message.passed) {
					if(window.opener)
					{
				    	if (typeof window.opener.updateActivityNavigation == 'function') { 
							window.opener.updateActivityNavigation(); 
						}
						if (typeof window.opener.updateActivityList == 'function') { 
							window.opener.updateActivityList(); 
						}
						if (typeof window.opener.updateScormStatus == 'function') { 
							window.opener.updateScormStatus(message); 
						}
					}
					if (typeof window.updateActivityNavigation == 'function') { 
						window.updateActivityNavigation(); 
					}
					if (typeof window.updateActivityList == 'function') { 
						window.updateActivityList(); 
					}
					if (typeof window.updateScormStatus == 'function') { 
						window.updateScormStatus(message); 
					}
				}
			} else 
			{
				// Process Exception
			}
			finishedscorm=true;
			if(scormembededmode==false)
			{
				window.close();
			}
	  	}

	};
	function msieversion() {

        var ua = window.navigator.userAgent;
        var msie = ua.indexOf("MSIE ");

        if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  
        {
            return true;
        }
        else
        {
            return false;
        }

   return false;
}
		
	window.onbeforeunload= function(e)
			{ 
				scormembededmode=true;
				finish_scorm();
				if(!msieversion())
				{
					return null;
				}
			};
			window.onunload= function(e)
			{ 
				scormembededmode=true;
				finish_scorm();
			};
</script>
<% } %>