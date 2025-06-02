package org.example.exceptions;

public class InvalidPokemonJsonException extends RuntimeException {
    public InvalidPokemonJsonException(String message) {
        super(message);
    }

    public InvalidPokemonJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
