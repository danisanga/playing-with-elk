package com.danisanga.dispensers.infrastructure.services.impl;

import com.danisanga.dispensers.domain.entities.Dispenser;
import com.danisanga.dispensers.domain.entities.Usage;
import com.danisanga.dispensers.domain.persistence.repositories.DispenserRepository;
import com.danisanga.dispensers.domain.persistence.repositories.UsageRepository;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserNotFoundException;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserWebServiceException;
import com.danisanga.dispensers.domain.services.DispenserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultDispenserService implements DispenserService {

    private final DispenserRepository dispenserRepository;
    private final UsageRepository usageRepository;

    public DefaultDispenserService(final DispenserRepository dispenserRepository,
                                   final UsageRepository usageRepository) {
        this.dispenserRepository = dispenserRepository;
        this.usageRepository = usageRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dispenser getDispenserIfExists(final UUID dispenserId) {
        return dispenserRepository.findById(dispenserId)
                .orElseThrow(() -> new DispenserNotFoundException(
                        String.format("There is not a Dispenser associated with this ID, %s", dispenserId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDispenserStatus(final UUID dispenserId, final String dispenserStatus) {
        final Dispenser dispenser = getDispenserIfExists(dispenserId);
        if ("open".equals(dispenserStatus)) {
            openDispenser(dispenser);
        }
        if ("close".equals(dispenserStatus)) {
            closeDispenser(dispenser);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID createDispenser(final Double flowVolume) {
        final Dispenser newDispenser =
                dispenserRepository.save(
                        Dispenser.builder()
                                .flowVolume(flowVolume)
                                .createdAt(new Date())
                                .build());
        return newDispenser.getId();
    }

    private boolean isDispenserWorking(final Dispenser dispenser) {
        final List<Usage> allUsages = dispenser.getUsages();
        return allUsages
                .stream()
                .anyMatch(usage -> usage.getClosedAt() == null);
    }

    private void openDispenser(final Dispenser dispenser) {
        final boolean isDispenserWorking = isDispenserWorking(dispenser);
        if(isDispenserWorking) {
            throw new DispenserWebServiceException("This Dispenser is working right now! Close the tap.");
        }
        usageRepository.save(Usage.builder()
                .dispenser(dispenser)
                .openedAt(new Date())
                .build());
    }

    private void closeDispenser(final Dispenser dispenser) {
        final boolean isDispenserWorking = isDispenserWorking(dispenser);
        if (!isDispenserWorking) {
            throw new DispenserWebServiceException("This dispenser is not open, so you can't close it!");
        }

        final List<Usage> usages = dispenser.getUsages();
        final Optional<Usage> currentUsage = usages
                .stream()
                .filter(usage -> usage.getClosedAt() == null)
                .findFirst();
        if (currentUsage.isPresent()) {
            final Usage usage = currentUsage.get();
            usageRepository.save(Usage.builder()
                    .id(usage.getId())
                    .dispenser(dispenser)
                    .openedAt(usage.getOpenedAt())
                    .closedAt(new Date())
                    .build());
        }
    }
}
