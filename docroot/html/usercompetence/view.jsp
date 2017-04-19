<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmtr" %>

<%@ include file="/init.jsp" %>

<jsp:useBean id="competences" type="java.util.List" scope="request" />
<jsp:useBean id="totale" class="java.lang.String" scope="request" />
<jsp:useBean id="delta" class="java.lang.String" scope="request" />

<%

	PortletPreferences preferences = renderRequest.getPreferences();
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	
	boolean showCompetence = GetterUtil.getBoolean(preferences.getValue("showCompetence", StringPool.TRUE));
	boolean showCourse = GetterUtil.getBoolean(preferences.getValue("showCourse", StringPool.FALSE));

//QQQ esto hay que revisarlo, esta raro.
	PortletURL viewURL = renderResponse.createRenderURL(); 
	viewURL.setParameter("delta", delta);
%>

<div id="user_competences">
	<liferay-ui:search-container curParam="act" emptyResultsMessage="there-are-no-competences" delta="10" deltaConfigurable="true" iteratorURL="<%=viewURL%>"  >
		<liferay-ui:search-container-results>
		<%
			pageContext.setAttribute("results", competences);
			try{
				pageContext.setAttribute("total", Integer.valueOf(totale));
			}catch(NumberFormatException nfe){
				pageContext.setAttribute("total", competences.size());
			}
		%>
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lms.views.CompetenceView" keyProperty="competenceId" modelVar="cc">
		
		   	<portlet:resourceURL var="resourceURL" >
				<portlet:param name="uuid" value="<%=String.valueOf(cc.getUuid())%>" />
			</portlet:resourceURL>
			
			<c:if test="<%=showCourse%>">
				<liferay-ui:search-container-column-text name="course.label" >
				    <%if(cc.getGenerateCertificate())
				    	{
				    %>
					<a target="_blank" href="<%=resourceURL %>" ><%=cc.getCourseTitle(themeDisplay.getLocale()) %></a>
					<%
					}
				    else
				    {
				    	%>
				    	<%=cc.getCourseTitle(themeDisplay.getLocale())%>
				    	<%
				    }
					%>
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="<%=showCompetence%>">
			<liferay-ui:search-container-column-text name="competence.label" >
			    <%if(cc.getGenerateCertificate())
			    	{
			    	%>
				<a target="_blank" href="<%=resourceURL %>" ><%=cc.getTitle(themeDisplay.getLocale()) %></a>
				<%
				}
			    else
			    {
			    	%>
			    	<%=cc.getTitle(themeDisplay.getLocale())%>
			    	<%
			    }
				%>
			</liferay-ui:search-container-column-text>
			</c:if>
			<liferay-ui:search-container-column-text name="date" >
				 <fmtr:setLocale value="<%= themeDisplay.getLocale()%>"/> 
				 	<%
				 	if(!themeDisplay.getLocale().toString().equals("en_US")){%>
						<fmtr:formatDate value="<%=  cc.getDate()%>" type="date" pattern="dd/MM/yyyy"  />

				 	<%}else{%>
				 		<fmtr:formatDate value="<%=  cc.getDate()%>" type="date" pattern="MM/dd/yyyy"  />
				 	<%} %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>