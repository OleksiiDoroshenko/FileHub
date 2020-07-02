package io.javaclasses.fileHub.storage.TokenStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.tokenStorage.TokenId;
import io.javaclasses.filehub.storage.tokenStorage.TokenRecord;
import io.javaclasses.filehub.storage.tokenStorage.TokenStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenStorage should: ")
public class TokenStorageTest {

    @DisplayName("correctly return existed record by its value.")
    @Test
    public void storageContainsTokenTest() {

        TokenStorage storage = new TokenStorage();
        TokenId id = new TokenId("test");
        TokenRecord record = new TokenRecord(id, new UserId("test"));
        storage.add(record);
        Optional<TokenRecord> optional = storage.get(record.token());
        assertTrue(optional.isPresent(),
                "TokenStorage can not find saved TokenRecord.");
        assertEquals(record, storage.get(id).get(),
                "storage should return exactly the same record as was added.");

    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(TokenStorage.class);
        tester.testAllPublicStaticMethods(TokenStorage.class);
    }

}
