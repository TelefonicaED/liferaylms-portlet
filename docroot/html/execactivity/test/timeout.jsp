<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page	import="com.liferay.lms.model.LearningActivity"%>
<%@page	import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page	import="com.liferay.lms.model.LearningActivityTry"%>
<%@ include file="/init.jsp" %>
<% 

	LearningActivity learnact=null;
	if(request.getAttribute("learningActivity")!=null)
	{
	 learnact=(LearningActivity)request.getAttribute("learningActivity");
	}
	else
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	}
	
	LearningActivityTry learnactTry=null;
	if(request.getAttribute("larntry")!=null)
	{
		learnactTry=(LearningActivityTry)request.getAttribute("larntry");
	}
	else
	{
		learnactTry=LearningActivityTryLocalServiceUtil
				.getLastLearningActivityTryByActivityAndUser(learnact.getActId(),themeDisplay.getUserId());
	}
	
	long activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"timeStamp"));
	
	if(activityTimestamp !=0){ 
		long timestamp=0;
		if(learnactTry.getEndDate()!=null) {
		    timestamp=activityTimestamp*1000 - (learnactTry.getEndDate().getTime() - learnactTry.getStartDate().getTime());				
		}
		else {
		    timestamp=activityTimestamp*1000 - (new Date().getTime() - learnactTry.getStartDate().getTime());			
		}
				
	  if(timestamp<0) {  
%>	    
	 <h3><liferay-ui:message key="execActivity.timeout" /></h3>       
<% 	    
	  }
	}
%>






