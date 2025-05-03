package com.app.services.impl;

import org.springframework.stereotype.Service;

import com.app.entity.RecordingSessionEntity;
import com.app.model.RecordingSession;
import com.app.repository.RecordingSessionRepository;
import com.app.services.interfaces.RecordingSessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordingSessionBaseServiceImpl implements RecordingSessionService {

    private final RecordingSessionRepository recordingSessionRepository;

    @Transactional
    @Override
    public RecordingSession startNewSession() {
        RecordingSession session = new RecordingSession(null);
        return recordingSessionRepository.save(new RecordingSessionEntity(session)).toModel();
    }

    @Override
    public void closeSession(String sessionId) {
        return;
    }
}
