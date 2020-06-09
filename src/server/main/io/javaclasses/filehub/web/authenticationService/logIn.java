package io.javaclasses.filehub.web.authenticationService;

import io.javaclasses.filehub.web.Command;
import io.javaclasses.filehub.web.State;

public class logIn implements Command {

    private final UserData userData;

    public logIn(UserData userData) {
        this.userData = userData;
    }

    @Override
    public void execute(State state) {

    }
}
