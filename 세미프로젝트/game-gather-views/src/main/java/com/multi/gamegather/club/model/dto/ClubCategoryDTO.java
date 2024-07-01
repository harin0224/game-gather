package com.multi.gamegather.club.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubCategoryDTO {
    private int id;
    private int clubId;
    private int userId;
    private String role;
}
