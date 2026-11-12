package co.edu.unbosque.mensajeria.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * Servicio utilitario para la generación, validación y manejo de tokens JWT
 * (JSON Web Token). Proporciona funcionalidades para crear tokens con claims
 * personalizados, extraer información de los tokens, validar su autenticidad y
 * manejar la expiración de los mismos. Utiliza la biblioteca jjwt para el
 * manejo de tokens JWT y Spring Security para la integración con el sistema de
 * autenticación y autorización.
 */
@Service
public class JwtUtil {

	/**
	 * Clave secreta utilizada para firmar los tokens JWT. Se obtiene de la
	 * configuración de la aplicación.
	 */
	@Value("${spring.jwt.secret}")
	private String secretKey;

	/**
	 * Tiempo de expiración de los tokens JWT en milisegundos. Se obtiene de la
	 * configuración de la aplicación.
	 */
	@Value("${spring.jwt.expirationtime}")
	private long expirationTime;

	/**
	 * Constructor vacio.
	 */
	public JwtUtil() {
	}

	/**
	 * Genera un token JWT con claims adicionales y un sujeto específico.
	 *
	 * @param extraClaims Claims adicionales a incluir en el token.
	 * @param subject     Sujeto del token (generalmente el nombre de usuario o
	 *                    correo electrónico).
	 * @return Token JWT generado y firmado.
	 */
	public String obtenerToken(Map<String, Object> extraClaims, String subject) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setClaims(extraClaims) // Claims adicionales
				.setSubject(subject).setIssuedAt(new Date(now)).setExpiration(new Date(now + expirationTime))
				.signWith(obtenerKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Obtiene la clave de firma para los tokens JWT. Decodifica la clave secreta en
	 * base64 y genera una clave HMAC-SHA.
	 *
	 * @return Clave de firma para los tokens JWT.
	 */
	private Key obtenerKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Valida un token JWT comparando su sujeto con los detalles del usuario y
	 * verificando que no haya expirado.
	 *
	 * @param token       Token JWT a validar.
	 * @param userDetails Detalles del usuario autenticado.
	 * @return true si el token es válido, false en caso contrario.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Extrae el nombre de usuario (sujeto) de un token JWT.
	 *
	 * @param token Token JWT del que extraer el nombre de usuario.
	 * @return Nombre de usuario contenido en el token.
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extrae la fecha de expiración de un token JWT.
	 *
	 * @param token Token JWT del que extraer la fecha de expiración.
	 * @return Fecha de expiración del token.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extrae un claim específico de un token JWT utilizando un resolver de claims.
	 *
	 * @param <T>            Tipo del claim a extraer.
	 * @param token          Token JWT del que extraer el claim.
	 * @param claimsResolver Función para resolver el claim deseado.
	 * @return Claim extraído del token.
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Verifica si un token JWT ha expirado.
	 *
	 * @param token Token JWT a verificar.
	 * @return true si el token ha expirado, false en caso contrario.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		return obtenerToken(new HashMap<>(), userDetails.getUsername());
	}

	/**
	 * Extrae todos los claims de un token JWT. Este método es público para permitir
	 * su acceso desde otros componentes.
	 *
	 * @param token Token JWT del que extraer los claims.
	 * @return Objeto {@link Claims} que contiene todos los claims del token.
	 */
	public Claims extractAllClaims(String token) {
	    return Jwts.parser()
	            .verifyWith((SecretKey) obtenerKey()) 
	            .build()
	            .parseSignedClaims(token)
	            .getPayload(); 
	}
}