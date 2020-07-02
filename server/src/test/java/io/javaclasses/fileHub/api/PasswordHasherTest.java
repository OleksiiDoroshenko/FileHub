package io.javaclasses.fileHub.api;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.registrationProcess.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PasswordHasher should: ")
public class PasswordHasherTest {

    @DisplayName("create password hash.")
    @Test
    public void correctDataTest() {
        Password password = new Password("test123456");

        String hash = PasswordHasher.getHash(password);

        Assertions.assertNotEquals(password.value(), hash,
                "Hash and password are equal, but they should not be.");
    }

    @DisplayName("throw exception if method parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(PasswordHasher.class);
        tester.testAllPublicStaticMethods(PasswordHasher.class);
    }
}
