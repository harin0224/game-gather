package com.multi.gamegather.member.model.dao;

import com.multi.gamegather.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDAO {

    MemberDTO findMemberById(String memberId);

    List<MemberDTO> findMemberByNos(List<Integer> memberNos);

    MemberDTO findUserByDetails(String userName, String userTel, String userGender);

    void insertMember(MemberDTO member);

    void updateMember(MemberDTO memberDTO);

    void deleteMember(String id);

    int getUserNoByUsername(String username);
}
