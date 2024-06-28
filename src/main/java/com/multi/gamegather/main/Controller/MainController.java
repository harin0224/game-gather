package com.multi.gamegather.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "main/main";
    }

    @RequestMapping("/main/main")
    public void main3(){

    }

    @PostMapping("/")
    public String main2(){
        return "redirect:/";
    }

}
