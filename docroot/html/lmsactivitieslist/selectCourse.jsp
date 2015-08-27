<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil"%>
<%@ include file="/init.jsp"%>
<% 
	long typeId = ParamUtil.getLong(renderRequest, "type",-1);
	long assetRendererPlid = ParamUtil.getLong(renderRequest, "assetRendererPlid");
	String assetRendererId = ParamUtil.getString(renderRequest, "assetRendererId");
	Class<?> assetPublisherUtilClass = PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.assetpublisher.util.AssetPublisherUtil");
	Method  getGroupIds = assetPublisherUtilClass.getMethod("getGroupIds", PortletPreferences.class, Long.TYPE, Layout.class);
    PortletPreferences assetRendererPreferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(),
													PortletKeys.PREFS_OWNER_ID_DEFAULT,
													PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
													assetRendererPlid,
													ParamUtil.getString(renderRequest, "assetRendererId"));
    Layout assetRendererLayout = LayoutLocalServiceUtil.fetchLayout(assetRendererPlid);
    Long[] groups = ArrayUtil.toArray((long[])getGroupIds.invoke(null,assetRendererPreferences, assetRendererLayout.getGroupId(), assetRendererLayout));
    AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
    
    PortletURL selectCourseUrl = renderResponse.createRenderURL();
	    selectCourseUrl.setParameter("mvcPath", "/html/lmsactivitieslist/selectCourse.jsp");
	    selectCourseUrl.setParameter("assetRendererId",themeDisplay.getPortletDisplay().getId());
	    selectCourseUrl.setParameter("assetRendererPlid",Long.toString(assetRendererPlid));
		if(typeId!=-1){
			selectCourseUrl.setParameter("type", Long.toString(typeId));
		}
    
    
%>

<liferay-ui:search-container id="tutorized" emptyResultsMessage="there-are-no-results" iteratorURL="<%=selectCourseUrl %>" delta="10" >
	<liferay-ui:search-container-results>
		<%
			searchContainer.setTotal(
					(int)CourseLocalServiceUtil.dynamicQueryCount(
							CourseLocalServiceUtil.dynamicQuery().
								add(PropertyFactoryUtil.forName("groupCreatedId").
									in(groups))));
			pageContext.setAttribute("total",searchContainer.getTotal());
			pageContext.setAttribute("results",
					CourseLocalServiceUtil.dynamicQuery(
							CourseLocalServiceUtil.dynamicQuery().
								add(PropertyFactoryUtil.forName("groupCreatedId").
									in(groups)),searchContainer.getStart(), searchContainer.getEnd()));
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
		<% 
			liferayPortletRequest.setAttribute("courseId", course.getCourseId());
			if(typeId>=0){
				liferayPortletRequest.setAttribute(WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID,typeId);
			}
			PortletURL newURL = assetRendererFactory.getURLAdd(liferayPortletRequest, liferayPortletResponse);
			newURL.setWindowState(renderRequest.getWindowState());
		%>
		<liferay-ui:search-container-column-text name="course" align="left" >
			<%=course.getTitle(themeDisplay.getLocale()) %>	
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text align="right" >
			<liferay-ui:icon image="add" message="add" label="<%=false %>" url="<%=newURL.toString() %>" />
		</liferay-ui:search-container-column-text>	
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
	<script type="text/javascript">
	<!--
		function <portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A) {

			var searchContainer = A.one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
			function <portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url){
				var params = {};
				var urlPieces = url.split('?');
				if (urlPieces.length > 1) {
					params = A.QueryString.parse(urlPieces[1]);
					params.p_p_state='<%=LiferayWindowState.EXCLUSIVE.toString() %>';
					url = urlPieces[0];
				}

				A.io.request(
					url,
					{
						data: params,
						dataType: 'html',
						on: {
							failure: function(event, id, obj) {
								var portlet = A.one('#p_p_id<portlet:namespace />');
								portlet.hide();
								portlet.placeAfter('<div class="portlet-msg-error"><liferay-ui:message key="there-was-an-unexpected-error.-please-refresh-the-current-page"/></div>');
							},
							success: function(event, id, obj) {
								searchContainer.setContent(A.Node.create(this.get('responseData')).one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container').getContent ());
								<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A);
								searchContainer.fire('ajaxLoaded');
							}
						}
					}
				);
			}
			
			<portlet:namespace /><%= searchContainer.getCurParam() %>updateCur = function(box){
				<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
				%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getCurParam()) 
					%>&<%= renderResponse.getNamespace() + searchContainer.getCurParam() %>=' + A.one(box).val());
			};

			<portlet:namespace /><%= searchContainer.getDeltaParam() %>updateDelta = function(box){
				<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
					%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getDeltaParam()) 
						%>&<%= renderResponse.getNamespace() + searchContainer.getDeltaParam() %>=' + A.one(box).val());
			};

			searchContainer.all('.taglib-page-iterator').each(
				function(pageIterator){
					pageIterator.all('a').each(
						function(anchor){
							var url=anchor.get('href');
							anchor.set('href','#');
						    anchor.on('click',
								function(){
						    		<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url);
							    }
						    );
						}
					);
				}
			);

		};

		AUI().ready('aui-io-request','querystring-parse','aui-parse-content',<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer);
	//-->
	</script>
</liferay-ui:search-container>