package com.multi.gamegather.club.dto;

import com.multi.gamegather.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ClubRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ClubRoom(String  roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    // 로직인가?
    public void handleActions(WebSocketSession session, ClubMessage clubMessage, ChatService chatService){
        if(clubMessage.getType().equals(ClubMessage.MessageType.ENTER)){
            sessions.add(session);
            clubMessage.setMessage((clubMessage.getSender() + "님이 입장했습니다."));
        }
        sendMessage(clubMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
