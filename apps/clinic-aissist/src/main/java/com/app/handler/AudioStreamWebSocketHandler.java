package com.app.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.app.model.AudioChunk;
import com.app.model.AudioSession;
import com.app.response.AudioStreamSessionStatusResponse;
import com.app.services.interfaces.AudioSessionService;
import com.app.services.interfaces.StorageService;
import com.app.services.interfaces.TranscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AudioStreamWebSocketHandler extends BinaryWebSocketHandler {

    private final StorageService storageService;
    private final TranscriptionService transcriptionService;
    private final AudioSessionService audioSessionService;

    // Controle de sessões ativas
    private final ConcurrentHashMap<String, AudioSession> activeSessions = new ConcurrentHashMap<>();
    // Controle de sequência por sessão
    private final ConcurrentHashMap<String, AtomicInteger> sequenceCounters = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Nova conexão WebSocket estabelecida: {}", session.getId());

        AudioSession newSession = audioSessionService.startNewSession();
        activeSessions.put(session.getId(), newSession);
        sequenceCounters.put(session.getId(), new AtomicInteger(1));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            byte[] audioBytes = message.getPayload().array();
            String filename = "chunk-" + session.getId() + "-" + System.currentTimeMillis() + ".webm";

            log.info("Recebendo áudio de sessão {}: {} bytes", session.getId(), audioBytes.length);

            // Upload do fragmento
            String audioUrl = storageService.uploadAudio(audioBytes, filename);
            log.info("Áudio enviado para Storage: {}", audioUrl);

            // Transcrição do fragmento
            AudioSession audioSession = activeSessions.get(session.getId());
            int startingSequenceNumber = sequenceCounters.get(session.getId()) == null
                    ? 1
                    : sequenceCounters.get(session.getId()).getAndAdd(1);

            List<AudioChunk> chunks = transcriptionService.transcribe(audioSession, audioUrl, startingSequenceNumber);
            log.info("Chunks transcritos: {}", chunks.size());

            // Salvar os chunks
            audioSessionService.saveChunks(chunks);
            log.info("Chunks salvos no banco para sessão {}", session.getId());

        } catch (Exception e) {
            log.error("Erro ao processar fragmento de áudio na sessão {}", session.getId(), e);
            closeSessionSafely(session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Conexão WebSocket encerrada: {}", session.getId());
        activeSessions.remove(session.getId());
        sequenceCounters.remove(session.getId());
    }

    public List<AudioStreamSessionStatusResponse> listSessions() {
        return activeSessions.entrySet().stream()
                .map(entry -> {
                    String sessionId = entry.getKey();
                    AudioSession audioSession = entry.getValue();
                    int nextSequenceNumber = sequenceCounters.getOrDefault(sessionId, new AtomicInteger(1)).get();
                    return new AudioStreamSessionStatusResponse(
                            sessionId,
                            audioSession.getId(),
                            nextSequenceNumber);
                })
                .toList();
    }

    private void closeSessionSafely(WebSocketSession session) {
        try {
            if (session.isOpen()) {
                session.close(CloseStatus.SERVER_ERROR);
            }
        } catch (IOException ex) {
            log.error("Erro ao fechar sessão WebSocket após falha", ex);
        }
    }
}
