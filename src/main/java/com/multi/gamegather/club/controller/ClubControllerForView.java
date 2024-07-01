package com.multi.gamegather.club.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import com.multi.gamegather.club.model.dto.HelloMessageDTO;
import com.multi.gamegather.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@Controller
@RequestMapping("/club")
public class ClubControllerForView {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

    private final ClubService clubService;

    @GetMapping("/club")            // 오류 있음
    public String test(){
        return "club/club";
    }

    @PostMapping("/club")
    public String club2(){
        return "redirect:/club";
    }

}
