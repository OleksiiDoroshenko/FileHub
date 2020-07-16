package io.javaclasses.filehub.api.fileUploadingProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItem;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.userStorage.UserId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Command} for uploading file.
 */
public class UploadFile implements Command {

    private final FolderId parentId;
    private final UserId ownerId;
    private final File file;

    /**
     * Creates instance of the {@link UploadFile} command with set {@link FileSystemItem} as parent folder identifier
     * for uploaded file, {@link UserId} as owner identifier and {@link File} as uploaded file description.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     * @param file     file information.
     */
    public UploadFile(FolderId parentId, UserId ownerId, File file) {

        this.parentId = checkNotNull(parentId);
        this.ownerId = checkNotNull(ownerId);
        this.file = checkNotNull(file);
    }

    public FolderId parentId() {
        return parentId;
    }

    public UserId ownerId() {
        return ownerId;
    }

    public File file() {
        return file;
    }
}
