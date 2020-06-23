package io.javaclasses.fileHub.web;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.Token;
import io.javaclasses.filehub.api.registrationProcess.Register;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ExecutorTest should: ")
class RegistrationTest {

    private final Registration process = new Registration();

    @DisplayName("log in user with correct data")
    @Test
    private void logInTest() {
        String login = "Test";
        String password = "Tests";
        Register register = new Register(login, password);
        Token token = process.logIn(register);
    }

    @DisplayName("not log in user with invalid data")
    @Test
    private void logInWithInvalidDataTest() {
        String login = "Test";
        String password = "Tests";
        Register register = new Register(login, password);
        assertThrows(IllegalArgumentException.class, () -> {
            process.logIn(register);
        }, "should throw exception when user data is invalid.");
    }

    @DisplayName("register user with correct data")
    @Test
    private void registerTest() {
        String login = "Test";
        String password = "Tests";
        Register register = new Register(login, password);
        User user = process.register(register);
        assertEquals("registration method should return user with proper data"
                , register, user.data());
    }

    @DisplayName("register user with invalid data")
    @Test
    private void registerWithInvalidDataTest() {
        String login = "Test";
        String password = "Tests";
        Register register = new Register(login, password);
        assertThrows(IllegalArgumentException.class, () -> {
            process.register(register);
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
