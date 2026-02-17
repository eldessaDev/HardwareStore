package com.hardware.store.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hardware Store API 🛠️",
                version = "1.0",
                description = "Documentación oficial para la gestión de Tienda de Hardware"
        )
)
public class OpenApiConfig {
    // Solo con las anotaciones de arriba es suficiente
}