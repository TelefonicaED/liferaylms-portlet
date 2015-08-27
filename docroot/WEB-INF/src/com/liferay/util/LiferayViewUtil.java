package com.liferay.util; 

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class LiferayViewUtil 
{
	public static long[] getAssetCategoryIds(UploadPortletRequest uploadRequest)
	{
		return StringUtil.split(ParamUtil.getString(uploadRequest, "assetCategoryIds"), 0L);	
	}
	
	public static String[] getAssetTagNames(UploadPortletRequest uploadRequest)
	{
		// En teoría tenemos que obtener los tags y categories así, pero 
		// hay que tener en cuenta que es un multipart y los params están realmente
		// en el uploadRequest y no en el actionRequest:			
		// http://www.liferay.com/community/wiki/-/wiki/Main/ServiceContext+Pattern
				
		return StringUtil.split(ParamUtil.getString(uploadRequest, "assetTagNames"));		
	}	
	
	public static String getNamespace(RenderResponse renderResponse,ResourceResponse resourceResponse)
	{
		if (renderResponse != null) return renderResponse.getNamespace();
		else return resourceResponse.getNamespace();
	}
	
	public static boolean getParamBoolean(PortletRequest request,String name,boolean defaultValue)
	{
		String valueStr = request.getParameter(name);
		if (valueStr == null) return defaultValue;		
		else return Boolean.parseBoolean(valueStr);	
	}	
	
	public static int getParamInt(PortletRequest request,String name,int defaultValue)
	{
		String valueStr = request.getParameter(name);
		if (valueStr == null) return defaultValue;		
		else return Integer.parseInt(valueStr);	
	}
	
	public static String getParamString(PortletRequest request,String name,String defaultValue)
	{
		String valueStr = request.getParameter(name);
		if (valueStr == null) return defaultValue;		
		return valueStr;	
	}	
	
	public static void copyRequestToRenderParameters(ActionRequest actionRequest,ActionResponse actionResponse) throws WindowStateException
	{	
		copyRequestToRenderParameters(actionRequest,null,null,actionResponse);
	}		
	
	public static void copyRequestToRenderParameters(ActionRequest actionRequest,UploadPortletRequest uploadRequest,Set<String> excludedParamsOnUpload,ActionResponse actionResponse) throws WindowStateException
	{	
		if (uploadRequest != null) 
		{
			// Machacamos los parámetros actuales del response pues vamos a simular
			// lo que hace el setRenderParameters de Liferay pero en el UploadPortletRequest
			// Los excludedParams es para evitar los parámetros que contienen los bytes de archivos
			// subidos por web (aunque sea una String el valor los bytes internos guardan el archivo)
			// por dos razones:
			// 1) Es rarísimo que sean necesarios en la renderización de la salida
			// 2) Vuelven loco al Tomcat que no se espera Strings "tan raras" en el caso de
			//    de un archivo no textual.
			Map<String,String[]> paramMap = actionResponse.getRenderParameterMap();
			if (paramMap != null) paramMap.clear();
	
			for(@SuppressWarnings("unchecked") 
				Enumeration<String> nameList = uploadRequest.getParameterNames(); 
				nameList.hasMoreElements(); )
			{
				String name = nameList.nextElement();
				if (excludedParamsOnUpload != null && excludedParamsOnUpload.contains(name))
					continue;
				String[] values = uploadRequest.getParameterValues(name);
				actionResponse.setRenderParameter(name,values);			
			}
		
			// Lo hacemos después del uploadRequest para que reemplace los comunes	(por si acaso aunque yo creo que da igual)		
			for(Map.Entry<String,String[]> entry : actionRequest.getParameterMap().entrySet())
			{
				String name = entry.getKey();
				if (excludedParamsOnUpload != null && excludedParamsOnUpload.contains(name))
					continue;				
				String[] values = entry.getValue();			
				actionResponse.setRenderParameter(name,values);		
			}
		}
		else
		{
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
		}
		
		setWindowStateRenderParameter(actionResponse,actionRequest.getWindowState());			
	}
	
	public static void setWindowStateRenderParameter(ActionResponse actionResponse,WindowState winState) throws WindowStateException
	{
		actionResponse.setWindowState(winState);
		actionResponse.setRenderParameter("p_p_state", winState.toString()); 
			// En teoría el parámetro windowState del actionURL debería ser suficiente,
			// y el setWindowState debería ser una inútil reiteración, pero aun así
			// lo único que funciona es poner A PELO el p_p_state, o hago algo mal o es penoso en fin...		
	}
	
	public static boolean hasPermissionInPortlet(PortletRequest request,String action) throws SystemException,PortalException
	{
		ThemeDisplay themeDisplay = getThemeDisplay(request);		
		PortletPermission portletPermission = PortletPermissionUtil.getPortletPermission();			
		PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();	
		long plid = themeDisplay.getLayout().getPlid();  // http://www.liferay.com/community/forums/-/message_boards/message/1224284
		String porletDispId = getPortletDisplayId(request,themeDisplay);
		return portletPermission.contains(permissionChecker,plid,porletDispId,action);
	}	
	

	public static ThemeDisplay getThemeDisplay(PortletRequest request)
	{
		return (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY); 
	}		
	
	public static ThemeDisplay getThemeDisplay(HttpServletRequest request)
	{
		return (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY); 
	}	
	
	public static User getUser(PortletRequest request)
	{
		// Este método es porque curiosamente PortalUtil.getUser(request); puede devolver null
		// y puede llegar a buscar en base de datos y a nosotros nos interesa simplemente
		// el usuario actual
		return getThemeDisplay(request).getUser(); // NO hace una búsqueda en base de datos, comprobado en código fuente  
	}	
	
	public static String getCurrentPageURL(ThemeDisplay themeDisplay)
	{	
		return getHomeURLOfCurrentGroup(themeDisplay) + themeDisplay.getLayout().getFriendlyURL();
	}
	
	public static String getHomeURLOfCurrentGroup(ThemeDisplay themeDisplay)
	{
		boolean publicURL = themeDisplay.getLayout().isPublicLayout();
		Group group = themeDisplay.getScopeGroup();
		return publicURL ? getPublicURLOfGroup(group,themeDisplay) : getPrivateURLOfGroup(group,themeDisplay);
	}
	
    public static String getPrivateURLOfGroup(Group group,ThemeDisplay themeDisplay)
    {
		String portalURL = themeDisplay.getPortalURL(); // Ej. "http://localhost" Podría ser también: PortalURL.getPortalURL(request);
		String privateGroup = themeDisplay.getPathFriendlyURLPrivateGroup();  // Ej. "/es/group"   
		// Si fuera la comunidad pública usar: getPathFriendlyURLPublic() que sería "/es/web"
		String groupFriendlyURL = group.getFriendlyURL(); // Ej. "/escuela-de-ingles"
    	// Ej. http://localhost/es/group/escuela-de-ingles 
    	return portalURL + privateGroup + groupFriendlyURL;  
    }	
    
    public static String getPublicURLOfGroup(Group group,ThemeDisplay themeDisplay)
    {
		String portalURL = themeDisplay.getPortalURL(); // Ej. "http://localhost" Podría ser también: PortalURL.getPortalURL(request);
		String publicGroup = themeDisplay.getPathFriendlyURLPublic();  // Ej. "/es/web"   
		String groupFriendlyURL = group.getFriendlyURL(); // Ej. "/escuela-de-ingles"
    	// Ej. http://localhost/es/web/escuela-de-ingles 
    	return portalURL + publicGroup + groupFriendlyURL;  
    }	    
	
	public static PortletPreferences getPortletPreferences(PortletRequest portletRequest) throws SystemException, PortalException
	{
		// En la documentación de Liferay sobre el modo configuración de un portlet pone que para acceder
		// a las preferences hay que llamar a PortletPreferencesFactoryUtil.getPortletSetup(portletRequest)
		// sin el portletResource, pero luego en un JSP "normal" (no configuración) es como si devolviera un 
		// PortletPreferences diferente tal que no tiene los datos salvados
		// http://www.liferay.com/community/wiki/-/wiki/Main/Portlet+Skins
		// El valor devuelto asociado a "portletResource" es el indentificador del portlet  
		PortletPreferences prefs = portletRequest.getPreferences();
		String portletResource = ParamUtil.getString(portletRequest, "portletResource");	
		if (Validator.isNotNull(portletResource)) // Siempre es true pero por seguir el patrón documentado...
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(portletRequest, portletResource);			
		return prefs;
	}	    
		
	public static String getPortletDisplayId(PortletRequest request,ThemeDisplay themeDisplay)
	{
		// El "portlet display id" es el identificador del portlet
		// Ejemplo: mediatecabyranking_WAR_npaportletportlet
		String id = themeDisplay.getPortletDisplay().getId();
		if (id == null || id.equals(""))
		{
			// Bug de Liferay, estamos en una fase action y el PortletDisplay del ThemeDisplay no se inicializa en una fase action
			// http://www.liferay.com/community/forums/-/message_boards/message/5446551
			id = PortalUtil.getPortletId(request);		
		}		
		return id;
	}	
	
	public static String setPortletURLBack(ThemeDisplay themeDisplay,String backURL) throws WindowStateException
	{
		String jsBackURL;
		if (backURL != null) jsBackURL = "window.location.href='" + backURL + "'";
		else jsBackURL = "window.history.go(-1)";
		themeDisplay.getPortletDisplay().setURLBack("javascript:" + jsBackURL);
		return jsBackURL; // Como JavaScript
	}	
	
	public static boolean isSuperUser(ThemeDisplay themeDisplay)
	{
		return (themeDisplay.isSignedIn() && 
				(themeDisplay.getPermissionChecker().isOmniadmin() ||
						themeDisplay.getPermissionChecker().isCompanyAdmin()));
	}		
}
