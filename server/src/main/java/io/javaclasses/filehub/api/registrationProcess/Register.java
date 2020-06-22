package io.javaclasses.filehub.api.registrationProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contains user's credentials.
 */
public class Register {

    private final UserCredentials userCredentials;
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    /**
     * Returns instance of {@link Register}.
     *
     * @param userCredentials - user's credentials that contains login and password.
     */
    public Register(UserCredentials userCredentials) {
        checkNotNull(userCredentials);
        logger.debug("Trying to create Register command.");
        this.userCredentials = userCredentials;
        logger.debug("Register command was created. Login: " + userCredentials.login() +
                ", password: " + userCredentials.password() + ".");
    }

    public UserCredentials userCredentials() {
        return userCredentials;
    }

    public String login() {
        return userCredentials.login();
    }

    public String password() {
        return userCredentials.password();
    }

    @Override
    public String toString() {
        return "Register{" +
                "login='" + userCredentials.login() + '\'' +
                ", password='" + userCredentials.password() + '\'' +
                '}';
    }
}
