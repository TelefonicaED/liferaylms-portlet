package com.liferay.lms.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CatalogCourses
 */
public class CatalogCourses extends MVCPortlet {
 
	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		
		String jsp = renderRequest.getParameter("view");
		
		if(jsp == null || "".equals(jsp)){
			showViewDefault(renderRequest, renderResponse);
		}
	}
	
	private void showViewDefault(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PortletURL renderURL = renderResponse.createRenderURL();
		renderURL.setParameter("javax.portlet.action", "doView");
		renderRequest.setAttribute("renderURL", renderURL.toString());

		boolean showVocabularies = GetterUtil.getBoolean(renderRequest.getPreferences().getValue("showVocabularies", StringPool.FALSE));
		boolean showTags = GetterUtil.getBoolean(renderRequest.getPreferences().getValue("showTags", StringPool.FALSE));
		
		String freeText = ParamUtil.getString(renderRequest, "freetext");
		
		PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("javax.portlet.action","doView");
		portletURL.setParameter("freetext",freeText);
		
		SearchContainer<Course> searchContainer = new SearchContainer<Course>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, portletURL, 
				null, "there-are-no-courses");
		
		long[] categoryAuxIds = null;
		
		/** Si por configuración está el check de mostrar los vocabularios ***/
		
		if(showVocabularies){
			long categoryId = ParamUtil.getLong(renderRequest, "categoryId", 0);
			portletURL.setParameter("categoryId", String.valueOf(categoryId));
			
			String categoryIdsString = ParamUtil.getString(renderRequest, "categoryIds");
			portletURL.setParameter("categoryIds", categoryIdsString);
			long[] categoryIds = StringUtil.split(categoryIdsString, ",",0L);
			
			if(categoryId > 0){
				categoryAuxIds = new long[1 + categoryIds.length];
				for(int i = 0; i < categoryAuxIds.length-1; i++){
					categoryAuxIds[i] = categoryIds[i];
				}
				categoryAuxIds[categoryAuxIds.length-1] = categoryId;
			}else{
				categoryAuxIds = categoryIds;
			}
			
			
			renderRequest.setAttribute("categoryIds", StringUtil.merge(categoryAuxIds,","));
			
			try {
				long[] assetVocabularyIds = StringUtil.split(renderRequest.getPreferences().getValue("assetVocabularyIds", ""),",", 0L);
				
				List<AssetVocabulary> listVocabulary = null;
				
				if(assetVocabularyIds != null && assetVocabularyIds.length > 0){
					listVocabulary = new ArrayList<AssetVocabulary>();
					PermissionChecker permissionChecker = themeDisplay
							.getPermissionChecker();
					for(long vocabularyId: assetVocabularyIds){
						if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), AssetVocabulary.class.getName(), vocabularyId, ActionKeys.VIEW))
							listVocabulary.add(AssetVocabularyLocalServiceUtil.getAssetVocabulary(vocabularyId));
					}
				}else{
					listVocabulary = AssetVocabularyServiceUtil.getGroupVocabularies(themeDisplay.getScopeGroupId());
				}
	
				renderRequest.setAttribute("listVocabulary", listVocabulary);
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<AssetCategory> listCategoriesSelected = new ArrayList<AssetCategory>();
			for(long categoryAuxId: categoryAuxIds){
				try {
					listCategoriesSelected.add(AssetCategoryLocalServiceUtil.getAssetCategory(categoryAuxId));
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			renderRequest.setAttribute("listCategoriesSelected", listCategoriesSelected);
		
		}
		
		long[] tagAuxIds = null;
		
		/** Si por configuración está el check de mostrar los tags ***/
		
		if(showTags){	
			long tagId = ParamUtil.getLong(renderRequest, "tagId", 0);
			portletURL.setParameter("tagId", String.valueOf(tagId));
			
			String tagIdsString = ParamUtil.getString(renderRequest, "tagIds");
			portletURL.setParameter("tagIds", tagIdsString);
			
			long[] tagIds = StringUtil.split(tagIdsString, ",",0L);
			
			if(tagId > 0){
				tagAuxIds = new long[1 + tagIds.length];
				for(int i = 0; i < tagAuxIds.length-1; i++){
					tagAuxIds[i] = tagIds[i];
				}
				tagAuxIds[tagAuxIds.length-1] = tagId;
			}else{
				tagAuxIds = tagIds;
			}
			
			renderRequest.setAttribute("tagIds", StringUtil.merge(tagAuxIds,","));
			
			List<AssetTag> listTagsSelected = new ArrayList<AssetTag>();
			for(long tagAuxId: tagAuxIds){
				try {
					listTagsSelected.add(AssetTagLocalServiceUtil.getAssetTag(tagAuxId));
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			renderRequest.setAttribute("listTagsSelected", listTagsSelected);
		}
		
		List<Course> listCourse = CourseLocalServiceUtil.getCoursesCatalogByTitleCategoriesTags(freeText, categoryAuxIds, tagAuxIds, themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay.getLanguageId(), searchContainer.getStart(), searchContainer.getEnd());
		int total = CourseLocalServiceUtil.countCoursesCatalogByTitleCategoriesTags(freeText, categoryAuxIds, tagAuxIds, themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay.getLanguageId());
		
		searchContainer.setResults(listCourse);
		searchContainer.setTotal(total);
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		include(this.viewJSP, renderRequest, renderResponse);
	}
	
	public void serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		String action = ParamUtil.getString(request, "action");
		
		if(action.equals("getCourses")){
			JSONArray usersJSONArray = JSONFactoryUtil.createJSONArray();
			
			String freeText = ParamUtil.getString(request, "courseTitle");
			
			List<Course> listCourse = CourseLocalServiceUtil.getCoursesCatalogByTitleCategoriesTags(freeText, null, null, themeDisplay.getCompanyId(), 
											themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay.getLanguageId(), -1, -1);
			
			JSONObject userJSON = null;

			for (Course course : listCourse) {
				userJSON = JSONFactoryUtil.createJSONObject();
				userJSON.put("courseTitle", course.getTitle(themeDisplay.getLocale()));
				userJSON.put("courseDescription", course.getDescription(themeDisplay.getLocale()));
				usersJSONArray.put(userJSON);
			}

			PrintWriter out = response.getWriter();
			out.println(usersJSONArray.toString());
		}
	}
	

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			// do nothing
			// _log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
