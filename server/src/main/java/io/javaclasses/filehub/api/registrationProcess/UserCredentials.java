package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Value object that contains user login and password.
 */
public class UserCredentials {

    private static final Logger logger = LoggerFactory.getLogger(UserCredentials.class);

    private final String login;
    private final String password;

    /**
     * Returns instance of {@link UserCredentials} class.
     *
     * @param login
     * @param password
     */
    public UserCredentials(String login, String password) {
        validateLogin(login);
        validatePassword(password);
        this.login = login;
        this.password = password;
    }

    public String login() {
        return login;
    }

    public String password() {
        return password;
    }


    /**
     * Validates user password.
     *
     * <p>Throws {@link InvalidUserCredentialsException} with specific message id user password is invalid. </p>
     *
     * @param password - user password.
     * @return - true if password is valid or throws {@link InvalidUserCredentialsException} if it is invalid.
     */
    private boolean validatePassword(String password) {

        logger.debug("Trying to validate password: " + password + "");
        if (password == null) {

            logger.error("Password is null.");
            throw new InvalidUserCredentialsException("Password should not be null.");
        } else {

            int minPasswordLength = 8;
            if (password.length() < minPasswordLength) {

                logger.error("Password is shorter than minimal length.");
                throw new InvalidUserCredentialsException("Password should be longer then " + minPasswordLength + " symbols.");
            }
        }
        logger.debug("Validation completed successfully.");
        return true;
    }

    /**
     * Validates user login.
     *
     * <p>Throws {@link InvalidUserCredentialsException} with specific message id user login is invalid. </p>
     *
     * @param login - user password.
     * @return - true if login is valid or throws {@link InvalidUserCredentialsException} if it is invalid.
     */
    private boolean validateLogin(String login) {

        logger.debug("Trying to validate login: " + login + "");
        if (login == null) {

            logger.error("Login is null.");
            throw new InvalidUserCredentialsException("Login should not be null.");
        } else {
            int minLoginLength = 4;
            if (login.length() < minLoginLength) {

                logger.error("Login is shorter than minimal length.");
                throw new InvalidUserCredentialsException("Login should be longer then " + minLoginLength + " symbols.");
            }
        }
        logger.debug("Validation completed successfully.");
        return true;
    }
}
