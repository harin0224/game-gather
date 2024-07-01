package com.multi.gamegather.club.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubManagementDTO {
    private int id;
    private int clubId;
    private int userId;
    private String role;

    public ClubManagementDTO() {
    }

    public ClubManagementDTO(int id, int clubId, int userId, String role) {
        this.id = id;
        this.clubId = clubId;
        this.userId = userId;
        this.role = role;
    }

    @Override
    public String toString() {
        return "ClubManagementDTO{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}
