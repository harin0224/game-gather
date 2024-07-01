package com.multi.gamegather.rchat.chatlog.service;

import com.multi.gamegather.rchat.chatlog.model.dto.RChatLogDTO;

import java.util.List;

public interface RChatLogService {
    void saveLog(RChatLogDTO rChatLogDTO);

    List<RChatLogDTO> selectLog();
}
