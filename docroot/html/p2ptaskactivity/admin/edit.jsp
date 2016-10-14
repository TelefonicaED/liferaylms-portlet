<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.learningactivity.TaskP2PLearningActivityType"%>
<%@ include file="/init.jsp"%>


<%
	long moduleId=ParamUtil.getLong(renderRequest,"resModuleId",0);

	boolean anonimous=false;
	boolean result=false;
	boolean disabled=true;
	boolean fileOptional = false;
	
	String dateUpload = "";

	SimpleDateFormat formatDay = new SimpleDateFormat("dd");
	formatDay.setTimeZone(themeDisplay.getTimeZone());
	SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
	formatMonth.setTimeZone(themeDisplay.getTimeZone());
	SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
	formatYear.setTimeZone(themeDisplay.getTimeZone());
	SimpleDateFormat formatHour = new SimpleDateFormat("HH");
	formatHour.setTimeZone(themeDisplay.getTimeZone());
	SimpleDateFormat formatMin = new SimpleDateFormat("mm");
	formatMin.setTimeZone(themeDisplay.getTimeZone());

	Date today = new Date(System.currentTimeMillis());

	int uploadDay = Integer.parseInt(formatDay.format(today));
	int uploadMonth = Integer.parseInt(formatMonth.format(today)) - 1;
	int uploadYear = Integer.parseInt(formatYear.format(today)) + 1;
	int uploadHour = Integer.parseInt(formatHour.format(today));
	int uploadMin = Integer.parseInt(formatMin.format(today));
	
	long numEvaluaciones = TaskP2PLearningActivityType.DEFAULT_VALIDATION_NUMBER;
	
	LearningActivity learningActivity = (LearningActivity)request.getAttribute("activity");	

	if(learningActivity!=null) {
		anonimous = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"anonimous"));
		result = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"result"));
		fileOptional = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"fileoptional"));
		String numEvaStr = LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"validaciones");
		numEvaluaciones = numEvaStr.equals("") ? TaskP2PLearningActivityType.DEFAULT_VALIDATION_NUMBER : Long.parseLong(numEvaStr);
		fileOptional = StringPool.TRUE .equals(LearningActivityLocalServiceUtil .getExtraContentValue( learningActivity.getActId(), "fileoptional"));
		String validaciones = LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"validaciones");
		if (!validaciones.equals("") && Validator.isNumber(validaciones)) {
			numEvaluaciones = Long.parseLong(validaciones);
		}
		
		dateUpload = LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"dateupload");

		if (!dateUpload.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//dateFormat.setTimeZone(themeDisplay.getTimeZone());
			Date date = dateFormat.parse(dateUpload);
			uploadDay = Integer.parseInt(formatDay.format(date));
			uploadMonth = Integer.parseInt(formatMonth.format(date)) - 1;
			uploadYear = Integer.parseInt(formatYear.format(date));
			uploadHour = Integer.parseInt(formatHour.format(date));
			uploadMin = Integer.parseInt(formatMin.format(date));
		} else {
			try{
				uploadDay = Integer.parseInt(formatDay.format(learningActivity.getEnddate())) - 1;
				uploadMonth = Integer.parseInt(formatMonth.format(learningActivity.getEnddate())) - 1;
				uploadYear = Integer.parseInt(formatYear.format(learningActivity.getEnddate()));
				uploadHour = Integer.parseInt(formatHour.format(learningActivity.getEnddate()));
				uploadMin = Integer.parseInt(formatMin.format(learningActivity.getEnddate()));
			}catch(Exception e){}
		}
		
		moduleId=learningActivity.getModuleId();
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	}
	
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())){
		disabled = false;
	}

%>

<script type="text/javascript">
<!--

window.<portlet:namespace />validate_p2ptaskactivity={
		rules:
		{
			<portlet:namespace />numValidaciones: {
				required: true,
				number: true,
				range: [0,100]
			}
		},
		fieldStrings:
		{
			<portlet:namespace />numValidaciones: {
        		required: Liferay.Language.get("p2ptaskactivity.editActivity.numValidaciones.required"),
        		number: Liferay.Language.get("p2ptaskactivity.editActivity.numValidaciones.number"),
        		range: Liferay.Language.get("p2ptaskactivity.editActivity.numValidaciones.range")
            }
		}	
};

AUI().ready('node','event','aui-io-request','aui-parse-content','liferay-portlet-url', function(A) {
	A.one('#<portlet:namespace />resModuleId').on('change', function (e) {
		var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%=LiferayWindowState.EXCLUSIVE.toString()%>');
			renderUrl.setPortletId('<%=portletDisplay.getId()%>');
			renderUrl.setParameter('jspPage','/html/p2ptaskactivity/admin/editModule.jsp');
			renderUrl.setParameter('resModuleId',e.target.getDOM().options[e.target.getDOM().selectedIndex].value);
		A.io.request(renderUrl.toString(), {
		   dataType: 'json',
	       on: {  
	      		success: function() {  
	      			var notEditable = !!this.get('responseData')['notEditable'];
	      			A.one('#<portlet:namespace />anonimousCheckbox').set('disabled',notEditable);
	      			<% if(!disabled){ %>
	      				A.one('#<portlet:namespace />resultCheckbox').set('disabled',notEditable);
	      				A.one('#<portlet:namespace />numValidaciones').set('disabled',notEditable);
	      			<% } %>
	      			if(notEditable) {
	      				A.one('#<portlet:namespace />anonimous').set('value','<%=Boolean.toString(anonimous)%>');
		      			A.one('#<portlet:namespace />anonimousCheckbox').set('checked',<%=Boolean.toString(anonimous)%>);
		      			<% if(!disabled){ %>
		      				A.one('#<portlet:namespace />result').set('value','<%=Boolean.toString(result)%>');
		      				A.one('#<portlet:namespace />resultCheckbox').set('checked',<%=Boolean.toString(result)%>);
		      				A.one('#<portlet:namespace />numValidaciones').set('value','<%=Long.toString(numEvaluaciones)%>');
		      			<% } %>
		      		}
		        } 
		   } 
		}); 
	});
});
//-->
</script>


<aui:input type="checkbox" name="anonimous" label="p2ptaskactivity.edit.anonimous" checked="<%=anonimous %>" ignoreRequestValue="true"></aui:input>
<aui:input type="checkbox" name="result" label="test.result" checked="<%=result %>" disabled="<%=disabled %>" 
	ignoreRequestValue="true"/>
	
<aui:input type="checkbox" name="fileoptional" label="p2ptaskactivity.edit.fileoptional" checked="<%=fileOptional%>" disabled="<%=disabled%>" ignoreRequestValue="true" />	
	
<aui:field-wrapper label="p2ptaskactivity.edit.dateUpload">

	<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
		yearNullable="false" dayNullable="false" monthNullable="false"
		dayParam="uploadDay" monthParam="uploadMon" yearParam="uploadYear"
		yearValue="<%=uploadYear%>" monthValue="<%=uploadMonth%>"
		dayValue="<%=uploadDay%>">
	</liferay-ui:input-date>

	<liferay-ui:input-time minuteParam="uploadMin" amPmParam="uploadAMPM"
		hourParam="uploadHour" hourValue="<%=uploadHour%>"
		minuteValue="<%=uploadMin%>">
	</liferay-ui:input-time>

</aui:field-wrapper>


<aui:input type="text" size="3" cssClass="lms-inpnumval" name="numValidaciones" label="p2ptaskactivity.edit.numvalidations" value="<%=numEvaluaciones%>" disabled="<%=disabled %>" 
		ignoreRequestValue="true"/>


<div id="<portlet:namespace />numValidacionesError" class="<%=((SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.required"))||
												      (SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.number"))||
												      (SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.range")))?
 														      "portlet-msg-error":StringPool.BLANK %>">
 	<%=(SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.required"))?
			LanguageUtil.get(pageContext,"p2ptaskactivity.editActivity.numValidaciones.required"):
	   (SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.number"))?
    		LanguageUtil.get(pageContext,"p2ptaskactivity.editActivity.numValidaciones.number"):
	   (SessionErrors.contains(renderRequest, "p2ptaskactivity.editActivity.numValidaciones.range"))?
    		LanguageUtil.get(pageContext,"p2ptaskactivity.editActivity.numValidaciones.range"):StringPool.BLANK %>
 </div>
