package io.javaclasses.filehub.api.registrationProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contains user's credentials.
 */
public class Register {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);
    private final UserCredentials userCredentials;

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

    UserCredentials userCredentials() {
        return userCredentials;
    }

    @Override
    public String toString() {
        return "Register{" +
                "login='" + userCredentials.login() + '\'' +
                ", password='" + userCredentials.password() + '\'' +
                '}';
    }
}
