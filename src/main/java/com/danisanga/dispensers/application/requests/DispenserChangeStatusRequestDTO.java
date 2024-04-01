package com.danisanga.dispensers.application.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "opened_at"
})
public class DispenserChangeStatusRequestDTO {
    public DispenserChangeStatusRequestDTO() {
    }

    @JsonProperty("status")
    private String status;
    @JsonProperty("opened_at")
    private Date openedAt;
}
