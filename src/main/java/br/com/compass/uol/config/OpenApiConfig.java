package br.com.compass.uol.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de banco Digital - Teste Compass UOL ")
                        .description("REST API para transferencia e consulta de valores")
                        .version("v1.0.0"));
    }
}
