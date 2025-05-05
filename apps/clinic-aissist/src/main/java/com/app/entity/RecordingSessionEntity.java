package com.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.app.enums.SessionStatus;
import com.app.model.RecordingSession;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_recording_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordingSessionEntity extends BaseEntity {

    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private SessionStatus status;

    // @OneToMany(mappedBy = "recordingSession", cascade = CascadeType.ALL)
    @Transient
    private List<MediaEntity> medias;

    @OneToMany(mappedBy = "recordingSession", cascade = CascadeType.ALL)
    private List<TranscriptionEntity> transcriptions;

    public RecordingSession toModel() {
        var recordingSession = new RecordingSession();
        recordingSession.setId(super.getId());
        recordingSession.setCreatedAt(super.getCreatedAt());
        recordingSession.setUpdatedAt(super.getUpdatedAt());
        recordingSession.setVersion(super.getVersion());
        recordingSession.setSessionId(this.sessionId);
        recordingSession.setStartedAt(this.startedAt);
        recordingSession.setEndedAt(this.endedAt);
        recordingSession.setStatus(this.status);
        // if (this.medias != null) {
        // recordingSession.setMedias(this.medias.stream().map(MediaEntity::toModel).toList());
        // }
        // if (this.transcriptions != null) {
        // recordingSession.setTranscriptions(this.transcriptions.stream().map(TranscriptionEntity::toModel).toList());
        // }
        return recordingSession;
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
        // if (audioSession.getMedias() != null) {
        // this.medias =
        // audioSession.getMedias().stream().map(MediaEntity::new).toList();
        // }
        // if (audioSession.getTranscriptions() != null) {
        // this.transcriptions =
        // audioSession.getTranscriptions().stream().map(TranscriptionEntity::new).toList();
        // }
    }

}
