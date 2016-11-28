package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;


/**
 * Portlet implementation class DeregisterAcademyMailPortlet
 */
public class DeregisterAcademyMailPortlet extends MVCPortlet {
 


	private String viewJSP = null;

	private static Log log = LogFactoryUtil.getLog(DeregisterAcademyMailPortlet.class);
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		
		String jsp = renderRequest.getParameter("view");
		
		log.debug("jsp: " + jsp);
	
		showViewDefault(renderRequest, renderResponse);

	}
	
	private void showViewDefault(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean emailVerified = false;
		
		if(themeDisplay.getUser().getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO)!=null){
			emailVerified = (Boolean)themeDisplay.getUser().getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO);
		}
		
		PortletURL renderURL = renderResponse.createRenderURL();
		renderURL.setParameter("javax.portlet.action", "doView");
		renderURL.setParameter("view", "new-edit-trainer");
		renderRequest.setAttribute("newTrainerURL", renderURL.toString());	
		
		renderRequest.setAttribute("emailVerified", emailVerified);
		
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
	
	
	public void saveDeregister(ActionRequest request,
			ActionResponse response) throws IOException,
			PortletException {
		
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
			User user = themeDisplay.getUser();
			boolean deregisterMail = ParamUtil.getBoolean(request, "deregister", false);
			
			if(user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO)==null){
				user.getExpandoBridge().addAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, ExpandoColumnConstants.BOOLEAN);
			}
			user.getExpandoBridge().setAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, deregisterMail);
			
			UserLocalServiceUtil.updateUser(user);
			log.debug("User updated!");
			SessionMessages.add(request, "saveDeregisterOK");
			
		}catch(Exception e){
			e.printStackTrace();
			SessionErrors.add(request, "saveDeregisterKO");
		}
			
	}
}
