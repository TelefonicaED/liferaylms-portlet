package com.liferay.lms.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.NoSuchCourseTypeException;
import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.course.inscriptiontype.InscriptionTypeRegistry;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseTypeAdmin
 */
public class CourseTypeAdmin extends MVCPortlet {
 
	private static Log log = LogFactoryUtil.getLog(CourseTypeAdmin.class);
	
	protected String viewJSP;
	protected String editJSP;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
		editJSP = getInitParameter("edit-template");
	}
	
	protected void include(String path, RenderRequest request, RenderResponse response) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(request, response);
		}
	}
	
	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		String jsp = (String) request.getParameter("view");
		if(log.isDebugEnabled())
			log.debug("DOVIEW:: " + jsp);
		if(jsp == null || "".equals(jsp)){
			showViewDefault(request, response);
		} else if (jsp.equals("edit")) {
			showViewEdit(request, response);
		}
	}
	
	private void showViewDefault(RenderRequest request,RenderResponse response) throws IOException, PortletException {
		if(log.isDebugEnabled())
			log.debug(" ::showViewDefault:: ");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		PortletURL editCourseTypeURL = response.createRenderURL();
		editCourseTypeURL.setParameter("view", "edit");
		request.setAttribute("editCourseTypeURL", editCourseTypeURL.toString());
		
		PortletURL deleteCourseTypeURL = response.createActionURL();
		deleteCourseTypeURL.setParameter("javax.portlet.action", "deleteCourseType");
		request.setAttribute("deleteCourseTypeURL", deleteCourseTypeURL.toString());
		
		SearchContainer<CourseType> searchContainer = new SearchContainer<CourseType>(request, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, response.createRenderURL(), null, "no-results");
		try {
			searchContainer.setResults(CourseTypeLocalServiceUtil.getCourseTypes(searchContainer.getStart(), searchContainer.getEnd()));
			searchContainer.setTotal(CourseTypeLocalServiceUtil.getCourseTypesCount());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		request.setAttribute("searchContainer", searchContainer);
		
		include(this.viewJSP, request, response);
	}
	
	private void showViewEdit(RenderRequest request,RenderResponse response) throws IOException, PortletException {
		if(log.isDebugEnabled())
			log.debug(" ::showViewEdit:: ");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		request.setAttribute("renderURL", response.createRenderURL().toString());
		
		PortletURL saveCourseTypeURL = response.createActionURL();
		saveCourseTypeURL.setParameter("javax.portlet.action", "saveCourseType");
		request.setAttribute("saveCourseTypeURL", saveCourseTypeURL.toString());
		
		long courseTypeId = ParamUtil.getLong(request, "courseTypeId", 0);
		CourseType courseType = null;
		if(log.isDebugEnabled())
			log.debug(" ::showViewEdit:: courseTypeId :: " + courseTypeId);
		if(courseTypeId!=0){
			try {
				courseType = CourseTypeLocalServiceUtil.getByCourseTypeId(courseTypeId);
				request.setAttribute("courseType", courseType);
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
		}
		
		//Plantillas de site
		List<LayoutSetPrototype> listTemplates = new ArrayList<LayoutSetPrototype>();
		try {
			String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
			for(String id:lspist){
				listTemplates.add(LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(id)));
			}
				
		} catch (SystemException | NumberFormatException | PortalException e) {
			e.printStackTrace();
		} 
		request.setAttribute("listTemplates", listTemplates);
		
		//Métodos de evaluación
		List<Long> courseEvalIds = new ArrayList<Long>();
		List<CourseEval> listCourseEvals = new ArrayList<CourseEval>();
		CourseEvalRegistry cer=new CourseEvalRegistry();
		try {
			courseEvalIds = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getCourseevals(),",",0L));
			for(long courseEvalId:courseEvalIds)
				listCourseEvals.add(cer.getCourseEval(courseEvalId));
		} catch (SystemException e) {
			e.printStackTrace();
		}
		request.setAttribute("listCourseEvals", listCourseEvals);
		
		//Tipos de actividades
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<LearningActivityType> listLearningActivityTypes = learningActivityTypeRegistry.getLearningActivityTypesForCreating();
		request.setAttribute("listLearningActivityTypes", listLearningActivityTypes);
		
		//Tipos de inscripción
		List<Long> inscriptionTypeIds = new ArrayList<Long>();
		List<InscriptionType> listInscriptionTypes = new ArrayList<InscriptionType>();
		InscriptionTypeRegistry inscription = new InscriptionTypeRegistry();
		try {
			inscriptionTypeIds = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getInscriptionTypes(),",",0L));
			for(long inscriptionTypeId:inscriptionTypeIds)
				listInscriptionTypes.add(inscription.getInscriptionType(inscriptionTypeId));
		} catch (SystemException e) {
			e.printStackTrace();
		}
		request.setAttribute("listInscriptionTypes", listInscriptionTypes);
		
		//Sistema de calificación
		List <Long> calificationTypeIds = new ArrayList<Long>();
		List<CalificationType> listCalificationTypes = new ArrayList<CalificationType>();
		CalificationTypeRegistry calificationType = new CalificationTypeRegistry();
		try {
			calificationTypeIds = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getScoretranslators(),",",0L));
			for(long calificationTypeId:calificationTypeIds)
				listCalificationTypes.add(calificationType.getCalificationType(calificationTypeId));
		} catch (SystemException e) {
			e.printStackTrace();
		}	
		request.setAttribute("listCalificationTypes", listCalificationTypes);
		
		include(this.editJSP, request, response);
	}
	
	//----ACTION
	public void editCourseType(ActionRequest request, ActionResponse response) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		long courseTypeId = ParamUtil.getLong(request, "courseTypeId", 0);
		
		if(log.isDebugEnabled())
			log.debug(" ::editCourseType:: courseTypeId :: " + courseTypeId);
		
		if(courseTypeId != 0){
			//Editando un tipo de curso existente
			CourseType courseType = null;
			try {
				courseType = CourseTypeLocalServiceUtil.getByCourseTypeId(courseTypeId);
				request.setAttribute("courseType", courseType);
			} catch (NoSuchCourseTypeException | SystemException e) {
				e.printStackTrace();
				SessionErrors.add(request, "coursetypeadmin.error.coursetype-not-found");
			}
		} 
	}
	
	public void saveCourseType(ActionRequest request, ActionResponse response) throws IOException, PortletException{
		if(log.isDebugEnabled())
			log.debug(" ::saveCourseType:: ");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		long courseTypeId = ParamUtil.getLong(request, "courseTypeId", 0);
		Map<Locale,String> courseTypeName =  LocalizationUtil.getLocalizationMap(request, "courseTypeName");
		Map<Locale,String> courseTypeDescription =  LocalizationUtil.getLocalizationMap(request, "courseTypeDescription");
		long[] templateIds = ParamUtil.getLongValues(request, "templateIds", new long[] {});
		long[] courseEvalTypeIds = ParamUtil.getLongValues(request, "courseEvalIds", new long[] {});
		long[] learningActivityTypeIds = ParamUtil.getLongValues(request, "learningActivityTypeIds", new long[] {});
		long[] inscriptionTypeIds = ParamUtil.getLongValues(request, "inscriptionTypeIds", new long[] {});
		long[] calificationTypeIds = ParamUtil.getLongValues(request, "calificationTypeIds", new long[] {});
		
		if(log.isDebugEnabled()){
			log.debug(" ::saveCourseType:: courseTypeId :: " + courseTypeId);
			log.debug(" ::saveCourseType:: courseTypeName :: " + courseTypeName);
			log.debug(" ::saveCourseType:: courseTypeDescription :: " + courseTypeDescription);
			log.debug(" ::saveCourseType:: templateIds :: " + templateIds.length);
			log.debug(" ::saveCourseType:: courseEvalTypeIds :: " + courseEvalTypeIds.length);
			log.debug(" ::saveCourseType:: learningActivityTypeIds :: " + learningActivityTypeIds.length);
			log.debug(" ::saveCourseType:: inscriptionTypeIds :: " + inscriptionTypeIds.length);
			log.debug(" ::saveCourseType:: calificationTypeIds :: " + calificationTypeIds.length);
		}
		
		CourseType newCourseType = null;
		
		if(courseTypeId == 0){
			//Añadir nuevo tipo de curso
			try {
				newCourseType = CourseTypeLocalServiceUtil.addCourseType(themeDisplay.getCompanyId(), themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), courseTypeName, courseTypeDescription,
						templateIds, courseEvalTypeIds, learningActivityTypeIds, inscriptionTypeIds, calificationTypeIds);
			} catch (SystemException e) {
				e.printStackTrace();
				newCourseType = null;
			}
				
			if(Validator.isNotNull(newCourseType))
				SessionMessages.add(request, "coursetypeadmin.success.add-new-coursetype");
			else
				SessionErrors.add(request, "coursetypeadmin.error.add-new-coursetype");
		} else {
			try {
				newCourseType = CourseTypeLocalServiceUtil.updateCourseType(courseTypeId, courseTypeName, courseTypeDescription, 
						templateIds, courseEvalTypeIds, learningActivityTypeIds, inscriptionTypeIds, calificationTypeIds);
			} catch (SystemException e) {
				e.printStackTrace();
				newCourseType = null;
			}
			if(Validator.isNotNull(newCourseType))
				SessionMessages.add(request, "coursetypeadmin.success.update-coursetype");
			else
				SessionErrors.add(request, "coursetypeadmin.error.update-coursetype");
		}
	}
	
	public void deleteCourseType(ActionRequest request, ActionResponse response) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		long courseTypeId = ParamUtil.getLong(request, "courseTypeId", 0);
		
		if(log.isDebugEnabled())
			log.debug(" ::deleteCourseType:: courseTypeId :: " + courseTypeId);
		
		CourseType courseType = null;
		try {
			courseType = CourseTypeLocalServiceUtil.deleteCourseType(courseTypeId);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
		
		if(Validator.isNotNull(courseType))
			SessionMessages.add(request,"coursetypeadmin.success.delete");
		else
			SessionErrors.add(request, "coursetypeadmin.error.delete");
		
	}
}
