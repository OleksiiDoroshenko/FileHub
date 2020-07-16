package io.javaclasses.fileHub.storage.fileSystemItemsStorage;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileDataRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileDataRecord should: ")
public class FileDataRecordTest {

    @DisplayName("not except null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileSystemItemId.class, new FileSystemItemId(""));

        tester.testAllPublicConstructors(FileDataRecord.class);
        tester.testAllPublicStaticMethods(FileDataRecord.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.simple().forClass(FileDataRecord.class).verify();
    }
}
