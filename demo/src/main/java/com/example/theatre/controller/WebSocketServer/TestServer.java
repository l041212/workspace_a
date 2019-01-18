package com.example.theatre.controller.WebSocketServer;

import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.example.demo.utils.WebSocketKit;

@ServerEndpoint("/theatre/testServer/{sessionId}")
@Component
public class TestServer extends BaseServer {

	private Logger logger = LoggerFactory.getLogger(TestServer.class);
	
	@OnOpen
	@Override	
	public boolean onOpen(Session session, @PathParam("sessionId") String sessionId) {
		if(super.onOpen(session, sessionId)) {
			logger.info("session id : "+ sessionId + " was been opened, current session count : " + WebSocketKit.getCount());
			return true;
		}
		return false;
	}
	
	@OnClose
	@Override
	public boolean onClose() {
		if(super.onClose()) {
			logger.info("session id : "+ this.sessionId + " was been closed, current session count : " + WebSocketKit.getCount());
		}
		return false;
	}
	
	@OnError
	@Override
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	@OnMessage
	@Override
	public void onMessage(Session session, String message) {
		Map<String, Object> map = JSON.parseObject(message);
		logger.info("session id : " + this.sessionId + ", message : " + map);
		if (map.get("data") != null) {
			WebSocketKit.sendMessage(this.sessionId, map.get("receiver").toString(), JSON.parseObject(map.get("data").toString()));
		}
	}

}
