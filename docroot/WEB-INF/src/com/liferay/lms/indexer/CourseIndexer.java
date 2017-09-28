package com.liferay.lms.indexer;

import java.text.Normalizer;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class CourseIndexer extends BaseIndexer {
	private static Log log = LogFactoryUtil.getLog(CourseIndexer.class);

	public static final String[] CLASS_NAMES = {Course.class.getName()};
	public static final String PORTLET_ID = "courseadmin_WAR_liferaylmsportlet"; 
	@Override
	public String[] getClassNames() {
		if(log.isDebugEnabled())log.debug("getClassNames");
		return CLASS_NAMES;
	} 

	
	@Override
	public boolean isPermissionAware() {
		return true;
	}


	public Summary getSummary(Document document, String snippet, PortletURL portletURL) {
		if(log.isDebugEnabled())log.debug("getSummary");
		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String groupId = document.get("groupId");
		String articleId = document.get(Field.ENTRY_CLASS_PK);
		String version = document.get("version");
		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		if(log.isDebugEnabled())log.debug("doDelete");
		Course entry = (Course)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getCourseId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));

	}
	
	@Override
	public Document getDocument(Object obj) throws SearchException {
		try {
			return doGetDocument(obj);
		} catch (Exception e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isErrorEnabled())log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Course entry = (Course)obj;
		if(log.isDebugEnabled())log.debug("doGetDocument::"+entry.getCourseId());
		
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = UserLocalServiceUtil.getUser(userId).getFullName();
		long entryId = entry.getCourseId();
		String title = entry.getTitle();
		Date startDate = entry.getStartDate();
		Date endDate = entry.getEndDate();
		Date executionStartDate = entry.getExecutionStartDate();
		Date executionEndDate = entry.getExecutionEndDate();
		
		AssetEntry assetEntry= null;
		try{
			assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), entryId);
		}catch(Exception e){
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isErrorEnabled())log.error(e.getMessage());
		}
		String content= null;
		if(assetEntry!=null){
			content = assetEntry.getSummary();
		}
			content= content+" "+HtmlUtil.extractText(entry.getDescription(LocaleUtil.getDefault(),true));
			
		String contentSinAcentos=content.toLowerCase();
		contentSinAcentos = Normalizer.normalize(contentSinAcentos, Normalizer.Form.NFD);
		contentSinAcentos = contentSinAcentos.replaceAll("[^\\p{InCombiningDiacriticalMarks}]", "");
		
		content=content+" "+contentSinAcentos;
		Date displayDate = null;
		
		if(assetEntry!=null){
			displayDate = assetEntry.getPublishDate();
		}		

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(Course.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(Course.class.getName(), entryId);
		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);
		document.addDate(Field.MODIFIED_DATE, displayDate);
		document.addDate("startDate", startDate);
		document.addDate("endDate", endDate);
		document.addDate("executionStartDate", executionStartDate);
		document.addDate("executionEndDate", executionEndDate);
		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		Group dependentGroup=GroupLocalServiceUtil.getGroup(groupId);
		document.addText("groupName",dependentGroup.getName());
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, Course.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		document.addKeyword(Field.STATUS, entry.getClosed() ? WorkflowConstants.STATUS_INACTIVE : WorkflowConstants.STATUS_APPROVED);
		String[] assetCategoryTitles = new String[assetCategoryIds.length];
		for (int i=0; i<assetCategoryIds.length;i++){
			AssetCategory categoria = AssetCategoryLocalServiceUtil.getCategory(assetCategoryIds[i]);
			assetCategoryTitles[i]=categoria.getName();
		}

		document.addKeyword("assetCategoryTitles", assetCategoryTitles);
		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);
		
		Map<String, Field> values = document.getFields();
		for (Map.Entry<String, Field> entri : values.entrySet()) {
			log.debug("Key = " + entri.getKey() + ", Value = " + entri.getValue());
		}

		if(log.isDebugEnabled())log.debug("return Document");
		return document;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Course entry = (Course)obj;
		if(log.isDebugEnabled())log.debug("doReindex::"+entry.getCourseId());
		
		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(getSearchEngineId(),entry.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		if(log.isDebugEnabled())log.debug("doReindex");
		Course entry = CourseLocalServiceUtil.getCourse(classPK);
		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		if(log.isDebugEnabled())log.debug("doReindex");
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);

	}

	private void reindexEntries(long companyId) throws Exception {
		if(log.isDebugEnabled())log.debug("reindexEntries company::"+companyId);
		java.util.List<Course> entries=CourseLocalServiceUtil.getCourses(companyId);
		if(entries==null ||entries.isEmpty())
		{
			return;
		}
		for(Course entry:entries)
		{
			doReindex(entry);
		}
	}
	

	@Override
	protected String getPortletId(SearchContext searchContext) {
		if(log.isDebugEnabled())log.debug("getPortletId");
		return PORTLET_ID;
	}


	@Override
	public String getPortletId() {
		if(log.isDebugEnabled())log.debug("getPortletId");
		return PORTLET_ID;
	}


	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet,
			PortletURL portletURL) throws Exception {
		if(log.isDebugEnabled())log.debug("doGetSummary");
		return getSummary(document, snippet, portletURL);
	}

	@Override
	public void postProcessContextQuery(BooleanQuery contextQuery, SearchContext searchContext)
			throws Exception {
		int status = GetterUtil.getInteger(searchContext.getAttribute(Field.STATUS), WorkflowConstants.STATUS_APPROVED);
		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}
	}
}
