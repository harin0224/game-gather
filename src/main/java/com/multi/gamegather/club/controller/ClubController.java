package com.multi.gamegather.club.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.club.model.dto.ClubDTO;
import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import com.multi.gamegather.club.model.dto.HelloMessageDTO;
import com.multi.gamegather.club.model.dto.MessageDTO;
import com.multi.gamegather.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@RestController //@Controller
@RequestMapping("/club")
public class ClubController {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

    private final ClubService clubService;

    @PostMapping
    public int createRoom(
        @RequestBody CreateClubRequestDTO data,
        @AuthenticationPrincipal CustomUser currentUser
    ) {
        System.out.println("1111 : " + currentUser.toString());
        data.setUserId(currentUser.getNo());
        System.out.println("1");

        return clubService.createClub(data);
    }

    @MessageMapping("/club/hello")
    @SendTo("/sub/club/test")
    public String test(HelloMessageDTO message) throws Exception {
        System.out.println("123123123");
        return ("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    // 메세지 보내기
    @MessageMapping("/club/send-message/{clubCode}")
    @SendTo("/sub/club/receive-message/{clubCode}")
    public MessageDTO messageManager(
            @DestinationVariable String clubCode,
            MessageDTO message
    ) throws Exception {
        System.out.println("Get From " + clubCode + " send to " + message.getMessage());
        return message;
    }
    
    // 방 삭제하기
    @DeleteMapping
    public void DeleteRoom(
        @RequestBody ClubDTO clubDTO,
        @AuthenticationPrincipal CustomUser currentUser){

        clubService.deleteClub(clubDTO.getId(), currentUser.getNo());
    }

}
