<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityLocalServiceImpl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivityCorrections"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp" %>

<%
Boolean isLinkTabletTaskP2PResult = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassTaskP2PResult="";
if(isLinkTabletTaskP2PResult){
	cssLinkTabletClassTaskP2PResult="tablet-link";
}
%>


<script type="text/javascript">
function actionDiv(element){
	var ua = navigator.userAgent;
	var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
	var rv;
	    if (re.exec(ua) != null){
	      rv = parseFloat( RegExp.$1 );
	  }
	 var childs;
	if ( rv == 8.0 ) {
		childs = element.parentNode.querySelectorAll('.collapsable2');
	}else{
		childs = element.parentNode.getElementsByClassName("collapsable2");
	}
	
	if(childs.length>0){
		for (var i = 0; i < childs.length; i++) {
			if (childs[i].style.display == 'none') {
				childs[i].style.display = 'block';
			}else{
				childs[i].style.display = 'none';
			}
		}
		
		if(element.parentNode.className == 'option-more2'){
			element.parentNode.className="option-less2";
		}else{
			element.parentNode.className="option-more2";
		}
	}
}
</script>

<script type="text/javascript">
	function changeDiv(id){
		if(id=="1"){
			$("#capa1").css('display','block');
		    $("#capa2").css('display','none');
		    $("#capa3").css('display','none');
		    $("#span1").addClass('selected');
		    $("#span2").removeClass('selected');
		    $("#span3").removeClass('selected');
		}else if(id=="2"){
			$("#capa1").css('display','none');
			$("#capa2").css('display','block');
			$("#capa3").css('display','none');
			$("#span1").removeClass('selected');
		    $("#span2").addClass('selected');
		    $("#span3").removeClass('selected');
		}
		else if(id=="3"){
			$("#capa1").css('display','none');
			$("#capa2").css('display','none');
			$("#capa3").css('display','block');
			$("#span1").removeClass('selected');
		    $("#span2").removeClass('selected');
		    $("#span3").addClass('selected');
		}
	}
	
</script>

<%
long actId=ParamUtil.getLong(request,"actId",0);
long userId=ParamUtil.getLong(request,"studentId",0);

if(actId!=0){
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=activity.getTypeId();
	long latId = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(actId, userId);
		if(latId>0){
			List<LearningActivityTry> latList = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(actId, userId);
			if(!latList.isEmpty()){
				for(LearningActivityTry lat :latList){
					latId = lat.getLatId();
				}
			}
		}
	User owner = UserLocalServiceUtil.getUser(userId);%>
	<div class="p2ptaskactivity-portlet">
	<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
	<h3 class="description-h3"><liferay-ui:message key="description" /></h3>
	<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
	<p class="sub-title"><liferay-ui:message key="p2ptask-done-by" /> <%=owner.getFullName()%></p>
	
	<% P2pActivity myp2pActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
	
	request.setAttribute("actId", actId);
	request.setAttribute("latId", latId);
	
	LearningActivityResult learnResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId,userId);
	if(myp2pActivity!=null){
		String classCSS2="";
		String classCSS3="";
		String passed="";
		String javascript="";
		Long showRevision = ParamUtil.getLong(request, "showRevision",0);
		if(!learnResult.getPassed()){
			classCSS2="selected";
			if(showRevision==1)
				javascript="changeDiv(3);";
			else
				javascript="changeDiv(2);";
		}
		else{
			classCSS3="selected";
			javascript="changeDiv(3);";
			passed="done";
		}
	%>
	<div class="steps">
		<span id="span1" onclick="changeDiv(1)" class="clicable done"><liferay-ui:message key="p2ptask-step1-resume" />&nbsp;>&nbsp;</span>
		<span id="span2" class="<%=classCSS2 %> clicable <%=passed%>" onclick="changeDiv(2)"><liferay-ui:message key="p2ptask-step2-resume" />&nbsp;>&nbsp;</span>
		<span id="span3" class="<%=classCSS3 %> clicable" onclick="changeDiv(3)"><liferay-ui:message key="p2ptask-step3-resume" /></span>
	</div>
	<div class="preg_content" id="capa1" style="display:none">
		
		<div class="container-textarea">
			<%= myp2pActivity.getDescription() %>
		</div>
		<% 
		if(myp2pActivity.getFileEntryId()!=0){
			DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myp2pActivity.getFileEntryId());
			String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
			int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
			int sizeKb = size/1024; //Lo paso a Kilobytes%>
			
			<div class="doc_descarga">
			<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
			<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassTaskP2PResult %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
			</div>
			
			<% }%>
			
		
		
	</div>
	<div class="preg_content" id="capa2" style="display:none">
	<%
		List<P2pActivityCorrections> p2pActList = P2pActivityCorrectionsLocalServiceUtil.findByActIdIdAndUserId(actId, userId);
		LearningActivityTry larEntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		dateFormat.setTimeZone(timeZone);
		int cont=0;
		if(!p2pActList.isEmpty()){
			
			DLFileEntry dlfile = null;
			String urlFile = "";
			int size = 0;
			int sizeKb = 0;
			String title = "";
			
			for (P2pActivityCorrections myP2PActiCor : p2pActList){
				P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.getP2pActivity(myP2PActiCor.getP2pActivityId());
				
				if(myP2PActivity.getUserId()!=userId){
					
					cont++;
					User propietary = UserLocalServiceUtil.getUser(myP2PActivity.getUserId());
					
					String description = myP2PActiCor.getDescription();
					String textButton = "p2ptask-correction";
					String correctionDate="";
					
					try{
						dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
						urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid(); 
						size = Integer.parseInt(String.valueOf(dlfile.getSize()));
						sizeKb = size/1024; //Lo paso a Kilobytes
						title = dlfile.getTitle();
					}catch(Exception e){
						e.printStackTrace();
					}
					
					if(myP2PActivity.getDate()!=null){
						correctionDate = dateFormat.format(myP2PActivity.getDate());
					}
					
					
					%>
					<div class="option-more2">
					<span class="label-col2" onclick="actionDiv(this);">
						
						<span class="name">
							<liferay-ui:message key="p2ptask.correction.activity" />&nbsp; 
							<%=propietary.getFullName()%>
						</span>
					</span>
					<div class="collapsable2" style="display:none">

						

						<c:if test="<%=myP2PActivity.getFileEntryId() != 0 %>">
							<div class="doc_descarga">
								<span><%=title%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
								<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassTaskP2PResult %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
							</div>
						</c:if>
						<div class="degradade">
							<div class="container-textarea">
								<label for="<portlet:namespace/>readonlydesc" />
								<%=description %>
							</div>
							<%

							if(myP2PActiCor.getFileEntryId()!=0){
								
								DLFileEntry dlfileCor = null;
								try{
									dlfileCor = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActiCor.getFileEntryId());
								}catch(Exception e){
									//e.printStackTrace();
								}
								
								if(dlfileCor != null){
									String urlFileCor = themeDisplay.getPortalURL()+"/documents/"+dlfileCor.getGroupId()+"/"+dlfileCor.getUuid();
									size = Integer.parseInt(String.valueOf(dlfileCor.getSize()));
									sizeKb = size/1024; //Lo paso a Kilobytes
								%>
								<div class="doc_descarga">
									<span><%=dlfileCor.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
									<a href="<%=urlFileCor%>" class="verMas <%=cssLinkTabletClassTaskP2PResult %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
								</div>
							<%	}
							}%>
						</div>
					
					</div>
				</div>
					<%
				}
			}
		}else{
		%>
			<div style="font-size: 14px;color: #B70050;font-weight: bold;"><liferay-ui:message key="no-p2pActivites-uploaded-resume" /></div>
		<%
		}
		%>
	</div>
	<div class="preg_content" id="capa3" style="display:none">
		<%
		List<P2pActivityCorrections> p2pActCorList = P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityId(myp2pActivity.getP2pActivityId());

		DLFileEntry dlfile = null;
		String urlFile = "";

		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		dateFormat.setTimeZone(timeZone);
		String correctionDate = "";
		cont=0;
		if(!p2pActCorList.isEmpty()){
			
			
			int size = 0;
			int sizeKb = 0;
			String title = "";
			for (P2pActivityCorrections myP2PActCor : p2pActCorList){
					
				cont++;
				User propietary = UserLocalServiceUtil.getUser(myP2PActCor.getUserId());
				String correctionText = myP2PActCor.getDescription();
				if(myP2PActCor.getFileEntryId()!=0){
					dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActCor.getFileEntryId());
					urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
					Date date = myP2PActCor.getDate();
					correctionDate = dateFormat.format(date);
				}
				
				boolean anonimous = false;
				String anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"anonimous");
				
				if(anonimousString.equals("true")){
					anonimous = true;
				}
				
				%>
					<div class="option-more2">
					<span class="label-col2" onclick="actionDiv(this);">
						
						<span class="name">
							<liferay-ui:message key="p2ptask.correction.activity" />&nbsp; 
							<%=propietary.getFullName()%>
						</span>
					</span>
					<div class="collapsable2" style="display:none">

						

						<c:if test="<%=myP2PActCor.getFileEntryId() != 0 %>">
							<div class="doc_descarga">
								<span><%=title%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
								<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassTaskP2PResult %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
							</div>
						</c:if>
						<div class="degradade">
							<div class="container-textarea">
								<label for="<portlet:namespace/>readonlydesc" />
								<%=myP2PActCor.getDescription() %>
							</div>
							<%

							if(myP2PActCor.getFileEntryId()!=0){
								
								DLFileEntry dlfileCor = null;
								try{
									dlfileCor = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActCor.getFileEntryId());
								}catch(Exception e){
									//e.printStackTrace();
								}
								
								if(dlfileCor != null){
									String urlFileCor = themeDisplay.getPortalURL()+"/documents/"+dlfileCor.getGroupId()+"/"+dlfileCor.getUuid();
									size = Integer.parseInt(String.valueOf(dlfileCor.getSize()));
									sizeKb = size/1024; //Lo paso a Kilobytes
								%>
								<div class="doc_descarga">
									<span><%=dlfileCor.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
									<a href="<%=urlFileCor%>" class="verMas <%=cssLinkTabletClassTaskP2PResult %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
								</div>
							<%	}
							}%>
						</div>
					
					</div>
				</div>
				<%
			}
		}else{
			%>
			<div style="font-size: 14px;color: #B70050;font-weight: bold;">
				<liferay-ui:message key="no-p2pActivites-corretion-resume" />
			</div>
			<%}%>
	</div>
	
	<script type="text/javascript">
	<%=javascript%>
	</script>
	<%
	}
}
%>	