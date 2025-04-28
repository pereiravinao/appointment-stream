package com.app.entity;

import java.time.LocalDateTime;

import com.app.model.AudioChunk;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_audio_chunk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioChunkEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "audio_session_id")
    private AudioSessionEntity audioSession;

    private int sequenceNumber;
    private String audioUrl;
    private String speakerLabel;
    private String text;
    private Float startTime;
    private Float endTime;
    private LocalDateTime receivedAt;

    public AudioChunk toModel() {
        var audioChunk = new AudioChunk();
        audioChunk.setId(super.getId());
        audioChunk.setCreatedAt(super.getCreatedAt());
        audioChunk.setUpdatedAt(super.getUpdatedAt());
        audioChunk.setVersion(super.getVersion());
        audioChunk.setReceivedAt(this.receivedAt);
        audioChunk.setSequenceNumber(this.sequenceNumber);
        audioChunk.setAudioUrl(this.audioUrl);
        audioChunk.setSpeakerLabel(this.speakerLabel);
        audioChunk.setText(this.text);
        audioChunk.setStartTime(this.startTime);
        audioChunk.setEndTime(this.endTime);
        if (this.audioSession != null) {
            audioChunk.setAudioSession(this.audioSession.toModel());
        }
        return audioChunk;

    }

    public AudioChunkEntity(AudioChunk audioChunk) {
        super.setId(audioChunk.getId());
        super.setCreatedAt(audioChunk.getCreatedAt());
        super.setUpdatedAt(audioChunk.getUpdatedAt());
        super.setVersion(audioChunk.getVersion());
        this.sequenceNumber = audioChunk.getSequenceNumber();
        this.audioUrl = audioChunk.getAudioUrl();
        this.speakerLabel = audioChunk.getSpeakerLabel();
        this.text = audioChunk.getText();
        this.startTime = audioChunk.getStartTime();
        this.endTime = audioChunk.getEndTime();
        this.receivedAt = audioChunk.getReceivedAt();
    }
}
