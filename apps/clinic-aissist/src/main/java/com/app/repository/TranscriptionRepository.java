package com.app.repository;

import org.springframework.stereotype.Repository;

import com.app.entity.TranscriptionEntity;

@Repository
public interface TranscriptionRepository extends BaseRepository<TranscriptionEntity> {

    // List<TranscriptionEntity> findByRecordingSessionId(String recordingSessionId);

}
