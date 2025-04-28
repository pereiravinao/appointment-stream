package com.app.parameter;

import lombok.Getter;

@Getter
public class AudioChunkParameter {
    private String sessionId;
    private int sequenceNumber;
    private byte[] audioData;

}
