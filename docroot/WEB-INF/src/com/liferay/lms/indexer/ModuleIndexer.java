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
import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class ModuleIndexer extends BaseIndexer {
	
	private static Log log = LogFactoryUtil.getLog(ModuleIndexer.class);

	public static final String[] CLASS_NAMES = {Module.class.getName()};
	public static final String PORTLET_ID = "editmodule_WAR_liferaylmsportlet"; 
	private static final boolean _PERMISSION_AWARE = true;
	
	@Override
	public String[] getClassNames() {
		if(log.isDebugEnabled())
			log.debug("getClassNames");
		return CLASS_NAMES;
	} 
	
	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE; 
	}
	
	@Override
	protected void doDelete(Object obj) throws Exception {
		
		if(log.isDebugEnabled())
			log.debug("doDelete");
		Module module = (Module)obj;

		deleteDocument(module.getCompanyId(), module.getModuleId());
	}
	
	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Module entry = (Module)obj;
		if(log.isDebugEnabled())
			log.debug("doGetDocument::"+entry.getModuleId());
		
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		User user = UserLocalServiceUtil.fetchUserById(userId);
		String userName =  user != null ? user.getFullName() : "";
		long entryId = entry.getModuleId();
		Map<Locale, String> titleMap = entry.getTitleMap();
		Date startDate = entry.getStartDate();
		Date endDate = entry.getEndDate();
		
		AssetEntry assetEntry= null;
		try{
			assetEntry = AssetEntryLocalServiceUtil.getEntry(Module.class.getName(), entryId);
		}catch(Exception e){
			if(log.isDebugEnabled())
				e.printStackTrace();
			if(log.isErrorEnabled())
				log.error(e.getMessage());
		}
		String content= null;
		if(assetEntry!=null)
			content = assetEntry.getSummary();
		
		content= content+" "+HtmlUtil.extractText(entry.getDescription(LocaleUtil.getDefault(),true));
			
		String contentSinAcentos=content.toLowerCase();
		contentSinAcentos = Normalizer.normalize(contentSinAcentos, Normalizer.Form.NFD);
		contentSinAcentos = contentSinAcentos.replaceAll("[^\\p{InCombiningDiacriticalMarks}]", "");
		
		content=content+" "+contentSinAcentos;
		Date displayDate = null;
		
		if(assetEntry!=null){
			displayDate = assetEntry.getPublishDate();
		}		
		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(Module.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(Module.class.getName(), entryId);
		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addDate(Field.MODIFIED_DATE, displayDate);
		document.addDate("startDate", startDate);
		document.addDate("endDate", endDate);
		document.addKeyword(Field.COMPANY_ID, companyId);
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

		document.addKeyword(Field.ENTRY_CLASS_NAME, Module.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		
		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);
		
		if(log.isDebugEnabled())
			log.debug("return Document");
		return document;
	}
	
	@Override
	protected void doReindex(Object obj) throws Exception {
		Module module = (Module)obj;
		if(log.isDebugEnabled())
			log.debug("doReindex::"+module.getModuleId());
		Document document = getDocument(module);
		AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Module.class.getName(),module.getModuleId());
		SearchEngineUtil.updateDocument(getSearchEngineId(),entry.getCompanyId(), document);
	}
	
	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		if(log.isDebugEnabled())
			log.debug("doReindex");
		Module entry = ModuleLocalServiceUtil.getModule(classPK);
		doReindex(entry);
	}

	
	@Override
	public Document getDocument(Object obj) throws SearchException {
		try {
			return doGetDocument(obj);
		} catch (Exception e) {
			if(log.isDebugEnabled())
				e.printStackTrace();
			if(log.isErrorEnabled())
				log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}


	@Override
	protected Summary doGetSummary(Document document, Locale locale,
			String snippet, PortletURL portletURL) throws Exception {
		if(log.isDebugEnabled())
			log.debug("doGetSummary");
		
		String title = document.get(locale, Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(
				document.get(locale, Field.CONTENT), 200);
		}

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		if(log.isDebugEnabled())
			log.debug("doReindex");
		long companyId = GetterUtil.getLong(ids[0]);
		reindexEntries(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}
	
	private void reindexEntries(long companyId) throws Exception {
		if(log.isDebugEnabled())
			log.debug("reindexEntries company::"+companyId);
		
		List<Module> modules = ModuleLocalServiceUtil.getModulesByCompanyId(companyId);
		
		if(Validator.isNull(modules) || modules.isEmpty())
			return;
		
		Collection<Document> documents = new ArrayList<Document>(modules.size());
		
		for(Module module : modules)
			documents.add(getDocument(module));
		
		SearchEngineUtil.updateDocuments(getSearchEngineId(), companyId, documents);

	}
}
