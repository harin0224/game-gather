package com.multi.gamegather.club.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.club.model.dao.ClubCategoryRelationMapper;
import com.multi.gamegather.club.model.dao.ClubManagementMapper;
import com.multi.gamegather.club.model.dao.ClubMapper;
import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public int createClub(CreateClubRequestDTO data){
        String randomId = UUID.randomUUID().toString();
        data.setClubCode(randomId);
        //System.out.println("2");

        // 1. 방 정보를 저장한다
        clubMapper.createClub(data);
        //System.out.println("3");

        // 2. 방과 유저의 연결 (Club user )
        ClubManagementDTO clubManagement = new ClubManagementDTO();
        clubManagement.setClubId(data.getId());
        clubManagement.setUserId(data.getUserId());
        clubManagement.setRole("MANAGER");
        //System.out.println("3213 : " + clubManagement.toString());
        clubManagementMapper.createClubManagement(clubManagement);
        //System.out.println("3");


        // 3. 카테고리와 방 정보 연결
        clubCategoryRelationMapper.clubCategoryRelationMapper(data.getId(), data.getCategories());
        //System.out.println("4");

        return data.getId();
    }
    
    // 채팅방 삭제
    @Transactional
    public void deleteClub(int clubId,int userId){
        System.out.println("clubId=" + clubId + " userId=" + userId);

        // 관리자 유저 체크
        String status = clubManagementMapper.getUserStatus(clubId,userId);
        System.out.println("status: " + status);
        if(!Objects.equals(status, "MANAGER")){
            throw new RuntimeException("Not a manager");
        }

        log.info("Club with ID {} has been deleted.", clubId);

        System.out.println("1");
        // 1. 클럽 관리 데이터 삭제
        clubManagementMapper.deleteClubManagementByClubId(clubId);

        System.out.println("2");
        // 2. 클럽 카테고리 관계 데이터 삭제
        clubCategoryRelationMapper.deleteClubCategoryRelationByClubId(clubId);

        System.out.println("3");
        // 3. 클럽 삭제
        clubMapper.deleteClub(clubId);

        System.out.println("0");

        log.info("Club with ID {} has been deleted.", clubId);

    }

    // 채팅방 가입

    // 채팅방 탈퇴
    
    
    


}
