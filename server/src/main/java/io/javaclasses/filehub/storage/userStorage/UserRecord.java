package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains user login and hash value of the password.
 */
public class UserRecord implements Record<UserId> {

    private String login;
    private String passwordHash;
    private static final Logger logger = LoggerFactory.getLogger(Registration.class);

    /**
     * Returns instance of {@link UserRecord} class.
     *
     * @param login    - user login.
     * @param password - user password.
     */
    public UserRecord(String login, String password) {
        this.login = login;
        this.passwordHash = String.valueOf(password.hashCode());
        logger.debug("New user record was created. User login: " + login + ".");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = String.valueOf(password.hashCode());
    }

}
