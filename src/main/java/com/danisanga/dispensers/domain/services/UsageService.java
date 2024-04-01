package com.danisanga.dispensers.domain.services;

import com.danisanga.dispensers.domain.data.DispenserStatsData;

import java.util.UUID;

public interface UsageService {
    /**
     * Get dispenser stats.
     *
     * @param dispenserId   dispenser UUID
     * @return  dispenser stats.
     */
    DispenserStatsData getDispenserStats(final UUID dispenserId);
}
