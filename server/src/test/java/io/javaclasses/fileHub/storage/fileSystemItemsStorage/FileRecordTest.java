package io.javaclasses.fileHub.storage.fileSystemItemsStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.api.getFolderContentView.FileType;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileRecord should: ")
public class FileRecordTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(FileSystemItemId.class, new FileSystemItemId(""));
        tester.setDefault(FileSystemItemName.class, new FileSystemItemName(""));
        tester.setDefault(FileSize.class, new FileSize(0));
        tester.setDefault(FileMimeType.class, new FileMimeType(""));
        tester.setDefault(FileType.class, new FileType(""));
        tester.setDefault(UserId.class, new UserId(""));


        tester.testAllPublicConstructors(FileRecord.class);
        tester.testAllPublicStaticMethods(FileRecord.class);
    }
}
