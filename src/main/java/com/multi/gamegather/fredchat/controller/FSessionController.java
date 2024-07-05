package com.multi.gamegather.fredchat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FSessionController {
    int count = 1;
    @GetMapping("/session/fsetting")
    public ResponseEntity<String> getRoomId(HttpSession session) {
        //fredDTO.getferdCode
        String fredCode = "amdinUser";
        String setting2 = fredCode;

//        String setting2 = (String)session.getAttribute("setting2");



        if(count > 2){
            setting2 = setting2 + count;
            count = 1;
        }

        if (setting2 != null) {
            count++;
            return ResponseEntity.ok(setting2);

        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
