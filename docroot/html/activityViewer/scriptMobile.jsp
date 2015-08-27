<script type="text/javascript">
<!--
	AUI().ready('event',function(A) {
		A.setInterval(function(){
				Liferay.Session.extend();
			}, 
			Liferay.Session._getWarningTime() - 60000);


		A.all('A[target=\'_blank\']').
			each( function( anchor ) {
				var href = anchor.get('href');
				anchor.setAttrs({
					target: '_self',
					href: '#'
				});
				anchor.on('click', 
					function(event){
						window.parent.postMessage({href:href}, '*');
				});
			});
	});
//-->
</script>