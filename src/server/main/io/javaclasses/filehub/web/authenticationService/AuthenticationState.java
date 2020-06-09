package io.javaclasses.filehub.web.authenticationService;

import io.javaclasses.filehub.web.State;
import io.javaclasses.filehub.web.Id;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationState implements State {

    private final Map<Token, User> users = new HashMap<>();

    public User getUser(Token token){
        return null;
    }

    public User getUser(Id id){
        return null;
    };
}
