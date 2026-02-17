package com.sentinel.backend.domain.ports.out;

import com.sentinel.backend.domain.model.InfrastructureAlert;

public interface AlertRepositoryPort {
    InfrastructureAlert save(InfrastructureAlert alert);
}
