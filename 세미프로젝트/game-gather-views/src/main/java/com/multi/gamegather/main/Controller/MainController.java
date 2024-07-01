package com.multi.gamegather.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "member/login";
    }

    @PostMapping("/")
    public String main2(){
        return "redirect:/";
    }

    @RequestMapping("/main/menubar")
    public void menubar(){

    }

}
