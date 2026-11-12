package co.edu.unbosque.mensajeria;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Inicializador del servlet para permitir el despliegue de la aplicación Spring Boot
 * en un contenedor de servlets externo (como Apache Tomcat en formato archivo .war).
 * Extiende de {@link SpringBootServletInitializer} para configurar la fuente principal 
 * de la aplicación mediante SpringApplicationBuilder.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación Spring Boot cuando se inicia como un contenedor externo.
	 * Registra la clase principal {@link MensajeriaApplication} como la fuente de configuración.
	 * 
	 * @param application el constructor de la aplicación Spring.
	 * @return el constructor configurado con la fuente principal de la aplicación.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MensajeriaApplication.class);
	}

}
