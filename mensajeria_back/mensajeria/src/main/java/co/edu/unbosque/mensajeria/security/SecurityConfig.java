package co.edu.unbosque.mensajeria.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de seguridad para la aplicación. Esta clase define la
 * configuración de seguridad utilizando Spring Security, incluyendo la gestión
 * de autenticación, autorización, CORS, y la configuración de filtros para JWT.
 * También habilita la seguridad basada en métodos y la seguridad web.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	/**
	 * Filtro de autenticación JWT para validar tokens en las solicitudes.
	 */
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	/**
	 * Servicio de detalles de usuario para cargar información de usuario durante la
	 * autenticación.
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Constructor para inyectar dependencias.
	 *
	 * @param jwtAuthFilter      Filtro de autenticación JWT.
	 * @param userDetailsService Servicio de detalles de usuario.
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Configura la cadena de filtros de seguridad para la aplicación. Define las
	 * reglas de autorización para los endpoints, desactiva CSRF, configura CORS, y
	 * establece la política de sesión como stateless.
	 *
	 * @param http Objeto {@link HttpSecurity} para configurar la seguridad HTTP.
	 * @return {@link SecurityFilterChain} configurado.
	 * @throws Exception Si ocurre un error durante la configuración.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.authorizeHttpRequests(auth -> auth
						//Recursos y especificaciones OpenAPI de Swagger (Spring Security remueve el /api interno)
						.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
						.requestMatchers("/swagger-resources", "/swagger-resources/**", "/webjars/**").permitAll()
						
						//Rutas públicas de autenticación del sistema
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/uploads/**").permitAll()
						
						.requestMatchers("/error").permitAll()
						
						// Cualquier otra petición al backend requerirá token JWT válido
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	/**
	 * Configura la fuente de configuración CORS para la aplicación. Define los
	 * orígenes, encabezados y métodos permitidos para las solicitudes CORS.
	 *
	 * @return {@link CorsConfigurationSource} configurado.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://chatzeroprog2.netlify.app"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Proporciona un proveedor de autenticación basado en DAO. Configura el
	 * proveedor para utilizar el servicio de detalles de usuario y el codificador
	 * de contraseñas.
	 *
	 * @return {@link AuthenticationProvider} configurado.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * Proporciona un codificador de contraseñas utilizando BCrypt.
	 *
	 * @return {@link PasswordEncoder} configurado.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Proporciona el gestor de autenticación basado en la configuración de
	 * autenticación.
	 *
	 * @param config Configuración de autenticación.
	 * @return {@link AuthenticationManager} configurado.
	 * @throws Exception Si ocurre un error al obtener el gestor de autenticación.
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
}

