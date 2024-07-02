package com.multi.gamegather.fredchat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FredChatMessage {

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
