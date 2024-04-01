package com.danisanga.dispensers.application.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationResponse {

    private String errorMessage;
    private Map<String, String> errors;

}
