package com.multi.gamegather.club.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.gamegather.club.dto.ClubRoom;
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
public class ClubService {

    private final ObjectMapper objectMapper;

    // private Map<String, ClubRoom> clubRooms;
    private Map<String, ClubRoom> clubRooms;

    @PostConstruct
    private void init() {
        clubRooms = new LinkedHashMap<>();
    }

    // 채팅방 조회, 존재하는 모든 채팅방 목록 반환
    public List<ClubRoom> findAllRoom(){
        return new ArrayList<>(clubRooms.values());
    }

    // 채팅방 조회, 주어진 roomId에 대한 채팅방 조회
    public ClubRoom findRoomById(String roomId){
        return clubRooms.get(roomId);
    }

    // 채팅방 생성
    public ClubRoom createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ClubRoom chatRoom = ClubRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        clubRooms.put(randomId, chatRoom);
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
