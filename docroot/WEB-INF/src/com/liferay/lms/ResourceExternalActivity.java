
package com.liferay.lms;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;
import com.tls.util.liferay.patch.PortalClassInvokerPatched;

/**
 * Portlet implementation class ResourceActivity
 */
public class ResourceExternalActivity extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(ResourceExternalActivity.class);
	
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";

	public void selectResource(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "resId", 0);
		long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);

		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		larn.setExtracontent(Long.toString(entryId));
		LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("resId", Long.toString(actId));
	}

	public void addfiles(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);

		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "resId", 0);
		String description = request.getParameter("description");
		String youtubecode=ParamUtil.getString(request,"youtubecode","");
		boolean videoControlEnabled=ParamUtil.getBoolean(request,"videoControl");
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		String extraContent=larn.getExtracontent();
		Document document = SAXReaderUtil.createDocument();
		Element rootElement = document.addElement("multimediaentry");
		if(extraContent!=null &&!"".equals(extraContent)&&!Validator.isNumber(extraContent))
		{
			document=SAXReaderUtil.read(extraContent);
			rootElement =document.getRootElement();
		}

		if(!"".equals(youtubecode))
		{
			Element video=rootElement.element("video");
			if(video!=null)
			{
				video.detach();
				rootElement.remove(video);
			}
			video = SAXReaderUtil.createElement("video");
			video.setText(youtubecode);		
			rootElement.add(video);
		}
		
		Element videoControl=rootElement.element("video-control");
		if(videoControl!=null)
		{
			videoControl.detach();
			rootElement.remove(videoControl);
		}
		
		videoControl = SAXReaderUtil.createElement("video-control");
		videoControl.setText(String.valueOf(videoControlEnabled));		
		rootElement.add(videoControl);
		
		larn.setExtracontent(document.formattedString());
		larn.setDescription( description,themeDisplay.getLocale());
		//LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);

		LearningActivityServiceUtil.modLearningActivity(larn);
		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), themeDisplay.getUserId(), AuditConstants.UPDATE, null);
		
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("actionEditingDetails", "true");	
		actionResponse.setRenderParameter("resId", Long.toString(actId));
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {

		long actId=0;

		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		} else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}

		log.error("actId: " + actId);
		
		if(actId==0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}else{
			try {
				//auditing
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				
				LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				long typeId=activity.getTypeId();

				log.error("typeId: " + typeId);
				
				if(typeId==2){
					Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(activity.getGroupId());
					boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());
					boolean hasAccessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());

					log.error("hasPermissionAccessCourseFinished: " + hasPermissionAccessCourseFinished);
					log.error("hasAccessLock: " + hasAccessLock);
					log.error("activity.getExtracontent(): " + activity.getExtracontent());
					
					if(activity.canAccess(true, themeDisplay.getUser(), themeDisplay.getPermissionChecker(), hasAccessLock, course, hasPermissionAccessCourseFinished) && 
							Validator.isNotNull(activity.getExtracontent())){
						renderRequest.setAttribute("activity", activity);
						renderRequest.setAttribute("hasPermissionAccessCourseFinished", hasPermissionAccessCourseFinished);
						
						try {
							Document document = SAXReaderUtil.read(activity.getExtracontent());

							Element root=document.getRootElement();
							
							//Comprobamos si es necesario ver un porcetanje del video para aprobar
							boolean isDefaultScore = (activity.getPasspuntuation() == 0);
							renderRequest.setAttribute("isDefaultScore", isDefaultScore);
							
							//Si hemos hecho un intento anteriormente y necesitamos ver un porcentaje especifico para aprobar posicionamos el video donde se quedó
							double videoPosition=0;
							int oldScore=0;
							int plays=0;

							if (!isDefaultScore){
								LearningActivityTry lastLearningActivityTry = null;
								
								if(!hasPermissionAccessCourseFinished){
									lastLearningActivityTry =LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId,themeDisplay.getUserId());
								}
								if (lastLearningActivityTry != null){
									//Poner posición del video.
									String xml = lastLearningActivityTry.getTryResultData();
									
									if(!xml.equals("")){

										Document documentTry = SAXReaderUtil.read(xml);
										Element rootElement = documentTry.getRootElement();
										Element positionElement = rootElement.element("position");						
										videoPosition =  Double.parseDouble(positionElement.getText());
										
										Element playsElement = rootElement.element("plays");	
										plays =  Integer.parseInt(playsElement.getText());

										Element scoreElement = rootElement.element("score");	
										oldScore =  Integer.parseInt(scoreElement.getText());			
									}		
								}
							}
							
							renderRequest.setAttribute("videoPosition", videoPosition);
							renderRequest.setAttribute("oldScore", oldScore);
							renderRequest.setAttribute("plays", plays);
							
							//Comprobamos si el usuario ha pasado el curso y lo enviamos para recargar los portlets al terminar
							boolean userPassed = LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId());
							renderRequest.setAttribute("userPassed", userPassed);
							
							//Tratamos el video si tiene
							Element video=root.element("video");
							if(video!=null){
								boolean isYoutubeIframe = false;
								boolean isVimeoIframe = false;
								boolean isDLFileEntry = false;
								
								//Comprobamos si es vimeo o youtube
								if(video.attributeValue("id","").equals("")){
									String videoIframeCode= video.getText();
									isYoutubeIframe = ((videoIframeCode.indexOf("iframe")>-1) &&  (videoIframeCode.indexOf("youtube")>-1));
									isVimeoIframe = ((videoIframeCode.indexOf("iframe")>-1) &&  (videoIframeCode.indexOf("vimeo")>-1));
									
									boolean videoControlDisabled = false;
									Element videoControl=root.element("video-control");
									if(videoControl!=null){
										videoControlDisabled = Boolean.parseBoolean(videoControl.getText());
									}
									String videoCode= video.getText();
									if (isYoutubeIframe){
										int delimitador = videoCode.indexOf("iframe") + "iframe".length();
										String partePrimera = videoCode.substring(0, delimitador);
										String parteSegunda = videoCode.substring(delimitador, videoCode.length());
										String parteTercera = parteSegunda.substring(parteSegunda.indexOf("src=\""));
										
										String[] split = parteTercera.split("\"");
										String src = split[1];
										String parametros = ((src.indexOf("?")> -1) ? "&enablejsapi=1" : "?enablejsapi=1");
										if (videoControlDisabled && !userPassed){
											parametros += "&controls=0";
										}
										if (videoPosition > 0 && oldScore<100){
											DecimalFormat df = new DecimalFormat("#####");
											parametros += "&start="+df.format(videoPosition);
										}
										parteSegunda = parteSegunda.replace(src, src.concat(parametros));
										StringBuilder tag  = new StringBuilder();
										tag.append(partePrimera);
										tag.append(" id=\"youtube-video\"");
										tag.append(parteSegunda);
										videoCode = tag.toString();
									}
									if (isVimeoIframe){
								
										int delimitador = videoCode.indexOf("iframe") + "iframe".length();
										String partePrimera = videoCode.substring(0, delimitador);
										String parteSegunda = videoCode.substring(delimitador, videoCode.length());
										String parteTercera = parteSegunda.substring(parteSegunda.indexOf("src=\""));
										
										String[] split = parteTercera.split("\"");
										String src = split[1];
										String parametros = "?api=1&amp;player_id=player_1";
										if(videoControlDisabled && !userPassed){
											parametros += "&background=1&loop=0&mute=0";
										}
										parteSegunda = parteSegunda.replace(src, src.concat(parametros));
										StringBuilder tag  = new StringBuilder();
										tag.append(partePrimera);
										tag.append(" id=\"player_1\"");
										tag.append(parteSegunda);
										videoCode = tag.toString();
										
										int seekTo = 0;
										if (videoPosition > 0 && oldScore<100){
											DecimalFormat df = new DecimalFormat("#####");
											seekTo = Integer.parseInt(df.format(videoPosition));
										}
										renderRequest.setAttribute("seekTo", seekTo);
									} 
									
									renderRequest.setAttribute("video", videoCode);
									
								}else{
									//Es un fileEntryId
									AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
									DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
									DLFileVersion videofileVersion = videofile.getFileVersion();
									String videoURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + videofileVersion.getGroupId() + StringPool.SLASH + videofileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(videofileVersion.getTitle()));

									isDLFileEntry = true;
									renderRequest.setAttribute("video", videoURL);
								}
								
								renderRequest.setAttribute("isYoutubeIframe", isYoutubeIframe);
								renderRequest.setAttribute("isVimeoIframe", isVimeoIframe);
								renderRequest.setAttribute("isDLFileEntry", isDLFileEntry);
							}
							
							//Creamos el nuevo intento al usuario
							ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);

							LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
							renderRequest.setAttribute("latId", learningTry.getLatId());
							//Si no hace falta nota para aprobar ya lo aprobamos
							if(isDefaultScore){
								learningTry.setEndDate(new Date());
								learningTry.setResult(100);
								LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);	
							}
							
							//Documentos anexos al recurso externo
							
							Element documento=null;
							int i = 0;
							List<FileVersion> listDocuments = new ArrayList<FileVersion>();
							do{
								String documentt = "document";
								if(i>0){
									documentt = documentt+(i-1);
								}
							
								documento=root.element(documentt);
								if(documento!=null){
									if(!documento.attributeValue("id","").equals("")){
										try{	
											AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
											FileEntry fileEntry=DLAppLocalServiceUtil.getFileEntry(docAsset.getClassPK());
											FileVersion fileVersion = fileEntry.getFileVersion();
											
											DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "");
											
											listDocuments.add(fileVersion);
											
										}catch(NoSuchEntryException nsee){
											nsee.printStackTrace();
										}
									}
								}
								i++;
							}while(documento!=null);
							
							renderRequest.setAttribute("listDocuments", listDocuments);
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						super.render(renderRequest, renderResponse);
					}else{
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
				}else{
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}			
		}
	}

	public void invokeTaglibDiscussion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
		
		//Se parchea porque da error al hacer comentarios con la clase por defecto del portal.
		
		PortletConfig portletConfig = getPortletConfig();
		
		PortalClassInvokerPatched.invoke(  // Notar el "Patched"
            true,
            "com.liferay.portlet.messageboards.action.EditDiscussionAction",
            "processAction",
            new String[] {
                    "org.apache.struts.action.ActionMapping",
                    "org.apache.struts.action.ActionForm",
                    PortletConfig.class.getName(), ActionRequest.class.getName(),
                    ActionResponse.class.getName()
            },
            null, null, portletConfig, actionRequest, actionResponse);
	}

}
