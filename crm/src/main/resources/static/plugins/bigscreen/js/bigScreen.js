$(function(){
	toastr.options = {  
	        closeButton: true,                                            // 是否显示关闭按钮，（提示框右上角关闭按钮）
	        debug: false,                                                    // 是否使用deBug模式
	        progressBar: true,                                            // 是否显示进度条，（设置关闭的超时时间进度条）
	        positionClass: "toast-bottom-right",              // 设置提示款显示的位置
	        onclick: null,                                                     // 点击消息框自定义事件 
	        showDuration: "300",                                      // 显示动画的时间
	        hideDuration: "1000",                                     //  消失的动画时间
	        timeOut: "1000",                                             //  自动关闭超时时间 
	        extendedTimeOut: "1000",                             //  加长展示时间
	        showEasing: "swing",                                     //  显示时的动画缓冲方式
	        hideEasing: "linear",                                       //   消失时的动画缓冲方式
	        showMethod: "fadeIn",                                   //   显示时的动画方式
	        hideMethod: "fadeOut"                                   //   消失时的动画方式
		    }; 
	var agentno = "a103";
	var password = "Ruixin01";
	var timers = {};
	var callserver = $.hubConnection("http://172.16.21.159:813/signalr");//连接服务器
	callserver.stateChanged(connectionStateChanged);
	callserver.start().done(function(){});//注册分机号
	var call = callserver.createHubProxy("aicHub"); 
	call.on("agentStatusBackEx", agentStatusBackEx);
	call.on("callStatusBackEx", callStatusBackEx);
	call.on("getErrCode", getErrCode);	
	callserver.start().done(function(){//注册分机号	
		call.invoke("reqMonitor",agentno).done(function(val){
		}); 
	});
	/**
	 *坐席信息卡加载方法
	 *@param 
	 */
	function createAgent( agent ){
		//console.log(agent);
		var box = $('<div class="box ani" style="transition-delay: 0.2s;"></div>');
		var content = $('<div class="content clearfix" id='+agent.user+'></div>');
		box.append(content);
		var left = $('<div class="left"><img src="/plugins/bigscreen/img/woman2.png"></div>');
		content.append(left);
		var pp = $('<div class="left pp"></div>');
		content.append(pp);
		var namep = $("<p class='ppp'><i class='fa fa-user'>：<span>"+agent.user+"</span></i></p>");
		pp.append(namep);
		var phonep = $("<p class='ppp'><i class='fa fa-phone'>：<span>"+agent.user+"</span></i></p>");
		pp.append(phonep);
		var timep = $("<p class='ppp'><i  class='fa fa-clock-o'>：<span id='time"+agent.user+"'>00:00:00</span></i></p>");
		pp.append(timep);
		var fonts;
		if(agent.status == "NONVIABLE"){//空闲
			fonts = $("<p class='ppp'><i style='color:#71dc80;' class='fa fa-check-circle-o'>：<span id='status"+agent.user+"'>示闲</span></i></p>");
		}else if(agent.status == "INITIATING"){//初始化INITIALIZED
			fonts = $("<p class='ppp'><i class='fa fa-check-circle-o'>：<span id='status"+agent.user+"'>初始化</span></i></p>");
		}else if(agent.status == "ALERTING"){//振铃
			fonts = $("<p class='ppp'><i style='color:#f25820;'  class='fa fa-volume-control-phone'>：<span id='status"+agent.user+"'>振铃</span></i></p>");
		}else if(agent.status == "DEFERRED"){//推迟
			fonts = $("<p class='ppp'><i style='color:#cee155;'  class='fa fa-stop-circle-o'>：<span id='status"+agent.user+"'>推迟</span></i></p>");
		}else if(agent.status == "WORKING"){//通话
			fonts = $("<p class='ppp'><i style='color:#53b8ed;'  class='fa fa-phone'>：<span id='status"+agent.user+"'>通话</span></i></p>");
		}else if(agent.status == "PAUSED"){//保持
			fonts = $("<p class='ppp'><i style='color:#53ed85;'  class='fa fa-stop-circle'>：<span id='status"+agent.user+"'>保持</span></i></p>");
		}else if(agent.status == "WRAPUP"){//：事后处理
			fonts = $("<p class='ppp'><i style='color:#green;'  class='fa fa-stop'>：<span id='status"+agent.user+"'>事后处理</span></i></p>");
		}else if(agent.status == "CONFERENCING"){//开会
			fonts = $("<p class='ppp'><i style='color:#green;'  class='fa fa-stop'>：<span id='status"+agent.user+"'>开会</span></i></p>");
		}else if(agent.status == "CONSULTING"){//咨询
			fonts = $("<p class='ppp'><i style='color:#green;'  class='fa fa-hand-o-up'>：<span id='status"+agent.user+"'>咨询</span></i></p>");
		}else if(agent.status == "TRANSFERRING"){//转接
			fonts = $("<p class='ppp'><i style='color:#green;'  class='fa fa-arrow-circle-right'>：<span id='status"+agent.user+"'>转接</span></i></p>");
		}else if(agent.status == "LOGGED_IN"){//签入
			fonts = $("<p class='ppp'><i   class='fa fa-unlock-alt'>：<span id='status"+agent.user+"'>签入</span></i></p>");
		}else if(agent.status == "UNINITIALIZED"){//没有初始化
			fonts = $("<p class='ppp'><i   class='fa fa-arrow-circle-right'>：<span id='status"+agent.user+"'>加载</span></i></p>");
		}else if(agent.status == "INITIALIZED"){//没有初始化
			fonts = $("<p class='ppp'><i   class='fa fa-arrow-circle-right'>：<span id='status"+agent.user+"'>OK</span></i></p>");
		}else if(agent.status == "INIT_AUXWORK"){//示忙
			fonts = $("<p class='ppp'><i style='color:#f6c1af;'   class='fa fa-minus-circle'>：<span id='status"+agent.user+"'>示忙</span></i></p>");
		}else if(agent.status == "AUXWORK"){//初始化示忙
			fonts = $("<p class='ppp'><i style='color:#f6c1af;'   class='fa fa-minus-circle'>：<span id='status"+agent.user+"'>示忙</span></i></p>");
		}else if(agent.status == "INIT_AVAILABLE"){//初始化示闲
			fonts = $("<p class='ppp'><i style='color:#f6c1af;'   class='fa fa-fa-check-circle-o'>：<span id='status"+agent.user+"'>示闲</span></i></p>");
		}else if(agent.status == "INIT_AVAILABLE"){//初始化示闲
			fonts = $("<p class='ppp'><i style='color:#f6c1af;'   class='fa fa-fa-check-circle-o'>：<span id='status"+agent.user+"'>示闲</span></i></p>");
		}
		pp.append(fonts);
		$(".container").append(box);		
			
	}
	/**
	 *判座席是否显示在浏览器上,如果不在就创建一个,如果在就改变对应的属性
	 *@param 
	 */
	function isOnline( agent,color ){
		if( document.getElementById( agent.user ) == null ){
			createAgent(agent);
			id = "time"+ agent.user;
			setTime(id);
		}else{
			$("#status"+agent.user).text(agent.status);
			id = "time"+ agent.user;
			clearInterval(timers[agent.user]);
			setTime(id);
		}
		$(document.getElementById( agent.user )).css("background-color", color);
	}
	//根据在线状态提示相应的操作
	function connectionStateChanged(state){
	  var stateConversion = {0: 'connecting', 1: 'connected', 2: 'reconnecting', 4: 'disconnected'};
	    console.log('SignalR state changed from: ' + stateConversion[state.oldState]
	     + ' to: ' + stateConversion[state.newState]);
	   if(state.newState==4){
		  toastr.warning('服务器已中断,系统将会在20秒内自行连接，请稍候...'); 
		  setTimeout(function(){callserver.start().done(function() {});},5000);
	   }
	   if(state.newState==2){
	   	 toastr.info('网络出现故障，系统正在重连，请稍候...'); 
	   }
	   if(state.newState==1){
	   	  toastr.success('服务器连接成功'); 
	   }
	}
	
			//回送话路状态
	function callStatusBackEx( agent ){
		
		var agent = JSON.parse( agent );
		console.log(agent);
		switch ( agent.status ) {
		case 'NONVIABLE':
			//agent.status = '空闲';
			isOnline( agent ,"#53ed85");
			break;
		case 'INITIATING':
			//agent.status = '初始化';
			isOnline( agent,"#d0d4d7" );
			break;
		case 'ALERTING':
			//agent.status = '振铃';
			isOnline( agent ,"#feb7b9");
			break;
		case 'DEFERRED':
			//agent.status = '推迟';
			isOnline( agent,"#feb7b9" );
			break;
		case 'WORKING':
			//agent.status = '通话中';
			isOnline( agent,"#cbecff" );
			break;
		case 'PAUSED':
			//agent.status = '保持中';
			isOnline( agent,"#cbffd5" );
			break;
		case 'WRAPUP':
			//agent.status = '事后处理';
			isOnline( agent,"green" );
			break;
		case 'CONFERENCING ':
			//agent.status = '正在开会';
			isOnline( agent ,"#cbecff");
			break;
		case 'CONSULTING ':
			//agent.status = '正在咨询';
			isOnline( agent,"#cbecff" );
			break;
		case 'TRANSFERRING ':
			//agent.status = '正在转接';
			isOnline( agent,"red" );
			break;
		default:
			break;
		}
	}
	
	//回送坐席状态
	function agentStatusBackEx( agent ){
		console.log(agent);
		var agent = JSON.parse( agent );
		if( agent.status == 'UNINITIALIZED'){//没有初始化
			//agent.status = '没有初始化';
			isOnline( agent,"white");	
		}else if( agent.status == 'INITIALIZED' ){//初始化完成
			//agent.status = '初始化完成';
			isOnline( agent,'#53ed85' );
		}else if( agent.status == 'LOGGED_IN' ){//签入
			//agent.status = '签入';
			isOnline( agent ,"#53ed85");
		}else if( agent.status == 'INIT_AUXWORK' ){//初始化示忙
			//agent.status = '初始化示忙';
			isOnline( agent,"#f6c1af" );
		}else if( agent.status == 'AUXWORK' ){//示忙
			//agent.status = '示忙';
			isOnline( agent,"#f6c1af" );
		}else if( agent.status == 'INIT_AVAILABLE' ){//初始化示闲
			//agent.status = '初始化示闲';
			isOnline( agent,"#53ed85" );
		}else if( agent.status == 'AVAILABLE' ){//示闲
			//agent.status = '示闲';
			isOnline( agent,"#53ed85" );
		}else if( agent.status == 'LOGGED_OUT' ){//签出
			if( document.getElementById( agent.user ) != null ){
				$("#"+agent.user ).parent().remove();	
				clearInterval(timers[agent.user]);
			}
		}
	}
	
	//给客户端送错误代码
	function getErrCode(errCode){
		if( errCode == 'User_Is_Loggedin' ){
			toastr.warning('用户已经登录'); 
		}else if( errCode == 'Login_Failed' ){
			toastr.warning('登录失败'); 
		}else if( errCode == 'Aic_Briddge_Down' ){
			toastr.warning('avaya ic javaBridge宕机了'); 
		}
	} 
	
	//计算通话时间
	function setTime(userid){
	    callTime = 0;
	    var sp = userid.split("time")[1];
	    $('#'+userid).html('00:00:00');
	    timers[sp] = setInterval(function(){
	        callTime+=1;
	        callTimeseconds = callTime%60;
	        callTimeminutes = Math.floor(callTime/60);
	        callHours = Math.floor(callTime/3600);
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
	         if(callHours<10){
	            callHours ='0' + callHours;
	        }else{
	            callHours = callHours+'';
	        }
	         $('#'+userid).html(callHours+':'+callTimeminutes+':'+callTimeseconds);
	    },1000);
	}
	

})


