package io.javaclasses.filehub.web.authenticationService;

import io.javaclasses.filehub.web.Command;
import io.javaclasses.filehub.web.Service;
import io.javaclasses.filehub.web.State;

public class AutheticationService implements Service {

    private final State state;

    public AutheticationService(State initState) {
        this.state = initState;
    }

    @Override
    public void process(Command command) {
        command.execute(this.state);
    }
}
