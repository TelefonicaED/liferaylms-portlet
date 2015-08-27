AUI().ready('event', 'node', 'anim',function(A) {

	A.all('.lms-tree ul li.option-more ul').each(function(ul){
		ul.hide();
	});
	
	A.all('.lms-tree ul li span.desplegar').each(function(span){
	
		var parentNode=span.get('parentNode');
		if(parentNode.one('div.lms-desplegable')) {
			var wrapper=parentNode.one('div.lms-desplegable');
			var height=wrapper.height();
			var open = new A.Anim({node: wrapper, to: {height:  height},
			     easing: A.Easing.easeOut});
	 		var close = new A.Anim({node: wrapper, to: {height:  0},
			     easing: A.Easing.easeIn});
	
			span.on('click',function(){
				
				if(parentNode.hasClass('option-more')) {
					parentNode.removeClass("option-more");
					parentNode.addClass("option-less");
					open.run();
				
				}
				else {
					parentNode.removeClass("option-less");
					parentNode.addClass("option-more");
					close.run();
				}
	
			});
		}
	});

	A.all('span.ico-desplegable, .preg_content div > span.label-col').each(function(span){
		var parentNode=span.get('parentNode');
		var wrapper = A.Node.create('<div style="overflow: hidden;" ></div>');
		wrapper.append(parentNode.one('div.collapsable').replace(wrapper));
		parentNode.one('div.collapsable').setStyle('display','block');
		var height=wrapper.height();
		
		if (height == 0) {
			parentNode.get('parentNode').setStyles({'position':'absolute','visibility':'hidden', 'display':'block'});
			height=wrapper.height();
			parentNode.get('parentNode').setStyles({'position':'relative','visibility':'visible', 'display':'none'});
		}
		wrapper.height(0);
		var open = new A.Anim({node: wrapper, to: {height:  height},
		     easing: A.Easing.easeOut});
 		var close = new A.Anim({node: wrapper, to: {height: 0},
		     easing: A.Easing.easeIn});
		span.on('click',function(){
			
			if(parentNode.hasClass('option-less')) {
				parentNode.removeClass("option-less");
				parentNode.addClass("option-more");
				close.run();
			}
			else {
				parentNode.removeClass("option-more");
				parentNode.addClass("option-less");
				open.run();
			}

		});
	});
});