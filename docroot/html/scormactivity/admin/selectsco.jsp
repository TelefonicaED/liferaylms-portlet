<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.scormcontent.ScormContenRegistry"%>
<%@page import="com.liferay.lms.learningactivity.scormcontent.ScormContentAsset"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>
<%!
public void printItems(Element itemi,JspWriter out,long resId,long resModuleId,long assetId,String assetTitle,String assertEditable,String assertWindowable,PortletRequest renderRequest,RenderResponse renderResponse, String portletName,long plid)
{
	try
	{
		java.util.List<Element> items=itemi.elements("item");
		if(items!=null&&items.size()>0)
		{
			out.println("<ul>");
			for(Element item:items)
			{
				
					Element titlee=item.element("title");
					if(titlee!=null)
					{
						String title=titlee.getText();
						String identifier=item.attributeValue("identifier");
						if(identifier==null || identifier.equals(""))
						{
								out.println("<li>"+title+"</li>");
						}
						else
						{
							
							PortletURL selectUrl = renderResponse.createRenderURL();
							selectUrl.setParameter("jspPage", "/html/editactivity/editactivity.jsp");
							selectUrl.setParameter("assertId", Long.toString(assetId));
							selectUrl.setParameter("assertTitle", assetTitle);
							selectUrl.setParameter("sco", identifier);
							selectUrl.setParameter("resId", String.valueOf(resId));
							selectUrl.setParameter("resModuleId", String.valueOf(resModuleId));
							selectUrl.setParameter("type", "9");
							
							out.println("<li><a href='"+selectUrl.toString()+"'>"+title+"</a></li>");
						}
					}
					printItems(item,out,resId,resModuleId,assetId,assetTitle,assertEditable,assertWindowable, renderRequest,renderResponse,  portletName, plid);	
			}
			out.println("</ul>");
		}
	}
	catch(IOException e)
	{
	}
}
%>




<%
long resId = ParamUtil.getLong(request,"resId",0);
long resModuleId = ParamUtil.getLong(request,"resModuleId",0);
String message = "new-activity-scorm";
if(resId > 0){
	message = LearningActivityLocalServiceUtil.fetchLearningActivity(resId).getTitle(themeDisplay.getLocale());
}
%>

<liferay-portlet:renderURL var="backURL" >
	<liferay-portlet:param name="mvcPath" value="/html/editactivity/editactivity.jsp" />
	<liferay-portlet:param name="resId" value="<%=String.valueOf(resId) %>" />
	<liferay-portlet:param name="resModuleId" value="<%=String.valueOf(resModuleId) %>" />
	<liferay-portlet:param name="type" value="9" />
</liferay-portlet:renderURL>


<liferay-ui:header 
	title="<%=message %>" 
	backURL="<%=backURL  %>"
	localizeTitle="<%=resId <= 0 %>"
/>




<div id="scolist">
<%
	long assetId=ParamUtil.getLong(request, "assertId");
    String assetTitle=ParamUtil.get(request, "assertTitle", "");
    String assertEditable=ParamUtil.get(request, "assertEditable", "false");
    String assertWindowable=ParamUtil.get(request, "assertWindowable", "false");
	if(assetId != 0)
	{
		
		AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(assetId);
		ScormContentAsset scormContentAsset=ScormContenRegistry.getScormContentAsset(entry.getClassName());
		
		if(scormContentAsset!=null)
		{
			Document manifest=scormContentAsset.getManifest(renderRequest, entry);
			Element organizatEl=manifest.getRootElement().element("organizations").element("organization");
			printItems(organizatEl,out,resId,resModuleId,assetId,assetTitle,assertEditable,assertWindowable,renderRequest,renderResponse, portletName, themeDisplay.getPlid());
		}
	
	}
%>
</div>
