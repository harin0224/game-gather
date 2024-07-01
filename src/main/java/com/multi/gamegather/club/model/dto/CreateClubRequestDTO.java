package com.multi.gamegather.club.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateClubRequestDTO {
    private int id;
    private String clubCode;
    private String name;
    private int userId;
    private List<ClubCategoryDTO> categories;

    @Builder
    public CreateClubRequestDTO(String  clubCode, String name, int userId, List<ClubCategoryDTO> categories){
        this.clubCode = clubCode;
        this.name = name;
        this.userId = userId;
        this.categories = categories;
    }
    
}
