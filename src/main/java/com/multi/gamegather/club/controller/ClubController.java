package com.multi.gamegather.club.controller;

import com.multi.gamegather.chat.dto.ChatRoom;
import com.multi.gamegather.chat.service.ChatService;
import com.multi.gamegather.club.dto.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

     private final ChatService chatService;

    @GetMapping("/club")            // 오류 있음
    public String club(){
        return "club/club";
    }

    @PostMapping("/club")
    public String club2(){
        return "redirect:/club";
    }

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @MessageMapping("/club/hello")
    @SendTo("/sub/club/test")
    public String test(HelloMessage message) throws Exception {
        System.out.println("123123123");
        return ("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}