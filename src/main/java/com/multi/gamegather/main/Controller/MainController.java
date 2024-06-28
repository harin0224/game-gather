package com.multi.gamegather.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "club/club";
    }

    @PostMapping("/")
    public String main2(){
        return "redirect:/";
    }

    @GetMapping("/test")            // 오류 있음
    public String club(){
        return "main/a";
    }


}
