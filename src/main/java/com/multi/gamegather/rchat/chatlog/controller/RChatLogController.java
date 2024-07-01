package com.multi.gamegather.rchat.chatlog.controller;

import com.multi.gamegather.rchat.chatlog.model.dto.RChatLogDTO;
import com.multi.gamegather.rchat.chatlog.service.RChatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rchat")
public class RChatLogController {
    private final RChatLogService rChatLogService;

    @Autowired
    public RChatLogController(RChatLogService rChatLogService) {
        this.rChatLogService = rChatLogService;
    }

    @PostMapping("/save-chat-logs")
    @ResponseBody
    public String saveChatLogs(@RequestBody List<Map<String, String>> chatLogs, Principal principal) {
        System.out.println("saveChatLogs called");
        try {
            for (Map<String, String> chatLog : chatLogs) {
                System.out.println("Processing chat log: " + chatLog.get("message"));
                RChatLogDTO rChatLogDTO = new RChatLogDTO();
                rChatLogDTO.setRchatcontent(chatLog.get("message"));
                String username = principal.getName();
                rChatLogDTO.setUserid(username);

                rChatLogService.saveLog(rChatLogDTO);
            }
            return "Chat logs saved successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save chat logs.";
        }
    }
}