package com.danisanga.dispensers.application.validators;

import com.danisanga.dispensers.application.requests.DispenserCreationRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class CreateDispenserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DispenserCreationRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final DispenserCreationRequestDTO requestDTO = (DispenserCreationRequestDTO) target;

        if (Objects.isNull(requestDTO.getFlowVolume()) || requestDTO.getFlowVolume().isNaN() || requestDTO.getFlowVolume() <= 0D) {
            errors.rejectValue("flowVolume", "Invalid value for this attribute");
        }
    }
}
