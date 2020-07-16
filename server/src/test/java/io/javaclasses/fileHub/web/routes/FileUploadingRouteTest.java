package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.ServerTimeZone;
import io.javaclasses.filehub.web.routes.FileUploadingRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("FileUploadingRoute should: ")
public class FileUploadingRouteTest {

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileStorage.class, new FileStorage());
        tester.setDefault(FolderStorage.class, new FolderStorage());
        tester.setDefault(FileDataStorage.class, new FileDataStorage());

        tester.testAllPublicConstructors(FileUploadingRoute.class);
        tester.testAllPublicStaticMethods(FileUploadingRoute.class);
    }

    @DisplayName("return correct success response.")
    @Test
    public void testRouteCorrectSuccessResponse() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        FileSystemItemId parentId = createParentId(folderStorage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);
        prepareFolderStorage(folderStorage, parentId, record.userId());

        FileUploadingRoute route = createRoute(fileDataStorage, fileStorage, folderStorage);

        Request request = createMockRequestWith(parentId);
        Response response = createMockResponse();

        try {
            route.handle(request, response);

            assertEquals(200, response.status(), "Route should return response with status \"200\" " +
                    "when correct request was processed.");

        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    @DisplayName("return \"404\" error response.")
    @Test
    public void testRouteFolderNotFoundResponse() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        FileSystemItemId parentId = createParentId(folderStorage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);

        FileUploadingRoute route = createRoute(fileDataStorage, fileStorage, folderStorage);

        Request request = createMockRequestWith(parentId);
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

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        FileSystemItemId parentId = createParentId(folderStorage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareFolderStorage(folderStorage, parentId, record.userId());

        FileUploadingRoute route = createRoute(fileDataStorage, fileStorage, folderStorage);

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

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        FileSystemItemId parentId = createParentId(folderStorage);
        LoggedInUserRecord record = createLoggedInUser();
        UserId ownerId = createUserId();

        prepareCurrentUser(record);
        prepareFolderStorage(folderStorage, parentId, ownerId);

        FileUploadingRoute route = createRoute(fileDataStorage, fileStorage, folderStorage);

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

    @DisplayName("return \"400\" error response.")
    @Test
    public void testRouteBadRequestResponse() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        FileSystemItemId parentId = createParentId(folderStorage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);
        prepareFolderStorage(folderStorage, parentId, record.userId());

        FileUploadingRoute route = createRoute(fileDataStorage, fileStorage, folderStorage);

        Request request = createInvalidMockRequestWith(parentId);
        Response response = createMockResponse();

        try {
            route.handle(request, response);

            assertEquals(400, response.status(), "Route should return response with status \"400\"" +
                    " when provided in request file is invalid.");
        } catch (Exception e) {

            fail(format("CreateFolderRoute should not throw any exceptions. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    private UserId createUserId() {
        return new UserId("kasjjldjalskdjlaksjdl");
    }


    private Request createInvalidMockRequestWith(FileSystemItemId parentId) {
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

            @Override
            public HttpServletRequest raw() {
                return createInvalidMockServletRequest();
            }

            private HttpServletRequest createInvalidMockServletRequest() {
                return new InvalidMockHttpServletRequest();
            }
        };
    }

    private Request createMockRequestWith(FileSystemItemId parentId) {
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

            @Override
            public HttpServletRequest raw() {
                return createMockServletRequest();
            }

        };
    }

    private HttpServletRequest createMockServletRequest() {
        return new MockHttpServletRequest();
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

    private FileUploadingRoute createRoute(FileDataStorage fileDataStorage, FileStorage fileStorage,
                                           FolderStorage folderStorage) {
        return new FileUploadingRoute(fileStorage, fileDataStorage, folderStorage);
    }

    private void prepareFolderStorage(FolderStorage storage, FileSystemItemId id, UserId ownerId) {

        FileSystemItemName name = new FileSystemItemName("");
        FolderRecord record = new FolderRecord(id, name, null, ownerId);

        storage.add(record);
    }


    private FileSystemItemId createParentId(FolderStorage storage) {
        return new FileSystemItemId(storage.generateId());
    }

    private void prepareCurrentUser(LoggedInUserRecord loggedInUser) {
        CurrentUser.set(loggedInUser);
    }

    private LoggedInUserRecord createLoggedInUser() {

        Token token = new Token("fdaasdasdasdas");
        UserId id = new UserId("adasd");
        LocalDate expireDate = LocalDate.now(ServerTimeZone.get()).plus(Period.ofDays(1));

        return new LoggedInUserRecord(token, id, expireDate);
    }

    private FolderStorage createFolderStorage() {
        return new FolderStorage();
    }

    private FileStorage createFileStorage() {
        return new FileStorage();
    }

    private FileDataStorage createFileDataStorage() {
        return new FileDataStorage();
    }
}
