package com.multi.gamegather.rchat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
    int count = 1;
    @GetMapping("/session/setting")
    public ResponseEntity<String> getRoomId(HttpSession session) {

        String setting2 = (String)session.getAttribute("setting2");
        String headcnt = (String)session.getAttribute("Headcnt");

        int usermax = Integer.parseInt(headcnt);
        if(count > usermax){
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
