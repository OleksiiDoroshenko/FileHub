package io.javaclasses.filehub.api.folderCreationProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Command} for creating new {@link FolderRecord}.
 */
public class CreateFolder implements Command {

    private final FileSystemItemId parentId;
    private final UserId ownerId;

    /**
     * Returns instance of {@link CreateFolder} class with set {@link FileSystemItemId} and {@link UserId}.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     */
    public CreateFolder(FileSystemItemId parentId, UserId ownerId) {

        this.parentId = checkNotNull(parentId);
        this.ownerId = checkNotNull(ownerId);
    }

    public FileSystemItemId parentId() {
        return parentId;
    }

    public UserId ownerId() {
        return ownerId;
    }
}
