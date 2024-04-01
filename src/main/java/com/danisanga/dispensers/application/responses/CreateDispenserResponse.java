package com.danisanga.dispensers.application.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "flow_volume"
})
public class CreateDispenserResponse {

    public CreateDispenserResponse() {
    }

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("flow_volume")
    private Double flowVolume;
}
