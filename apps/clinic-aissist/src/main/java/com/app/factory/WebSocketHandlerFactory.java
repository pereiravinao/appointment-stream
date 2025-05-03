package com.app.factory;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.app.enums.WebSocketMessageType;
import com.app.handler.MessageHandlerStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketHandlerFactory {

    private final List<MessageHandlerStrategy> handlers;

    public Optional<MessageHandlerStrategy> getHandlerFor(WebSocketMessageType type) {
        return handlers.stream().filter(h -> h.supports(type)).findFirst();
    }

}
