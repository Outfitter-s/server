package fr.paillaugue.outfitter.common.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fr.paillaugue.outfitter.common.errorHandling.dto.ErrorResponseDTO;

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
            "Validation failed: "+ex.getBindingResult().getFieldError()
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            httpStatus.value(),
            "An unexpected error occurred"
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
