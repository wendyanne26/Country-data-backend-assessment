package com.chinwendu.citydataexplorer_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class CityDataExplorerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityDataExplorerBackendApplication.class, args);
    }

    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }

}
