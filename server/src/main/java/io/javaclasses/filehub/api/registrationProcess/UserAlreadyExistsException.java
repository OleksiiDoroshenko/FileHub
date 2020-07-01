package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;

/**
 * An exception that will be thrown when user already exists in the {@link UserRecordStorage}.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Returns instance of {@link UserAlreadyExistsException}.
     *
     * @param message error message that describes what went wrong.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
