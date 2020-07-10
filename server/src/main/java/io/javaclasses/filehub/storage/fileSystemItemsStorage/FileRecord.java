package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.api.getFolderContentView.FileType;
import io.javaclasses.filehub.storage.userStorage.UserId;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving folder in the {@link FileStorage}.
 */
@Immutable
public final class FileRecord implements FileSystemItem {

    private final FileSystemItemId id;

    private final FileSystemItemName name;

    @Nullable
    private final FileSystemItemId parentId;

    private final FileSize size;

    private final FileMimeType mimeType;

    private final FileType type;
    private final UserId ownerId;

    /**
     * Returns instance of {@link FileRecord} class.
     *
     * @param id       file identifier.
     * @param name     file name.
     * @param parentId file parent identifier.
     * @param size     file size.
     * @param mimeType file mimeType.
     * @param type     file type.
     * @param ownerId  file owner identifier.
     */
    public FileRecord(FileSystemItemId id, FileSystemItemName name, FileSystemItemId parentId,
                      FileSize size, FileMimeType mimeType, FileType type, UserId ownerId) {

        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.parentId = checkNotNull(parentId);
        this.size = checkNotNull(size);
        this.mimeType = checkNotNull(mimeType);
        this.type = checkNotNull(type);
        this.ownerId = checkNotNull(ownerId);
    }

    @Override
    public FileSystemItemName name() {
        return name;
    }

    @Override
    public FileSystemItemId parentId() {
        return parentId;
    }

    @Override
    public UserId ownerId() {
        return ownerId;
    }

    @Override
    public FileSystemItemId id() {
        return id;
    }

    public FileSize size() {
        return size;
    }

    public FileMimeType mimeType() {
        return mimeType;
    }

    public FileType type() {
        return type;
    }
}
