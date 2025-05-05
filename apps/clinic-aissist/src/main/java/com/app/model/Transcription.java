package com.app.model;

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

    private RecordingSession recordingSession;
    private String text;
    private String speaker;
    private String audioUrl;
    private String key;
    private TranscriptionType type;

    public Transcription(RecordingSession recordingSession, TranscriptionType type, String key) {
        this.recordingSession = recordingSession;
        this.type = type;
        this.key = key;
    }

}