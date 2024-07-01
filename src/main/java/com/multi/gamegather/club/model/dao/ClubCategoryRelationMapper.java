package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.ClubCategoryDTO;
import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClubCategoryRelationMapper {


    int clubCategoryRelationMapper(int clubId, List<ClubCategoryDTO> data);
    void deleteClubCategoryRelationByClubId(int clubId);



    ClubManagementDTO findByUsername(String username);
    void deleteUser(Long userId);
}


