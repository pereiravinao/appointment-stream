package com.app.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioChunk extends BaseModel {

    private AudioSession audioSession;
    private int sequenceNumber;
    private String audioUrl;
    private String speakerLabel;
    private String text;
    private Float startTime;
    private Float endTime;
    private LocalDateTime receivedAt;

}