<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@ include file="/init-min.jsp" %>

<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>

<jsp:useBean id="listDocuments" type="java.util.List<com.liferay.portal.kernel.repository.model.FileVersion>" scope="request"/>

<%@ include file="/html/shared/isTablet.jsp" %>

<%
long actId=ParamUtil.getLong(request,"actId",0);

Boolean isLinkTabletResourceExternal = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassResourceExternal="";
if(isLinkTabletResourceExternal){
	cssLinkTabletClassResourceExternal="tablet-link";
}%> 

<h2 class="description-title">${activity.getTitle(themeDisplay.locale)}</h2>

<div class="description">${activity.getDescriptionFiltered(themeDisplay.locale,true)}</div>

<c:choose>
	<c:when test="${isDLFileEntry }">
		<div class="video">
			
					<embed type="application/x-shockwave-flash" src="${request.contextPath}/flash/flvplayer/playervideo.swf" 
		  			width="560" height="315" style="undefined" id="cab" name="cab" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" allowscriptaccess="always" wmode="transparent" menu="false" 
		  			flashvars="file=${video}&type=flv" />
		</div>
		<script>
			var unloadEvent = function (e) {
				console.log("unloadEvent video dlfileentry ");
				var serviceParameterTypes = [
			     	'long',
			     	'int',
			     	'double',
			    	'int'
			    ];
				
				var message = Liferay.Service.Lms.LearningActivityTry.update(
					{
						latId: '${latId}',
						score: 100,
						position: 0,
						plays: 0,
						serviceParameterTypes: JSON.stringify(serviceParameterTypes)
					}
				);
				var exception = message.exception;											
					
			  };
			  window.addEventListener("beforeunload", unloadEvent);	
		</script>
	</c:when>
	<c:when test="${isYoutubeIframe || isVimeoIframe }">
		<div class="video">
			${video}
		</div>
		<c:if test="${isYoutubeIframe}">
			<%@ include file="/html/resourceExternalActivity/youtube.jsp" %>		
		</c:if>	
		<c:if test="${isVimeoIframe}">			
			<%@ include file="/html/resourceExternalActivity/vimeo.jsp" %>			
		</c:if>
	</c:when>
	<c:otherwise>
		<script>
			var unloadEvent = function (e) {
				console.log("unloadEvent otherwise");
				var serviceParameterTypes = [
			     	'long',
			     	'int',
			     	'double',
			    	'int'
			    ];
				
				var message = Liferay.Service.Lms.LearningActivityTry.update(
					{
						latId: '${latId}',
						score: 100,
						position: 0,
						plays: 0,
						serviceParameterTypes: JSON.stringify(serviceParameterTypes)
					}
				);
				var exception = message.exception;												
					
			  };
			  window.addEventListener("beforeunload", unloadEvent);	
		</script>
	</c:otherwise>
</c:choose>
	<c:if test="<%=listDocuments != null && listDocuments.size() > 0%>">
		<div class="container_files">
			<%for(FileVersion documentVersion: listDocuments){ %>
				<div class="row_file">
					<span class="upfile">
						<a href='<%=DLUtil.getPreviewURL(documentVersion.getFileEntry(), documentVersion, themeDisplay, "")%>' class="<%=cssLinkTabletClassResourceExternal%>" target="_blank">
							<img class="dl-file-icon" src="${themeDisplay.pathThemeImages}/file_system/small/<%=documentVersion.getIcon()%>.png" />
							<liferay-ui:message key="resourceexternalactivity.downloadFile"  arguments='<%=new Object[]{HtmlUtil.escape(documentVersion.getTitle())} %>' />
						</a>
					</span>
				</div>
			<%} %>
		</div>
	</c:if>
</div>