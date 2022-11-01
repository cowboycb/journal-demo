package com.clinked.journal;

import com.clinked.journal.common.DomainComponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ComponentScan(
    basePackages = "com.clinked.journal",
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
    }
)
public class InfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfraApplication.class, args);
    }

}
