package io.javaclasses.fileHub.api.getFolderContentView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FolderDto;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.userStorage.UserId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FolderDTO should: ")
public class FolderDtoTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileSystemItemId.class, new FileSystemItemId(""));
        tester.setDefault(FileSystemItemName.class, new FileSystemItemName(""));
        tester.setDefault(UserId.class, new UserId(""));

        tester.testAllPublicConstructors(FolderDto.class);
        tester.testAllPublicStaticMethods(FolderDto.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FolderDto.class).verify();
    }
}
