package com.danisanga.dispensers.application.controllers;

import com.danisanga.dispensers.application.requests.DispenserChangeStatusRequestDTO;
import com.danisanga.dispensers.application.requests.DispenserCreationRequestDTO;
import com.danisanga.dispensers.application.responses.DispenserStatsResponse;
import com.danisanga.dispensers.application.responses.ValidationResponse;
import com.danisanga.dispensers.application.validators.ChangeDispenserStatusValidator;
import com.danisanga.dispensers.application.validators.CreateDispenserValidator;
import com.danisanga.dispensers.domain.data.DispenserStatsData;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserNotFoundException;
import com.danisanga.dispensers.infrastructure.exceptions.DispenserWebServiceException;
import com.danisanga.dispensers.domain.services.DispenserService;
import com.danisanga.dispensers.domain.services.UsageService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/dispensers")
public class DispensersController {

    @Resource
    private DispenserService dispenserService;
    @Resource
    private UsageService usageService;
    @Resource
    private CreateDispenserValidator createDispenserValidator;
    @Resource
    private ChangeDispenserStatusValidator changeDispenserStatusValidator;
    @Resource
    private ModelMapper modelMapper;

    @GetMapping(value = "/{dispenserId}/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DispenserStatsResponse getDispenserStats(@PathVariable final UUID dispenserId) {
        final DispenserStatsData dispenserStats = usageService.getDispenserStats(dispenserId);
        return modelMapper.map(dispenserStats, DispenserStatsResponse.class);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ValidationResponse createDispenser(@RequestBody final DispenserCreationRequestDTO requestBody,
                                              final BindingResult bindingResult) {
        createDispenserValidator.validate(requestBody, bindingResult);
        if (bindingResult.hasErrors()) {
            return getErrorList(bindingResult);
        }

        dispenserService.createDispenser(requestBody.getFlowVolume());
        return null;
    }

    @PutMapping(value = "/{dispenserId}/status/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ValidationResponse changeDispenserStatus(@PathVariable final UUID dispenserId,
                                                    @RequestBody final DispenserChangeStatusRequestDTO requestBody,
                                                    final BindingResult bindingResult) {
        changeDispenserStatusValidator.validate(requestBody, bindingResult);
        if (bindingResult.hasErrors()) {
            return getErrorList(bindingResult);
        }

        dispenserService.changeDispenserStatus(dispenserId, requestBody.getStatus().toLowerCase());
        return null;
    }

    @ExceptionHandler(DispenserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationResponse handleDispenserNotFoundException(final DispenserNotFoundException exception) {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setErrorMessage(exception.getMessage());
        return validationResponse;
    }

    @ExceptionHandler(DispenserWebServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationResponse handleDispenserWebServiceException(final DispenserWebServiceException exception) {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setErrorMessage(exception.getMessage());
        return validationResponse;
    }

    private ValidationResponse getErrorList(final BindingResult bindingResult) {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setErrorMessage("There were an error with this Dispenser!");
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for(final FieldError error : bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getCode());
            }
            validationResponse.setErrors(errors);
        }
        return validationResponse;
    }
}
