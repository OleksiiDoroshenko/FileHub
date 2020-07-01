package io.javaclasses.filehub.web;

/**
 * An exception that will be thrown when user data is invalid.
 */
public class InvalidUserCredentialsException extends RuntimeException {

    /**
     * Returns instance of {@link InvalidUserCredentialsException}.
     *
     * @param message error message that describes what went wrong.
     */
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
