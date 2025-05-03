package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.enums.WebSocketMessageType;
import com.app.response.AudioStreamSessionStatusResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/audio-stream")
@RequiredArgsConstructor
public class AudioStreamAdminController {

    @GetMapping("/sessions")
    public ResponseEntity<List<AudioStreamSessionStatusResponse>> listActiveSessions(
            @RequestParam WebSocketMessageType type) {
        return ResponseEntity.noContent().build();
    }
}