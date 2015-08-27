AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable','dd-constrain', 'dd-proxy', 'dd-drop',function(A) {

	A.all('div.question-page-current ul.sortable').each(function(node) {
		new A.Sortable(
		{
			container: node,
		    nodes: 'li'
		});
	});
	
    A.DD.DDM.on('drag:start', function(e) {
        var drag = e.target;
        drag.get('node').setStyle('opacity', '.25');
        drag.get('dragNode').set('innerHTML', drag.get('node').get('innerHTML'));
        drag.get('dragNode').setStyles({
            opacity: '.5',
            borderColor: drag.get('node').getStyle('borderColor'),
            backgroundColor: drag.get('node').getStyle('backgroundColor')
        });
    });
    A.DD.DDM.on('drag:end', function(e) {
        var drag = e.target;
        drag.get('node').setStyles({
            visibility: '',
            opacity: '1'
        });
    });
    	
	A.all('.draganddrop').each(function(node) {
		var elem = '#'+node.attr('id');
		
		A.all(elem + ' > .items > div').each(function(v,k) {
			var dd = new A.DD.Drag({
				container: elem,
	        	node: v,
	        	groups: ['group1', 'group2']
			}).plug(A.Plugin.DDProxy, {
				moveOnEnd: false
			}).plug(A.Plugin.DDConstrained, {
		        constrain2node: elem
		    });
		});
		
	    
		A.all(elem + ' > .drop > .drop-containers > div').each(function(v, k) {
			var dd = new A.DD.Drag({
				node: v,
				groups: ['group2']
			}).plug(A.Plugin.DDProxy, {
				moveOnEnd: false
			}).plug(A.Plugin.DDConstrained, {
		        constrain2node: elem
		    });
		});
		
		A.all(elem + ' > .drop > .drop-containers').each(function(v, k) {
			var droppable = new A.DD.Drop({
	            node: v,
	            groups: ['group1']
	        });
		
			droppable.on('drop:hit', function(e) {
				var drop = e.drop.get('node'),
	            drag = e.drag.get('node');
				var padre = drag.get('parentNode');
				if (!drop.hasClass('occupied') && drag.attr('tagName').toLowerCase() === 'div' && padre.hasClass('items')) {
					drop.text('');
					drop.append(drag);
					drop.addClass('occupied');
					drop.removeClass('base');
					A.one('input[name="' + drop.attr("name")+'hidden"]').val(drag.attr('id'));
				}
		    });
		});
		
		
		A.all(elem + ' > .items').each(function(v,k) {
			var droppable = new A.DD.Drop({
	            node: v,
	            groups: ['group2']
	        });
			
			droppable.on('drop:hit', function(e) {
				var drop = e.drop.get('node'),
	            drag = e.drag.get('node');
				var padre = drag.get('parentNode');
				if (drag.attr('tagName').toLowerCase() === 'div' && padre.hasClass('drop-containers')) {
					drop.append(drag);
					var idPadre = padre.attr('id');
					var textoPadre = idPadre.replace(/[^\d]/g, '');
					padre.text(Liferay.Language.get('drop', textoPadre));
					padre.removeClass('occupied');
					padre.addClass('base');
					A.one('input[name="' + padre.attr("name")+'hidden"]').val(-1);
				}
		    });
		});
		
	});
	
});
