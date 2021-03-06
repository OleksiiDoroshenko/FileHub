package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.*;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Registration should: ")
public class RegistrationTest {

    @DisplayName("register user in the system.")
    @Test
    public void registerUserTest() {
        LoginName login = new LoginName("Test");
        Password password = new Password("Test123456");
        RegisterUser register = new RegisterUser(login, password);

        UserStorage storage = new UserStorage();
        FolderStorage folderStorage = new FolderStorage();

        Registration registration = new Registration(storage, folderStorage);

        UserId id = registration.handle(register);

        assertTrue(storage.contains(id),
                "registration process does not register user with valid credentials.");

    }

    @DisplayName("throw exception if user with same login already exists in the system.")
    @Test
    public void throwExceptionIfUserAlreadyExistsTest() {
        LoginName login = new LoginName("Test");
        Password password = new Password("Test123456");
        RegisterUser register = new RegisterUser(login, password);

        UserStorage storage = new UserStorage();
        FolderStorage folderStorage = new FolderStorage();

        Registration registration = new Registration(storage, folderStorage);

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
        tester.testAllPublicInstanceMethods(new Registration(new UserStorage(), new FolderStorage()));
        tester.testAllPublicStaticMethods(Registration.class);
    }
}
