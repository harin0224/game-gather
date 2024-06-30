package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.ClubDTO;
import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubManagementMapper {

    int createClubManagement(ClubManagementDTO data);
    void deleteClubManagementByClubId(int clubCode);
    String getUserStatus(int clubId,int userId);
    List<ClubDTO> findUserClubs(int userId);
    void addUser(int userId,int clubId);
    void deleteUser(int userId, int clubId);
    void kickUser(int userId, int clubId);

    List<Integer> findUserByClub(int clubId);

}

