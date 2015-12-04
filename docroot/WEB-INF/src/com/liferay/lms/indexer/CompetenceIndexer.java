package com.liferay.lms.indexer;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

public class CompetenceIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {Competence.class.getName()};
	public static final String PORTLET_ID = "competencesadmin_WAR_liferaylmsportlet"; 
	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		Competence entry = (Competence)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getCompanyId());

		deleteDocument(entry.getCompanyId(), document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Competence entry = (Competence)obj;

		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		long entryId = entry.getCompetenceId();
		String title = entry.getTitle();

		AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(Competence.class.getName(), entryId);
		String content=entry.getDescription();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
				Competence.class.getName(), entryId);

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				Competence.class.getName(), entryId);

		Document document = new DocumentImpl();
		document.addUID(PORTLET_ID, entryId);

		document.addKeyword(Field.CLASS_PK, entryId);
		document.addKeyword(Field.ENTRY_CLASS_NAME, Competence.class.getName());
		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		
		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addText(Field.DESCRIPTION, assetEntry.getSummary());
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);
		
		return document;
	}

	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletURL portletURL) throws Exception {
		String uid = document.getUID();
		long competenceId = GetterUtil.getLong(uid);
		
		Competence competence = CompetenceLocalServiceUtil.getCompetence(competenceId);
		return new Summary(competence.getTitle(locale), competence.getDescription(locale), portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Competence entry = (Competence)obj;
		try
		{
			AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(Competence.class.getName(), entry.getCompetenceId());
			if (!assetEntry.getVisible()) {
				this.delete(obj);
				return;
			}

			Document document = doGetDocument(entry);
			SearchEngineUtil.updateDocument(
					getSearchEngineId(), entry.getCompanyId(), document);
		}
		catch(com.liferay.portlet.asset.NoSuchEntryException ae)
		{
			return;
		}
		
	}

	@Override
	protected void doReindex(String[] array) throws Exception {

		for(String id:array){
			long companyId = GetterUtil.getLong(id);
			List<Competence> competences = CompetenceLocalServiceUtil.findByCompanyId(companyId);
			for(Competence competence : competences){
				doReindex(competence);
			}
		}

	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Competence entry =  CompetenceLocalServiceUtil.getCompetence(classPK);
		doReindex(entry);
	}

	@Override
	protected String getPortletId(SearchContext arg0) {
		return PORTLET_ID;
	}

}
