package com.multi.gamegather.rchat.chatlog.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RChatLogDTO {
    private String rchatcontent;
    private LocalDateTime rchattime;
    // + getter, setter


}
