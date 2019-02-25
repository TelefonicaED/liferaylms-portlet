<!-- <h1 class="taglib-categorization-filter entry-title"> -->
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.ParseException"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseOccur"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClause"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQuery"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryFinderUtil"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.lms.util.CourseComparator"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>

<%

String search = ParamUtil.getString(request, "search","");
String freetext = ParamUtil.getString(request, "freetext","");
String tags = ParamUtil.getString(request, "tags","");
Integer state = ParamUtil.getInteger(request, "state",WorkflowConstants.STATUS_APPROVED);

long catId=ParamUtil.getLong(request, "categoryId",0);

String[] tagsSel = null;

ServiceContext sc=ServiceContextFactory.getInstance(request);
tagsSel = sc.getAssetTagNames();

long[] tagsSelIds = null;

if(tagsSel != null){
	long[] groups = new long[]{themeDisplay.getScopeGroupId()};
	tagsSelIds = AssetTagLocalServiceUtil.getTagIds(groups, tagsSel);
}


Enumeration<String> pnames =request.getParameterNames();
ArrayList<String> tparams = new ArrayList<String>();
ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
ArrayList<Long> assetTagIds = new ArrayList<Long>();


while(pnames.hasMoreElements()){
	String name = pnames.nextElement();
	if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
		tparams.add(name);
		String value = request.getParameter(name);
		String[] values = value.split(",");
		for(String valuet : values){
			try{
				assetCategoryIds.add(Long.parseLong(valuet));
			}catch(Exception e){
			}
		}
		
	}
}

if(ParamUtil.getString(request, "search").equals("search")){
	portletSession.setAttribute("freetext", freetext);
	portletSession.setAttribute("state", state);
	portletSession.setAttribute("assetCategoryIds", assetCategoryIds);
	portletSession.setAttribute("assetTagIds", tagsSelIds);

}else{
	try{
		String freetextTemp = (String)portletSession.getAttribute("freetext");
		if(freetextTemp!=null){
			freetext = freetextTemp;
		}
	}catch(Exception e){}
	try{
		ArrayList<Long> assetCategoryIdsTemp = (ArrayList<Long>)portletSession.getAttribute("assetCategoryIds");
		if(assetCategoryIdsTemp!=null){
			assetCategoryIds = assetCategoryIdsTemp;
		}
	}catch(Exception e){}
	try{
		ArrayList<Long> assetTagIdsTemp = (ArrayList<Long>)portletSession.getAttribute("assetTagIds");
		if(assetTagIdsTemp!=null){
			assetTagIds = assetTagIdsTemp;
		}
	}catch(Exception e){}
	try{
		Integer stateTemp = (Integer)portletSession.getAttribute("state");
		if(stateTemp!=null){
			state = stateTemp;
		}
	}catch(Exception e){}
}


boolean scategories = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("categories", new String[]{StringPool.TRUE})[0],true);

HashMap<Long,Document> lucenes = new HashMap<Long,Document>();
if(!freetext.isEmpty()){
	SearchContext scon=new SearchContext();
	scon.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
	if(catId>0){
		try{
			BooleanQuery booleanQueryCategoryId = BooleanQueryFactoryUtil.create(scon);
			booleanQueryCategoryId.addTerm("assetCategoryIds", catId);
			BooleanClause booleanClauseCategoryId = BooleanClauseFactoryUtil.create(scon,booleanQueryCategoryId, BooleanClauseOccur.MUST.getName());
			BooleanClause[] clauses = {booleanClauseCategoryId};
			scon.setBooleanClauses(clauses);
		}catch(ParseException pe){
			pe.printStackTrace();
		}
	}
	scon.setCompanyId(themeDisplay.getCompanyId());
	scon.setKeywords(freetext+"*");
	
	Indexer indexer=IndexerRegistryUtil.getIndexer(Course.class);
	Hits hits=indexer.search(scon);
	Document[] docs=hits.getDocs();
	for(Document doc : docs){
		lucenes.put(Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)), doc);
	}
}

java.util.List<Course> courses=null;
AssetCategory category=null;
long[] catIds=ParamUtil.getLongValues(request, "categoryIds");

StringBuffer sb = new StringBuffer();
for(long cateId : assetCategoryIds){
	sb.append(cateId);
	sb.append(",");
}
String catIdsText = sb.toString();

if((catIds==null||catIds.length<=0)&&(assetCategoryIds!=null&&assetCategoryIds.size()>0)){
	catIds = new long[assetCategoryIds.size()];
	for(int i=0;i<assetCategoryIds.size();i++){
		catIds[i] = assetCategoryIds.get(i);
	}
}
if(assetCategoryIds==null||assetCategoryIds.size()<=0){
	for(long cat:catIds)
	{
		if(cat!=0)
		{
		AssetCategory categ=AssetCategoryLocalServiceUtil.getAssetCategory(cat);
		
			String cats=StringUtil.merge(ArrayUtil.remove(catIds, cat));
		    if(cats==null || cats.equals(""))
		    {
		    	cats="0";
		    }
		%>
		<liferay-portlet:renderURL var="choseCatURL" >
			<liferay-portlet:param name="categoryIds" value="<%=cats %>"/>
			</liferay-portlet:renderURL>
		<span class="asset-entry">
		<%=categ.getTitle(locale) %>
		<a href="<%= choseCatURL %>" title="<liferay-ui:message key="remove" />">
					<span class="aui-icon aui-icon-close aui-textboxlistentry-close"></span>
				</a>
		</span>
		<%
		}
	}
}
%>
</h1>
<%
if(catIds==null ||catIds.length==0)
{
	if(catId!=0)
	{
		catIds=new long[]{catId};
	}
}
if(catIds!=null&&catIds.length>0)
{
	catIds=ArrayUtil.remove(catIds, 0l);
}
if( (catIds!=null&&catIds.length>0) || !freetext.isEmpty() || state!=WorkflowConstants.STATUS_ANY)
{
	List<AssetEntry> entries=new ArrayList<AssetEntry>();
	AssetEntryQuery entryQuery=new AssetEntryQuery();
	long[] groupIds={themeDisplay.getScopeGroupId()};
	entryQuery.setAllCategoryIds(catIds);
	if(tagsSelIds != null){
		entryQuery.setAllTagIds(tagsSelIds);
	}
	entryQuery.setGroupIds(groupIds);
	entryQuery.setClassName(Course.class.getName());
	entryQuery.setEnablePermissions(true);
	entryQuery.setExcludeZeroViewCount(false);
	entryQuery.setVisible(true);
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));
	entryQuery.setVisible(false);
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));

	courses=new ArrayList<Course>();
	if(!freetext.isEmpty()){
		for(AssetEntry entry:entries)
		{
			if(lucenes.get(entry.getClassPK())!=null){
				Course course = null;
				try{
					course = CourseLocalServiceUtil.getCourse(entry.getClassPK());
				}catch(Exception e){
					continue;
				}
				
				if(state!=WorkflowConstants.STATUS_ANY){
					if(state==WorkflowConstants.STATUS_APPROVED){
						if(!course.getClosed())
							courses.add(course);
					}
					else if(state==WorkflowConstants.STATUS_INACTIVE){
						if(course.getClosed())
							courses.add(course);
					}
				}else{
					courses.add(course);
				}
			}
		}
	}else{
		for(AssetEntry entry:entries){
			Course course = null;
			try{
				course = CourseLocalServiceUtil.getCourse(entry.getClassPK());
			}catch(Exception e){
				continue;
			}
			
			if(state!=WorkflowConstants.STATUS_ANY){
				if(state==WorkflowConstants.STATUS_APPROVED){
					if(!course.getClosed())
						courses.add(course);
				}
				else if(state==WorkflowConstants.STATUS_INACTIVE){
					if(course.getClosed())
						courses.add(course);
				}
			}else{
				courses.add(course);
			}
		}
	}
}
else
{
	courses=CourseServiceUtil.getCoursesOfGroup(scopeGroupId);
}
java.util.List<Course> finalCourses=new ArrayList<Course>();
for(Course course:courses)
{
		finalCourses.add(course);
}
courses=finalCourses;

//Order courses

if(courses!=null&&courses.size()>0){
	CourseComparator courseComparator = new CourseComparator(themeDisplay.getLocale());
	Collections.sort(courses, courseComparator );
}


PortletPreferences preferences = null;
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
	preferences = renderRequest.getPreferences();
}

boolean showSearchTags = preferences.getValue("showSearchTags","false").equals("true");
if(!"".equals(preferences.getValue("showSearchTagsGeneralStats", ""))){
	showSearchTags = preferences.getValue("showSearchTagsGeneralStats","false").equals("true");
}

if(!"".equals(preferences.getValue("showSearchCategoriesGeneralStats", ""))){
	scategories = preferences.getValue("showSearchCategoriesGeneralStats", "true").equals("true");
}
%>

<portlet:renderURL var="searchURL">
</portlet:renderURL>
	<div class="admin-course-search-form">
		<aui:form action="${searchURL}" method="post" name="search" role="search">
			<aui:fieldset cssClass="checkBoxes">
				<aui:input name="search" type="hidden" value="search" />
				<aui:input inlineField="true" name="freetext" type="text" value="<%=freetext %>">
					<aui:validator name="maxLength">75</aui:validator>
				</aui:input>
				<aui:select inlineField="true" name="state">
					<aui:option label="active" selected="<%= (state == WorkflowConstants.STATUS_APPROVED) %>" value="<%= WorkflowConstants.STATUS_APPROVED %>" />
					<aui:option label="inactive" selected="<%= (state == WorkflowConstants.STATUS_INACTIVE) %>" value="<%= WorkflowConstants.STATUS_INACTIVE %>" />
					<aui:option label="any-status" selected="<%= (state == WorkflowConstants.STATUS_ANY) %>" value="<%= WorkflowConstants.STATUS_ANY %>" />
				</aui:select>
							
				<aui:button type="submit" value="search"></aui:button>
			</aui:fieldset>
			<c:if test="<%=showSearchTags %>">
				<liferay-ui:panel id="panel_tags" title="tags" collapsible="true" defaultState="closed">
					<liferay-ui:asset-tags-selector ></liferay-ui:asset-tags-selector>
				</liferay-ui:panel>
			</c:if>
			<c:if test="<%=scategories%>">
				<liferay-ui:panel id="panel_categories" title="categorization" collapsible="true" defaultState="<%=catIds.length>0?\"open\":\"closed\" %>">
					<aui:fieldset cssClass="checkBoxes">
						<liferay-ui:asset-categories-selector  className="<%= Course.class.getName() %>" curCategoryIds="<%=catIdsText %>" />
					</aui:fieldset>
				</liferay-ui:panel>
			</c:if>
		</aui:form>
	</div>