<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@ page contentType="application/json" %>
<%@ include file="/init.jsp"%>
<%
   renderResponse.setProperty(
		"clear-request-parameters", Boolean.TRUE.toString());
   response.setContentType("application/json");
   response.setHeader("Content-Disposition", "inline");
   JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
   String responseCode = ParamUtil.getString(renderRequest,"responseCode",StringPool.ASCII_TABLE[49]); //1   
   jsonObject.put("responseCode", responseCode);
   
   
   JSONArray messages = JSONFactoryUtil.createJSONArray();
   jsonObject.put("messages",messages);
   
   for(String message:ParamUtil.getParameterValues(renderRequest, "message")){
	   messages.put(message);
   }

   jsonObject.write(out);
%>