package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeechSegment {
    private Speaker speaker;
    private String text;
    private Float start;
    private Float end;

    public SpeechSegment(Integer speaker, String text, Float start, Float end) {
        this.speaker = new Speaker(Long.parseLong(speaker.toString()));
        this.text = text;
        this.start = start;
        this.end = end;
    }
}
