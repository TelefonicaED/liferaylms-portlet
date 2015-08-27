package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.script.Invocable;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

public class SCORMContentAssetRenderer extends BaseAssetRenderer implements Invocable {

	private SCORMContent _scorm;
	AssetEntry assetEntry;
	public SCORMContentAssetRenderer (SCORMContent scorm) {
		_scorm = scorm;
		try {
			assetEntry=AssetEntryLocalServiceUtil.getEntry(SCORMContent.class.getName(), scorm.getScormId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		}
		public long getClassPK() {
		return _scorm.getScormId();
		}
		@Override
		public String getDiscussionPath() {
			return "edit_file_entry_discussion";
		}
		public long getGroupId() {
		return _scorm.getGroupId();
		}
		public String getSummary() { 
		return assetEntry.getDescription();
		} 
		public String getTitle() { 
			
		return assetEntry.getTitle();
		} 
		public long getUserId() {
		return _scorm.getUserId();
		}
		public String getUuid() {
		return _scorm.getUuid();
		}
		public String render(RenderRequest request, RenderResponse response,
				String template)
				throws Exception {
			request.setAttribute("scorm", _scorm);
					if (template.equals(TEMPLATE_FULL_CONTENT)) {
						request.setAttribute("scorm", _scorm);
						String [] jss = {
								"/liferaylms-portlet/js/scorm/sscompat.js",
								"/liferaylms-portlet/js/scorm/sscorlib.js",
								"/liferaylms-portlet/js/scorm/ssfx.Core.js",
							 
								"/liferaylms-portlet/js/scorm/API_BASE.js",
							    "/liferaylms-portlet/js/scorm/API.js",
							    "/liferaylms-portlet/js/scorm/API_1484_11.js",

							    "/liferaylms-portlet/js/scorm/Controls.js",
								"/liferaylms-portlet/js/scorm/LocalStorage.js",
								"/liferaylms-portlet/js/scorm/Player.js",
								"/liferaylms-portlet/js/scorm/PersistenceStoragePatched.js"
						};
						for (int i = 0; i < jss.length; i++) {
							org.w3c.dom.Element jsLink = response.createElement("script");
							jsLink.setAttribute("type", "text/javascript");
							jsLink.setAttribute("src", jss[i]);
							jsLink.setTextContent(" "); // important
							response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsLink);
						}
						return "/html/asset/scorm/" + template + ".jsp";
						}
						else {
							return null;
						}
						
						
					}
			public PortletURL getURLEdit(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse) throws Exception 
			{
				 HttpServletRequest request =
			            liferayPortletRequest.getHttpServletRequest();

			 ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			            WebKeys.THEME_DISPLAY);
			 try
		      {
		    	  PortletURL portletURL= 
		    			  PortletURLFactoryUtil.create(request,"scormadmin_WAR_liferaylmsportlet",getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);
		          portletURL.setParameter("mvcPath", "/html/scormadmin/editscorm.jsp");
		          portletURL.setParameter("scormId", Long.toString(_scorm.getScormId()));
		         return portletURL;
		      }
		      catch(Exception e)
		      {
		    	  e.printStackTrace();
		    	  
		      }
		      return null;
		
			
			}
			@Override
			public String getURLViewInContext(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse,
					String noSuchEntryRedirect) throws Exception {
				if (liferayPortletRequest != null && liferayPortletResponse == null) {
					return "file://" + SCORMContentLocalServiceUtil.getDirScormzipPath(_scorm) + "/"+_scorm.getUuid()+".zip";
					//ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
					//return PortalUtil.getPortalURL(serviceContext.getRequest())+"/"+ClpSerializer.getServletContextName()+"/scorm/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/imsmanifest.xml";
				}
				if (liferayPortletRequest == null && liferayPortletResponse == null) {
					ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
					return PortalUtil.getPortalURL(serviceContext.getRequest())+"/"+ClpSerializer.getServletContextName()+"/scormzip/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/"+_scorm.getUuid()+".zip?ciphered="+(_scorm.getCiphered() ? "1" : "0");
				}
				return null;
			}
			@Override
			public String getViewInContextMessage() {
				// TODO Auto-generated method stub
				
				return "";
			}
			@Override
			public boolean hasEditPermission(PermissionChecker permissionChecker)
					throws PortalException, SystemException {
				if( permissionChecker.hasPermission(_scorm.getGroupId(), SCORMContent.class.getName(), _scorm.getScormId(),ActionKeys.UPDATE))
				{
					return true;
				}
				else
				{
					return permissionChecker.hasOwnerPermission(_scorm.getCompanyId(), SCORMContent.class.getName(), _scorm.getScormId(),_scorm.getUserId(),ActionKeys.UPDATE);
				}
			}
			
			public String getSummary(Locale locale) {
				
				return HtmlUtil.extractText(_scorm.getDescription());
			}
			
			public String getTitle(Locale locale) {
				// TODO Auto-generated method stub
				return _scorm.getTitle();
			}
			@Override
			public String getUserName() {
				return null;
			}
   
			@Override
			public boolean hasViewPermission(PermissionChecker permissionChecker)
				throws PortalException, SystemException {
				
				return permissionChecker.hasPermission(_scorm.getGroupId(), SCORMContent.class.getName(), _scorm.getScormId(),ActionKeys.VIEW);
			}
			
			String getUrl(HttpServletRequest request){
				return PortalUtil.getPortalURL(request)+"/"+ClpSerializer.getServletContextName()+"/scormzip/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/"+_scorm.getUuid()+".zip?ciphered="+(_scorm.getCiphered() ? "1" : "0");
			}
			
			String getFileUrl(HttpServletRequest request, String path) {
				request = ServiceContextThreadLocal.getServiceContext().getRequest();
				return PortalUtil.getPortalURL(request)+"/"+ClpSerializer.getServletContextName()+"/scorm/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/"+path;
			}

			@Override
			public Object invokeMethod(Object thiz, String name, Object... args)
					throws ScriptException, NoSuchMethodException {
				throw new NoSuchMethodException();
			}

			@Override
			public Object invokeFunction(String name, Object... args)
					throws ScriptException, NoSuchMethodException {
				if(("getUrl".equals(name))&&(args.length==1)&&(args[0] instanceof HttpServletRequest)) {
					return getUrl((HttpServletRequest) args[0]);
				}
				
				if(("getFileUrl".equals(name))&&(args.length==2)&&(args[0] == null || args[0] instanceof HttpServletRequest)&&(args[1] instanceof String)) {
					return getFileUrl((HttpServletRequest) args[0], (String) args[1]);
				}
				
				throw new NoSuchMethodException(name);
			}

			@Override
			public <T> T getInterface(Class<T> clasz) {
				return null;
			}

			@Override
			public <T> T getInterface(Object thiz, Class<T> clasz) {
				return null;
			}
}
