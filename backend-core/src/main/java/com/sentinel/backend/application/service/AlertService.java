package com.sentinel.backend.application.service;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.in.CreateAlertUseCase;
import com.sentinel.backend.domain.ports.out.AlertRepositoryPort;
import com.sentinel.backend.domain.ports.out.AnalyzeAlertPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService implements CreateAlertUseCase {

    private final AlertRepositoryPort alertRepositoryPort;
    private final AnalyzeAlertPort analyzeAlertPort;

    @Override
    public InfrastructureAlert createAlert(InfrastructureAlert alert) {
        log.info("Creating initial alert: {}", alert.podName());

        // 1. Prepare initial alert (add createdAt)
        InfrastructureAlert alertToSave = new InfrastructureAlert(
            null,
            alert.podName(),
            alert.errorMessage(),
            alert.severity(),
            alert.status(),
            alert.createdAt() != null ? alert.createdAt() : LocalDateTime.now(),
            null,
            null
        );

        // 2. Save initial alert to get ID
        InfrastructureAlert savedAlert = alertRepositoryPort.save(alertToSave);
        log.info("Saved alert with ID: {}", savedAlert.id());

        // 3. Call AI Brain for analysis
        InfrastructureAlert analyzedAlert = analyzeAlertPort.analyze(savedAlert);

        // 4. Update alert with analysis (if any)
        if (analyzedAlert.aiAnalysis() != null) {
            log.info("Updating alert {} with AI analysis", savedAlert.id());
            return alertRepositoryPort.save(analyzedAlert);
        }

        return savedAlert;
    }
}
