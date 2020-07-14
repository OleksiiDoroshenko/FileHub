package io.javaclasses.fileHub.web;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.AuthenticationFilter;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("AuthenticationFilter should: ")
public class AuthenticationFilterTest {

    @DisplayName("process valid requests.")
    @Test
    public void validRequestTest() {

        LoggedInUsersStorage loggedInUsersStorage = new LoggedInUsersStorage();
        ThreadLocal<LoggedInUserRecord> loggedInUser = new ThreadLocal<>();
        LoggedInUserRecord record = createAndAddLoggedInUser(loggedInUsersStorage);
        loggedInUser.set(record);

        Request request = createMockRequest(record.id().value());
        Response response = createMockResponse();

        AuthenticationFilter filter = new AuthenticationFilter(loggedInUsersStorage);

        try {
            filter.handle(request, response);
        } catch (Exception e) {
            fail(format("Filter should not throw any exceptions. Exception: %s.", e.getClass()));
        }

    }

    @DisplayName("halt response if request is invalid.")
    @Test
    public void invalidRequestTest() {

        LoggedInUsersStorage loggedInUsersStorage = new LoggedInUsersStorage();
        ThreadLocal<LoggedInUserRecord> loggedInUser = new ThreadLocal<>();
        LoggedInUserRecord record = createLoggedInUserRecord();
        loggedInUser.set(record);

        Request request = createMockRequest(record.id().value());
        Response response = createMockResponse();

        AuthenticationFilter filter = new AuthenticationFilter(loggedInUsersStorage);

        try {
            assertThrows(HaltException.class, () -> {
                filter.handle(request, response);
            }, "Does not throw halt exception when request is invalid, but should.");
        } catch (Exception e) {
            fail(format("Filter should not throw any exceptions. Exception: %s.", e.getClass()));
        }

    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(ThreadLocal.class, new ThreadLocal<LoggedInUserRecord>());
        tester.setDefault(LoggedInUsersStorage.class, new LoggedInUsersStorage());

        tester.testAllPublicConstructors(AuthenticationFilter.class);
        tester.testAllPublicStaticMethods(AuthenticationFilter.class);
    }

    private LoggedInUserRecord createAndAddLoggedInUser(LoggedInUsersStorage storage) {
        UserId userId = new UserId("Test");
        LoggedInUserRecord loggedInUser = new LoggedInUserRecord(new Token("Test"),
                userId, LocalDate.now(ServerTimeZone.get()));
        storage.add(loggedInUser);
        return loggedInUser;
    }

    private LoggedInUserRecord createLoggedInUserRecord() {
        return new LoggedInUserRecord(new Token(""), new UserId(""), LocalDate.now(ServerTimeZone.get()));
    }

    private Request createMockRequest(String value) {
        return new Request() {

            @Override
            public String headers(String header) {
                return value;
            }
        };
    }

    private Response createMockResponse() {
        return new Response() {
            int status;
            String responseBody;

            @Override
            public void type(String contentType) {
            }

            @Override
            public void status(int statusCode) {
                status = statusCode;
            }

            @Override
            public int status() {
                return status;
            }

            public String responseBody() {
                return responseBody;
            }
        };
    }
}
