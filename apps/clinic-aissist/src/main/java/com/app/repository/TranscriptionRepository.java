package com.app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.entity.RecordingSessionEntity;
import com.app.entity.TranscriptionEntity;

@Repository
public interface TranscriptionRepository extends BaseRepository<TranscriptionEntity> {

    List<TranscriptionEntity> findByAudioSession(RecordingSessionEntity audioSession);

}
