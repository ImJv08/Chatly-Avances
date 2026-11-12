package co.edu.unbosque.mensajeria;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.nio.file.Paths;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase principal que inicializa y configura la aplicación Spring Boot.
 * Implementa {@link WebMvcConfigurer} para personalizar la configuración de Spring MVC,
 * permitiendo gestionar el acceso a recursos estáticos externos (como la carpeta de archivos cargados).
 */
@SpringBootApplication
public class MensajeriaApplication implements WebMvcConfigurer{
	
	/**
	 * Punto de entrada principal (método main) que ejecuta y arranca la aplicación Spring Boot.
	 * 
	 * @param args argumentos de la línea de comandos pasados durante la ejecución.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MensajeriaApplication.class, args);
	}
	
	/**
	 * Configura los manejadores de recursos web para permitir el acceso público
	 * a los archivos almacenados localmente en el directorio de cargas (`uploads`).
	 * Mapea la ruta URL {@code /uploads/**} con la ruta absoluta del sistema de ficheros.
	 *
	 * @param registry registro de controladores de recursos de Spring MVC.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String ruta = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(ruta);
	}
	
	/**
	 * Define y expone un bean de tipo {@link ModelMapper} en el contenedor de Spring.
	 * Es utilizado globalmente para el mapeo automatizado y la conversión de objetos 
	 * entre entidades de base de datos y objetos de transferencia de datos (DTOs).
	 * 
	 * @return una nueva instancia inicializada de {@link ModelMapper}.
	 */
	@Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
