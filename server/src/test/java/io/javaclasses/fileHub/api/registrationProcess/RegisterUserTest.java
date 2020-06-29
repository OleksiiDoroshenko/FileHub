package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("RegisterUser test should: ")
public class RegisterUserTest {

    @DisplayName("not throw eny exceptions if constructor parameters data is valid.")
    @Test
    public void validDataRegisterUseTest() {
        String login = "Test";
        String password = "Test123456";
        UserCredentials credentials = new UserCredentials(login, password);
        RegisterUser register = new RegisterUser(credentials);
        Assertions.assertEquals(credentials, register.userCredentials(),
                "Register user credentials are not equals to passed ones.");
    }

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(RegisterUser.class);
        tester.testAllPublicStaticMethods(RegisterUser.class);
    }
}
