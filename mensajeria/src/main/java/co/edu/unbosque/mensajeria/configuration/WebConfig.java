
package co.edu.unbosque.mensajeria.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración web para Spring MVC.
 * Implementa {@link WebMvcConfigurer} para registrar manejadores de recursos estáticos,
 * permitiendo exponer y servir archivos locales almacenados en el servidor (como imágenes o documentos subidos).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Configura los manejadores de recursos para enlazar la URL pública {@code /uploads/**}
	 * con el directorio físico local de almacenamiento (`uploads`) en el sistema de archivos.
	 * Utiliza rutas absolutas y normalizadas para garantizar un acceso seguro y multiplataforma.
	 *
	 * @param registry registro de controladores de recursos de Spring MVC.
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploads = Paths.get(
            System.getProperty("user.dir"),
            "uploads"
        ).toAbsolutePath().normalize();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploads.toUri().toString());
    }
}
