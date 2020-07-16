package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.userStorage.UserId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving file in the {@link FileStorage}.
 */
@Immutable
public final class FileRecord implements FileSystemItem<FileId> {

    private final FileId id;

    private final FileSystemItemName name;

    private final FolderId parentId;

    private final FileSize size;

    private final FileMimeType mimeType;

    private final UserId ownerId;

    /**
     * Creates instance of {@link FileRecord} class.
     *
     * @param id       file identifier.
     * @param name     file name.
     * @param parentId file parent identifier.
     * @param size     file size.
     * @param mimeType file mimeType.
     * @param ownerId  file owner identifier.
     */
    public FileRecord(FileId id, FileSystemItemName name, FolderId parentId,
                      FileSize size, FileMimeType mimeType, UserId ownerId) {

        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.parentId = checkNotNull(parentId);
        this.size = checkNotNull(size);
        this.mimeType = checkNotNull(mimeType);
        this.ownerId = checkNotNull(ownerId);
    }

    @Override
    public FileSystemItemName name() {
        return name;
    }

    @Override
    public FolderId parentId() {
        return parentId;
    }

    @Override
    public UserId ownerId() {
        return ownerId;
    }

    @Override
    public FileId id() {
        return id;
    }

    public FileSize size() {
        return size;
    }

    public FileMimeType mimeType() {
        return mimeType;
    }

}
