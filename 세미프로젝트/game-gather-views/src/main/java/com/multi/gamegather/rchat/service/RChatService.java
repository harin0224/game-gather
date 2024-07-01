package com.multi.gamegather.rchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.gamegather.rchat.dto.RChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RChatService {

    private final ObjectMapper objectMapper;


    private Map<String, RChatRoom> rchatRooms;

    @PostConstruct
    private void init() {
        rchatRooms = new LinkedHashMap<>();
    }

    // 채팅방 조회, 존재하는 모든 채팅방 목록 반환
    public List<RChatRoom> findAllRoom(){
        return new ArrayList<>(rchatRooms.values());
    }

    // 채팅방 조회, 주어진 roomId에 대한 채팅방 조회
    public RChatRoom findRoomById(String roomId){
        return rchatRooms.get(roomId);
    }


    // 채팅방 생성
    public RChatRoom createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        RChatRoom chatRoom = RChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        rchatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    // 메세지 전송
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }
}
