package io.javaclasses.fileHub.api.storage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("UserRecordStorage should: ")
public class UserRecordStorageTest {

    @DisplayName("should correctly check if UserRecord is present in the storage.")
    @Test
    public void storageContainsUserTest() {

        String login = "Test";
        String password = "Test123456";

        UserCredentials credentials = new UserCredentials(login, password);
        UserRecord record = new UserRecord(credentials);

        UserRecordStorage storage = new UserRecordStorage();

        storage.add(record);

        Assertions.assertTrue(storage.containsUser(credentials), "UserStorage can not find saved UserRecord.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(UserRecordStorage.class);
        tester.testAllPublicStaticMethods(UserRecordStorage.class);
    }
}
