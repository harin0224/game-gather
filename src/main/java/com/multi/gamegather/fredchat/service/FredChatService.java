package com.multi.gamegather.fredchat.service;

import com.multi.gamegather.fredchat.model.dao.FredChatDAO;
import com.multi.gamegather.fredchat.model.dto.FredChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FredChatService {

    private final FredChatDAO fredChatDAO;

    public void saveChatMessage(FredChatMessage chatMessage) {
        fredChatDAO.insertChatMessage(chatMessage);
    }

}
