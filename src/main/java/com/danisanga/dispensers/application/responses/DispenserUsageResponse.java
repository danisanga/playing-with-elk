package com.danisanga.dispensers.application.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DispenserUsageResponse {

    public DispenserUsageResponse() {
    }

    @JsonProperty("opened_at")
    private Date openedAt;
    @JsonProperty("closed_at")
    private Date closedAt;
    @JsonProperty("total_spent")
    private Double totalSpent;

}
