
<%@page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityLocalServiceImpl"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.util.P2pCheckActivity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.model.P2pActivityCorrections"%>
<%@page import="com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@include file="/init.jsp" %>


<script type="text/javascript">
function hideDiv(element){
	var ua = navigator.userAgent;
	
	var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
	var rv;
	 if (re.exec(ua) != null){
	      rv = parseFloat( RegExp.$1 );
	  }
	 var childs;
	if ( rv == 8.0 ) {
		childs = element.parentNode.querySelectorAll('.collapsable');
	}else{
		childs = element.parentNode.getElementsByClassName("collapsable");
	}
	
	if(childs.length>0){
		var height;
		if(element.parentNode.className == 'option-more'){
			element.parentNode.removeClass("option-more");
			element.parentNode.addClass("option-less");
		}else{
			element.parentNode.removeClass("option-less");
			element.parentNode.addClass("option-more");			
		}
	}
}
</script>


<%
long actId=ParamUtil.getLong(request,"actId",0);
long userId = themeDisplay.getUserId();

boolean result = false;
String resultString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"result");

if(resultString.equals("true")){
	result = true;
}

P2pActivity myp2pActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);

List<P2pActivityCorrections> p2pActCorList = P2pActivityCorrectionsLocalServiceUtil.
		findByP2pActivityId(myp2pActivity.getP2pActivityId());

DLFileEntry dlfile = null;
String urlFile = "";

SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
dateFormat.setTimeZone(timeZone);
String correctionDate = "";
int cont=0;
long resultTotal=0;
boolean correctionsDone=false;

LearningActivityResult actresult =LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
resultTotal=actresult.getResult();


boolean anonimous = false;
String anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"anonimous");

if(anonimousString.equals("true")){
	anonimous = true;
}	

%>




<c:if test="<%=result %>">
<% if(resultTotal >= 0 && result) { %>
		<div class="color_tercero font_14"><liferay-ui:message key="p2ptask.correction.result.total" />: <%= resultTotal %></div>
	<% } else { %>
			<div class="color_tercero font_14"><liferay-ui:message key="p2ptask.correction.result.notcompleted" /></div>
	<% } %>
</c:if>

<%
String fullName = "";
if(!p2pActCorList.isEmpty()){
	for (P2pActivityCorrections myP2PActCor : p2pActCorList){
		
		cont++;
		// Lo reseteamos en cada iteracción.
		dlfile = null;
		User propietary = UserLocalServiceUtil.fetchUser(myP2PActCor.getUserId());
		if(propietary != null){
			fullName = propietary.getFullName();
		}else{
			fullName = "";
		}
		
		String correctionText = myP2PActCor.getDescription();
		if(myP2PActCor.getFileEntryId()!=0)
		{
			dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActCor.getFileEntryId());
			urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
		}
		
		if(myP2PActCor.getDate()!=null){
			correctionDate = dateFormat.format(myP2PActCor.getDate());
		}else{
			correctionDate = "";
		}

		%>
		<c:if test="<%=myP2PActCor.getDate() != null %>">
			<%correctionsDone=true; %>
			<div class="option-more">
				<span class="label-col" onclick="hideDiv(this);"><liferay-ui:message key="p2ptask-correction-title" />
			
				
					<c:if test="<%=!anonimous %>">
					 	<span class="name">
					 		<liferay-ui:message key="by" />
					 		<%=fullName %>
					 	</span>
				 	</c:if>
				 	<c:if test="<%=anonimous %>">
					 	<span class="number">
					 		<liferay-ui:message key="number" /> 
					 		<%=cont%>
					 	</span> 
				 	</c:if>
				 	<c:if test="<%=myP2PActCor.getDate() != null %>">
				 		<span class="date"><liferay-ui:message key="p2ptaskactivity.inc.correctiondate" /> <%=correctionDate %></span>
				 	</c:if>
			 	</span>
	
				<div class="collapsable" style="padding-left:10px;">
					<c:if test="<%=myP2PActCor.getDate() != null %>">
						<c:if test="<%=result %>">
							<div class="container-result">
								<div class="color_tercero font_13"><liferay-ui:message key="p2ptask.correction.result" />: <%=myP2PActCor.getResult() %></div>
							</div>
						</c:if>
						<label class="aui-field-label" style="font-size: 14px;"> <liferay-ui:message key="p2ptaskactivity.comment" /> </label>
						<div class="container-textarea">
							<label for="<portlet:namespace/>corrected" />
							<%=correctionText %>
						</div>
						<%
						if(dlfile!=null){
							int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
							int sizeKb = size/1024; //Lo paso a Kilobytes
						%>
						<div class="doc_descarga">
							<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
							<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
						</div>
						
						<%
						}
						%>
					</c:if>
					<c:if test="<%=myP2PActCor.getDate() == null %>">
						<div class="color_tercero font_13">
							<liferay-ui:message key="p2ptaskactivity.inc.nocorrection" />
						</div>
					</c:if>
				</div>
					
			</div>
		</c:if>
		<%
	}
}

if(!correctionsDone){
	%>
	<div class="no-p2pActivites-corretion">
		<liferay-ui:message key="no-p2pActivites-corretion" />
	</div>
	<%
}%>