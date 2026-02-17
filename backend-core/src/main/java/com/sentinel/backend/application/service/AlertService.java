package com.sentinel.backend.application.service;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.in.CreateAlertUseCase;
import com.sentinel.backend.domain.ports.out.AlertRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertService implements CreateAlertUseCase {

    private final AlertRepositoryPort alertRepositoryPort;

    @Override
    public InfrastructureAlert createAlert(InfrastructureAlert alert) {
        // Enforce createdAt if missing
        if (alert.createdAt() == null) {
            alert = new InfrastructureAlert(
                alert.id(),
                alert.podName(),
                alert.errorMessage(),
                alert.severity(),
                alert.status(),
                LocalDateTime.now()
            );
        }
        return alertRepositoryPort.save(alert);
    }
}
