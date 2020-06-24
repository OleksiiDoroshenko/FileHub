package io.javaclasses.filehub.api.registrationProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represent client intention to register new user.
 */
public class RegisterUser {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUser.class);
    private final UserCredentials userCredentials;

    /**
     * Returns instance of {@link RegisterUser}.
     *
     * @param userCredentials - user's credentials that contains login and password.
     */
    public RegisterUser(UserCredentials userCredentials) {
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
