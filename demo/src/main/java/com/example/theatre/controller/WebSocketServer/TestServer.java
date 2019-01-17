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

@ServerEndpoint("/theatre/testServer/{sid}")
@Component
public class TestServer extends BaseServer {

	private Logger logger = LoggerFactory.getLogger(TestServer.class);
	
	@OnOpen
	@Override	
	public boolean onOpen(Session session, @PathParam("sid") String sid) {
		if(super.onOpen(session, sid)) {
			logger.info("session id : "+ sid + " was been opened, current session count : " + count);
			return true;
		}
		return false;
	}
	
	@OnClose
	@Override
	public boolean onClose() {
		if(super.onClose()) {
			logger.info("session id : "+ this.sid + " was been closed, current session count : " + count);
		}
		return false;
	}
	
	@OnError
	@Override
	public void onError(Session session, Throwable error) {
		super.onError(session, error);
	}

	@OnMessage
	@Override
	public void onMessage(Session session, String message) {
		Map<String, Object> map = JSON.parseObject(message);
		logger.info("session id : " + this.sid + ", message : " + map);
		if (map.get("data") != null) {
				sendMessage(this.sid, map.get("receiver").toString(), JSON.parseObject(map.get("data").toString()));
		}
	}

}
