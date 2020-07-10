package io.javaclasses.fileHub.api.getFolderContentView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getFolderContentView.FolderContent;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

@DisplayName("FolderContent should: ")
public class FolderContentTest {

    @DisplayName("not accept null parameters.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(FileSystemItemId.class, new FileSystemItemId(""));
        tester.setDefault(LoggedInUserRecord.class, new LoggedInUserRecord(new Token(""), new UserId(""),
                LocalDate.now(ServerTimeZone.get()).plus(Period.ofDays(1))));

        tester.testAllPublicConstructors(FolderContent.class);
        tester.testAllPublicStaticMethods(FolderContent.class);
    }

}
