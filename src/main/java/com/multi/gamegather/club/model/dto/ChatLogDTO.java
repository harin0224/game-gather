package com.multi.gamegather.club.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatLogDTO {
    private int id;
    private int clubId;
    private int userId;
    private String chatLog;
    private String createdAt;
    private String userIdFromUsersTable;
}
