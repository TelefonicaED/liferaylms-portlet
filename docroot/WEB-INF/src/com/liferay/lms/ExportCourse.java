package com.liferay.lms;

import java.io.File;
import java.util.List;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;


public class ExportCourse implements MessageListener {

	long groupId;
	String fileName;
	String key;
	ThemeDisplay themeDisplay;
	ServiceContext serviceContext;
		
	public ExportCourse(long groupId, String fileName, String key, ThemeDisplay themeDisplay, ServiceContext serviceContext) {
		super();
		this.groupId = groupId;
		this.fileName = fileName;
		this.key = key;
		this.themeDisplay = themeDisplay;
		this.serviceContext = serviceContext;
	}
	

	public ExportCourse() {
	}
	
	
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try {
			
			this.groupId	= message.getLong("groupId");
			this.fileName = message.getString("fileName");
			this.key = message.getString(key);
			this.serviceContext = (ServiceContext)message.get("serviceContext");
			this.themeDisplay = (ThemeDisplay)message.get("themeDisplay");
		
			Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
			List<User> adminUsers = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
			 
			PrincipalThreadLocal.setName(adminUsers.get(0).getUserId());
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(adminUsers.get(0));
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		
			MultiVMPoolUtil.put("exportCourseCache", key, true);
			try {
				doExportCourse();
			} finally {
				MultiVMPoolUtil.remove("exportCourseCache", key);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doExportCourse() throws Exception {
		File source = LayoutLocalServiceUtil.exportPortletInfoAsFile(themeDisplay.getLayout().getPlid(), groupId, themeDisplay.getPortletDisplay().getId(), serviceContext.getRequest().getParameterMap(), null, null);
		
		File parentDestination = new File(SystemProperties.get("liferay.home")+"/data/lms_exports/courses/"+themeDisplay.getCompanyId()+"/"+groupId);
		
		File destination = new File(parentDestination, fileName);
		
		FileUtil.copyFile(source, destination);
	
	}

}
