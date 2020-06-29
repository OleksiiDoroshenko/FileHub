package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("UserCredentials should: ")
public class UserCredentialsTest {

    @DisplayName("not throw any exceptions if parameters are valid.")
    @Test
    public void validParametersTest() {
        String login = "test";
        String password = "test123456";

        UserCredentials credentials = new UserCredentials(login, password);

        assertEquals(credentials.login(), login, "User credentials does not correctly save login.");
        assertEquals(credentials.password(), password, "User credentials does not correctly save password.");
    }

    @DisplayName("throw exceptions if parameters are invalid.")
    @Test
    public void invalidParametersTest() {

        assertThrows(InvalidUserCredentialsException.class, () -> {
            String login = "te";
            String password = "test123456";

            new UserCredentials(login, password);
        }, "UserCredentials should throw exception when login is to short.");

        assertThrows(InvalidUserCredentialsException.class, () -> {
            String login = "test";
            String password = "test";

            new UserCredentials(login, password);
        }, "UserCredentials should throw exception when login is to short.");

    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(UserCredentials.class);
        tester.testAllPublicStaticMethods(UserCredentials.class);
    }
}
