package com.danisanga.dispensers.infrastructure.configuration;

import com.danisanga.dispensers.application.responses.DispenserStatsResponse;
import com.danisanga.dispensers.domain.data.DispenserStatsData;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class BaseConfiguration {

    @Resource
    private AbstractConverter<DispenserStatsData, DispenserStatsResponse> dispenserStatsConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(dispenserStatsConverter);
        return modelMapper;
    }
}
