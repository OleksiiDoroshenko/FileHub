package io.javaclasses.filehub.api.registrationProcess;

/**
 * Class that will be thrown when user already exists in the user storage.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Returns instance of {@link UserAlreadyExistsException}.
     *
     * @param message - error message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
