var nojquery = (typeof jQuery == 'undefined');

if (nojquery) {
	var jqScript = document.createElement('script');
	jqScript.type = 'text/javascript';
	jqScript.onload = function() {
		var jquiScript = document.createElement('script');
		jquiScript.type = 'text/javascript';
		jquiScript.onload = function() {
			var jss = [
			            '/liferaylms-portlet/js/jquery.ui.touch-punch.min.js', 
						'/liferaylms-portlet/js/mouse.js', 
						'/liferaylms-portlet/js/widget.js',
						'/liferaylms-portlet/js/test.js'];
			for (var i = 0; i < jss.length; i++) {
				var newScript = document.createElement('script');
				newScript.type = 'text/javascript';
				newScript.src = jss[i];
				document.getElementsByTagName("head")[0].appendChild(newScript);
			}
		};
		jquiScript.src = 'http://code.jquery.com/ui/1.8.21/jquery-ui.min.js';
		document.getElementsByTagName("head")[0].appendChild(jquiScript);
	};
	jqScript.src = 'http://code.jquery.com/jquery-1.7.2.min.js';
	document.getElementsByTagName("head")[0].appendChild(jqScript);
	
} else {
	var newScript = document.createElement('script');
	newScript.type = 'text/javascript';
	newScript.src = '/liferaylms-portlet/js/test.js';
	document.getElementsByTagName("head")[0].appendChild(newScript);
}