package io.javaclasses.fileHub.storage.TokenStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.tokenStorage.TokenValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TokenValue should: ")
public class TokenValueTest {

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(TokenValue.class);
        tester.testAllPublicStaticMethods(TokenValue.class);
    }
}
