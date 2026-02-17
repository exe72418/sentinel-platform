package com.sentinel.backend.infrastructure.persistence.repository;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.out.AlertRepositoryPort;
import com.sentinel.backend.infrastructure.persistence.entity.AlertEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgresAlertRepositoryAdapter implements AlertRepositoryPort {

    private final SpringDataAlertRepository springDataAlertRepository;

    @Override
    public InfrastructureAlert save(InfrastructureAlert alert) {
        AlertEntity entity = mapToEntity(alert);
        AlertEntity savedEntity = springDataAlertRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    private AlertEntity mapToEntity(InfrastructureAlert alert) {
        return AlertEntity.builder()
                .id(alert.id())
                .podName(alert.podName())
                .errorMessage(alert.errorMessage())
                .severity(alert.severity())
                .status(alert.status())
                .createdAt(alert.createdAt())
                .aiAnalysis(alert.aiAnalysis())
                .suggestedAction(alert.suggestedAction())
                .build();
    }

    private InfrastructureAlert mapToDomain(AlertEntity entity) {
        return new InfrastructureAlert(
                entity.getId(),
                entity.getPodName(),
                entity.getErrorMessage(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getAiAnalysis(),
                entity.getSuggestedAction()
        );
    }
}
