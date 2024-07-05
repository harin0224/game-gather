package com.multi.gamegather.fredchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/fredchat")
public class FChatController1 {


    @GetMapping("/fredchat")
    public String rchatpage(){
        return "fredchat/fredchat";

    }


}