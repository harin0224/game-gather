package com.multi.gamegather.club.controller;

import com.multi.gamegather.club.model.dto.CreateClubRequestDTO;
import com.multi.gamegather.club.model.dto.HelloMessageDTO;
import com.multi.gamegather.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@RestController //@Controller
@RequestMapping("/club")
public class ClubController {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

    private final ClubService clubService;

//    @RequestMapping("/")
//    public String club(){
//        return "redirect:/club/club";
//    }

//    @GetMapping("/home")
//    public String club(){
//        return "club/club";
//    }

    @GetMapping("/club")            // 오류 있음
    public String club(){
        return "club/club";
    }

    @PostMapping("/club")
    public String club2(){
        return "redirect:/club";
    }



    @PostMapping
    public void createRoom(
        @RequestBody CreateClubRequestDTO data
        // ,@CurrentUser UserDetails currentUser
    ) {
        // data.setUserId(currentUser.getId());
        System.out.println("1");
        clubService.createClub(data);

    }

//    @GetMapping
//    public List<ClubRoomDTO> findAllRoom() {
//        return clubService.findAllRoom();
//    }

    @MessageMapping("/club/hello")
    @SendTo("/sub/club/test")
    public String test(HelloMessageDTO message) throws Exception {
        System.out.println("123123123");
        return ("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
