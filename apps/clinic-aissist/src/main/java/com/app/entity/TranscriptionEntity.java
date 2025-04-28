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
    @JoinColumn(name = "audio_session_id")
    private AudioSessionEntity audioSession;

    private String text;

    @ManyToOne
    @JoinColumn(name = "speaker_id")
    private SpeakerEntity speaker;

    private LocalDateTime transcribedAt;

    public Transcription toModel() {
        var transcription = new Transcription();
        transcription.setId(super.getId());
        transcription.setCreatedAt(super.getCreatedAt());
        transcription.setUpdatedAt(super.getUpdatedAt());
        transcription.setVersion(super.getVersion());
        transcription.setText(this.text);
        if (this.speaker != null) {
            transcription.setSpeaker(this.speaker.toModel());
        }
        transcription.setTranscribedAt(this.transcribedAt);
        return transcription;
    }

    public TranscriptionEntity(Transcription transcription) {
        super.setId(transcription.getId());
        super.setCreatedAt(transcription.getCreatedAt());
        super.setUpdatedAt(transcription.getUpdatedAt());
        super.setVersion(transcription.getVersion());
        this.text = transcription.getText();
        if (transcription.getSpeaker() != null) {
            this.speaker = new SpeakerEntity(transcription.getSpeaker());
        }
        this.transcribedAt = transcription.getTranscribedAt();
        if (transcription.getAudioSession() != null) {
            this.audioSession = new AudioSessionEntity(transcription.getAudioSession());
        }
    }
}
