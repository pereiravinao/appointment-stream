package com.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.enums.TranscriptionType;
import com.app.model.Transcription;
import com.app.properties.GroqProperties;
import com.app.strategy.TranscriptionTypeStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptionGroqServiceImpl implements TranscriptionTypeStrategy {

    private final GroqProperties groqProperties;
    private final WebClient.Builder webClientBuilder;

    @Override
    public boolean supports(TranscriptionType type) {
        return type.equals(TranscriptionType.FULL);
    }

    @Override
    public List<Transcription> transcribe(Transcription transcription) {
        return new ArrayList<Transcription>();
    }

}
