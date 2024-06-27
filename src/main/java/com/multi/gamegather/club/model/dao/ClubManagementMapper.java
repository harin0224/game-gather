package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClubManagementMapper {

    int createClubManagement(ClubManagementDTO data);
//
//    ClubManagement findByUsername(String username);
//
//    void deleteUser(Long userId);
}


