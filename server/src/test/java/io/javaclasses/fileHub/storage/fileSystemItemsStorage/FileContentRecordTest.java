package io.javaclasses.fileHub.storage.fileSystemItemsStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.fileUploadingProcess.FileContent;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileContentRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileDataRecord should: ")
public class FileContentRecordTest {

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileId.class, new FileId(""));
        tester.setDefault(FileContent.class, new FileContent(new byte[0]));

        tester.testAllPublicConstructors(FileContentRecord.class);
        tester.testAllPublicStaticMethods(FileContentRecord.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.simple().forClass(FileContentRecord.class).verify();
    }
}
