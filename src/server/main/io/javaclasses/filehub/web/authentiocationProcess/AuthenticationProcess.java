package io.javaclasses.filehub.web.authentiocationProcess;

/**
 * Provides functionality for authorization process into the system.
 */
public class AuthenticationProcess {
    /**
     * Allows user to log in.
     *
     * @param userData - user credentials.
     * @return session token.
     */
    public Token logIn(UserData userData) {
        return null;
    }

    /**
     * Allows user to register.
     *
     * @param userData - user credentials.
     * @return registered user.
     */
    public User register(UserData userData) {
        return null;
    }

    public User getUser(Token token) {
        return null;
    }
}
