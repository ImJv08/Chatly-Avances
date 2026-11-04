package co.edu.unbosque.mensajeria.exception;


import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
        String mensaje = sqlEx.getMessage();

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
