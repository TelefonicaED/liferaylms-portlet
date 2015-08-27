package com.liferay.lms.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class LearningActivityIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {LearningActivity.class.getName()};

	public static final String PORTLET_ID = "lmsactivitieslist_WAR_liferaylmsportlet";

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		LearningActivity entry = (LearningActivity)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getActId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		LearningActivity entry = (LearningActivity)obj;

		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = PortalUtil.getUserName(userId, entry.getUserName());
		long entryId = entry.getActId();
		String title = entry.getTitle();
		String content = HtmlUtil.extractText(entry.getDescription());
		Date displayDate = entry.getCreateDate();

		long[] assetCategoryIds = new long[0];
			//AssetCategoryLocalServiceUtil.getCategoryIds(				LearningActivity.class.getName(), entryId);
		String[] assetTagNames =new String[0];
			//AssetTagLocalServiceUtil.getTagNames(			BlogsEntry.class.getName(), entryId);

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

		document.addKeyword(Field.ENTRY_CLASS_NAME, LearningActivity.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		LearningActivity entry = (LearningActivity)obj;

		if (!entry.isApproved()) {
			return;
		}

		Document document = getDocument(entry);

		SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		LearningActivity entry = LearningActivityLocalServiceUtil.getLearningActivity(classPK);

		doReindex(entry);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEntries(long companyId) throws Exception {
		
			reindexEntries(companyId, 0, 200000);
	}

	protected void reindexEntries(long companyId, int start, int end)
		throws Exception {

		List<LearningActivity> entries=LearningActivityLocalServiceUtil.getLearningActivities(start, end);

		if (entries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (LearningActivity entry : entries) {
			Document document = getDocument(entry);
			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	//@Override
	public String getPortletId() {
		// TODO Auto-generated method stub
		return PORTLET_ID;
	}


	protected Summary doGetSummary(Document arg0, Locale arg1, String arg2,
			PortletURL arg3) throws Exception {
		return getSummary(arg0, arg2, arg3);
	}
}
