package co.edu.unbosque.mensajeria.configuration;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;

/**
 * Clase de configuración para la documentación de la API utilizando Springdoc OpenAPI (Swagger).
 * Define la información general de la API (título, descripción) y configura 
 * el esquema de seguridad global basado en tokens JWT (Bearer Authentication).
 */
@Configuration
public class OpenApiConfig {

	/**
	 * Configura y expone el bean {@link OpenAPI} personalizado para la documentación de los endpoints.
	 * Establece los metadatos de la aplicación y configura los componentes de seguridad 
	 * para permitir la autenticación mediante tokens JWT directamente desde la interfaz de Swagger UI.
	 * 
	 * @return una instancia configurada de {@link OpenAPI} con la información y esquemas de seguridad.
	 */
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Chatly")
                        .description("Aplicativo de envio de mensajes de texto, imagenes o archivos"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("BearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
