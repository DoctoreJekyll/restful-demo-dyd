package org.generations.restfuldemodyd.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler; // Anotación para indicar qué excepción manejar
import org.springframework.web.bind.annotation.RestControllerAdvice; // Marca esta clase como manejador global de errores

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

// Marca la clase como un manejador de excepciones global para todos los controladores REST
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación de objetos (@Valid en @RequestBody, por ejemplo)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Convierte la lista de errores en un Map<campo, mensaje>
        Map<String,String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField, // clave: nombre del campo
                        FieldError::getDefaultMessage // valor: mensaje de error
                ));

        // Crea el objeto de respuesta de error personalizado
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Validation Failed", // Mensaje general
                LocalDateTime.now(), // Momento del error
                errors, // Detalles campo -> mensaje
                request.getRequestURI() // Endpoint donde ocurrió
        );

        // Devuelve la respuesta con código 400
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Maneja validaciones a nivel de parámetros (@RequestParam, @PathVariable, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {

        // Convierte las violaciones de validación en un mapa clave-valor
        Map<String,String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        cv -> cv.getPropertyPath().toString(), // clave: ruta del parámetro
                        ConstraintViolation::getMessage // valor: mensaje de error
                ));

        // Crea la respuesta de error
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                LocalDateTime.now(),
                errors,
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Maneja cuando un recurso no existe (excepción personalizada)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage(), // Mensaje específico del error
                LocalDateTime.now(),
                null, // No hay errores de validación
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Maneja errores cuando el JSON está mal formado o el tipo no coincide
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "JSON malformado o tipo inválido",
                LocalDateTime.now(),
                null,
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier otro error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex, HttpServletRequest request) {

        ex.printStackTrace(); // IMPORTANTE: en producción se debería registrar en logs, no imprimir

        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Error interno del servidor",
                LocalDateTime.now(),
                null,
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
