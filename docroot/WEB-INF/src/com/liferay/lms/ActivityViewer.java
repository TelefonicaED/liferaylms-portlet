package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.filter.RenderResponseWrapper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.WriterOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletWrapper;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletQName;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class ActivityViewer
 */
public class ActivityViewer extends MVCPortlet {

	public static final String LMS_EDITACTIVITY_PORTLET_ID =  PortalUtil.getJsSafePortletId("editactivity"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String LMS_EDITMODULE_PORTLET_ID =  PortalUtil.getJsSafePortletId("editmodule"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private static Log log = LogFactoryUtil.getLog(ActivityViewer.class);
	
	
	private static Set<String> reservedAttrs = new HashSet<String>();
	private volatile Constructor<?> createComponentContext;
	private volatile Method getContext;
	private volatile Method setContext;
	private volatile Method getPublicParameters;

	static {
		reservedAttrs.add(WebKeys.PAGE_TOP);
		reservedAttrs.add(WebKeys.AUI_SCRIPT_DATA);
		reservedAttrs.add(WebKeys.RUNTIME_PORTLET_IDS);
	}


	@Override
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		try {
			Class<?> publicRenderParametersPoolClass = PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.PublicRenderParametersPool");
			getPublicParameters = publicRenderParametersPoolClass.getMethod("get", HttpServletRequest.class, Long.TYPE);

			Class<?> componentContextClass = PortalClassLoaderUtil.getClassLoader().loadClass("org.apache.struts.tiles.ComponentContext");
			createComponentContext = componentContextClass.getConstructor(Map.class);
			getContext = componentContextClass.getMethod("getContext", ServletRequest.class);
			setContext = componentContextClass.getMethod("setContext",componentContextClass,ServletRequest.class);
		} catch (Throwable e) {
			throw new PortletException(e);
		}
	}

	@SuppressWarnings({"unchecked"})
	private final Map<String, String[]> getPublicParameters(HttpServletRequest request, long plid) throws SystemException{
		try {
			return (Map<String, String[]>) getPublicParameters.invoke(null, request, plid);
		} catch (Throwable e) {
			throw new SystemException(e);
		} 
	}

	private final  Object createComponentContext(Map<String,String> attributes) throws SystemException{
		try {
			return createComponentContext.newInstance(attributes);
		} catch (Throwable e) {
			throw new SystemException(e);
		} 
	}

	private final  Object getContext(ServletRequest servletRequest) throws SystemException{
		try {
			return getContext.invoke(null,servletRequest);
		} catch (Throwable e) {
			throw new SystemException(e);
		} 
	}

	private final void setContext(Object componetContext,ServletRequest servletRequest) throws SystemException{
		try {
			setContext.invoke(null,componetContext,servletRequest);
		} catch (Throwable e) {
			throw new SystemException(e);
		} 
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean isWidget = themeDisplay.isWidget();
		long actId=GetterUtil.DEFAULT_LONG;		
		boolean actionEditingDetails = ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false);
		boolean actionEditingActivity = ParamUtil.getBoolean(renderRequest, "actionEditingActivity", false);
		boolean actionEditingModule = ParamUtil.getBoolean(renderRequest, "actionEditingModule", false);
		boolean actionCalifications = ParamUtil.getBoolean(renderRequest, "actionCalifications", false);
		long typeId = ParamUtil.getLong(renderRequest, "typeId",0);	
		
		log.debug("isWidget:"+isWidget);
		log.debug("actionEditingDetails:"+actionEditingDetails);
		log.debug("actionEditingActivity:"+actionEditingActivity);
		log.debug("actionEditingModule:"+actionEditingModule);
		log.debug("actionCalifications:"+actionCalifications);
		log.debug("typeId:"+typeId);
		
		if(!isWidget && actionEditingDetails){
			actId=ParamUtil.getLong(renderRequest, "resId", ParamUtil.getLong(renderRequest, "actId",0));
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());			
			log.debug("::actId = resId = "+actId);
		}else{
			actId=ParamUtil.getLong(renderRequest, "actId");			
			log.debug("::actId = "+actId);
		}
		
		
		
		if(Validator.isNull(actId)) {
			
			String portletId = null;
			
			if(Validator.isNotNull(typeId) && actionEditingDetails){
				LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(typeId);
				portletId = learningActivityType.getPortletId();
				log.debug("*****Creando actividad, editando detalles : "+typeId);
			}else if(actionEditingActivity){
				portletId = LMS_EDITACTIVITY_PORTLET_ID;
			}else if(actionEditingModule){
				portletId = LMS_EDITMODULE_PORTLET_ID;
			}
			
			if(Validator.isNotNull(portletId)){

				log.debug("***CREACION DE ACTIVIDAD O CREACION/EDICION DE MODULO");


				try{
					Portlet portlet = null;
					log.debug("*****CARGO EL PORTLET: "+portletId);
					portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);

					HttpServletRequest renderHttpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(themeDisplay.getLayout(), portlet.getPortletId());

					if(isWidget){
						Map<String, String[]> publicParameters = getPublicParameters(renderHttpServletRequest, themeDisplay.getPlid());
						for(PublicRenderParameter publicRenderParameter:portlet.getPublicRenderParameters()) {
							String[] parameterValues = renderRequest.getParameterValues(publicRenderParameter.getIdentifier());
							if(Validator.isNotNull(parameterValues)) {
								String publicRenderParameterName = PortletQNameUtil.getPublicRenderParameterName(publicRenderParameter.getQName());
								String[] currentValues = publicParameters.get(publicRenderParameterName);
								if(Validator.isNotNull(currentValues)){
									parameterValues = ArrayUtil.append(parameterValues, currentValues);
								}
								publicParameters.put(publicRenderParameterName, parameterValues);
							}
						}
						renderResponse.setProperty("clear-request-parameters",StringPool.TRUE);

						if(ParamUtil.getBoolean(renderRequest, "scriptMobile",true)) {
							RenderResponseWrapper renderResponseWrapper = new RenderResponseWrapper(renderResponse) {
								private final StringWriter stringWriter = new StringWriter();

								@Override
								public PrintWriter getWriter() throws IOException {
									return new PrintWriter(stringWriter);
								}

								@Override
								public OutputStream getPortletOutputStream()
										throws IOException {
									return new WriterOutputStream(stringWriter);
								}

								@Override
								public String toString() {
									return stringWriter.toString();
								}

							};
							include("/html/activityViewer/scriptMobile.jsp", renderRequest, renderResponseWrapper);

							StringBundler pageTopStringBundler = (StringBundler)renderRequest.getAttribute(WebKeys.PAGE_TOP);

							if (pageTopStringBundler == null) {
								pageTopStringBundler = new StringBundler();
								renderRequest.setAttribute(WebKeys.PAGE_TOP, pageTopStringBundler);
							}


							pageTopStringBundler.append(renderResponseWrapper.toString());
						}
					}
					
					Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
					boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());

					String activityContent = renderPortlet(renderRequest, renderResponse, 
							themeDisplay, themeDisplay.getScopeGroupId(), portlet, isWidget, true, hasPermissionAccessCourseFinished);

					renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
					renderResponse.getWriter().print(activityContent);	
				}catch(Exception e){
					e.printStackTrace();
				}

			}else{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		}else {
			try {
				LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);

				if(Validator.isNull(learningActivity)) {
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}else {
					LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(learningActivity.getTypeId());

					if((!actionEditingActivity && !actionCalifications ) && ((Validator.isNull(learningActivityType))||((!isWidget)&&
					    (themeDisplay.getLayoutTypePortlet().getPortletIds().contains(learningActivityType.getPortletId()))))) {
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}else {
						Portlet portlet = null;
						
						if(actionEditingActivity || actionCalifications ){
							log.debug("*****CARGO EL PORTLET: "+LMS_EDITACTIVITY_PORTLET_ID);
							portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), LMS_EDITACTIVITY_PORTLET_ID);
						}else{							
							log.debug("*****CARGO EL PORTLET: "+learningActivityType.getPortletId());
							portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), learningActivityType.getPortletId());
					}
						
						
						HttpServletRequest renderHttpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
						PortletPreferencesFactoryUtil.getLayoutPortletSetup(themeDisplay.getLayout(), portlet.getPortletId());
						if(isWidget){
							Map<String, String[]> publicParameters = getPublicParameters(renderHttpServletRequest, themeDisplay.getPlid());
							for(PublicRenderParameter publicRenderParameter:portlet.getPublicRenderParameters()) {
								String[] parameterValues = renderRequest.getParameterValues(publicRenderParameter.getIdentifier());
								if(Validator.isNotNull(parameterValues)) {
									String publicRenderParameterName = PortletQNameUtil.getPublicRenderParameterName(publicRenderParameter.getQName());
									String[] currentValues = publicParameters.get(publicRenderParameterName);
									if(Validator.isNotNull(currentValues)){
										parameterValues = ArrayUtil.append(parameterValues, currentValues);
									}
									publicParameters.put(publicRenderParameterName, parameterValues);
								}
							}
							renderResponse.setProperty("clear-request-parameters",StringPool.TRUE);

							if(ParamUtil.getBoolean(renderRequest, "scriptMobile",true)) {
								RenderResponseWrapper renderResponseWrapper = new RenderResponseWrapper(renderResponse) {
									private final StringWriter stringWriter = new StringWriter();

									@Override
									public PrintWriter getWriter() throws IOException {
										return new PrintWriter(stringWriter);
									}

									@Override
									public OutputStream getPortletOutputStream()
											throws IOException {
										return new WriterOutputStream(stringWriter);
									}

									@Override
									public String toString() {
										return stringWriter.toString();
									}

								};
								include("/html/activityViewer/scriptMobile.jsp", renderRequest, renderResponseWrapper);

								StringBundler pageTopStringBundler = (StringBundler)renderRequest.getAttribute(WebKeys.PAGE_TOP);

								if (pageTopStringBundler == null) {
									pageTopStringBundler = new StringBundler();
									renderRequest.setAttribute(WebKeys.PAGE_TOP, pageTopStringBundler);
								}


								pageTopStringBundler.append(renderResponseWrapper.toString());
							}
						}
						
						//Si estoy en modo consulta incluyo el jsp
						Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(learningActivity.getGroupId());
						boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());
						log.debug("hasPermissionAccessCourseFinished: " + hasPermissionAccessCourseFinished);
						
						AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivity.class.getName(),
								actId, themeDisplay.getUserId(), AuditConstants.VIEW, Long.toString(learningActivityType.getTypeId()));

						String activityContent = renderPortlet(renderRequest, renderResponse, 
								themeDisplay, themeDisplay.getScopeGroupId(), portlet, isWidget, true, hasPermissionAccessCourseFinished);
						SocialActivityLocalServiceUtil.addActivity(
								learningActivity.getUserId(), learningActivity.getGroupId(),
								LearningActivity.class.getName(), learningActivity.getActId(),
								SocialActivityConstants.TYPE_VIEW, StringPool.BLANK, 0);
						renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
						renderResponse.getWriter().print(activityContent);
						String portletName = learningActivityType.getPortletId();

						int warSeparatorIndex = portletName.indexOf(PortletConstants.WAR_SEPARATOR);
						if (warSeparatorIndex != -1) {
							portletName = portletName.substring(0, warSeparatorIndex);
						}
					}
				}
			}catch(Throwable throwable) {
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		}

		//super.render(renderRequest, renderResponse);
	}


	/**
	 * Renders the given portlet as a runtime portlet and returns the portlet's HTML.
	 * Based on http://www.devatwork.nl/2011/07/liferay-embedding-portlets-in-your-portlet/
	 * @param hasPermissionAccessCourseFinished 
	 * @throws PortalException 
	 * @throws PortletException 
	 */
	@SuppressWarnings("unchecked")
	public String renderPortlet(final RenderRequest request, final RenderResponse response,final ThemeDisplay themeDisplay,
			final long scopeGroup, final Portlet portlet,final boolean copyNonNamespaceParameters,final boolean copyPublicParameters, boolean hasPermissionAccessCourseFinished) 
					throws SystemException, IOException, ServletException, PortalException, PortletException {
		// Get servlet request / response
		HttpServletRequest renderServletRequest = PortalUtil.getHttpServletRequest(request);
		HttpSession renderServletSession = renderServletRequest.getSession();
		HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(renderServletRequest);
		HttpSession servletSession = servletRequest.getSession();
		HttpServletResponse servletResponse = PortalUtil.getHttpServletResponse(response);



		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		PortletDisplay portletDisplayClone = new PortletDisplay();
		portletDisplay.copyTo(portletDisplayClone);
		final Map<String, Object> requestAttributeBackup = new HashMap<String, Object>();
		for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
			requestAttributeBackup.put(key, servletRequest.getAttribute(key));
		}

		final Map<String, Object> sessionAttributeBackup = new HashMap<String, Object>();
		for (final String key : Collections.list((Enumeration<String>) servletSession.getAttributeNames())) {
			sessionAttributeBackup.put(key, servletSession.getAttribute(key));
		}

		for (final String key : Collections.list((Enumeration<String>) renderServletSession.getAttributeNames())) {
			if(Validator.isNull(servletSession.getAttribute(key))) {
				servletSession.setAttribute(key, renderServletSession.getAttribute(key));
			}
		}

		// Render the portlet as a runtime portlet
		String result=null;
		long currentScopeGroup = themeDisplay.getScopeGroupId();
		String currentOuterPortlet = (String) servletRequest.getAttribute("OUTER_PORTLET_ID");
		Layout currentLayout = (Layout)servletRequest.getAttribute(WebKeys.LAYOUT);
		try {
			ServletContext servletContext = (ServletContext)servletRequest.getAttribute(WebKeys.CTX);
			servletRequest.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);
			long defaultGroupPlid = LayoutLocalServiceUtil.getDefaultPlid(scopeGroup);
			if(defaultGroupPlid!=LayoutConstants.DEFAULT_PLID) {
				servletRequest.setAttribute(WebKeys.LAYOUT, LayoutLocalServiceUtil.getLayout(defaultGroupPlid));
			}

			servletRequest.setAttribute("OUTER_PORTLET_ID",PortalUtil.getPortletId(request));

			StringBundler queryStringStringBundler = new StringBundler();

			if(copyNonNamespaceParameters) {
				String portletNamespace = PortalUtil.getPortletNamespace(portlet.getPortletId());
				Map<String, String[]> parameters = servletRequest.getParameterMap();
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
					if((!entry.getKey().startsWith(PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE))&&
							(!entry.getKey().startsWith(portletNamespace))&&
							(!PortalUtil.isReservedParameter(entry.getKey()))&&
							(!request.getPublicParameterMap().containsKey(entry.getKey()))) {
						for(String value:entry.getValue()) {
							if(queryStringStringBundler.index()!=0) {
								queryStringStringBundler.append(StringPool.AMPERSAND);
							}
							queryStringStringBundler.append(entry.getKey());
							queryStringStringBundler.append(StringPool.EQUAL);
							queryStringStringBundler.append(value);
						}
					}
				}
			}

			if(copyPublicParameters) {
				for (Entry<String, String[]> entry : request.getPublicParameterMap().entrySet()) {
					String[] values = entry.getValue();
					for(int itrValues=values.length-1;itrValues>=0;itrValues--) {
						if(queryStringStringBundler.index()!=0) {
							queryStringStringBundler.append(StringPool.AMPERSAND);
						}
						queryStringStringBundler.append(entry.getKey());
						queryStringStringBundler.append(StringPool.EQUAL);
						queryStringStringBundler.append(values[itrValues]);
					}
				}
			}

			String renderedPortlet = PortalUtil.renderPortlet(servletContext, servletRequest, servletResponse, 
					new PortletWrapper(portlet){
				private static final long serialVersionUID = 229422682924083706L;

				@Override
				public boolean isUseDefaultTemplate() {
					return false;
				}
			}, queryStringStringBundler.toString(), false);

			for (final String key : Collections.list((Enumeration<String>) servletSession.getAttributeNames())) {
				if(Validator.isNull(renderServletRequest.getAttribute(key))) {
					renderServletRequest.setAttribute(key, servletSession.getAttribute(key));
				}
			}

			List<String> markupHeaders = (List<String>)servletRequest.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);
			if((Validator.isNotNull(markupHeaders))&&(!markupHeaders.isEmpty())) {
				StringBundler pageTopStringBundler = (StringBundler)request.getAttribute(WebKeys.PAGE_TOP);

				if (pageTopStringBundler == null) {
					pageTopStringBundler = new StringBundler();
					request.setAttribute(WebKeys.PAGE_TOP, pageTopStringBundler);
				}

				for(String markupHeader:markupHeaders) {
					pageTopStringBundler.append(markupHeader);
				}
			}

			if(portlet.isUseDefaultTemplate()) {
				String  portletHeader = StringPool.BLANK, 
						portletBody = renderedPortlet,
						portletQueue = StringPool.BLANK;

				int portletBodyBegin = renderedPortlet.indexOf(PORTLET_BODY);
				if(portletBodyBegin>0) {
					int portletBodyEnd = renderedPortlet.lastIndexOf(DIV_END, renderedPortlet.lastIndexOf(DIV_END)-1);
					portletBodyBegin+=PORTLET_BODY.length();
					portletHeader = renderedPortlet.substring(0, portletBodyBegin);
					portletBody = renderedPortlet.substring(portletBodyBegin, portletBodyEnd);
					portletQueue = renderedPortlet.substring(portletBodyEnd);
				}

				if(Validator.isNull(getContext(servletRequest))) {
					Map<String,String> attributes = new HashMap<String,String>();
					attributes.put("portlet_content", themeDisplay.getTilesContent());
					setContext(createComponentContext(attributes), servletRequest);
				}

				servletRequest.setAttribute(PortletRequest.LIFECYCLE_PHASE,PortletRequest.RENDER_PHASE);
				servletRequest.setAttribute(WebKeys.RENDER_PORTLET, portlet);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, request);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, response);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_CONFIG, PortletConfigFactoryUtil.create(portlet, servletContext));
				servletRequest.setAttribute("PORTLET_CONTENT", portletBody);
				
				log.debug("servletContext: " + servletContext.getContextPath());
				log.debug("otro servletContext: " + ((ServletContext)renderServletRequest.getAttribute(WebKeys.CTX)).getContextPath());

				String permissionCourseFinished = "";
				if(hasPermissionAccessCourseFinished){
					RenderResponseWrapper renderResponseWrapper = new RenderResponseWrapper(response) {
						private final StringWriter stringWriter = new StringWriter();

						@Override
						public PrintWriter getWriter() throws IOException {
							return new PrintWriter(stringWriter);
						}

						@Override
						public OutputStream getPortletOutputStream()
								throws IOException {
							return new WriterOutputStream(stringWriter);
						}

						@Override
						public String toString() {
							return stringWriter.toString();
						}

					};
					include("/html/activityViewer/access_course_finished.jsp", request, renderResponseWrapper);

					permissionCourseFinished = renderResponseWrapper.toString();
				}
				
				result = portletHeader+
						permissionCourseFinished+
						PortalUtil.renderPage(servletContext, servletRequest, servletResponse, "/html/common/themes/portlet.jsp",false)+
						portletQueue;
			}
			else {
				result = renderedPortlet;
			}

			Set<String> runtimePortletIds = (Set<String>)request.getAttribute(
					WebKeys.RUNTIME_PORTLET_IDS);

			if (runtimePortletIds == null) {
				runtimePortletIds = new HashSet<String>();
			}

			runtimePortletIds.add(portlet.getPortletId());

			request.setAttribute(WebKeys.RUNTIME_PORTLET_IDS, runtimePortletIds);
		}finally {
			// Restore the state
			Set<Entry<String,Object>> sessionAttributesSet = sessionAttributeBackup.entrySet();
			for (Entry<String, Object> entry : sessionAttributesSet) {
				if(Validator.isNull(servletRequest.getAttribute(entry.getKey()))) {
					servletSession.setAttribute(entry.getKey(), entry.getValue());
				}
			}

			for (final String key : Collections.list((Enumeration<String>) servletSession.getAttributeNames())) {
				if(!sessionAttributeBackup.containsKey(key)) {
					servletSession.removeAttribute(key);
				}
			}

			themeDisplay.setScopeGroupId(currentScopeGroup);
			servletRequest.setAttribute(WebKeys.LAYOUT, currentLayout);
			servletRequest.setAttribute("OUTER_PORTLET_ID",currentOuterPortlet);
			portletDisplay.copyFrom(portletDisplayClone);
			portletDisplayClone.recycle();
			for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
				if ((!requestAttributeBackup.containsKey(key))&&(!reservedAttrs.contains(key))) {
					servletRequest.removeAttribute(key);
				}
			}
			for (final Map.Entry<String, Object> entry : requestAttributeBackup.entrySet()) {
				servletRequest.setAttribute(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}

	private static final String PORTLET_BODY = "<div class=\"portlet-body\">";
	private static final String DIV_END = "</div>";
}
