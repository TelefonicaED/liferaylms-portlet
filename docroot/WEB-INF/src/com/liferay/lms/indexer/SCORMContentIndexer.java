package com.liferay.lms.indexer;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletURL;

import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class SCORMContentIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {SCORMContent.class.getName()};
	public static final String PORTLET_ID = "scormadmin_WAR_liferaylmsportlet"; 
	@Override
	public String[] getClassNames() {
		// TODO Auto-generated method stub
		return CLASS_NAMES;
	}

	
	public Summary getSummary(Document document, String snippet,
			PortletURL portletURL) {
		// TODO Auto-generated method stub
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
		SCORMContent entry = (SCORMContent)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getScormId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));

	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		SCORMContent entry = (SCORMContent)obj;
		
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = UserLocalServiceUtil.getUser(userId).getFullName();
		long entryId = entry.getScormId();
		String title = entry.getTitle();
		
		AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(SCORMContent.class.getName(), entryId);
		String content=HtmlUtil.extractText(entry.getDescription());
			
		String contentSinAcentos=content.toLowerCase();
		contentSinAcentos=contentSinAcentos.replace('á', 'a');
		contentSinAcentos=contentSinAcentos.replace('é', 'e');
		contentSinAcentos=contentSinAcentos.replace('í', 'i');
		contentSinAcentos=contentSinAcentos.replace('ó', 'o');
		contentSinAcentos=contentSinAcentos.replace('ú', 'u');
		content=content+" "+contentSinAcentos;
		Date displayDate = assetEntry.getPublishDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			SCORMContent.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				SCORMContent.class.getName(), entryId);
		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, SCORMContent.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		String[] assetCategoryTitles = new String[assetCategoryIds.length];
		for (int i=0; i<assetCategoryIds.length;i++){
			AssetCategory categoria = AssetCategoryLocalServiceUtil.getCategory(assetCategoryIds[i]);
			assetCategoryTitles[i]=categoria.getName();
		}

		document.addKeyword("assetCategoryTitles", assetCategoryTitles);
		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);
		
		return document;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		SCORMContent entry = (SCORMContent)obj;
		try
		{
			AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(SCORMContent.class.getName(), entry.getScormId());
			if (!assetEntry.getVisible()) {
				return;
			}

			Document document = getDocument(entry);

			SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
		}
		catch(com.liferay.portlet.asset.NoSuchEntryException ae)
		{
			return;
		}
		
		

	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		SCORMContent entry = SCORMContentLocalServiceUtil.getSCORMContent(classPK);
		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);

	}

	private void reindexEntries(long companyId) throws Exception {
		java.util.List<SCORMContent> entries=SCORMContentLocalServiceUtil.getSCORMContents(companyId);
		if(entries==null ||entries.isEmpty())
		{
			return;
		}
		for(SCORMContent entry:entries)
		{
			doReindex(entry);
		}
	}
	

	@Override
	protected String getPortletId(SearchContext searchContext) {
		// TODO Auto-generated method stub
		return PORTLET_ID;
	}


	@Override
	public String getPortletId() {
		// TODO Auto-generated method stub
		return PORTLET_ID;
	}


	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet,
			PortletURL portletURL) throws Exception {
		// TODO Auto-generated method stub
		return getSummary(document, snippet, portletURL);
	}
	
	@Override
	public boolean hasPermission( PermissionChecker permissionChecker, long entryClassPK, String actionId) throws Exception {
		SCORMContent entry = SCORMContentLocalServiceUtil.getSCORMContent(entryClassPK);
		return permissionChecker.hasPermission(entry.getGroupId(), SCORMContent.class.getName(), entryClassPK, ActionKeys.VIEW);
	}
	
	@Override
	public boolean isPermissionAware() {
		return true;
	}

}
