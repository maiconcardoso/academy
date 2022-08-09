package academy.cardoso.springboot.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import academy.cardoso.springboot.exceptions.BadRequestException;
import academy.cardoso.springboot.exceptions.BadRequestExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequest) {
        return new ResponseEntity<>(
            BadRequestExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request")
                .details(badRequest.getLocalizedMessage())
                .developerMessage(badRequest.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST
        );
    }
}
