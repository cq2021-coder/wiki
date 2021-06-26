package com.cq.wiki.service;

import com.cq.wiki.websocket.WebSocketServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WsService {

    @Resource
    public WebSocketServer webSocketServer;


    @Async
    public void sentInfo(String message){
        webSocketServer.sendInfo(message);
    }
}
