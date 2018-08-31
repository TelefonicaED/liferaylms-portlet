package com.liferay.lms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;

import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.portlet.p2p.P2PActivityPortlet;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class PortalAdmin
 */
public class PortalAdmin extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(PortalAdmin.class);
 
	
	@ProcessAction(name = "asignP2pActivity")
	public void asignP2pActivity (ActionRequest request, ActionResponse response) throws Exception {
		String ip = com.liferay.portal.util.PortalUtil.getHttpServletRequest(request).getRemoteAddr();
		LiferaylmsUtil.saveStringToFile("asignP2pActivity.txt", "Asignar P2pActivity desde "+ip);
		
		P2PAssignations asignations = new P2PAssignations();
		asignations.asignCorrectionsToP2PActivities();
	}
	public void resetAuditImplamentations (ActionRequest request, ActionResponse response) throws Exception {
		try{
			AuditingLogFactory.resetAuditLogs();
			SessionMessages.add(request, "portaladmin.success-reset-audit");
		}catch(Exception e){
			SessionErrors.add(request, "portaladmin.error-reset-audit");
			e.printStackTrace();
		}
	}
	
	public void updateResultP2PActivities (ActionRequest request, ActionResponse response) throws Exception {
		
		int conta = 0;

		String ip = com.liferay.portal.util.PortalUtil.getHttpServletRequest(request).getRemoteAddr();
		String traza = "P2pActivity actualizados desde "+ip+":\n";
		
		List<P2pActivity> p2pActivityList = P2pActivityLocalServiceUtil.getP2pActivities(0, P2pActivityLocalServiceUtil.getP2pActivitiesCount());
		
		Calendar start = Calendar.getInstance();
		log.info(" ## START ## "+start.getTime()+" tamano "+p2pActivityList.size());
		
		for(P2pActivity p2pActivity: p2pActivityList){
			conta++;
			
			log.info(" :: P2P Update (numero: "+conta+"):: getUserId: "+p2pActivity.getUserId()+", P2pActivityId: "+p2pActivity.getP2pActivityId());
			traza += p2pActivity.getP2pActivityId()+", ";

			P2PActivityPortlet.updateResultP2PActivity(p2pActivity.getP2pActivityId(), p2pActivity.getUserId());
			
		}
		Calendar end = Calendar.getInstance();
		log.info("------------------------------------------------");
		log.info(" ## START ## "+start.getTime());
		log.info(" ##  END  ## "+end.getTime());
		log.info(" ##  UPDATED  ## "+conta);
		log.info("------------------------------------------------");
		
		try {
			LiferaylmsUtil.saveStringToFile("updateResultP2PActivities.txt", traza+"\nUPDATED: "+conta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateExtraContentMultimediaActivities (ActionRequest request, ActionResponse response) throws Exception {
		
		String ip = com.liferay.portal.util.PortalUtil.getHttpServletRequest(request).getRemoteAddr();
		LiferaylmsUtil.saveStringToFile("updateExtraContentMultimediaActivities.txt", "Update ExtraContent Multimedia Activities desde "+ip);
		
		String updateBD = ParamUtil.getString(request, "updateBD", "false");

		List<LearningActivity> learningActivityList = LearningActivityLocalServiceUtil.getLearningActivities(0, LearningActivityLocalServiceUtil.getLearningActivitiesCount());

		int conta = 1;
		String todas ="<p>Actualizado en base de datos:</p>", sqlUpdate = "";
		
		for(LearningActivity activity: learningActivityList){
			
			//Si es un multimediaentry y tiene etiqueta p2p.
			if(activity.getTypeId() == 2 && activity.getExtracontent().contains("<p2p>")){
				
				String xmlMultimedia = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "validaciones");
				String xmlVideo = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "video"); 
				String xmlDocument = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "document");
									
				//Calculamos el nuevo extracontent
				try {			
					HashMap<String, String> hashMap = new HashMap<String, String>();
					Document document;
					
					document = SAXReaderUtil.read(xmlMultimedia);
					Element rootElement = document.getRootElement();
					
					for(Element key:rootElement.elements()){
						hashMap.put(key.getName(), key.getText());
					}
					
					for(Element key:rootElement.elements()){
						if(hashMap.get(key.getName()) != null && key.attributeValue("id") != null){
							hashMap.put(key.getName(), key.attributeValue("id"));
						}
					}
					
					String videoToUpdate = "";
					String documentToUpdate = "";

					if(xmlVideo == ""){
						if(hashMap.get("video") != null){
							videoToUpdate = hashMap.get("video");
						}
					}else{
						videoToUpdate = xmlVideo;
					}

					if(xmlDocument == "" || xmlDocument == null){
						if(hashMap.get("document") != null){
							documentToUpdate = hashMap.get("document");
						}
					}else{
						documentToUpdate = xmlVideo;
					}
					
					if(updateBD.equals("false")){
						//Evitar que falle la carga del video.
						videoToUpdate = videoToUpdate.replace("<iframe", "&lt;iframe");
						videoToUpdate = videoToUpdate.replace("></iframe>", "&gt;&lt;/iframe&gt;");
						
						//Componemos la consulta para actualizar.
						String newExtraContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <multimediaentry>";
						if(documentToUpdate != ""){
							newExtraContent += "<document id=\""+documentToUpdate +"\" />";
						}
						if(videoToUpdate != ""){
							newExtraContent += "<video>"+videoToUpdate +"</video>";
						}
						newExtraContent += "</multimediaentry>";
						
						String updateQuery = " update lms_learningactivity set extracontent='"+newExtraContent+"' where actId="+activity.getActId()+";<br />";
						
						sqlUpdate += HtmlUtil.escape(updateQuery);
						
					}
					//Actualizamos en la bd.
					else if(updateBD.equals("true")){
						//Borramos lo que habia.
						activity.setExtracontent("");
						LearningActivityLocalServiceUtil.updateLearningActivity(activity);
						
						//Anadimos los campos.
						LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "video", HtmlUtil.escape(videoToUpdate));
						LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "document", documentToUpdate);
						
						todas += "<p>Numero: "+ (conta++) +", Activity: "+activity.getTitle(Locale.getDefault())+"<br /> newDocument: " + documentToUpdate+"<br /> newVideo: " + videoToUpdate+"</p>";
					}
					
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
		if(updateBD.equals("true")){
			response.setRenderParameter("resultados", todas);
		}else if(updateBD.equals("false")){
			response.setRenderParameter("resultados", sqlUpdate);
		}
		
	}
	
	public void updateExtraContentScormActivities (ActionRequest request, ActionResponse response) throws Exception {
				
		String updateBD = ParamUtil.getString(request, "updateBD", "false");

		List<LearningActivity> learningActivityList = LearningActivityLocalServiceUtil.getLearningActivities(0, LearningActivityLocalServiceUtil.getLearningActivitiesCount());

		int conta = 1;
		String todas ="<p>Actualizado en base de datos:</p>", sqlUpdate = "";
		
		for(LearningActivity activity: learningActivityList) {
			
			//Si es un scorm y tiene etiqueta scorm.
			if (activity.getTypeId() == 9 && activity.getExtracontent().contains("<scorm>")) {
				
				String uuid = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "uuid");
				String openWindow = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "openWindow");
				String assetEntry = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "assetEntry");
				
				if (Validator.isNull(uuid) && Validator.isNotNull(assetEntry) && Validator.isNumber(assetEntry)) {
					AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Long.valueOf(assetEntry));
					AssetRenderer scorm = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
					uuid = scorm.getUuid();
					if (Validator.isNotNull(openWindow)) {
						String key = "lms.scorm.windowable."+entry.getClassName();
						if (Validator.isNull(key)) {
							key = "false";
						}
						openWindow = PropsUtil.get(key);
					}
					
				}
				
				if(updateBD.equals("false")){						
					//Componemos la consulta para actualizar.
					String newExtraContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <scorm>";
					if (Validator.isNotNull(openWindow)) {
						newExtraContent += "<openWindow>"+openWindow +"<openWindow/>";
					}
					if (Validator.isNotNull(uuid)) {
						newExtraContent += "<uuid>"+uuid +"<uuid/>";
					}
					if (Validator.isNotNull(assetEntry)) {
						newExtraContent += "<assetEntry>"+assetEntry +"</assetEntry>";
					}
					newExtraContent += "</scorm>";
					
					String updateQuery = "UPDATE lms_learningactivity SET extracontent = '"+newExtraContent+"' WHERE actId = "+activity.getActId()+";<br />";
					
					sqlUpdate += HtmlUtil.escape(updateQuery);
					
				}
				//Actualizamos en la bd.
				else if(updateBD.equals("true")){
					//Borramos lo que había.
					activity.setExtracontent("<scorm></scorm>");
					LearningActivityLocalServiceUtil.updateLearningActivity(activity);
					
					//Anadimos los campos.
					LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "openWindow", openWindow);
					LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "uuid", uuid);
					LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "assetEntry", assetEntry);
					
					todas += "<p>Número: "+ (conta++) +", Activity: "+activity.getTitle(Locale.getDefault())+"<br /> openWindow: " + openWindow+"<br /> uuid: " + uuid+"<br /> assetEntry: " + assetEntry+"</p>";
				}
			}
		}
		if(updateBD.equals("true")){
			response.setRenderParameter("resultados", todas);
		}else if(updateBD.equals("false")){
			response.setRenderParameter("resultados", sqlUpdate);
		}
		
	}

	public static void deleteRepeatedModuleResult(boolean updateBD) throws SystemException{

		int deleted = 0;
		String resultados = "";
		String traza = "";
		
		List<Long> moduleResultDeleted = new ArrayList<Long>();
		
		List<ModuleResult> moduleResultList = ModuleResultLocalServiceUtil.getModuleResults(0, ModuleResultLocalServiceUtil.getModuleResultsCount());
		
		Calendar start = Calendar.getInstance();
		log.info(" ## START ## "+start.getTime()+", tamano "+moduleResultList.size());
		
		for(ModuleResult module: moduleResultList){
			
			//Para no volver a hacer lo mismo cuando ya borramos un module result
			if(!moduleResultDeleted.contains(module.getMrId())){

				List<ModuleResult> moduleResultUserList = ModuleResultLocalServiceUtil.getListModuleResultByModuleAndUser(module.getModuleId(), module.getUserId());
				
				if(moduleResultUserList.size() > 1){
				
					log.info("  module: "+module.getModuleId()+", user:  "+module.getUserId()+", Repetidos: "+moduleResultUserList.size());
					
					boolean isFirst = true;
					for(ModuleResult moduleRepeat: moduleResultUserList){
						if(!isFirst){
							
							log.info("   * Borrado ( total borrados: "+deleted+"): "+moduleRepeat.getMrId());
							traza += "  * Borrado ( total borrados: "+deleted+") mrId: "+moduleRepeat.getMrId();
							
							moduleResultDeleted.add(moduleRepeat.getMrId());
							
							if(updateBD){
								try {
									ModuleResultLocalServiceUtil.deleteModuleResult(moduleRepeat.getMrId());
								} catch (Exception e) {
									//e.printStackTrace();
								}
							}
							
							resultados += "delete from lms_moduleresult where mrId = "+moduleRepeat.getMrId()+"; \n";
							
							deleted++;
							
						}else{
							log.info("   Se mantiene : "+moduleRepeat.getMrId());
							traza += "  Se mantiene : "+moduleRepeat.getMrId();
						}
							
						isFirst = false;
					}
				}
			}
		}
		
		Calendar end = Calendar.getInstance();
		log.info("------------------------------------------------");
		log.info(" ## START ## "+start.getTime());
		log.info(" ##  END  ## "+end.getTime());
		log.info(" ##  DELETED  ## "+deleted);
		log.info("------------------------------------------------");
				
		if(updateBD){
			LiferaylmsUtil.saveStringToFile("deleteRepeatedModuleResult.txt", traza);
		}
		else{
			LiferaylmsUtil.saveStringToFile("deleteRepeatedModuleResult.txt", resultados);
		}
	}
	
	
	public void deleteRepeatedModuleResult (ActionRequest request, ActionResponse response) throws Exception {
		
		String ip = com.liferay.portal.util.PortalUtil.getHttpServletRequest(request).getRemoteAddr();
		LiferaylmsUtil.saveStringToFile("deleteRepeatedModuleResult.txt", "Delete Repeated ModuleResult desde "+ip);
		
		String updateBD = ParamUtil.getString(request, "updateBD", "");
		
		deleteRepeatedModuleResult(updateBD.equals("true"));
	}
	
	public void changePortletName (ActionRequest request, ActionResponse response) throws Exception {
		
		try {
			
			boolean updateBD = ParamUtil.getBoolean(request, "updateBD", false);
			String oldPortletName = ParamUtil.getString(request, "before", "");
			String newPortletName = ParamUtil.getString(request, "after", "");
			
			//String oldPortletName = "p2ptaskactivity_WAR_liferaylmsportlet";
			//String newPortletName = "courseadmin_WAR_liferaylmsportlet";
			
			log.debug("OLD Portlet ID: "+oldPortletName);
			Portlet portletOld = PortletLocalServiceUtil.getPortletById(oldPortletName);
			
			log.debug("NEW Portlet ID: "+newPortletName);
			Portlet portletNew = PortletLocalServiceUtil.getPortletById(newPortletName);
			
			
			
			//journalcontentsearch 
			log.debug("\n journalcontentsearch");
			log.debug("----------------------------------------");
			
			List<JournalContentSearch> journals = JournalContentSearchLocalServiceUtil.getArticleContentSearches();
			for(JournalContentSearch journal:journals){
				
				if(journal.getPortletId().compareTo(oldPortletName) == 0){
					log.debug("  *  JournalContentSearch: " + journal.getPortletId());
					log.debug("    - Change: " + journal.getPortletId());
					
					if(updateBD){
						journal.setPortletId(newPortletName);
						JournalContentSearchLocalServiceUtil.updateJournalContentSearch(journal);
					}
				}
			}
			
			//portletitem 
			log.debug("\n portletitem");
			log.debug("----------------------------------------");
			
			List<PortletItem> portletItems = PortletItemLocalServiceUtil.getPortletItems(0, PortletItemLocalServiceUtil.getPortletItemsCount());
			for(PortletItem item:portletItems){
				
				if(item.getPortletId().compareTo(oldPortletName) == 0){
					log.debug("  *  JournalContentSearch: " + item.getPortletId());
					log.debug("    - Change: " + item.getPortletId());
					
					if(updateBD){
						item.setPortletId(newPortletName);
						PortletItemLocalServiceUtil.updatePortletItem(item);
					}
				}
			}
			
			//portletpreferences 
			log.debug("\n portletpreferences");
			log.debug("----------------------------------------");
			
			List<PortletPreferences> pPreferences = PortletPreferencesLocalServiceUtil.getPortletPreferences();
			for(PortletPreferences prefs:pPreferences){
				
				if(prefs.getPortletId().compareTo(oldPortletName) == 0){
					log.debug("  *  PortletPreferences: " + prefs.getPortletPreferencesId());
					log.debug("    - Change: " + prefs.getPortletId());
					
					if(updateBD){
						prefs.setPortletId(newPortletName);
						PortletPreferencesLocalServiceUtil.updatePortletPreferences(prefs);
					}
				}
			}
			
			//repository
			log.debug("\n repository");
			log.debug("----------------------------------------");
			
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(0, LayoutLocalServiceUtil.getLayoutsCount());
			for(Layout layout:layouts){
				
				if(layout.getTypeSettings().contains(oldPortletName)){
					log.debug("  * Layout: " + layout.getName(Locale.getDefault()));
					log.debug("    - Change: " + layout.getGroup().getFriendlyURL() + layout.getFriendlyURL());
					
					if(updateBD){
						layout.setTypeSettings(layout.getTypeSettings().replace(oldPortletName, newPortletName));
						LayoutLocalServiceUtil.updateLayout(layout);
					}
				}
			}
					
			try {
				//Borrar el nombre viejo.
				if(updateBD){
					portletOld.setPortletId(portletOld.getPortletId()+"_old");
					PortletLocalServiceUtil.updatePortlet(portletOld);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(" No se ha podido borrar el portlet antiguo: "+e.getMessage());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void updateModulePassedDate (ActionRequest request, ActionResponse response) throws Exception {

		boolean updateBD = ParamUtil.getBoolean(request, "updateBD", false);
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		Message message=new Message();
		AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), null, "liferay/lms/portalAdmin");
		message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
		message.put("updateBD",updateBD);
		message.put("action","updateModulePassedDate");
		MessageBusUtil.sendMessage("liferay/lms/portalAdmin", message);
		
	}
	

	@ProcessAction(name="checkgroups")
	public void checkgroups(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Course> courses = CourseLocalServiceUtil.findByCompanyId(themeDisplay.getCompanyId());
		
		int counter = 0;
		for(Course course: courses){
			if(log.isDebugEnabled())log.debug("Course::"+course.getCourseId());
			Group group = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			ClassName cn = ClassNameLocalServiceUtil.getClassName(Course.class.getName());
			if(!group.getClassName().equals(Course.class.getName())){
				if(log.isDebugEnabled())log.debug("Change!"+group.getGroupId());

				group.setClassNameId(cn.getClassNameId());
				group.setClassPK(course.getCourseId());
				
				GroupLocalServiceUtil.updateGroup(group);
				
				counter++;
			}
		}
		actionResponse.setRenderParameter("counter", String.valueOf(counter));

		SessionMessages.add(actionRequest, "ok");
	}
}
