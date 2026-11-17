package io.github.deepakjvm.vaultiq.exception;

public class OtpRetryLimitExceededException extends RuntimeException {

    public OtpRetryLimitExceededException(String message) {
        super(message);
    }
}
