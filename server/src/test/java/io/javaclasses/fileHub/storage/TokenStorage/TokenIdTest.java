package io.javaclasses.fileHub.storage.TokenStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.tokenStorage.TokenId;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TokenId should: ")
public class TokenIdTest {

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(TokenId.class);
        tester.testAllPublicStaticMethods(TokenId.class);
    }
}
