package com.multi.gamegather.rchat.dto;

import com.multi.gamegather.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public RChatRoom(String  roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, RChatMessage rchatMessage, ChatService chatService){
        if(rchatMessage.getType().equals(RChatMessage.MessageType.ENTER)){
            sessions.add(session);
            rchatMessage.setMessage((rchatMessage.getSender() + "님이 입장했습니다."));
        }
        sendMessage(rchatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
