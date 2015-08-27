<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page
	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp"%>


<aui:select cssClass="lms-inpprecendence" inlineLabel="true"
	label="activity" name="precedence">
	<%
		renderResponse.setProperty("clear-request-parameters", Boolean.TRUE.toString());

			long actId = ParamUtil.getLong(request, "resId", 0);
			long moduleId = ParamUtil.getLong(request, "resModuleId", 0);
			long precedence = ParamUtil.getLong(request, "precedence", 0);
			boolean isFirstRun = ParamUtil.getBoolean(request, "firstLoad", true);

			if (precedence != 0) {
				LearningActivity precedenceAct = LearningActivityLocalServiceUtil.getLearningActivity(precedence);

				if (precedenceAct.getModuleId() != 0 && isFirstRun) {
					moduleId = precedenceAct.getModuleId();
				}
			}
			List<LearningActivity> activities = null;
			if (moduleId > 0 && (precedence > 0 || !isFirstRun)) {
				activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
			} else {
				activities = new ArrayList<LearningActivity>();
			}
	%>
	<aui:option value="0"><%=LanguageUtil.get(pageContext, "none")%></aui:option>
	<%
		for (LearningActivity activity : activities) {
				if (activity.getActId() != actId) {
	%>
	<aui:option value="<%=activity.getActId()%>"
		selected="<%=(precedence == activity.getActId())%>"><%=activity.getTitle(themeDisplay.getLocale())%></aui:option>
	<%
		}
			}
	%>
</aui:select>