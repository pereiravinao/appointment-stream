package com.app.handler.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.enums.WebSocketMessageType;
import com.app.handler.MessageHandlerStrategy;
import com.app.model.WebSocketMessage;
import com.app.response.WebSocketMessageResponse;
import com.app.services.interfaces.RecordingSessionService;
import com.app.services.interfaces.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUploadUrlHandler implements MessageHandlerStrategy {

    private final StorageService storageService;
    private final RecordingSessionService recordingSessionService;

    @Override
    public boolean supports(WebSocketMessageType type) {
        return type.equals(WebSocketMessageType.GET_UPLOAD_URL);
    }

    @Override
    public WebSocketMessageResponse handle(WebSocketMessage message) {
        String requestId = buildRequestId(message);
        String chunkNumber = buildChunkNumber(message);

        String filename = "session/" + requestId + "/chunk/" + chunkNumber + ".wav";
        String uploadUrl = storageService.generatePresignedUploadUrl(filename);
        String objectUrl = storageService.getPublicUrl(filename);

        Map<String, Object> response = Map.of(
                "uploadUrl", uploadUrl,
                "objectUrl", objectUrl,
                "chunkNumber", chunkNumber);

        return new WebSocketMessageResponse(WebSocketMessageType.UPLOAD_URL_RESPONSE, requestId, response);
    }

    private String buildRequestId(WebSocketMessage message) {
        if (message.getRequestId() != null) {
            return message.getRequestId();
        }
        var recordingSession = recordingSessionService.startNewSession();
        return recordingSession.getSessionId();
    }

    private String buildChunkNumber(WebSocketMessage message) {
        return String.valueOf(System.currentTimeMillis());
    }
}
