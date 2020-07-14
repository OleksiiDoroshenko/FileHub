package io.javaclasses.fileHub.api.getFolderContentView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.*;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("GetFolderContent should: ")
public class GetFolderContentTest {

    @DisplayName("handle valid query.")
    @Test
    public void handleValidQueryTest() {
        FolderStorage folderStorage = createFolderStorage();
        FileStorage fileStorage = createFileStorage();
        int numberOfElements = 3;
        FileSystemItemId parentId = createParentId();
        UserId ownerId = createOwnerId();
        prepareStorage(fileStorage, folderStorage, numberOfElements, parentId, ownerId);

        GetFolderContent view = createView(folderStorage, fileStorage);
        FolderContent query = createQuery(parentId, ownerId);

        try {
            FolderContentDto content = view.process(query);
            assertEquals(numberOfElements, content.files().size(),
                    "GetFolderContent return invalid file list.");
            assertEquals(numberOfElements, content.folders().size(),
                    "GetFolderContent return invalid folder list.");

        } catch (NullPointerException e) {
            fail("GetFolderContent should not throw any exceptions when query is valid.");
        }
    }

    private FolderContent createQuery(FileSystemItemId folderId, UserId ownerId) {
        Token token = new Token("");
        LocalDate expirationDate = LocalDate.now(ServerTimeZone.get()).plus(Period.ofDays(3));
        LoggedInUserRecord loggedInUser = new LoggedInUserRecord(token, ownerId, expirationDate);
        return new FolderContent(folderId, loggedInUser);
    }

    private UserId createOwnerId() {
        return new UserId("test");
    }

    private FileSystemItemId createParentId() {
        return new FileSystemItemId("parent");
    }

    private void prepareStorage(FileStorage fileStorage, FolderStorage folderStorage,
                                int numberOfElements, FileSystemItemId parentId, UserId ownerId) {

        FolderRecord root = new FolderRecord(parentId, new FileSystemItemName(""), null, ownerId);
        folderStorage.add(root);

        for (int i = 0; i < numberOfElements; i++) {
            FileRecord fileRecord = createFileRecord(parentId, ownerId, fileStorage);
            fileStorage.add(fileRecord);

            FolderRecord folderRecord = createFolderRecord(parentId, ownerId, folderStorage);
            folderStorage.add(folderRecord);
        }
    }

    private FolderRecord createFolderRecord(FileSystemItemId parentId, UserId ownerId, FolderStorage storage) {
        FileSystemItemId id = new FileSystemItemId(storage.generateId());
        FileSystemItemName name = new FileSystemItemName("");
        return new FolderRecord(id, name, parentId, ownerId);
    }

    private FileRecord createFileRecord(FileSystemItemId parentId, UserId ownerId, FileStorage storage) {
        FileSystemItemId id = new FileSystemItemId(storage.generateId());
        FileSystemItemName name = new FileSystemItemName("");
        FileSize size = new FileSize(1);
        FileMimeType mimeType = new FileMimeType("");
        return new FileRecord(id, name, parentId, size, mimeType, ownerId);
    }

    private FileStorage createFileStorage() {
        return new FileStorage();
    }

    private FolderStorage createFolderStorage() {
        return new FolderStorage();
    }

    private GetFolderContent createView(FolderStorage folderStorage, FileStorage fileStorage) {
        return new GetFolderContent(folderStorage, fileStorage);
    }

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(GetFolderContent.class);
        tester.testAllPublicStaticMethods(GetFolderContent.class);
    }
}
