package com.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transcription extends BaseModel {

    private AudioSession audioSession;
    private String text;
    private Speaker speaker;
    private LocalDateTime transcribedAt;

    public Transcription(String text, Long speakerId) {
        this.text = text;
        this.speaker = new Speaker(speakerId);
    }

}