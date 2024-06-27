package com.multi.gamegather.club.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubCategoryRelationDTO {
    private String id;
    private String clubId;
    private String userId;
    private String role;
}
