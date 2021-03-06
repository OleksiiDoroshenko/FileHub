package io.javaclasses.fileHub.storage.loggedInUserStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("LoggedInUsersStorage should: ")
public class LoggedInUsersStorageTest {

    @DisplayName("correctly return existed record by its value.")
    @Test
    public void storageContainsTokenTest() {

        LoggedInUsersStorage storage = new LoggedInUsersStorage();
        Token token = addValidRecordToStorage(storage);

        Optional<LoggedInUserRecord> optional = storage.get(token);
        assertTrue(optional.isPresent(),
                "TokenStorage can not find saved TokenRecord.");
    }

    @SuppressWarnings("LocalDateTemporalAmount")
    private Token addValidRecordToStorage(LoggedInUsersStorage storage) {
        Token id = new Token("test");
        LoggedInUserRecord record = new LoggedInUserRecord(id, new UserId("test"),
                LocalDate.now(ServerTimeZone.get()).plus(Period.ofDays(3)));
        return storage.add(record);
    }

    @DisplayName("not return token if its expiration date was passed.")
    @Test
    public void gettingRecordWithExpiredTokenTest() {
        LoggedInUsersStorage storage = new LoggedInUsersStorage();
        Token token = addRecordWithExpiredToken(storage);

        Optional<LoggedInUserRecord> optional = storage.get(token);
        assertTrue(!optional.isPresent(),
                "TokenStorage returns record with expired token, but should not.");
    }

    @SuppressWarnings("LocalDateTemporalAmount")
    private Token addRecordWithExpiredToken(LoggedInUsersStorage storage) {
        Token id = new Token("test");
        LoggedInUserRecord record = new LoggedInUserRecord(id, new UserId("test"),
                LocalDate.now(ServerTimeZone.get()).minus(Period.ofDays(3)));
        return storage.add(record);
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(LoggedInUsersStorage.class);
        tester.testAllPublicStaticMethods(LoggedInUsersStorage.class);
    }

}
