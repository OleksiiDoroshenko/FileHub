package io.javaclasses.filehub.web;

/**
 * Class that will be thrown when user data is invalid.
 */
public class InvalidUserCredentialsException extends RuntimeException {
    /**
     * Returns instance of {@link InvalidUserCredentialsException}.
     *
     * @param message - error message.
     */
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
