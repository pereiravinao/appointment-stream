package com.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.app.enums.SessionStatus;
import com.app.model.RecordingSession;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordingSessionEntity extends BaseEntity {

    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private SessionStatus status;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<MediaEntity> medias;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<TranscriptionEntity> transcriptions;

    public RecordingSession toModel() {
        var audioSession = new RecordingSession();
        audioSession.setId(super.getId());
        audioSession.setCreatedAt(super.getCreatedAt());
        audioSession.setUpdatedAt(super.getUpdatedAt());
        audioSession.setVersion(super.getVersion());
        audioSession.setSessionId(this.sessionId);
        audioSession.setStartedAt(this.startedAt);
        audioSession.setEndedAt(this.endedAt);
        audioSession.setStatus(this.status);
        if (this.medias != null) {
            audioSession.setMedias(this.medias.stream().map(MediaEntity::toModel).toList());
        }
        if (this.transcriptions != null) {
            audioSession.setTranscriptions(this.transcriptions.stream().map(TranscriptionEntity::toModel).toList());
        }
        return audioSession;
    }

    public RecordingSessionEntity(RecordingSession audioSession) {
        super.setId(audioSession.getId());
        super.setCreatedAt(audioSession.getCreatedAt());
        super.setUpdatedAt(audioSession.getUpdatedAt());
        super.setVersion(audioSession.getVersion());
        this.sessionId = audioSession.getSessionId();
        this.startedAt = audioSession.getStartedAt();
        this.endedAt = audioSession.getEndedAt();
        this.status = audioSession.getStatus();
        if (audioSession.getMedias() != null) {
            this.medias = audioSession.getMedias().stream().map(MediaEntity::new).toList();
        }
        if (audioSession.getTranscriptions() != null) {
            this.transcriptions = audioSession.getTranscriptions().stream().map(TranscriptionEntity::new).toList();
        }
    }

}
