package io.javaclasses.filehub.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides functionality for user login and password validation.
 */
public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    /**
     * Validates user password.
     *
     * <p>Throws {@link InvalidUserDataException} with specific message id user password is invalid. </p>
     *
     * @param password - user password.
     * @return - true if password is valid or throws {@link InvalidUserDataException} if it is invalid.
     */
    public boolean validatePassword(String password) {

        logger.debug("Trying to validate password: " + password + "");
        if (password == null) {

            logger.error("Password is null.");
            throw new InvalidUserDataException("Password should not be null.");
        } else {

            int minPasswordLength = 8;
            if (password.length() < minPasswordLength) {

                logger.error("Password is shorter than minimal length.");
                throw new InvalidUserDataException("Password should be longer then " + minPasswordLength + " symbols.");
            }
        }
        logger.debug("Validation completed successfully.");
        return true;
    }

    /**
     * Validates user login.
     *
     * <p>Throws {@link InvalidUserDataException} with specific message id user login is invalid. </p>
     *
     * @param login - user password.
     * @return - true if login is valid or throws {@link InvalidUserDataException} if it is invalid.
     */
    public boolean validateLogin(String login) {

        logger.debug("Trying to validate login: " + login + "");
        if (login == null) {

            logger.error("Login is null.");
            throw new InvalidUserDataException("Login should not be null.");
        } else {
            int minLoginLength = 4;
            if (login.length() < minLoginLength) {

                logger.error("Login is shorter than minimal length.");
                throw new InvalidUserDataException("Login should be longer then " + minLoginLength + " symbols.");
            }
        }
        logger.debug("Validation completed successfully.");
        return true;
    }
}

