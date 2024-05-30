package com.petpal.petpalservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.Mapping;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }
}