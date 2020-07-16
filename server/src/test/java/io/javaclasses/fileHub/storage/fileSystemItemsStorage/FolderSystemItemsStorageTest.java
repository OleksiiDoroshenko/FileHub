package io.javaclasses.fileHub.storage.fileSystemItemsStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DisplayName("FolderStorage should:")
public class FolderSystemItemsStorageTest {

    @DisplayName("return children of the folder")
    @Test
    public void storageGetSubFoldersTest() {
        FolderStorage storage = createStorage();
        FolderId parentId = createFolderId(storage);
        UserId ownerId = createOwnerId();
        prepareStorage(storage, parentId, ownerId);

        List<FolderRecord> children = storage.all(parentId, ownerId);

        assertIterableEquals(storage.all(), children,
                "Storage method getChildren returns incorrect result.");
    }

    private UserId createOwnerId() {
        return new UserId("test");
    }

    private FolderId createFolderId(FolderStorage storage) {
        return new FolderId(storage.generateId());
    }

    private void prepareStorage(FolderStorage storage, FolderId parentId, UserId ownerId) {
        FolderId id;
        FileSystemItemName name;
        for (int i = 0; i < 3; i++) {
            id = new FolderId(storage.generateId());
            name = new FileSystemItemName("");
            storage.add(new FolderRecord(id, name, parentId, ownerId));
        }
    }

    private FolderStorage createStorage() {
        return new FolderStorage();
    }

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(FolderStorage.class);
        tester.testAllPublicStaticMethods(FolderStorage.class);
    }
}
