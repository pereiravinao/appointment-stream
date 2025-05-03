package com.app.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.app.enums.WebSocketMessageType;
import com.app.factory.WebSocketHandlerFactory;
import com.app.model.WebSocketMessage;
import com.app.response.WebSocketMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AudioWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketHandlerFactory handlerFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("Mensagem recebida WS: {}", message.getPayload());

        WebSocketMessage incoming = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);
        var handlerOpt = handlerFactory.getHandlerFor(incoming.getType());

        if (handlerOpt.isPresent()) {
            WebSocketMessageResponse response = handlerOpt.get().handle(incoming);
            String jsonResponse = objectMapper.writeValueAsString(response);
            session.sendMessage(new TextMessage(jsonResponse));
            log.info("Mensagem enviada via WS com sucesso: type={}, reqId={}", response.getType(), response.getRequestId());

        } else {
            session.sendMessage(new TextMessage("""
                    { "type": "%s", "requestId": "%s", "message": "Unknown message type: %s" }
                    """.formatted(WebSocketMessageType.ERROR.value(), incoming.getRequestId(), incoming.getType())));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Conexão WebSocket encerrada: {} - Motivo: {}", session.getId(), status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Erro no transporte WebSocket para sessão {}: {}", session.getId(), exception.getMessage(),
                exception);
    }

}
