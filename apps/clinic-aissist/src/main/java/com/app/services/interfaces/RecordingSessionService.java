package com.app.services.interfaces;

import com.app.model.RecordingSession;

public interface RecordingSessionService {

    void closeSession(String sessionId);

    RecordingSession startNewSession();

    RecordingSession findBySessionId(String sessionId);

}
