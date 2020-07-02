package io.javaclasses.fileHub.storage.TokenStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.tokenStorage.TokenId;
import io.javaclasses.filehub.storage.tokenStorage.TokenRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("TokenRecord should: ")
public class TokenTest {

    @DisplayName("generate internal token.")
    @Test
    public void hashingUserPasswordTest() {

        TokenId id = new TokenId("test");
        UserId userId = new UserId("test");
        TokenRecord record = new TokenRecord(id, userId);

        assertNotNull(record.token(), "TokenRecord does not generate internal token, but should.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(TokenId.class, new TokenId("test"));
        tester.setDefault(UserId.class, new UserId("Test"));


        tester.testAllPublicConstructors(TokenRecord.class);
        tester.testAllPublicStaticMethods(TokenRecord.class);
    }
}
