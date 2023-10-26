package aug.laundry.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    // 메세지를 설정하는 부분
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 스프링에서 제공하는 내장 브로커를 사용하겠다는 설정
        // 파라매터의 의미(/topic)은 prefix로 부터 붙은 메세지가 송신되었을때 그 메세지를 메세지브로커가 처리하겠다는 의미
        // prefix에 /topic이라는 경로가 붙은 메세지가 송신되었을때 simpleBroker가 메세지를 받고 구독자들에게 전달해준다는 의미
        // 1:1 1:다 가능
        registry.enableSimpleBroker("/topic");
        // 메세지의 어떤 처리나 가공이 필요할 떄 앤들러를 타게 할 수 있다.
        // /app이 붙어있는 경로로 발신되면 해당 경로를 처리하고 있는 핸들러로 전달하는 것
        registry.setApplicationDestinationPrefixes("/message");
    }
}
