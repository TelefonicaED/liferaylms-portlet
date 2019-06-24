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

<%
long resId = ParamUtil.getLong(request, "resId");
long resModuleId = ParamUtil.getLong(request, "resModuleId");

long assetId=ParamUtil.getLong(request, "assertId");
String assetTitle=StringPool.BLANK;
String disabled = "disabled=\"disabled\"";



if(assetId!=0){
	try{
		AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(assetId);
		assetId=entry.getEntryId();
		assetTitle=entry.getTitle(renderRequest.getLocale());
	}catch(Exception e){
		assetId=0;
	}
}

LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");
if(learningActivity!=null) {	
	if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
		try{
			if(assetId==0){
				AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(
					GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"assetEntry")));
				assetId=entry.getEntryId();
				assetTitle=entry.getTitle(renderRequest.getLocale());	
			}
		}
		catch(PortalException e){}
	}	
}

if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())) disabled="";

%>
<liferay-portlet:renderURL var="selectResource" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/resourceInternalActivity/admin/searchresource.jsp"/>
</liferay-portlet:renderURL>


<aui:input type="hidden" name="assetEntryId" ignoreRequestValue="true" value="<%=Long.toString(assetId) %>"/>
<aui:field-wrapper name="resourceinternalactivity.edit.asserts" cssClass="search-button-container">
	<aui:input type="text" name="assetEntryName" ignoreRequestValue="true" value="<%=assetTitle %>" label="" inlineField="true" disabled="true"  size="50"/>
	
	
		<liferay-portlet:renderURL var="buscarRecurso" >
			<liferay-portlet:param name="mvcPath" value="/html/resourceInternalActivity/admin/searchresource.jsp" />
			<liferay-portlet:param name="resId" value="<%=String.valueOf(resId) %>" />
			<liferay-portlet:param name="actId" value="<%=String.valueOf(resId) %>" />
			<liferay-portlet:param name="resModuleId" value="<%=String.valueOf(resModuleId) %>" />
		</liferay-portlet:renderURL>
		
		<button <%=disabled %> type="button" id="<portlet:namespace/>searchEntry" onclick="javascript:${renderResponse.getNamespace()}goToSearchResource();" >
		    <span class="aui-buttonitem-icon aui-icon aui-icon-search"></span>
		    <span class="aui-buttonitem-label"><%= LanguageUtil.get(pageContext, "search") %></span>
		</button>
	
</aui:field-wrapper>
		
<div id="<portlet:namespace/>backButton" style="display:none;">
	<liferay-ui:icon image="back" message="back" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"back();\" %>" label="true"  />
</div>

<script type="text/javascript">

function <portlet:namespace />goToSearchResource(){
	var form = document.createElement("form");
	var url =  '${buscarRecurso}';
	
	with(form) {
		setAttribute("name", "<portlet:namespace />formResourceInternal"); //nombre del form
		setAttribute("action", url); // action por defecto
		setAttribute("method", "post"); // method POST 
	}
	
	var inputDescription = document.createElement("input");
	with(inputDescription) {
		setAttribute("name", "<portlet:namespace />description"); //nombre del input
		setAttribute("type", "hidden"); // tipo hidden
		setAttribute("value", ""); // valor por defecto
	}
	form.appendChild(inputDescription);
	
	var inputTitle = document.createElement("input");
	with(inputTitle) {
		setAttribute("name", "<portlet:namespace />title"); //nombre del input
		setAttribute("type", "hidden"); // tipo hidden
		setAttribute("value", ""); // valor por defecto
	}
	
	form.appendChild(inputTitle);
	
	document.getElementsByTagName("body")[0].appendChild(form);
	
	var languageId = '<%= themeDisplay.getLanguageId()%>';
	
	document.<portlet:namespace />formResourceInternal.<portlet:namespace />description.value = $('#<portlet:namespace />description').val();
	document.<portlet:namespace />formResourceInternal.<portlet:namespace />title.value = $('#<portlet:namespace />title_'+languageId).val();

	document.<portlet:namespace />formResourceInternal.submit();
}

</script>
