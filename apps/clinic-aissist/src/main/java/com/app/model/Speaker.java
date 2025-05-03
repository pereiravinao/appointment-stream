package com.app.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Speaker extends BaseModel {

    private String name;
    private String metadata;
    private List<Transcription> transcriptions;

    public Speaker(Long id) {
        super.setId(id);
    }

    public Speaker(String name) {
        this.name = name;
    }

}