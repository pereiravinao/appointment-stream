package com.app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.entity.AudioSessionEntity;
import com.app.entity.TranscriptionEntity;

@Repository
public interface TranscriptionRepository extends BaseRepository<TranscriptionEntity> {

    List<TranscriptionEntity> findByAudioSession(AudioSessionEntity audioSession);

}
