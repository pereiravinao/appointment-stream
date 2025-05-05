package com.app.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.enums.TranscriptionType;
import com.app.model.Transcription;
import com.app.properties.GroqProperties;
import com.app.response.GroqResponse;
import com.app.services.interfaces.StorageService;
import com.app.strategy.TranscriptionStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranscriptionGroqServiceImpl implements TranscriptionStrategy {

    private final GroqProperties groqProperties;
    private final WebClient.Builder webClientBuilder;
    private final StorageService storageService;

    @Override
    public boolean supports(TranscriptionType type) {
        return type.equals(TranscriptionType.CHUNK);
    }

    @Override
    public List<Transcription> transcribe(Transcription transcription) {
        WebClient webClient = webClientBuilder.build();

        var audioUrl = storageService.getSignedUrl(transcription.getKey());

        try {
            var response = webClient.post()
                    .uri(groqProperties.getApiUrl())
                    .header("Authorization", "bearer " + groqProperties.getApiKey())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("url", audioUrl)
                            .with("model", "whisper-large-v3")
                            .with("response_format", "json")
                            .with("temperature", "0")
                            .with("language", "pt"))
                    .retrieve()
                    .bodyToMono(GroqResponse.class)
                    .blockOptional()
                    .orElseThrow(() -> new RuntimeException("Resposta vazia da Groq."));
            log.info("Resposta da Groq: {}", response);
            transcription.setText(response.getText());
            return List.of(transcription);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao chamar Groq API: " + e.getMessage(), e);
        }
    }

}
