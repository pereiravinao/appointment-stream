package com.app.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.enums.TranscriptionType;
import com.app.model.Transcription;
import com.app.properties.DeepgramProperties;
import com.app.response.DeepgramResponse;
import com.app.strategy.TranscriptionStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptionDeepgramServiceImpl implements TranscriptionStrategy {

    private final DeepgramProperties deepgramProperties;
    private final WebClient.Builder webClientBuilder;

    @Override
    public boolean supports(TranscriptionType type) {
        return type.equals(TranscriptionType.FULL);
    }

    @Override
    public List<Transcription> transcribe(Transcription transcription) {
        DeepgramResponse deepgramResponse = requestDeepgram(transcription.getAudioUrl());
        List<Transcription> transcriptions = new ArrayList<>();
        deepgramResponse.getResults().getChannels().forEach(channel -> {
            channel.getAlternatives().forEach(alternative -> {
                alternative.getParagraphs().getParagraphs().forEach(paragraph -> {
                    var speaker = paragraph.getSpeaker().toString();
                    var text = paragraph.getSentences().stream().map(sentence -> sentence.getText())
                            .collect(Collectors.joining(" "));
                    var transcriptionCreated = Transcription.builder()
                            .speaker(speaker)
                            .text(text)
                            .type(TranscriptionType.FULL)
                            .audioUrl(transcription.getAudioUrl())
                            .recordingSession(transcription.getRecordingSession())
                            .build();
                    transcriptions.add(transcriptionCreated);
                });
            });
        });
        return transcriptions;
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
                "language", "pt-BR",
                "model", "whisper");
                
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}
