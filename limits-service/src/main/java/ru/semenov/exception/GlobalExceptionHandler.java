package ru.semenov.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.semenov.model.exception.ErrorResponse;
import ru.semenov.model.exception.InsufficientLimitException;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientLimitException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientLimit(
            InsufficientLimitException ex,
            WebRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Ошибка при списании",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            Exception ex,
            WebRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Ошибка валидации",
                "Невалидные параметры request",
                request
        );
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex,
            WebRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Ошибка при обработке транзакции",
                "Ресурс изменяется другой транзакцией. Повторите операцию",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            WebRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Внутренняя ошибка сервера",
                "Неизвестная ошибка сервера",
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String error,
            String message,
            WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(
                        OffsetDateTime.now(),
                        status.value(),
                        error,
                        message,
                        request.getDescription(false).replace("uri=", "")
                ),
                status
        );
    }
}