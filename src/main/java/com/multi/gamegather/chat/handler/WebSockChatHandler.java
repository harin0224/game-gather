package com.multi.gamegather.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.gamegather.chat.dto.ChatMessage;
import com.multi.gamegather.chat.dto.ChatRoom;
import com.multi.gamegather.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);
        //페이로드란 전송되는 데이터를 의미한다.

//        TextMessage textMessage = new TextMessage("welcome chat server");
//        session.sendMessage(textMessage);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }
}
