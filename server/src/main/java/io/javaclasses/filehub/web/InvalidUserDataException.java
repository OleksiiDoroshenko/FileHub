package io.javaclasses.filehub.web;

/**
 * Class that will be thrown when user data is invalid.
 */
public class InvalidUserDataException extends RuntimeException {
    /**
     * Returns instance of {@link InvalidUserDataException}.
     *
     * @param message - error message.
     */
    public InvalidUserDataException(String message) {
        super(message);
    }
}
