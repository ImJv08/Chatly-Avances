package co.edu.unbosque.mensajeria.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Filtro de seguridad personalizado que intercepta cada solicitud HTTP entrante 
 * para validar la presencia, integridad y autenticidad del token JWT en el encabezado de autorización,
 * estableciendo la sesión de seguridad en el contexto de Spring Security si es válido.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	/**
	 * Utilidad para la gestión, extracción y validación de tokens JWT.
	 */
	private final JwtUtil jwtUtil;
	
	/**
	 * Servicio para la carga de detalles de usuario desde la base de datos.
	 */
	private final UserDetailsService userDetailsService;
	
	/**
	 * Constructor para la inyección de dependencias de los componentes de seguridad.
	 * 
	 * @param jwtUtil utilidad para manejo de tokens JWT.
	 * @param userDetailsService servicio de carga de usuarios.
	 */
	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

	/**
	 * Intercepta la petición HTTP para verificar exclusiones de rutas públicas, 
	 * extraer el token JWT, validarlo y configurar la autenticación en el contexto de seguridad.
	 * 
	 * @param request petición HTTP entrante.
	 * @param response respuesta HTTP saliente.
	 * @param filterChain cadena de filtros de seguridad.
	 * @throws ServletException en caso de error en el procesamiento del servlet.
	 * @throws IOException en caso de error de entrada/salida.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		
		if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}
		
		if (path.startsWith("/swagger-ui") ||
	            path.startsWith("/v3/api-docs") ||
	            path.startsWith("/api-docs") ||
	            path.startsWith("/swagger-resources") ||
	            path.startsWith("/webjars") ||
	            path.startsWith("/auth")) {

	            filterChain.doFilter(request, response);
	            return;
	    }
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);

            } catch (Exception e) {
                logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage(), e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            if (jwtUtil.validateToken(jwt, userDetails)) {
                List<GrantedAuthority> authorities = extractAuthoritiesFromToken(jwt);
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                logger.debug("Usuario autenticado: " + username + " con authorities: " + authorities);
            }
        }

        filterChain.doFilter(request, response);
	} 
	
	/**
	 * Extrae y mapea los permisos o roles (authorities) contenidos dentro de las reclamaciones (claims) del token JWT.
	 * 
	 * @param token cadena de texto del token JWT.
	 * @return una lista de objetos {@link GrantedAuthority} extraídos del token.
	 */
	private List<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            List<Map<String, String>> authoritiesClaim = claims.get("authorities", List.class);
            
            if (authoritiesClaim != null) {
                for (Map<String, String> authority : authoritiesClaim) {
                    String authorityName = authority.get("authority");
                    if (authorityName != null) {
                        authorities.add(new SimpleGrantedAuthority(authorityName));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al extraer authorities del token: " + e.getMessage(), e);
        }
        return authorities;
    }
	
}
