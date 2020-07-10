package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DTO for {@link FolderRecord}.
 */
@Immutable
public final class FolderDTO {

    @SerializedName("name")
    private final FileSystemItemName name;

    @SerializedName("id")
    private final FileSystemItemId id;

    @SerializedName("parentId")
    private final FileSystemItemId parentId;

    /**
     * Returns instance of {@link FolderDTO} class.
     *
     * @param name     folder name.
     * @param id       folder identifier.
     * @param parentId folder parent identifier.
     */
    public FolderDTO(FileSystemItemName name, FileSystemItemId id, FileSystemItemId parentId) {
        this.name = checkNotNull(name);
        this.id = checkNotNull(id);
        this.parentId = checkNotNull(parentId);
    }

    public FileSystemItemName name() {
        return name;
    }

    public FileSystemItemId id() {
        return id;
    }

    public FileSystemItemId parentId() {
        return parentId;
    }
}
