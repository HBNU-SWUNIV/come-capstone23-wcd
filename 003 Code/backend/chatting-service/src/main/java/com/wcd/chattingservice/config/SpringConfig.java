package com.wcd.chattingservice.config;

import com.wcd.chattingservice.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class SpringConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler; // jwt 인증

    // 웹소켓 configuration의 addHandler 메소드와 유사
    // cors, SockJS 설정 가능
    /*
       STOMP를 사용하면 웹소켓만 사용할 때와 다르게 하나의 연결주소마다 핸들러 클래스를 따로 구현할 필요없이
       Controller 방식으로 간편하게 사용할 수 있다.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url => /ws
        registry.addEndpoint("/ws") // 연결될 엔드포인트
                .setAllowedOrigins("https://wcd.kro.kr", "http://wcd.kro.kr", "http://localhost:3000", "https://localhost:3000", "http://localhost:8080", "ws://localhost:3000", "wss://localhost:3000", "ws://wcd.kro.kr", "wss://wcd.kro.kr", "10.1.1.66")
                .setHandshakeHandler(new MyHandshakeHandler())
                .withSockJS(); // SocketJS 를 연결한다는 설정
    }

    // STOMP에서 사용하는 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // enableSimpleBroker: 내장 메시지 브로커를 사용하기 위한 메소드
        // 파라미터로 지정한 prefix가 붙은 메시지를 발행할 경우, 메시지 브로커가 이를 처리하게 된다.
        // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
        registry.enableSimpleBroker("/topic");

        // 메세지 핸들러로 라우팅 되는 prefix를 파라미터로 지정할 수 있다.
        // 메시지 가공 처리가 필요한 경우, 가공 핸들러로 메시지를 라우팅 되도록하는 설정
        // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
        registry.setApplicationDestinationPrefixes("/app");
    }

    // URL을 로깅하기 위한 커스텀 핸드셰이크 핸들러
    private static class MyHandshakeHandler extends DefaultHandshakeHandler {
        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            Principal principal = super.determineUser(request, wsHandler, attributes);

            // 연결된 URL을 로그로 출력
            String connectionUrl = request.getURI().toString();
            log.info("WebSocket 연결 URL: {}", connectionUrl);

            return principal;
        }
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
}
