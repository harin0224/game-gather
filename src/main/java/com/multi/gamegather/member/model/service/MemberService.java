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


    public void saveMember(MemberDTO memberDTO) {
        memberDAO.insertMember(memberDTO);
    }

    public boolean updateMember(MemberDTO memberDTO) {
        try {
            MemberDAO.updateMember(memberDTO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
