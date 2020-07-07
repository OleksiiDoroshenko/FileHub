package io.javaclasses.filehub.storage.folderStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.Record;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving folder in the {@link FolderStorage}.
 */
@Immutable
public final class FolderRecord implements Record<FolderId> {

    private final FolderId id;

    private final FolderName name;

    @Nullable
    private final FolderId parentId;

    private final String TYPE = "folder";

    /**
     * Returns instance of {@link FolderRecord} class.
     *
     * @param id       folder id.
     * @param name     folder name.
     * @param parentId folder parent id.
     */
    public FolderRecord(FolderId id, FolderName name, FolderId parentId) {
        this.id = checkNotNull(id);
        this.name = checkNotNull(name);
        this.parentId = checkNotNull(parentId);
    }

    public FolderName name() {
        return name;
    }

    public FolderId parentId() {
        return parentId;
    }

    public String type() {
        return TYPE;
    }

    @Override
    public FolderId id() {
        return id;
    }
}
