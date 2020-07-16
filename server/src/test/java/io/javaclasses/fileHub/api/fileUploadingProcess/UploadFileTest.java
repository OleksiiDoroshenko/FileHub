package io.javaclasses.fileHub.api.fileUploadingProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.fileUploadingProcess.File;
import io.javaclasses.filehub.api.fileUploadingProcess.UploadFile;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UploadFile command should: ")
public class UploadFileTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(File.class, new File(
                new byte[0],
                new FileSystemItemName(""),
                new FileMimeType(""),
                new FileSize(1)));

        tester.setDefault(FileSystemItemId.class, new FileSystemItemId(""));
        tester.setDefault(UserId.class, new UserId(""));

        tester.testConstructors(UploadFile.class, NullPointerTester.Visibility.PUBLIC);
        tester.testAllPublicConstructors(UploadFile.class);
        tester.testAllPublicStaticMethods(UploadFile.class);
    }
}
