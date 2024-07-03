package com.multi.gamegather.friend.model.dto;

public class FriendDTO {
    private String frCode;
    private String userId;
    private String frId;
    private String frFlag;

    public FriendDTO(){} // DTO 호출할 때

    public FriendDTO(String frCode, String userId, String frId, String frFlag) {
        this.frCode = frCode;
        this.userId = userId;
        this.frId = frId;
        this.frFlag = frFlag;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrId() {
        return frId;
    }

    public void setFrId(String frId) {
        this.frId = frId;
    }

    public String getFrFlag() {
        return frFlag;
    }

    public void setFrFlag(String frFlag) {
        this.frFlag = frFlag;
    }

    @Override
    public String toString() {
        return "FriendDTO{" +
                "frCode='" + frCode + '\'' +
                ", userId='" + userId + '\'' +
                ", frId='" + frId + '\'' +
                ", frFlag='" + frFlag + '\'' +
                '}';
    }
}
