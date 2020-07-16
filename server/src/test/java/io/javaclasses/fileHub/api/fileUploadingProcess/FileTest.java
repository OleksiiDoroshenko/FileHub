package io.javaclasses.fileHub.api.fileUploadingProcess;

import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;
import io.javaclasses.filehub.api.fileUploadingProcess.File;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("File should: ")
public class FileTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileSystemItemName.class, new FileSystemItemName(""));
        tester.setDefault(FileSize.class, new FileSize(1));
        tester.setDefault(FileMimeType.class, new FileMimeType(""));

        tester.testConstructors(File.class, Visibility.PUBLIC);
        tester.testAllPublicConstructors(File.class);
        tester.testAllPublicStaticMethods(File.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.simple().forClass(File.class).verify();
    }
}
