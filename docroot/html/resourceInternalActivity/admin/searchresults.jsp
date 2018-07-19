<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.SearchContextFactory"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContextUtil"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.portal.model.ServiceComponent"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@ include file="/html/resourceInternalActivity/admin/searchresource.jsp" %>
<%
String className=ParamUtil.getString(request,"className","");
String keywords=ParamUtil.getString(request,"keywords","");
String assetCategoryIds=ParamUtil.getString(request,"assetCategoryIds","");
long groupIdSelected=ParamUtil.getLong(request,"groupId",themeDisplay.getScopeGroupId());
ServiceContext serviceContext=ServiceContextFactory.getInstance( renderRequest);

AssetRendererFactory selarf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);
long[] groupIds=new long[1];
groupIds[0]=groupIdSelected;
String[] entryClassNames={className};

title =  ParamUtil.getString(request, "title", "");
description = ParamUtil.getString(request, "description","");


PortletURL portletURL= renderResponse.createRenderURL();
portletURL.setParameter("className",className);
portletURL.setParameter("jspPage","/html/resourceInternalActivity/admin/searchresults.jsp");
portletURL.setParameter("groupId",Long.toString(groupIdSelected));
portletURL.setParameter("actionEditing","true");
portletURL.setParameter("resModuleId",String.valueOf(resModuleId));
portletURL.setParameter("resId",String.valueOf(resId));
portletURL.setParameter("title",String.valueOf(title));
portletURL.setParameter("description",String.valueOf(description));

%>
<liferay-ui:search-container emptyResultsMessage="resourceInternalActivity.there-are-no-assets" iteratorURL="<%=portletURL%>"
 delta="10">
<liferay-ui:search-container-results>

<%
total=0;
if(keywords.length()>0)
{

	SearchContext searchContext=SearchContextFactory.getInstance(request);
	searchContext.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
	searchContext.setUserId(themeDisplay.getUserId());
	searchContext.setGroupIds(groupIds);
	searchContext.setEntryClassNames(entryClassNames);
	searchContext.setKeywords(keywords);
	searchContext.setStart(searchContainer.getStart());
	searchContext.setEnd(searchContainer.getEnd());
	
	Indexer indexer=IndexerRegistryUtil.getIndexer(className);
Hits hits = indexer.search(searchContext);


results = new ArrayList<AssetEntry>();
for(Document doc : hits.getDocs()) { 
	Long classPK = Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)); 
    
    AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(className, classPK);
    results.add(asset);
    total=hits.getLength();
}
}
else
{
	AssetEntryQuery query=new AssetEntryQuery();
	query.setClassName(className);
	query.setGroupIds(groupIds);
	query.setEnablePermissions(true);
	query.setExcludeZeroViewCount(false);
	if(serviceContext.getAssetCategoryIds()!=null)
	{
	query.setAllCategoryIds(serviceContext.getAssetCategoryIds());
	}
	query.setVisible(true);
	total = AssetEntryServiceUtil.getEntriesCount(query);
	query.setStart(searchContainer.getStart());
	query.setEnd(searchContainer.getEnd());
	results = AssetEntryServiceUtil.getEntries(query);
}



pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row 
	className="com.liferay.portlet.asset.model.AssetEntry"
	keyProperty="entryId"
	modelVar="assetEntry">
	
	<liferay-ui:search-container-column-text name="title" property="title" orderable="false"/>
	
<liferay-portlet:renderURL var="selectResourceURL">
 <liferay-portlet:param value="/html/editactivity/editactivity.jsp" name="jspPage"/>
 <liferay-portlet:param value="<%=Long.toString(assetEntry.getEntryId()) %>" name="assertId"/>
 <liferay-portlet:param value="<%=assetEntry.getTitle(renderRequest.getLocale()) %>" name="assertTitle"/>
 <liferay-portlet:param value="7" name="type"/>
 <liferay-portlet:param name="resModuleId" value="<%=String.valueOf(resModuleId) %>"/>
 <liferay-portlet:param name="resId" value="<%=String.valueOf(resId) %>"/>
 <liferay-portlet:param name="title" value="<%=String.valueOf(title) %>" />
<liferay-portlet:param name="description" value="<%=String.valueOf(description) %>" />
</liferay-portlet:renderURL>

<liferay-ui:search-container-column-text>
	<a class="newitem2" href="<%=selectResourceURL.toString() %>" ><liferay-ui:message key="select" /></a>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
 <liferay-ui:search-iterator />
</liferay-ui:search-container>
