package io.javaclasses.filehub.web.authentiocationProcess;

/**
 * Contains all info about user.
 */
public class User {
    private UserData data;

    /**
     * @return user credentials
     */
    public UserData data() {
        return data;
    }

    /**
     * @return user session token.
     */
    public Token token() {
        return null;
    }
}
