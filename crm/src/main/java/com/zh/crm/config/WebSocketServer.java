package com.zh.crm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //记录当前连接数
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接受的sid
    private String sid="";

    //开启websocket
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();//在线数加1
        logger.info("有新的窗口开始监听"+sid+"，当前在线人数为："+getOnlineCount());
       /* try {
            //sendMessage("连接成功");
        } catch (IOException e) {
            logger.error("websocket IO异常");
        }*/
    }

    //关闭连接
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        subOnlineCount(); //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    //收到客户端消息
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口"+sid+"的信息:"+message); //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) { e.printStackTrace();
            }
        }
    }

    //发生错误
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    //服务器主动推送消息
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //群发消息
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try { //这里可以设定只推送给这个sid的，为null则全部推送
               if(sid==null) {
                   item.sendMessage(message);
               }else if(item.sid.equals(sid)){
                   item.sendMessage(message);
               }
            } catch (IOException e) {
                continue;
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
