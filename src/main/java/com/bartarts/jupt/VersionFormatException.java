package com.bartarts.jupt;

/**
 * Wyjatek rzucany, gdy zapis wersji jest niepoprawny.
 */
public class VersionFormatException extends NumberFormatException {

    public VersionFormatException() {
    }

    public VersionFormatException(String s) {
        super(s);
    }

}
