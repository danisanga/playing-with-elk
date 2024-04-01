package com.danisanga.dispensers.domain.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispenserStatsData {

    private Double amount;
    private List<DispenserUsageData> usages;

}
