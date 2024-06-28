package com.multi.gamegather.member.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.member.model.dto.MemberDTO;
import com.multi.gamegather.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/login")
    public void login() {

    }

    @GetMapping("/signup")
    public void signup() {

    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/signup")
    public String handleFileUpload(@RequestParam("profile_img_path") MultipartFile file,
                                   @RequestParam("id") String id,
                                   @RequestParam("password") String password,
                                   @RequestParam("name") String name,
                                   @RequestParam("nickname") String nickname,
                                   @RequestParam("age") int age,
                                   @RequestParam("gender") String gender,
                                   @RequestParam("tel") String tel) {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPwd(password);
        memberDTO.setName(name);
        memberDTO.setNickname(nickname);
        memberDTO.setAge(age);
        memberDTO.setGender(gender);
        memberDTO.setTel(tel);
        memberDTO.setProfileIMG(file.getOriginalFilename());
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return "파일을 선택해 주세요";
        }

        try {
            // 파일 저장 경로 설정
            Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);

            // 회원 정보와 파일 이름을 데이터베이스에 저장
            memberService.saveMember(memberDTO);

        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드 실패";
        }

        return "회원 가입 완료";
    }

    @RequestMapping("/mypage")
    public void mypage() {

    }

    @GetMapping("/memberinfo")
    public void memberInfo() {

    }

    @PostMapping("regist")
    public String updateMemberInfo(MemberDTO memberDTO, Model model) {
        boolean isUpdated = memberService.updateMember(memberDTO);
        if (isUpdated) {
            model.addAttribute("message", "User information updated successfully.");
        } else {
            model.addAttribute("message", "Failed to update user information.");
        }
        return "personal_info_edit";
    }

    @GetMapping("/findUserIdPwdForm")
    public String findUserIdPwdForm() {
        return "member/findUserIdPwd";
    }

    @PostMapping("/findUserIdPwd")
    public ResponseEntity<?> findUserIdPwd(@RequestBody MemberDTO memberDTO, @AuthenticationPrincipal CustomUser currentUser) {
        System.out.println("Logged in user ID: " + currentUser.getId());
        MemberDTO member = memberService.findUser(memberDTO);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
