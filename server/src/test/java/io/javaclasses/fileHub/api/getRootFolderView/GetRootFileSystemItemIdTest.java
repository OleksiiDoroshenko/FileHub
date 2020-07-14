package io.javaclasses.fileHub.api.getRootFolderView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getRootFolderView.GetRootFolderId;
import io.javaclasses.filehub.api.getRootFolderView.RootFolderId;
import io.javaclasses.filehub.api.logInProcess.UserNotRegisteredException;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.ServerTimeZone;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


@DisplayName("GetRootFolderId should: ")
public class GetRootFileSystemItemIdTest {

    @DisplayName("successfully process valid query.")
    @Test
    public void validQueryTest() {

        UserStorage storage = new UserStorage();
        GetRootFolderId view = new GetRootFolderId(storage);
        FileSystemItemId rootId = new FileSystemItemId("test");
        LoggedInUserRecord loggedInUser = createAndAddLoggedInUser(storage, rootId);
        RootFolderId query = new RootFolderId(loggedInUser);


        try {
            FileSystemItemId actualId = view.process(query);

            assertEquals(rootId, actualId, "Returns incorrect root folder id.");
        } catch (Exception e) {

            fail("Handle method should not throw any exception when query is correct.");
        }
    }

    private LoggedInUserRecord createAndAddLoggedInUser(UserStorage storage, FileSystemItemId rootId) {
        UserId userId = new UserId("Test");
        LoggedInUserRecord loggedInUser = new LoggedInUserRecord(new Token("test"),
                userId, LocalDate.now(ServerTimeZone.get()));
        storage.add(new UserRecord(userId, new LoginName("Test"), "test", rootId));
        return loggedInUser;
    }

    @DisplayName("throw exception if query is invalid.")
    @Test
    public void invalidQueryTest() {

        UserStorage storage = new UserStorage();
        GetRootFolderId view = new GetRootFolderId(storage);

        LoggedInUserRecord loggedInUser = createLoggedInUser();
        RootFolderId query = new RootFolderId(loggedInUser);

        try {

            assertThrows(UserNotLoggedInException.class, () -> {
                view.process(query);
            }, "Does not throw UserNotRegisteredException when user is not exist in storage, but should.");
        } catch (Exception e) {

            fail("Handle method should not throw any exception when query is correct.");
        }
    }

    private LoggedInUserRecord createLoggedInUser() {
        UserId userId = new UserId("Test");
        return new LoggedInUserRecord(new Token("test"),
                userId, LocalDate.now(ServerTimeZone.get()));
    }

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(UserStorage.class, new UserStorage());

        tester.testAllPublicInstanceMethods(new GetRootFolderId(new UserStorage()));
        tester.testAllPublicConstructors(GetRootFolderId.class);
        tester.testAllPublicStaticMethods(GetRootFolderId.class);
    }
}
