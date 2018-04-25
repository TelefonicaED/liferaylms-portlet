package com.liferay.lms.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class AsynchronousProcessDashboard
 */
public class AsynchronousProcessDashboard extends MVCPortlet {
	private String viewJSP = null;

	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-jsp");
	}

	private static Log log = LogFactoryUtil.getLog(AsynchronousProcessDashboard.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try{
			log.debug(":: VIEW ASSYNCRONOUS PROCESSES DASHBOARD "+this.viewJSP);
			String classNameValue = ParamUtil.getString(renderRequest, "className", "");
			
			//Obtenemos todos los classname distintos para efectuar la lista;
			List<String> classnames = AsynchronousProcessAuditLocalServiceUtil.getDistinctTypes(themeDisplay.getCompanyId());
			renderRequest.setAttribute("classnames", classnames);
			
			renderRequest.setAttribute("className", classNameValue);
			//Obtenemos las fechas para la búsqueda
			int startDay= ParamUtil.getInteger(renderRequest, "startDay",-1);
			int startMonth= ParamUtil.getInteger(renderRequest, "startMonth",-1);
			int startYear= ParamUtil.getInteger(renderRequest, "startYear",-1);
			int endDay= ParamUtil.getInteger(renderRequest, "endDay",-1);
			int endMonth= ParamUtil.getInteger(renderRequest, "endMonth",-1);
			int endYear= ParamUtil.getInteger(renderRequest, "endYear",-1);
			
			
			Date startDate=null;
			if(startDay!=-1 && startMonth!=-1 && startYear!=-1){
				Calendar startCalendar= Calendar.getInstance();
				startCalendar.set(Calendar.YEAR, startYear);
				startCalendar.set(Calendar.MONTH, startMonth);
				startCalendar.set(Calendar.DAY_OF_MONTH, startDay);
				startDate = startCalendar.getTime();
			}
				
			Date endDate=null;
			if(endDay!=-1 && endMonth!=-1 && endYear!=-1){
				Calendar endCalDate= Calendar.getInstance();
				endCalDate.set(Calendar.YEAR, endYear);
				endCalDate.set(Calendar.MONTH, endMonth);
				endCalDate.set(Calendar.DAY_OF_MONTH, endDay);
				endDate = endCalDate.getTime();
			}
			renderRequest.setAttribute("startDay",startDay);
			renderRequest.setAttribute("startMonth",startMonth);
			renderRequest.setAttribute("startYear",startYear);
			renderRequest.setAttribute("endDay",endDay);
			renderRequest.setAttribute("endMonth",endMonth);
			renderRequest.setAttribute("endYear",endYear);
			
			
			renderRequest.setAttribute("defaultStartYear", LiferaylmsUtil.defaultStartYear);
			renderRequest.setAttribute("defaultEndYear", LiferaylmsUtil.defaultEndYear);
			
			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("startDay", String.valueOf(startDay));
			iteratorURL.setParameter("startMonth", String.valueOf(startMonth));
			iteratorURL.setParameter("startYear", String.valueOf(startYear));
			iteratorURL.setParameter("defaultStartYear", String.valueOf(LiferaylmsUtil.defaultStartYear));
			iteratorURL.setParameter("defaultEndtYear", String.valueOf(LiferaylmsUtil.defaultEndYear));
			
			iteratorURL.setParameter("endDay", String.valueOf(endDay));
			iteratorURL.setParameter("endMonth", String.valueOf(endMonth));
			iteratorURL.setParameter("endYear", String.valueOf(endYear));

			iteratorURL.setParameter("className", String.valueOf(classNameValue));
			List<AsynchronousProcessAudit> asynchronousProcesses = new ArrayList<AsynchronousProcessAudit>();
			
			SearchContainer<AsynchronousProcessAudit> searchContainer = new SearchContainer<AsynchronousProcessAudit>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					SearchContainer.DEFAULT_DELTA, iteratorURL, 
					null, "no-results");
			asynchronousProcesses = AsynchronousProcessAuditLocalServiceUtil.getByCompanyIdClassNameIdCreateDate(themeDisplay.getCompanyId(), classNameValue, startDate, endDate, searchContainer.getStart(),searchContainer.getEnd());
			int asynchronousProcessesCount = AsynchronousProcessAuditLocalServiceUtil.countByCompanyIdClassNameIdCreateDate(themeDisplay.getCompanyId(), classNameValue, startDate, endDate);
			searchContainer.setIteratorURL(iteratorURL);
			searchContainer.setResults(asynchronousProcesses);
			searchContainer.setTotal(asynchronousProcessesCount);
			
			renderRequest.setAttribute("searchContainer", searchContainer);
			
			
			
			
			
			
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
				
		} catch (Exception e) {
			e.printStackTrace();
		} 

		this.include(this.viewJSP, renderRequest, renderResponse);		
	}
	
	

	
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
		if (portletRequestDispatcher == null) {
			
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
	
	
	

	
	

	
	
			


}
