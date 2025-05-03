package com.app.handler.impl;

import org.springframework.stereotype.Component;

import com.app.enums.WebSocketMessageType;
import com.app.handler.MessageHandlerStrategy;
import com.app.model.WebSocketMessage;
import com.app.response.WebSocketMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UploadUrlResponseHandler implements MessageHandlerStrategy {

    @Override
    public boolean supports(WebSocketMessageType type) {
        return type.equals(WebSocketMessageType.UPLOAD_URL_RESPONSE);
    }

    @Override
    public WebSocketMessageResponse handle(WebSocketMessage message) {
        log.warn("UPLOAD_URL_RESPONSE handler foi chamado no servidor, o que não deveria ocorrer. Ignorando.");
        return new WebSocketMessageResponse(WebSocketMessageType.UPLOAD_URL_RESPONSE, message.getRequestId(),
                "UPLOAD_URL_RESPONSE é um tipo de mensagem de saída, não deve ser processado pelo servidor.");
    }
}
