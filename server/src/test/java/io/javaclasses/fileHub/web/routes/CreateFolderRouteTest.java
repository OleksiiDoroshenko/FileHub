package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.ServerTimeZone;
import io.javaclasses.filehub.web.routes.CreateFolderRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.Period;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@DisplayName("CreateFolderRoute should: ")
public class CreateFolderRouteTest {

    @DisplayName("return correct success response.")
    @Test
    public void testRouteCorrectSuccessResponse() {
        FolderStorage storage = createFolderStorage();
        FolderId parentId = generateParentId(storage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);
        prepareStorage(storage, parentId, record.userId());

        CreateFolderRoute route = createRoute(storage);

        Request request = createMockRequestWith(parentId);
        Response response = createMockResponse();

        try {
            Object result = route.handle(request, response);

            assertEquals(200, response.status(), "Route should return response with status \"200\" " +
                    "when correct request was processed.");
            assertNotNull(result, "The result of the Route work should not be null.");

        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }


    @DisplayName("return \"404\" error response.")
    @Test
    public void testRouteFolderNotFoundResponse() {
        FolderStorage storage = createFolderStorage();
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);

        CreateFolderRoute route = createRoute(storage);

        Request request = createMockRequest();
        Response response = createMockResponse();

        try {
            route.handle(request, response);

            assertEquals(404, response.status(), "Route should return response with status \"404\" " +
                    "when provided in request parent folder was not found.");

        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    @DisplayName("return \"401\" error response.")
    @Test
    public void testRouteUserNotLoggedInExceptionResponse() {

        CurrentUser.clear();

        FolderStorage storage = createFolderStorage();
        CreateFolderRoute route = createRoute(storage);
        FolderId parentId = generateParentId(storage);

        UserId unLoggedInUserId = generateUserId();

        prepareStorage(storage, parentId, unLoggedInUserId);

        Request request = createMockRequestWith(parentId);
        Response response = createMockResponse();

        try {
            route.handle(request, response);

            assertEquals(401, response.status(), "Route should return response with status \"401\"" +
                    " if there is no current logged in user.");
        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    @DisplayName("return \"409\" error response.")
    @Test
    public void testRouteUserNotOwnerResponse() {
        FolderStorage storage = createFolderStorage();
        LoggedInUserRecord record = createLoggedInUser();
        FolderId parentId = generateParentId(storage);
        UserId ownerId = generateUserId();

        prepareCurrentUser(record);
        prepareStorage(storage, parentId, ownerId);

        CreateFolderRoute route = createRoute(storage);

        Request request = createMockRequestWith(parentId);
        Response response = createMockResponse();

        try {
            route.handle(request, response);

            assertEquals(409, response.status(), "Route should return response with status \"409\"" +
                    " when logged in user is not the owner of the provided parent folder.");
        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(FolderStorage.class, new FolderStorage());

        tester.testAllPublicConstructors(CreateFolderRoute.class);
        tester.testAllPublicStaticMethods(CreateFolderRoute.class);
    }

    private UserId generateUserId() {
        return new UserId("sdjfjsd");
    }

    private void prepareStorage(FolderStorage storage, FolderId id, UserId ownerId) {

        FileSystemItemName name = new FileSystemItemName("");
        FolderRecord record = new FolderRecord(id, name, null, ownerId);

        storage.add(record);
    }

    private LoggedInUserRecord createLoggedInUser() {

        Token token = new Token("fdaasdasdasdas");
        UserId id = new UserId("adasd");
        LocalDate expireDate = LocalDate.now(ServerTimeZone.get()).plus(Period.ofDays(1));
        return new LoggedInUserRecord(token, id, expireDate);
    }

    private void prepareCurrentUser(LoggedInUserRecord loggedInUser) {
        CurrentUser.set(loggedInUser);
    }

    private FolderId generateParentId(FolderStorage storage) {
        return new FolderId(storage.generateId());
    }

    private FolderStorage createFolderStorage() {
        return new FolderStorage();
    }

    private CreateFolderRoute createRoute(FolderStorage storage) {
        return new CreateFolderRoute(storage);
    }

    private Request createMockRequestWith(FolderId parentId) {
        return new Request() {

            @Override
            public String params(String param) {
                return parentId.value();
            }

            @Override
            public String pathInfo() {
                return "/folder/:id/folder";
            }

            @Override
            public String body() {
                return "tests";
            }
        };
    }

    private Response createMockResponse() {
        return new Response() {
            int status;

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

        };
    }

    private Request createMockRequest() {
        return new Request() {

            @Override
            public String params(String param) {
                return "daksdlkjaslkd";
            }

            @Override
            public String pathInfo() {
                return "/folder/:id/folder";
            }

            @Override
            public String body() {
                return "tests";
            }
        };
    }
}
