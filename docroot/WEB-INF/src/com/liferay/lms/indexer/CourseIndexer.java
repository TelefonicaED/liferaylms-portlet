package com.liferay.lms.indexer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
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
		return _PERMISSION_AWARE; 
	}
	
	@Override
	public void postProcessContextQuery(BooleanQuery contextQuery, SearchContext searchContext) throws Exception {
		
		int status = GetterUtil.getInteger(searchContext.getAttribute(Field.STATUS), WorkflowConstants.STATUS_APPROVED);
		
		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}
		
		int statusCourse = GetterUtil.getInteger(searchContext.getAttribute("statusCourse"), WorkflowConstants.STATUS_ANY);
		
		if (statusCourse != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm("statusCourse", statusCourse);
		}
	}

	@Override
	public void postProcessSearchQuery(BooleanQuery searchQuery, SearchContext searchContext) throws Exception {
		
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, true);
	}
	
	@Override
	protected void addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		String value = String.valueOf(searchContext.getAttribute(field));

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		field = DocumentImpl.getLocalizedName(searchContext.getLocale(), field);

		if (searchContext.isAndSearch()) {
			searchQuery.addRequiredTerm(field, value, like);
		}else {
			searchQuery.addTerm(field, value, like);
		}
	}
	
	@Override
	protected void doDelete(Object obj) throws Exception {
		
		if(log.isDebugEnabled())log.debug("doDelete");
		Course course = (Course)obj;

		deleteDocument(course.getCompanyId(), course.getCourseId());

	}
	
	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Course entry = (Course)obj;
		if(log.isDebugEnabled())log.debug("doGetDocument::"+entry.getCourseId());
		
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		User user = UserLocalServiceUtil.fetchUserById(userId);
		String userName =  user != null ? user.getFullName() : "";
		long entryId = entry.getCourseId();
		Map<Locale, String> titleMap = entry.getTitleMap();
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
		document.addLocalizedText(Field.TITLE, titleMap);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);
		document.addKeyword("statusCourse", entry.getStatus());

		document.addKeyword(Field.ENTRY_CLASS_NAME, Course.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		document.addKeyword(Field.STATUS, entry.getClosed() ? WorkflowConstants.STATUS_INACTIVE : WorkflowConstants.STATUS_APPROVED);
		
		Locale[] locales = LanguageUtil.getAvailableLocales();
		String[] assetCategoryTitles = null;
		AssetCategory categoria=null;
		for (Locale locale : locales) {
			assetCategoryTitles = new String[assetCategoryIds.length];
			for (int i=0; i<assetCategoryIds.length;i++){
				categoria = AssetCategoryLocalServiceUtil.getCategory(assetCategoryIds[i]);
				assetCategoryTitles[i]=categoria.getTitle(locale);
			}
	
			document.addKeyword("assetCategoryTitles".concat(StringPool.UNDERLINE).concat(LanguageUtil.getLanguageId(locale)), assetCategoryTitles);
		}
		locales=null;
		assetCategoryTitles=null;
		
		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);
		
		Map<String, Field> values = document.getFields();
		for (Map.Entry<String, Field> entri : values.entrySet()) {
			log.debug("Key = " + entri.getKey() + ", Value = " + entri.getValue());
		}    

		if(log.isDebugEnabled())log.debug("return Document");
		return document;
	}
	
	
	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet,
			PortletURL portletURL) throws Exception {
		
		if(log.isDebugEnabled())log.debug("doGetSummary");
		
		String title = document.get(locale, Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(
				document.get(locale, Field.CONTENT), 200);
		}

		return new Summary(title, content, portletURL);
	}
	
	/**
	 * Solo se reindexan los cursos que son visibles y estan abiertos
	 */
	@Override
	protected void doReindex(Object obj) throws Exception {
		Course course = (Course)obj;
		if(log.isDebugEnabled())log.debug("doReindex::"+course.getCourseId());
		
		Document document = getDocument(course);
		
		AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
		
		if(course.isClosed() || !entry.getVisible()){
			SearchEngineUtil.deleteDocument(getSearchEngineId(), course.getCompanyId(), document.get(Field.UID));
		}else{
			SearchEngineUtil.updateDocument(getSearchEngineId(),entry.getCompanyId(), document);
		}
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

	/**
	 * Solo se reindexan los cursos que están publicados y abiertos ya que son los que se muestran en las busquedas
	 * @param companyId
	 * @throws Exception
	 */
	private void reindexEntries(long companyId) throws Exception {
		if(log.isDebugEnabled())log.debug("reindexEntries company::"+companyId);
		
		List<Course> courses = CourseLocalServiceUtil.getCourses(companyId);
		
		if(courses==null ||courses.isEmpty()){
			return;
		}
		
		AssetEntry entry = null;
		Document document = null;
		
		Collection<Document> documents = new ArrayList<Document>(courses.size());
		
		for(Course course : courses)
		{
			document = getDocument(course);
			
			if(course.isClosed()){
				SearchEngineUtil.deleteDocument(getSearchEngineId(), course.getCompanyId(), document.get(Field.UID));
				continue;
			}
			
			entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
			
			if(!entry.getVisible()){
				SearchEngineUtil.deleteDocument(getSearchEngineId(), course.getCompanyId(), document.get(Field.UID));
				continue;
			}
			
			documents.add(document);
		}
		
		SearchEngineUtil.updateDocuments(getSearchEngineId(), companyId, documents);

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
	
	private static final boolean _PERMISSION_AWARE = true;
}
