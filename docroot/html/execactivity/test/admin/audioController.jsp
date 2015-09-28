<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.util.PropsUtil" %>

<%
	String attempts = PropsUtil.get("lms.learningactivity.audiocontroller.attempts");
	if(attempts==null){
		attempts = "1";
	}
%>

<script type="text/javascript">
	var intentos=0;
	
	if(!document.getElementById("enunciado")){
		document.getElementById("reproducir").style.display = "none";
	}else{
		document.getElementById("enunciado").ontimeupdate = function() {
	    	var tiempo_reproducido="<liferay-ui:message key='learningactivity.embeddedtest.playinprogress'/>" + <portlet:namespace />pintarTiempo(document.getElementById("enunciado").currentTime);
	   		document.getElementById("reproducir").innerHTML =  tiempo_reproducido;
	      	document.getElementById('barra_proceso').setAttribute("value", document.getElementById("enunciado").currentTime / document.getElementById("enunciado").duration);
		}
		document.getElementById("enunciado").onplay  = function() {
			if (intentos < <%=attempts %> ){
				document.getElementById("barra_proceso").style.display = "inline";
	        	document.getElementById("enunciado").play(); 
	   		}else{
	        	document.getElementById("enunciado").pause(); 
	        	document.getElementById("enunciado").currentTime = 0;
	        	document.getElementById("enunciado").src = '';
	   		}    
	  	}
	   	document.getElementById("enunciado").onended = function() {
	    	document.getElementById("enunciado").pause();
	    	document.getElementById('barra_proceso').setAttribute("value", 0);
	    	document.getElementById("barra_proceso").style.display = "none";
	    	document.getElementById("reproducir").innerHTML ="Reproducir";
	    	intentos=intentos+1;
	    	if (intentos >= <%=attempts %> ){
	      		document.getElementById("enunciado").currentTime = 0;
	         	document.getElementById("enunciado").src = '';
	         	document.getElementById('reproducir').setAttribute("class", "reproduccion_bloqueado");
	     	}
	  	}
	}
  	function <portlet:namespace />reproducir(){
  		if(document.getElementById("enunciado")){
	   		<portlet:namespace />reproducir();
   		}
  	}
    function <portlet:namespace />pintarTiempo(seconds) {
    	minutes = Math.floor(seconds / 60);
     	minutes = (minutes >= 10) ? minutes : "0" + minutes;
     	seconds = Math.floor(seconds % 60);
     	seconds = (seconds >= 10) ? seconds : "0" + seconds;
    	
     	return  minutes + ":" + seconds;
   	}
    AUI().ready(function(A) {
   		<portlet:namespace />reproducir();
   	});
</script>