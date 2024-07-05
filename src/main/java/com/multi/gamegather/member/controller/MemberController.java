package com.multi.gamegather.member.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.member.model.dto.MemberDTO;
import com.multi.gamegather.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
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

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/member/login";
    }

    @GetMapping("/signup")
    public void signup() {

    }

    @PostMapping("/signup")
    public ModelAndView handleFileUpload(@RequestParam("profile_img_path") MultipartFile file,
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
            return new ModelAndView("redirect:/member/signup?error=파일을 선택해 주세요");
        }

        try {
            // 절대 경로 설정
            Path uploadPath = Paths.get(uploadDir);
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
            return new ModelAndView("redirect:/member/signup?error=파일 업로드 실패");
        }
        return new ModelAndView("redirect:/member/login");
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
    public ModelAndView updateMember(@RequestParam("profile_img_path") MultipartFile file,
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
                String uniqueFileName = UUID.randomUUID().toString().substring(0, 8) + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), filePath);

                // 파일명을 MemberDTO에 설정
                memberDTO.setProfileIMG(uniqueFileName);

            } catch (IOException e) {
                e.printStackTrace();
                return new ModelAndView("redirect:/member/mypage?error=파일 업로드 실패");
            }
        }
        memberService.updateMember(memberDTO);

        return new ModelAndView("redirect:/member/mypage");
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

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public ResponseEntity<MemberDTO> getCurrentUser(@AuthenticationPrincipal CustomUser customUser) {
        MemberDTO member = memberService.findMemberById(customUser.getId());
        return ResponseEntity.ok(member);
    }

    @GetMapping("/profile-img/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다: " + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽을 수 없습니다: " + filename, e);
        }
    }

    @PostMapping("/incrementMannerCount")
    public ModelAndView incrementMannerCount(@RequestParam("userId") String userId) {
        memberService.incrementMannerCount(userId);
        return new ModelAndView("redirect:/success/manner");
    }

    @PostMapping("/incrementBanCount")
    public ModelAndView incrementBanCount(@RequestParam("userId") String userId) {
        memberService.incrementBanCount(userId);
        return new ModelAndView("redirect:/success/ban");
    }

    @RequestMapping("/findUserAndChangePwd")
    public void findUserAndChangePwd() {

    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String name = payload.get("name");
        String tel = payload.get("tel");
        String gender = payload.get("gender");
        String newPassword = payload.get("newPassword");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(userId);
        memberDTO.setName(name);
        memberDTO.setTel(tel);
        memberDTO.setGender(gender);

        MemberDTO member = memberService.findUserByDetails(memberDTO);
        if (member != null) {
            memberService.changePassword(userId, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
