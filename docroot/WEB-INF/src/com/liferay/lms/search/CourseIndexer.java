package com.liferay.lms.search;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletURL;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class CourseIndexer extends BaseIndexer {

	
	public static final String[] CLASS_NAMES = {Course.class.getName()};

	public static final String PORTLET_ID = "courseadmin_WAR_liferaylmsportlet";

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
	@Override
	protected void doDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Course entry = (Course)obj;
		AssetEntry asset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), entry.getCourseId());
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		User user=UserLocalServiceUtil.getUser(userId);
		String userName = user.getFullName();
		long entryId = entry.getCourseId();
		String title = entry.getTitle();
		String content = HtmlUtil.extractText(entry.getDescription());
		Date displayDate = asset.getPublishDate();

		long[] assetCategoryIds =AssetCategoryLocalServiceUtil.getCategoryIds(Course.class.getName(), entryId);
		String[] assetTagNames =AssetTagLocalServiceUtil.getTagNames(Course.class.getName(), entryId);

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

	@Override
	protected void doReindex(Object obj) throws Exception {
		Course entry = (Course)obj;
		AssetEntry asset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), entry.getCourseId());
		if(asset.isVisible())
		{
			Document document = getDocument(entry);
			SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
		}

	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {

		Course course=CourseLocalServiceUtil.getCourse(classPK);
		doReindex(course);

	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);

	}


	private void reindexEntries(long companyId) {
		//java.util.List<Course> courses=CourseLocalServiceUtil.
		
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		// TODO Auto-generated method stub
		return PORTLET_ID;
	}


	@Override
	public String getPortletId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected Summary doGetSummary(Document arg0, Locale arg1, String arg2,
			PortletURL arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
