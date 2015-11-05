package com.liferay.lms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;



public class PostLoginAction extends Action{
	
	// Standard desktop browser detection strings
    private static final String iphone = "iphone";
    private static final String android = "android";
    private static final String mobile = "mobile";
    private static final String phone = "phone";
    private static final String ipad = "ipad";

	@Override
	public void run(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ActionException {
		// TODO Auto-generated method stub
		String userAgent = servletRequest.getHeader("User-Agent");
		String platform = "Desktop";
		if(detectMobile(userAgent.toLowerCase())){
			platform = "Smartphone";
		}
		
		
		
		String extraData = servletRequest.getRemoteAddr()+"###"+userAgent+"###"+platform;
		try {
			User u = PortalUtil.getUser(servletRequest);
			AuditingLogFactory.audit(u.getCompanyId(),0, User.class.getName(), 0, u.getUserId(), AuditConstants.ACCESS, extraData);
		} catch (PortalException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch( SystemException e2){
			e2.printStackTrace();
		}

	}
	
	//*****************************
    // For Desktop Browsers
    //*****************************
   
	private boolean detectMobile(String userAgent) {
	    return (userAgent.indexOf(iphone)!=-1 || 
	    		userAgent.indexOf(android)!=-1 ||
	    		userAgent.indexOf(mobile)!=-1 ||
	    		userAgent.indexOf(phone)!=-1||
	    		userAgent.indexOf(ipad)!=-1);
	} 
	
	



}
