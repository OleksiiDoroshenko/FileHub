package io.javaclasses.fileHub.api.getFolderContentView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FolderContentDto;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FolderContentDTO should: ")
public class FolderContentDtoTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicConstructors(FolderContentDto.class);
        tester.testAllPublicStaticMethods(FolderContentDto.class);
    }

    @DisplayName("correctly process equals contract.")
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FolderContentDto.class).verify();
    }
}
