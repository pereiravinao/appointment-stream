package com.app.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AudioStreamSessionStatusResponse {
    private String webSocketSessionId;
    private Long audioSessionId;
    private int nextSequenceNumber;
}
