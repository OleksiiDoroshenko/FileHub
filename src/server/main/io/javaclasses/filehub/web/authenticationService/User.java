package io.javaclasses.filehub.web.authenticationService;

import io.javaclasses.filehub.web.Id;

public class User {

    private final Id id;
    private UserData data;

    public User(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    public UserData getData() {
        return data;
    }

    public void changeUserPassword(String password) {
        this.data.setPassword(password);
    }

    public void changeUserLogin(String login) {
        this.data.setLogin(login);
    }
}
