package com.danisanga.application.services.impl;

import com.danisanga.dispensers.domain.entities.Dispenser;
import com.danisanga.dispensers.domain.entities.Usage;
import com.danisanga.dispensers.domain.persistence.repositories.UsageRepository;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserNotFoundException;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserWebServiceException;
import com.danisanga.dispensers.infrastructure.services.impl.DefaultDispenserService;
import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.domain.data.DispenserUsageData;
import com.danisanga.dispensers.domain.persistence.repositories.DispenserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDispenserServiceTest {

    private static final UUID DISPENSER_ID = UUID.randomUUID();
    private static final String STATUS_OPEN = "open";
    private static final String STATUS_CLOSE = "close";
    private static final Double FLOW_VOLUME = 0.005D;

    @InjectMocks
    @Spy
    private DefaultDispenserService testObj;

    @Mock
    private DispenserRepository dispenserRepositoryMock;
    @Mock
    private UsageRepository usageRepositoryMock;

    private final DispenserUsageData dispenserUsageDataStub = new DispenserUsageData();
    private final DispenserStatsData dispenserStatsDataStub = new DispenserStatsData();
    private final Dispenser dispenserStub = new Dispenser();
    private final Dispenser newDispenserStub = new Dispenser();
    private final Usage usageStub = new Usage();

    @Test
    void getDispenserIfExists_shouldThrowException_whenDispenserNotExists() {
        doThrow(DispenserNotFoundException.class).when(dispenserRepositoryMock).findById(DISPENSER_ID);

        assertThrows(DispenserNotFoundException.class, () -> testObj.getDispenserIfExists(DISPENSER_ID));
    }

    @Test
    void getDispenserIfExists_shouldReturnDispenser_whenExists() {
        when(dispenserRepositoryMock.findById(DISPENSER_ID)).thenReturn(Optional.of(dispenserStub));

        final Dispenser result = testObj.getDispenserIfExists(DISPENSER_ID);

        assertThat(result).isNotNull();
    }

    @Test
    void createDispenser_shouldCreateNewDispenser() {
        newDispenserStub.setId(DISPENSER_ID);
        when(dispenserRepositoryMock.save(any(Dispenser.class))).thenReturn(newDispenserStub);

        final UUID result = testObj.createDispenser(FLOW_VOLUME);

        assertThat(result).isEqualTo(DISPENSER_ID);
    }

    @Test
    void changeDispenserStatus_shouldChangeStatusToOpen_whenDispenserExistsAndIsNotWorking() {
        usageStub.setClosedAt(new Date());
        dispenserStub.setUsages(List.of(usageStub));
        doReturn(dispenserStub).when(testObj).getDispenserIfExists(DISPENSER_ID);

        testObj.changeDispenserStatus(DISPENSER_ID, STATUS_OPEN);

        verify(usageRepositoryMock).save(any(Usage.class));
    }

    @Test
    void changeDispenserStatus_shouldChangeStatusToClose_whenDispenserExistsAndIsWorking() {
        dispenserStub.setUsages(List.of(usageStub));
        doReturn(dispenserStub).when(testObj).getDispenserIfExists(DISPENSER_ID);

        testObj.changeDispenserStatus(DISPENSER_ID, STATUS_CLOSE);

        verify(usageRepositoryMock).save(any(Usage.class));
    }

    @Test
    void changeDispenserStatus_shouldThrowException_whenUserOpensDispenserAndIsWorking() {
        dispenserStub.setUsages(List.of(usageStub));
        doReturn(dispenserStub).when(testObj).getDispenserIfExists(DISPENSER_ID);

        assertThrows(DispenserWebServiceException.class, () -> testObj.changeDispenserStatus(DISPENSER_ID, STATUS_OPEN));
        verifyNoInteractions(usageRepositoryMock);
    }

    @Test
    void changeDispenserStatus_shouldThrowException_whenUserClosesDispenserAndIsNotWorking() {
        usageStub.setClosedAt(new Date());
        dispenserStub.setUsages(List.of(usageStub));
        doReturn(dispenserStub).when(testObj).getDispenserIfExists(DISPENSER_ID);

        assertThrows(DispenserWebServiceException.class, () -> testObj.changeDispenserStatus(DISPENSER_ID, STATUS_CLOSE));
        verifyNoInteractions(usageRepositoryMock);
    }

}