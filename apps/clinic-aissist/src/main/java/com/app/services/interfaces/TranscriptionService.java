package com.app.services.interfaces;

import java.util.List;

import com.app.model.AudioChunk;
import com.app.model.AudioSession;

public interface TranscriptionService {
    List<AudioChunk> transcribe(AudioSession audioSession, String audioUrl, int startingSequenceNumber);

}
