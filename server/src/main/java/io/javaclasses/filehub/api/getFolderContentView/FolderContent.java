package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.Query;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The intention of the client to get the content of the folder.
 */
@Immutable
public final class FolderContent implements Query {

    private final FolderId folderId;
    private final LoggedInUserRecord loggedInUser;

    /**
     * Creates instance of {@link FolderContent} class.
     *
     * @param folderId     an identifier of the folder whose content should be retrieved.
     * @param loggedInUser folder owner.
     */
    public FolderContent(FolderId folderId, LoggedInUserRecord loggedInUser) {
        this.folderId = checkNotNull(folderId);
        this.loggedInUser = checkNotNull(loggedInUser);
    }

    public FolderId folderId() {
        return folderId;
    }

    public LoggedInUserRecord loggedInUser() {
        return loggedInUser;
    }
}
