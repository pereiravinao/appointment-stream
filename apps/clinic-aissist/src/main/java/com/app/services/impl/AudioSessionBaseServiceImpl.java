package com.app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.app.entity.AudioChunkEntity;
import com.app.entity.AudioSessionEntity;
import com.app.model.AudioChunk;
import com.app.model.AudioSession;
import com.app.model.SpeechSegment;
import com.app.repository.AudioChunkRepository;
import com.app.repository.AudioSessionRepository;
import com.app.services.interfaces.AudioSessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AudioSessionBaseServiceImpl implements AudioSessionService {

    private final AudioSessionRepository sessionRepository;
    private final AudioChunkRepository chunkRepository;

    @Transactional
    public void saveChunks(List<AudioChunk> chunks) {
        if (chunks == null || chunks.isEmpty()) {
            throw new IllegalArgumentException("Lista de AudioChunks não pode ser vazia.");
        }
        chunkRepository.saveAll(chunks.stream().map(AudioChunkEntity::new).toList());
    }

    @Transactional
    @Override
    public AudioSession startNewSession() {
        AudioSession session = new AudioSession();
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(new AudioSessionEntity(session)).toModel();
    }

    @Transactional
    public AudioSession createSession(String audioUrl, List<SpeechSegment> segments) {
        AudioSession session = new AudioSession();
        session.setFinalAudioUrl(audioUrl);
        session.setCreatedAt(LocalDateTime.now());

        List<AudioChunk> chunks = IntStream.range(0, segments.size())
                .mapToObj(index -> {
                    AudioChunk chunk = new AudioChunk();
                    chunk.setAudioSession(session);
                    chunk.setSequenceNumber(index);
                    chunk.setReceivedAt(LocalDateTime.now());
                    return chunk;
                })
                .toList();

        session.setAudioChunks(chunks);

        return sessionRepository.save(new AudioSessionEntity(session)).toModel();
    }

    @Override
    public void saveAudioChunk(String sessionId, byte[] audioData) {
        return;
    }

    @Override
    public void closeSession(String sessionId) {
        return;
    }
}
