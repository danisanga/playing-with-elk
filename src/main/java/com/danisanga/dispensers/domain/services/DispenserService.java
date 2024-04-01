package com.danisanga.dispensers.domain.services;

import com.danisanga.dispensers.domain.entities.Dispenser;

import java.util.UUID;

public interface DispenserService {
    /**
     * Checks if a dispenser exists.
     *
     * @param dispenserId dispenser UUID
     */
    Dispenser getDispenserIfExists(final UUID dispenserId);

    /**
     * Change dispenser status.
     *
     * @param dispenserId       dispenser UUID
     * @param dispenserStatus   dispenser status (open or close)
     */
    void changeDispenserStatus(final UUID dispenserId, final String dispenserStatus);

    /**
     * Creates a dispenser.
     *
     * @param flowVolume    dispenser flow volume
     * @return  created dispenser UUID.
     */
    UUID createDispenser(final Double flowVolume);
}
