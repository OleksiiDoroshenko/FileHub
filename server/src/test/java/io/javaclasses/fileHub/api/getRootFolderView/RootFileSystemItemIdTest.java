package io.javaclasses.fileHub.api.getRootFolderView;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.api.getRootFolderView.RootFolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@DisplayName("RootFolderId should: ")
public class RootFileSystemItemIdTest {

    @DisplayName("throw exception if constructor parameters are null.")
    @Test
    public void nullPointerTest() {
        NullPointerTester tester = new NullPointerTester();

        tester.setDefault(LoggedInUserRecord.class, createLoggedInUserRecord());

        tester.testAllPublicConstructors(RootFolderId.class);
        tester.testAllPublicStaticMethods(RootFolderId.class);
    }

    private LoggedInUserRecord createLoggedInUserRecord() {
        return new LoggedInUserRecord(new Token(""), new UserId(""), LocalDate.now(ServerTimeZone.get()));
    }

}
