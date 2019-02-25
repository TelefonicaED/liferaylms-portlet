<%@ include file="/init.jsp" %>

<liferay-ui:error key="coursetypeadmin.error-file-size" message="coursetypeadmin.error-file-size" />
<liferay-ui:error key="coursetypeadmin.error-image-size" message="coursetypeadmin.error-image-size" />
<liferay-ui:error key="coursetypeadmin.error-uploading-icon-image" message="coursetypeadmin.error-uploading-icon-image" />
<liferay-ui:error key="coursetypeadmin.error.add-new-coursetype" message="coursetypeadmin.error.add-new-coursetype" />
<liferay-ui:error key="coursetypeadmin.error.update-coursetype" message="coursetypeadmin.error.update-coursetype" />

<aui:form name="savecoursetypefm" action="${saveCourseTypeURL }"  method="post" role="form" enctype="multipart/form-data">
	<aui:input type="hidden" name="courseTypeId" value="${courseType.courseTypeId }"/>
	
	<div>
		<span class="aui-field-label"><liferay-ui:message key="name"/></span>
			<liferay-ui:input-localized
				formName="savecoursetypefm"
		 		name="courseTypeName"
		 		xml="${courseType.name }"
		 		required="true">
		 	</liferay-ui:input-localized> 
	 </div>
	 
	 <div>
		<span class="aui-field-label"><liferay-ui:message key="description"/></span>
			<liferay-ui:input-localized
				formName="savecoursetypefm"
		 		name="courseTypeDescription"
		 		xml="${courseType.description }">
		 	</liferay-ui:input-localized> 
	 </div>
	 
	 <div>
		 <aui:select required="true" label="course-template" name="templateIds" multiple="true" helpMessage="${not empty courseType.courseTemplateIds ? 'coursetypeadmin.coursetype-has-templates.only-add' : ''}" ignoreRequestValue="true">
			<c:forEach items="${listTemplates }" var="template">
				<aui:option 
					value="${template.layoutSetPrototypeId }" 
					selected="${not empty courseType.courseTemplateIds and courseType.courseTemplateIds.contains(template.layoutSetPrototypeId ) }"
					disabled="${not empty courseType.courseTemplateIds and courseType.courseTemplateIds.contains(template.layoutSetPrototypeId ) }">
						${template.getName(themeDisplay.locale) }
				</aui:option>
			</c:forEach>
		</aui:select>
		
		<aui:select required="true" label="course-correction-method" name="courseEvalIds" multiple="true"  helpMessage="${not empty courseType.courseEvalTypeIds ? 'coursetypeadmin.coursetype-has-course-eval-types.only-add' : ''}" ignoreRequestValue="true">
			<c:forEach items="${listCourseEvals }" var="courseEval">
				<aui:option
					value="${courseEval.typeId }"
					selected="${not empty courseType.courseEvalTypeIds and courseType.courseEvalTypeIds.contains(courseEval.typeId ) }"
					disabled="${not empty courseType.courseEvalTypeIds and courseType.courseEvalTypeIds.contains(courseEval.typeId ) }">
						<liferay-ui:message key="${courseEval.name }" />
				</aui:option>
			</c:forEach>
		</aui:select>
	</div>
	
	<div>
		<aui:select required="true" label="lms-activities" name="learningActivityTypeIds" multiple="true"  helpMessage="${not empty courseType.courseEvalTypeIds ? 'coursetypeadmin.coursetype-has-learning-activity-types.only-add' : ''}" ignoreRequestValue="true">
			<c:forEach items="${listLearningActivityTypes }" var="activityType">
				<aui:option
					value="${activityType.typeId }"
					selected="${not empty courseType.learningActivityTypeIds and courseType.learningActivityTypeIds.contains(activityType.typeId ) }"
					disabled="${not empty courseType.learningActivityTypeIds and courseType.learningActivityTypeIds.contains(activityType.typeId ) }">
						<liferay-ui:message key="${activityType.name }" />
				</aui:option>
			</c:forEach>
		</aui:select>
	</div>
	
	<div>
		<aui:select required="true" label="inscription-type" name="inscriptionTypeIds" multiple="true"  helpMessage="${not empty courseType.inscriptionTypeIds ? 'coursetypeadmin.coursetype-has-inscription-types.only-add' : ''}" ignoreRequestValue="true">
			<c:forEach items="${listInscriptionTypes }" var="inscriptionType">
				<aui:option
					value="${inscriptionType.typeId }"
					selected="${not empty courseType.inscriptionTypeIds and courseType.inscriptionTypeIds.contains(inscriptionType.typeId ) }"
					disabled="${not empty courseType.inscriptionTypeIds and courseType.inscriptionTypeIds.contains(inscriptionType.typeId ) }">
						${inscriptionType.getTitle(themeDisplay.locale) }
				</aui:option>
			</c:forEach>
		</aui:select>
	</div>
	
	<div>
		<aui:select required="true" label="calificationType" name="calificationTypeIds" multiple="true"  helpMessage="${not empty courseType.calificationTypeIds ? 'coursetypeadmin.coursetype-has-calification-types.only-add' : ''}" ignoreRequestValue="true">
			<c:forEach items="${listCalificationTypes }" var="calificationType">
				<aui:option
					value="${calificationType.typeId }"
					selected="${not empty courseType.calificationTypeIds and courseType.calificationTypeIds.contains(calificationType.typeId ) }"
					disabled="${not empty courseType.calificationTypeIds and courseType.calificationTypeIds.contains(calificationType.typeId ) }">
						<liferay-ui:message key="${calificationType.getTitle(themeDisplay.locale) }"/>
				</aui:option>
			</c:forEach>
		</aui:select>
	</div>
	
	<div>
		<aui:input label="icon" name="iconCourseTypeFile" type="file" value="">
			<aui:validator name="acceptFiles">'jpg, png, jpeg'</aui:validator>
		</aui:input>
		<c:if test="${not empty courseType.getIconCourseTypeURL(themeDisplay) }">
			<div>
				<img src="${courseType.getIconCourseTypeURL(themeDisplay) }"/>
				<aui:input name="deleteIcon" label="delete" type="checkbox" value=""/>
			</div>
		</c:if>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="save"></aui:button>
		<aui:button type="button" value="cancel" onClick="${renderURL }"/>
	</aui:button-row>
</aui:form>
