
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil"%>
<%@page import="com.liferay.portal.model.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="javax.swing.plaf.ListUI"%>
<%@page import="com.liferay.portal.model.Plugin"%>
<%@page import="com.liferay.portal.service.PluginSettingLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PluginSetting"%>
<%@page import="com.liferay.portal.kernel.plugin.PluginPackage"%>
<%@page import="com.liferay.portal.model.LayoutTemplate"%>
<%@page import="com.liferay.portal.model.LayoutTypePortlet"%>
<%@page import="com.liferay.portal.service.PortletServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Properties"%>
<%@ include file="/init.jsp"%>
	
<%
long actId = ParamUtil.getLong(request,"actId",0);
String res = ParamUtil.getString(request,"resultados","");

if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
%>	

<style>
	.action{border: 1px solid #000;margin:10px;padding:10px;}
</style>


<%

	Properties prop = new Properties();
	long buildNumber = 0;
	Date date = new Date(0);
	try {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		prop.load(classLoader.getResourceAsStream("service.properties"));
	
		buildNumber = Long.valueOf(prop.getProperty("build.date",""));
		
		date = new Date(buildNumber);
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

%>



<%

List<String> portletIds = new ArrayList<String>();

List<Layout> layoutForPortletName = LayoutLocalServiceUtil.getLayouts(0, LayoutLocalServiceUtil.getLayoutsCount()); 


for(Layout l:layoutForPortletName){
	

	LayoutTypePortlet layoutTypePortletName = (LayoutTypePortlet)l.getLayoutType(); 
	LayoutTemplate layoutTemplate=layoutTypePortletName.getLayoutTemplate(); 
	List columnCount = layoutTemplate.getColumns(); 

	for(int i=0; i < columnCount.size() ; i++){
		
		String column = (String)columnCount.get(i); 
		List<Portlet> portlets = layoutTypePortletName.getAllPortlets(column); 

		for(Portlet portlet : portlets){ 
			
			if(!portletIds.contains(portlet.getPortletId()) && portlet.getPortletId().contains("_WAR_liferaylmsportlet")){
				portletIds.add(portlet.getPortletId());
			}
		}  
	}	

}

%>

<liferay-ui:success key="portaladmin.success-reset-audit" message="portaladmin.success-reset-audit" />
<liferay-ui:error key="portaladmin.error-reset-audit" message="portaladmin.error-reset-audit" />


<h3><%= "Build date: "	+ date.toString() %></h3>
<h3><%= "Build number: "+ prop.getProperty("build.number","") %></h3>
<h3><%= "Auto upgrade: "+ prop.getProperty("build.auto.upgrade","") %></h3>


<div class="actions">
	<%--
	<div class="action">
		<portlet:actionURL name="checkgroups" var="checkgroupsURL" />
		<liferay-ui:icon image="unlink" label="<%=true %>" message="checkGroups" url='<%= checkgroupsURL %>'/>
	</div>
	 --%>

	<div class="action">
		<portlet:actionURL name="asignP2pActivity" var="asignP2pActivityURL" />
		<liferay-ui:icon image="assign" label="<%=true %>" message="p2ptaskactivity.edit.asignp2p" url='<%= asignP2pActivityURL %>'/>
	</div>
	
	
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.reset-audit-implementations" /></h4>
		<portlet:actionURL name="resetAuditImplamentations" var="resetAuditImplamentationsURL" />
		<aui:form action="${resetAuditImplamentationsURL}" name="resetAuditImplamentationsFm" >
		<aui:button-row>
			<aui:button type="submit" value="reset" name="reset" class="submit"/>
		</aui:button-row>
		</aui:form>
	</div>
	
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.updateModulePassedDate" /></h4>
		<portlet:actionURL name="updateModulePassedDate" var="updateModulePassedDateURL" />
		<aui:form action="<%=updateModulePassedDateURL %>" method="POST" name="form_mail">		
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox" helpMessage="portaladmin.updateModulePassedDate"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.updateModulePassedDate" class="submit"></aui:button>
			</aui:button-row>
		</aui:form>
		<div>
			<a onClick="openLogs('/custom_logs/updateModulePassedDate.txt')" style="Cursor:pointer;">updateModulePassedDate.txt</a>
		</div>
	</div>
	
	<div class="action">
		<liferay-ui:panel id="portlet_titles" title="portaladmin.portlet_titles" extended="closed">
		<%
			DynamicQuery dq = new DynamicQueryFactoryUtil().forClass(PortletPreferences.class)
			.add(RestrictionsFactoryUtil.like("preferences","%portletSetupTitle%"))
			.add(RestrictionsFactoryUtil.or(RestrictionsFactoryUtil.like("portletId", "%liferaylmsportlet%"),RestrictionsFactoryUtil.like("portletId","%weclassportlet%")))
			.addOrder(OrderFactoryUtil.getOrderFactory().asc("portletId"));

			List<PortletPreferences> prefs = PortletPreferencesLocalServiceUtil.dynamicQuery(dq);
			
			String id = "";
			for(PortletPreferences pre : prefs){
				
				if(pre.getPortletId().equals(id)){
					continue;
				}
				id=pre.getPortletId();
				
				%><h4><%=pre.getPortletId() %></h4><%
				Document document = SAXReaderUtil.read(pre.getPreferences());
				Element rootElement = document.getRootElement();
				for (Element element : rootElement.elements("preference")) {
					if(element.elementText("name").startsWith("portletSetupTitle")){
						%><p><%=element.elementText("name") %> - <%=element.elementText("value") %></p><%
					}
				}
			}
		%>
		</liferay-ui:panel>
	</div>
	
	<div class="action">
		<h4><liferay-ui:message key="cambiar nombre del portlet" /></h4>
		<portlet:actionURL name="changePortletName" var="changePortletNameURL" />
		<aui:form action="<%=changePortletNameURL %>" method="POST" name="form_mail">
					
			<aui:select name="before" label="portaladmin.portletname.before" helpMessage="portaladmin.portletname.help">
			<%
				List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(0, PortletLocalServiceUtil.getPortletsCount());
			
				//ordenamos la lista 
			    Collections.sort(portlets, new Comparator() {  
	
			        public int compare(Object o1, Object o2) {  
			        	Portlet e1 = (Portlet) o1;  
			        	Portlet e2 = (Portlet) o2;  
			            return e1.getPortletId().compareToIgnoreCase(e2.getPortletId());  
			        }  
			    });  
				
				for(Portlet  portlet:portlets){
					if(portlet.getPortletId().contains("_WAR_liferaylmsportlet")){
						%>
						<aui:option value="<%=portlet.getPortletId() %>"><%=portlet.getPortletId() %></aui:option>
						<%
					}
				}
			%>
			</aui:select>
			
			<aui:select name="after" label="portaladmin.portletname.after" helpMessage="portaladmin.portletname.help">
			<%
				List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(0, PortletLocalServiceUtil.getPortletsCount());
			
				//ordenamos la lista 
			    Collections.sort(portlets, new Comparator() {  
	
			        public int compare(Object o1, Object o2) {  
			        	Portlet e1 = (Portlet) o1;  
			        	Portlet e2 = (Portlet) o2;  
			            return e1.getPortletId().compareToIgnoreCase(e2.getPortletId());  
			        }  
			    });  
				
				for(Portlet  portlet:portlets){
					if(portlet.getPortletId().contains("_WAR_liferaylmsportlet")){
						%>
						<aui:option value="<%=portlet.getPortletId() %>"><%=portlet.getPortletId() %></aui:option>
						<%
					}
				}
			%>
			</aui:select>
			
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<div class="action">
		<portlet:actionURL name="updateResultP2PActivities" var="updateResultP2PActivitiesURL" />
		<liferay-ui:icon image="recent_changes" label="<%=true %>" message="p2ptaskactivity.updateResult" url='<%= updateResultP2PActivitiesURL %>'/>
		<div>
			<a onClick="openLogs('/custom_logs/updateResultP2PActivities.txt')" style="Cursor:pointer;">updateResultP2PActivities.txt</a>
		</div>
	</div>
	
	<portlet:actionURL name="updateExtraContentMultimediaActivities" var="updateExtraContentMultimediaActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentMultimediaActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="updateExtraContentScormActivities" var="updateExtraContentScormActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.scorm.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentScormActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="deleteRepeatedModuleResult" var="deleteRepeatedModuleResultURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.deleteRepeatedModuleResult" /></h4>
		<aui:form action="<%=deleteRepeatedModuleResultURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="update" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
		<div>
			<a onClick="openLogs('/custom_logs/deleteRepeatedModuleResult.txt')" style="Cursor:pointer;">deleteRepeatedModuleResult.txt</a>
		</div>
	</div>

	<div class="resultado">
		<%=res %>
	</div>

</div>
<%
}
%>