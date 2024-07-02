package com.multi.gamegather.fredchat.controller;

import com.multi.gamegather.fredchat.service.FredChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController //@Controller
@RequestMapping("/fredchat")
public class FredChatController {
    private final FredChatService fredChatService;



}
