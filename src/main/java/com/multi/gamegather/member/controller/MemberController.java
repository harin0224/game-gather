package com.multi.gamegather.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @RequestMapping("/login")
    public void login() {

    }

    @RequestMapping("/loginProc")
    public String loginProc() {
        return "member/mypage";
    }

    @GetMapping("/signup")
    public void signup() {

    }

    @RequestMapping("/mypage")
    public void mypage() {

    }

    @GetMapping("/memberinfo")
    public void memberInfo() {

    }

}