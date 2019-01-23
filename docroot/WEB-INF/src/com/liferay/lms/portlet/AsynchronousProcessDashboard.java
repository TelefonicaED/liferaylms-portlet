package com.liferay.lms.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class AsynchronousProcessDashboard
 */
public class AsynchronousProcessDashboard extends MVCPortlet {
	
	private String viewJSP = null;

	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	private static Log log = LogFactoryUtil
			.getLog(AsynchronousProcessDashboard.class);

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
			
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		
		if(log.isDebugEnabled())
			log.debug(" ::showViewDefault:: ");
		
		log.debug(":1: VIEW ASSYNCRONOUS PROCESSES DASHBOARD "
				+ this.viewJSP);
		
		try {
			
			PortletPreferences prefs;
			String portletResource = ParamUtil.getString(renderRequest, "portletResource");	
			if (Validator.isNotNull(portletResource)){
				prefs = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
			} else {
				prefs = renderRequest.getPreferences();
			}
			
			boolean onlyForUserOwner = (prefs.getValue("preferences--onlyForUserOwner--", "false")).compareTo("true") == 0;
			renderRequest.setAttribute("onlyForUserOwner", onlyForUserOwner);
			boolean showExtraContent = (prefs.getValue("preferences--showExtraContent--", "true")).compareTo("true") == 0;
			renderRequest.setAttribute("showExtraContent", showExtraContent);
			String classNamePrefsValues = prefs.getValue("className", StringPool.BLANK);
			String classNameValue = ParamUtil.getString(renderRequest,"className", "");
			
			// Obtenemos todos los classname distintos para efectuar la lista; Si estan por preferencias los editamos
			List<String> classnames = new ArrayList<String>();
			if(!classNamePrefsValues.isEmpty()){
				if(classNamePrefsValues.indexOf("todos")>=0){
					classnames =AsynchronousProcessAuditLocalServiceUtil
							.getDistinctTypes(themeDisplay.getCompanyId());
				}else if(classNamePrefsValues.indexOf(",")>=0){
					   for (String typeUnit : classNamePrefsValues.split(",")) {
						   classnames.add(typeUnit);
					   }
				}
				else{
					classnames.add(classNamePrefsValues);
				}
			}
			else{
				classnames =AsynchronousProcessAuditLocalServiceUtil
				.getDistinctTypes(themeDisplay.getCompanyId());
			}
			
			if(classNameValue.equalsIgnoreCase("")){
				classNameValue = classNamePrefsValues;
			}
			
			long userId =0L;
			if(onlyForUserOwner){
				userId = themeDisplay.getUserId();
			}
			
			//Para buscar por todos lo ponemos a null
			if(classNameValue.equalsIgnoreCase("todos")){
				classNameValue = null;
			}
			
			
			renderRequest.setAttribute("classnames", classnames);
	
			renderRequest.setAttribute("className", classNameValue);
			// Obtenemos las fechas para la búsqueda
			int startDay = ParamUtil.getInteger(renderRequest, "startDay", -1);
			int startMonth = ParamUtil.getInteger(renderRequest, "startMonth",
					-1);
			int startYear = ParamUtil
					.getInteger(renderRequest, "startYear", -1);
			int endDay = ParamUtil.getInteger(renderRequest, "endDay", -1);
			int endMonth = ParamUtil.getInteger(renderRequest, "endMonth", -1);
			int endYear = ParamUtil.getInteger(renderRequest, "endYear", -1);
	
			Date startDate = null;
			if (startDay != -1 && startMonth != -1 && startYear != -1) {
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.set(Calendar.YEAR, startYear);
				startCalendar.set(Calendar.MONTH, startMonth);
				startCalendar.set(Calendar.DAY_OF_MONTH, startDay);
				startDate = startCalendar.getTime();
			}
	
			Date endDate = null;
			if (endDay != -1 && endMonth != -1 && endYear != -1) {
				Calendar endCalDate = Calendar.getInstance();
				endCalDate.set(Calendar.YEAR, endYear);
				endCalDate.set(Calendar.MONTH, endMonth);
				endCalDate.set(Calendar.DAY_OF_MONTH, endDay);
				endDate = endCalDate.getTime();
			}
			renderRequest.setAttribute("startDay", startDay);
			renderRequest.setAttribute("startMonth", startMonth);
			renderRequest.setAttribute("startYear", startYear);
			renderRequest.setAttribute("endDay", endDay);
			renderRequest.setAttribute("endMonth", endMonth);
			renderRequest.setAttribute("endYear", endYear);
	
			renderRequest.setAttribute("defaultStartYear",
					LiferaylmsUtil.defaultStartYear);
			renderRequest.setAttribute("defaultEndYear",
					LiferaylmsUtil.defaultEndYear);
	
			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("startDay", String.valueOf(startDay));
			iteratorURL.setParameter("startMonth", String.valueOf(startMonth));
			iteratorURL.setParameter("startYear", String.valueOf(startYear));
			iteratorURL.setParameter("defaultStartYear",
					String.valueOf(LiferaylmsUtil.defaultStartYear));
			iteratorURL.setParameter("defaultEndtYear",
					String.valueOf(LiferaylmsUtil.defaultEndYear));
	
			iteratorURL.setParameter("endDay", String.valueOf(endDay));
			iteratorURL.setParameter("endMonth", String.valueOf(endMonth));
			iteratorURL.setParameter("endYear", String.valueOf(endYear));
	
			iteratorURL.setParameter("className",
					String.valueOf(classNameValue));
			
//			iteratorURL.setParameter("userId",
//					String.valueOf(userId));
			List<AsynchronousProcessAudit> asynchronousProcesses = new ArrayList<AsynchronousProcessAudit>();
	
			SearchContainer<AsynchronousProcessAudit> searchContainer = new SearchContainer<AsynchronousProcessAudit>(
					renderRequest, null, null,
					SearchContainer.DEFAULT_CUR_PARAM,
					SearchContainer.DEFAULT_DELTA, iteratorURL, null,
					"no-results");
			
			
			asynchronousProcesses = AsynchronousProcessAuditLocalServiceUtil
					.getByCompanyIdClassNameIdCreateDate(
							themeDisplay.getCompanyId(), classNameValue, userId,
							startDate, endDate, searchContainer.getStart(),
							searchContainer.getEnd());
			int asynchronousProcessesCount = AsynchronousProcessAuditLocalServiceUtil
					.countByCompanyIdClassNameIdCreateDate(
							themeDisplay.getCompanyId(), classNameValue, userId,
							startDate, endDate);
			searchContainer.setIteratorURL(iteratorURL);
			searchContainer.setResults(asynchronousProcesses);
			searchContainer.setTotal(asynchronousProcessesCount);
	
			renderRequest.setAttribute("searchContainer", searchContainer);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.include(this.viewJSP, renderRequest, renderResponse);
		
	}
	
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);
		if (portletRequestDispatcher == null) {

		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

}
