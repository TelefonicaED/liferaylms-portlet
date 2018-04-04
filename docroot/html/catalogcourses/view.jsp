<%@include file="/init.jsp" %> 

<jsp:useBean id="assetCategoryServiceUtil" class="com.liferay.portlet.asset.service.AssetCategoryServiceUtil"/>

<aui:form action="${renderURL } " method="POST" name="fm"> 
	<aui:input name="categoryId" value="" type="hidden"/>
	<aui:input name="tagId" value="" type="hidden"/>
	<aui:input name="categoryIds" value="${categoryIds }" type="hidden"/>
	<aui:input name="tagIds" value="${tagIds }" type="hidden"/>
	<aui:fieldset>
		<aui:input inlineField="true" label="" name="freetext" size="30" value="" />

		<aui:button align="absmiddle" border="0" name="search" onClick='${renderResponse.getNamespace()}search(0,0)' src='${themeDisplay.pathThemeImages}/common/search.png' title="search" type="image" />

		<portlet:renderURL copyCurrentRenderParameters="false" var="clearSearchURL">
		</portlet:renderURL>

		<a class="clean-search" href="${clearSearchURL}"><liferay-ui:message key="clear-search"></liferay-ui:message></a>
	</aui:fieldset>
	
	<div class="lfr-token-list" id="<portlet:namespace />searchTokens">
		<div class="lfr-token-list-content" id="<portlet:namespace />searchTokensContent">
			<c:forEach items="${listCategoriesSelected }" var="category">
				<span class="lfr-token">
					<span class="lfr-token-text">${category.getTitle(themeDisplay.locale) }</span>

					<aui:a cssClass="aui-icon aui-icon-close lfr-token-close" href="javascript:${renderResponse.getNamespace()}deleteCategory(${category.categoryId })" />
				</span>
			</c:forEach>
			<c:forEach items="${listTagsSelected }" var="tag">
				<span class="lfr-token">
					<span class="lfr-token-text">${tag.name }</span>

					<aui:a cssClass="aui-icon aui-icon-close lfr-token-close" href="javascript:${renderResponse.getNamespace()}deleteTag(${tag.tagId })" />
				</span>
			</c:forEach>
		</div>
	</div>
	
	<aui:layout cssClass='search-layout menu-column'>
		<aui:column cssClass="menu aui-w25" first="true" id="facetNavigation">
			<c:if test="${not empty listVocabulary}">
	
				<div class="asset-vocabulary search-facet search-asset_vocabulary" data-facetFieldName="assetCategoryIds" >
					<aui:input name="assetCategoryIds" type="hidden" value="" />
					<ul class="lfr-component tag-list"> 
						<li class="facet-value default current-term">
							<img alt="" src="${themeDisplay.getPathThemeImages()}/common/category.png" /><liferay-ui:message key="category" />
						</li>
						<li></li>
						<c:forEach items="${listVocabulary }" var="vocabulary">
							<c:set var="listCategories" value="${assetCategoryServiceUtil.getVocabularyCategories(vocabulary.vocabularyId, -1, -1, null) }"/>
							<c:if test="${not empty listCategories }">
								<li>
									<liferay-ui:panel id="panel${vocabulary.vocabularyId }" collapsible="true" defaultState="collapsed" extended="false" title="${vocabulary.getTitle(locale)}">
										<div class="search-asset-vocabulary-list-container">
											<ul class="lfr-component search-asset-vocabulary-list">
												<c:forEach items="${listCategories }" var="category">
													<c:set var="classFacet" value="facet-value" />
													<c:set var="urlFacet" value="javascript:${renderResponse.getNamespace()}search(${category.categoryId },0)" />
													<c:if test="${fn:contains(categoryIds, category.categoryId)}">
														<c:set var="classFacet" value="facet-value current-term" />
														<c:set var="urlFacet" value="#" />
													</c:if>
													<li class="${classFacet }">
														<c:choose>
															<c:when test="${empty categoryCourses.get(category.categoryId)}">
																${category.getTitle(locale) } (0)
															</c:when>
															<c:otherwise>
																<a href="${urlFacet }" data-value="${category.categoryId }">
																${category.getTitle(locale) } (${categoryCourses.get(category.categoryId)}) 
																</a>
															</c:otherwise>
														</c:choose>																													
														<span class="frequency"></span>	
													</li>
												</c:forEach>										
											</ul>
										</div>
									</liferay-ui:panel>
								</li>
							</c:if>
						</c:forEach>	
					</ul>		
				</div>
			</c:if>
			<c:if test="${not empty listAssetTag}">
	
				<div class="asset-tags search-facet search-asset_tags" data-facetFieldName="assetTagNames" >
					<aui:input name="assetTagIds" type="hidden" value="" />
					<ul class="lfr-component tag-list"> 
						<li class="facet-value default current-term">
							<img alt="" src="${themeDisplay.getPathThemeImages()}/common/tag.png" /><liferay-ui:message key="tag" />
						</li>
						<c:forEach items="${listAssetTag }" var="tag">
							<c:set var="classFacet" value="facet-value tag-popularity-1" />
							<c:set var="urlFacet" value="javascript:${renderResponse.getNamespace()}search(0,${tag.tagId })" />
							<c:if test="${fn:contains(tagIds, tag.tagId)}">
								<c:set var="classFacet" value="facet-value tag-popularity-1 current-term" />
								<c:set var="urlFacet" value="#" />
							</c:if>
							<li class="${classFacet } ">
									<c:choose>
										<c:when test="${empty tagCourses.get(tag.tagId)}">
											${tag.name } (0)
										</c:when>
										<c:otherwise>
											<a href="${urlFacet }" data-value="${tag.name }">
												${tag.name } (${tagCourses.get(tag.tagId)}) 
											</a>
										</c:otherwise>
									</c:choose>
								<span class="frequency"></span>	
							</li>
						</c:forEach>	
					</ul>		
				</div>
			</c:if>

		</aui:column>
		<aui:column cssClass="aui-w1 portlet-column toggle-show" id="searchToggle"></aui:column>
		<aui:column cssClass="result aui-w75" last="true">
			<liferay-ui:search-container 
				searchContainer="${searchContainer}"
				iteratorURL="${searchContainer.iteratorURL}">
				
				<liferay-ui:search-container-results 
					total="${searchContainer.total }" 
					results="${searchContainer.results }"
				/>
			
				<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">

					<liferay-ui:search-container-column-text name="">
			
						<span class="asset-entry"> 
							<span class="asset-entry-type"> </span> 
							<span class="asset-entry-title"> 
								<a href="${themeDisplay.portalURL}/${themeDisplay.locale.language}/web/${course.group.friendlyURL}" >${course.getTitle(themeDisplay.locale) } </a> 
							</span> 
							<div class="asset-entry-content"> 
								<div class="asset-resource-image"> 
									<img class="courselogo" src="${course.getImageURL(themeDisplay) }" alt="<%= course.getTitle(themeDisplay.getLocale()) %>"> 
								</div> 
								<c:set var="descripton" value="${course.group.description }" />
								<c:if test="${fn:length(description) > 200 }">
									<c:set var="description" value="${fn:substring(description, 0, 200) }..."/>
								</c:if>
								<p></p> 
								<div class="asset-entry-ratings"> 
									<liferay-ui:ratings-score
										score="${course.averageScore }"
									/> 
									<c:if test="${not empty course.assetCategoryIds }">
										<div class="asset-entry-categories"> 
											<span><liferay-ui:message key="categories" />:</span> 
											<div class="taglib-asset-categories-summary"> 
												<c:forEach items="${course.assetCategoryIds }" var="category">
													<a class="asset-category" href="javascript:${renderResponse.getNamespace()}search(${category.categoryId },0)"> ${category.getTitle(themeDisplay.locale) } </a>
												</c:forEach>  
											</div> 
										</div> 
									</c:if>
									<c:if test="${not empty course.assetTagIds }">
										<div class="asset-entry-tags" > 
											<label><liferay-ui:message key="tags" />:</label> 
											<div class="taglib-asset-tags-summary"> 
												<c:forEach items="${course.assetTagIds}" var="tag">
													<a class="tag" href="javascript:${renderResponse.getNamespace()}search(0,${tag.tagId })">${tag.name }</a> 
												</c:forEach>
											</div> 
										</div>
									</c:if>
								</div>  
							</div>
						</span>
					
					</liferay-ui:search-container-column-text>
					
				</liferay-ui:search-container-row>
				
				<liferay-ui:search-iterator />
			
			</liferay-ui:search-container>
		</aui:column>
	</aui:layout>

</aui:form>

<portlet:resourceURL var="getCourses">
	<portlet:param name="action" value="getCourses" />
</portlet:resourceURL>

<script type="text/javascript">
	function <portlet:namespace />search(categoryId, tagId){
		document.<portlet:namespace />fm.<portlet:namespace />categoryId.value = categoryId;
		document.<portlet:namespace />fm.<portlet:namespace />tagId.value = tagId;
		document.<portlet:namespace />fm.submit();
	}
	
	function <portlet:namespace />deleteCategory(categoryId){

		var fieldValues = document.<portlet:namespace />fm.<portlet:namespace />categoryIds.value.split(",");

		var fieldValuesAux = "";
		
		for(i = 0; i < fieldValues.length; i++){
			if(categoryId != fieldValues[i]){
				fieldValuesAux += fieldValues[i] + ",";
			}
		}
		
		if(fieldValuesAux.length > 0) fieldValuesAux = fieldValuesAux.substring(0, fieldValuesAux.length-1);
		
		document.<portlet:namespace />fm.<portlet:namespace />categoryIds.value = fieldValuesAux;
		
		document.<portlet:namespace />fm.submit();

	}
	
	function <portlet:namespace />deleteTag(tagId){

		var fieldValues = document.<portlet:namespace />fm.<portlet:namespace />tagIds.value.split(",");

		var fieldValuesAux = "";
		
		for(i = 0; i < fieldValues.length; i++){
			if(tagId != fieldValues[i]){
				fieldValuesAux += fieldValues[i] + ",";
			}
		}
		
		if(fieldValuesAux.length > 0) fieldValuesAux = fieldValuesAux.substring(0, fieldValuesAux.length-1);
		
		document.<portlet:namespace />fm.<portlet:namespace />tagIds.value = fieldValuesAux;
		
		document.<portlet:namespace />fm.submit();

	}
	
	

</script>

<aui:script use="liferay-token-list">
	Liferay.namespace('Search').tokenList = new Liferay.TokenList(
		{
			boundingBox: '#<portlet:namespace />searchTokens',
			contentBox: '#<portlet:namespace />searchTokensContent'
		}
	).render();

	
</aui:script>

<aui:script>
	AUI().use('autocomplete-list','aui-base','aui-io-request','autocomplete-filters','autocomplete-highlighters',function (A) {
		var testData;
		new A.AutoCompleteList({
			enableCache: 'true',
			activateFirstItem: 'false',
			inputNode: '#<portlet:namespace />freetext',
			resultTextLocator:'courseTitle',
			render: 'true',
			resultHighlighter: 'phraseMatch',
			maxResults: 0,
			minQueryLength: 4,
			source:function(){
				var inputValue=A.one("#<portlet:namespace />freetext").get('value');
				var myAjaxRequest=A.io.request('${getCourses }',{
					dataType: 'json',
					method:'POST',
					data:{
						<portlet:namespace />courseTitle:inputValue,
					},
					autoLoad:false,
					sync:false,
					on: {
						success:function(){
							var data=this.get('responseData');
							testData=data;
						}
					}
				});
				myAjaxRequest.start();
				return testData;
			},
		});
	});
	
</aui:script>
	