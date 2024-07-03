package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.ClubCategoryDTO;
import com.multi.gamegather.club.model.dto.ClubManagementDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubCategoryMapper {



    ClubManagementDTO findByUsername(String username);
    void deleteUser(Long userId);
    List<ClubCategoryDTO> getAllCategories();
}


