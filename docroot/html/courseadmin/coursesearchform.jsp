<!-- <h1 class="taglib-categorization-filter entry-title"> -->
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.lms.model.Course"%>

<%@page import="java.util.Calendar"%>

<%

String tags = ParamUtil.getString(request, "tags","");

long[] catIds= (long[])request.getAttribute("catIds");
long catId = ParamUtil.getLong(request, "catId");
%>

<c:if test="${noAssetCategoryIds }">
	<%for(long cat:catIds){
		if(cat!=0)
		{
		AssetCategory categ=AssetCategoryLocalServiceUtil.getAssetCategory(cat);
		
			String cats=StringUtil.merge(ArrayUtil.remove(catIds, cat));
		    if(cats==null || cats.equals(""))
		    {
		    	cats="0";
		    }
		%>
		<liferay-portlet:renderURL var="choseCatURL" >
			<liferay-portlet:param name="categoryIds" value="<%=cats %>"/>
			</liferay-portlet:renderURL>
		<span class="asset-entry">
		<%=categ.getTitle(locale) %>
		<a href="${choseCatURL}" title="<liferay-ui:message key="remove" />">
					<span class="aui-icon aui-icon-close aui-textboxlistentry-close"></span>
				</a>
		</span>
		<%
		}
	}
	%>
</c:if>
</h1>
<%
if(catIds==null ||catIds.length==0)
{
	if(catId!=0)
	{
		catIds=new long[]{catId};
	}
}
if(catIds!=null&&catIds.length>0)
{
	catIds=ArrayUtil.remove(catIds, 0l);
}

%>

<portlet:renderURL var="searchURL">
	<portlet:param name="view" value="${view }"></portlet:param>
	<portlet:param name="courseId" value="${courseId}"></portlet:param>	
</portlet:renderURL>

<div class="admin-course-search-form search-advanced">
	<aui:form action="${searchURL}" method="post" id="searchCourses" name="searchCourses" role="search">
	<%--<aui:form action="${searchURL}" method="post" name="searchCourses" role="search">--%>
		<aui:input name="courseId" value="${courseId }" type="hidden" />
		<aui:fieldset cssClass="checkBoxes">
			<aui:input name="search" type="hidden" value="search" />
			<aui:input inlineField="true" name="freetext" type="text" value="${freetext}" >
				<aui:validator name="maxLength">150</aui:validator>
			</aui:input>
			<c:if test="${empty courseId }">
			<aui:input inlineField="true" name="findInEditions" label="course-admin.findInEditions" type="checkbox" value="${findInEditions}" helpMessage="help.course-admin.findInEditions" />
			</c:if>
			<aui:select inlineField="true" name="state">
				<aui:option label="active" selected="${state == STATUS_APPROVED}" value="${STATUS_APPROVED}" />
				<aui:option label="inactive" selected="${state == STATUS_INACTIVE}" value="${STATUS_INACTIVE}" />
				<aui:option label="any-status" selected="${state == STATUS_ANY}" value="${STATUS_ANY}" />
			</aui:select>
						
		</aui:fieldset>
		
		<c:if test="${renderRequest.preferences.getValue('executionDates', 'false') }">
			<aui:fieldset cssClass="executionDates" >
				<div class="startDatePnl">
					<aui:input helpMessage="help.course-admin.start-execution-date" name="startDateFilter" label="course-admin.start-execution-date" type="checkbox" onChange="javascript:showStartDate('startDateFilterCheckbox');"/>
					<div id="startDateFilterDiv" class="aui-helper-hidden">
						<aui:field-wrapper>
							<liferay-ui:input-date
						           monthParam="dateMonthStart"
						           monthNullable="<%=Boolean.FALSE%>"
						           monthValue="${dateMonthStart}"
						           dayNullable="<%=Boolean.FALSE%>"
						           dayParam="dateDayStart"
						           dayValue="${dateDayStart}"
						           yearParam="dateYearStart"
						           yearNullable="<%=Boolean.FALSE%>"
						           yearValue="${dateYearStart}"
						           yearRangeStart="${yearRangeStart}"
						           yearRangeEnd="${yearRangeEnd}"
						         	cssClass="date-input"
						         	firstDayOfWeek="${firstDayOfWeek}"
						      	/>	
						</aui:field-wrapper>
					</div>
				</div>
				
				<div class="endDatePnl">
					<aui:input helpMessage="help.course-admin.end-execution-date" name="endDateFilter" label="course-admin.end-execution-date" type="checkbox" onChange="javascript:showEndDate('endDateFilterCheckbox');"/>	
				    <div id="endDateFilterDiv" class="aui-helper-hidden">
				     	<aui:field-wrapper>			
							<liferay-ui:input-date
					           monthParam="dateMonthEnd"
					           monthNullable="<%=Boolean.FALSE%>"
					           monthValue="${dateMonthEnd}"
					           dayNullable="<%=Boolean.FALSE%>"
					           dayParam="dateDayEnd"
					           dayValue="${dateDayEnd}"
					           yearParam="dateYearEnd"
					           yearNullable="<%=Boolean.FALSE%>"
					           yearValue="${dateYearEnd}"
					           yearRangeStart="${yearRangeStart}"
					           yearRangeEnd="${yearRangeEnd}"
					         	cssClass="date-input"
					         	firstDayOfWeek="${firstDayOfWeek}"
					      	/>	
						</aui:field-wrapper>
					</div>
				</div>	
			</aui:fieldset>
		</c:if>
		
		
		<c:if test="${renderRequest.preferences.getValue('showGroupFilter', 'false') && (empty courseId || courseId == 0)}">
			<aui:select name="selectedGroupId" label="courseadmin.search.select-group">
					<aui:option label="" value="0"/>
					<c:forEach items="${listGroups}"  var="courseGroup">
						<aui:option label="${courseGroup.name }" value="${courseGroup.groupId}"/>
					</c:forEach>
			</aui:select>
		</c:if>
		<c:if test="${renderRequest.preferences.getValue('showSearchTags', 'false') }">
			<liferay-ui:panel id="panel_tags" title="tags" collapsible="true" defaultState="closed">
				<liferay-ui:asset-tags-selector ></liferay-ui:asset-tags-selector>
			</liferay-ui:panel>
		</c:if>
		<c:if test="${renderRequest.preferences.getValue('categories', 'false') }">
			<c:set var="defaultStateCat" value="open"/>
			<c:if test="<%=catIds == null || catIds.length <= 0%>">
				<c:set var="defaultStateCat" value="closed"/>
			</c:if>
			<liferay-ui:panel id="panel_categories" title="categorization" collapsible="true" defaultState="${defaultStateCat}">
				<aui:fieldset cssClass="checkBoxes">
					<liferay-ui:asset-categories-selector  className="<%= Course.class.getName() %>" curCategoryIds="${catIdsText}" />
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>
		<c:if test="${(renderRequest.preferences.getValue('showExpandos', 'false') && (empty courseId || courseId == 0)) ||
						(renderRequest.preferences.getValue('showExpandosEdition', 'false') && courseId > 0) }">
			<liferay-ui:panel id="panel_expando" title="custom-attributes" collapsible="true" defaultState="closed">
				<aui:fieldset cssClass="checkBoxes">
					<aui:select label="custom-attributes" name="columnId" onChange="javascript:${renderResponse.getNamespace()}changeExpando();">
						<aui:option value="0" label=""/>
						<c:forEach items="${listExpandos }" var="expando">
							<c:set var="expandoProperty" value="showExpando_${expando.columnId }" />
							<c:if test="${courseId > 0 }">
								<c:set var="expandoProperty" value="showExpandoEdition_${expando.columnId }" />
							</c:if>
							<c:if test="${renderRequest.preferences.getValue(expandoProperty, 'false')}">
								<aui:option value="${expando.columnId }" label="${expando.name }"/>
							</c:if>	
						</c:forEach>
					</aui:select>
					<aui:input name="expandoValue" value="${expandoValue }" label=""/>
					<script>
					function <portlet:namespace />changeExpando(){
						var columnValue = $('#<portlet:namespace />expandoValue').val("");
					}
					</script>
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>
		
		<c:choose>
		    <c:when test="${renderRequest.preferences.getValue('executionDates', 'false') }">
		      <aui:button type="submit" value="search" onClick="checkDates()"></aui:button>
		    </c:when>    
		    <c:otherwise>
		        <aui:button type="submit" value="search"></aui:button> 
		    </c:otherwise>
		</c:choose>
	</aui:form>
</div>
	
<portlet:resourceURL var="getCourses">
	<portlet:param name="action" value="getCourses" />
</portlet:resourceURL>
<aui:script>


function checkDates(){
 	var cb1 = document.getElementById("<portlet:namespace/>startDateFilterCheckbox");
 	var cb2 = document.getElementById("<portlet:namespace/>endDateFilterCheckbox");
 	if (!cb1.checked && !cb2.checked){
 		document.<portlet:namespace/>searchCourses.submit();
 	}else{ 	
		if ((cb1 != null && cb1.checked) || (cb2 != null && cb2.checked)) {
			document.<portlet:namespace/>searchCourses.submit();
		}
	}
}


function showStartDate(id){
	var namespace = "<portlet:namespace/>";
 	var cb = document.getElementById(namespace+id);
	if( cb != null && cb.checked) {
    	$('#startDateFilterDiv').removeClass('aui-helper-hidden');
	}else{
		$('#startDateFilterDiv').addClass('aui-helper-hidden');
	}
}

function showEndDate(id){
	var namespace = "<portlet:namespace/>";
 	var cb = document.getElementById(namespace+id);
	if(cb != null && cb.checked) {
    	$('#endDateFilterDiv').removeClass('aui-helper-hidden');
	}else{
		$('#endDateFilterDiv').addClass('aui-helper-hidden');
	}
}

showStartDate('startDateFilterCheckbox');
showEndDate('endDateFilterCheckbox');


	AUI().use('autocomplete-list','aui-base','aui-io-request','autocomplete-filters','autocomplete-highlighters',function (A) {
		var testData;
		var autoComplete = new A.AutoCompleteList({
			enableCache: 'true',
			inputNode: '#<portlet:namespace />freetext',
			resultTextLocator:'courseTitle',
			render: 'true',
			resultHighlighter: 'phraseMatch',
			maxResults: 0,
			minQueryLength: 4,
			source:function(){
				var inputValue=A.one("#<portlet:namespace />freetext").get('value');
				var stateValue=A.one('#<portlet:namespace />state').get('value');
				var groupId = 0;
				var columnId = 0;
				var expandoValue = null;
				if("${renderRequest.preferences.getValue('showGroupFilter', 'false') && (empty courseId || courseId == 0)}" == "true"){
					groupId = A.one('#<portlet:namespace />selectedGroupId').get('value');
				}
				if("${(renderRequest.preferences.getValue('showExpandos', 'false') && (empty courseId || courseId == 0)) ||(renderRequest.preferences.getValue('showExpandosEdition', 'false') && courseId > 0) }" == "true"){
					columnId = A.one('#<portlet:namespace />columnId').get('value');
					expandoValue = A.one('#<portlet:namespace />expandoValue').get('value');
				}
				
				var myAjaxRequest=A.io.request('${getCourses }',{
					dataType: 'json',
					method:'POST',
					data:{
						<portlet:namespace />courseTitle:inputValue,
						<portlet:namespace />status:stateValue,
						<portlet:namespace />selectedGroupId:groupId,
						<portlet:namespace />columnId:columnId,
						<portlet:namespace />expandoValue:expandoValue
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
				console.log("ejecutamos");
				myAjaxRequest.start();
				console.log("ejecutado");
				return testData;
			},
			on: {
				select: function(event) {
					A.one("#<portlet:namespace />freetext").set('value', event.result.raw.courseTitle);
					$('#<portlet:namespace />searchCourses').submit();
				}
			}
		});
	});
	
</aui:script>

