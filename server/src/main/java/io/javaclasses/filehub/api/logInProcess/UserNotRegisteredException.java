package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.storage.userStorage.UserStorage;

/**
 * An exception that is thrown when the user is not registered in the {@link UserStorage}.
 */
public class UserNotRegisteredException extends RuntimeException {

    /**
     * Returns instance of {@link UserNotRegisteredException}.
     *
     * @param message error message that describes what went wrong.
     */
    public UserNotRegisteredException(String message) {
        super(message);
    }
}
