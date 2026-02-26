package fr.paillaugue.outfitter.common.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fr.paillaugue.outfitter.common.errorHandling.dto.ErrorResponseDTO;

import java.util.Arrays;

import static java.util.Arrays.stream;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutfitterSpringException.class)
    public ResponseEntity<ErrorResponseDTO> handleOutfitterSpringException(OutfitterSpringException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            ex.getStatus().value(),
            ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            httpStatus.value(),
            "Validation failed: "+ex.getBindingResult().getFieldErrors().stream().map((e) -> e.getField() + " " + e.getDefaultMessage()).reduce((a, b) -> a + "; " + b).orElse("No details")
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Throwable cause = ex.getMostSpecificCause();
        String message = "Malformed request";

        // Handle InvalidFormatException (common for enums)
        if (cause instanceof tools.jackson.databind.exc.InvalidFormatException ife) {
            var path = ife.getPath();
            if (!path.isEmpty() && ife.getTargetType().isEnum()) {
                message = path.stream()
                        .map(ref -> ref.getPropertyName() != null ? ref.getPropertyName() :"[" + ref.getIndex() + "]")
                        .reduce((a, b) -> a + "." + b)
                        .orElse("?") + " has invalid value: " + ife.getValue() + ", expected one of " + Arrays.toString(ife.getTargetType().getEnumConstants());
            }
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorResponse = new ErrorResponseDTO(
            httpStatus.value(),
            "An unexpected error occurred (" + ex.getClass() + ")"
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
