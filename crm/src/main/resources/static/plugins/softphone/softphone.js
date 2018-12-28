/** 
 * 创建人：詹行
 * 创建时间：2018年1月22日
 * @version 1.0
 */
$(function(){
	if($.constant.user.USER_EXTNUM == null || $.constant.user.USER_EXTNUM == "" ){
		$.bigBox({
			title : "提示",
			content : "您没有关联分机号，请联系管理员",
			color : "#CA2121",
			timeout: 2000,
			icon : "fa fa-exclamation-triangle bounce animated"
		});
		return ;
	}
	/*-------------------------------------软电话Start---------------------------------------*/
	var callserver ;
	var call ;
	var chatMessage = "" ;
	var vdn = "120" ;
	var ext = "601" ;//座席分机号
	//var ext = 601 ;//座席分机号
	var agentNum = "100" ;//座席工号
	console.log(ext);
	
	var timer;//计时器
	var phoneNumber = '';
	var phoneNumber1 = '';
	var keeping = false ; 
	var phoneIng = false ;//false表示没有通话，true表示通话中
	var transferIng = false ; //咨询成功震铃即为true
	var meeting = false ; //true表示可以建立会议
	var callId1 = "";
	var callId2 = "";
	//拨号盘start
	var phoneNum = {
	    '48':'0',
	    '49':'1',
	    '50':'2',
	    '51':'3',
	    '52':'4',
	    '53':'5',
	    '54':'6',
	    '55':'7',
	    '56':'8',
	    '57':'9',
	    '96':'0',
	    '97':'1',
	    '98':'2',
	    '99':'3',
	    '100':'4',
	    '101':'5',
	    '102':'6',
	    '103':'7',
	    '104':'8',
	    '105':'9'
	}	
	callserver=$.hubConnection("http://172.17.3.225:811/signalr");//连接软电话服务器
	call = callserver.createHubProxy("ctiHub");
	callserver.stateChanged(connectionStateChanged);
	callserver.connectionSlow(connectionSlow);
	call.on("sendResponse", sendResponse);//4.3	回送给坐席要求的动作情况
	call.on("onInbound", onInbound);//4.1	来电弹屏事件
	call.on("callStatusBack", callStatusBack);//4.6	坐席话务状态回送【班长席】
	call.on("toIdle", toIdle);//示闲
	call.on("toLogIn", toLogIn);//签入
	call.on("onOutbound", onOutbound);//4.2	去电弹屏事件
	//call.on("agentStatusBack", agentStatusBack);//4.7	坐席状态回送【班长席】
	callserver.start().done(function() {
		//注册
		call.invoke("req_Register",ext).done(function(val){
			if(val){
				$.smallBox({
				     title : "提示",
				     content : "注册成功",
				     color : "#29B871",
				     timeout: 2000,
				     iconSmall : "fa fa-check  bounce animated"
			    });
			}else{
				$.bigBox({
					title : "提示",
					content : "注册失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
		});
	});
	/**************************************事件推送区START************************************/
	//网络延迟时
	function connectionSlow(){
		$.bigBox({
			title : "提示",
			content : "请检查网络状况，如丢包严重，请通知IT人员，以免影响在线使用，谢谢！",
			color : "#CA2121",
			timeout: 2000,
			icon : "fa fa-exclamation-triangle bounce animated"
		});
	}
	
	//根据在线状态提示相应的操作
	function connectionStateChanged(state){
		  var stateConversion = {0: 'connecting', 1: 'connected', 2: 'reconnecting', 4: 'disconnected'};
		    console.log('SignalR state changed from: ' + stateConversion[state.oldState]
		     + ' to: ' + stateConversion[state.newState]);
		   if(state.newState==4){	
			   phoneNumber = '';
			   phoneNumber1 = '';
			   callId1 = "" ;
			   callId2 = "" ;
			   keeping = false ;
			   phoneIng = false ;
			   transferIng = false ;
			   clearInterval(timer);//清空计时器
				$.bigBox({
					title : "提示",
					content : "您与服务器已中断，请检测网络配置，是否出现问题，如无问题,系统将会在20秒内自行连接，请稍候。。。，如连接不上请联系管理员，谢谢！",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			   setTimeout(function(){
				   callserver.start().done(function() {});
				   },5000);
		   }
		   if(state.newState==2){
			   $.bigBox({
					title : "提示",
					content : "网络出现故障，系统正在重连，请稍候...",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
		   }
		   if(state.newState==1){
				$.smallBox({
					title : "提示",
					content : "服务器连接成功",
					color : "#29B871",
					timeout: 2000,
					iconSmall : "fa fa-check  bounce animated"
				});
		   }

	}
	
	
	function sendResponse(retValue,request,cause){
		console.log(retValue+"==========="+request+"**************");
		switch(request){
		case 0 ://来电应答
			if(retValue=="0"){//应答成功
				setTime(0);//开始计时
				$('.phoneanswer').css('background','#fff none repeat scroll 0 0');
				$('.phoneanswer').html('');
				$('.endListen').css('display','none');			
			}else{
				$.bigBox({
					title : "提示",
					content : "应答失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 1 ://挂断座机
			if(retValue=="0"){
				phoneIng = false ;
			}else{
				$.bigBox({
					title : "提示",
					content : "挂断座机失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 2 ://保持
			if(retValue=="0"){
				keeping = true ;//状态变为保持
				//保持图标变亮
				$('.hold img').prop('src','img/softphone/phone/keepafter.png');
		        $('.hold p').html('取消').css('color','#20ff3a');
			}else{
				$.bigBox({
					title : "提示",
					content : "保持失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 3 ://取消保持
			if(retValue=="0"){
				keeping = false ; 
				$('.hold img').prop('src','img/softphone/phone/keep.png');
		        $('.hold p').html('保持').css('color','#bbb');
			}else{
				$.bigBox({
					title : "提示",
					content : "取消保持失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 4 :
			if(retValue=="0"){//转接成功
				$('.transfer img').prop('src','img/softphone/phone/transfer.png');//咨询图标变暗
				$('.transfer').find('p').css('color','#bbb');
				phoneIng = false ;//转为非通话状态
				transferIng = false;//咨询状态为false	
				$('.callOut1').html('即将转接');
				$('.callOut').html('通话即将结束');
			    $('.callTime').html('');//清空时间
			    clearInterval(timer);//清空计时器
			    setTimeout(function(){//延迟半秒执行
			        $('.callOut1').html('转接成功');
			        $('.callOut').html('通话结束');
			        setTimeout(function(){
			        	$('.Callerid').css('display','none');
			            $('.Callerid1').css('display','none');
			            $('.dialPhone').css('display','none');
			        },500);
			    },500);		
			}else{//转接失败
				$('.transfer img').prop('src','img/softphone/phone/transfer.png');//咨询图标变暗
				$('.transfer').find('p').css('color','#bbb');
				transferIng = false;//咨询状态为false
				$('.callOut1').html('转接失败');
				  setTimeout(function(){//延迟半秒执行
				        $('.callOut1').html('');
				        setTimeout(function(){
				            $('.Callerid1').css('display','none');
				            $('.dialPhone').css('display','none');
				        },500);
				    },500);
				  $.bigBox({
						title : "提示",
						content : "转接失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
			}		
			break;
		case 5 ://双步会议
			if(retValue=="0"){//会议已经建立，对方已经震铃
				meeting = true ;//表示会议已经建立
				transferIng = false;//咨询状态为false
			}else{
				$('.meeting img').prop('src','img/softphone/phone/tripartite.png') ;//咨询图标变暗
				$('.meeting').find('p').css('color','#bbb') ;
				$('.callOut1').html('会议连接失败') ;
				meeting = false ;
				  setTimeout(function(){//延迟半秒执行
				        $('.callOut1').html('会议关闭') ;
				        setTimeout(function(){
				            $('.Callerid1').css('display','none') ;
				            $('.dialPhone').css('display','none') ;
				        },500);
				    },500);
				  $.bigBox({
						title : "提示",
						content : "会议失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
			}		
			break;
		case 6 ://咨询
			if(retValue=="0"){//咨询连接建立时
				 transferIng = true;//咨询状态为false
				 $('.callOut1').html('已连接,正在震铃') ;
			}else{
				$('.callOut1').html('无应答') ;
			    setTimeout(function(){//延迟半秒执行
			        $('.callOut1').html('通话结束') ;
			        transferIng = false;
			        setTimeout(function(){
			            $('.Callerid1').css('display','none') ;
			            $('.dialPhone').css('display','none') ;
			        },500);
			    },500);
			    $.bigBox({
					title : "提示",
					content : "咨询失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 7 :
			//retvaule(retValue,"单步转接");
			break;
		case 8 :
			//retvaule(retValue,"单步会议");
			break;
		case 9 :
			if(chatMessage == "签入"){
				if(retValue=="0"){//签入成功
					 $('.loginShow img').prop('src','img/softphone/phone/chenggong.png') ;
			         $('.loginShow p').html('签出').css('color','#20ff3a') ;
			         $('.free img').prop('src','img/softphone/phone/freeafter.png') ;
					 $('.free').find('p').css('color','#20ff3a') ;
				}else{
					$.bigBox({
						title : "提示",
						content : "签入失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
				}
			}else if(chatMessage == "签出"){
				if(retValue=="0"){//签出成功
					 $('.loginShow img').attr('src','img/softphone/phone/weidenglu.png') ;
			         $('.loginShow p').html('签入').css('color','#bbb') ;
			         $('.busy img').attr('src','img/softphone/phone/busy.png') ;
			         $('.busy p').css('color','#bbb') ;
			         $('.free img').attr('src','img/softphone/phone/free.png') ;
					 $('.free').find('p').css('color','#bbb') ;
					 $('.answer img').attr('src','img/softphone/phone/Listening.png') ;
					 $('.answer').find('p').css('color','#bbb') ;
					 $('.split img').attr('src','img/softphone/phone/split.png') ;
					 $('.split').find('p').css('color','#bbb') ;
					 $('.dialout img').attr('src','img/softphone/phone/dial.png') ;
					 $('.dialout').find('p').css('color','#bbb') ;
					 $('.hold img').attr('src','img/softphone/phone/keep.png') ;
					 $('.hold').find('p').css('color','#bbb') ;
					 $('.transfer img').attr('src','img/softphone/phone/transfer.png') ;
					 $('.transfer').find('p').css('color','#bbb') ;
					 $('.meeting img').attr('src','img/softphone/phone/tripartite.png') ;
					 $('.meeting').find('p').css('color','#bbb') ;
					 $('.satisfaction img').attr('src','img/softphone/phone/satisfaction.png') ;
					 $('.satisfaction').find('p').css('color','#bbb') ;
				}else{
					$.bigBox({
						title : "提示",
						content : "签出失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
				}
			}else if(chatMessage == "示忙"){
				if(retValue=="0"){//示忙成功
				    $('.busy img').prop('src','img/softphone/phone/busyafter.png') ; 
				    $('.busy').find('p').css('color','#20ff3a') ;
				    $('.free img').prop('src','img/softphone/phone/free.png') ;
				    $('.free').find('p').css('color','#bbb') ;
				}else{
					$.bigBox({
						title : "提示",
						content : "示忙失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
				}		
			}else if(chatMessage == "示闲"){
				if(retValue=="0"){//示闲成功
					$('.free img').prop('src','img/softphone/phone/freeafter.png');
					$('.free').find('p').css('color','#20ff3a');
					$('.busy img').prop('src','img/softphone/phone/busy.png');
				    $('.busy').find('p').css('color','#bbb');
				}else{
					$.bigBox({
						title : "提示",
						content : "示闲失败，请联系管理员",
						color : "#CA2121",
						timeout: 2000,
						icon : "fa fa-exclamation-triangle bounce animated"
					});
				}
			}
			break;
		case 10 ://外呼
			if(retValue=="0"){//外呼成功震铃时
				 //显示来电显示页面
				 $('.Callerid').css('display','block');
				 $('.callPhone').html(phoneNumber);
				 $('.Telephone').css('display','block');
				 // $('.phoneanswer').css('display','none');
				 //设置背景颜色为空
				 //$('.phoneanswer').css('dispaly','block');
				 $('.phoneanswer').css('background','#fff none repeat scroll 0 0');
				 $('.phoneanswer').html('');
				 $('.hangup_two').css('display','block');
				 //$('.Hangup').css('display','block');
	             $('.endListen').css('display','none');
	             $('.callOut').html('正在呼叫...');
			}else{
				$.bigBox({
					title : "提示",
					content : "外呼失败，请联系管理员",
					color : "#CA2121",
					timeout: 2000,
					icon : "fa fa-exclamation-triangle bounce animated"
				});
			}
			break;
		case 11 : //取消呼叫时
			//if(retValue=="0"){}else{}
			break;
	}
	}
	
	/**
	 * 返回的事件
	 * @param ext 分机号   
	 * @param agentId  座席工号
	 * @param callStatus 分机状态
	 * @param agentStatus 座席状态
	 */
	function callStatusBack(ext,agentId,callStatus,agentStatus,callId){
		  console.log(callStatus+"////////////////////////");
		   var status="";
		   if(agentStatus==0){
			   status="示忙";
		   }else if(agentStatus==1){
			   status="没有登录";
		   }else if(agentStatus==2){
			   status="空闲状态";
		   }else if(agentStatus==3){
			   status="正在通话";
		   }else if(agentStatus==4){
			   status="ACW";
		   }else if(agentStatus==5){
			   status="状态未知";
		   }	
		   switch(callStatus){
		   case 0 ://没有话路状态状态(客户挂断)
			    if(agentStatus == 2){//示闲状态
				   $('.free img').prop('src','img/softphone/phone/freeafter.png');
				   $('.free').find('p').css('color','#20ff3a');
			    }else{
				   $('.free img').prop('src','img/softphone/phone/free.png');
				   $('.free').find('p').css('color','#bbb');
			    }
			    clearInterval(timer);//清空计时器
			    $('.dialout img').attr('src','img/softphone/phone/dial.png');						
			    $('.dialout').find('p').css('color','#bbb');
			    $('.hold img').attr('src','img/softphone/phone/keep.png');
				$('.hold').find('p').css('color','#bbb');
				$('.transfer img').attr('src','img/softphone/phone/transfer.png');
				$('.transfer').find('p').css('color','#bbb');
				$('.meeting img').attr('src','img/softphone/phone/tripartite.png');
				$('.meeting').find('p').css('color','#bbb');
			    phoneNumber = '' ;
			    phoneNumber1 = '' ;
			    phoneIng = false ;
			    keeping = false ;
			    transferIng = false ;
			    callId1 = "" ;
				callId2 = "" ;
				$('.callOut1').html('通话即将结束');
				$('.callOut').html('通话即将结束');
				setTimeout(function(){//延迟半秒执行
			        $('.callOut1').html('通话结束');
			        $('.callOut').html('通话结束');
			        setTimeout(function(){
			        	$('.Callerid').css('display','none');
			            $('.Callerid1').css('display','none');
			            $('.dialPhone').css('display','none');
			        },500);
			    },500);								
				break;
			case 1 ://震铃返回的值
				if(phoneIng){
					if(callId1 != "" && callId2 == ""){
						callId2 = callId ;
					}else{
						callId1 = callId ;
					}
				}					
				break;
			case 2 ://外呼通话已经应答 ,包含电话两端的应答情况，已经接通电话了
				phoneIng=true;
				setTime(0);//开始计时
				if(transferIng){//咨询已经建立连接，且双方已经通话
					$('.callOut1').html("正在通话");
					//transferPhoneIng = true ;//已经连接成功
				}else{
					$('.callOut').html("来电地址");
					setTime(0);//开始计时
				}
				break;
			case 3 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 外呼状态");
				break;
			case 4 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 保持状态");
				break;
			case 5 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 解除保持状态");
				break;
			case 6 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 正在咨询状态");
				break;
			case 7 ://返回，表示座席正在会议状态，已经进行
				meetThree = true ;
				$('.callOut1').html('正在三方通话');
				clearInterval(timer);//清空计时器
				$('.Callerid').css('display','none');
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 正在会议状态");
				break;
			case 8 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 转接状态");
				break;
			case 9 :		
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 失败状态");
				break;
			case 10 :		
				if(callId2 != ""){				
					if(callId1 == callId){//挂断第一路
						callId1 = "";
						$('.minS').css('display','none');
						$('.callOut').html('通话即将结束');
						$('.callTime').html('');//清空时间
						clearInterval(timer);//清空计时器
					    setTimeout(function(){//延迟半秒执行
					        $('.callOut').html('通话结束');
					        setTimeout(function(){
					            $('.Callerid').css('display','none');
					            $('.dialPhone').css('display','none');
					        },500);
					    },500);	
					}else{//挂断第二路
						callId2 = "";
						$('.callOut1').html('通话即将结束');
						setTimeout(function(){//延迟半秒执行
					        $('.callOut1').html('通话结束');
					        setTimeout(function(){
					            $('.Callerid1').css('display','none');
					            $('.dialPhone').css('display','none');
					        },500);
					    },500);	
					}
				}else{//只有callId1的时候
					if(transferIng){//取消咨询成功
						 transferIng = false ;
						 $('.callOut1').html('通话即将结束');
						 setTimeout(function(){//延迟半秒执行
						        $('.callOut1').html('通话结束');
						        setTimeout(function(){
						            $('.Callerid1').css('display','none');
						            $('.dialPhone').css('display','none');
						        },500);
						    },500);
					
					}else{
						phoneIng = false ;
						transferIng = false ;
						callId1 = "" ;
						//挂断第一路
						$('.minS').css('display','none');
						$('.callOut').html('通话即将结束');
						$('.callTime').html('');//清空时间
						clearInterval(timer);//清空计时器
					    setTimeout(function(){//延迟半秒执行
					        $('.callOut').html('通话结束');
					        setTimeout(function(){
					            $('.Callerid').css('display','none');
					            $('.dialPhone').css('display','none');
					        },500);
					    },500);	
					}								
				}		
				break;
			case 11 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> bridge call状态");
				break;
			case 12 :
				//monitoring("坐席话务状态 分机："+ext+ ",座席："+agentId+ " 座席状态："+status+" ==> 未知状态");
				break;
		}
	}

	/* * 来电震铃事件
	 * @param ucid  //电话编号
	 * @param callingNo  //主叫号码
	 * @param calledNo  //被叫号码
	 * @param userData  //客户资料
	 **/
	function onInbound(ucid,callingNo,calledNo,userData){	 
		  console.info(userData+"pppppppppppppppppppppppppp");
		  $(".Callerid").css("display","block");
		  $('.callPhone').html(callingNo);
		  $('.callOut').html("来电地址");
		  $('.callTime').html("震铃中...");
		  $('.phoneanswer').css('background',' #5dca91 none repeat scroll 0 0');
		  $('.phoneanswer').html('接听');
		  clearInterval(timer);
		  window.location.hash = "/ajax/loadForm/51de6d625e9d47b89a74d2c036f3bb76?param="+callingNo ;
	}
	
	/**
	 * 呼出成功返回事件
	 * @param ucid 通话编号
	 * @param callingNo 被叫号码
	 * @param calledNo 主叫号码
	 * @param userData 客户资料
	 */
	function onOutbound(ucid,callingNo,calledNo,userData){
		 $('.callOut').html('');
		// setTime(1);	//开始计算通话时间
	}
	
	function toIdle(){//通知座席需要示闲
		$.smallBox({
		     title : "提示",
		     content : "请示闲",
		     color : "#29B871",
		     timeout: 2000,
		     iconSmall : "fa fa-check  bounce animated"
	    });
	}
	
	function toLogIn(){//通知座席需要签入
		$.smallBox({
		     title : "提示",
		     content : "请签入",
		     color : "#29B871",
		     timeout: 2000,
		     iconSmall : "fa fa-check  bounce animated"
	    });
	}
	/**************************************事件推送区END************************************/
	/**************************************主动调用区START************************************/
	//index{签入0   签出1  示忙2  示闲3	}
	this.setAgentState = function(index){
		if(index == 0){//签入签出时
			var msg =  $('.loginShow p').text();
			if(msg == "签入"){
				chatMessage = "签入" ;
				callserver.start().done(function(){//注册分机号						
					call.invoke("setAgentState",ext,agentNum,"0",'4','').done(function(val){//执行签入													
					});											
				 });
			}else if(msg == "签出"){
				chatMessage = "签出" ;
				callserver.start().done(function() {
					call.invoke("setAgentState",ext,agentNum,"1",'4','').done(function(val){
					});
				 });
			}
		} ; 
		if(index == 2){//示忙操作
			var msg = $('.busy img').attr("src");
			if(msg.indexOf("busyafter") == "-1"){
				chatMessage = "示忙" ;
				callserver.start().done(function(){
					call.invoke("setAgentState",ext,agentNum,2,'4','').done(function(val){
					});
				 });
			}
		};
		if(index == 3){
			var msg = $('.free img').attr("src");
			if(msg.indexOf("freeafter") == "-1"){
				chatMessage = "示闲" ;
				callserver.start().done(function() {
					call.invoke("setAgentState",ext,agentNum,3,'4','').done(function(val){
					});
				 });
			}
		}
	}	
	
	//计算通话时间
	function setTime(time){
	    clearInterval(timer);
	    callTime = time;
	    $('.callTime').html('00:00');
	    $('.telTime').html('00:00');
	    timer = setInterval(function(){
	        callTime+=1;
	        callTimeseconds = callTime%60;
	        callTimeminutes = Math.floor(callTime/60);
	        if(callTimeseconds<10){
	            callTimeseconds ='0'+ callTimeseconds;
	        }else{
	            callTimeseconds = callTimeseconds+'';
	        }
	        if(callTimeminutes<10){
	            callTimeminutes ='0' + callTimeminutes;
	        }else{
	            callTimeminutes = callTimeminutes+'';
	        }
	        $('.callTime').html(callTimeminutes+':'+callTimeseconds);
	        $('.telTime').html(callTimeminutes+':'+callTimeseconds);
	    },1000);
	}

	//挂断分机
	this.dropPhone = function(){
		if(!keeping){//保持中无法挂断分机
			$('.dialout img').attr('src','img/softphone/phone/dial.png');    	 //呼出图标变暗
		    $('.dialout p').css('color','#bbb');
			phoneNumber = '';
			phoneNumber1 = '';
			$('.minS').css('display','none');
			$('.callOut').html('通话即将结束');
		    $('.callTime').html('');//清空时间
		    clearInterval(timer);//清空计时器
		    setTimeout(function(){//延迟半秒执行
		        $('.callOut').html('通话结束');
		        setTimeout(function(){
		            $('.Callerid').css('display','none');
		            $('.dialPhone').css('display','none');
		        },500);
		    },500);
			callserver.start().done(function() {
				call.invoke("req_Drop",ext).done(function(val){});
			 });
		}else{
			$.bigBox({
				title : "提示",
				content : "取消保持后方可挂断分机",
				color : "#CA2121",
				icon : "fa fa-exclamation-triangle bounce animated"
			});
		}
	}
	
	//来电点击接听
	this.answerPhone = function(){
		callserver.start().done(function(){
			call.invoke("req_Answer",ext).done(function(val){});
		});
	}
	
	
	//点击呼出图标时
	this.showDail = function(){
		$('.dialout img').prop('src','img/softphone/phone/dialafter.png');
	    $('.dialout p').css('color','#20ff3a');
		$('.phoneNumber').val('');
		showCall('.call');
	}
	
	//点击呼出电话时
	this.dialPhone = function(){
		var phoneNum = $('.phoneNumber').val();
		phoneNum = $.trim(phoneNumber);
		if(phoneNum!=""){
			//隐藏拨号盘
			$('.dialPhone').css('display','none');
         	callserver.start().done(function() {
        		call.invoke("req_MakeCall",ext,phoneNum,'').done(function(val){            				
        		});
        	});
		}else{
			$.bigBox({
				title : "提示",
				content : "电话号码不能为空！",
				color : "#CA2121",
				timeout: 2000,
				icon : "fa fa-exclamation-triangle bounce animated"
			});
		}
	}
	
	//点击保持时
	this.holdPhone = function(){
		var msg =  $('.hold p').text();
		if(msg=="保持"){
			callserver.start().done(function() {//请求保持
				call.invoke("req_Hold",ext).done(function(val){});
			 });					
		}else{
			callserver.start().done(function() {
				call.invoke("req_UnHold",ext).done(function(val){});
			 });
		}
	}
	//点击咨询时
	this.transferPhone = function(){
		$('.transfer img').prop('src','img/softphone/phone/transferafter.png');
	    $('.transfer').find('p').css('color','#20ff3a');
		$(".dialPhone").css("display","block");
		$('.phoneNumber').val('');
		showCall('.transfer');
	}
	//点击咨询拨号按钮时
	this.transfer = function(){
		var phoneNum = $('.phoneNumber').val();
		phoneNum = $.trim(phoneNumber);
		if(phoneNum!=""){
	         phoneNumber1 = phoneNum ;
	 		//隐藏拨号盘
			 $('.dialPhone').css('display','none');
			 $('.Callerid1').css('display','block');//显示咨询页面
			 $("#meet").css('display',"none");
			 $("#tran").css('display',"block");
			 $('.callPhone1').html(phoneNum);
			 $('.callOut1').html('正在建立连接');
	 		 callserver.start().done(function() {
	 			call.invoke("req_Consult",ext,phoneNum,'').done(function(val){});
	 		 });
		}else{
			$.bigBox({
				title : "提示",
				content : "电话号码不能为空！",
				color : "#CA2121",
				timeout: 2000,
				icon : "fa fa-exclamation-triangle bounce animated"
			});
		}
	}
	
	//点击转接时
	this.transferOther = function(){
		callserver.start().done(function() {
			call.invoke("req_Transfer",ext).done(function(val){});
		 });
	}
	
	//咨询或者会议挂断时
	this.dropOther = function(){	
		if(meetThree){//会议中挂断，则关掉所有通话
			$('.dialout img').attr('src','img/softphone/phone/dial.png');
			$('.dialout').find('p').css('color','#bbb');
			$('.hold img').attr('src','img/softphone/phone/keep.png');
			$('.hold').find('p').css('color','#bbb');
			$('.transfer img').attr('src','img/softphone/phone/transfer.png');
			$('.transfer').find('p').css('color','#bbb');
			$('.meeting img').attr('src','img/softphone/phone/tripartite.png');
			$('.meeting').find('p').css('color','#bbb');
			phoneIng = false ;//false表示没有通话，true表示通话中
			keeping = false ;//true即为电话保持中
			transferIng = false ; //咨询成功震铃即为true
			transferPhoneIng = false ;//true表示已经建立转接条件
			meeting = false ; //true表示可以建立会议
			meetThree = false ;
			callId1 = "" ;
			callId2 = "" ;
			$('.callOut1').html('通话即将结束');
			$('.callOut').html('通话即将结束');
			clearInterval(timer);//清空计时器
			setTimeout(function(){//延迟半秒执行
		        $('.callOut1').html('通话结束');
		        $('.callOut').html('通话结束');
		        setTimeout(function(){
		        	$('.Callerid').css('display','none');
		            $('.Callerid1').css('display','none');
		            $('.dialPhone').css('display','none');
		        },500);
		    },500);	
			callserver.start().done(function() {
				call.invoke("req_Drop",ext).done(function(val){});
			 });	
			return false;
		}
		if(transferIng){//取消咨询
			call.invoke("Req_Reconnect",ext).done(function(val){});
		}
	}
	
	
	//点击会议图标时
	this.meetIng = function(){
		//通话中方可发起会议
	    $('.meeting img').prop('src','img/softphone/phone/tripartiteafter.png');
	    $('.meeting').find('p').css('color','#20ff3a');
		$(".dialPhone").css("display","block");
		$('.phoneNumber').val('');
		phoneNumber = '' ;
		showCall('.threeWay');
	}
	//点击邀请三方参加会议时
	this.meetIngThree = function(){
		transferIng = true ; //咨询状态改为true
		var phoneNum = $('.phoneNumber').val();
		phoneNum = $.trim(phoneNumber);
		if(phoneNum!=""){
	         phoneNumber1 = phoneNum ;
	 		//隐藏拨号盘
			 $('.dialPhone').css('display','none');
			 $('.Callerid1').css('display','block');//显示咨询页面
			 $("#meet").css('display',"block");
			 $("#tran").css('display',"none");
			 $('.callPhone1').html(phoneNum);
			 $('.callOut1').html('正在建立连接');
	 		callserver.start().done(function() {//咨询
	 			call.invoke("req_Consult",ext,phoneNum,'').done(function(val){});
	 		 });
		}else{
			$.bigBox({
				title : "提示",
				content : "电话号码不能为空！",
				color : "#CA2121",
				timeout: 2000,
				icon : "fa fa-exclamation-triangle bounce animated"
			});
		}
	}
	
	//三方会议
	this.meetThreeing = function(){
		callserver.start().done(function() {
			call.invoke("req_Conference",ext).done(function(val){});
		 });
	}
	
	//发送满意度评价
	this.satisfaction = function(){
		callserver.start().done(function() {
			 var callingtel=agentNum+","+"1112"+","+"223"+",,,"+"dddd"+";1";
			call.invoke("Satisfy",ext,vdn,callingtel).done(function(val){});
		 });
	}
	
	//显示拨号盘
	function showCall (name){
	    $('.phoneNumber').val('');
	    $('.callZong').css('display','none');
	    $(name).css('display','block');
	    $('.dialPhone').css({'display':'block','z-index':'6'});
	};
	/**************************************主动调用区END************************************/
	//点击关闭电话键盘
    $('.shutDown').click(function(){
    	$('.dialout img').attr('src','img/softphone/phone/dial.png');    	 //呼出图标变暗
        $('.dialout p').css('color','#bbb');
        $('.transfer img').prop('src','img/softphone/phone/transfer.png');//咨询图标变暗
		$('.transfer').find('p').css('color','#bbb');
		$('.meeting img').prop('src','img/softphone/phone/tripartite.png');
		$('.meeting').find('p').css('color','#bbb');
   	 	phoneNumber="";
        $('.phoneNumber').val('');
        $('.dialPhone').css('display','none');
    })
	
    //键盘输入号码
	$(document).keydown(function(event){
	    if((event.keyCode>47&&event.keyCode<58)||(event.keyCode>95&&event.keyCode<106)){
	             // alert(phoneNum[event.keyCode])
	             phoneNumber+=phoneNum[event.keyCode];
	             $('.phoneNumber').val(phoneNumber);
	         }
	         if(event.keyCode==8||event.keyCode==46){
	             if(phoneNumber.length>0){
	                 phoneNumber=phoneNumber.substring(0,phoneNumber.length-1);
	                 $('.phoneNumber').val(phoneNumber);
	             }
	         }
	 })
	 
	//删除拨号
     $('.delete').click(function(){
         if(phoneNumber.length>0){
             phoneNumber=phoneNumber.substring(0,phoneNumber.length-1);
             $('.phoneNumber').val(phoneNumber);
         }
     })
     //点击软电话键盘
     $('.number li').click(function(){
         phoneNumber += $(this).html();
         $('.phoneNumber').val(phoneNumber);
         return false;
     })	
     
     	//点击隐藏来电显示
	 $('.minSamll').click(function(){
         // answer(1);
         $('.Callerid').css('display','none');
         $('.minS').css('display','block');
     })
     //点击放大来电显示
	 $('.minS').click(function(){
         $('.Callerid').css('display','block');
         $('.minS').css('display','none');
	 })
     
     //拖拽效果start
     function drag(drag){
         $(drag).mousedown(function(e){
              // e.pageX
              var positionDiv = $(this).offset();
              var distenceX = e.pageX - positionDiv.left;
              var distenceY = e.pageY - positionDiv.top;
              onoff=true;
              //alert(distenceX)
              // alert(positionDiv.left);
              $(document).mousemove(function(e){
                 var x = e.pageX - distenceX;
                 var y = e.pageY - distenceY;
                 onoff=false;
                 if(x<0){
                   x=0;
                 }else if(x>$(document).width()-$(drag).outerWidth(true)){
                   x = $(document).width()-$(drag).outerWidth(true);
                 }
                 if(y<0){
                   y=0;
                 }else if(y>$(document).height()-$(drag).outerHeight(true)){
                   y = $(document).height()-$(drag).outerHeight(true);
                 }
                 $(drag).css({
                   'left':x+'px',
                   'top':y+'px'
                 });
                 return false;                 
              });
             $(document).mouseup(function(){
                 $(document).off('mousemove');
             });
             return false;
         });
         return false;
     }
     drag('.minS');
     drag('.dialPhone');
     drag('.Callerid');
     drag('.Callerid1');
    
	/*-------------------------------------软电话END---------------------------------------*/
})

