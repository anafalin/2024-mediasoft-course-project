package team.mediasoft.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAvailableProductException extends RuntimeException {
    public NotAvailableProductException(String message) {
        super(message);
    }
}