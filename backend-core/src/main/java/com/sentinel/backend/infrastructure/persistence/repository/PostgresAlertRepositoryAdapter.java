package com.sentinel.backend.infrastructure.persistence.repository;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import com.sentinel.backend.domain.ports.out.AlertRepositoryPort;
import com.sentinel.backend.infrastructure.persistence.entity.AlertEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<InfrastructureAlert> findAll() {
        return springDataAlertRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
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
