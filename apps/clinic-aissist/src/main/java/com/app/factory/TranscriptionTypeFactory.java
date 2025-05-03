package com.app.factory;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.app.enums.TranscriptionType;
import com.app.strategy.TranscriptionTypeStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TranscriptionTypeFactory {

    private final List<TranscriptionTypeStrategy> strategies;

    public Optional<TranscriptionTypeStrategy> getStrategyFor(TranscriptionType type) {
        return strategies.stream().filter(s -> s.supports(type)).findFirst();
    }

}
