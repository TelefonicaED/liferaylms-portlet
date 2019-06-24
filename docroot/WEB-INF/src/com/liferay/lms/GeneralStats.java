package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.lms.views.CourseStatsView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.CourseOrderByCreationDate;
import com.tls.lms.util.CourseOrderByTitle;

/**
 * Portlet implementation class GeneralStats
 */
public class GeneralStats extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(GeneralStats.class);
	
	
	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		renderRequest.getPreferences().setValue("showSearchTags", renderRequest.getPreferences().getValue("showSearchTagsGeneralStats", "false"));
		renderRequest.getPreferences().setValue("categories", renderRequest.getPreferences().getValue("showSearchCategoriesGeneralStats", "true"));
		long courseId = ParamUtil.getLong(renderRequest, "courseId", 0);
		String search = ParamUtil.getString(renderRequest, "search","");
		String freetext = ParamUtil.getString(renderRequest, "freetext","");
		String tags = ParamUtil.getString(renderRequest, "tags","");
		int state = ParamUtil.getInteger(renderRequest, "state",WorkflowConstants.STATUS_APPROVED);
		long selectedGroupId = ParamUtil.get(renderRequest,"selectedGroupId",-1);
		long catId=ParamUtil.getLong(renderRequest, "categoryId",0);
		
		
		//*****************************************Cogemos los tags************************************//
		String[] tagsSel = null;
		long[] tagsSelIds = null;
		try {
			ServiceContext sc = ServiceContextFactory.getInstance(renderRequest);
			tagsSel = sc.getAssetTagNames();

			if(tagsSel != null){
				long[] groups = new long[]{themeDisplay.getScopeGroupId()};
				tagsSelIds = AssetTagLocalServiceUtil.getTagIds(groups, tagsSel);
			}
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		
		//*****************************************Cogemos las categorias************************************//
		Enumeration<String> pnames =renderRequest.getParameterNames();
		ArrayList<String> tparams = new ArrayList<String>();
		ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
		


		while(pnames.hasMoreElements()){
			String name = pnames.nextElement();
			if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
				tparams.add(name);
				String value = renderRequest.getParameter(name);
				String[] values = value.split(",");
				for(String valuet : values){
					try{
						assetCategoryIds.add(Long.parseLong(valuet));
					}catch(Exception e){
					}
				}
				
			}
		}
		
		//***************************Si estás buscando te guarda los parámetros en la sesión, si no estás buscando te los coge de la sesión****************************//

		PortletSession portletSession = renderRequest.getPortletSession();
		String prefix = "";
		if(courseId > 0){
			prefix = String.valueOf(courseId);
		}
		if(ParamUtil.getString(renderRequest, "search").equals("search")){
			portletSession.setAttribute(prefix+"freetext", freetext);
			portletSession.setAttribute(prefix+"state", state);
			portletSession.setAttribute(prefix+"assetCategoryIds", assetCategoryIds);
			portletSession.setAttribute(prefix+"assetTagIds", tagsSelIds);
			
			

		}else{
			try{
				String freetextTemp = (String)portletSession.getAttribute(prefix+"freetext");
				if(freetextTemp!=null){
					freetext = freetextTemp;
				}
			}catch(Exception e){
				log.debug(e);
			}
			try{
				ArrayList<Long> assetCategoryIdsTemp = (ArrayList<Long>)portletSession.getAttribute(prefix+"assetCategoryIds");
				if(assetCategoryIdsTemp!=null){
					assetCategoryIds = assetCategoryIdsTemp;
				}
			}catch(Exception e){
				log.debug(e);
			}
			try{
				Integer stateTemp = (Integer)portletSession.getAttribute(prefix+"state");
				if(stateTemp!=null){
					state = stateTemp;
				}
			}catch(Exception e){}
			
		}
				
		long[] catIds=ParamUtil.getLongValues(renderRequest, "categoryIds");

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
		
		
		
		PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("javax.portlet.action","doView");
		portletURL.setParameter("freetext",freetext);
		portletURL.setParameter("selectedGroupId", String.valueOf(selectedGroupId));
		portletURL.setParameter("state",String.valueOf(state));

		pnames =renderRequest.getParameterNames();
		while(pnames.hasMoreElements()){
			String name = pnames.nextElement();
			if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
				portletURL.setParameter(name,renderRequest.getParameter(name));
			}
		}
		for(String param : tparams){
			portletURL.setParameter(param,renderRequest.getParameter(param));
		}
		portletURL.setParameter("search","search");
		
		
		if(courseId > 0){
			portletURL.setParameter("courseId", String.valueOf(courseId));
			portletURL.setParameter("view", "editions");
		}
		
		long[] categoryIds = ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()]));
		
		int closed = -1;
		if(state!=WorkflowConstants.STATUS_ANY){
			if(state==WorkflowConstants.STATUS_APPROVED){
				closed = 0;
			}
			else if(state==WorkflowConstants.STATUS_INACTIVE){
				closed = 1;
			}
		}
		
		boolean isAdmin = false;
		try {
			isAdmin = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR).getRoleId())
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.UPDATE)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.DELETE)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.PERMISSIONS)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "PUBLISH")
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "COURSEEDITOR")
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "ASSIGN_MEMBERS");
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		long groupId = themeDisplay.getScopeGroupId();
		log.debug("SELECTED GROUP ID " + selectedGroupId);
		if(selectedGroupId>-1){
			groupId = selectedGroupId;
		}
		
		String emptyResultsMessage = "there-are-no-courses";
		if(courseId > 0){
			emptyResultsMessage = "there-are-no-editions";
		}
		
		SearchContainer<CourseStatsView> searchContainer = new SearchContainer<CourseStatsView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, portletURL, 
				null, emptyResultsMessage);
		
		log.debug("freetext: " + freetext);
		log.debug("closed: " + closed);
		log.debug("categoryIds: " + categoryIds);
		log.debug("tagsSelIds: " + tagsSelIds);
		log.debug("courseId: " + courseId);
		log.debug("themeDisplay.getCompanyId(): " + themeDisplay.getCompanyId());
		log.debug("groupId: " + groupId);
		log.debug("isAdmin: " + isAdmin);
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(categoryIds != null && categoryIds.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categoryIds);
		}
		if(tagsSelIds != null && tagsSelIds.length > 0){
			params.put(CourseParams.PARAM_TAGS, tagsSelIds);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, themeDisplay.getUserId());
		}
		
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		
		if (Validator.isNull(orderByCol) ||
			Validator.isNull(orderByType)){
			orderByCol = "title";
			orderByType = "asc";
		}
		
		OrderByComparator obc = null;
		if(Validator.isNotNull(orderByCol) && orderByCol.equals("title")){
			obc = new CourseOrderByTitle(themeDisplay, orderByType.equals("asc"));
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("createDate")){
			obc = new CourseOrderByCreationDate(orderByType.equals("asc"));
		}
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setOrderByComparator(obc);
		List<Course> courses = CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, courseId, -1, params, 
				searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		List<CourseStatsView> courseStats = new ArrayList<CourseStatsView>();
		CourseStatsView courseStatView=null;
		long[] userExcludedIds;
		for(Course course:courses){
			userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
			courseStatView =  new CourseStatsView(course.getCourseId(),themeDisplay.getLocale(), 0, userExcludedIds, null,true);
			courseStats.add(courseStatView);
		}
		searchContainer.setResults(courseStats);
		searchContainer.setTotal(CourseLocalServiceUtil.countCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, courseId, -1, params));
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("catIds", catIds);
		renderRequest.setAttribute("noAssetCategoryIds", assetCategoryIds == null || assetCategoryIds.size() == 0);
		renderRequest.setAttribute("catId", catId);
		renderRequest.setAttribute("search", search);
		renderRequest.setAttribute("freetext", freetext);
		renderRequest.setAttribute("tags", tags);
		renderRequest.setAttribute("state", state);
		renderRequest.setAttribute("catIdsText", catIdsText);
	
		renderRequest.setAttribute("STATUS_APPROVED", WorkflowConstants.STATUS_APPROVED);
		renderRequest.setAttribute("STATUS_INACTIVE", WorkflowConstants.STATUS_INACTIVE);
		renderRequest.setAttribute("STATUS_ANY", WorkflowConstants.STATUS_ANY);
		ResourceURL exportReportURL = renderResponse.createResourceURL();
		exportReportURL.setResourceID("exportReport");
		renderRequest.setAttribute("exportReportURL", exportReportURL.toString());
		include(this.viewJSP, renderRequest, renderResponse);
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
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String action = resourceRequest.getResourceID();
		
		if(action.equals("exportReport")){
			
			ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String freetext = ParamUtil.getString(resourceRequest, "freetext","");
			int state = ParamUtil.getInteger(resourceRequest, "state",WorkflowConstants.STATUS_APPROVED);
			
			
			//*****************************************Cogemos los tags************************************//
			String[] tagsSel = null;
			long[] tagsSelIds = null;
			try {
				ServiceContext sc = ServiceContextFactory.getInstance(resourceRequest);
				tagsSel = sc.getAssetTagNames();

				if(tagsSel != null){
					long[] groups = new long[]{themeDisplay.getScopeGroupId()};
					tagsSelIds = AssetTagLocalServiceUtil.getTagIds(groups, tagsSel);
				}
			} catch (PortalException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}

			
			//*****************************************Cogemos las categorias************************************//
			Enumeration<String> pnames =resourceRequest.getParameterNames();
			ArrayList<String> tparams = new ArrayList<String>();
			ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
			


			while(pnames.hasMoreElements()){
				String name = pnames.nextElement();
				if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
					tparams.add(name);
					String value = resourceRequest.getParameter(name);
					String[] values = value.split(",");
					for(String valuet : values){
						try{
							assetCategoryIds.add(Long.parseLong(valuet));
						}catch(Exception e){
						}
					}
					
				}
			}
			long[] categoryIds = ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()]));
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			if(categoryIds != null && categoryIds.length > 0){
				params.put(CourseParams.PARAM_AND_CATEGORIES, categoryIds);
			}
			if(tagsSelIds != null && tagsSelIds.length > 0){
				params.put(CourseParams.PARAM_TAGS, tagsSelIds);
			}
			
			List<Course> courses = CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, 0, -1, params, 
					WorkflowConstants.STATUS_ANY, WorkflowConstants.STATUS_ANY, null);
			if(log.isDebugEnabled()){
				log.debug("::generalstats:: action :: " + action);
				log.debug("::generalstats:: courseIds.length :: " + courses.size());
			}
			
			try
			{
			String charset = LanguageUtil.getCharset(themeDisplay.getLocale());
			if (Validator.isNull(charset)) {
				charset = LanguageUtil.getCharset(LocaleUtil.getDefault());
			}
			charset = StringPool.UTF8;
			resourceResponse.setCharacterEncoding(charset);
			resourceResponse.setContentType("text/csv;charset="+charset);
			resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=generalstats."+Long.toString(System.currentTimeMillis())+".csv");
			if (StringPool.UTF8.equals(charset)) {
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        resourceResponse.getPortletOutputStream().write(b);
			}
	        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),charset),';');
	        String[] linea=new String[10];
	        linea[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.name");
	        linea[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.registered");
	        linea[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.start.student");
	        linea[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.student");
	        linea[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.passed");
	        linea[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.failed");
	        linea[6]=LanguageUtil.get(themeDisplay.getLocale(),"closed");
	        linea[7]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.marks.average");
	        linea[8]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulecounter");
	        linea[9]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.activitiescounter");
	        
	        writer.writeNext(linea);
	        long[] userExcludedIds = null;
	        CourseStatsView courseStatView = null;
	        for(Course course:courses){
	        	userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
	        	courseStatView = new CourseStatsView(course.getCourseId(), themeDisplay.getLocale(), 0, userExcludedIds, null, true);
	        	linea=new String[10];
		        linea[0]=courseStatView.getCourseTitle();
		    	linea[1]=Long.toString(courseStatView.getRegistered());
				linea[2]=Long.toString(courseStatView.getStarted());
				linea[3]=Long.toString(courseStatView.getFinished());
		        linea[4]=Long.toString(courseStatView.getPassed());
		        linea[5]=Long.toString(courseStatView.getFailed());
				linea[6]=courseStatView.getClosed();
				linea[7]=courseStatView.getAvgResult();
				linea[8]= Long.toString(courseStatView.getModules());
				linea[9]=Long.toString(courseStatView.getActivities());
		        writer.writeNext(linea);
		        //resourceResponse.getPortletOutputStream().write(b);
	        }
	        writer.flush();
			writer.close();
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
