package com.danisanga.dispensers.domain.persistence.repositories;

import com.danisanga.dispensers.domain.entities.Dispenser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DispenserRepository extends CrudRepository<Dispenser, UUID> {

}
