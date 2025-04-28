package com.app.entity;

import java.util.List;

import com.app.model.Speaker;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_speaker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerEntity extends BaseEntity {

    private String name;
    private String metadata;

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL)
    private List<TranscriptionEntity> transcriptions;

    public Speaker toModel() {
        var speaker = new Speaker();
        speaker.setId(super.getId());
        speaker.setCreatedAt(super.getCreatedAt());
        speaker.setUpdatedAt(super.getUpdatedAt());
        speaker.setVersion(super.getVersion());
        speaker.setName(this.name);
        speaker.setMetadata(this.metadata);
        if (this.transcriptions != null) {
            speaker.setTranscriptions(this.transcriptions.stream().map(TranscriptionEntity::toModel).toList());
        }
        return speaker;
    }

    public SpeakerEntity(Speaker speaker) {
        super.setId(speaker.getId());
        super.setCreatedAt(speaker.getCreatedAt());
        super.setUpdatedAt(speaker.getUpdatedAt());
        super.setVersion(speaker.getVersion());
        this.name = speaker.getName();
        this.metadata = speaker.getMetadata();
        if (speaker.getTranscriptions() != null) {
            this.transcriptions = speaker.getTranscriptions().stream().map(TranscriptionEntity::new).toList();
        }
    }

}
