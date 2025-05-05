package com.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.entity.TranscriptionEntity;
import com.app.enums.TranscriptionType;
import com.app.factory.TranscriptionFactory;
import com.app.model.Transcription;
import com.app.repository.TranscriptionRepository;
import com.app.services.interfaces.RecordingSessionService;
import com.app.services.interfaces.StorageEventService;
import com.app.services.interfaces.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageEventBaseServiceImpl implements StorageEventService {

    private final TranscriptionFactory transcriptionFactory;
    private final TranscriptionRepository transcriptionRepository;
    private final RecordingSessionService recordingSessionService;
    private final StorageService storageService;

    @Override
    public void processChunkAudio(String sessionId, String storageKey) {
        var recordingSession = recordingSessionService.findBySessionId(sessionId);
        var transcription = new Transcription(recordingSession, TranscriptionType.CHUNK, storageKey);
        processAudio(transcription);
    }

    @Override
    public void processFullAudio(String sessionId) {
        var recordingSession = recordingSessionService.findBySessionId(sessionId);
        var objectKey = concatAudioChunks(sessionId);
        var audioUrl = storageService.getSignedUrl(objectKey);

        var transcription = new Transcription(recordingSession, TranscriptionType.FULL, objectKey);
        transcription.setAudioUrl(audioUrl);
        processAudio(transcription);
    }

    private void processAudio(Transcription transcription) {

        transcriptionFactory.getStrategyFor(transcription.getType())
                .ifPresent(strategy -> {
                    var transcripts = strategy.transcribe(transcription);
                    saveTranscriptions(transcripts);
                });
    }

    private void saveTranscriptions(List<Transcription> transcriptions) {
        transcriptionRepository.saveAll(transcriptions
                .stream()
                .map(t -> new TranscriptionEntity(t)).toList());
    }

    private String concatAudioChunks(String sessionId) {
        return storageService.concatAudioChunks(sessionId);
    }
}
