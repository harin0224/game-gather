package com.multi.gamegather.fredchat.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class FChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public FChatRoom(String  roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }



}
