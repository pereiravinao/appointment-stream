package com.app.response;

import com.app.enums.WebSocketMessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessageResponse {

    private WebSocketMessageType type;
    private String requestId;
    private Object payload;
}
