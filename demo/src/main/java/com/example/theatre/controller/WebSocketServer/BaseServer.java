package com.example.theatre.controller.WebSocketServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public abstract class BaseServer {

	protected String sid = null;
	protected static int count = 0;
	protected static Map<String, Session> map = null;

	protected boolean onOpen(Session session, String sid) {
		if (map == null) {
			synchronized (BaseServer.class) {
				map = map == null ? new ConcurrentHashMap<String, Session>() : map;
			}
		}
		if (StringUtils.isNotBlank(sid)) {
			this.sid = sid;
			map.put(sid, session);
			plusCount();
			return true;
		}
		return false;
	}

	protected boolean onClose() {
		if (StringUtils.isNotBlank(this.sid)) {
			map.remove(this.sid);
			minusCount();
			return true;
		}
		return false;
	}

	protected void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	protected abstract void onMessage(Session session, String message);

	/**
	 * format: {sender: "senderId", receiver: "receiverId", data: "[{},{}...]"}
	 * 
	 * @param sender   sender session id
	 * @param receiver receiver session id
	 * @param object   data object
	 */
	public void sendMessage(String sender, String receiver, Object object) {
		if (StringUtils.isNotBlank(receiver) && !"null".equals(receiver.trim())) {
			sendMessageForOne(sender, receiver, object);
		} else {
			sendMessageForAll(sender, object);
		}
	}

	public void sendMessageForOne(String sender, String receiver, Object object) {
		if (object != null && StringUtils.isNotBlank(receiver)) {
			try {
				if (map.containsKey(receiver) && this.sid != receiver) {
					Map<String, Object> message = new HashMap<String, Object>();
					message.put("sender", StringUtils.isNotBlank(sender) ? sender : "server");
					message.put("receiver", receiver);
					message.put("data", JSON.toJSONString(object));
					map.get(receiver).getBasicRemote().sendText(JSON.toJSONString(message));
					System.out.println("receiver id : " + receiver + " message : " + JSON.toJSONString(message));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessageForAll(String sender, Object object) {
		if (object != null && StringUtils.isNotBlank(this.sid)) {
			map.entrySet().stream().forEach((entry) -> {
				sendMessageForOne(sender, entry.getKey(), object);
			});
		}
	}

	protected static synchronized int getCount() {
		return BaseServer.count;
	}

	protected static synchronized int plusCount() {
		return ++BaseServer.count;
	}

	protected static synchronized int minusCount() {
		return BaseServer.count > 0 ? --BaseServer.count : BaseServer.count;
	}

}
