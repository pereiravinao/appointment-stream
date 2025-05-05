package com.app.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.app.entity.RecordingSessionEntity;

@Repository
public interface RecordingSessionRepository extends BaseRepository<RecordingSessionEntity> {

    Optional<RecordingSessionEntity> findBySessionId(String sessionId);
}
