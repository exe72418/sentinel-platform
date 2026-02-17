package com.sentinel.backend.domain.ports.out;

import com.sentinel.backend.domain.model.InfrastructureAlert;
import java.util.List;

public interface AlertRepositoryPort {
    InfrastructureAlert save(InfrastructureAlert alert);
    List<InfrastructureAlert> findAll();
}
