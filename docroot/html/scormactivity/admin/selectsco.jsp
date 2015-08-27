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
public void printItems(Element itemi,JspWriter out,long assetId,String assetTitle,String assertEditable,String assertWindowable,PortletRequest renderRequest,RenderResponse renderResponse, String portletName,long plid)
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
							selectUrl.setParameter("jspPage", "/html/scormactivity/admin/result.jsp");
							selectUrl.setParameter("assertId", Long.toString(assetId));
							selectUrl.setParameter("assertTitle", assetTitle);
							selectUrl.setParameter("assertEditable",assertEditable);
							selectUrl.setParameter("assertWindowable", assertWindowable);
							selectUrl.setParameter("sco", identifier);

							out.println("<li><a href='"+selectUrl.toString()+"'>"+title+"</a></li>");
						}
					}
					printItems(item,out,assetId,assetTitle,assertEditable,assertWindowable, renderRequest,renderResponse,  portletName, plid);	
			}
			out.println("</ul>");
		}
	}
	catch(IOException e)
	{
	}
}
%>
<div id="scolist">
<%
	long assetId=ParamUtil.getLong(request, "assertId");
    String assetTitle=ParamUtil.get(request, "assertTitle", "");
    String assertEditable=ParamUtil.get(request, "assertEditable", "false");
    String assertWindowable=ParamUtil.get(request, "assertWindowable", "false");
	if(assetId != 0)
	{
		
		AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(assetId);
		if(entry.getClassName().equals(SCORMContent.class.getName()))
		{
			
			SCORMContent scorm=SCORMContentLocalServiceUtil.getSCORMContent(entry.getClassPK())	;
			String rutaDatos = SCORMContentLocalServiceUtil.getBaseDir();
		
			String urlIndex=rutaDatos+"/"+Long.toString(scorm.getCompanyId())+"/"+Long.toString(scorm.getGroupId())+"/"+scorm.getUuid()+"/imsmanifest.xml";
			FileInputStream fis = new FileInputStream(urlIndex);
			Document manifest=SAXReaderUtil.read(fis);
			Element organizatEl=manifest.getRootElement().element("organizations").element("organization");
			printItems(organizatEl,out,assetId,assetTitle,assertEditable,assertWindowable,renderRequest,renderResponse, portletName, themeDisplay.getPlid());
		}
	
	}
%>
</div>
