package com.multi.gamegather.rchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RChatMessage {

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;



    public RChatMessage(String sender) {
        this.sender = sender;
    }

}
