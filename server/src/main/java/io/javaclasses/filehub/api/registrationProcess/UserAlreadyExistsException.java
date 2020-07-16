package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.storage.userStorage.UserStorage;

/**
 * An exception that is thrown when the user already exists in the {@link UserStorage}.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Creates instance of {@link UserAlreadyExistsException}.
     *
     * @param message error message that describes what went wrong.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
