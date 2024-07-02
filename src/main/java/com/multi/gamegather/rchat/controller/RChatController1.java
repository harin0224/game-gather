package com.multi.gamegather.rchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rchat")
public class RChatController1 {


    @GetMapping("/rchat")
    public String rchatpage(){
        return "rchat/rchat";

    }

    @GetMapping("/score")
    public String scorepage(){
        return "rchat/score";

    }



}