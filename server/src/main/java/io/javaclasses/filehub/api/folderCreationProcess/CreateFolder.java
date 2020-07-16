package io.javaclasses.filehub.api.folderCreationProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Command} for creating new folder.
 */
public class CreateFolder implements Command {

    private final FolderId parentId;
    private final UserId ownerId;

    /**
     * Creates instance of {@link CreateFolder} with set {@link FolderId} and {@link UserId}.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     */
    public CreateFolder(FolderId parentId, UserId ownerId) {

        this.parentId = checkNotNull(parentId);
        this.ownerId = checkNotNull(ownerId);
    }

    public FolderId parentId() {
        return parentId;
    }

    public UserId ownerId() {
        return ownerId;
    }
}
