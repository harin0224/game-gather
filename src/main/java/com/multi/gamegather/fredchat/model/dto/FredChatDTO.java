package com.multi.gamegather.fredchat.model.dto;

import java.sql.Timestamp;

public class FredChatDTO {
    private int no;
    private String frndCode;
    private String userId;
    private String chatLog;
    private Timestamp frndChatDate;

    public FredChatDTO() {

    }

    public FredChatDTO(int no, String frndCode, String userId, String chatLog, Timestamp frndChatDate) {
        this.no = no;
        this.frndCode = frndCode;
        this.userId = userId;
        this.chatLog = chatLog;
        this.frndChatDate = frndChatDate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getFrndCode() {
        return frndCode;
    }

    public void setFrndCode(String frndCode) {
        this.frndCode = frndCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatLog() {
        return chatLog;
    }

    public void setChatLog(String chatLog) {
        this.chatLog = chatLog;
    }

    public Timestamp getFrndChatDate() {
        return frndChatDate;
    }

    public void setFrndChatDate(Timestamp frndChatDate) {
        this.frndChatDate = frndChatDate;
    }

    @Override
    public String toString() {
        return "FredChatDTO{" +
                "no=" + no +
                ", frndCode='" + frndCode + '\'' +
                ", userId='" + userId + '\'' +
                ", chatLog='" + chatLog + '\'' +
                ", frndChatDate=" + frndChatDate +
                '}';
    }
}
