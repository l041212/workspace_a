package com.example.demo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class WebSocketKit {

	private static int count = 0;
	private static final Map<String, Session> SESSION_CACHE = new ConcurrentHashMap<String, Session>();

	/**
	 * format: {sender: "senderId", receiver: "receiverId", data: "[{},{}...]"}
	 * 
	 * @param sender   sender session id
	 * @param receiver receiver session id
	 * @param object   data object
	 */
	public static void sendMessage(String sender, String receiver, Object object) {
		if (StringUtils.isNotBlank(receiver) && !"null".equals(receiver.trim())) {
			sendMessageForOne(sender, receiver, object);
		} else {
			sendMessageForAll(sender, object);
		}
	}

	public static void sendMessageForOne(String sender, String receiver, Object object) {
		if (object != null && StringUtils.isNotBlank(receiver)) {
			try {
				if (SESSION_CACHE.containsKey(receiver) && sender != receiver) {
					Map<String, Object> message = new HashMap<String, Object>();
					message.put("sender", StringUtils.isNotBlank(sender) ? sender : "server");
					message.put("receiver", receiver);
					message.put("data", JSON.toJSONString(object));
					SESSION_CACHE.get(receiver).getBasicRemote().sendText(JSON.toJSONString(message));
					System.out.println("receiver id : " + receiver + " message : " + JSON.toJSONString(message));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendMessageForAll(String sender, Object object) {
		if (object != null) {
			SESSION_CACHE.entrySet().stream().forEach((entry) -> {
				sendMessageForOne(sender, entry.getKey(), object);
			});
		}
	}

	public static Map<String, Session> getSessionCache() {
		return SESSION_CACHE;
	}

	public static synchronized int getCount() {
		return WebSocketKit.count;
	}

	public static synchronized int plusCount() {
		return ++WebSocketKit.count;
	}

	public static synchronized int minusCount() {
		return WebSocketKit.count > 0 ? --WebSocketKit.count : WebSocketKit.count;
	}

}
