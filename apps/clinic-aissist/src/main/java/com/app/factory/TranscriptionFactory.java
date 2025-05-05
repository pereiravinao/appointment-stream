package com.app.factory;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.app.enums.TranscriptionType;
import com.app.strategy.TranscriptionStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TranscriptionFactory {

    private final List<TranscriptionStrategy> strategies;

    public Optional<TranscriptionStrategy> getStrategyFor(TranscriptionType type) {
        return strategies.stream().filter(s -> s.supports(type)).findFirst();
    }

}
