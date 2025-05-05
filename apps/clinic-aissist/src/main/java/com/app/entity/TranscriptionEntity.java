package com.app.entity;

import com.app.enums.TranscriptionType;
import com.app.model.Transcription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_transcription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptionEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "recording_session_id")
    private RecordingSessionEntity recordingSession;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    private String speaker;

    @Enumerated(EnumType.STRING)
    private TranscriptionType type;

    private String audioUrl;

    public Transcription toModel() {
        var transcription = new Transcription();
        transcription.setId(super.getId());
        transcription.setCreatedAt(super.getCreatedAt());
        transcription.setUpdatedAt(super.getUpdatedAt());
        transcription.setVersion(super.getVersion());
        transcription.setText(this.text);
        transcription.setSpeaker(this.speaker);
        transcription.setType(this.type);
        transcription.setAudioUrl(this.audioUrl);
        return transcription;
    }

    public TranscriptionEntity(Transcription transcription) {
        super.setId(transcription.getId());
        super.setCreatedAt(transcription.getCreatedAt());
        super.setUpdatedAt(transcription.getUpdatedAt());
        super.setVersion(transcription.getVersion());
        this.text = transcription.getText();
        this.speaker = transcription.getSpeaker();
        if (transcription.getRecordingSession() != null) {
            this.recordingSession = new RecordingSessionEntity(transcription.getRecordingSession());
        }
        this.type = transcription.getType();
        this.audioUrl = transcription.getAudioUrl();
    }
}
