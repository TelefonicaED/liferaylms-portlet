package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;


public abstract class BaseLearningActivityType implements LearningActivityType, Serializable {

	private static Log log = LogFactoryUtil.getLog(BaseLearningActivityType.class);
	
	@Override
	public String getMesageEditDetails() {
		return "edit-activity-details";
	}

	@Override
	public boolean allowsBank() {
		return false;
	}
	@Override
	public boolean allowsDeleteBank(){
		return true;
	}

	@Override
	public boolean isDone(LearningActivity learningActivity, long userId)throws SystemException, PortalException {
		LearningActivityResult lar=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), userId);
		if(lar==null)
		{
			return false;
		}
		else
		{
			return lar.getEndDate()!=null;
		}
	}

	@Override
	public boolean hasEditDetails() {
		return true;
	}

	@Override
	public boolean hasDeleteTries() {
		return false;
	}
	
	@Override
	public String getUrlIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gradebook() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public long getDefaultScore() {		
		return 0;
	}

	@Override
	public long getDefaultTries() {
		return 0;
	}

	@Override
	public String getDefaultFeedbackCorrect() {
		
		return "";
	}

	@Override
	public String getDefaultFeedbackNoCorrect() {
		return "";
	}

	@Override
	public boolean isScoreConfigurable() {
		return false;
	}

	@Override
	public boolean isTriesConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return false;
	}
	
	
	@Override
	public boolean hasMandatoryDates() {
		return false;
	}
	
	@Override
	public String getExpecificContentPage() {
		return null;
	}
	
	@Override
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse,LearningActivity learningActivity) throws PortalException,SystemException,DocumentException,IOException, NumberFormatException, Exception {
		return null;
	}
	
	
	@Override
	public boolean especificValidations(UploadRequest uploadRequest,PortletResponse portletResponse) {
		return true;
	}
	
	@Override
	public void afterInsertOrUpdate(UploadRequest uploadRequest,PortletResponse portletResponse,LearningActivity learningActivity) throws PortalException,SystemException {
	}

	public Object invokeMethod(
			String name, String[] parameterTypes, Object[] arguments)
		throws Throwable {
		return this.invokeMethod(name, parameterTypes, arguments);
	}
	
	@Override
	public void deleteResources(ActionRequest actionRequest,ActionResponse actionResponse,LearningActivity larn) throws PortalException,SystemException,DocumentException,IOException{
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn)
			throws SystemException, PortalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoCorrect() {
		return true;
	}

	@Override
	public String importExtraContent(LearningActivity newLarn, Long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws PortalException, IOException, DocumentException, SystemException{
		return null;
	}
	
	@Override
	public boolean canBeLinked(){
		return false;
	}
	
	@Override
	public String addZipEntry(LearningActivity actividad, Long assetEntryId,PortletDataContext context, Element entryElementLoc)
			throws PortalException, SystemException	{
		
		AssetEntry docAsset;
		try {
			docAsset = AssetEntryLocalServiceUtil.getAssetEntry(assetEntryId);
	
			
			log.info("mimeType: " + docAsset.getMimeType());
			DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
			
			log.info("docFile: " + docfile.getFileEntryId());
			String extension = "";
			String title = docfile.getTitle();
			if(!docfile.getTitle().endsWith(docfile.getExtension()) && docfile.getExtension().equals("")){
				if(docfile.getMimeType().equals("image/jpeg")){
					extension= ".jpg";
				}else if(docfile.getMimeType().equals("image/png")){
					extension= ".png";
				}else if(docfile.getMimeType().equals("video/mpeg")){
					extension= ".mpeg";
				}else if(docfile.getMimeType().equals("application/pdf")){
					extension= ".pdf";
				}else{
					String ext[] = extension.split("/");
					if(ext.length>1){
						extension = ext[1];
					}
				}
			}else if(!docfile.getTitle().endsWith(docfile.getExtension()) && !docfile.getExtension().equals("")){
				extension="."+docfile.getExtension();
			}else if(docfile.getTitle().endsWith(docfile.getExtension())){
				extension="."+docfile.getExtension();
				title = title.substring(0, title.indexOf(docfile.getExtension())-1);
			}
	
			log.info("file Title: " + title);
			title = changeSpecialCharacter(title);
			title += extension;
			log.info("title: " + title);
			
			String pathqu = getEntryPath(context, docfile);
			String pathFile = getFilePath(context, docfile,actividad.getActId());
			Element entryElementfe= entryElementLoc.addElement("dlfileentry");
			entryElementfe.addAttribute("path", pathqu);
			entryElementfe.addAttribute("file", pathFile+title);
			context.addZipEntry(pathqu, docfile);
			
			log.info("pathqu: " + pathqu);
			log.info("pathFile: " + pathFile);
	
			//Guardar el fichero en el zip.
			InputStream input = DLFileEntryLocalServiceUtil.getFileAsStream(docfile.getUserId(), docfile.getFileEntryId(), docfile.getVersion());
	
			context.addZipEntry(getFilePath(context, docfile,actividad.getActId())+title, input);
			
			String txt = (actividad.getTypeId() == 2) ? "external":"internal";
			log.info("    - Resource "+ txt + ": " + title);
	
			return null;
		
		
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	private static String changeSpecialCharacter(String str) {
	    
		str = str.replaceAll("[^a-zA-Z0-9.]", "");
	    return str;
	}
	
	private static String getEntryPath(PortletDataContext context, DLFileEntry file) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/");
		sb.append(file.getFileEntryId());
		sb.append(".xml");
		return sb.toString();
	}
	
	private static String getFilePath(PortletDataContext context,DLFileEntry file, long actId) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/activities/"+String.valueOf(actId)+"/");
		return sb.toString();
	}
	
	public boolean canBeSeenResults(){
		return false;
	}
	
	public long calculateResult(LearningActivity learningActivity, LearningActivityTry lat){
		return lat.getResult();
	}
	
}
