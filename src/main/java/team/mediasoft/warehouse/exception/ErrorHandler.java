package team.mediasoft.warehouse.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import team.mediasoft.warehouse.dto.ErrorDetailsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "info", createErrorDetailsDto(ex, "Not correct value of parameters"),
                        "errors", getErrorMap(ex))
                );
    }

    @ExceptionHandler(value = {NotAvailableProductException.class})
    public ResponseEntity<Object> handleBadRequest(final RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorDetailsDto(ex, ex.getMessage()));
    }

    @ExceptionHandler(value = {NotUniqueProductNameException.class, NotUniqueCategoryNameException.class})
    public ResponseEntity<Object> handleBConflict(final RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(createErrorDetailsDto(ex, ex.getMessage()));
    }

    @ExceptionHandler(value = {NotFoundCategoryException.class, NotFoundProductException.class})
    public ResponseEntity<Object> handleNotFoundRequest(final RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createErrorDetailsDto(ex, ex.getMessage()));
    }

    private ErrorDetailsDto createErrorDetailsDto(Exception ex, String message) {
        log.error(ex.getMessage());

        return ErrorDetailsDto.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }

    private Map<String, Object> getErrorMap(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    if (errors.get(field) == null) {
                        errors.put(field, error.getDefaultMessage());
                    } else {
                        Object message = errors.get(field);
                        List<Object> listMessage = new ArrayList<>(List.of(message));
                        listMessage.add(error.getDefaultMessage());
                        errors.put(field, listMessage);

                        log.error(error.getDefaultMessage());
                    }
                });
        return errors;
    }
}