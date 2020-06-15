package io.javaclasses.fileHub.web;

import io.javaclasses.filehub.web.authentiocationProcess.AuthenticationProcess;
import io.javaclasses.filehub.web.authentiocationProcess.Token;
import io.javaclasses.filehub.web.authentiocationProcess.User;
import io.javaclasses.filehub.web.authentiocationProcess.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ExecutorTest should: ")
class AuthenticationProcessTest {

    private final AuthenticationProcess process = new AuthenticationProcess();

    @DisplayName("log in user with correct data")
    @Test
    private void logInTest() {
        String login = "Test";
        String password = "Tests";
        UserData userData = new UserData(login, password);
        Token token = process.logIn(userData);
    }

    @DisplayName("not log in user with invalid data")
    @Test
    private void logInWithInvalidDataTest() {
        String login = "Test";
        String password = "Tests";
        UserData userData = new UserData(login, password);
        assertThrows(IllegalArgumentException.class, () -> {
            process.logIn(userData);
        }, "should throw exception when user data is invalid.");
    }

    @DisplayName("register user with correct data")
    @Test
    private void registerTest() {
        String login = "Test";
        String password = "Tests";
        UserData userData = new UserData(login, password);
        User user = process.register(userData);
        assertEquals("registration method should return user with proper data"
                , userData, user.data());
    }

    @DisplayName("register user with invalid data")
    @Test
    private void registerWithInvalidDataTest() {
        String login = "Test";
        String password = "Tests";
        UserData userData = new UserData(login, password);
        assertThrows(IllegalArgumentException.class, () -> {
            process.register(userData);
        }, "should throw exception when user data is invalid.");
    }

    @DisplayName("get user with correct token")
    @Test
    private void getUserTest() {
        Token token = new Token("token");
        User user = process.getUser(token);
        assertEquals("getUser method should return user with proper token"
                , token, user.token());
    }

    @DisplayName("get user with with invalid data")
    @Test
    private void getUserWithInvalidDataTest() {
        Token token = new Token("token");
        User user = process.getUser(token);
        assertThrows(IllegalArgumentException.class, () -> {
            process.getUser(token);
        }, "should throw exception when token is invalid.");
    }
}
