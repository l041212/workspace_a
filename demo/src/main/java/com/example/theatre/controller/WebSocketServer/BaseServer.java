package com.example.theatre.controller.WebSocketServer;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.utils.WebSocketKit;

public abstract class BaseServer {

	protected String sessionId = null;

	protected boolean onOpen(Session session, String sessionId) {
		if (StringUtils.isNotBlank(sessionId)) {
			this.sessionId = sessionId;
			WebSocketKit.getSessionCache().put(sessionId, session);
			WebSocketKit.plusCount();
			return true;
		}
		return false;
	}

	protected boolean onClose() {
		if (StringUtils.isNotBlank(this.sessionId)) {
			WebSocketKit.getSessionCache().remove(this.sessionId);
			WebSocketKit.minusCount();
			return true;
		}
		return false;
	}

	protected abstract void onError(Session session, Throwable error);

	protected abstract void onMessage(Session session, String message);

}
