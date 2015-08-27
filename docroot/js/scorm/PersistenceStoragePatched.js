Type.createNamespace('Player');

SCORM_1_2.API_LIB.prototype.$27 = function($p0) {
	var $0 = false;
	if (this.$1F.getDataTreeValue('cmi.core.exit') == null || this.$1F.getDataTreeValue('cmi.core.exit') == '') {
		this.$1F.setDataTreeValue('cmi.core.exit', 'suspend', false);
	}
	if ($p0) {
		if (this.$1F.getDataTreeValue('cmi.core.exit') === 'suspend') {
			$0 = true;
			this.$1F.setDataTreeValue('cmi.core.entry', 'resume', false);
		} else {
			this.$1F.setDataTreeValue('cmi.core.entry', '', false);
		}
		if (this.$1F.getDataTreeValue('cmi.core.lesson_status') === 'not attempted') {
			if (this.$1F.getDataTreeValue('cmi.core.lesson_mode') === 'browse') {
				this.$1F.setDataTreeValue('cmi.core.lesson_status',
						'browsed', false);
			} else {
				this.$1F.setDataTreeValue('cmi.core.lesson_status',
						'completed', false);
			}
		}
		if (this.$1F.getDataTreeValue('cmi.core.lesson_mode') === 'normal') {
			if (this.$1F.getDataTreeValue('cmi.core.credit') === 'credit') {
				if (this.$1F.getDataTreeValue('cmi.core.lesson_status') === 'completed') {
					if (this.$1F
							.getDataTreeValue('cmi.student_data.mastery_score') !== ''
							&& this.$1F
									.getDataTreeValue('cmi.core.score.raw') !== '') {
						if (parseFloat(this.$1F
								.getDataTreeValue('cmi.core.score.raw')) >= parseFloat(this.$1F
								.getDataTreeValue('cmi.student_data.mastery_score'))) {
							this.$1F.setDataTreeValue(
									'cmi.core.lesson_status', 'passed',
									false);
						} else {
							this.$1F.setDataTreeValue(
									'cmi.core.lesson_status', 'failed',
									false);
						}
					}
				}
			}
		}
		if (this.$1F.getDataTreeValue('cmi.core.session_time') === (this.$1E['cmi.core.session_time'])['defaultvalue']) {
			this.$1F.setDataTreeValue('cmi.core.session_time', this
					.$28((new Date().getTime() - this.$24) / 1000), false);
		}
		if (this.$1F.getDataTreeValue('cmi.core.session_time') != null
				&& this.$1F.getDataTreeValue('cmi.core.session_time') !== '') {
			this.$1F.setDataTreeValue('cmi.core.total_time', this.$29(
					this.$1F.getDataTreeValue('cmi.core.total_time'),
					this.$1F.getDataTreeValue('cmi.core.session_time')),
					false);
		}
	}
	if (!$0
			&& this.$1F.getLessonstatus() !== this.$1F
					.getDataTreeValue('cmi.core.lesson_status')) {
		this.$1F.setLessonstatus(this.$1F
				.getDataTreeValue('cmi.core.lesson_status'));
		if (this.$1F.getRoot() != null) {
			this.$1F.getRoot().reEvaluate();
		}
	}
	if (this.$1F.getRoot() != null) {
		this.$1F.getRoot().onEventSCO(
				new API_BASE.BaseActivityTreeNodeEventArgs(this.$1F, 1));
	}
};

SCORM_1_3.API_1484_11_LIB.prototype.Initialize = function(param) {
	this.$30 = '0';
	this.$2F = '';
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if ((!this.$2D) && (!this.$2E)) {
			this.$2D = true;
			this.$30 = '0';
			if (this.$2C.getSequencing() != null) {
				this.$2C.getSequencing().status.activityProgressStatus = true;
			}
			this.$2C.setDataTreeValue('cmi.learner_id',
					(typeof themeDisplay != 'undefined' ? themeDisplay.getUserId() : ''),
					'');
			this.$2C.setDataTreeValue('cmi.learner_name',
					(typeof themeDisplay != 'undefined' ? themeDisplay.getUserName() : ''),
					'');
			this.$2C.setDataTreeValue('cmi.session_time',
					(this.$2A['cmi.session_time'])['defaultvalue'], false);
			this.$31 = new Date().getTime();
			API_BASE.LOG.displayMessage('Initialize with param: \'' + param
					+ '\'', this.$30, this.$32(this.$30));
			return 'true';
		} else {
			if (this.$2D) {
				this.$30 = '103';
			} else {
				this.$30 = '104';
			}
		}
	} else {
		this.$30 = '201';
		this.$2F = 'The parameter passed into the Initialize() API method shall be an empty characterstring (\'\').';
	}
	API_BASE.LOG.displayMessage('Initialize with param: \'' + param + '\'',
			this.$30, this.$32(this.$30));
	return 'false';
};

SCORM_1_2.API_LIB.prototype.LMSInitialize = function(param) {
	this.$23 = '0';
	this.$22 = false;
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (!this.$21) {
			this.$21 = true;
			this.$23 = '0';
			this.$1F.setDataTreeValue('cmi.core.student_id',
					(typeof themeDisplay != 'undefined' ? themeDisplay.getUserId() : ''),
					'');
			this.$1F.setDataTreeValue('cmi.core.student_name',
					(typeof themeDisplay != 'undefined' ? themeDisplay.getUserName() : ''),
					'');
			this.$1F.setDataTreeValue('cmi.core.session_time',
					(this.$1E['cmi.core.session_time'])['defaultvalue'],
					false);
			this.$24 = new Date().getTime();
			API_BASE.LOG.displayMessage('LMSInitialize with param: \''
					+ param + '\'', this.$23, this.$25(this.$23));
			return 'true';
		} else {
			//this.$23 = '101';
		}
	} else {
		this.$23 = '201';
	}
	API_BASE.LOG.displayMessage('LMSInitialize with param: \'' + param
			+ '\'', this.$23, this.$25(this.$23));
	return 'false';
};

SCORM_1_2.API_LIB.prototype.LMSFinish = function(param) {
	this.$23 = '0';
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (this.$21 && (!this.$22)) {
			this.$21 = false;
			this.$22 = true;
			var $0 = this.$1F.getDataTreeValue('nav.event');
			this.$27(true);
			if (this.$1F.getRoot() != null) {
				this.$1F.getRoot().onEventSCO(
						new API_BASE.BaseActivityTreeNodeEventArgs(
								this.$1F, 0));
				if (!isNullOrUndefined($0) && $0.length > 0) {
					this.$1F.getRoot().requestNavigation($0);
				} else if (this.$20) {
					this.$1F.getRoot().requestNavigation('continue');
				}
			}
			API_BASE.LOG.displayMessage('LMSFinish with param: \'' + param
					+ '\'', this.$23, this.$25(this.$23));
			if(this.$1F.$2.$1.length==1&& this.$1F.$0==1 && this.$1F.$1.length==0)
			{
				finish_scorm();
			}
			return 'true';
		} else {
			//this.$23 = '301';
		}
	} else {
		this.$23 = '201';
	}
	
	API_BASE.LOG.displayMessage('LMSFinish with param: \'' + param + '\'',
			this.$23, this.$25(this.$23));
	return 'false';
};

SCORM_1_2.API_LIB.prototype.LMSGetValue = function(element) {
	this.$23 = '0';
	if (!this.$21) {
		this.LMSInitialize('');
	}
	if (this.$21) {
		if (isNullOrUndefined(element)) {
			element = '';
		}
		if (element !== '') {
			var $0 = new RegExp(this.$9, 'g');
			var $1 = element.replace($0, '.n.');
			if (!isUndefined(this.$1E[$1])) {
				if ((Type.safeCast(this.$1E[$1], Object))['mod'] !== 'w') {
					element = element.replace($0, '$1.');
					var $2 = this.$1F.getDataTreeValue(element);
					if (!isUndefined($2)) {
						this.$23 = '0';
						API_BASE.LOG.displayMessage('LMSGetValue '
								+ element + ', \'' + $2 + '\'', this.$23,
								this.$25(this.$23));
						return $2;
					} else {
						this.$23 = '201';
					}
				} else {
					this.$23 = (Type.safeCast(this.$1E[$1], Object))['readerror'];
				}
			} else {
				var $3 = '._children';
				var $4 = '._count';
				if ($1.substr($1.length - $3.length, $1.length) === $3) {
					var $5 = $1.substr(0, $1.length - $3.length);
					if (!isUndefined(this.$1E[$5])) {
						this.$23 = '202';
					} else {
						this.$23 = '201';
					}
				} else if ($1.substr($1.length - $4.length, $1.length) === $4) {
					var $6 = $1.substr(0, $1.length - $4.length);
					if (!isUndefined(this.$1E[$6])) {
						this.$23 = '203';
					} else {
						this.$23 = '201';
					}
				} else {
					if (element.startsWith('cmi.')) {
						this.$23 = '201';
					} else {
						this.$23 = '401';
					}
				}
			}
		} else {
			this.$23 = '201';
		}
	} else {
		this.$23 = '301';
	}
	API_BASE.LOG.displayMessage('LMSGetValue ' + element + ', \'\'',
			this.$23, this.$25(this.$23));
	return '';
};

SCORM_1_2.API_LIB.prototype.LMSCommit = function(param) {
	this.$23 = '0';
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (this.$21) {
			this.$27(false);
			API_BASE.LOG.displayMessage('LMSCommit with param: \'' + param
					+ '\'', this.$23, this.$25(this.$23));
			/*
			var $0 = this.$1F.getDataTreeValue('nav.event');
			if ($0 && $0 != 'exitAll' && $0 != 'exit') {
				this.LMSFinish('');
			}
			*/
			return 'true';
		} else {
			this.$23 = '301';
		}
	} else {
		this.$23 = '201';
	}
	API_BASE.LOG.displayMessage('LMSCommit with param: \'' + param + '\'',
			this.$23, this.$25(this.$23));
	return 'false';
};

Player.PersistentStateStorage.prototype.$77 = null;

Player.PersistentStateStorage.prototype.$3 = function($p0) {
	try {
		this.$0.setObjectItem('scormpool', $p0);
		this.$2 = true;
		var element = window;
		var eventName = "scormevent";
		if (document.createEvent) {
			var evt = document.createEvent("HTMLEvents");
		    evt.initEvent(eventName, false, true);
		    element.dispatchEvent(evt);
		} else {
			var clicker = document.getElementById('clicker');
			if (clicker == null) {
				var clicker = document.createElement('a');
				clicker.setAttribute('id', 'clicker');
				clicker.setAttribute('href', '#');
				clicker.innerHTML = '__';
				clicker.style.display = 'none';
				document.getElementById('placeholder_clicker').appendChild(clicker);
			}
			var evt = document.createEventObject();
			evt.eventName = "click";
			clicker.fireEvent("onclick", evt);
		}
	} catch ($0) {
		alert('Error! Can\'t save data to Local Storage. Problem can be related to storage size limit.');
		this.$2 = false;
	}
};

Player.ContentPlayer.prototype.$1B = function() {
	this.$7 = document.getElementById('navigationContainer');
	this.$77 = document.getElementById('navigationContainer2');
	this.$8 = Delegate.create(this, this.$1D);
	if (isNullOrUndefined(this.$7)) {
		this.$7 = document.createElement('div');
		this.$7.id = 'navigationContainer';
		this.$7.style.display = 'none';
		
		this.$12 = this.$1C('btnExit', PlayerConfiguration.BtnExitLabel);
		this.$13 = this.$1C('btnExitAll',
				PlayerConfiguration.BtnExitAllLabel);
		this.$14 = this.$1C('btnAbandon',
				PlayerConfiguration.BtnAbandonLabel);
		this.$15 = this.$1C('btnAbandonAll',
				PlayerConfiguration.BtnAbandonAllLabel);
		this.$16 = this.$1C('btnSuspendAll',
				PlayerConfiguration.BtnSuspendAllLabel);
		var $0 = document.getElementById('placeholder_navigationContainer');
		if (!isNullOrUndefined($0)) {
			$0.appendChild(this.$7);
		}
	}
	if (isNullOrUndefined(this.$77)) {
		this.$77 = document.createElement('div');
		this.$77.id = 'navigationContainer2';
		this.$77.style.display = 'none';
		this.$10 = this.$1C('btnPrevious',
				PlayerConfiguration.BtnPreviousLabel);
		this.$11 = this.$1C('btnContinue',
				PlayerConfiguration.BtnContinueLabel);
		var $0 = document.getElementById('placeholder_navigationContainer2');
		if (!isNullOrUndefined($0)) {
			$0.appendChild(this.$77);
		}
	}
};

Player.ContentPlayer.prototype.$1C = function($p0, $p1) {
	if ($p0 == 'btnPrevious' || $p0 == 'btnContinue') {
		var $00 = document.createElement('div');
		$00.id = $p0 + '-link';
		var $0 = document.createElement('a');
		$0.setAttribute('id', $p0);
		$0.setAttribute('href', '#');
		$0.innerHTML = $p1;
		$0.style.display = 'none';
		$0.attachEvent('onclick', this.$8);
		$00.appendChild($0);
	
		this.$77.appendChild($00);
		return $0;
	} else {
		var $0 = document.createElement('input');
		$0.setAttribute('id', $p0);
		$0.setAttribute('type', 'button');
		$0.setAttribute('value', $p1);
		$0.style.display = 'none';
		$0.attachEvent('onclick', this.$8);
		
		this.$7.appendChild($0);
		return $0;
	}
};

Player.ContentPlayer.prototype.$1D = function() {
	var $0 = window.event.srcElement;
	if (isNullOrUndefined($0)) {
		return;
	}
	if (this.$F) {
		return;
	}
	if ($0.id === 'btnPrevious') {
		this.$9.requestNavigation('previous');
		if (window.event.preventDefault) {
     	 	window.event.preventDefault();
     	} else {
     		window.event.returnValue = false;
     	}
	} else if ($0.id === 'btnContinue') {
		this.$9.requestNavigation('continue');
		if (window.event.preventDefault) {
     	 	window.event.preventDefault();
     	} else {
     		window.event.returnValue = false;
     	}
	} else if ($0.id === 'btnExit') {
		this.$9.requestNavigation('exit');
		this.$12.disabled = true;
		this.$14.disabled = true;
	} else if ($0.id === 'btnExitAll') {
		this.$9.requestNavigation('exitAll');
	} else if ($0.id === 'btnAbandon') {
		this.$9.requestNavigation('abandon');
		this.$12.disabled = true;
		this.$14.disabled = true;
	} else if ($0.id === 'btnAbandonAll') {
		this.$9.requestNavigation('abandonAll');
	} else if ($0.id === 'btnSuspendAll') {
		this.$9.requestNavigation('suspendAll');
	}
};

Player.ContentPlayer.prototype.toggleScorm = function() {
	var evt = window.event;
	document.getElementById('treeContainer').style.display = 'block';
	var A = AUI();
	var menuA = A.one('#open-close-scorm-menu');
	A.use('anim', 'anim-easing', function() {
		var coapagar = new A.Anim(
	      {
		    node: '#treeContainer',
		    to: {
		        opacity: 0.0
		    },
		    duration: 0.3,
		    easing:   A.Easing.easeIn
		  }
		);
		var apagar = new A.Anim(
		  {
		    node: '#placeholder_treeContainer',
		    to: {
		        /*height:  A.one('#placeholder_barContainer').get('offsetHeight') + 2*/
		    	width:  A.one('#placeholder_barContainer').get('offsetWidth') + 2
		    },
		    duration: 0.8,
		    easing:   A.Easing.easeIn,
		    on: {
		    	start: function() {
		    		menuA.setAttribute('class', 'open-scorm-menu');
		    		coapagar.run();
		    	}
		    }
		  }
		);
		var coencender = new A.Anim(
	      {
		    node: '#treeContainer',
		    to: {
		        opacity: 1.0
		    },
		    duration: 1.0,
		    easing:   A.Easing.easeIn
		  }
		);
		var encender = new A.Anim(
		  {
		    node: '#placeholder_treeContainer',
		    to: {
		    	width:  A.one('#placeholder_barContainer').get('offsetWidth') + A.one('#treeContainer').get('offsetWidth')
		    	
		    },
		    duration: 0.5,
		    easing:   A.Easing.easeIn,
		    on: {
		    	start: function() {
		    		menuA.setAttribute('class', 'close-scorm-menu');
		    		coencender.run();
		    	}
		    }
		  }
		);
		if (menuA.getAttribute('class') != 'open-scorm-menu') {
			apagar.run();
			document.getElementById('treeContainer').style.display = 'none';
		} else {
			document.getElementById('treeContainer').style.display = 'block';
			encender.run();
		}
	});
	if (evt.preventDefault) {
		evt.preventDefault();
	}
	evt.returnValue = false;
};

Player.ContentPlayer.prototype.$1E = function() {
	this.$5 = document.getElementById('treeContainer');
	if (isNullOrUndefined(this.$5)) {
		this.$5 = document.createElement('div');
		this.$5.id = 'treeContainer';
		var togglerContainer = document.createElement('div');
		togglerContainer.id = 'placeholder_barContainer';
		togglerContainer.style.display = 'block';
		var toggler = document.createElement('a');
		toggler.id = 'open-close-scorm-menu';
		toggler.href = '#';
		
		if (this.$9.isSingleItem()) {
			toggler.className = 'open-scorm-menu';
      	} else {
      		toggler.className = 'close-scorm-menu';
      	}
		
		toggler.attachEvent('onclick', Delegate.create(this, this.toggleScorm));
		togglerContainer.appendChild(toggler);
		
		var $0 = document.getElementById('placeholder_treeContainer');
		if (isNullOrUndefined($0)) {
			document.body.appendChild(this.$5);
			if (this.$9.isSingleItem()) {
				this.$5.style.opacity = 0.0;
			}
		} else {
			$0.appendChild(togglerContainer);
			$0.appendChild(this.$5);
			if (this.$9.isSingleItem()) {
				this.$5.style.opacity = 0.0;
				$0.style.display = 'none';
			}
		}
		var $1 = new ControlsCollection.TreeView(this.$5,
				PlayerConfiguration.TreeMinusIcon,
				PlayerConfiguration.TreePlusIcon);
		this.$9
				.scan(
						this.$9.getOrganization(),
						Delegate
								.create(
										this,
										function($p1_0) {
											var $1_0;
											if ($p1_0 !== this.$9
													.getOrganization()) {
												var $1_1 = ($p1_0
														.isVisible()) ? '#'
														: null;
												if (this.$2 === 1) {
													$1_1 = (isNullOrUndefined($p1_0
															.getUrl()) || $p1_0
															.getUrl()
															.trim().length <= 0) ? null
															: $1_1;
												} else if (this.$2 === 2) {
													var $1_2 = $p1_0;
													if (!$1_2
															.getParentSequencing().choice) {
														$1_1 = null;
													} else if (isNullOrUndefined($1_2
															.getScormType())
															&& !$1_2
																	.getSequencing().flow) {
														$1_1 = null;
													}
												}
												if ($p1_0.isLeaf()) {
													$1_0 = ($p1_0
															.getParent()
															.getData())
															.addNode(
																	$p1_0
																			.getTitle(),
																	$1_1,
																	PlayerConfiguration.TreeLeafIcon);
												} else {
													$1_0 = ($p1_0
															.getParent()
															.getData())
															.addNode(
																	$p1_0
																			.getTitle(),
																	$1_1);
												}
												if ($1_1 != null) {
													this.$18.add($1_0);
												}
											} else {
												$1_0 = $1;
											}
											$p1_0.setData($1_0);
											$1_0.set_userData($p1_0);
											return true;
										}), null);
		$1.add_nodeClick(Delegate.create(this, this.$21));
	}
};

Player.ContentPlayer.prototype.$22 = function($p0, $p1) {
	if ($p1.get_eventType() === 5 || $p1.get_eventType() === 3
			|| $p1.get_eventType() === 4) {
		if (this.$1 && this.$C != null) {
			API_BASE.LOG.displayMessage('Unloading '
					+ this.$C.getScormType() + ': '
					+ this.$C.getIdentifier(), '0', null);
			
		}
		if ($p1.get_eventType() === 5) {
			this.$C = null;
			this.$B = null;
			this.$D = null;
			
			this.hidePlayer(!this.$1);
			document.getElementById('open-close-scorm-menu').style.display = 'none';
			if (window.opener) {
				window.close();
			} else {
				if (typeof finish_scorm != 'undefined') {
					finish_scorm(null);
				}
			}
			if (this.$1) {
				API_BASE.LOG.displayMessage('End Session!', '0', null);
			}
		} else {
			if (this.$B != null && this.$C.getScormType() === 'sco'
					&& !this.$B.isInitAttempted()) {
				return;
			}
		}
		if ($p1.get_eventType() !== 4) {
			this.$F = true;
			this.$3.attachEvent('onload', this.$4);
		} else {
			if (isNullOrUndefined(this.$C)) {
				API_BASE.LOG
						.displayMessage('No SCO to deliver!', '0', null);
				this.$23();
			}
			this.$12.disabled = true;
			this.$14.disabled = true;
		}
		this.$3.src = '/liferaylms-portlet/blank.html';
	} else if ($p1.get_eventType() === 1) {
		if (this.$B != null) {
			if (!this.$B.isFinishAttempted()) {
				this.$23();
			}
			if (this.$17 != null) {
				this.$17.saveItemCMI(this.$C.getIdentifier(), this.$C
						.getDataTree());
				if (typeof update_scorm != 'undefined') {
					update_scorm(null);
				}
			}
		}
	} else if ($p1.get_eventType() === 0) {
		if (this.$17 != null) {
			if (this.$2 === 1) {
				this.$17.saveState(null, (this.$9).getStoredStatuses(),
						null, null, null);
			} else if (this.$2 === 2) {
				var $0 = this.$9;
				this.$17
						.saveState(
								$0.getADLCPData(),
								$0.getStoredStatuses(),
								($0.savedSuspendedActivity != null) ? null
										: ($0.isObjectivesglobaltoSystem()) ? $0
												.getClonedGlobalObjectives()
												: null,
								($0.savedSuspendedActivity != null) ? $0.savedSuspendedActivity
										.getIdentifier()
										: null,
								($0.savedSuspendedActivity != null) ? $0
										.getClonedGlobalObjectives() : null);
			}
			if (typeof update_scorm != 'undefined') {
				update_scorm(null);
			}
		}
	} else if ($p1.get_eventType() === 2) {
		this.$28($p1.get_treeNode());
	}
};

Player.ContentPlayer.prototype.$26 = function() {
	if (this.$1) {
		API_BASE.LOG.displayMessage('Loading ' + this.$C.getScormType()
				+ ': ' + this.$C.getIdentifier(), '0', null);
	}
	this.$23();
	var regexp = /^(http(?:s)?\:\/\/[a-zA-Z0-9\-]+(?:\.[a-zA-Z0-9\-]+)*\.[a-zA-Z]{2,6}(?:\/?|(?:\/[\w\-]+)*)(?:\/?|\/\w+\.[a-zA-Z]{2,4}(?:\?[\w]+\=[\w\-]+)?)?(?:\&[\w]+\=[\w\-]+)*)$/;
	if (regexp.test(this.$C.getUrl())) { 
		this.$3.src = this.$C.getUrl();
	} else {
		var urls = this.$C.getUrl().split(/(https?\:\/\/)/);
		if (urls.length > 3) {
			this.$3.src = urls[3] + urls[4];
		} else {
			this.$3.src = this.$C.getUrl();
		}
	}
};

Player.ContentPlayer.prototype.$28 = function($p0) {
	if ($p0 != null) {
		var $0 = this.$D;
		if (this.$17 != null) {
			$p0.setDataTree(this.$17.getItemCMI($p0.getIdentifier()));
		}
		if (this.$2 === 1) {
			this.$B = new SCORM_1_2.API_LIB($p0);
		} else if (this.$2 === 2) {
			this.$B = new SCORM_1_3.API_1484_11_LIB($p0);
		}
		this.$C = $p0;
		this.$D = this.$C.getData();
		this.$9.setActiveAPI(this.$B);
		if ($0 != null && this.$E != null) {
			$0.getIcon().src = this.$E;
		}
		if (!isNullOrUndefined(PlayerConfiguration.TreeActiveIcon)
				&& PlayerConfiguration.TreeActiveIcon.length > 0
				&& !isNullOrUndefined(this.$D.getIcon())) {
			this.$E = this.$D.getIcon().src;
			this.$D.getIcon().src = PlayerConfiguration.TreeActiveIcon;
		}
		var $1 = $p0.getHideLMSUI();
		if (!isNullOrUndefined(this.$10)) {
			this.$10.style.display = (this.$9.isSingleItem() || $1
					.contains('previous')) ? 'none' : 'inline';
			this.$10.disabled = false;
		}
		if (!isNullOrUndefined(this.$11)) {
			this.$11.style.display = (this.$9.isSingleItem() || $1
					.contains('continue')) ? 'none' : 'inline';
			this.$11.disabled = false;
		}
		if (!isNullOrUndefined(this.$13)) {
			this.$13.style.display = ($1.contains('exitall')) ? 'none'
					: 'inline';
			this.$13.disabled = false;
		}
		
		if (this.$10.style.display == 'none' && this.$11.style.display == 'none') {
			this.$77.style.display = 'none';
		} else {
			this.$77.style.display = 'block';
		}

		if (!this.$F) {
			this.$26();
		}
	}
};

Player.ContentPlayer.prototype.adjustPlayer = function() {
	if(window.name=='scormactivity')
	{
		//QQQ ajusta el player
		  height=window.innerHeight
         || document.documentElement.clientHeight
         || document.body.clientHeight;
 		var nav1=0;
 		if(document.getElementById("placeholder_navigationContainer")!= null)
 		{
 			nav1=document.getElementById("placeholder_navigationContainer").clientHeight;
 		
 		}
 		var nav2=0;
 		if(document.getElementById("placeholder_navigationContainer2")!= null)
 		{
 			nav2=document.getElementById("placeholder_navigationContainer2").clientHeight;
 		
 		}
 		var iframeHeight=height-nav1-nav2-20;
 		iframeHeight=iframeHeight+"px";
 		document.getElementById("placeholder_contentIFrame").style.height=iframeHeight;
 		document.getElementById("contentIFrame").style.height=iframeHeight;
	}
	else
	{
		var iframe = document.getElementById('placeholder_contentIFrame');
		var iFrameID = document.getElementById('contentIFrame');
		iFrameID.style.height = "";
		iFrameID.style.height = (iFrameID.contentWindow.document.body.scrollHeight > 200 ? iFrameID.contentWindow.document.body.scrollHeight + 50 : 688) + "px";
		iframe.style.height = iFrameID.style.height;
	}
	/*
	var frameset = iFrameID.contentWindow.document.getElementsByTagName('frameset');
	if (frameset != null) {
		var iframes = iFrameID.contentWindow.document.getElementsByTagName('frame');
		for (var i = 0; i < iframes.length; i++) {
			var ifra = iframes[i];
			ifra.style.height = ifra.contentWindow.document.body.scrollHeight + "px";
			
			if (parseInt(iframe.style.height.replace('px', '')) < parseInt(ifra.style.height.replace('px', ''))) {
				iFrameID.style.height = "";
				iFrameID.style.height = ifra.style.height;
				iframe.style.height = ifra.style.height;
			}
		}
	}
	*/
};

Player.ContentPlayer.prototype.checkPlayer = function() {
   	var treeContainer = document.getElementById('treeContainer');
   	
   	var imgs = treeContainer.getElementsByTagName('img');
   	var linkToClickIndex = -1;
   	var initiated = false;
   	for (var i = 0; i < imgs.length; i++) {
   		var img = imgs[i];
   		if (img.src.endsWith('select.gif')) {
   			initiated = true;
   			break;
   		}
   		if (linkToClickIndex == -1 && img.src.endsWith('leaf.gif')) {
   			linkToClickIndex = i;
   		}
   	}
   	
   	if (!initiated) {
   		var links = treeContainer.getElementsByTagName('a');
   		var link = links[linkToClickIndex];
   		var event;
   	    if (document.createEvent) {
   	      event = document.createEvent("HTMLEvents");
   	      event.initEvent("click", true, true);
   	    } else {
   	      event = document.createEventObject();
   	      event.eventType = "click";
   	    }
   	  
   		if (document.createEvent) {
   		  link.dispatchEvent(event);
   		} else {
   		  link.fireEvent("onclick", event);
   		}
   		links[linkToClickIndex].dispatchEvent();
   	}
};

Player.ContentPlayer.prototype.showPlayer = function() {
	if (this.$2 !== 0) {
		this.$3.style.display = 'block';
		this.$5.style.opacity = (!this.$9.isSingleItem()) ? 1.0 : 0.0;
		this.$5.style.display = (!this.$9.isSingleItem()) ? 'block' : 'none';
		this.$7.style.display = 'block';
		if (this.$10.style.display == 'none' && this.$11.style.display == 'none') {
			this.$77.style.display = 'none';
		} else {
			this.$77.style.display = 'block';
		}
		if (!isNullOrUndefined(this.$6)) {
			this.$6.style.display = (this.$1) ? 'block' : 'none';
		}
		
		document.getElementById('contentIFrame').attachEvent('onload', Delegate.create(this, function() {
			this.adjustPlayer();
			this.checkPlayer();
		}));
	}
};

Player.ContentPlayer.prototype.hidePlayer = function(includeDebugger) {
	if (this.$2 !== 0) {
		this.$3.style.display = 'none';
		this.$5.style.display = 'none';
		this.$7.style.display = 'none';
		this.$77.style.display = 'none';
		if (includeDebugger && !isNullOrUndefined(this.$6)) {
			this.$6.style.display = 'none';
		}
	}
};


Player.PersistentStateStorage.createClass('Player.PersistentStateStorage');
Player.ContentPlayer.createClass('Player.ContentPlayer');
PlayerConfiguration.createClass('PlayerConfiguration');
Run.createClass('Run');
PlayerConfiguration.Debug = false;
PlayerConfiguration.StorageSupport = false;
PlayerConfiguration.TreeMinusIcon = null;
PlayerConfiguration.TreePlusIcon = null;
PlayerConfiguration.TreeLeafIcon = null;
PlayerConfiguration.TreeActiveIcon = null;
PlayerConfiguration.BtnPreviousLabel = 'Previous';
PlayerConfiguration.BtnContinueLabel = 'Continue';
PlayerConfiguration.BtnExitLabel = 'Exit';
PlayerConfiguration.BtnExitAllLabel = 'Exit All';
PlayerConfiguration.BtnAbandonLabel = 'Abandon';
PlayerConfiguration.BtnAbandonAllLabel = 'Abandon All';
PlayerConfiguration.BtnSuspendAllLabel = 'Suspend All';
Run.$0 = null;
Run.$1 = null;
Run.$2 = null;