package com.multi.gamegather.club.model.dto;

import com.multi.gamegather.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ClubRoomDTO {
//    private String id;
//    private String code;
//    private String name;


    private String roomId;
    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ClubRoomDTO(String  roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    // 로직인가?
//    public void handleActions(WebSocketSession session, ClubMessageDTO clubMessageDTO, ChatService chatService){
//        if(clubMessageDTO.getType().equals(ClubMessageDTO.MessageType.ENTER)){
//            sessions.add(session);
//            clubMessageDTO.setMessage((clubMessageDTO.getSender() + "님이 입장했습니다."));
//        }
//        sendMessage(clubMessageDTO, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService){
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
//    }
}

