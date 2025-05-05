package com.app.strategy;

import java.util.List;

import com.app.enums.TranscriptionType;
import com.app.model.Transcription;

public interface TranscriptionStrategy {

    boolean supports(TranscriptionType type);

    List<Transcription> transcribe(Transcription transcription);
}
