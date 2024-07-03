package com.multi.gamegather.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/success")
public class CustomSuccess {

    @RequestMapping("/manner")
    public void manner(){}

    @RequestMapping("/ban")
    public void ban(){}
}
