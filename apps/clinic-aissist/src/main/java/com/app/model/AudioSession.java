package com.app.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.enums.AudioSessionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioSession extends BaseModel {

    private String requestId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String finalAudioUrl;

    private AudioSessionStatus status;
    private List<AudioChunk> audioChunks;
    private List<Transcription> transcriptions;

    public AudioSession(String requestId) {
        if (requestId == null) {
            this.requestId = UUID.randomUUID().toString();
        } else {
            this.requestId = requestId;
        }
    }

}