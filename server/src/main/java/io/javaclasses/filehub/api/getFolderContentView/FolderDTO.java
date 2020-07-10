package io.javaclasses.filehub.api.getFolderContentView;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderDTO folderDTO = (FolderDTO) o;
        return Objects.equal(name, folderDTO.name) &&
                Objects.equal(id, folderDTO.id) &&
                Objects.equal(parentId, folderDTO.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, parentId);
    }
}
