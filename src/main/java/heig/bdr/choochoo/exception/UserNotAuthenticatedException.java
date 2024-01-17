package heig.bdr.choochoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException() {
        super("User is not authenticated.");
    }
}
