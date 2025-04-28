package com.app.services.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.model.AudioChunk;
import com.app.model.AudioSession;
import com.app.model.SpeechSegment;
import com.app.properties.DeepgramProperties;
import com.app.response.DeepgramResponse;
import com.app.services.interfaces.TranscriptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptionDeepgramServiceImpl implements TranscriptionService {

    private final DeepgramProperties deepgramProperties;
    private final WebClient.Builder webClientBuilder;

    @Override
    public List<AudioChunk> transcribe(AudioSession audioSession, String audioUrl, int startingSequenceNumber) {
        DeepgramResponse deepgramResponse = requestDeepgram(audioUrl);
        List<AudioChunk> audioChunks = deepgramResponse.toAudioChunks(audioSession, audioUrl, startingSequenceNumber);
        return audioChunks;
    }

    private DeepgramResponse requestDeepgram(String audioUrl) {
        WebClient webClient = webClientBuilder.build();

        Map<String, Object> requestBody = Map.of(
                "url", audioUrl);

        var url = deepgramProperties.getApiUrl() + "?" + buildParams(audioUrl);

        try {
            return webClient.post()
                    .uri(url)
                    .header("Authorization", "Token " + deepgramProperties.getApiKey())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(DeepgramResponse.class)
                    .blockOptional()
                    .orElseThrow(() -> new RuntimeException("Resposta vazia da Deepgram."));
        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar Deepgram API: " + e.getMessage(), e);
        }
    }

    private String buildParams(String audioUrl) {
        Map<String, Object> params = Map.of(
                "diarize", true,
                "dictation", true,
                "paragraphs", true,
                "language", "pt-BR");

        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}
