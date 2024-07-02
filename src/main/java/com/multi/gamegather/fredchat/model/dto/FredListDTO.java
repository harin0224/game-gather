package com.multi.gamegather.fredchat.model.dto;

public class FredListDTO {
    private int no;
    private String frndCode;
    private String userId;
    private String frndId;
    private String frndFlag;

    public FredListDTO() {
    }

    public FredListDTO(int no, String frndCode, String userId, String frndId, String frndFlag) {
        this.no = no;
        this.frndCode = frndCode;
        this.userId = userId;
        this.frndId = frndId;
        this.frndFlag = frndFlag;
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

    public String getFrndId() {
        return frndId;
    }

    public void setFrndId(String frndId) {
        this.frndId = frndId;
    }

    public String getFrndFlag() {
        return frndFlag;
    }

    public void setFrndFlag(String frndFlag) {
        this.frndFlag = frndFlag;
    }

    @Override
    public String toString() {
        return "FredListDTO{" +
                "no=" + no +
                ", frndCode='" + frndCode + '\'' +
                ", userId='" + userId + '\'' +
                ", frndId='" + frndId + '\'' +
                ", frndFlag='" + frndFlag + '\'' +
                '}';
    }
}