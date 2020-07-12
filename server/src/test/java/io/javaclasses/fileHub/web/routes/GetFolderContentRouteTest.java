package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.ServerTimeZone;
import io.javaclasses.filehub.web.routes.GetFolderContentRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("GetFolderContentRoute should: ")
public class GetFolderContentRouteTest {

    @DisplayName("process request with an existing folder.")
    @Test
    public void validRequestsTest() {
        LoggedInUserRecord userRecord = createLoggedInUserRecord();
        CurrentUser.set(userRecord);

        FolderStorage folderStorage = createFolderStorage();
        FileStorage fileStorage = createFileStorage();
        int numberOfElements = 3;
        FileSystemItemId parentId = createParentId();
        UserId ownerId = createOwnerId();
        prepareStorage(fileStorage, folderStorage, numberOfElements, parentId, ownerId);

        GetFolderContentRoute route = new GetFolderContentRoute(folderStorage, fileStorage);

        Request request = createMockRequest();
        Response response = createMockResponse();

        try {

            Object result = route.handle(request, response);

            assertNotNull(result, "Handle method should return not null result.");
            assertEquals(SC_OK, response.status(),
                    "Handle method should return status 200 when request is valid.");

        } catch (Exception exception) {

            fail("GetFolderContentRoute should not throw any exceptions. Exception: " + exception.getClass());
        }
    }

    @DisplayName("return error responses when requested folder is not found.")
    @Test
    public void invalidRequestsTest() {

        LoggedInUserRecord userRecord = createLoggedInUserRecord();
        CurrentUser.set(userRecord);

        FolderStorage folderStorage = createFolderStorage();
        FileStorage fileStorage = createFileStorage();

        GetFolderContentRoute route = new GetFolderContentRoute(folderStorage, fileStorage);

        Request request = createMockRequest();
        Response response = createMockResponse();

        try {

            route.handle(request, response);
            assertEquals(SC_NOT_FOUND, response.status(),
                    "Handle method should return status 404 when requested folder foes not exist.");

        } catch (Exception exception) {

            fail("GetRootFolderRoute should not throw any exceptions. Exception: " + exception.getClass());
        }
    }

    private LoggedInUserRecord createLoggedInUserRecord() {
        return new LoggedInUserRecord(new Token(""), new UserId(""), LocalDate.now(ServerTimeZone.get()));
    }

    private UserId createOwnerId() {
        return new UserId("test");
    }

    private FileSystemItemId createParentId() {
        return new FileSystemItemId("id");
    }

    private void prepareStorage(FileStorage fileStorage, FolderStorage folderStorage,
                                int numberOfElements, FileSystemItemId parentId, UserId ownerId) {
        folderStorage.add(createRootFolder(parentId, ownerId));

        for (int i = 0; i < numberOfElements; i++) {
            fileStorage.add(createFileRecord(parentId, ownerId, fileStorage));
            folderStorage.add(createFolderRecord(parentId, ownerId, folderStorage));
        }
    }

    private FolderRecord createRootFolder(FileSystemItemId id, UserId ownerId) {
        FileSystemItemName name = new FileSystemItemName("root");
        return new FolderRecord(id, name, null, ownerId);
    }

    private FolderRecord createFolderRecord(FileSystemItemId parentId, UserId ownerId, FolderStorage storage) {
        FileSystemItemId id = new FileSystemItemId(storage.generateId());
        FileSystemItemName name = new FileSystemItemName("");
        return new FolderRecord(id, name, parentId, ownerId);
    }

    private FileRecord createFileRecord(FileSystemItemId parentId, UserId ownerId, FileStorage storage) {
        FileSystemItemId id = new FileSystemItemId(storage.generateId());
        FileSystemItemName name = new FileSystemItemName("");
        FileSize size = new FileSize(0);
        FileMimeType mimeType = new FileMimeType("");
        return new FileRecord(id, name, parentId, size, mimeType, ownerId);
    }

    private FileStorage createFileStorage() {
        return new FileStorage();
    }

    private FolderStorage createFolderStorage() {
        return new FolderStorage();
    }

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(FileStorage.class, new FileStorage());
        tester.setDefault(FolderStorage.class, new FolderStorage());

        tester.testAllPublicConstructors(GetFolderContentRoute.class);
        tester.testAllPublicStaticMethods(GetFolderContentRoute.class);
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
                return "/folder/id/content";
            }
        };
    }
}
