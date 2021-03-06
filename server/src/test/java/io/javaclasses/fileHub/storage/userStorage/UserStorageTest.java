package io.javaclasses.fileHub.storage.userStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("UserRecordStorage should: ")
public class UserStorageTest {

    @DisplayName("correctly add, check if present and get record.")
    @Test
    public void storageContainsUserTest() {

        UserStorage storage = new UserStorage();
        UserRecord record = createValidRecord(storage);

        UserId id = storage.add(record);

        Optional<UserRecord> optional = storage.get(record.loginName());
        assertTrue(optional.isPresent(),
                "UserStorage can not find saved UserRecord.");
        assertEquals(record, storage.get(id).get(), "storage should return exactly the same record as was added.");

    }

    @DisplayName("correctly remove record.")
    @Test
    public void storageRemoveUserTest() {

        UserStorage storage = new UserStorage();
        UserRecord record = createValidRecord(storage);

        UserId id = storage.add(record);

        Optional<UserRecord> removedRecord = storage.remove(id);

        Optional<UserRecord> optional = storage.get(id);
        assertTrue(!optional.isPresent(),
                "UserStorage can not find saved UserRecord.");
        assertEquals(record, removedRecord.get(),
                "storage should return record that was removed.");

    }

    @DisplayName("correctly return all added records.")
    @Test
    public void storageReturnAllUserRecordsTest() {

        UserStorage storage = new UserStorage();
        int listLength = 5;

        for (int i = 0; i < listLength; i++) {
            storage.add(createValidRecord(storage));
        }

        assertEquals(storage.all().size(), listLength, "UserStorage returns not all added records.");
    }

    @DisplayName("correctly add record that login already exists in the storage.")
    @Test
    public void storageAddAlreadyExistedRecordTest() {

        UserStorage storage = new UserStorage();
        UserRecord record = createValidRecord(storage);

        storage.add(record);

        UserId id = new UserId(storage.generateId());
        FolderId rootFolder = new FolderId("test");
        UserRecord recordCopy = new UserRecord(id, record.loginName(), "password", rootFolder);

        storage.add(recordCopy);

        assertNotEquals(record, storage.get(recordCopy.loginName()),
                "Storage does not rewrite existing record with same loginName, but should.");

    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(UserStorage.class);
        tester.testAllPublicStaticMethods(UserStorage.class);
    }

    private UserRecord createValidRecord(UserStorage storage) {
        LoginName login = new LoginName(generateRandomName());
        Password password = new Password("Test123456");
        UserId id = new UserId(storage.generateId());
        FolderId rootFolder = new FolderId("test");

        return new UserRecord(id, login, password.value(), rootFolder);
    }

    private String generateRandomName() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
