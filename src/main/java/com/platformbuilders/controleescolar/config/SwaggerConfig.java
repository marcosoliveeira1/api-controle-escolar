package com.platformbuilders.controleescolar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Controle Escolar API")
                        .description("API do Sistema de Controle Escolar")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Marcos Oliveira")
                                .email("olv.marcos1@gmail.com")));
    }
}