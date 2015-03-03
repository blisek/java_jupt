package com.bartarts.jupt;

/**
 * Created by bartek on 03.03.15.
 */
public class UpdateInfoParseException extends RuntimeException {
    public UpdateInfoParseException() {
    }

    public UpdateInfoParseException(String message) {
        super(message);
    }

    public UpdateInfoParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateInfoParseException(Throwable cause) {
        super(cause);
    }
}
