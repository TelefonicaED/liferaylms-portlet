package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.AnnouncementsFlagConstants;
import com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

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
