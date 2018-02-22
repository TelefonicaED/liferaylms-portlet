<script>
	Liferay.provide(
	        window,
	        '<portlet:namespace />questionValidation',
	        function(question) {
				var A = AUI();
				var questionValidators = {
					questiontype_options : function(question) {
						return (question.all('div.answer input[type="radio"]:checked').size() > 0);
					},
					questiontype_options_select : function(question) {
						var selectAnswers = question.all('select.answer');
						var validSelects = true;
						selectAnswers.each(function (selectAnswer){
							if (selectAnswer.val()==""||selectAnswer.val()==0){
								validSelects = false;
							}
						});
						return validSelects;
					},
					questiontype_multioptions : function(question) {
						return (question.all('div.answer input[type="checkbox"]:checked').size() > 0);
					},
					questiontype_freetext : function(question) {
						return (A.Lang.trim(question.one('div.answer textarea').val()) != '');
					},
					questiontype_fillblank : function(question) {
						var texts = question.all(':text');
						var validTexts = true;
						texts.each(function(node) {
							validTexts = validTexts && (A.Lang.trim(node.val()) != '');
						});
						
						var selecteds = question.all(':selected');
						var validSelecteds = true;
						selecteds.each(function(node) {
							validSelecteds = validSelecteds && (node.val() != '');
						});
						
						var validCheckeds = (question.all(':radio:checked').size() == question.all('.multichoice').size());
						
						return validTexts && validSelecteds && validCheckeds;
					},
					questiontype_sortable : function(question) {
						return true;
					},
					questiontype_draganddrop : function(question) {
						return (question.all('div.drop > input[type="hidden"][value="-1"]').size() == 0);
					},
					
				};
				
				var clases = question.getAttribute('class').split(" ");
				var questiontypevalidator = '';
				for ( var i = 0; i < clases.length; i++) {
					var clase = clases[i];
					if (clase.indexOf('questiontype_') == 0) {
						questiontypevalidator = clase;
						break;
					}
				}
				if (questionValidators[questiontypevalidator] != null) {
					var resultado = questionValidators[questiontypevalidator](question);
					return resultado;
				} else {
					return true;
				}
				
	        },
	        ['node', 'aui-dialog', 'event', 'node-event-simulate']
	);
	
</script>