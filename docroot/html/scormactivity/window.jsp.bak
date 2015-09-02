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
			
if(entryId != 0)
{
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT); 
	themeDisplay.setIncludeServiceJs(true); 

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
<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>">
<liferay-util:param name="scoshow" value="<%=scoshow%>" />
</liferay-util:include>

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