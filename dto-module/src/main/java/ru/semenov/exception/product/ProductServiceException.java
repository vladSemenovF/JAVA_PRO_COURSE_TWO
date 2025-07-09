package ru.semenov.exception.product;

public class ProductServiceException extends RuntimeException{
    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProductServiceException(String message) {
        super(message);
    }
}
