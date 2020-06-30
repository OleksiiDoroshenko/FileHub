package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Password should:")
public class PasswordTest {

    @DisplayName("not throw any exceptions if passed value is valid.")
    @Test
    public void correctDataTest() {
        String value = "test123456";

        assertDoesNotThrow(() -> new Password(value),
                "Password throw exception when passed value is correct.");
    }

    @DisplayName("throw exceptions if passed login is too short.")
    @Test
    public void incorrectDataTest() {
        String value = "tes";

        assertThrows(InvalidUserCredentialsException.class, () ->
                        new Password(value)
                , "Password does not throw exception when passed value is too short.");
    }

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(Password.class);
        tester.testAllPublicStaticMethods(Password.class);
    }
}
