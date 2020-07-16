package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.routes.LogInRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import spark.Request;
import spark.Response;

import java.util.stream.Stream;

import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("LogInRoute should: ")
public class LogInRouteTest {

    @DisplayName("process valid requests.")
    @Test
    public void validRequestsTest() {
        UserStorage userStorage = new UserStorage();
        LoggedInUsersStorage loggedInUsersStorage = new LoggedInUsersStorage();
        LogInRoute route = new LogInRoute(userStorage, loggedInUsersStorage);

        String loginName = "test";
        String password = "test123456";

        String validRequestBody = format("{\"login\":\"%s\", \"password\":\"%s\"}", loginName, password);


        UserRecord record = createValidUserRecord(loginName, password);
        userStorage.add(record);

        Request request = createMockRequest(validRequestBody);
        Response response = createMockResponse();

        try {

            String token = String.valueOf(route.handle(request, response));

            assertEquals(SC_ACCEPTED, response.status(),
                    "Handle method throws exception when request is valid.");
            assertNotNull(token,
                    "Logging in process does not return token after processing valid request, but it should.");
        } catch (Exception exception) {

            fail("LogInRoute should not throw any exceptions. Exception message " + exception.getMessage());
        }
    }

    private UserRecord createValidUserRecord(String loginName, String password) {

        String passwordHash = PasswordHasher.getHash(new Password(password));
        FolderId rootFolder = new FolderId("test");

        return new UserRecord(new UserId("test"), new LoginName(loginName), passwordHash, rootFolder);
    }

    private static Stream<Arguments> invalidRequestBody() {

        return Stream.of(
                Arguments.of("{\"log\":\"test\", \"password\":\"test123456\"}"),
                Arguments.of("<p></p>"),
                Arguments.of("Hello")
        );
    }


    @DisplayName("return error responses when requests are invalid.")
    @ParameterizedTest
    @MethodSource("invalidRequestBody")
    public void invalidRequestsTest(String body) {
        UserStorage userStorage = new UserStorage();
        LoggedInUsersStorage loggedInUsersStorage = new LoggedInUsersStorage();
        LogInRoute route = new LogInRoute(userStorage, loggedInUsersStorage);

        Request request = createMockRequest(body);
        Response response = createMockResponse();

        try {

            route.handle(request, response);
            assertEquals(SC_BAD_REQUEST, response.status(),
                    "Handle method does not throws exception when request is invalid.");
        } catch (Exception exception) {

            fail("LogInRoute should not throw any exceptions.");
        }
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(UserStorage.class, new UserStorage());
        tester.setDefault(LoggedInUsersStorage.class, new LoggedInUsersStorage());

        tester.testAllPublicConstructors(LogInRoute.class);
        tester.testAllPublicStaticMethods(LogInRoute.class);
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

    private Request createMockRequest(String body) {
        return new Request() {
            @Override
            public String body() {
                return body;
            }
        };
    }


}
