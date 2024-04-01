package com.danisanga.dispensers.infrastructure.services.impl;

import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.domain.entities.Dispenser;
import com.danisanga.dispensers.domain.entities.Usage;
import com.danisanga.dispensers.domain.services.DispenserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUsageServiceTest {

    private static final UUID DISPENSER_ID = UUID.randomUUID();

    @InjectMocks
    private DefaultUsageService testObj;

    @Mock
    private DispenserService dispenserServiceMock;

    private final Dispenser dispenserStub = new Dispenser();
    private final Usage usageStub = new Usage();
    private final Usage usageStub2 = new Usage();

    @BeforeEach
    void setUp() {
        testObj = Mockito.spy(new DefaultUsageService(12.25D, dispenserServiceMock));
    }

    @Test
    void getDispenserStats_shouldReturnStats() {
        prepareTestUsages();
        when(dispenserServiceMock.getDispenserIfExists(DISPENSER_ID)).thenReturn(dispenserStub);

        final DispenserStatsData result = testObj.getCalculatedDispenserStats(DISPENSER_ID);

        assertThat(result).isNotNull();
        final Double expectedAmount = 122.5D;
        assertThat(result.getAmount()).isEqualTo(expectedAmount);
    }

    private void prepareTestUsages() {
        final Date openedAt = new Date();
        usageStub.setOpenedAt(openedAt);
        final Date closedAt = new Date();
        closedAt.setTime(closedAt.getTime() + 5000);
        usageStub.setClosedAt(closedAt);

        final Date openedAt2 = new Date();
        openedAt2.setTime(openedAt2.getTime() - 5000);
        usageStub2.setOpenedAt(openedAt2);

        dispenserStub.setUsages(List.of(usageStub, usageStub2));
    }
}