package io.javaclasses.fileHub.api.getFolderContentView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FileDto;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.userStorage.UserId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileDTO should: ")
public class FileDtoTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileId.class, new FileId(""));
        tester.setDefault(FileSystemItemName.class, new FileSystemItemName(""));
        tester.setDefault(FileSize.class, new FileSize(1));
        tester.setDefault(FileMimeType.class, new FileMimeType(""));
        tester.setDefault(UserId.class, new UserId(""));

        tester.testAllPublicConstructors(FileDto.class);
        tester.testAllPublicStaticMethods(FileDto.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FileDto.class).verify();
    }
}
