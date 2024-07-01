package com.multi.gamegather.member.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.member.model.dto.MemberDTO;
import com.multi.gamegather.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @RequestMapping("/login")
    public void login() {

    }

    @GetMapping("/signup")
    public void signup() {

    }

    @PostMapping("/signup")
    public ResponseEntity<String> handleFileUpload(@RequestParam("profile_img_path") MultipartFile file,
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

        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일을 선택해 주세요");
        }

        try {
            // 절대 경로 설정
            Path currentPath = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
            Path uploadPath = currentPath.resolve("src/main/resources/static").resolve(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 고유한 파일명 생성
            String uniqueFileName = UUID.randomUUID().toString().substring(0, 8) + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(uniqueFileName);

            // 디버깅을 위한 경로 출력
            System.out.println("파일 저장 경로: " + filePath.toString());

            Files.copy(file.getInputStream(), filePath);

            // 파일명을 MemberDTO에 설정
            memberDTO.setProfileIMG(uniqueFileName);

            // 회원 정보와 파일 이름을 데이터베이스에 저장
            memberService.insertMember(memberDTO);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }
        return ResponseEntity.ok("회원 가입 완료");
    }

    @GetMapping("/mypage")
    public void mypage() {

    }

    @GetMapping("/memberinfo")
    public void memberInfo() {

    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<MemberDTO> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        MemberDTO member = memberService.findMemberById(customUser.getId());
        return ResponseEntity.ok(member);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateMember(@RequestParam("profile_img_path") MultipartFile file,
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

        // 파일이 비어있는지 확인
        if (!file.isEmpty()) {
            try {
                // 파일 저장 경로 설정
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), filePath);

                // 파일명을 MemberDTO에 설정
                memberDTO.setProfileIMG(uniqueFileName);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
            }
        }

        memberService.updateMember(memberDTO);

        return ResponseEntity.ok("회원 정보 수정 완료");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        memberService.deleteMember(customUser.getId());
        return ResponseEntity.ok("Account deleted successfully.");
    }

    @GetMapping("/findUserIdPwdForm")
    public String findUserIdPwdForm() {
        return "member/findUserIdPwd";
    }

    @PostMapping("/findUserIdPwd")
    public ResponseEntity<?> findUserIdPwd(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.findUser(memberDTO);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
