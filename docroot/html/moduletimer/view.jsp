<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp"%>
<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
long currentModuleId=0;
long actId=ParamUtil.getLong(request,"actId",0);
Module theModule=null;
long leftTime = 0;
if(moduleId!=0)
{
	theModule=ModuleLocalServiceUtil.getModule(moduleId);
}
else
{
	if(actId!=0)
	{
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		theModule=ModuleLocalServiceUtil.getModule(larn.getModuleId());
	}
	else
	{
		List<Module> modules=(List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(modules.size()>0)
		{
			theModule=modules.get(0);
		}
	}
}
if(theModule!=null)
{
    ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(), themeDisplay.getUserId());
    if(mr!=null && !mr.getPassed())
    {
    	long usedTime = System.currentTimeMillis() - mr.getStartDate().getTime();
    	leftTime = theModule.getAllowedTime() - usedTime;
    	if(theModule.getAllowedTime()!=0 && !ModuleLocalServiceUtil.isUserFinished(theModule.getModuleId(), themeDisplay.getUserId())){
    	%>
    		<script type="text/javascript">
				AUI().ready('liferay-notice', 'collection', function(A) {
					var TestActivity = function(options) {
						var instance = this;
						instance.timeout=options.timeout||false;
						instance.warningText=options.warningText||'Timeout Warning: <span class="countdown-timer"/>';
						instance.expiredText=options.expiredText;
						instance.onClose=options.onClose;
						instance.banner=null;
						if(instance.timeout) {
							instance.banner=new Liferay.Notice({content:instance.warningText,closeText:false,toggleText:false});
							instance.countdowntext=instance.banner.one('.countdown-timer');
							if(instance.countdowntext){
								instance.countdowntext.text(instance._formatTime(instance.timeout));
							}
							var interval=1000;
							instance.finishtime = new Date().getTime()+instance.timeout;
							instance._countdownTimer = setInterval(
								function() {
									var currentTimeout = instance.finishtime-new Date().getTime();
									if (currentTimeout > 0) {
										instance.countdowntext.text(instance._formatTime(currentTimeout));
									}else {
										instance.banner.html(instance.expiredText);
										instance.banner.toggleClass('popup-alert-notice').toggleClass('popup-alert-warning');
										if (instance._countdownTimer) {
											clearInterval(instance._countdownTimer);
										}
										if (instance.onClose) {
											instance.onClose();
										}
									}
								},interval
							);					
						}
					};
			
					TestActivity.prototype = {
						_formatNumber: function(num) {
							if (num <= 9) {
								num = '0' + num;
							}
							return num;
						},
						_formatTime: function(time) {
							var instance = this;
							time = Math.floor(time/1000);
							var hours = Math.floor(time/3600);
							time = time%3600;
							var minutes = Math.floor(time/60);
							time = time%60;
							var seconds = Math.floor(time);
							return A.Array.map([hours,minutes,seconds], instance._formatNumber).join(':') +' <liferay-ui:message key="seconds"/>';	
						}
					};
								
					new TestActivity({timeout:<%=Long.toString(leftTime)%>,
							  warningText:'<liferay-ui:message key="execActivity.timeout.warning" />',
							  expiredText:'<liferay-ui:message key="execActivity.timeout" />',
							  onClose:function(){
								  if(typeof window.finishTry == 'function'){
									  finishTry();
								  }
						  }});
			
				});
			</script>	
    	<%
    	}
    }
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>