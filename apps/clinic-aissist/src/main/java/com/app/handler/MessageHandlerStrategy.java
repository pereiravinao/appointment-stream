package com.app.handler;

import com.app.enums.WebSocketMessageType;
import com.app.model.WebSocketMessage;
import com.app.response.WebSocketMessageResponse;

public interface MessageHandlerStrategy {
    boolean supports(WebSocketMessageType type);

    WebSocketMessageResponse handle(WebSocketMessage message);

}
