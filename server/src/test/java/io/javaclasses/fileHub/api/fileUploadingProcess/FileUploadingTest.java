package io.javaclasses.fileHub.api.fileUploadingProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.fileUploadingProcess.File;
import io.javaclasses.filehub.api.fileUploadingProcess.FileUploading;
import io.javaclasses.filehub.api.fileUploadingProcess.UploadFile;
import io.javaclasses.filehub.api.folderCreationProcess.UserNotOwnerException;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
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

@DisplayName("FileUploading process should: ")
public class FileUploadingTest {

    @DisplayName("upload file.")
    @Test
    public void testUploadingFile() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        LoggedInUserRecord record = createLoggedInUser();
        FileSystemItemId parentId = createParentId(folderStorage);
        File uploadedFile = createFile();

        prepareCurrentUser(record);
        prepareFolderStorage(folderStorage, parentId, record.userId());

        UploadFile command = createCommand(parentId, record.userId(), uploadedFile);
        FileUploading process = createProcess(fileDataStorage, fileStorage, folderStorage);

        try {
            FileSystemItemId createdFolderId = process.handle(command);

            assertNotNull(fileStorage.get(createdFolderId),
                    "\"FileUploading\" process does add file record to \"FileStorage\", but should.");
            assertNotNull(fileDataStorage.get(createdFolderId),
                    "\"FileUploading\" process does add file data record to \"FileDataStorage\", but should.");

        } catch (Exception e) {
            fail(format("FileUploading process should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }
    }

    @DisplayName("throw exception if required parent folder is not found.")
    @Test
    public void testProcessThrowsFolderNotFoundException() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        LoggedInUserRecord record = createLoggedInUser();
        FileSystemItemId parentId = createParentId(folderStorage);
        File uploadedFile = createFile();

        prepareCurrentUser(record);

        UploadFile command = createCommand(parentId, record.userId(), uploadedFile);
        FileUploading process = createProcess(fileDataStorage, fileStorage, folderStorage);

        try {

            assertThrows(FolderNotFoundException.class, () -> process.handle(command),
                    "\"FileUploading\" process does not throw \"FolderNotFoundException\" " +
                            "when parent folder is not found.");

        } catch (Exception e) {

            fail(format("\"FileUploading\" process should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }

    }

    @DisplayName("throw exception if set user is not the owner of required parent folder.")
    @Test
    public void testProcessThrowsUserNotOwnerException() {

        FileDataStorage fileDataStorage = createFileDataStorage();
        FileStorage fileStorage = createFileStorage();
        FolderStorage folderStorage = createFolderStorage();
        LoggedInUserRecord record = createLoggedInUser();
        FileSystemItemId parentId = createParentId(folderStorage);
        File uploadedFile = createFile();
        UserId ownerId = createUserId();

        prepareCurrentUser(record);
        prepareFolderStorage(folderStorage, parentId, ownerId);

        UploadFile command = createCommand(parentId, record.userId(), uploadedFile);
        FileUploading process = createProcess(fileDataStorage, fileStorage, folderStorage);

        try {

            assertThrows(UserNotOwnerException.class, () -> process.handle(command),
                    "\"FileUploading\" does not throw \"UserNotOwnerException\"" +
                            " when set user is not the owner of required parent folder.");

        } catch (Exception e) {

            fail(format("\"FileUploading\" should not throw any exception. Exception %s with message %s.",
                    e.getClass(), e.getMessage()));
        }

    }

    private UserId createUserId() {
        return new UserId("sdjfjsd");
    }

    private FileUploading createProcess(FileDataStorage fileDataStorage, FileStorage fileStorage,
                                        FolderStorage folderStorage) {
        return new FileUploading(fileDataStorage, fileStorage, folderStorage);
    }

    private UploadFile createCommand(FileSystemItemId parentId, UserId ownerId, File uploadedFile) {
        return new UploadFile(parentId, ownerId, uploadedFile);
    }

    private void prepareFolderStorage(FolderStorage storage, FileSystemItemId id, UserId ownerId) {

        FileSystemItemName name = new FileSystemItemName("");
        FolderRecord record = new FolderRecord(id, name, null, ownerId);

        storage.add(record);
    }

    private File createFile() {

        byte[] data = new byte[0];
        FileSystemItemName name = new FileSystemItemName("");
        FileMimeType mimeType = new FileMimeType("");
        FileSize size = new FileSize(0);

        return new File(data, name, mimeType, size);
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

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileDataStorage.class, new FileDataStorage());
        tester.setDefault(FileStorage.class, new FileStorage());
        tester.setDefault(FolderStorage.class, new FolderStorage());

        tester.testConstructors(FileUploading.class, NullPointerTester.Visibility.PUBLIC);
        tester.testAllPublicConstructors(FileUploading.class);
        tester.testAllPublicStaticMethods(FileUploading.class);
    }
}
