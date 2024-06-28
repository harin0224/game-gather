package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClubMapper {
    // 입력된 클럽 정보로 클럽 생성
    int createClub(CreateClubRequestDTO data);
    void deleteClub(int clubCode);
}


