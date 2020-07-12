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
public final class FolderDto {

    @SerializedName("name")
    private final String name;

    @SerializedName("id")
    private final String id;

    @SerializedName("parentId")
    private final String parentId;

    /**
     * Returns instance of {@link FolderDto} class.
     *
     * @param name     folder name.
     * @param id       folder identifier.
     * @param parentId folder parent identifier.
     */
    public FolderDto(FileSystemItemName name, FileSystemItemId id, FileSystemItemId parentId) {
        this.name = checkNotNull(name.value());
        this.id = checkNotNull(id.value());
        this.parentId = checkNotNull(parentId.value());
    }

    public String name() {
        return name;
    }

    public String id() {
        return id;
    }

    public String parentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderDto folderDTO = (FolderDto) o;
        return Objects.equal(name, folderDTO.name) &&
                Objects.equal(id, folderDTO.id) &&
                Objects.equal(parentId, folderDTO.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, parentId);
    }
}
