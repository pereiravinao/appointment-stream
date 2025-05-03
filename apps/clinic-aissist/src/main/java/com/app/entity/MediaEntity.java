package com.app.entity;

import com.app.model.Media;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaEntity extends BaseEntity {

    private String url;
    private String name;
    private Integer duration;
    private String type;
    private String size;
    private String hash;
    private String extension;
    private String mimeType;

    public Media toModel() {
        var media = new Media();
        media.setId(super.getId());
        media.setCreatedAt(super.getCreatedAt());
        media.setUpdatedAt(super.getUpdatedAt());
        media.setVersion(super.getVersion());
        media.setUrl(this.url);
        media.setName(this.name);
        media.setDuration(this.duration);
        media.setType(this.type);
        media.setSize(this.size);
        media.setHash(this.hash);
        media.setExtension(this.extension);
        media.setMimeType(this.mimeType);
        return media;

    }

    public MediaEntity(Media audioChunk) {
        super.setId(audioChunk.getId());
        super.setCreatedAt(audioChunk.getCreatedAt());
        super.setUpdatedAt(audioChunk.getUpdatedAt());
        super.setVersion(audioChunk.getVersion());
        this.url = audioChunk.getUrl();
        this.name = audioChunk.getName();
        this.duration = audioChunk.getDuration();
        this.type = audioChunk.getType();
        this.size = audioChunk.getSize();
        this.hash = audioChunk.getHash();
        this.extension = audioChunk.getExtension();
        this.mimeType = audioChunk.getMimeType();
    }
}
