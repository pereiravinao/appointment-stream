package com.app.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.enums.SessionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordingSession extends BaseModel {

    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    private SessionStatus status;
    private List<Media> medias;
    private List<Transcription> transcriptions;

    public RecordingSession(String sessionId) {
        if (sessionId == null) {
            this.sessionId = UUID.randomUUID().toString();
        } else {
            this.sessionId = sessionId;
        }
    }

}