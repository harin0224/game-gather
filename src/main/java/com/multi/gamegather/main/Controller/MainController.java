package com.multi.gamegather.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")//추가 main에없었음
public class MainController {

    @GetMapping("/")
    public String matchpage() {

        return "/main/main";

    }


//    @GetMapping("/club")// /club으로 바꿈
//    public String main() {
//        return "club/club";
//    }

    //    @PostMapping("/")
//    public String main2(){
//        return "redirect:/";
//    }

}
