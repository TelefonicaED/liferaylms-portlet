package com.liferay.lms;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;

import org.apache.commons.fileupload.FileItem;

import com.liferay.lms.model.Module;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

/**
 * Upload implementation class module
 */
public class moduleUpload {

	public static String HIDDEN = "HIDDEN";

	public static String IMAGEGALLERY_REQUESTFOLDER = HIDDEN+LmsConstant.SEPARATOR+"folderIGId";
	public static String DOCUMENTLIBRARY_REQUESTFOLDER = HIDDEN+LmsConstant.SEPARATOR+"folderDLId";

	public static String IMAGEFILE = "IMAGEFILE";
	public static String IMAGEGALLERY_MAINFOLDER = "PortletUploads";
	public static String IMAGEGALLERY_PORTLETFOLDER = "module";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Portlet Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = "";
	public static String IMAGE_DELETE = "DELETEIMAGE";

	public static String DOCUMENTFILE = "DOCUMENTFILE";
	public static String DOCUMENT_DELETE = "DELETEDOCUMENT";

	private Long igFolderId = 0L;
	private Long dlFolderId = 0L;

	private List<FileItem> files = null;
	private HashMap hiddens = null;
	private HashMap deleteds = null;

	public moduleUpload() {
		init();
	}

	private void init(){
		files = new ArrayList<FileItem>();
		hiddens = new HashMap();
		deleteds = new HashMap();
		igFolderId = 0L;
		dlFolderId = 0L;
	}

	public void add(FileItem item) {
		if(files==null) files = new ArrayList<FileItem>();
		files.add(item);
	}

	public void addHidden(String formField, Long value){
		if(hiddens==null) hiddens = new HashMap();
		//Check if Hidden folders
		if (formField.equalsIgnoreCase(IMAGEGALLERY_REQUESTFOLDER)){
			if((value!=null)&& (value!=0L)) igFolderId = value;
		} else if (formField.equalsIgnoreCase(DOCUMENTLIBRARY_REQUESTFOLDER)){
			if((value!=null)&& (value!=0L)) dlFolderId = value;
		} else {
			hiddens.put(formField, value);
		}
	}

	public void addDeleted(String formField) {
		if(deleteds==null) deleteds = new HashMap();
		deleteds.put(formField,new Boolean("true"));
	}

	public Module uploadFiles(ActionRequest request,Module module) throws PortalException, SystemException, IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User defaultuser = themeDisplay.getCompany().getDefaultUser();
		
        Long userId = Long.parseLong(request.getRemoteUser());
    	User user = UserLocalServiceUtil.getUserById(userId);
    	Long groupId = UserLocalServiceUtil.getUser(userId).getGroup().getGroupId();
    	long repositoryId = DLFolderConstants.getDataRepositoryId(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ServiceContext igServiceContext = null;
		ServiceContext dlServiceContext = null;

		for(FileItem item : files) {
			String formField = item.getFieldName();
			String strType = formField.substring(formField.lastIndexOf(LmsConstant.SEPARATOR)+1);
			if(strType.equalsIgnoreCase(IMAGEFILE)){
				formField = getFieldFromAttribute(extractSufix(IMAGEFILE,formField));
				if(deleteds.get(formField)!=null) {
					if(hiddens!=null) {
						Long prevImage = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
						if((prevImage!=null)&&(prevImage!=0L)) {
							DLAppLocalServiceUtil.deleteFileEntry(prevImage);
						}
					}
				} else if(!item.getName().equals("")){
					if(igServiceContext == null){
						igServiceContext = createServiceContext(request,FileEntry.class.getName(),userId,groupId);
					}
					if(igFolderId==0L){
						createIGFolders(request,userId,repositoryId,igServiceContext);
					}
					
					String  contentType= MimeTypesUtil.getContentType(item.getName());
					FileEntry igImage = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, igFolderId, item.getName(), contentType, item.getName(), item.getName(), "", item.getInputStream(),item.getSize(), igServiceContext);
					callSetMethod(formField,module,igImage.getFileEntryId());
					//Check possible previous values
					if(hiddens!=null){
						Long prevImage = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
						if((prevImage!=null) && (prevImage!=0L)){
							//Delete previous image
							DLAppLocalServiceUtil.deleteFileEntry(prevImage);
						}
					}
				} else {
					//See hidden value, possible edit
					if(hiddens!=null){
						Long prevImage = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
						if((prevImage!=null)&&(prevImage!=0L)){
							callSetMethod(formField,module,(Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField));
						}
					}

				}
			}else if(strType.equalsIgnoreCase(DOCUMENTFILE)) {
				formField = getFieldFromAttribute(extractSufix(DOCUMENTFILE,formField));
				if(deleteds.get(formField)!=null){
					Long prevDocument = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
					if((prevDocument!=null)&&(prevDocument!=0L)) {
						DLAppLocalServiceUtil.deleteFileEntry(prevDocument);
					}
				} else if(!item.getName().equals("")){
					if(dlServiceContext == null){
						dlServiceContext = createServiceContext(request,FileEntry.class.getName(),userId,groupId);
					}
					if(dlFolderId==0L) {
						
						createDLFolders(request,userId,repositoryId,request);
					}
					FileEntry dlDocument = DLAppLocalServiceUtil.addFileEntry(userId, groupId, dlFolderId, item.getName(), item.getName(), item.getName(), "", "", item.getInputStream(),item.getSize(),dlServiceContext);
					callSetMethod(formField,module,dlDocument.getFileEntryId());
					//Check possible previous values
					if(hiddens!=null){
						Long prevDocument = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
						if((prevDocument!=null)&&(prevDocument!=0L)){
							//Delete previous document
							DLAppLocalServiceUtil.deleteFileEntry(prevDocument);
						}
					}
				} else {
					//See hidden value, possible edit
					if(hiddens!=null){
						Long prevDocument = (Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField);
						if((prevDocument!=null)&&(prevDocument!=0L)) {
							callSetMethod(formField,module,(Long)hiddens.get(HIDDEN+LmsConstant.SEPARATOR+formField));
						}
					}
				}
			}
		}
		return module;
	}

	public void deleteFiles() throws PortalException, SystemException{
		if(igFolderId!=0L) {
			DLAppLocalServiceUtil.deleteFolder(igFolderId);
		}
		if(dlFolderId!=0L) {
			DLAppLocalServiceUtil.deleteFolder(dlFolderId);
		}
	}

	private void callSetMethod(String formField, Module module, Long value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String strMethod = "set"+StringUtil.upperCaseFirstLetter(formField);
		Method methodSet = module.getClass().getMethod(strMethod,long.class);
		methodSet.invoke(module, value);
	}

	public Long getIgFolderId() {
		return igFolderId;
	}

	public void setIgFolderId(Long igFolderId) {
		this.igFolderId = igFolderId;
	}

	public Long getDlFolderId() {
		return dlFolderId;
	}

	public void setDlFolderId(Long dlFolderId) {
		this.dlFolderId = dlFolderId;
	}

	/**
	 * Create a serviceContext with given arguments
	 * @param request
	 * @param className
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	private ServiceContext createServiceContext(ActionRequest request, String className, Long userId, Long groupId) throws PortalException, SystemException{
		ServiceContext serviceContext = ServiceContextFactory.getInstance(className, request);
        serviceContext.setAddGroupPermissions(true);
        serviceContext.setAddGuestPermissions(true);
        serviceContext.setUserId(userId);
        serviceContext.setScopeGroupId(groupId);
        return serviceContext;
	}

	/**
	 * Create folders for upload images from our portlet to ImageGallery portlet
	 * @param request
	 * @param userId
	 * @param groupId
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void createIGFolders(ActionRequest request,Long userId,Long repositoryId, ServiceContext serviceContext) throws PortalException, SystemException{
		//Variables for folder ids
		Long igMainFolderId = 0L;
		Long igPortletFolderId = 0L;
		Long igRecordFolderId = 0L;
        //Search for folders
        boolean igMainFolderFound = false;
        boolean igPortletFolderFound = false;
        try {
        	//Get the main folder
        	Folder igMainFolder = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,IMAGEGALLERY_MAINFOLDER);
        	igMainFolderId = igMainFolder.getFolderId();
        	igMainFolderFound = true;
        	//Get the portlet folder
        	Folder igPortletFolder = DLAppLocalServiceUtil.getFolder(repositoryId,igMainFolderId,IMAGEGALLERY_PORTLETFOLDER);
        	igPortletFolderId = igPortletFolder.getFolderId();
        	igPortletFolderFound = true;
        } catch (Exception ex) {
        	//Not found main folder
        }
        //Create main folder if not exist
        if(!igMainFolderFound) {
        	Folder newImageMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, IMAGEGALLERY_MAINFOLDER, IMAGEGALLERY_MAINFOLDER_DESCRIPTION, serviceContext);
        	igMainFolderId = newImageMainFolder.getFolderId();
        	igMainFolderFound = true;
        }
        //Create portlet folder if not exist
        if(igMainFolderFound && !igPortletFolderFound){
        	Folder newImagePortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, igMainFolderId, IMAGEGALLERY_PORTLETFOLDER, IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION, serviceContext);
        	igPortletFolderFound = true;
        	igPortletFolderId = newImagePortletFolder.getFolderId();
        }
        //Create this record folder
        if(igPortletFolderFound){
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
        	String igRecordFolderName=dateFormat.format(date)+LmsConstant.SEPARATOR+userId;
        	Folder newImageRecordFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, igPortletFolderId,igRecordFolderName, "", serviceContext);
        	igRecordFolderId = newImageRecordFolder.getFolderId();
        }
        igFolderId = igRecordFolderId;
      }

	/**
	 * Create folders for upload documents from our portlet to DocumentLibrary portlet
	 * @param request
	 * @param userId
	 * @param groupId
	 * @param request2
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void createDLFolders(ActionRequest request,Long userId,Long respositoryId,ActionRequest request2) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		Long dlPortletFolderId = 0L;
		Long dlRecordFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        boolean dlPortletFolderFound = false;
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User defaultuser = themeDisplay.getCompany().getDefaultUser();
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(respositoryId,0,LmsConstant.DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        	Folder dlFolderPortlet = DLAppLocalServiceUtil.getFolder(respositoryId,dlMainFolderId,LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER);
        	dlPortletFolderId = dlFolderPortlet.getFolderId();
        	dlPortletFolderFound = true;
        } catch (Exception ex){
        	//Not found Main Folder
        }
        
        ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), request);
        
        //Create main folder if not exist
        if(!dlMainFolderFound){

        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(defaultuser.getUserId(), respositoryId, 0, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION, serviceContext);
			
        	String[] communityPermissions = new String[]{ActionKeys.VIEW,ActionKeys.ADD_FOLDER,ActionKeys.ADD_DOCUMENT};
        	Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
        	try {
				Role siteMember = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
				roleIdsToActionIds.put(siteMember.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	try {
        		Role organizationUser = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ORGANIZATION_USER);
				roleIdsToActionIds.put(organizationUser.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	ResourcePermissionServiceUtil.setIndividualResourcePermissions(
					newDocumentMainFolder.getGroupId(), themeDisplay.getCompanyId(), DLFolder.class.getName(),
					Long.toString(newDocumentMainFolder.getFolderId()), roleIdsToActionIds);
        	
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        	dlMainFolderFound = true;
        }
        //Create portlet folder if not exist
        if(dlMainFolderFound && !dlPortletFolderFound){
        	
    		
        	Folder newDocumentPortletFolder = DLAppLocalServiceUtil.addFolder(defaultuser.getUserId(), respositoryId, dlMainFolderId , LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER, LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION, serviceContext);
			
        	String[] communityPermissions = new String[]{ActionKeys.VIEW,ActionKeys.ADD_FOLDER,ActionKeys.ADD_DOCUMENT};
        	Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
        	try {
				Role siteMember = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
				roleIdsToActionIds.put(siteMember.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	try {
        		Role organizationUser = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ORGANIZATION_USER);
				roleIdsToActionIds.put(organizationUser.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	ResourcePermissionServiceUtil.setIndividualResourcePermissions(
        			newDocumentPortletFolder.getGroupId(), themeDisplay.getCompanyId(), DLFolder.class.getName(),
					Long.toString(newDocumentPortletFolder.getFolderId()), roleIdsToActionIds);
        	
        	dlPortletFolderFound = true;
            dlPortletFolderId = newDocumentPortletFolder.getFolderId();
        }

        //Create this record folder
        if(dlPortletFolderFound){
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
        	String dlRecordFolderName = dateFormat.format(date)+LmsConstant.SEPARATOR+userId;
        	Folder newDocumentRecordFolder = DLAppLocalServiceUtil.addFolder(userId, respositoryId, dlPortletFolderId, dlRecordFolderName, dlRecordFolderName, serviceContext);
			
        	String[] communityPermissions = new String[]{ActionKeys.VIEW};
        	Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
        	try {
				Role siteMember = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
				roleIdsToActionIds.put(siteMember.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	try {
        		Role organizationUser = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ORGANIZATION_USER);
				roleIdsToActionIds.put(organizationUser.getRoleId(), communityPermissions);
			} catch (PortalException e) {
			}
        	
        	ResourcePermissionServiceUtil.setIndividualResourcePermissions(
        			newDocumentRecordFolder.getGroupId(), themeDisplay.getCompanyId(), DLFolder.class.getName(),
					Long.toString(newDocumentRecordFolder.getFolderId()), roleIdsToActionIds);
        	
        	dlRecordFolderId = newDocumentRecordFolder.getFolderId();
        }
        dlFolderId = dlRecordFolderId;
	}

	/**
	  * Extract a given sufix from a String
	  * This method loof for sufix, and then, substring the rest to the left.
	  * Posible last char = "_" deleted
	  * @param sufix
	  * @param itemName
	  * @return
	  */
	private String extractSufix (String sufix, String itemName){
		String result = itemName;
			if(itemName!=null && sufix!=null){
				int lastPos  = itemName.lastIndexOf(sufix);
				result = itemName.substring(0,lastPos);
				//Delete posible "_" char
				if(result.substring(result.length()-1,result.length()).equals("_")) {
					result = result.substring(0,result.length()-1);
				}
			}
		return result;
	}

	/**
	 * Get the field string from the attribute string
	 * @param attribute
	 * @return
	 */
	private String getFieldFromAttribute(String attribute){
		return attribute.substring(attribute.lastIndexOf(LmsConstant.SEPARATOR)+1);
	}
}
