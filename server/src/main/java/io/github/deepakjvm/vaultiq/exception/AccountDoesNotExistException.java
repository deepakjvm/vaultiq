package io.github.deepakjvm.vaultiq.exception;

public class AccountDoesNotExistException extends RuntimeException {

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
