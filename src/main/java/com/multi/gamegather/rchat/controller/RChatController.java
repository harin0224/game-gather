package com.multi.gamegather.rchat.controller;

import com.multi.gamegather.chat.service.ChatService;
import com.multi.gamegather.rchat.dto.HelloMessage;
import com.multi.gamegather.rchat.dto.RChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/rchat")
public class RChatController {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

     private final ChatService chatService;

     private RChatMessage rChatMessage = new RChatMessage("박재민");





//    @RequestMapping("/")
//    public String club(){
//        return "redirect:/club/club";
//    }

//    @GetMapping("/home")
//    public String club(){
//        return "club/club";
//    }

//    @GetMapping("/rchat")            // 오류 있음
//    public String rchatpage(){
//        return "rchat/rchat";
//
//    }

    @PostMapping("/rchat")
    public String rchat2(){
        return "redirect:/rchat";
    }


//    @PostMapping("/rchatroom")
//    public ChatRoom createRoom(@RequestParam String name){
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping("/rchatroom")
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }

//    @MessageMapping("/rchat/hello")
//    @SendTo("/sub/rchat/test")
//    public String test(HelloMessage message) throws Exception {
//
//        return (rChatMessage.getSender() + " : " + message.getName() + "!");
//    }

    //        MatchDTO savedSetting= (MatchDTO)session.getAttribute("setting");
//        System.out.println(savedSetting);


    @MessageMapping("/rchat/{roomId}/hello")
    @SendTo("/sub/rchat/{roomId}")
    public String test(@DestinationVariable("roomId") String roomId, HelloMessage message) throws Exception {
        System.out.println(roomId);



        return (rChatMessage.getSender() + " : " + message.getName() + "!");
    }

}