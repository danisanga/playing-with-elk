package com.danisanga.dispensers.application.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "usages"
})
public class DispenserStatsResponse {

    public DispenserStatsResponse() {
    }

    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("usages")
    private List<DispenserUsageResponse> usages;

}
