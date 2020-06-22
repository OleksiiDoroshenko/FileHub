package io.javaclasses.fileHub.web;

import io.javaclasses.filehub.api.registrationProcess.Register;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.InvalidUserDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ExecutorTest should: ")
class RegistrationTest {
    private final UserStorage storage = new UserStorage();
    private final Registration process = new Registration(storage);


    @DisplayName("register user with correct data")
    @Test
    void registerTest() {
        String login = "Tests";
        String password = "Testsdkashdlkanfl";
        Register register = new Register(new UserCredentials(login, password));
        UserId id = process.handle(register);
        UserRecord record = storage.get(id);

        assertEquals(record.login(), login, "registration method should return user with proper login");

        assertEquals(record.passwordHash(), String.valueOf(password.hashCode()),
                "registration method should return user with proper password hash");

    }

    @DisplayName("register user with invalid data")
    @Test
    void registerWithInvalidDataTest() {

        assertThrows(InvalidUserDataException.class, () -> {
            String login = "Tes";
            String password = "Testsdkashdjkjasklhdh";
            new Register(new UserCredentials(login, password));

        }, "crating register command with invalid login should throw exception when user login is to short.");

        assertThrows(InvalidUserDataException.class, () -> {
            String login = "Admin";
            String password = "Tests";
            new Register(new UserCredentials(login, password));

        }, "crating register command with invalid password" +
                " should throw exception when user password is to short.");
    }

}
