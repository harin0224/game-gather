package com.multi.gamegather.club.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.gamegather.club.model.dao.ClubCategoryRelationMapper;
import com.multi.gamegather.club.model.dao.ClubManagementMapper;
import com.multi.gamegather.club.model.dao.ClubMapper;
import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClubService {

    private final ObjectMapper objectMapper;
    private final ClubManagementMapper clubManagementMapper;
    private final ClubMapper clubMapper;
    private final ClubCategoryRelationMapper clubCategoryRelationMapper;

    // 룸 목록, 필요없음
    // private Map<String, ClubRoom> clubRooms;
//    private Map<String, ClubRoomDTO> clubRooms;

//    @PostConstruct
//    private void init() {
//        clubRooms = new LinkedHashMap<>();
//    }

    // 채팅방 조회, 존재하는 모든 채팅방 목록 반환
//    public List<ClubRoomDTO> findAllRoom(){
//
//        return new ArrayList<>(clubRooms.values());
//    }

//    // 채팅방 조회, 주어진 roomId에 대한 채팅방 조회
//    public ClubRoomDTO findRoomById(String roomId){
//        return clubRooms.get(roomId);
//    }


    // 채팅방 생성
    @Transactional      // 하나라도 틀리면 되돌아감(트랜잭션 걸기)
    public void createClub(CreateClubRequestDTO data){
        String randomId = UUID.randomUUID().toString();
        data.setClubCode(randomId);
        System.out.println("2");

        //1. 방 정보를 저장한다
        //2. 방과 유저의 연결 (Club user )
        //3. 카테고리와 방 정보 연결

        //1. 방 정보 저장
        clubMapper.createClub(data);
        System.out.println("3");

        //2. 방과 유저 연결
        ClubManagementDTO clubManagement = new ClubManagementDTO();
        clubManagement.setClubId(data.getId());
        clubManagement.setUserId(data.getUserId());
        clubManagement.setRole("MANAGER");
        System.out.println("3213 : " + clubManagement.toString());
        clubManagementMapper.createClubManagement(clubManagement);
        System.out.println("3");


        //3. 카테고리와 방 정보 연결
        clubCategoryRelationMapper.clubCategoryRelationMapper(data.getId(), data.getCategories());
        System.out.println("4");
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
