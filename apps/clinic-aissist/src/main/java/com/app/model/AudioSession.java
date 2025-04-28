package com.app.model;

import java.time.LocalDateTime;
import java.util.List;

import com.app.enums.AudioSessionStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AudioSession extends BaseModel {

    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String finalAudioUrl;

    private AudioSessionStatus status;
    private List<AudioChunk> audioChunks;
    private List<Transcription> transcriptions;

    public AudioSession(String sessionId) {
        this.sessionId = sessionId;
    }

}