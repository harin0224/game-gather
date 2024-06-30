package com.multi.gamegather.club.model.dto;

import com.multi.gamegather.member.model.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClubDTO {
    private int id;
    private String code;
    private String name;
    private String status;
    private int userId;
    public List<MemberDTO> members;
}



