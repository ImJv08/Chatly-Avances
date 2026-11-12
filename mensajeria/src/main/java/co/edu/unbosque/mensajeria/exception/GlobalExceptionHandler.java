package co.edu.unbosque.mensajeria.exception;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para interceptar y gestionar errores 
 * originados en la capa de persistencia y base de datos de la aplicación,
 * transformándolos en respuestas HTTP normalizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Maneja excepciones relacionadas con el acceso a datos y fallos de JPA/base de datos,
     * determinando el código de estado HTTP adecuado según el código SQLState.
     * 
     * @param ex excepción de runtime lanzada por la capa de persistencia.
     * @return un {@link ResponseEntity} que contiene un mapa con el mensaje de error normalizado y el estado HTTP correspondiente.
     */
    @ExceptionHandler({
        DataAccessException.class,
        JpaSystemException.class
    })
    public ResponseEntity<Map<String, String>> manejarErrorBD(
            RuntimeException ex) {

        SQLException sqlEx = obtenerSQLException(ex);

        if (sqlEx == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "mensaje",
                        "Ocurrió un error interno al procesar la solicitud"
                    ));
        }

        String codigo = sqlEx.getSQLState();
        String mensaje = limpiarMensaje(sqlEx.getMessage());

        HttpStatus estado;

        if (codigo == null) {
            estado = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            estado = switch (codigo) {
                case "P1001" -> HttpStatus.NOT_FOUND;
                case "P1002" -> HttpStatus.NOT_FOUND;
                case "P1003" -> HttpStatus.NOT_FOUND;
                case "P1004" -> HttpStatus.CONFLICT;
                case "P1005" -> HttpStatus.FORBIDDEN;
                case "P1006" -> HttpStatus.NOT_FOUND;
                case "P1007" -> HttpStatus.CONFLICT;
                case "P1008" -> HttpStatus.CONFLICT;
                default -> HttpStatus.INTERNAL_SERVER_ERROR;
            };
        }

        if (estado == HttpStatus.INTERNAL_SERVER_ERROR) {
            mensaje = "Ocurrió un error interno al procesar la solicitud";
        } else if (mensaje == null || mensaje.isBlank()) {
            mensaje = "No se pudo completar la operación";
        }

        return ResponseEntity
                .status(estado)
                .body(Map.of("mensaje", mensaje));
    }

  
    /**
     * Limpia y formatea el mensaje de error original de la excepción SQL, 
     * eliminando prefijos de error, saltos de línea y trazas de ubicación.
     * 
     * @param m mensaje de error original de la base de datos.
     * @return el mensaje de error depurado y listo para el cliente, o {@code null} si el parámetro es nulo.
     */
    private String limpiarMensaje(String m) {
        if (m == null) {
            return null;
        }
        m = m.trim();
        if (m.startsWith("ERROR:")) {
            m = m.substring("ERROR:".length());
        }
        int salto = m.indexOf('\n');
        if (salto >= 0) {
            m = m.substring(0, salto);
        }
        int where = m.indexOf("Where:");
        if (where >= 0) {
            m = m.substring(0, where);
        }
        return m.trim();
    }

    /**
     * Realiza una búsqueda iterativa a través de la causa raíz de las excepciones
     * para extraer la instancia subyacente de {@link SQLException}.
     * 
     * @param ex la excepción raíz o envolvente capturada.
     * @return el objeto {@link SQLException} encontrado, o {@code null} si no existe en la jerarquía de causas.
     */
    private SQLException obtenerSQLException(Throwable ex) {
        Throwable actual = ex;

        while (actual != null) {
            if (actual instanceof SQLException sqlEx) {
                return sqlEx;
            }

            actual = actual.getCause();
        }

        return null;
    }
}
