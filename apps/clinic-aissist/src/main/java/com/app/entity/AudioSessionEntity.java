package com.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.app.enums.AudioSessionStatus;
import com.app.model.AudioSession;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_audio_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioSessionEntity extends BaseEntity {

    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private AudioSessionStatus status;
    private String finalAudioUrl;

    @OneToMany(mappedBy = "audioSession", cascade = CascadeType.ALL)
    private List<AudioChunkEntity> audioChunks;

    @OneToMany(mappedBy = "audioSession", cascade = CascadeType.ALL)
    private List<TranscriptionEntity> transcriptions;

    public AudioSession toModel() {
        var audioSession = new AudioSession();
        audioSession.setId(super.getId());
        audioSession.setCreatedAt(super.getCreatedAt());
        audioSession.setUpdatedAt(super.getUpdatedAt());
        audioSession.setVersion(super.getVersion());
        audioSession.setSessionId(this.sessionId);
        audioSession.setStartedAt(this.startedAt);
        audioSession.setEndedAt(this.endedAt);
        audioSession.setStatus(this.status);
        audioSession.setFinalAudioUrl(this.finalAudioUrl);
        if (this.audioChunks != null) {
            audioSession.setAudioChunks(this.audioChunks.stream().map(AudioChunkEntity::toModel).toList());
        }
        if (this.transcriptions != null) {
            audioSession.setTranscriptions(this.transcriptions.stream().map(TranscriptionEntity::toModel).toList());
        }
        return audioSession;
    }

    public AudioSessionEntity(AudioSession audioSession) {
        super.setId(audioSession.getId());
        super.setCreatedAt(audioSession.getCreatedAt());
        super.setUpdatedAt(audioSession.getUpdatedAt());
        super.setVersion(audioSession.getVersion());
        this.sessionId = audioSession.getSessionId();
        this.startedAt = audioSession.getStartedAt();
        this.endedAt = audioSession.getEndedAt();
        this.status = audioSession.getStatus();
        this.finalAudioUrl = audioSession.getFinalAudioUrl();
        if (audioSession.getAudioChunks() != null) {
            this.audioChunks = audioSession.getAudioChunks().stream().map(AudioChunkEntity::new).toList();
        }
        if (audioSession.getTranscriptions() != null) {
            this.transcriptions = audioSession.getTranscriptions().stream().map(TranscriptionEntity::new).toList();
        }
    }

}
