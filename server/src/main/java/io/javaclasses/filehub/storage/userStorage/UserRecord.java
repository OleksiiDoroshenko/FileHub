package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
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
     * @param userCredentials - user credentials that contains login and password.
     */
    public UserRecord(UserCredentials userCredentials) {
        this.login = userCredentials.login();
        this.passwordHash = String.valueOf(userCredentials.password().hashCode());
        logger.debug("New user record was created. User login: " + login + ".");
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

}