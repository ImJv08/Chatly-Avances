package co.edu.unbosque.mensajeria;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.nio.file.Paths;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MensajeriaApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MensajeriaApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String ruta = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(ruta);
	}
	
	@Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
