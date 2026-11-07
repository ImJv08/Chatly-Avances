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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		
		if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}
		
		//Rutas publicas que no requieren autenticacion
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
		
		//Extreaer el token JWT del encabezado Authorization
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);

            } catch (Exception e) {
                logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage(), e);
            }
        }

        // Si el usuario es válido y no hay autenticación establecida
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Validar el token
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // ✅ EXTRAER AUTHORITIES DEL TOKEN JWT
                List<GrantedAuthority> authorities = extractAuthoritiesFromToken(jwt);
                
                // Crear el token de autenticación con las authorities del JWT
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                logger.debug("Usuario autenticado: " + username + " con authorities: " + authorities);
            }
        }

        filterChain.doFilter(request, response);
	} 
	
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
