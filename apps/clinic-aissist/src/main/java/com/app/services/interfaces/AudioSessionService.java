package com.app.services.interfaces;

import java.util.List;

import com.app.model.AudioChunk;
import com.app.model.AudioSession;
import com.app.model.SpeechSegment;

public interface AudioSessionService {
    AudioSession createSession(String audioUrl, List<SpeechSegment> segments);

    void saveChunks(List<AudioChunk> chunks);

    void saveAudioChunk(String sessionId, byte[] audioData);

    void closeSession(String sessionId);

    AudioSession startNewSession();

    
}
