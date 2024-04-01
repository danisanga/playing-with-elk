package com.danisanga.dispensers.application.validators;

import com.danisanga.dispensers.application.requests.DispenserChangeStatusRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangeDispenserStatusValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DispenserChangeStatusRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final DispenserChangeStatusRequestDTO requestDTO = (DispenserChangeStatusRequestDTO) target;

        if (StringUtils.isBlank(requestDTO.getStatus()) || statusContainsInvalidValues(requestDTO.getStatus())) {
            errors.rejectValue("status", "Invalid value for this attribute");
        }
    }

    private boolean statusContainsInvalidValues(final String status) {
        return !StringUtils.equalsAnyIgnoreCase(status, "open", "close");
    }
}
