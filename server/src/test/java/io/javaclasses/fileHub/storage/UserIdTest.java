package io.javaclasses.fileHub.storage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("UserId should: ")
public class UserIdTest {

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(UserId.class);
        tester.testAllPublicStaticMethods(UserId.class);
    }
}
