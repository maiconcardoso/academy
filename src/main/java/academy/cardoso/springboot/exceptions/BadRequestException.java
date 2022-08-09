package academy.cardoso.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    public static final String MESSAGE = "Entity Not Found";

    public BadRequestException(String message) {
        super(message);
    }
    
}
