package com.multi.gamegather.rchat.controller;

import com.multi.gamegather.rchat.dto.HelloMessage;
import com.multi.gamegather.rchat.dto.RChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/rchat")
public class RChatController {


//    private RChatMessage rChatMessage = new RChatMessage("박재민");




    @PostMapping("/rchat")
    public String rchat2() {
        return "redirect:/rchat";
    }


    @MessageMapping("/rchat/{roomId}/hello")
    @SendTo("/sub/rchat/{roomId}")
    public String test(@DestinationVariable("roomId") String roomId, HelloMessage message, Principal principal) throws Exception {
        String username = principal.getName();
        RChatMessage rChatMessage = new RChatMessage(username);

        return (rChatMessage.getSender() + " : " + message.getChatting());
    }

}