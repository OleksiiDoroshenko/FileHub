package io.javaclasses.fileHub.api.storage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRecord should: ")
public class UserRecordTest {

    @DisplayName("should hash user password")
    @Test
    public void hashingUserPasswordTest() {

        String login = "test";
        String password = "test123456";

        UserRecord record = new UserRecord(new UserCredentials(login, password));

        assertNotEquals(password, record.passwordHash(), "UserRecord should hash user password.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(UserRecord.class);
        tester.testAllPublicStaticMethods(UserRecord.class);
    }
}
