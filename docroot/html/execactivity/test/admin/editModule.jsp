<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@ page contentType="application/json" %>
<%@ include file="/init.jsp"%>
<%
   response.setContentType("application/json");
   response.setHeader("Content-Disposition", "inline");
   JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
   long moduleId=ParamUtil.getLong(renderRequest,"resModuleId",0);   
   Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
   jsonObject.put("notEditable", (moduleId!=0)&&(ModuleLocalServiceUtil.getModule(moduleId).getStartDate().before(new Date())));
   jsonObject.write(out);
%>