package io.javaclasses.filehub.web;

/**
 * An exception that is thrown when logged in user is not present in the FileHub application but trying to get access.
 */
public class UserNotLoggedInException extends RuntimeException {

    /**
     * Returns instance of {@link UserNotLoggedInException} class.
     *
     * @param message error message that describes what went wrong.
     */
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
