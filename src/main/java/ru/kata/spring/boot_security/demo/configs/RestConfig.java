package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.Arrays;


@Configuration
@ComponentScan("ru.kata.spring.boot_security.demo")
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }

}
