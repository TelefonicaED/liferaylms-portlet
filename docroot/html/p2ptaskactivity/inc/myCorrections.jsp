
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
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
<%
Boolean isLinkTabletP2PMyCorrections = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassMyCorrections="";
if(isLinkTabletP2PMyCorrections){
	cssLinkTabletClassMyCorrections="tablet-link";
}
%>

<script>
function <portlet:namespace />hideDiv(element){
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


boolean configAnonimous = false;
String anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"anonimous");

if(anonimousString.equals("true")){
	configAnonimous = true;
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
boolean anonimous;
if(!p2pActCorList.isEmpty()){
	for (P2pActivityCorrections myP2PActCor : p2pActCorList){
		
		cont++;
		// Lo reseteamos en cada iteracción.
		dlfile = null;
		User propietary = UserLocalServiceUtil.fetchUser(myP2PActCor.getUserId());
		anonimous = configAnonimous;
		if(propietary != null){
			fullName = propietary.getFullName();
		}else{
			anonimous = true;
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

		P2pActivity myP2PActivity = null;
		
		try{
			myP2PActivity = P2pActivityLocalServiceUtil.getP2pActivity(myP2PActCor.getP2pActivityId());
		}catch(Exception e){
			
		}
		
		%>
		<c:if test="<%=myP2PActCor.getDate() != null %>">
			<%correctionsDone=true; %>
			<div class="option-more">
				<span class="label-col" onclick="${renderResponse.getNamespace()}hideDiv(this);"><liferay-ui:message key="p2ptask-correction-title" />
			
				
					<c:if test="<%=!anonimous%>">
					 	<span class="name">
					 		<liferay-ui:message key="by" />
					 		<%=fullName %>
					 	</span>
				 	</c:if>
				 	<c:if test="<%=anonimous%>">
					 	<span class="number">
					 		<liferay-ui:message key="p2ptaskactivity.number" /> 
					 		<%=cont%>
					 	</span> 
				 	</c:if>
				 	<c:if test="<%=myP2PActCor.getDate() != null %>">
				 		<span class="date"><liferay-ui:message key="p2ptaskactivity.inc.correctiondate" /> <%=correctionDate %></span>
				 	</c:if>
			 	</span>
	
				<div class="collapsable" style="display: none;">
					<c:if test="<%=myP2PActCor.getDate() != null %>">
						<c:if test="<%=result %>">
							<div class="container-result">
								<div class="color_tercero font_13"><liferay-ui:message key="p2ptask.correction.result" />: <%=myP2PActCor.getResult() %></div>
							</div>
						</c:if>
						<label class="aui-field-label" style="font-size: 14px;"> <liferay-ui:message key="p2ptaskactivity.comment" /> </label>
						<div class="container-textarea">
<%
							
								if(myP2PActivity!=null){
									JSONArray jArrayDes = null;
									try{
										jArrayDes = JSONFactoryUtil.createJSONArray(correctionText);
									}catch(Exception e){}
								
									if(jArrayDes!=null&&jArrayDes.length()>0){
										%><div class="p2pResponse">
											<div class="p2pAnswer"><%=myp2pActivity.getDescription()%></div>
										<ul><%
										for(int i=0;i<jArrayDes.length();i++){
											JSONObject jsonObjectDes = jArrayDes.getJSONObject(i);
											String valoration = null;
											if(jsonObjectDes!=null)
												valoration = jsonObjectDes.getString("text"+i);
											
												String question = LearningActivityLocalServiceUtil.getExtraContentValue(actId, "text"+i);
												
											%>
												<li>
													<div class="p2pQuestion"><%=question!=null?question:StringPool.BLANK %></div>
													<div class="p2pCorrect"><%=valoration!=null?valoration:StringPool.BLANK %></div>
												</li>
											<%
										}%></ul></div><%
										
									}else{
										String description = myP2PActCor.getDescription();
										%><%=description %><%
									}
								}
							%>
						</div>
						<%
						if(dlfile!=null){
							int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
							int sizeKb = size/1024; //Lo paso a Kilobytes
						%>
						<div class="doc_descarga">
							<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
							<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassMyCorrections%>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
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