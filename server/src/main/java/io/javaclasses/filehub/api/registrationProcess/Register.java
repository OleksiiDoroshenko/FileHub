package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.web.Validator;

/**
 * Contains user's credentials.
 */
public class Register implements Command {

    private String login;
    private String password;

    /**
     * Returns instance of {@link Register}.
     *
     * @param login    - user's login.
     * @param password - user's password.
     */
    public Register(String login, String password) {
        new Validator().validateLogin(login);
        new Validator().validatePassword(password);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        new Validator().validateLogin(login);
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        new Validator().validatePassword(password);
        this.password = password;
    }
}
