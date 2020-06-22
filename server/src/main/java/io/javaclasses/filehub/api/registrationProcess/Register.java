package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains user's credentials.
 */
public class Register {

    private String login;
    private String password;
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    /**
     * Returns instance of {@link Register}.
     *
     * @param login    - user's login.
     * @param password - user's password.
     */
    public Register(String login, String password) {
        logger.debug("Trying to create Register command.");
        new Validator().validateLogin(login);
        new Validator().validatePassword(password);
        this.login = login;
        this.password = password;
        logger.debug("Register command was created. Login: " + login + ", password: " + password + ".");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Register{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
