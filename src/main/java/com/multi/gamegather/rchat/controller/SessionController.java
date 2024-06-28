package com.multi.gamegather.rchat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/session/gamename")
    public ResponseEntity<String> getGamename(HttpSession session) {
        String setting2 = (String)session.getAttribute("setting2");
        if (setting2 != null) {
            return ResponseEntity.ok(setting2);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
//@Controller
//@RequestMapping("/session")
//public class SessionController {
//
//    @Autowired
//    private MatchDTO matchDTO;
//
//    @GetMapping("/gamename")
//    @ResponseBody
//    public String getGamename() {
//        return matchDTO.getGamename();
//    }
//}

//
//@GetMapping("/session/gamename")
//public ResponseEntity<String> getGamename(HttpSession session) {
//    String gamename = (String) session.getAttribute("gamename");
//    if (gamename != null) {
//        return ResponseEntity.ok(gamename);
//    } else {
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//}