package com.app.model;

import java.util.Map;

import com.app.enums.WebSocketMessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {

    private WebSocketMessageType type;
    private String requestId;
    private Map<String, Object> payload;
}
