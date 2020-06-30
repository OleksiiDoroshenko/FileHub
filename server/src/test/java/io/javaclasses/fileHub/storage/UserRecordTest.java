package io.javaclasses.fileHub.storage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("UserRecord should: ")
public class UserRecordTest {

    @DisplayName("should hash user password")
    @Test
    public void hashingUserPasswordTest() {

        LoginName login = new LoginName("test");
        Password password = new Password("test123456");
        UserId id = new UserId("test");

        UserRecord record = new UserRecord(id, login, password.value());

        assertNotEquals(password, record.password(), "UserRecord should hash user password.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(LoginName.class, new LoginName("test"));
        tester.setDefault(Password.class, new Password("tesT123456"));
        tester.setDefault(UserId.class, new UserId("Test"));


        tester.testAllPublicConstructors(UserRecord.class);
        tester.testAllPublicStaticMethods(UserRecord.class);
    }
}
