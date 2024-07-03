package com.multi.gamegather.member.model.service;

import com.multi.gamegather.member.model.dao.MemberDAO;
import com.multi.gamegather.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberDTO findUser(MemberDTO memberDTO) {
        return memberDAO.findUserByDetails(memberDTO.getName(), memberDTO.getTel(), memberDTO.getGender());
    }

    public MemberDTO findMemberById(String id) {
        return memberDAO.findMemberById(id);
    }

    public void insertMember(MemberDTO memberDTO) {
        // 비밀번호 암호화
        memberDTO.setPwd(passwordEncoder.encode(memberDTO.getPwd()));
        memberDAO.insertMember(memberDTO);
    }

    public void updateMember(MemberDTO memberDTO) {
        if (memberDTO.getPwd() != null && !memberDTO.getPwd().isEmpty()) {
            memberDTO.setPwd(passwordEncoder.encode(memberDTO.getPwd()));
        } else {
            MemberDTO existingMember = memberDAO.findMemberById(memberDTO.getId());
            memberDTO.setPwd(existingMember.getPwd());
        }
        memberDAO.updateMember(memberDTO);
    }

    public void deleteMember(String id) {
        memberDAO.deleteMember(id);
    }

    public void incrementMannerCount(String userId) {
        memberDAO.incrementMannerCount(userId);
    }

    public void incrementBanCount(String userId) {
        memberDAO.incrementBanCount(userId);
    }
}
