package com.app.model;

import java.time.LocalDateTime;

import com.app.enums.TranscriptionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transcription extends BaseModel {

    private RecordingSession session;
    private String text;
    private String speaker;
    private LocalDateTime transcribedAt;
    private String audioUrl;
    private TranscriptionType type;

    public Transcription(String text, String speaker) {
        this.text = text;
        this.speaker = speaker;
    }

}