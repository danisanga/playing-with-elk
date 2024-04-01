package com.danisanga.dispensers.application.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "flow_volume"
})
public class DispenserCreationRequestDTO {
    public DispenserCreationRequestDTO() {
    }

    @JsonProperty("flow_volume")
    private Double flowVolume;
}
