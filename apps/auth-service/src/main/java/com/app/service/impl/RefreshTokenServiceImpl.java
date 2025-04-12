package com.app.service.impl;

import com.app.entity.RefreshTokenEntity;
import com.app.exception.auth.RefreshTokenException;
import com.app.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {

    private final RefreshTokenRepository refreshTokenRepository;

    public String generateRefreshToken(String email) {
        var refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        return refreshTokenRepository.save(refreshToken).getToken();
    }

    public boolean validateRefreshToken(String token) {
        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenException::invalid);
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw RefreshTokenException.expired();
        }
        return true;
    }

    public void deleteRefreshToken(String token) {
        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenException::invalid);
        refreshTokenRepository.delete(refreshToken);
    }
}