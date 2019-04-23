<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@ include file="/init-min.jsp" %>

<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/mediaelementplayer.min.css">


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

 <portlet:resourceURL var="finishTryURL" id="finishTry" />

<c:choose>
	<c:when test="${isDLFileEntry }">
		<div class="video">
			
				<embed type="application/x-shockwave-flash" src="${request.contextPath}/flash/flvplayer/playervideo.swf" 
		  			width="560" height="315" style="undefined" id="cab" name="cab" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" allowscriptaccess="always" wmode="transparent" menu="false" 
		  			flashvars="file=${video}&type=flv" />
		</div>
		<c:if test="${!hasPermissionAccessCourseFinished }">
			<script>
				var unloadEvent = function (e) {
					console.log("unloadEvent video dlfileentry ");
					<portlet:namespace/>finishTry(100,0,0);												
				  };
				  window.addEventListener("beforeunload", unloadEvent);	
			</script>
		</c:if>
	</c:when>
	<c:when test="${not empty mimeType }">
		<%@ include file="/html/resourceExternalActivity/mediaelement.jsp" %>
	</c:when>
	<c:otherwise>
		<div class="video">
			${video}
		</div>
		<c:if test="${!hasPermissionAccessCourseFinished }">
			<script>
				var unloadEvent = function (e) {
					console.log("unloadEvent otherwise");
					<portlet:namespace/>finishTry(100,0,0);												
				  };
				  window.addEventListener("beforeunload", unloadEvent);	
			</script>
		</c:if>
	</c:otherwise>
</c:choose>
	<c:if test="<%=listDocuments != null && listDocuments.size() > 0%>">
		<div class="container_files">
			<%for(FileVersion documentVersion: listDocuments){ %>
				<div class="row_file">
					<span class="upfile">
						<a href='<%=DLUtil.getPreviewURL(documentVersion.getFileEntry(), documentVersion, themeDisplay, "")%>' class="<%=cssLinkTabletClassResourceExternal%>" target="_blank">
							<img class="dl-file-icon" src="${themeDisplay.pathThemeImages}/file_system/small/<%=documentVersion.getIcon()%>.png" />
							<%=HtmlUtil.escape(documentVersion.getTitle()) %>
						</a>
					</span>
				</div>
			<%} %>
		</div>
	</c:if>
	
	<div class="container-activity isFeedback aui-helper-hidden" id="${renderResponse.getNamespace()}videoQuestionFeedback">
	</div>
	
</div>

<c:if test="${!hasPermissionAccessCourseFinished }">
	<script>
		function <portlet:namespace/>finishTry(score,position,plays){
			$.ajax({
					dataType: 'json',
					url: '${finishTryURL}',
				    cache:false,
					data: {
						actId: '${activity.actId}',
						latId: '${latId}',
						score: score,
						position: position,
						plays: plays
					},
					success: function(data){
						
						if(data.questionCorrection){
							if(data.finalFeedback){
								$('#<portlet:namespace/>videoQuestionFeedback').removeClass("aui-helper-hidden");
								$('#<portlet:namespace/>videoQuestionFeedback').html(data.feedback);	
							}
						}
						
					},
					error: function(){
						console.log("ERROR");
					}
				});
		}
	</script>
</c:if>

