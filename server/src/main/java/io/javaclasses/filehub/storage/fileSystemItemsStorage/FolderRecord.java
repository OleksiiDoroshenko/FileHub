package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.userStorage.UserId;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving folder in the {@link FolderStorage}.
 */
@Immutable
public final class FolderRecord implements FileSystemItem<FolderId> {

    private final FolderId id;

    private final FileSystemItemName name;

    @Nullable
    private final FolderId parentId;

    private final UserId ownerId;

    /**
     * Creates instance of {@link FolderRecord} class.
     *
     * @param id       folder identifier.
     * @param name     folder name.
     * @param parentId folder parent identifier.
     * @param ownerId  folder owner identifier.
     */
    public FolderRecord(FolderId id, FileSystemItemName name,
                        @Nullable FolderId parentId, UserId ownerId) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.parentId = parentId;
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
    public FolderId id() {
        return id;
    }
}
