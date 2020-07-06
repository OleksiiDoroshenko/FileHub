package io.javaclasses.fileHub.storage.TokenStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.tokenStorage.LoggedIdUserRecord;
import io.javaclasses.filehub.storage.tokenStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@DisplayName("TokenValue should: ")
public class LoggedInUserRecordTest {

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(Token.class, new Token(""));
        tester.setDefault(UserId.class, new UserId(""));
        tester.setDefault(LocalDate.class, LocalDate.now(ServerTimeZone.get()));

        tester.testAllPublicConstructors(LoggedIdUserRecord.class);
        tester.testAllPublicStaticMethods(LoggedIdUserRecord.class);
    }
}
