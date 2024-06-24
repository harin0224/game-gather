package com.multi.gamegather.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
//@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

//    private final WebSocketHandler webSocketHandler;

//    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
////    }


    // stomp 를 통한 pub/sub 기능 설정
    // 메세지 주고받는 규칙
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); //발행자가 "/topic"의 경로로 메시지를 주면 구독자들에게 전달
        config.setApplicationDestinationPrefixes("/pub"); // 발행자가 "/"의 경로로 메시지를 주면 가공을 해서 구독자들에게 전달
    }

    // 클라이언트에서 서버(소켓) 연결하는 주소 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/club-websocket").withSockJS(); // 커넥션을 맺는 경로 설정. 만약 WebSocket을 사용할 수 없는 브라우저라면 다른 방식을 사용하도록 설정
    }
}