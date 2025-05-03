package com.app.services.interfaces;

import java.util.List;

import com.app.model.Media;
import com.app.model.RecordingSession;

public interface TranscriptionService {
    List<Media> transcribe(RecordingSession audioSession, String audioUrl, int startingSequenceNumber);

}
