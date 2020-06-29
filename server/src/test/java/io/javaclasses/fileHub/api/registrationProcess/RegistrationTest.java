package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Registration test should: ")
public class RegistrationTest {

    @DisplayName("register user in the system")
    @Test
    public void registerUserTest() {
        String login = "Test";
        String password = "Test123456";

        UserCredentials credentials = new UserCredentials(login, password);
        RegisterUser register = new RegisterUser(credentials);
        UserRecordStorage storage = new UserRecordStorage();
        Registration registration = new Registration(storage);

        UserId id = registration.handle(register);

        assertTrue(storage.contains(id),
                "registration process does not register user with valid credentials.");

    }

    @DisplayName("throw exception if user with same login already exists in the system.")
    @Test
    public void throwExceptionIfUserAlreadyExistsTest() {
        String login = "Test";
        String password = "Test123456";

        UserCredentials credentials = new UserCredentials(login, password);
        RegisterUser register = new RegisterUser(credentials);
        UserRecordStorage storage = new UserRecordStorage();
        Registration registration = new Registration(storage);

        registration.handle(register);

        assertThrows(UserAlreadyExistsException.class, () -> {
            registration.handle(register);
        }, "register process should throw exception if user with same login already registered.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(Registration.class);
        tester.testAllPublicInstanceMethods(new Registration(new UserRecordStorage()));
        tester.testAllPublicStaticMethods(Registration.class);
    }
}
