package io.javaclasses.fileHub.api.folderCreationProcess;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.folderCreationProcess.CreateFolder;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.userStorage.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CreateFolder command should: ")
public class CreateFolderTest {

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FolderId.class, new FolderId(""));
        tester.setDefault(UserId.class, new UserId(""));

        tester.testAllPublicConstructors(CreateFolder.class);
        tester.testAllPublicStaticMethods(CreateFolder.class);
    }
}
