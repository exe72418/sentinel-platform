package com.sentinel.backend.infrastructure.web.controller;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.in.CreateAlertUseCase;
import com.sentinel.backend.infrastructure.web.dto.AlertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final CreateAlertUseCase createAlertUseCase;

    @PostMapping
    public ResponseEntity<InfrastructureAlert> createAlert(@RequestBody AlertRequest request) {
        InfrastructureAlert alert = new InfrastructureAlert(
            null,
            request.podName(),
            request.errorMessage(),
            request.severity(),
            request.status(),
            null // Will be set by service if missing
        );

        InfrastructureAlert savedAlert = createAlertUseCase.createAlert(alert);
        return ResponseEntity.ok(savedAlert);
    }
}
