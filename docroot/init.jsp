<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Properties"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.NoSuchUserException" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchEntry" %>
<%@ page import="com.liferay.portal.kernel.dao.search.TextSearchEntry" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.log.Log" %>
<%@ page import="com.liferay.portal.kernel.log.LogFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.messaging.DestinationNames" %>
<%@ page import="com.liferay.portal.kernel.messaging.MessageBusUtil" %>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.model.Contact" %>
<%@ page import="com.liferay.portal.model.Group" %>
<%@ page import="com.liferay.portal.model.GroupConstants" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="com.liferay.portal.model.LayoutConstants" %>
<%@ page import="com.liferay.portal.model.Organization" %>
<%@ page import="com.liferay.portal.model.Portlet" %>
<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portal.security.permission.ActionKeys" %>
<%@ page import="com.liferay.portal.service.GroupLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.LayoutLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.OrganizationLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.PortletLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.permission.UserPermissionUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.util.PortletKeys" %>
<%@ page import="com.liferay.portal.util.comparator.UserLoginDateComparator" %>
<%@ page import="com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil" %>
<%@ page import="com.liferay.util.RSSUtil" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<%
Properties pp = new Properties();
long bNum = 0, miliseconds = 0;
Date c = new Date();
try {
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	pp.load(classLoader.getResourceAsStream("service.properties"));
	bNum = Long.valueOf(pp.getProperty("build.number",""));
	miliseconds = Long.valueOf(pp.getProperty("build.date",""));
	c = new Date(miliseconds);
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
%>
<portlet:defineObjects />

<% 
	WindowState windowState = renderRequest.getWindowState();
	
	if(!LiferayWindowState.EXCLUSIVE.equals(windowState)) {
%>
<!-- <%="vd-"+bNum+"-"+c.toString() %>-->
<% 
	}
%>


<liferay-theme:defineObjects />

<%


String currentURL = PortalUtil.getCurrentURL(request);

Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

Organization organization = null;
User user2 = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
}
else if (group.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(group.getClassPK());
}

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
NumberFormat percentFormat = NumberFormat.getPercentInstance(locale);

%>