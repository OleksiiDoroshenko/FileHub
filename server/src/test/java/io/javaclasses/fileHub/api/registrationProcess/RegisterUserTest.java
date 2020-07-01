package io.javaclasses.fileHub.api.registrationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RegisterUser should: ")
public class RegisterUserTest {

    @DisplayName("not throw eny exceptions if constructor parameters data is valid.")
    @Test
    public void validDataRegisterUseTest() {
        LoginName login = new LoginName("Test");
        Password password = new Password("Test123456");
        RegisterUser register = new RegisterUser(login, password);
        assertEquals(login, register.loginName(),
                "Register user login are not equals to passed one.");
    }

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(LoginName.class, new LoginName("test"));
        tester.setDefault(Password.class, new Password("tesT123456"));

        tester.testAllPublicConstructors(RegisterUser.class);
        tester.testAllPublicStaticMethods(RegisterUser.class);
    }
}
