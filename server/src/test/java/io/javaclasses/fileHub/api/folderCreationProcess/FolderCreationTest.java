package io.javaclasses.fileHub.api.folderCreationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.folderCreationProcess.CreateFolder;
import io.javaclasses.filehub.api.folderCreationProcess.FolderCreation;
import io.javaclasses.filehub.api.folderCreationProcess.UserNotOwnerException;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.FolderNotFoundException;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static java.lang.String.format;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("FolderCreation process should: ")
public class FolderCreationTest {

    @DisplayName("create new folder.")
    @Test
    public void testNewFolderCreation() {

        FolderStorage storage = createFolderStorage();
        FileSystemItemId parentId = generateParentId(storage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);
        prepareStorage(storage, parentId, record.userId());

        CreateFolder command = createCommand(parentId, record.userId());
        FolderCreation process = createProcess(storage);

        try {
            FileSystemItemId createdFolderId = process.handle(command);

            assertNotNull(storage.get(createdFolderId), "CreationProcess does not create new folder.");

        } catch (Exception e) {
            fail(format("CreationProcess should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }

    }

    @DisplayName("throw exception if required parent folder is not found.")
    @Test
    public void testProcessThrowsFolderNotFoundException() {

        FolderStorage storage = createFolderStorage();
        FileSystemItemId parentId = generateParentId(storage);
        LoggedInUserRecord record = createLoggedInUser();

        prepareCurrentUser(record);

        CreateFolder command = createCommand(parentId, record.userId());
        FolderCreation process = createProcess(storage);

        try {

            assertThrows(FolderNotFoundException.class, () -> process.handle(command),
                    "CreationProcess does not throw \"FolderNotFoundException\" " +
                            "when parent folder is not found.");

        } catch (Exception e) {

            fail(format("CreationProcess should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }

    }

    @DisplayName("throw exception if set user is not the owner of required parent folder.")
    @Test
    public void testProcessThrowsUserNotOwnerException() {

        FolderStorage storage = createFolderStorage();
        FileSystemItemId parentId = generateParentId(storage);
        LoggedInUserRecord record = createLoggedInUser();
        UserId ownerId = generateUserId();

        prepareStorage(storage, parentId, ownerId);
        prepareCurrentUser(record);

        CreateFolder command = createCommand(parentId, record.userId());
        FolderCreation process = createProcess(storage);

        try {

            assertThrows(UserNotOwnerException.class, () -> process.handle(command),
                    "CreationProcess does not throw \"UserNotOwnerException\"" +
                            " when set user is not the owner of required parent folder.");

        } catch (Exception e) {

            fail(format("CreationProcess should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }

    }

    private UserId generateUserId() {
        return new UserId("sdjfjsd");
    }

    private void prepareStorage(FolderStorage storage, FileSystemItemId id, UserId ownerId) {

        FileSystemItemName name = new FileSystemItemName("");
        FolderRecord record = new FolderRecord(id, name, null, ownerId);

        storage.add(record);
    }

    private FolderCreation createProcess(FolderStorage storage) {
        return new FolderCreation(storage);
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

    private FileSystemItemId generateParentId(FolderStorage storage) {
        return new FileSystemItemId(storage.generateId());
    }

    private CreateFolder createCommand(FileSystemItemId parentId, UserId ownerId) {
        return new CreateFolder(parentId, ownerId);
    }

    private FolderStorage createFolderStorage() {
        return new FolderStorage();
    }

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FolderStorage.class, new FolderStorage());

        tester.testAllPublicConstructors(FolderCreation.class);
        tester.testAllPublicStaticMethods(FolderCreation.class);
    }
}
