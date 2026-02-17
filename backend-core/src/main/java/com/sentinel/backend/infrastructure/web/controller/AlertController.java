package com.sentinel.backend.infrastructure.web.controller;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.in.CreateAlertUseCase;
import com.sentinel.backend.domain.ports.out.AlertRepositoryPort;
import com.sentinel.backend.infrastructure.web.dto.AlertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all origins for dev
public class AlertController {

    private final CreateAlertUseCase createAlertUseCase;
    private final AlertRepositoryPort alertRepositoryPort;

    @PostMapping
    public ResponseEntity<InfrastructureAlert> createAlert(@RequestBody AlertRequest request) {
        InfrastructureAlert alert = new InfrastructureAlert(
            null,
            request.podName(),
            request.errorMessage(),
            request.severity(),
            request.status(),
            null,
            null,
            null
        );

        InfrastructureAlert savedAlert = createAlertUseCase.createAlert(alert);
        return ResponseEntity.ok(savedAlert);
    }

    @GetMapping
    public ResponseEntity<List<InfrastructureAlert>> getAllAlerts() {
        return ResponseEntity.ok(alertRepositoryPort.findAll());
    }
}
