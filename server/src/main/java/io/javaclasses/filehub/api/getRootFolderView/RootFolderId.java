package io.javaclasses.filehub.api.getRootFolderView;

import io.javaclasses.filehub.api.Query;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Query} for getting user root folder id.
 */
public class RootFolderId implements Query {

    private final LoggedInUserRecord loggedInUser;

    /**
     * Returns instance of {@link RootFolderId} class.
     *
     * @param loggedInUser logged in user.
     */
    public RootFolderId(LoggedInUserRecord loggedInUser) {
        this.loggedInUser = checkNotNull(loggedInUser);
    }

    public LoggedInUserRecord loggedInUser() {
        return loggedInUser;
    }
}
