package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.storage.Record;

public class UserRecord implements Record<UserId> {

    private String login;
    private String passwordHash;

    public UserRecord(String login, String password) {
        this.login = login;
        this.passwordHash = String.valueOf(password.hashCode());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
