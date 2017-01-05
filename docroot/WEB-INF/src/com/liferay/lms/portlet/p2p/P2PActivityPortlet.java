package com.liferay.lms.portlet.p2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.moduleUpload;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.model.impl.P2pActivityImpl;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;



/**
 * Portlet implementation class P2PActivityPortlet
 */
public class P2PActivityPortlet extends MVCPortlet {
	Log log = LogFactoryUtil.getLog(P2PActivityPortlet.class);
	

	@ProcessAction(name = "addP2PActivity")
	public void addP2PActivity(ActionRequest request, ActionResponse response) throws Exception
	{
		try{
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			ServiceContext serviceContext = null;
			try {
				serviceContext = ServiceContextFactory.getInstance(request);
			} catch (PortalException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			} 


			if(log.isDebugEnabled()){
				Enumeration<String> parNam = uploadRequest.getParameterNames();

				while (parNam.hasMoreElements()) {
					String parName = parNam.nextElement();
					log.debug(parName+"::"+uploadRequest.getParameter(parName));
				}
			}
						
			//Obtenemos los campos necesarios.
			User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
			Long groupId = themeDisplay.getScopeGroupId();
			
			String description = uploadRequest.getParameter("description");
			Long actId = Long.parseLong(uploadRequest.getParameter("actId"));
			Long p2pActivityId = Long.parseLong(uploadRequest.getParameter("p2pActivityId"));
			
			String fileName = uploadRequest.getFileName("fileName");
			String title = fileName;
			
			File file = uploadRequest.getFile("fileName");
			String mimeType = uploadRequest.getContentType("fileName");
			
			if(log.isDebugEnabled()){
				log.debug(groupId);
				log.debug(description);
				log.debug(actId);
				log.debug(p2pActivityId);
				log.debug(fileName);
				log.debug(title);
				log.debug(mimeType);
			}
			
			
			//Evitamos que se suban ficheros con las extensiones siguientes.
			if(	file.getName().endsWith(".bat") 
				|| file.getName().endsWith(".com")
				|| file.getName().endsWith(".exe")
			    || file.getName().endsWith(".msi") ){
				
				SessionErrors.add(request, "p2ptaskactivity-error-file-type");
				request.setAttribute("actId", actId);
				return;
			}
			
			//Comprobar que el tamaño del fichero no supere los 300mb
			if(file.length()> 300 * 1024 * 1024){

				SessionErrors.add(request, "p2ptaskactivity-error-file-size");
				request.setAttribute("actId", actId);
				return;
			}
						
			//Controlamos que no se duplica la P2pActivity subida por el usuario.
			//Solo puede subir una P2pActivity por Activity.
			P2pActivity p2pAc = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, user.getUserId());
			
			//Obtener si es obligatorio el fichero en las p2p.
			boolean fileoptional = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "fileoptional"));
			
			if(p2pAc==null){

				//Cuando el fichero es obligatorio y no esta seleccionado.
				if( !fileoptional &&  (fileName == null || fileName.equals("")) ){
					SessionErrors.add(request, "campos-necesarios-vacios");
				}
				else if( description != null && !description.equals("") ){
					
					//Registramos la actividad p2p del usuario.
					P2pActivity p2pActivity = new P2pActivityImpl();
					p2pActivity.setActId(actId);		
					p2pActivity.setDate(new Date(System.currentTimeMillis()));

					p2pActivity.setUserId(user.getUserId());
					p2pActivity.setDescription(description);
					p2pActivity.setP2pActivityId(p2pActivityId);
				
					//Subimos el fichero si existe. Si llegamos aqui y no existe, es que no es obligatorio.
					if( fileName != null && !fileName.equals("") ){
						
						long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
						//Obtenermos el Id de directorio. Creamos el directorio si no existe.
						long folderId = createDLFolders(user.getUserId(), repositoryId, request);
						
						try {
							
							//Subimos el Archivo en la Document Library
							InputStream is = new FileInputStream(file);
							DLFileEntry dlDocument = DLFileEntryLocalServiceUtil.addFileEntry(serviceContext.getUserId(), groupId, groupId,folderId,fileName, mimeType, title, description, "Importation", 0, null, null , is, file.length(), serviceContext);
							
							//Damos permisos al archivo para usuarios de comunidad.
							DLFileEntryLocalServiceUtil.addFileEntryResources(dlDocument, true, false);
							//Asociamos con el fichero subido.
							p2pActivity.setFileEntryId(dlDocument.getFileEntryId());
							
						} catch (FileExtensionException fee) {
							SessionErrors.add(request, "p2ptaskactivity-error-file-type");
							request.setAttribute("actId", actId);
							return;
						
						} catch (FileNameException fee) {
							SessionErrors.add(request, "p2ptaskactivity-error-file-name");
							request.setAttribute("actId", actId);
							return;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
					//A�adir la actividad a bd
					P2pActivityLocalServiceUtil.addP2pActivity(p2pActivity);
					
					//Creamos el LearningActivityTry
					LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
					learningTry.setStartDate(new Date());
					learningTry.setUserId(user.getUserId());
					learningTry.setResult(0);
					LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);
					boolean deregisterMail = false;
					if(user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, false)!=null){
						deregisterMail = (Boolean)user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, false);
					}
					
					if(!deregisterMail){
						//Enviar por email que se ha entregado una tarea p2p.
						P2PActivityPortlet.sendMailP2pDone(user, actId, themeDisplay);
					}
					request.setAttribute("latId", learningTry.getLatId());
					
				}
			}
			
			request.setAttribute("actId", actId);
			response.setRenderParameter("uploadCorrect", "true");
			response.setRenderParameter("jspPage","/html/p2ptaskactivity/view.jsp");
			
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("Error P2pActivityPortlet.addP2PActivity");
				_log.error(e.getMessage());
			}
			e.printStackTrace();
			SessionErrors.add(request, "error-subir-p2p");
		}
	}
	

	//This is for the tablet
	@ProcessAction(name = "setActivity")
	public void setActivity(ActionRequest actionRequest,ActionResponse actionResponse) throws IOException, NestableException {
		final String TEXT_XML= "text";
		final String RICH_TEXT_XML = "richText";
		final String FILE_XML = "file";	
		
		if(log.isDebugEnabled())log.debug("setActivity");
		
		long actId = ParamUtil.getLong(actionRequest, "actId");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		if(log.isDebugEnabled()){
			Enumeration<String> parNam = actionRequest.getParameterNames();

			while (parNam.hasMoreElements()) {
				String parName = parNam.nextElement();
				log.debug(parName+"::"+actionRequest.getParameter(parName));
			}
			
			parNam = uploadRequest.getParameterNames();

			while (parNam.hasMoreElements()) {
				String parName = parNam.nextElement();
				log.debug(parName+"::"+uploadRequest.getParameter(parName));
			}
		}
		
		String text = ParamUtil.getString(uploadRequest, "text");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		Long userId = ParamUtil.getLong(uploadRequest, "userId",0L);
		
		User user = null;
		if(userId>0L){
			user = UserLocalServiceUtil.getUser(userId);
		}else{
			user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
		}

		boolean isSetTextoEnr =  StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"textoenr"));
		boolean isSetFichero =  StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"fichero"));

		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, user.getUserId());

		if((learningActivity.getTries()!=0)&&(learningActivity.getTries()<=LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, user.getUserId()))) {
			SessionErrors.add(actionRequest, "onlineActivity.max-tries");	
		}
		else {

			//ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);

			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

			if(isSetFichero) {
				String fileName = uploadRequest.getFileName("fileName");
				File file = uploadRequest.getFile("fileName");
				String mimeType = uploadRequest.getContentType("fileName");
				if (Validator.isNull(fileName)) {
					SessionErrors.add(actionRequest, "onlineActivity.mandatory.file");
					actionRequest.setAttribute("actId", actId);
					actionResponse.setRenderParameter("text", text);
					return;
				}
				if(	file.getName().endsWith(".bat") 
						|| file.getName().endsWith(".com")
						|| file.getName().endsWith(".exe")
						|| file.getName().endsWith(".msi") ){

					SessionErrors.add(actionRequest, "onlineActivity.not.allowed.file.type");
					actionResponse.setRenderParameter("text", text);
					actionRequest.setAttribute("actId", actId);
					return;
				}

				long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				long folderId = createDLFolders(user.getUserId(), repositoryId, actionRequest);

				//Subimos el Archivo en la Document Library
				ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), actionRequest);
				//Damos permisos al archivo para usuarios de comunidad.
				serviceContext.setAddGroupPermissions(true);
				FileEntry document = DLAppLocalServiceUtil.addFileEntry(
						user.getUserId(), repositoryId , folderId , fileName, mimeType, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContext ) ;

				Element fileXML=SAXReaderUtil.createElement(FILE_XML);
				fileXML.addAttribute("id", Long.toString(document.getFileEntryId()));
				resultadosXML.add(fileXML);
			}

			if(isSetTextoEnr){
				Element richTextXML=SAXReaderUtil.createElement(RICH_TEXT_XML);
				richTextXML.setText(text);
				resultadosXML.add(richTextXML);				
			}
			else {
				Element textXML=SAXReaderUtil.createElement(TEXT_XML);
				textXML.setText(text);
				resultadosXML.add(textXML);				
			}

			ServiceContext sc = ServiceContextFactory.getInstance(actionRequest);
			sc.setUserId(user.getUserId());
			LearningActivityTry learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,sc);
			learningActivityTry.setTryResultData(resultadosXMLDoc.formattedString());	
			//learningActivityTry.setEndDate(new Date());
			//learningActivityTry.setResult(0);
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
			SessionMessages.add(actionRequest, "onlinetaskactivity.updating");
		}

	}
		
	@ProcessAction(name = "saveCorrection")
	public void saveCorrection(ActionRequest request, ActionResponse response) throws Exception {
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		PortletConfig portletConfig = this.getPortletConfig();
				
		//Obtenemos los campos necesarios.
		User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
		long resultuser=0;
		
		String description = uploadRequest.getParameter("description");
		Long actId = Long.parseLong(uploadRequest.getParameter("actId"));
		Long latId = Long.parseLong(uploadRequest.getParameter("latId"));
		Long p2pActivityId = Long.parseLong(uploadRequest.getParameter("p2pActivityId"));
		
		String result = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"result");
							
		if(result.equals("true")){
			resultuser =  Long.parseLong(uploadRequest.getParameter("resultuser"));
		}
		 		
		String fileName = uploadRequest.getFileName("fileName");
		File file = uploadRequest.getFile("fileName");
		String mimeType = uploadRequest.getContentType("fileName");
		
		if(	file.getName().endsWith(".bat") 
				|| file.getName().endsWith(".com")
				|| file.getName().endsWith(".exe")
			    || file.getName().endsWith(".msi") ){
				
				SessionErrors.add(request, "p2ptaskactivity-error-file-type");
				return;
			}
		
		//Comprobar que el tamaño del fichero no supere los 300mb
		if(file.length()> 300 * 1024 * 1024){
			SessionErrors.add(request, "p2ptaskactivity-error-file-size");
			request.setAttribute("actId", actId);
			return;
		}
		
		//Ya debe existir una correcion en la bd para este usuario y para esta P2pActivity.
		P2pActivityCorrections p2pActCor = P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityIdAndUserId(p2pActivityId, user.getUserId());
		
		if(p2pActCor!=null){
			
			if(description!=null && !description.equals("")){
				
				try{
					long fileId = new Long(0);
					
					//Si ha subido un archivo se guarda.
					if(fileName!=null && !fileName.equals("")){
						//Obtenermos el Id de directorio. Creamos el directorio si no existe.
						long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
						long folderId = createDLFolders(user.getUserId(), repositoryId, request);
						
						//Subimos el Archivo en la Document Library
						ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), request);
						//Damos permisos al archivo para usuarios de comunidad.
						serviceContext.setAddGroupPermissions(true);
						
						FileEntry document;
						try {
							
							document = DLAppLocalServiceUtil.addFileEntry(user.getUserId(), repositoryId , folderId , fileName, mimeType, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContext );
							
							fileId = document.getFileEntryId();
							
						} catch (FileExtensionException fee) {
							SessionErrors.add(request, "p2ptaskactivity-error-file-type");
							request.setAttribute("actId", actId);
							return;
						} catch (FileNameException fee) {
							SessionErrors.add(request, "p2ptaskactivity-error-file-name");
							request.setAttribute("actId", actId);
							return;
						}catch (Exception e) {
							e.printStackTrace();
							SessionErrors.add(request, "p2ptaskactivity-error-file");
							request.setAttribute("actId", actId);
							return;
						}
						
						
					}
					
					//Usamos la que tenemos
					p2pActCor.setActId(actId);
					p2pActCor.setDescription(description);
					p2pActCor.setP2pActivityId(p2pActivityId);
					p2pActCor.setDate(new Date(System.currentTimeMillis()));
					p2pActCor.setFileEntryId(fileId);
					p2pActCor.setUserId(user.getUserId());
					p2pActCor.setResult(resultuser);
					P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections(p2pActCor);
										
					String valoracion = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
					int val = 0;
					try {
						val = Integer.valueOf(valoracion);
					} catch (Exception e) { }

					
					//Actualizar los resultados de las correcciones de las P2P. Antiguo updateStateActivity: updateStateActivity(latId, p2pActivityId);
					updateResultP2PActivity(p2pActivityId, user.getUserId());

					//Obtenemso la url del reto de la actividad.
					Group grupo=themeDisplay.getScopeGroup();
					long retoplid=themeDisplay.getPlid();
					for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false))
					{
						if(theLayout.getFriendlyURL().equals("/reto"))
						{
							retoplid=theLayout.getPlid();
						}
					}
					
					LearningActivity  act = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					Module myModule = ModuleLocalServiceUtil.getModule(act.getModuleId());
					
					PortletURL portletURL = PortletURLFactoryUtil.create(
					uploadRequest, "p2ptaskactivity_WAR_liferaylmsportlet", retoplid,
					PortletRequest.RENDER_PHASE);
					portletURL.setParameter("moduleId", Long.toString(myModule.getModuleId()));
					portletURL.setParameter("actId", Long.toString(act.getActId()));
					portletURL.setParameter("showRevision", "1");
					String url = portletURL.toString();
					
					
					P2pActivity p2pActivity = P2pActivityLocalServiceUtil.getP2pActivity(p2pActivityId);
					User userPropietaryP2pAct = UserLocalServiceUtil.getUser(p2pActivity.getUserId());
					boolean deregisterMail = false;
					if(user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, false)!=null){
						deregisterMail = (Boolean)userPropietaryP2pAct.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO, false);
					}
					
					if(!deregisterMail){
						sendMailCorrection(userPropietaryP2pAct, actId, p2pActCor, themeDisplay, portletConfig, url);
					}
					request.setAttribute("actId", actId);
					request.setAttribute("latId", latId);
		
					//Para mostrar el mensaje de que ya ha corregido todas las actividades
					List<P2pActivityCorrections> corrections = P2pActivityCorrectionsLocalServiceUtil.getCorrectionsDoneByUserInP2PActivity(actId, user.getUserId());
								
					if(corrections.size() >= val){
						response.setRenderParameter("correctionsCompleted", "true");
					}else{
						response.setRenderParameter("correctionsCompleted", "false");
					}
					
					response.setRenderParameter("correctionSaved", "true");
					response.setRenderParameter("jspPage","/html/p2ptaskactivity/view.jsp");
					
				}
				catch(Exception e){
					if (_log.isErrorEnabled()) {
						_log.error("Error getting P2pActivityPortlet.saveCorrection");
						_log.error(e);
					}
					SessionErrors.add(request, "error-p2ptask-correction");
				}
			}else{
				SessionErrors.add(request, "p2ptask-no-empty-answer");
			}
			
		}
	}
	
	private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		Long dlPortletFolderId = 0L;
		Long dlRecordFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        boolean dlPortletFolderFound = false;
        
        Folder dlFolderMain = null;
        //Get main folder
        try {
        	//Get main folder
        	dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,moduleUpload.DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        	Folder dlFolderPortlet = DLAppLocalServiceUtil.getFolder(repositoryId,dlMainFolderId,moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER);
        	dlPortletFolderId = dlFolderPortlet.getFolderId();
        	dlPortletFolderFound = true;
        } catch (Exception ex){
        	//Not found Main Folder
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound || dlFolderMain==null){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, moduleUpload.DOCUMENTLIBRARY_MAINFOLDER, moduleUpload.DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION, serviceContext);
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        	dlMainFolderFound = true;
        }
        //Create portlet folder if not exist
        if(dlMainFolderFound && !dlPortletFolderFound){
        	Folder newDocumentPortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlMainFolderId , moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER, moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION, serviceContext);
        	dlPortletFolderFound = true;
            dlPortletFolderId = newDocumentPortletFolder.getFolderId();
        }

        //Create this record folder
        if(dlPortletFolderFound){
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
        	String dlRecordFolderName = dateFormat.format(date)+moduleUpload.SEPARATOR+userId;
        	Folder newDocumentRecordFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlPortletFolderId, dlRecordFolderName, dlRecordFolderName, serviceContext);
        	dlRecordFolderId = newDocumentRecordFolder.getFolderId();
        }
        return dlRecordFolderId;
	}
	
	public void numValidaciones(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long numValidaciones = ParamUtil.getLong(actionRequest, "numValidaciones", 3);
		String anonimous = ParamUtil.getString(actionRequest, "anonimous", "false");
		String result = ParamUtil.getString(actionRequest, "result", "false");
		
		LearningActivityLocalServiceUtil.setExtraContentValue(actId, "validaciones", String.valueOf(numValidaciones));
		LearningActivityLocalServiceUtil.setExtraContentValue(actId, "anonimous", anonimous);
		LearningActivityLocalServiceUtil.setExtraContentValue(actId, "result", result);
	
		_log.debug(":::numValidaciones:::");
		SessionMessages.add(actionRequest, "activity-saved-successfully");
	}

	//Antiguo updateStateActivity(Long latId, Long p2pActivityId
	public static void updateResultP2PActivity(Long p2pActivityId, Long userId)throws Exception {
		//El valor que añadiremos al LearningActivityResult
		long newValueResult = 0;
		boolean correctionCompleted = false;
		
		//La p2p que se corrige.
		P2pActivity p2pActivityCorrected = P2pActivityLocalServiceUtil.getP2pActivity(p2pActivityId);
		
		//La p2p del que realiza la corrección.
		P2pActivity p2pActivityFromCorrectorUser = P2pActivityLocalServiceUtil.findByActIdAndUserId(p2pActivityCorrected.getActId(), userId);
		
		//La actividad será la misma para las dos actividades.
		long actId = p2pActivityCorrected.getActId();
		
		//Obtener los valores del extracontent.
		String result = LearningActivityLocalServiceUtil.getExtraContentValue(actId, "result");
		
		//Log para ver la traza de ejecución.
		if(_log.isDebugEnabled()){
			_log.debug("----------------------");
			_log.debug(" PARA EL USUARIO QUE CORRIGE");
			_log.debug(" p2pActivityId: "+p2pActivityFromCorrectorUser.getP2pActivityId()+", actId: "+p2pActivityFromCorrectorUser.getActId()+", userId: "+p2pActivityFromCorrectorUser.getUserId());
			_log.debug(" correctionCompleted: "+P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, p2pActivityFromCorrectorUser.getUserId())+", avg: "+P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(p2pActivityFromCorrectorUser.getP2pActivityId()));
			_log.debug(" PARA EL USUARIO QUE ES CORREGIDO");
			_log.debug(" p2pActivityId: "+p2pActivityCorrected.getP2pActivityId()+", actId: "+p2pActivityCorrected.getActId()+", userId: "+p2pActivityCorrected.getUserId());
			_log.debug(" correctionCompleted: "+P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, p2pActivityCorrected.getUserId())+", avg: "+P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(p2pActivityCorrected.getP2pActivityId()));
			_log.debug("----------------------");
		}
		
		//Si en las p2p tenemos nota en la correccion.
		if (result.equals("true")) {

			//PARA EL USUARIO QUE CORRIGE
			correctionCompleted = P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, p2pActivityFromCorrectorUser.getUserId());
			
			//Si ya ha corregido todas la tareas que debe correguir, le ponemos el 50% mas la media que ha recibido de sus correctores.
			if(correctionCompleted){
				long avg = P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(p2pActivityFromCorrectorUser.getP2pActivityId());
				newValueResult = 50 + (avg / 2);
			}
			//Si no ha corregido todas, su nota sera 0.
			else{
				newValueResult = 0;
			}
			//Guardamos el resultado
			saveLearningActivityResult(actId, p2pActivityFromCorrectorUser.getUserId(), newValueResult);
			
			//PARA EL USUARIO QUE ES CORREGIDO
			correctionCompleted = P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, p2pActivityCorrected.getUserId());
			
			//Si ya ha corregido todas la tareas que debe correguir, le ponemos el 50% mas la media que ha recibido de sus correctores.
			if(correctionCompleted){
				long avg = P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(p2pActivityCorrected.getP2pActivityId());
				newValueResult = 50 + (avg / 2);
			}
			//Si no ha corregido todas, su nota sera 0.
			else{
				newValueResult = 0;
			}
			//Guardamos el resultado
			saveLearningActivityResult(actId, p2pActivityCorrected.getUserId(), newValueResult);
			
		} 
		//Si en las p2p NO tenemos nota en la correccion.
		else {
			// Sólo se aprueba la actividad (sin nota) si estan todas las valoraciones asignadas corregidas. 
			correctionCompleted = P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(actId, p2pActivityFromCorrectorUser.getUserId());
			
			//Si las que ha corregido son las que tiene que corregir.
			if(correctionCompleted){
				newValueResult = 100;
			}
			//Si no ha corregido todas, tiene valor 0.
			else{
				newValueResult = 0;
			}
			//Guardamos el resultado
			saveLearningActivityResult(actId, p2pActivityFromCorrectorUser.getUserId(), newValueResult);
		}

	}
	
	private static void saveLearningActivityResult(long actId, long userId, long value) throws SystemException, PortalException{
		
		try {
			

			//Actualizamos el try, que a su vez actualiza el result
			LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
			
			if(learningActivityTry != null){
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(value);
				LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
			}

			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			//Actualizamos el result, ya que puede bajar la nota, y en el Impl no se baja la nota nunca.
			LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			
			learningActivityResult.setResult(value);
			learningActivityResult.setPassed(value >= learningActivity.getPasspuntuation());
			learningActivityResult.setEndDate(new java.util.Date(System.currentTimeMillis()));
			
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
			
			if(learningActivityResult.getPassed()){
				ModuleResultLocalServiceUtil.update(learningActivityResult);
			}
			
			
			//Generar consultas update
			//ModuleUpdateResult.saveStringToFile("updateResultP2PActivities.txt", "update ");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LiferaylmsUtil.saveStringToFile("updateResultP2PActivitiesERROR.txt", "ERROR: actId: "+actId+", userId: "+userId+", value: "+value+", Message:"+e.getMessage());
		}
	}
	
	private static void sendMailCorrection(User user, long actId, 
			P2pActivityCorrections p2pActiCor, ThemeDisplay themeDisplay,PortletConfig portletConfig, String url){
		try
		{
			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			boolean anonimous=false;
			String anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"anonimous");
			boolean result = false;
			String resultString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"result");
			
			if(resultString.equals("true")){
				result =  true;
			}
			if(anonimousString.equals("true")){
				anonimous =  true;
			}
			
			Group group = GroupLocalServiceUtil.getGroup(activity.getGroupId());
			
			Course course= CourseLocalServiceUtil.getCourseByGroupCreatedId(activity.getGroupId());
			
			Module module = ModuleLocalServiceUtil.getModule(activity.getModuleId());
			String courseFriendlyUrl = "";
			String courseTitle = "";
			String activityTitle = activity.getTitle(user.getLocale());
			String moduleTitle =  module.getTitle(user.getLocale());
			String portalUrl = PortalUtil.getPortalURL(themeDisplay);
			String pathPublic = PortalUtil.getPathFriendlyURLPublic();
			
			if(course != null){
				courseFriendlyUrl = portalUrl + pathPublic + group.getFriendlyURL();
				courseTitle = course.getTitle(user.getLocale());
			}
							
			String messageArgs[]= {activityTitle, moduleTitle, courseTitle, courseFriendlyUrl};
			String resultArgs[]= {String.valueOf(p2pActiCor.getResult())};
			String titleArgs[]= {String.valueOf(user.getFullName())};
			User u = UserLocalServiceUtil.getUserById(p2pActiCor.getUserId());
			String userArgs[]= {String.valueOf(u.getFullName())};
			
			//Nuevos campos del email
			//Subject
			String subject = LanguageUtil.get(user.getLocale(), "p2ptaskactivity.mail.valoration.recieved.subject"); 
			
			//Body
			String title  			 = LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.valoration.recieved.body.title",   titleArgs); 
			String message  		 = LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.valoration.recieved.body.message", messageArgs);
			String usercorrection    = LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.valoration.recieved.body.usercorrection", userArgs); 
			String resultcorrection  = LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.valoration.recieved.body.result",  resultArgs); 			
			String end  			 = LanguageUtil.get(user.getLocale(), 	"p2ptaskactivity.mail.valoration.recieved.body.end"); 
			
			//Componer el body seg�n la actividad.
			String body = title;
			
			if(message!=null){
				body += "<br /><br />" + message;	
			}
			
			if(!anonimous){
				if(usercorrection!=null){
					body += "<br /><br />" + usercorrection;
				}
			}
			
			//Comentarios realizados por el usuario que ha corregido la actividad.
			if(p2pActiCor!= null && p2pActiCor.getDescription()!=null){
				body += "<br /><br />" + p2pActiCor.getDescription();	
			}
			
			if(result){
				if(resultcorrection!=null){
					body += "<br /><br />" + resultcorrection;	
				}
			}
			
			String fileId = String.valueOf(p2pActiCor.getFileEntryId());
			if(fileId.length() == 1 && fileId.equals("0")){
				body += "<br /><br />" + LanguageUtil.get(user.getLocale(), 	"p2ptaskactivity.mail.valoration.recieved.body.file.no"); 
			} else {
				body += "<br /><br />" + LanguageUtil.get(user.getLocale(), 	"p2ptaskactivity.mail.valoration.recieved.body.file.yes");
			}
			
			body += "<br /><br />" + end;	
			
			
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::subject:"+subject);}
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::body:"+body);}
			
			//String fromUser=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			String fromName=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_NAME,"");
			String fromAddress=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS,"");
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			InternetAddress to = new InternetAddress(user.getEmailAddress(), user.getFullName());
			
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
			
			MailServiceUtil.sendEmail(mailMessage);			
			//MailEngine.send(from, new InternetAddress[]{to}, new InternetAddress[]{}, subject, body, true);
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::Mail Enviado");}
		}
		/*catch(MailServerException ex) {
			if(_log.isErrorEnabled()){_log.error(ex);}

		}*/ catch (Exception e) {
			if(_log.isErrorEnabled()){_log.error(e);}
		}
	}
	
	/*
	 * Email para el caso 1, avisando que ha entregado su tarea.
	 * Asunto: 1. Confirmacion de entrega de tarea p2p
	 * 
	 * */
	private static void sendMailP2pDone(User user, long actId, ThemeDisplay themeDisplay){
		try
		{
			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			Group group = GroupLocalServiceUtil.getGroup(activity.getGroupId());
			
			Course course= CourseLocalServiceUtil.getCourseByGroupCreatedId(activity.getGroupId());
			
			Module module = ModuleLocalServiceUtil.getModule(activity.getModuleId());
			String courseFriendlyUrl = "";
			String courseTitle = "";
			String activityTitle = activity.getTitle(user.getLocale());
			String moduleTitle =  module.getTitle(user.getLocale());
			String portalUrl = PortalUtil.getPortalURL(themeDisplay);
			String pathPublic = PortalUtil.getPathFriendlyURLPublic();
			
			if(course != null){
				courseFriendlyUrl = portalUrl + pathPublic + group.getFriendlyURL();
				courseTitle = course.getTitle(user.getLocale());
			}
			
			String messageArgs[]= {activityTitle, moduleTitle, courseTitle, courseFriendlyUrl};
			String titleArgs[]= {String.valueOf(user.getFullName())};
			
			//Nuevos campos del email
			//Subject
			
			String subject = LanguageUtil.get(user.getLocale(), "p2ptaskactivity.mail.sendactivity.mail.subject"); 
			String title = LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.sendactivity.mail.title", titleArgs);
			String body = title +"<br /><br />"+ LanguageUtil.format(user.getLocale(), "p2ptaskactivity.mail.sendactivity.mail.message", messageArgs);
			
			String firmaPortal  = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),"firma.email.admin");
			// JOD
			firmaPortal = (firmaPortal!=null?firmaPortal:"");

			
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::subject:"+subject);}
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::body:"+body);}
			
			//String fromUser=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			String fromName=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_NAME,"");
			String fromAddress=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS,"");
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			InternetAddress to = new InternetAddress(user.getEmailAddress(), user.getFullName());
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
			
			MailServiceUtil.sendEmail(mailMessage);		
			//MailEngine.send(from, new InternetAddress[]{to}, new InternetAddress[]{}, subject, body, true);
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::Mail Enviado");}
		}
		/*catch(MailEngineException ex) {
			if(_log.isErrorEnabled()){_log.error(ex);}

		}*/ catch (Exception e) {
			if(_log.isErrorEnabled()){_log.error(e);}
		}
	}
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {
		
		long actId=ParamUtil.getLong(renderRequest, "actId", 0);
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);

		}
		renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		
		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
			LearningActivity activity;
			try {
				activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				
				//auditing
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivity.class.getName(), 
						activity.getActId(), themeDisplay.getUserId(), AuditConstants.GET, null);
				
				long typeId=activity.getTypeId();
				
				if(typeId==3)
				{
					super.render(renderRequest, renderResponse);
				}
				else
				{
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException e) {
				// TODO Auto-generated catch block
			} catch (SystemException e) {
				// TODO Auto-generated catch block
			}			
		}
	}
	
	private static Log _log = LogFactoryUtil.getLog(P2PActivityPortlet.class);
}
