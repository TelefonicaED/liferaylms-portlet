<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.asset.NoSuchEntryException"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<div class="container-activity">
<%
long actId=ParamUtil.getLong(request,"actId",0);

if(actId==0 )
{
	
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{

	LearningActivity learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	
	%>
		<h2 class="description-title"><%=learnact.getTitle(themeDisplay.getLocale())%></h2>
		<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
		<div class="description"><%=learnact.getDescription(themeDisplay.getLocale()) %></div>
		
	<%
	
	if(learnact.getTypeId()!=2 )
	{
		
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		if(!LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
		{
			if(!permissionChecker.hasPermission(learnact.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE) ||
					!permissionChecker.hasOwnerPermission(learnact.getCompanyId(), LearningActivity.class.getName(), actId, learnact.getUserId(), ActionKeys.UPDATE)){
			
				ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
	
				LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				learningTry.setEndDate(new java.util.Date(System.currentTimeMillis()));
				learningTry.setResult(100);
				LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);
			}

		}
		if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
		{
			
			if(Validator.isNumber(learnact.getExtracontent()))
			{
				long entryId=Long.valueOf(learnact.getExtracontent());
					AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
					AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
					AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
					String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
				
					if(entry.getClassName().equals(DLFileEntry.class.getName()))
					{
						
						FileEntry fileEntry=DLAppLocalServiceUtil.getFileEntry(entry.getClassPK());
						long fileEntryId = fileEntry.getFileEntryId();
						long folderId = fileEntry.getFolderId();
						String extension = fileEntry.getExtension();
						String title = fileEntry.getTitle();
	
						FileVersion fileVersion = fileEntry.getFileVersion();
						long fileEntryTypeId = 0;
						if (fileVersion.getModel() instanceof DLFileVersion) {
							DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();
	
							fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
						}
						boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
						boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
						boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
						boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);
						int previewFileCount = 0;
						String previewFileURL = null;
						String[] previewFileURLs = null;
						String videoThumbnailURL = null;
	
						String previewQueryString = null;
	
						if (hasAudio) {
							previewQueryString = "&audioPreview=1";
						}
						else if (hasImages) {
							previewQueryString = "&imagePreview=1";
						}
						else if (hasPDFImages) {
							previewFileCount = PDFProcessorUtil.getPreviewFileCount(fileVersion);
	
							previewQueryString = "&previewFileIndex=";
	
							previewFileURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString);
						}
						else if (hasVideo) {
							previewQueryString = "&videoPreview=1";
	
							videoThumbnailURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1");
						}
	
						if (Validator.isNotNull(previewQueryString)) {
							if (hasAudio) {
								previewFileURLs = new String[PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS).length];
	
								for (int i = 0; i < PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS).length; i++) {
									previewFileURLs[i] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString + "&type=" + PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS)[i]);
								}
							}
							else if (hasVideo) {
								if (PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length > 0) {
									previewFileURLs = new String[PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length];
	
									for (int i = 0; i <PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length; i++) {
										previewFileURLs[i] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString + "&type=" + PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS)[i]);
									}
								}
								else {
									previewFileURLs = new String[1];
	
									previewFileURLs[0] = videoThumbnailURL;
								}
							}
							else {
								previewFileURLs = new String[1];
	
								previewFileURLs[0] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString);
							}
	
							previewFileURL = previewFileURLs[0];
	
							if (!hasPDFImages) {
								previewFileCount = 1;
							}
						}
	
						request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(hasAudio));
						request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(hasVideo));
	
						request.setAttribute("view_file_entry.jsp-previewFileURLs", previewFileURLs);
						request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
						%>
						<c:choose>
								<c:when test="<%= previewFileCount == 0 %>">
									<c:if test="<%= AudioProcessorUtil.isAudioSupported(fileVersion) || ImageProcessorUtil.isImageSupported(fileVersion) || PDFProcessorUtil.isDocumentSupported(fileVersion) || VideoProcessorUtil.isVideoSupported(fileVersion) %>">
										<div class="portlet-msg-info">
											<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="<%= hasAudio %>">
											<div class="lfr-preview-audio" id="<portlet:namespace />previewFile">
												<div class="lfr-preview-audio-content" id="<portlet:namespace />previewFileContent"></div>
											</div>
	
											<liferay-util:include page="/html/portlet/document_library/player.jsp" />
										</c:when>
										<c:when test="<%= hasImages %>">
											<div class="lfr-preview-file lfr-preview-image" id="<portlet:namespace />previewFile">
												<div class="lfr-preview-file-content lfr-preview-image-content" id="<portlet:namespace />previewFileContent">
													<div class="lfr-preview-file-image-current-column">
														<div class="lfr-preview-file-image-container">
															<img class="lfr-preview-file-image-current" src="<%= previewFileURL %>" />
														</div>
													</div>
												</div>
											</div>
										</c:when>
										<c:when test="<%= hasVideo %>">
											<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace />previewFile">
												<div class="lfr-preview-file-content lfr-preview-video-content">
													<div class="lfr-preview-file-video-current-column">
														<div id="<portlet:namespace />previewFileContent"></div>
													</div>
												</div>
											</div>
	
											<liferay-util:include page="/html/portlet/document_library/player.jsp" />
										</c:when>
										<c:otherwise>
											<div class="lfr-preview-file" id="<portlet:namespace />previewFile">
												<div class="lfr-preview-file-content" id="<portlet:namespace />previewFileContent">
													<div class="lfr-preview-file-image-current-column">
														<div class="lfr-preview-file-image-container">
															<img class="lfr-preview-file-image-current" id="<portlet:namespace />previewFileImage" src="<%= previewFileURL + "1" %>" />
														</div>
														<span class="lfr-preview-file-actions aui-helper-hidden" id="<portlet:namespace />previewFileActions">
															<span class="lfr-preview-file-toolbar" id="<portlet:namespace />previewToolbar"></span>
	
															<span class="lfr-preview-file-info">
																<span class="lfr-preview-file-index" id="<portlet:namespace />previewFileIndex">1</span> of <span class="lfr-preview-file-count"><%= previewFileCount %></span>
															</span>
														</span>
													</div>
	
													<div class="lfr-preview-file-images" id="<portlet:namespace />previewImagesContent">
														<div class="lfr-preview-file-images-content"></div>
													</div>
												</div>
											</div>
	
											<aui:script use="aui-base,liferay-preview">
												new Liferay.Preview(
													{
														actionContent: '#<portlet:namespace />previewFileActions',
														baseImageURL: '<%= previewFileURL %>',
														boundingBox: '#<portlet:namespace />previewFile',
														contentBox: '#<portlet:namespace />previewFileContent',
														currentPreviewImage: '#<portlet:namespace />previewFileImage',
														imageListContent: '#<portlet:namespace />previewImagesContent',
														maxIndex: <%= previewFileCount %>,
														previewFileIndexNode: '#<portlet:namespace />previewFileIndex',
														toolbar: '#<portlet:namespace />previewToolbar'
													}
												).render();
											</aui:script>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>			<%
					}
					else
					{
				%>
					<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
					<%
					}
			}
			else
			{

			Document document = SAXReaderUtil.read(learnact.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			if(video!=null)
			{
				if(!video.attributeValue("id","").equals(""))
				{
				AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
				DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
				DLFileVersion videofileVersion = videofile.getFileVersion();
				String videoURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + videofileVersion.getGroupId() + StringPool.SLASH + videofileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(videofileVersion.getTitle()));
				
				%>
			<div class="video">
				
 	<embed type="application/x-shockwave-flash" src="<%=request.getContextPath()%>/flash/flvplayer/playervideo.swf" 
			  width="560" height="315" style="undefined" id="cab" name="cab" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" allowscriptaccess="always" wmode="transparent" menu="false" 
			  flashvars="file=<%=videoURL%>&type=flv" />
			  <%-- 
 			<video width="320" height="240" controls="controls">
  					<source src="<%=videoURL %>" type="video/mp4">
  					</video>
--%>
		</div>
				<%
				}
				else
				{
					%>
					<div class="video">
					<%=video.getText()%>
					</div>
					<%
				}
			}
			%>
			<div class="container_files">
			<%
			Element documento=null;
			int i = 0;
			do{
				String documentt = "document";
				if(i>0){
					documentt = documentt+(i-1);
				}
			
				documento=root.element(documentt);
				if(documento!=null)
				{
					if(!documento.attributeValue("id","").equals(""))
					{
					try{	
						AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
						DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
						DLFileVersion docfileVersion = docfile.getFileVersion();
						
						String docURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + docfileVersion.getGroupId() + StringPool.SLASH + docfileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(docfileVersion.getTitle()));
						%>
						<div class="row_file">
							<span class="upfile"><a href="<%=docURL%>" target="_blank"><img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= docfileVersion.getIcon() %>.png" /><liferay-ui:message key="resourceexternalactivity.downloadFile"  arguments="<%=new Object[]{HtmlUtil.escape(docfileVersion.getTitle())} %>" /></a></span>
						</div>
						<%
					}catch(NoSuchEntryException nsee){}
					}
				}
				i++;
			}while(documento!=null);

			%>
			</div>
			<%
			
			}
		}
	}
}
%>
</div>