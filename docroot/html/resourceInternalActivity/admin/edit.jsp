<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.Criterion"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>

<liferay-portlet:renderURL var="selectResource" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/resourceInternalActivity/admin/searchresource.jsp"/>
</liferay-portlet:renderURL>

<script type="text/javascript">
<!--

function <portlet:namespace />search() {
	AUI().use('node','aui-resize-iframe',function(A) {
		var backbutton = A.one('#<portlet:namespace/>backButton').one('span').clone();
		backbutton.setAttribute('id','<portlet:namespace/>backbutton');
		A.one('.portlet-body').prepend(backbutton);
		var iframe = A.Node.create('<iframe id="<portlet:namespace/>finder" src="javascript:false;" onload="<portlet:namespace />load(this)" frameBorder="0" scrolling="no" width="'+A.getBody().width()+'" height="0"></iframe>');
		A.one('.portlet-body').append(iframe);
		A.all('.acticons').each(function(icon){ icon.hide(); });
		A.one('#<portlet:namespace/>fm').hide();
		A.one('#<portlet:namespace/>backbutton').scrollIntoView();

		iframe.plug(A.Plugin.ResizeIframe);
		iframe.setAttribute('src','<%=selectResource %>');
	});
}
	
function <portlet:namespace />load(source) {

	AUI().use('node','querystring-parse',function(A) {
		
		var params=A.QueryString.parse(source.contentWindow.location.search.replace('?',''));
	
		if((params['<portlet:namespace />jspPage']=='/html/resourceInternalActivity/admin/result.jsp')&&
			(A.Lang.isNumber(params['<portlet:namespace />assertId']))&&
			(A.Lang.isString(params['<portlet:namespace />assertTitle']))) {
			A.one('#<portlet:namespace/>backbutton').remove();
			A.one('#<portlet:namespace/>finder').remove();
			A.all('.acticons').each(function(icon){ icon.show(); });
			A.one('#<portlet:namespace/>fm').show();
			A.one('#<portlet:namespace/>assetEntryId').set('value',params['<portlet:namespace />assertId']);		
			A.one('#<portlet:namespace/>assetEntryName').set('value',params['<portlet:namespace />assertTitle']);	
		}

	});
}

function <portlet:namespace />back() {
	AUI().use('node',function(A) {
		A.one('#<portlet:namespace/>backbutton').remove();
		A.one('#<portlet:namespace/>finder').remove();
		A.all('.acticons').each(function(icon){ icon.show(); });
		A.one('#<portlet:namespace/>fm').show();

	});
}

//-->
</script>

<%
long assetId=0;
String assetTitle=StringPool.BLANK;
String disabled = "disabled=\"disabled\"";
LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");
if(learningActivity!=null) {	
	if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
		try{
			AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(
				GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"assetEntry")));
			assetId=entry.getEntryId();
			assetTitle=entry.getTitle(renderRequest.getLocale());	
		}
		catch(PortalException e){}
	}	
}

if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())) disabled="";

%>

<aui:input type="hidden" name="assetEntryId" ignoreRequestValue="true" value="<%=Long.toString(assetId) %>"/>
<aui:field-wrapper name="resourceinternalactivity.edit.asserts" cssClass="search-button-container">
	<aui:input type="text" name="assetEntryName" ignoreRequestValue="true" value="<%=assetTitle %>" label="" inlineField="true" disabled="true"  size="50"/>
	<button <%=disabled %> type="button" id="<portlet:namespace/>searchEntry" onclick="<portlet:namespace/>search();" >
	    <span class="aui-buttonitem-icon aui-icon aui-icon-search"></span>
	    <span class="aui-buttonitem-label"><%= LanguageUtil.get(pageContext, "search") %></span>
	</button>
</aui:field-wrapper>
		
<div id="<portlet:namespace/>backButton" style="display:none;">
	<liferay-ui:icon image="back" message="back" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"back();\" %>" label="true"  />
</div>

