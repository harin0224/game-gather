package com.multi.gamegather.member.model.dao;

import com.multi.gamegather.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String memberId);

    MemberDTO findUserByDetails(String userName, String userTel, String userGender);

    void insertMember(MemberDTO member);

    static void updateMember(MemberDTO memberDTO) {

    }
}
