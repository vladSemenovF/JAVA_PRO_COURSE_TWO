package ru.semenov.model.exception;

public class InsufficientLimitException extends RuntimeException {
    public InsufficientLimitException(String limitExceeded) {
        super(limitExceeded);
    }
}
