package com.app.handler.impl;

import org.springframework.stereotype.Component;

import com.app.enums.WebSocketMessageType;
import com.app.handler.MessageHandlerStrategy;
import com.app.model.WebSocketMessage;
import com.app.response.WebSocketMessageResponse;
import com.app.services.interfaces.StorageEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EndSessionHandler implements MessageHandlerStrategy {

    private final StorageEventService storageEventService;

    @Override
    public boolean supports(WebSocketMessageType type) {
        return type.equals(WebSocketMessageType.END_SESSION);
    }

    @Override
    public WebSocketMessageResponse handle(WebSocketMessage message) {
        log.info("EndSessionHandler handle: {}", message);
        var sessionId = message.getRequestId();
        storageEventService.processFullAudio(sessionId);
        return new WebSocketMessageResponse(WebSocketMessageType.END_SESSION, sessionId, "Session closed");
    }
}
