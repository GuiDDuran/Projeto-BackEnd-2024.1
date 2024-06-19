package com.ibmec.backend.malldelivery;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.awt.SystemColor.info;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI apiDocConfiguration(){
        return new OpenAPI()
                .info(new Info()
                        .title("Mall Delivery API")
                        .description("API para o projeto de Mall Delivery")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Guilherme")
                                .email("guilherme.d.gea@gmail.com")
                        )
                );
    }
}
