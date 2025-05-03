package com.app.entity;

import java.time.LocalDateTime;

import com.app.model.Transcription;

import jakarta.persistence.Entity;
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
    @JoinColumn(name = "session_id")
    private RecordingSessionEntity session;

    private String text;

    private String speaker;

    private LocalDateTime transcribedAt;

    public Transcription toModel() {
        var transcription = new Transcription();
        transcription.setId(super.getId());
        transcription.setCreatedAt(super.getCreatedAt());
        transcription.setUpdatedAt(super.getUpdatedAt());
        transcription.setVersion(super.getVersion());
        transcription.setText(this.text);
        transcription.setSpeaker(this.speaker);
        transcription.setTranscribedAt(this.transcribedAt);
        return transcription;
    }

    public TranscriptionEntity(Transcription transcription) {
        super.setId(transcription.getId());
        super.setCreatedAt(transcription.getCreatedAt());
        super.setUpdatedAt(transcription.getUpdatedAt());
        super.setVersion(transcription.getVersion());
        this.text = transcription.getText();
        this.speaker = transcription.getSpeaker();
        this.transcribedAt = transcription.getTranscribedAt();
        if (transcription.getSession() != null) {
            this.session = new RecordingSessionEntity(transcription.getSession());
        }
    }
}
