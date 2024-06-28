package com.multi.gamegather.match.model.dto;

public class MatchDTO {

    private String gamename;
    private String headcnt;
    private String tag1;
    private String tag2;
    private String tag3;
    private String age;


    public MatchDTO() {

    }

    public MatchDTO(String gamename) {
        this.gamename = gamename;
    }

    public MatchDTO(String gamename, String headcnt, String tag1, String tag2, String tag3, String age) {
        this.gamename = gamename;
        this.headcnt = headcnt;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.age = age;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getHeadcnt() {
        return headcnt;
    }

    public void setHeadcnt(String headcnt) {
        this.headcnt = headcnt;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "gamename='" + gamename + '\'' +
                ", headcnt='" + headcnt + '\'' +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", tag3='" + tag3 + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
