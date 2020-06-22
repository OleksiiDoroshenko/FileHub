package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Contains user login and hash value of the password.
 */
public class UserRecord implements Record<UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final String login;
    private final String passwordHash;
    private final UserId id;


    /**
     * Returns instance of {@link UserRecord} class.
     *
     * @param userCredentials - user credentials that contains login and password.
     */
    public UserRecord(UserCredentials userCredentials) {
        this.login = userCredentials.login();
        this.passwordHash = String.valueOf(userCredentials.password().hashCode());
        this.id = new UserId(UUID.randomUUID().toString());
        logger.debug("New user record was created. User login: " + login + ".");
    }

    public String login() {
        return login;
    }

    public String passwordHash() {
        return passwordHash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserId id() {
        return id;
    }
}
