package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.storage.folderStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.ServerTimeZone;
import io.javaclasses.filehub.web.routes.GetRootFolderRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("GetRootFolderRoute should: ")
public class GetRootFolderRouteTest {

    @DisplayName("process valid requests.")
    @Test
    public void validRequestsTest() {

        UserStorage userStorage = new UserStorage();
        ThreadLocal<LoggedInUserRecord> loggedInUser = new ThreadLocal<>();

        FolderId rootId = new FolderId("Test");

        LoggedInUserRecord userRecord = createAndAddLoggedInUser(userStorage, rootId);
        loggedInUser.set(userRecord);

        GetRootFolderRoute route = new GetRootFolderRoute(userStorage, loggedInUser);

        Request request = createMockRequest();
        Response response = createMockResponse();

        try {

            Object handle = route.handle(request, response);
            assertNotNull(handle, "Handle method should return not null value.");
            assertEquals(SC_OK, response.status(),
                    "Handle method does not throws exception when request is valid.");

        } catch (Exception exception) {

            fail("GetRootFolderRoute should not throw any exceptions. Exception: " + exception.getClass());
        }
    }


    @DisplayName("return error responses when requests are invalid.")
    @Test
    public void invalidRequestsTest() {

        UserStorage userStorage = new UserStorage();
        ThreadLocal<LoggedInUserRecord> loggedInUser = new ThreadLocal<>();

        LoggedInUserRecord userRecord = createLoggedInUserRecord();
        loggedInUser.set(userRecord);

        GetRootFolderRoute route = new GetRootFolderRoute(userStorage, loggedInUser);

        Request request = createMockRequest();
        Response response = createMockResponse();

        try {

            route.handle(request, response);
            assertEquals(SC_CONFLICT, response.status(),
                    "Handle method does not throws exception when " +
                            "request is invalid but return specific status.");

        } catch (Exception exception) {

            fail("GetRootFolderRoute should not throw any exceptions. Exception: " + exception.getClass());
        }
    }

    private LoggedInUserRecord createAndAddLoggedInUser(UserStorage storage, FolderId rootId) {
        UserId userId = new UserId("Test");
        LoggedInUserRecord loggedInUser = new LoggedInUserRecord(new Token("test"),
                userId, LocalDate.now(ServerTimeZone.get()));
        storage.add(new UserRecord(userId, new LoginName("Test"), "test", rootId));
        return loggedInUser;
    }

    private LoggedInUserRecord createLoggedInUserRecord() {
        return new LoggedInUserRecord(new Token(""), new UserId(""), LocalDate.now(ServerTimeZone.get()));
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(UserStorage.class, new UserStorage());
        tester.setDefault(ThreadLocal.class, new ThreadLocal<LoggedInUserRecord>());

        tester.testAllPublicConstructors(GetRootFolderRoute.class);
        tester.testAllPublicStaticMethods(GetRootFolderRoute.class);
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

    private Request createMockRequest() {
        return new Request() {

            @Override
            public String pathInfo() {
                return "super.pathInfo()";
            }

            @Override
            public String body() {
                return "super.body()";
            }
        };
    }
}
