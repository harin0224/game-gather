package com.multi.gamegather.club.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String message;
    private int senderId;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "message='" + message + '\'' +
                ", senderId='" + senderId + '\'' +
                '}';
    }
}
