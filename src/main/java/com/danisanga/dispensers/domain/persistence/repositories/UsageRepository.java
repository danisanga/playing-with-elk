package com.danisanga.dispensers.domain.persistence.repositories;

import com.danisanga.dispensers.domain.entities.Usage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsageRepository extends CrudRepository<Usage, UUID> {
}
