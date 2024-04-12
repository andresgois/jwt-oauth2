package br.com.andresgois.FeignApplication.infrastructure.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> userPrincipalNotFoundException(
            final HttpServletRequest request,
            final UserNotFoundException user
    ) {
        ErrorResponse userNotFound = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuário não econtrado");
        return ResponseEntity.ok(userNotFound);
    }
}
