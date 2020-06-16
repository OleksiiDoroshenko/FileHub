package io.javaclasses.filehub.web;

public class Validator {

    public boolean validatePassword(String password) {

        if (password == null) {

            throw new InvalidUserDataException("Password should not be null.");
        } else if (password.length() < 8) {

            throw new InvalidUserDataException("Password should be longer then 8 symbols.");
        }
        return true;
    }

    public boolean validateLogin(String login) {

        if (login == null) {

            throw new InvalidUserDataException("Login should not be null.");
        } else if (login.length() < 8) {

            throw new InvalidUserDataException("Login should be longer then 8 symbols.");
        }
        return true;
    }
}

