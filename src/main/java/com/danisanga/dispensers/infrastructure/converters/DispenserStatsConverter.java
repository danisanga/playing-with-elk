package com.danisanga.dispensers.infrastructure.converters;

import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.domain.data.DispenserUsageData;
import com.danisanga.dispensers.application.responses.DispenserStatsResponse;
import com.danisanga.dispensers.application.responses.DispenserUsageResponse;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DispenserStatsConverter extends AbstractConverter<DispenserStatsData, DispenserStatsResponse> {
    @Override
    protected DispenserStatsResponse convert(final DispenserStatsData dispenserStatsData) {
        DispenserStatsResponse dispenserStatsResponse = new DispenserStatsResponse();
        dispenserStatsResponse.setAmount(dispenserStatsData.getAmount());

        List<DispenserUsageResponse> dispenserUsagesResponse = new ArrayList<>();
        for (final DispenserUsageData dispenserUsageData : dispenserStatsData.getUsages()) {
            DispenserUsageResponse dispenserUsageResponse = new DispenserUsageResponse();
            dispenserUsageResponse.setOpenedAt(dispenserUsageData.getOpenedAt());
            dispenserUsageResponse.setClosedAt(dispenserUsageData.getClosedAt());
            dispenserUsageResponse.setTotalSpent(dispenserUsageData.getTotalSpent());
            dispenserUsagesResponse.add(dispenserUsageResponse);
        }
        dispenserStatsResponse.setUsages(dispenserUsagesResponse);

        return dispenserStatsResponse;
    }
}
