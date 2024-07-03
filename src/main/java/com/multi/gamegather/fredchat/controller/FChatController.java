package com.multi.gamegather.fredchat.controller;

import com.multi.gamegather.fredchat.dto.FHelloMessage;
import com.multi.gamegather.fredchat.dto.FChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class FChatController {

    @PostMapping("/fredchat")
    public String rchat2() {
        return "redirect:/fredchat";
    }


    @MessageMapping("/fredchat/{roomId}/hello")
    @SendTo("/sub/fredchat/{roomId}")
    public String test(@DestinationVariable("roomId") String roomId, FHelloMessage message, Principal principal) throws Exception {
        String username = principal.getName();
        FChatMessage fChatMessage = new FChatMessage(username);

        return (fChatMessage.getSender() + " : " + message.getChatting());
    }

}