package com.danisanga.dispensers.infrastructure.services.impl;

import com.danisanga.dispensers.domain.entities.Dispenser;
import com.danisanga.dispensers.domain.entities.Usage;
import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.domain.data.DispenserUsageData;
import com.danisanga.dispensers.domain.services.DispenserService;
import com.danisanga.dispensers.domain.services.UsageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DefaultUsageService implements UsageService {

    private final Double beerPrice;

    private final DispenserService dispenserService;

    public DefaultUsageService(@Value("${beer.price}") final Double beerPrice, final DispenserService dispenserService) {
        this.beerPrice = beerPrice;
        this.dispenserService = dispenserService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispenserStatsData getDispenserStats(final UUID dispenserId) {
        return populateDispenserStats(dispenserId);
    }

    private DispenserStatsData populateDispenserStats(final UUID dispenserId) {
        return getCalculatedDispenserStats(dispenserId);
    }

    protected DispenserStatsData getCalculatedDispenserStats(final UUID dispenserId) {
        DispenserStatsData dispenserStatsData = new DispenserStatsData();
        final List<DispenserUsageData> dispenserUsages = getDispenserUsages(dispenserId);
        dispenserStatsData.setUsages(dispenserUsages);

        final double totalAmount = dispenserUsages
                .stream()
                .map(DispenserUsageData::getTotalSpent)
                .mapToDouble(Double::doubleValue)
                .sum();
        dispenserStatsData.setAmount(totalAmount);
        return dispenserStatsData;
    }

    private List<DispenserUsageData> getDispenserUsages(final UUID dispenserId) {
        final Dispenser dispenser = dispenserService.getDispenserIfExists(dispenserId);
        final List<Usage> usages = dispenser.getUsages();
        return usages
                .stream()
                .map(this::populateDispenserUsageData)
                .toList();
    }

    private DispenserUsageData populateDispenserUsageData(final Usage usage) {
        DispenserUsageData dispenserUsageData = new DispenserUsageData();
        dispenserUsageData.setDispenserId(usage.getId());
        dispenserUsageData.setOpenedAt(usage.getOpenedAt());
        dispenserUsageData.setClosedAt(usage.getClosedAt());
        final Long timeDifference = getTimeDifference(dispenserUsageData);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
        final double amount = seconds * beerPrice;
        dispenserUsageData.setTotalSpent(amount);
        return dispenserUsageData;
    }

    private Long getTimeDifference(final DispenserUsageData dispenserUsage) {
        final Long timeDifference = Optional.ofNullable(dispenserUsage)
                .filter(usage -> Objects.nonNull(usage.getClosedAt()))
                .map(use -> use.getClosedAt().getTime() - use.getOpenedAt().getTime())
                .orElse(0L);

        final Long timeDifferenceForNotClosedTaps = Optional.ofNullable(dispenserUsage)
                .filter(usage -> Objects.isNull(usage.getClosedAt()))
                .map(use -> new Date().getTime() - use.getOpenedAt().getTime())
                .orElse(0L);

        return timeDifference + timeDifferenceForNotClosedTaps;
    }
}
