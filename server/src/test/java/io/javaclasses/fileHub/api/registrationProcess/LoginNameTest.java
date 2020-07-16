package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("LoginName should:")
public class LoginNameTest {

    @DisplayName("not throw any exceptions if passed login is valid.")
    @Test
    public void correctDataTest() {
        String login = "test";

        assertDoesNotThrow(() -> new LoginName(login),
                "LoginName throw exception when passed login is correct.");
    }

    @DisplayName("throw exceptions if passed login is too short.")
    @Test
    public void incorrectDataTest() {
        String login = "tes";

        assertThrows(InvalidUserCredentialsException.class, () ->
                        new LoginName(login)
                , "LoginName does not throw exception when passed login is too short.");
    }

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(LoginName.class);
        tester.testAllPublicStaticMethods(LoginName.class);
    }
}
